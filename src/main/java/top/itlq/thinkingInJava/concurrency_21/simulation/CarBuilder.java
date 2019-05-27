package top.itlq.thinkingInJava.concurrency_21.simulation;

/**
 * 将一个Car的建造分成多个步骤任务完成，包括，制造地盘，组装发动机、车厢、轮子，完成测试；
 * 其中对于同一Car，组装发动机、车厢、轮子三步使用机器人并发完成；
 *     机器人可以使用抽象类对应不同步骤不同实现；
 *     可以将这些不同机器人存储在池中，需要时使用并拿出，用完放回；
 *
 * Car类，有状态及改变状态的方法；如 引擎、车厢、轮子是否装好等；
 * ChassisBuilder 基础无法在一个Car上并行的任务，产生一个底盘创建完成的Car队列；
 * Assembler 组装任务，从地盘完成的Car队列中取出，从RobotPool中取出对应任务的Robot进行组装（使用CyclicBarrier使组装任务并行）
 *     组装完成后，放入完成Car的队列；
 * CarReporter 一个输出完成Car队列的任务；
 * Robot 抽象类，是一个任务，有开关机状态，初始关机状态等待，有开机方法，开机后，调用需要在子类中实现的抽象方法完成任务；
 *     关机方法，等待开机状态，在完成一个任务后调用关机方法关机等待；
 *     包含Assembler域，在运行任务时调用assembler的cyclicBarrier的阻塞方法，需等待几个任务均完成后结束组装任务；
 *     包含RobotPool域，在完成任务后，释放Robot？？？
 * Robot的几个实现类；
 * RobotPool类，用于存储创建的Robot，添加robot方法，通过需要的类型找到对应Robot取出池让其运行方法；放回池方法；
 */

class Car{
    private final int id;
    private boolean engine = false, driveTrain = false, wheels = false;
    public Car(int id){
        this.id = id;
    }

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

    @Override
    public void run() {

    }
}

public class CarBuilder {
}
