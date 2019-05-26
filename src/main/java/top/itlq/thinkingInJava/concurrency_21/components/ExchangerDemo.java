package top.itlq.thinkingInJava.concurrency_21.components;

import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.concurrent.*;

/**
 * 两个任务先后调用同一个 Exchanger<T> 的 exchange(T)方法，
 * 第一个调用的任务将阻塞等待，直到第二个 exchange(T)方法被调用，然后两个任务将交换对象;
 * 交换后返回交换完的对象的引用；
 */

class ExchangerProducer<T> implements Runnable{
    private List<T> list;
    private Exchanger<List<T>> exchanger;
    private Class<T> tClass;
    public ExchangerProducer(List<T> list, Exchanger<List<T>> exchanger, Class<T> tClass){
        this.list = list;
        this.tClass = tClass;
        this.exchanger = exchanger;
    }
    @Override
    public void run() {
        try {
            while (!Thread.interrupted()){
                for(int i=0;i<5;i++){
                    list.add(tClass.getDeclaredConstructor().newInstance());
                }
                System.out.println("producer size before exchange:" + list.size());
                list = exchanger.exchange(list);
                System.out.println("producer size after exchange:" + list.size());
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
    }
}

class ExchangerConsumer<T> implements Runnable{
    private List<T> list;
    private Exchanger<List<T>> exchanger;
    public ExchangerConsumer(List<T> list, Exchanger<List<T>> exchanger){
        this.list = list;
        this.exchanger = exchanger;
    }
    @Override
    public void run() {
        try {
            while (!Thread.interrupted()){
                System.out.println("consumer size before exchange:" + list.size());
                list = exchanger.exchange(list);
                System.out.println("consumer size after exchange:" + list.size());
                for(T t:list){
                    list.remove(t); // 遍历过程中的移除，CopyOnWriterList
                }
                System.out.println("consumer size after remove:" + list.size());
            }
        }
        catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}

public class ExchangerDemo {
    public static void main(String...args) throws Exception{
        Exchanger<List<Fat>> exchanger = new Exchanger<>();
        List<Fat> produceList = new CopyOnWriteArrayList<>(),
                consumerList = new CopyOnWriteArrayList<>();
        ExecutorService executorService = Executors.newCachedThreadPool();
        executorService.execute(new ExchangerProducer<>(produceList, exchanger, Fat.class));
        executorService.execute(new ExchangerConsumer<>(consumerList, exchanger));
        TimeUnit.MILLISECONDS.sleep(100);
        executorService.shutdownNow();
    }
}
