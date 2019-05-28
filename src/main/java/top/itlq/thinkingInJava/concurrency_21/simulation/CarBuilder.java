package top.itlq.thinkingInJava.concurrency_21.simulation;

import java.util.HashSet;
import java.util.Random;
import java.util.concurrent.*;

/**
 * 将一个Car的建造分成多个步骤任务完成，包括，制造地盘，组装发动机、车厢、轮子，完成测试；
 * 其中对于同一Car，组装发动机、车厢、轮子三步使用机器人并发完成；
 *     机器人可以使用抽象类对应不同步骤不同实现；
 *     可以将这些不同机器人存储在池中，需要时使用并拿出，用完放回；
 *
 * Car类，有状态及改变状态的方法；如 引擎、车厢、轮子是否装好等；
 * ChassisBuilder 基础,无法在一个Car上并行的任务，产生一个底盘创建完成的Car队列；
 * Assembler 组装任务，从地盘完成的Car队列中取出，从RobotPool中取出对应任务的Robot进行组装（使用CyclicBarrier使组装任务并行）
 *     组装完成后，放入完成Car的队列；
 * CarReporter 一个输出完成Car队列的任务；
 * Robot 抽象类，是一个任务，有开关机状态，初始关机状态等待，有开机方法，开机后，调用需要在子类中实现的抽象方法完成任务；
 *     关机方法，等待开机状态，在完成一个任务后调用关机方法关机等待；
 *     包含Assembler域，在运行任务时调用assembler的cyclicBarrier的阻塞方法，需等待几个任务均完成后结束组装任务；
 *     包含RobotPool域，在完成任务后，释放Robot？？？
 * Robot的几个实现类；
 * RobotPool类，用于存储创建的Robot，添加robot方法，通过需要的类型找到对应Robot取出池让其运行方法；放回池方法；'
 *     某个组装线调用RobotPool的同步方法负责开启Robot任务并分配某个组装线(Robot需要在组装线上运行)；
 */

class Car{
    private static int counter;
    private final int id = counter++;
    private boolean engine = false, driveTrain = false, wheels = false;

    public int getId() {
        return id;
    }
    public synchronized void addEngine(){
        engine = true;
    }
    public synchronized void addDriveTrain(){
        driveTrain = true;
    }
    public synchronized void addWheels(){
        wheels = true;
    }
    @Override
    public String toString(){
        return "Car #" + id + " engine:" + engine + "; driveTrain:" + driveTrain + "; wheels:" + wheels;
    }
}

class ChassisBuilder implements Runnable{
    private BlockingQueue<Car> carQueue;
    public ChassisBuilder(BlockingQueue<Car> carQueue){
        this.carQueue = carQueue;
    }
    @Override
    public void run() {
        try {
            while (!Thread.interrupted()){
                TimeUnit.MILLISECONDS.sleep(500);
                Car car = new Car();
                System.out.println(car + " chassis finished");
                carQueue.put(car);
            }
        }catch (InterruptedException e){
            System.out.println("chassisBuilder interrupted");
        }
    }
}

class Assembler implements Runnable{
    private static int counter;
    private final int id = counter++;
    private BlockingQueue<Car> chassisQueue;
    private Car car;
    private BlockingQueue<Car> finishedQueue;
    private CyclicBarrier cyclicBarrier = new CyclicBarrier(4);
    private RobotPool robotPool;
    public Assembler(BlockingQueue<Car> chassisQueue, BlockingQueue<Car> finishedQueue, RobotPool robotPool){
        this.chassisQueue = chassisQueue;
        this.finishedQueue = finishedQueue;
        this.robotPool = robotPool;
    }
    @Override
    public void run() {
        try {
            while (!Thread.interrupted()){
                car = chassisQueue.take();
                System.out.println(car + " took in " + this);
                robotPool.hire(EngineRobot.class, this);
                robotPool.hire(WheelsRobot.class, this);
                robotPool.hire(DriveTrainRobot.class, this);
                cyclicBarrier.await();
                System.out.println(car + " finished assembling");
                finishedQueue.put(car);
            }
        }catch (InterruptedException e){
            System.out.println(this + " interrupted");
        }catch (BrokenBarrierException e){
            System.out.println(this + " broken");
        }
    }

    public CyclicBarrier getCyclicBarrier() {
        return cyclicBarrier;
    }

