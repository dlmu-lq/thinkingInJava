package top.itlq.thinkingInJava.containers_17.map;

import org.junit.jupiter.api.Test;

import java.util.LinkedHashMap;

/**
 * LinkedHashMap的最近最少使用次序，最近get put的元素都将放在序列最后
 */
public class LinkedHashMapLru {
    @Test
    public void test(){
        LinkedHashMap<Integer,String> linkedHashMap = new LinkedHashMap<>(16,0.75f,true);
        for(int i=0;i<10;i++){
            linkedHashMap.put(i,i + "str");
        }
        System.out.println(linkedHashMap);
        linkedHashMap.put(2,"2strchange");
        System.out.println(linkedHashMap);
        linkedHashMap.get(0);
        linkedHashMap.get(1);
        linkedHashMap.get(2);
        System.out.println(linkedHashMap);
        linkedHashMap.get(0);
        System.out.println(linkedHashMap);
    }
}
