package top.itlq.thinkingInJava.concurrency_21.activeObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.*;

/**
 *
 */
public class ActiveObjectDemo {
    private ExecutorService executorService = Executors.newSingleThreadExecutor();
    private static Random random = new Random();
    private void pause(){
        try{
            TimeUnit.MILLISECONDS.sleep(100);
        }catch (InterruptedException e){
            System.out.println("pause interrupted");
        }
    }
    public Future<Integer> addInt(int i, int j){
        return executorService.submit(new Callable<>() {
            @Override
            public Integer call() throws Exception {
                System.out.println("starting " + i + " + " + j);
                pause();
                return i + j;
            }
        });
    }
    public Future<Float> addFloat(float i, float j){
        return executorService.submit(() -> {
            System.out.println("starting " + i + " + " + j);
            pause();
            return i + j;
        });
    }

    public void shutdown(){
        executorService.shutdown();
    }

    public static void main(String...args) throws ExecutionException, InterruptedException {
        ActiveObjectDemo d1 = new ActiveObjectDemo();
        List<Future<?>> results = new CopyOnWriteArrayList<>();
        for(float i=1.0f;i<2.0f;i+=0.2f){
            results.add(d1.addFloat(i,i));
        }
        for(int i=1;i<3;i++){
            results.add(d1.addInt(i,i));
        }
        while (results.size() > 0){
            for(Future<?> future:results){
                if(future.isDone()){
                    System.out.println(future.get());
                    results.remove(future);
                }
            }
        }
        d1.shutdown();
    }
}