    public Car getCar() {
        return car;
    }

    @Override
    public String toString(){
        return "Assembler #" + id;
    }
}

class CarReporter implements Runnable{
    private BlockingQueue<Car> finishedQueue;
    public CarReporter(BlockingQueue<Car> finishedQueue){
        this.finishedQueue = finishedQueue;
    }
    @Override
    public void run() {
        try{
            while (!Thread.interrupted()){
                System.out.println("finished " + finishedQueue.take());
            }
        }catch (InterruptedException e){
            System.out.println("carReporter interrupted");
        }
    }
}

abstract class Robot implements Runnable{
    private static int counter;
    private final int id = counter++;
    private volatile boolean on = false;
    Assembler assembler;
    private RobotPool robotPool;
    public Robot(RobotPool robotPool){
        this.robotPool = robotPool;
    }
    @Override
    public void run(){
        try {
            while (!Thread.interrupted()){
                synchronized (this){
                    while (!on){
                        wait();
                    }
                }
                work();
                TimeUnit.MILLISECONDS.sleep(new Random().nextInt(1000));
                System.out.println(this + " finished with " + assembler.getCar());
                assembler.getCyclicBarrier().await();
                powerDown();
                robotPool.release(this);
            }
        }catch (InterruptedException e){
            System.out.println("assembler interrupted");
        } catch (BrokenBarrierException e) {
            System.out.println("assembler broken");
        }
    }
    public void powerDown(){
        on = false;
    }
    public synchronized void powerOn(Assembler assembler){
        this.assembler = assembler;
        on = true;
        notifyAll();
    }
    public abstract void work();
    @Override
    public String toString(){
        return this.getClass().getName() + " #" + id;
    }
}

class EngineRobot extends Robot{
    public EngineRobot(RobotPool robotPool){
        super(robotPool);
    }
    @Override
    public void work() {
        assembler.getCar().addEngine();
    }
}

class WheelsRobot extends Robot{
    public WheelsRobot(RobotPool robotPool){
        super(robotPool);
    }
    @Override
    public void work() {
        assembler.getCar().addWheels();
    }
}

class DriveTrainRobot extends Robot{
    public DriveTrainRobot(RobotPool robotPool){
        super(robotPool);
    }
    @Override
    public void work() {
        assembler.getCar().addDriveTrain();
    }
}

class RobotPool{
    HashSet<Robot> robots = new HashSet<>();
    public synchronized void add(Robot robot){
        robots.add(robot);
        notifyAll();
    }
    // 可能有多个组装线需要同时调用robots池，需要同步
    public synchronized void hire(Class<? extends Robot> rClass, Assembler assembler) throws InterruptedException{
        for(Robot robot:robots){
            if(robot.getClass().equals(rClass)){
                robots.remove(robot);
                robot.powerOn(assembler);
                System.out.println(robot + " took from robotPool for " + assembler.getCar());
                return;
            }
        }
        wait(); // 将无法在没有EngineRobot时派出WheelsRobot？
        hire(rClass, assembler);
    }
    public void release(Robot robot){
        add(robot);
    }
}

public class CarBuilder {
    public static void main(String...args) throws Exception{
        ExecutorService executorService = Executors.newCachedThreadPool();

        BlockingQueue<Car> chassisQueue = new LinkedBlockingQueue<>(),
                finishedQueue = new LinkedBlockingQueue<>();

        //地盘
        executorService.execute(new ChassisBuilder(chassisQueue));

        // robots
        RobotPool robotPool = new RobotPool();
        Robot engineRobot = new EngineRobot(robotPool),
                wheelsRobot = new WheelsRobot(robotPool),
                driveTrainRobot = new DriveTrainRobot(robotPool);
        robotPool.add(engineRobot);
        robotPool.add(wheelsRobot);
        robotPool.add(driveTrainRobot);
        executorService.execute(engineRobot);
        executorService.execute(wheelsRobot);
        executorService.execute(driveTrainRobot);
        // 两个组装线
        executorService.execute(new Assembler(chassisQueue, finishedQueue, robotPool));
        executorService.execute(new Assembler(chassisQueue, finishedQueue, robotPool));
        // 输出完成的car
        executorService.execute(new CarReporter(finishedQueue));
        TimeUnit.SECONDS.sleep(10);
        executorService.shutdownNow();
    }
}
