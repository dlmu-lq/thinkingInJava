package top.itlq.thinkingInJava.concurrency_21;

import java.io.IOException;
import java.io.PipedReader;
import java.io.PipedWriter;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * 使用管道的输入输出控制线程之间的通信
 */

class Sender implements Runnable{
    private PipedWriter pipedWriter = new PipedWriter();

    public PipedWriter getPipedWriter() {
        return pipedWriter;
    }

    @Override
    public void run(){
        try {
            while (!Thread.interrupted()){
                for(char c = 'A';c < 'z';c++){
                    TimeUnit.MILLISECONDS.sleep(10);
                    pipedWriter.write(c);
                }
            }
        } catch (Exception e) {
            System.out.println("send exception:" + e);
        }
    }
}

class Receiver implements Runnable{
    private PipedReader pipedReader;
    public Receiver(PipedWriter pipedWriter){
        try {
            this.pipedReader = new PipedReader(pipedWriter);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    @Override
    public void run() {
        try {
            while (!Thread.interrupted()){
                System.out.println("read" + (char)pipedReader.read());
            }
        }catch (Exception e){
            System.out.println("read exception:" + e);
        }
    }
}
public class PipedIO {
    public static void main(String...args) throws Exception{
        Sender sender = new Sender();
        Receiver receiver = new Receiver(sender.getPipedWriter());
        ExecutorService executorService = Executors.newCachedThreadPool();
        executorService.execute(sender);
        executorService.execute(receiver);
        executorService.shutdown();
        TimeUnit.SECONDS.sleep(2);
        executorService.shutdownNow();
    }
}
