/**
 * 新IO 的管道 缓冲器操作
 */
package top.itlq.thinkingInJava.io_18;

import org.junit.Test;

public class NewIO {
    /**
     * 通过ByteBuffer缓冲器，FileChannel 读写文件；
     * ByteBuffer.wrap(byte [])将byte数组包装通过channel写入；
     * channel.read(ByteBuffer.allocate(字节数):ByteBuffer)将数据读入ByteBuffer
     * read读入之后buffer.flip()可供读，ByteBuffer.get() 读取 todo 试着输出字符串
     * 别忘记close
     */

    @Test
    public void test1(){

    }

    /**
     * 通过ByteBuffer读取写入，转移
     * 注意 in.read(ByteBuffer) ByteBuffer.flip() out.write(ByteBuffer) ByteBuffer.clear()
     * 虽然拙劣，却高效；
     * 可用 inChannel.transferTo(0,in.size(),outChannel)、
     * //outChannel.transferFrom(inChannel,0,inChannel.size()) 实现
     */

    @Test
    public void test2(){

    }

    @Test
    public void test3(){

    }

    /**
     * Byte与字符转换 直接asCharBuffer()。toString()不好用
     * Charset.decode();   getBytes("UTF-16BE") ;
     * asCharBuffer().put("text") asCharBuffer.toString()
     */

    @Test
    public void test4(){

    }

    /**
     * 获取可用的Charset
     * Charset.availableCharsets();
     * 别名 aliases();
     */
}
