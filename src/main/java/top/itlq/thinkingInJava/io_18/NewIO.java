/**
 * 新IO 的管道 缓冲器操作
 */
package top.itlq.thinkingInJava.io_18;

import org.junit.Test;

import java.io.*;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.channels.FileChannel;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.SortedMap;

public class NewIO {
    static String filePath = "src/main/resources/io/test";
    static String filePath2 = "src/main/resources/io/test2";
    static String filePath3 = "src/main/resources/io/test3";

    /**
     * 通过ByteBuffer缓冲器，FileChannel 读写文件；
     * ByteBuffer.wrap(byte [])将byte数组包装通过channel写入；
     * channel.read(ByteBuffer.allocate(字节数):ByteBuffer)将数据读入ByteBuffer
     * read读入之后buffer.flip()可供读，ByteBuffer.get() 读取 todo 试着输出字符串
     * 别忘记close
     */

    @Test
    public void test1(){
        try {
            FileChannel in = new FileInputStream(filePath).getChannel();
            ByteBuffer bb = ByteBuffer.allocate(1);
            try {
                while (in.read(bb) != -1){
                    bb.flip();
                    System.out.print((char) bb.get());
                    bb.clear();
                }
            }finally {
                in.close();
            }

            System.out.println();
            System.out.println();

            FileChannel out = new FileOutputStream(filePath,true).getChannel();
            out.write(ByteBuffer.wrap("哈哈".getBytes()));
            out.close();

            //
            bb = ByteBuffer.allocate(2);
            byte [] bytes = new byte[100];
            in = new FileInputStream(filePath).getChannel();
            try{
                int i = 0;
                while (in.read(bb) != -1){
                    bb.flip();
                    bytes[i++] = bb.get();
                    bytes[i++] = bb.get();
                    bb.clear();
                }
            }finally {
                in.close();
            }
            System.out.println(new String(bytes, StandardCharsets.UTF_8)); // 字节数组为字符
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 通过ByteBuffer读取写入，转移
     * 注意 in.read(ByteBuffer) ByteBuffer.flip() out.write(ByteBuffer) ByteBuffer.clear()
     * 虽然拙劣，却高效；
     * 可用 inChannel.transferTo(0,in.size(),outChannel)、
     * //outChannel.transferFrom(inChannel,0,inChannel.size()) 实现
     */

    @Test
    public void test3(){
        try {
            FileChannel in = new FileInputStream(filePath).getChannel(),
                    out = new FileOutputStream(filePath2).getChannel();
            try {
                in.transferTo(0,in.size(),out);
            }finally {
                in.close();
                out.close();
            }
        }catch (Exception e){

        }
    }

    /**
     * ByteBuffer与字符转换 直接asCharBuffer().toString()不好用
     * Charset.decode();   getBytes("UTF-16BE") ;
     * asCharBuffer().put("text") asCharBuffer.toString()
     */

    @Test
    public void test4(){
        try {
            FileChannel in = new FileInputStream(filePath).getChannel();
            ByteBuffer bb = ByteBuffer.allocate(1024);
            try{
                in.read(bb);
                bb.flip();
                // 乱码
                 System.out.println(bb.asCharBuffer().toString());
                 System.out.println();
                // 利用编码方式解码
                System.out.println(Charset.forName(System.getProperty("file.encoding")).decode(bb));
                System.out.println();
                // 写入时，使用asCharBuffer()输入值
                File file = new File(filePath3);
                if(!file.exists())
                    file.createNewFile();
                FileChannel out = new FileOutputStream(filePath3).getChannel();

                bb = ByteBuffer.allocate(1024); // 此处clear()也没用，不能复用
                bb.asCharBuffer().put("test"); // 默认使用UTF-16BE编码
                out.write(bb);
                in = new FileInputStream(filePath3).getChannel();
                bb.clear();
                in.read(bb);
                bb.flip();
                System.out.println(bb.asCharBuffer());
            }finally {
                in.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 获取可用的Charset
     * Charset.availableCharsets();
     * 别名 aliases();
     */
    @Test
    public void test5(){
        SortedMap<String,Charset> charsets = Charset.availableCharsets();
        for(Charset charset:charsets.values()){
            System.out.println(charset.name() + ":" + charset.aliases());
        }
    }

    /**
     * ByteBuffer 操作基本类型
     * as基本类型Buffer
     */
    @Test
    public void test6(){
        ByteBuffer buff = ByteBuffer.allocate(1024);
        buff.asFloatBuffer().put(0.222f);
        try {
            FileChannel out = new FileOutputStream(filePath3,true).getChannel();

            try{
                out.write(buff); // 将float按照字节写入文件，打开乱码，但能顾读出
                out.close();
                buff.clear();
                System.out.println(buff.getFloat());
                FileChannel in = new FileInputStream(filePath3).getChannel();
                in.read(buff);
                buff.flip();
                System.out.println(buff.getFloat());
            }finally {
                out.close();
            }
        }catch (Exception e){
            e.printStackTrace();
        }

    }

    /**
     * ByteBuffer 视图和存储次序
     */
    @Test
    public void test7(){
        ByteBuffer buffer = ByteBuffer.allocate(14);
        buffer.asCharBuffer().put("abcdef总");
        System.out.println(new String(buffer.array(), StandardCharsets.UTF_16BE));
        // 切换到小端，存储次序已乱
        buffer.clear();
        buffer.order(ByteOrder.LITTLE_ENDIAN);
        buffer.asCharBuffer().put("abcdef总");
        System.out.println(new String(buffer.array(), StandardCharsets.UTF_16BE));
        // 但使用缓冲器读取没问题
        System.out.println(buffer.asCharBuffer());
    }
}
