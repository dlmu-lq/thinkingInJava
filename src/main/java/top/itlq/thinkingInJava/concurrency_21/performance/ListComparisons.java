package top.itlq.thinkingInJava.concurrency_21.performance;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * 实现
 */

abstract class ListTester extends Tester<List<Integer>>{

    public ListTester(String id, int readNums, int writeNums) {
        super(id, readNums, writeNums);
    }

    class Reader extends TestTask{
        private long result;
        @Override
        public void test() {
            for(int i=0;i<testCycles;i++){
                for(int ii=0;ii<testSize;ii++){
                    result += testContainer.get(ii);
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
            for(int i=0;i<testCycles;i++){
                for(int ii=0;ii<testSize;ii++){
                    testContainer.set(ii, writeData[ii]);
                }
            }
        }

        @Override
        public void putResults() {
            writeTime += duration;
        }
    }

    @Override
    public void startReadersAndWriters(){
        for(int i=0;i<readNums;i++){
            executorService.execute(new Reader());
        }
        for(int i=0;i<writeNums;i++){
            executorService.execute(new Writer());
        }
    }
}

class SynchronizedArrayListTester extends ListTester{

    public SynchronizedArrayListTester(String id,
                                       int readNums, int writeNums) {
        super(id, readNums, writeNums);
    }

    @Override
    public List<Integer> containerInitializer() {
        List<Integer> integerList = new ArrayList<>();
        Random random = new Random();
        for(int i=0;i<testSize;i++){
            integerList.add(random.nextInt());
        }
        return Collections.synchronizedList(new ArrayList<>(integerList));
    }
}

class CopyOnWriteArrayListTester extends ListTester{

    public CopyOnWriteArrayListTester(String id,
                                      int readNums, int writeNums) {
        super(id, readNums, writeNums);
    }

    @Override
    public List<Integer> containerInitializer() {
        List<Integer> integerList = new ArrayList<>();
        Random random = new Random();
        for(int i=0;i<testSize;i++){
            integerList.add(random.nextInt());
        }
        return new CopyOnWriteArrayList<>(integerList);
    }
}

public class ListComparisons {
    public static void main(String...args){
        Tester.initMain();
        new CopyOnWriteArrayListTester("copyOnWriteArrayList  r10 w0 ", 10 , 0);
        new CopyOnWriteArrayListTester("copyOnWriteArrayList  r10 w10", 10 , 10);
        new CopyOnWriteArrayListTester("copyOnWriteArrayList  r10 w5 ", 10 , 5);
        new CopyOnWriteArrayListTester("synchronizedArrayList r10 w0 ", 10 , 0);
        new CopyOnWriteArrayListTester("synchronizedArrayList r10 w10", 10 , 10);
        Tester.executorService.shutdown();
    }
}
