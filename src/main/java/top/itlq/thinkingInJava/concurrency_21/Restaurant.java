package top.itlq.thinkingInJava.concurrency_21;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * 餐厅厨师、服务员作为生产者、消费者；
 * 线程协作、wait与notify
 */

class Meal{
    private int id;
    public Meal(int id){
        this.id = id;
    }
    @Override
    public String toString(){
        return "Meal #" + id;
    }
}

class WaitPerson implements Runnable{
    private Restaurant restaurant;
    public WaitPerson(Restaurant restaurant){
        this.restaurant = restaurant;
    }
    @Override
    public void run() {
        try{
            while (!Thread.interrupted()){
                synchronized (this){
                    while (restaurant.meal == null)
                        wait();
                }
                System.out.println(restaurant.meal + " served");
                restaurant.meal = null;
                synchronized (restaurant.chef){
                    restaurant.chef.notify();
                }
            }
        }catch (InterruptedException e){
            System.out.println("waitPerson interrupted");
        }
    }
}
class Chef implements Runnable{
    private Restaurant restaurant;
    private int count;
    public Chef(Restaurant restaurant){
        this.restaurant = restaurant;
    }

    @Override
    public void run() {
        try {
            while (!Thread.interrupted()){
                synchronized (this){
                    while (restaurant.meal != null){
                        wait();
                    }
                }
                if(++count > 5){
                    System.out.println("closing...");
                    restaurant.executorService.shutdownNow();
                }else{
                    System.out.println("Order Up!");
                    TimeUnit.MILLISECONDS.sleep(500);
                    restaurant.meal = new Meal(count);
                    System.out.println(restaurant.meal + " cooked");
                    synchronized (restaurant.waitPerson){
                        restaurant.waitPerson.notify();
                    }
                }
            }
            System.out.println("chef offwork");
        }catch (InterruptedException e){
            System.out.println("chef interrupted");
        }
    }
}
public class Restaurant {
    Meal meal;
    Chef chef;
    WaitPerson waitPerson;
    ExecutorService executorService = Executors.newCachedThreadPool();

    public Restaurant(){
        chef = new Chef(this);
        waitPerson = new WaitPerson(this);
        executorService.execute(chef);
        executorService.execute(waitPerson);
        executorService.shutdown();
    }

    public static void main(String...args){
        new Restaurant();
    }
}
