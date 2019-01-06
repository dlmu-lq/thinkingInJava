package top.itlq.thinkingInJava.io_18.serializable.test1;

import org.junit.Test;

import java.io.*;

public class TestSerializable1 {
    static String filePathParent = "src/main/java/top/itlq/thinkingInJava/io_18/serializable/";
    @Test
    public void test1() throws Exception{
        ObjectInputStream ois = new ObjectInputStream(new BufferedInputStream(new FileInputStream(new File(filePathParent,"test.obj"))));
        Object alien = ois.readObject();
        System.out.println(alien.getClass());
    }
}
