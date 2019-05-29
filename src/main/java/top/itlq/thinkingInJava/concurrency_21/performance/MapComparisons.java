package top.itlq.thinkingInJava.concurrency_21.performance;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 抽象类继承泛型参数为Map的Tester，包含两个确定的读写任务和任务创建抽象方法的实现；
 * 使用不同的Map，synchronizedmap，concurrenthashmap，测试
 */
abstract class MapTester extends Tester<Map<Integer,Integer>>{

    public MapTester(String id, int readNums, int writeNums) {
        super(id, readNums, writeNums);
    }

    @Override
    public void startReadersAndWriters() {
        for(int i=0;i<readNums;i++){
            executorService.execute(new Reader());
        }
        for(int i=0;i<writeNums;i++){
            executorService.execute(new Writer());
        }
    }

    class Reader extends TestTask{
        private long result;
        @Override
        public void test() {
            for(int i=0;i<testCycles;i++) {
                for (Map.Entry<Integer, Integer> entry : testContainer.entrySet()) {
                    result += entry.getValue();
                }
            }
        }

        @Override
        public void putResults() {
            readResult += result;
            readTime += duration;
        }
    }

    class Writer extends TestTask{

        @Override
        public void test() {
            for(int i=0;i<testCycles;i++) {
                for (int ii=0;ii<testSize;ii++) {
                    testContainer.put(ii, writeData[ii]);
                }
            }
        }

        @Override
        public void putResults() {
            writeTime += writeTime;
        }
    }
}

class SynchronizedMapTester extends MapTester{

    public SynchronizedMapTester(String id, int readNums, int writeNums) {
        super(id, readNums, writeNums);
    }

    @Override
    public Map<Integer, Integer> containerInitializer() {
        HashMap<Integer,Integer> map = new HashMap<>(testSize);
        for(int i=0;i<testSize;i++){
            map.put(i,i);
        }
        return Collections.synchronizedMap(map);
    }

}

class ConcurrentHashMapTester extends MapTester{

    public ConcurrentHashMapTester(String id, int readNums, int writeNums) {
        super(id, readNums, writeNums);
    }

    @Override
    public Map<Integer, Integer> containerInitializer() {
        HashMap<Integer,Integer> map = new HashMap<>(testSize);
        for(int i=0;i<testSize;i++){
            map.put(i,i);
        }
        return new ConcurrentHashMap<>(map);
    }
}

public class MapComparisons{
    public static void main(String...args){
        new SynchronizedMapTester("SynchronizedMapTester r10 w10", 10 ,10);
        new SynchronizedMapTester("SynchronizedMapTester r10 w5", 10 ,5);
        new SynchronizedMapTester("SynchronizedMapTester r10 w0", 10 ,0);
        new ConcurrentHashMapTester("ConcurrentHashMapTester r10 w10", 10 ,10);
        new ConcurrentHashMapTester("ConcurrentHashMapTester r10 w5", 10 ,5);
        new ConcurrentHashMapTester("ConcurrentHashMapTester r10 w0", 10 ,0);
    }
}
