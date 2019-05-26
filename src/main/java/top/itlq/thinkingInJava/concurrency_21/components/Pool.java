package top.itlq.thinkingInJava.concurrency_21.components;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Semaphore;

public class Pool<T> {
    private List<T> items = new ArrayList<>();
    private int size;
    private Semaphore semaphore;
    private boolean[] checkouts;
    public Pool(Class<T> tClass, int size){
        this.size = size;
        semaphore = new Semaphore(size);
        checkouts = new boolean[size];
        for(int i=0;i<size;i++){
            try {
                items.add(tClass.getDeclaredConstructor().newInstance());
            } catch (InstantiationException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            }
        }
    }

    public T checkOut() throws InterruptedException{
        semaphore.acquire();
        return getItem();
    }


    public void checkIn(T item){
        if(releaseItem(item))
            semaphore.release();
    }

    private synchronized T getItem(){
        for(int i=0;i<size;i++){
            if(!checkouts[i]){
                checkouts[i] = true;
                return items.get(i);
            }
        }
        throw new RuntimeException("shouldn't reach here");
    }

    private synchronized boolean releaseItem(T item){
        int index = items.indexOf(item);
        if(index == -1) return false;
        if(checkouts[index]){
            checkouts[index] = false;
            return true;
        }
        return false;
    }


}
