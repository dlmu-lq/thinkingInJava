/**
 * 一些Input(Output)Stream/Reader/Writer (装饰器)的
 * 不同源读写的 常见组合用法
 */
package top.itlq.thinkingInJava.io_18;

import org.junit.Test;

import java.io.*;
import java.util.Arrays;

public class NormalIOExample {

    static String filePath = "src/main/resources/io/test";
    /**
     * 按行读取字符文件
     * FileReader() BufferedReader装饰器，readLine()会将换行符去掉
     * 切记 close() 方法
     */
    @Test
    public void test1() throws Exception{
        BufferedReader br = new BufferedReader(new FileReader(filePath));
        try {
            String s;
            while ((s = br.readLine()) != null){
                System.out.println(s);
            }
        }finally {
            br.close();
        }
    }

    /**
     * 从内存中读取 StringReader
     * 注意 read() 方法及其重载的返回
     */
    @Test
    public void test2(){
        StringReader sr = new StringReader("aaaaaaaaa");
        try {
            int i;
            while ((i = sr.read()) != -1){
                System.out.print((char) i);
            }
            System.out.println();
            sr.reset();
            char [] chars = new char[5];
            char [] chars2 = new char[20];
            System.out.println(sr.read(chars,0,4)); // 返回读的个数，不能超过chars的size
            sr.reset();
            System.out.println(Arrays.toString(chars));

            System.out.println(sr.read(chars2,0,15)); // len可以超过不读就是，返回读的个数
            System.out.println(Arrays.toString(chars2));
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            sr.close();
        }
    }

    /**
     * 格式化的输入 DataInputSream
     * 读取特定类型，这里以byte数组为例
     * 注意 readByte() 返回值，判断结束，available() (谨慎使用)
     */

    @Test
    public void test3() throws Exception{
        DataInputStream dis = new DataInputStream(new ByteArrayInputStream("aaaa.啥".getBytes()));
       try {
           while (dis.available() != 0){
               System.out.print((char) dis.readByte());
           }
       }catch (Exception e){
           throw new RuntimeException(e);
       }finally {
           dis.close();
       }
    }

    /**
     * 输出到文件，FileWriter() BufferedWriter做缓冲，
     * 可再用 PrintWriter做第二层装饰器，对数据进行格式化；
     * Writer的close() 方法；
     * PrintWriter有辅助构造器，直接传入filename/File，写入时，自动执行装饰；（少有的快捷方式）
     */

    @Test
    public void test4() throws Exception{
        PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter(filePath,true)));
        try{
            pw.write("aaaaaa");
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            pw.close();
        }
        PrintStream ps = new PrintStream(new BufferedOutputStream(new FileOutputStream(filePath,true)));
        ps.printf("bbb\n%d\rccc",1);
        ps.close();
    }

    /**
     * PrintWriter可产生供人阅读的格式化数据
     * DataInputStream,DataOutputStream 则可用来恢复/存储 供流使用的数据 (面向字节)
     * 注意 writeUTF()的特殊存储方式，根据不同字符集变化编码长度 writeUTF() 使用的是 UTF-8 变体
     * 更好的数据存储读取方式，对象序列化，XML
     */
    @Test
    public void test5(){
        try {
            DataOutputStream dos = new DataOutputStream(new FileOutputStream(filePath,true));
            try {
                dos.write("\r\n喜".getBytes());
            }finally {
                dos.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 读写随机 访问文件 RandomAccessFile
     * DataInput DataOutput, 写入覆盖？试试
     * 注意，不能装饰，因为其不属于 Input/OutputStream ,由内存映射代替
     */
    @Test
    public void test6(){
        try {
            RandomAccessFile randomAccessFile = new RandomAccessFile(filePath,"rw");
            try{
                System.out.println((char) randomAccessFile.read());
                randomAccessFile.write("新增9".getBytes()); // 会覆盖后面字节位置
            }finally {
                randomAccessFile.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
