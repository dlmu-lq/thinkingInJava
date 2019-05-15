package top.itlq.thinkingInJava.containers_17.functions;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.List;

/**
 * 多个进程同时修改同一个容器的内容时，你的进程一旦发现其他进程修改了容器，快速抛出异常
 */
public class FailFast {
    /**
     * 在获取iterator与使用iterator之间修改了容器，会造成不可预知的错误
     */
    @Test
    public void test1(){
        List<String> list = new ArrayList<>();
        Iterator<String> iterator = list.iterator();
        list.add("a");
        try {
            iterator.next();
        }catch (ConcurrentModificationException e){
            System.out.println(e);
        }
    }
}
