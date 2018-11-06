/**
 * 缓冲器及视图缓冲器
 */
package top.itlq.thinkingInJava.io_18;

import org.junit.Test;

import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
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
        ByteBuffer buff = null;
        try {
            buff = ByteBuffer.wrap("abcde".getBytes("UTF-16BE"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        CharBuffer charBuffer = buff.asCharBuffer();
        charBuffer.rewind();
        upsideDown(charBuffer);
        while (buff.hasRemaining())
            System.out.print(buff.getChar());
    }



    public static void upsideDown(CharBuffer charBuffer){
        char [] chars = new char[charBuffer.capacity()];
        int i = 0;
        while (charBuffer.hasRemaining())
            chars[i++] = charBuffer.get();
        charBuffer.rewind();
        i = chars.length;
        while (charBuffer.hasRemaining()){
            charBuffer.put(chars[--i]);
        }
    }

    /**
     * 交换字符串的一部分，使用ByteBuff的mark和reset
     */
    @Test
    public void test4(){
        char [] chars = "abcdef".toCharArray();
        ByteBuffer buff = ByteBuffer.allocate(chars.length * 2);
        CharBuffer charBuffer = buff.asCharBuffer();
        charBuffer.put(chars);
        System.out.println("空" + charBuffer);
        charBuffer.rewind();
        System.out.println(charBuffer);
        symmetricScramble(charBuffer);
        System.out.println(charBuffer.rewind()); // 必须rewind才能输出
    }

    public static void symmetricScramble(CharBuffer charBuffer){
        while (charBuffer.hasRemaining()){
            charBuffer.mark();
            char c1 = charBuffer.get();
            char c2 = charBuffer.get();
            char c3 = charBuffer.get();
            charBuffer.reset();
            charBuffer.put(c3).put(c2).put(c1);
        }
    }
}
