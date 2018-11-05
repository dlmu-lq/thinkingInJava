/**
 * 缓冲器及视图缓冲器
 */
package top.itlq.thinkingInJava.io_18;

import org.junit.Test;

import java.nio.ByteBuffer;
import java.nio.IntBuffer;
import java.nio.ShortBuffer;

public class ByteBufferView {
    /**
     * 对视图的操作都反映在实际缓冲器中
     * 注意put方法
     */
    @Test
    public void test1(){
        ByteBuffer buff = ByteBuffer.allocate(1024);
        IntBuffer intBuffer = buff.asIntBuffer();
        intBuffer.put(65);
        buff.flip(); // 测试，无用
        buff.clear(); // 测试，无用
//        buff.reset(); // position = mark 没有mark抛异常
        System.out.println(buff.getChar());
        System.out.println(buff.getChar());
    }

    /**
     * 基本类型视图缓冲器
     * hasRemaining() position() get();
     * double
     */
    @Test
    public void test2(){
        ByteBuffer buff = ByteBuffer.wrap(new byte[]{0,0,0,65});
        while (buff.hasRemaining())
            System.out.println("char：" + buff.getChar());
        buff.rewind();
        ShortBuffer shortBuffer = buff.asShortBuffer();
        while (shortBuffer.hasRemaining())
            System.out.println("short：" + shortBuffer.get());
        buff.rewind();
        IntBuffer intBuffer = buff.asIntBuffer();
        while (intBuffer.hasRemaining())
            System.out.println("int：" + intBuffer.get());
    }

    /**
     * 利用ByteBuffer 使字符串倒序（任意调换顺序）
     * todo
     */
    @Test
    public void test3(){

    }
}
