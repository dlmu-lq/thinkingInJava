package top.itlq.thinkingInJava.io_18.serializable.test;

import org.junit.Test;
import top.itlq.thinkingInJava.io_18.serializable.test.Alien;

import java.io.*;

public class TestSerializable {
    static String filePath = "src/main/java/top/itlq/thinkingInJava/io_18/serializable/test.obj";
    @Test
    public void test1() throws Exception{
        ObjectOutputStream oos = new ObjectOutputStream(new BufferedOutputStream(new FileOutputStream(filePath)));
        Alien alien1 = new Alien();
        System.out.println(alien1.s1);
        oos.writeObject(alien1);
        oos.flush();
        oos.close();
        ObjectInputStream ois = new ObjectInputStream(new BufferedInputStream(new FileInputStream(filePath)));
        Alien alien = (Alien) ois.readObject();
        System.out.println(alien.s1);
        System.out.println(alien == alien1);
    }
}
