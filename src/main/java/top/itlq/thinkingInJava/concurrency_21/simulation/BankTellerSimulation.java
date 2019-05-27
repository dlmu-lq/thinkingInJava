package top.itlq.thinkingInJava.concurrency_21.simulation;

import java.util.*;
import java.util.concurrent.*;

/**
 * 银行出纳员仿真；
 * 客户，以某个不定频率出现，需要服务不定时间，需要排队；
 * 出纳员，可能在服务客户，可能在做别的事情，取决于当前排队客户数是否大于出纳员的2倍；
 * 出纳员工作需要定时调整；
 *
 * 客户作为一个阻塞队列，使用BlockingQueue; 客户的到来作为一个任务，添加至队列；
 * 每个出纳员作为一个任务，从客户阻塞队列中取出进行服务，可以在服务客户状态，也可以在其它状态；
 * 出纳员管理作为一个任务，根据客户数量与出纳员数量的比值，决定从闲置出纳员/重新创建添加出纳员，或分配出纳员不再服务客户；
 *     正在服务的出纳员作为一个排序队列，可以根据需求优先取出服务人数少的出纳员进行
 */

class Customer{
    private static int counter;
    private final int id = counter++;
    private final int serviceTime;
    public Customer(int serviceTime){
        this.serviceTime = serviceTime;
    }
    public int getServiceTime() {
        return serviceTime;
    }
    @Override
    public String toString(){
        return "Customer #" + id;
    }
}

class CustomerLine implements Runnable{
    private BlockingQueue<Customer> customerQueue;
    private Random random = new Random();
    public CustomerLine(BlockingQueue<Customer> customerQueue){
        this.customerQueue = customerQueue;
    }
    @Override
    public void run() {
        try {
            while (!Thread.interrupted()){
                customerQueue.put(new Customer(random.nextInt(1500)));
                TimeUnit.MILLISECONDS.sleep(random.nextInt(200));
            }
        }catch (InterruptedException e){
            System.out.println("customerLine interrupted");
        }
    }
}

class Teller implements Runnable, Comparable<Teller>{
    private static int counter;
    private final int id = counter++;
    private BlockingQueue<Customer> customerQueue;
    private int servedCount;
    private volatile boolean serving = true;
    public Teller(BlockingQueue<Customer> customerQueue){
        this.customerQueue = customerQueue;
    }
    @Override
    public void run() {
        try {
            while (!Thread.interrupted()){
                Customer customer = customerQueue.take();
                System.out.println(this + " serving " + customer);
                TimeUnit.MILLISECONDS.sleep(customer.getServiceTime());
                synchronized (this){
                    servedCount++;
                    while (!serving){
                        System.out.println(this + " waiting...");
                        wait();
                        System.out.println(this + " awake");
                    }
                }
            }
        }catch (InterruptedException e){
            System.out.println(this + " interrupted");
        }
    }
    // 客户不多，做其它事去
    public void doSomethingElse(){
        this.serving = false;
    }
    // 调用通知方法必须同步
    public synchronized void serveCustomer(){
        this.serving = true;
        notifyAll();
    }
    @Override
    public String toString(){
        return "Teller #" + id;
    }

    @Override
    public int compareTo(Teller o) {
        return -Integer.compare(this.servedCount, o.servedCount); // 服务数量多的优先可以被重新分配取出不再服务
    }
}

class TellerManager implements Runnable{
    private BlockingQueue<Customer> customerQueue;
    private PriorityQueue<Teller> servingTellers = new PriorityQueue<>();
    private Queue<Teller> doingSomethingElseTellerQueue = new LinkedList<>();
    private int adjustPeriod; // 调整周期
    private ExecutorService exec;
    public TellerManager(BlockingQueue<Customer> customerQueue, int adjustPeriod, ExecutorService exec){
        this.customerQueue = customerQueue;
        this.adjustPeriod = adjustPeriod;
        this.exec = exec;
        Teller teller = new Teller(customerQueue);
        exec.execute(teller);
        servingTellers.add(teller);
    }

    public void addTellers(){
        if(servingTellers.size() == 0 || (customerQueue.size() / servingTellers.size() > 2)){
            if(doingSomethingElseTellerQueue.size() > 0){
                Teller teller = doingSomethingElseTellerQueue.remove();
                teller.serveCustomer();
                servingTellers.add(teller);
                addTellers();
            }
            Teller teller = new Teller(customerQueue);
            exec.execute(teller);
            servingTellers.add(teller);
            addTellers();
        }
    }

    public void reassignTeller(){
        if((customerQueue.size() > 1 && customerQueue.size() / servingTellers.size() < 2)
                || (customerQueue.size() == 0 && servingTellers.size() > 1)){
            Teller teller = servingTellers.poll();
            teller.doSomethingElse();
            doingSomethingElseTellerQueue.offer(teller);
            reassignTeller();
        }
    }

    @Override
    public void run() {
        try {
            while (!Thread.interrupted()){
                TimeUnit.MILLISECONDS.sleep(adjustPeriod);
                addTellers();
                reassignTeller();
                System.out.println("customers:" + customerQueue);
                System.out.println("servingTellers:" + servingTellers);
            }
        }catch (InterruptedException e) {
            System.out.println("tellerManager interrupted");
        }
    }
}

public class BankTellerSimulation {
    public static void main(String...args) throws Exception{
        BlockingQueue<Customer> customerQueue = new ArrayBlockingQueue<>(10);
        ExecutorService executorService = Executors.newCachedThreadPool();
        Future<?> customeLine = executorService.submit(new CustomerLine(customerQueue));
        executorService.execute(new TellerManager(customerQueue, 2000, executorService));
        TimeUnit.SECONDS.sleep(3);
        customeLine.cancel(true);
        TimeUnit.SECONDS.sleep(7);
        executorService.shutdownNow();
    }
}
