/**
 * 一个分离测试代码与应用方法的测试例子
 * 测试内存映射的效率
 * todo 先进行内存映射，再使用stream会报错；
 */
package top.itlq.thinkingInJava.io_18;

import java.io.*;
import java.nio.CharBuffer;
import java.nio.channels.FileChannel;
import java.util.Arrays;

public class MappedIOTest {
    // 内部抽象类，分离运行代码与额外代码，类似代理；
    public static abstract class Tester{
        private String name;
        public abstract void test();
        public Tester(String name){
            this.name = name;
        }
        public void runTest(){
            long start = System.currentTimeMillis();
            test();
            System.out.println(name + "运行毫秒数：" + (System.currentTimeMillis() - start));
        }
    }

    private static Tester[] testers = {

            new Tester("stream write 10MB") {
                @Override
                public void test() {
                    try {
                        DataOutputStream fc = new DataOutputStream(new BufferedOutputStream(new FileOutputStream("src/main/resources/io/temp")));
                        try {
                            for (int i=0;i<10*1024*1024/2;i++)
                                fc.writeChar('x');
                        }finally {
                            fc.close();
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                }
            },

            new Tester("mapped write 10MB") {
                @Override
                public void test() {
                    try {
                        FileChannel fc = new RandomAccessFile("src/main/resources/io/temp","rw").getChannel();
                        try {
                            CharBuffer charBuffer = fc.map(FileChannel.MapMode.READ_WRITE,0,10*1024*1024).asCharBuffer();
                            for (int i=0;i<10*1024*1024/2;i++){
                                charBuffer.put('x');
                            }
                        }finally {
                            fc.close();
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                }
            },

            new Tester("randomAccessFile write 10MB") {
                @Override
                public void test() {
                    try {
                        RandomAccessFile fc = new RandomAccessFile("src/main/resources/io/temp","rw");
                        try {
                            for (int i=0;i<10*1024*1024/2;i++)
                                fc.writeChar('x');
                        }finally {
                            fc.close();
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                }
            }
    };

    public static void main(String...args){
        Arrays.stream(testers).forEach(Tester::runTest);
    }
}
