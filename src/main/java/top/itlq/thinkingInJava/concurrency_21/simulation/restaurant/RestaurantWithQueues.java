package top.itlq.thinkingInJava.concurrency_21.simulation.restaurant;

import top.itlq.thinkingInJava.enum_19.Course;
import top.itlq.thinkingInJava.enum_19.Food;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.*;

/**
 * 饭店仿真
 * 食客，点菜，一次只能吃一个菜；
 * 服务员，从食客拿到点菜，给厨师，从厨师拿菜，给食客上菜；
 * 厨师，从服务员拿到菜单，做菜，放入做好的对列；
 *
 * 食客做为一个任务，构造时即加入一个服务员，用来给他点菜（与服务员通信1）；任务运行时即点菜
 *     等菜等待，等菜到达（与服务员通信2），这里使用 SynchronousQueue 一次只能点一个菜；
 * 服务员，做为一个任务，要点菜，要上菜，将点菜动作放在食客点菜任务中触发（与食客通信1，与厨师通信1）；
 *     在服务员任务中，负责从做好的菜中，使用阻塞队列，BlockingQueue，取出（与厨师通信2），上菜（与食客通信2）
 * 厨师，做为一个任务，从未做的点的菜队列中取出（与服务员通信1），制作，放入做好的菜队列（与服务员通信2）
 */

class Order{
    private Customer customer;
    private Food food;
    private WaitPerson waitPerson;
    public Order(Customer customer, Food food, WaitPerson waitPerson){
        this.customer = customer;
        this.food = food;
        this.waitPerson = waitPerson;
    }
    public Customer getCustomer() {
        return customer;
    }

    public Food getFood() {
        return food;
    }

    public WaitPerson getWaitPerson() {
        return waitPerson;
    }

    @Override
    public String toString(){
        return food + " for " + customer + " served by " + waitPerson;
    }
}

class Customer implements Runnable{
    private static int counter;
    private final int id = counter++;
    private SynchronousQueue<Food> platePlace = new SynchronousQueue<>();
    private WaitPerson waitPerson;
    public Customer(WaitPerson waitPerson){
        this.waitPerson = waitPerson;
    }
    public void deliver(Food food){
        try {
            System.out.println(this + " got " + food);
            platePlace.put(food);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    @Override
    public void run() {
        try {
            while (!Thread.interrupted()){
                for(Course course:Course.values()){
                    Food food = course.randomSelection();
                    System.out.println(this + " ordered " + food);
                    waitPerson.orderUp(new Order(this, food, waitPerson));
                    System.out.println(this + " waiting for " + food);
                    platePlace.take();
                    System.out.println(this + " eating " + food);
                }
            }
        }catch (InterruptedException e){
            System.out.println(this + " interrupted");
        }
    }
    @Override
    public String toString(){
        return "Customer #" + id;
    }
}

class WaitPerson implements Runnable{
    private static int counter;
    private final int id = counter++;
    private BlockingQueue<Order> orders;
    private BlockingQueue<Order> filledOrders;
    public WaitPerson(BlockingQueue<Order> orders, BlockingQueue<Order> filledOrders){
        this.orders = orders;
        this.filledOrders = filledOrders;
    }

    /**
     * 从客户那里触发，服务员下单，厨师会从orders中取出制作
     * @param order
     */
    public void orderUp(Order order){
        try {
            System.out.println(this + " order up " + order);
            orders.put(order);
        } catch (InterruptedException e) {
            System.out.println(this + " interrupted");
        }
    }
    @Override
    public void run() {
        try {
            while (!Thread.interrupted()){
                // 取出厨师做完的进行上菜
                Order order = filledOrders.take();
                System.out.println(this + " take finished " + order);
                order.getCustomer().deliver(order.getFood());
            }
        }catch (InterruptedException e){
            System.out.println(this + " interrupted");
        }
    }
    @Override
    public String toString(){
        return "WaitPerson #" + id;
    }
}

class Chef implements Runnable{
    private static int counter;
    private final int id = counter++;
    private BlockingQueue<Order> orders;
    private BlockingQueue<Order> filledOrders;
    public Chef(BlockingQueue<Order> orders, BlockingQueue<Order> filledOrders){
        this.orders = orders;
        this.filledOrders = filledOrders;
    }
    @Override
    public void run() {
        try {
            while (!Thread.interrupted()){
                Order order = orders.take();
                System.out.println(this + " taked " + order);
                TimeUnit.MILLISECONDS.sleep(1000);
                System.out.println(this + " cooked " + order);
                filledOrders.put(order);
            }
        }catch (InterruptedException e){
            System.out.println(this + " interrupted");
        }
    }
    @Override
    public String toString(){
        return "Chef #" + id;
    }
}

class Restaurant implements Runnable{
    private List<WaitPerson> waitPeople;
    private List<Chef> chefs;
    private BlockingQueue<Order> orders = new LinkedBlockingDeque<>();
    private BlockingQueue<Order> filledOrders = new LinkedBlockingDeque<>();
    private ExecutorService exec;
    public Restaurant(int waitPersonNums, int chefNums, ExecutorService exec){
        waitPeople = new ArrayList<>(waitPersonNums);
        for(int i=0;i<waitPersonNums;i++){
            WaitPerson waitPerson = new WaitPerson(orders, filledOrders);
            waitPeople.add(waitPerson);
            exec.execute(waitPerson);
        }
        chefs = new ArrayList<>(chefNums);
        for(int i=0;i<chefNums;i++){
            Chef chef = new Chef(orders, filledOrders);
            chefs.add(chef);
            exec.execute(chef);
        }
        this.exec = exec;
    }
    @Override
    public void run() {
        try {
            while (!Thread.interrupted()){
                WaitPerson waitPerson = waitPeople.get(new Random().nextInt(waitPeople.size()));
                Customer customer = new Customer(waitPerson);
                exec.execute(customer);
                TimeUnit.MILLISECONDS.sleep(2000);
            }
        } catch (InterruptedException e) {
            System.out.println("Restaurant closed");
        }
    }
}

public class RestaurantWithQueues{
    public static void main(String...args) throws Exception{
        ExecutorService exec = Executors.newCachedThreadPool();
        exec.execute(new Restaurant(5, 2, exec));
        TimeUnit.SECONDS.sleep(5);
        exec.shutdownNow();
    }
}
