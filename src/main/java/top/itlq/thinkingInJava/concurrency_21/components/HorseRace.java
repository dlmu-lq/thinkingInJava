package top.itlq.thinkingInJava.concurrency_21.components;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.*;

/**
 * CyclicBarrier，创建时传入一个计数值（一组任务个数）,每个任务完成后调用 CyclicBarrier.await();
 *          （一组任务即一组马比赛，await()某匹马完成一个回合比赛）
 * 当所有被等待任务都完成时，这一组任务将结束等待挂起，继续运行（一组马完成一个回合比赛后，进行下一回合）
 * 所有的任务完成后，调用创建CyclicBarrier时传入的任务（检验赛马比赛时候完成）；
 */

class Horse implements Runnable{
    private static Random random = new Random();
    private static int count = 0;

    private final int id = count++;
    private CyclicBarrier cyclicBarrier;
    private int strides;

    public Horse(CyclicBarrier cyclicBarrier){
        this.cyclicBarrier = cyclicBarrier;
    }
    @Override
    public void run(){
        try {
            while (!Thread.interrupted()){
                synchronized (this){
                    strides += random.nextInt(3);
                }
                cyclicBarrier.await();
            }
        }catch (InterruptedException e){
            System.out.println(this + " interrupted");
        }catch (BrokenBarrierException e){
            System.out.println(this + " broke barrier");
        }
    }
    @Override
    public String toString(){
        return "Horse #" + id;
    }
    public synchronized int getStrides(){
        return strides;
    }
    public String getTracks(){
        StringBuilder sb = new StringBuilder();
        for(int i=0;i<getStrides();i++){
            sb.append("*");
        }
        return sb.toString();
    }
}
public class HorseRace {
    private int FINISH_LINE = 75;
    private List<Horse> horses = new ArrayList<>();
    private ExecutorService executorService = Executors.newCachedThreadPool();
    public HorseRace(int horseNums){
        CyclicBarrier cyclicBarrier = new CyclicBarrier(horseNums, new Runnable() {
            @Override
            public void run() {
                StringBuilder sb = new StringBuilder();
                for(int i=0;i<FINISH_LINE;i++){
                    sb.append("=");
                }
                System.out.println(sb);
                for(Horse horse:horses){
                    System.out.println(horse.getTracks());
                    if(horse.getStrides() >= FINISH_LINE){
                        System.out.println(horse + " won!");
                        executorService.shutdownNow();
                        return;
                    }
                }
                try {
                    TimeUnit.MILLISECONDS.sleep(200);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        for(int i=0;i<horseNums;i++){
            Horse horse = new Horse(cyclicBarrier);
            executorService.execute(horse);
            horses.add(horse);
        }
    }

    public static void main(String...args){
        new HorseRace(5);
    }
}
