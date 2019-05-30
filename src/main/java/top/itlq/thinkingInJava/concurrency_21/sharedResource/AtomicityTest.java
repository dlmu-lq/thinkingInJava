package top.itlq.thinkingInJava.concurrency_21.sharedResource;

/**
 * 一个同步方法修改，另一个非同步方法返回，有可能获取到不稳定状态；
 */
public class AtomicityTest implements Runnable{

    private int value;

    @Override
    public void run() {
        while (true)
            increment();
    }

    private synchronized void increment(){
        value++;
        value++;
    }

    // 若像正确，所有相关的方法都应设为同步
    public int getValue(){
        return value;
    }

    public static void main(String...args){
        AtomicityTest at = new AtomicityTest();
        new Thread(at).start();
        while (true){
            int val = at.getValue();
            if(val % 2 != 0){
                System.out.println(val);
                System.exit(0);
            }
        }
    }
}
