package top.itlq.thinkingInJava.holdingObjects_11.map;

import org.junit.Test;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TestMap {
    @Test
    public void test1(){
        Map<Person,List<String>> contacts = new HashMap<Person, List<String>>();
        contacts.put(new Person("l"), Arrays.asList("111","222"));
        // 要想覆盖需重写hashCode equals两个方法
        contacts.put(new Person("l"),Arrays.asList("333","444"));
        System.out.println(contacts);

        Map<String,List<String>> keyStringMap = new HashMap<String, List<String>>();
        keyStringMap.put(new String("l"), Arrays.asList("111","222"));
        keyStringMap.put(new String("l"),Arrays.asList("333","444"));
        System.out.println(keyStringMap);
    }
}