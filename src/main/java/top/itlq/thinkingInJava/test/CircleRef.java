package top.itlq.thinkingInJava.test;

import org.junit.Test;

import java.io.*;

public class CircleRef implements Serializable {
    // 堆栈错误，递归无限创建，抛异常
//    private CircleRef circleRef = new CircleRef();

    // 自己持有一个同类而已
    private CircleRef circleRef = null;

    // 自循环引用，序列化可能会出问题，GC时会使用根进程有向图判断，不影响回收
    private CircleRef circleRef1 = this;

    private int i = 1;

    @Test
    public void test(){
        CircleRef circleRef = new CircleRef();
        System.out.println(circleRef == circleRef.circleRef1);
    }

    /**
     * 序列化为文件没出问题
     */
    @Test
    public void serialize(){
        CircleRef circleRef = new CircleRef();
        String resourcePath = this.getClass().getResource("/").getPath();
        try {
            ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(resourcePath + "test/circleref/a"));
            oos.writeObject(circleRef);
            oos.flush();
            oos.close();
            ObjectInputStream ois = new ObjectInputStream(new FileInputStream(resourcePath + "test/circleref/a"));
            CircleRef circleRef1 = (CircleRef)ois.readObject();
            System.out.println(circleRef1.circleRef1 == circleRef1);
            System.out.println(circleRef1.i);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
