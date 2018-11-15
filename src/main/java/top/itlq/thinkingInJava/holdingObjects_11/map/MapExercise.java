package top.itlq.thinkingInJava.holdingObjects_11.map;

import org.junit.Test;

import java.util.*;

public class MapExercise {
    /**
     * 练习1 通过keySet遍历
     * entrySet(),和 values
     */
    @Test
    public void test1(){
        Map<String,Person> personMap = new HashMap<String, Person>();
        personMap.put("name:l",new Person("l"));
        personMap.put("name:q",new Person("q"));
        personMap.put("name:c",new Person("c"));
        Iterator<String> iterator = personMap.keySet().iterator();
        while (iterator.hasNext()){
            String key = iterator.next();
            System.out.println(key);
            personMap.get(key).speek();
        }
        System.out.println(personMap);
        System.out.println(personMap.entrySet());
        System.out.println(personMap.values());
    }

    /**
     * 练习2 排序
     */
    @Test
    public void test2(){
        Map<String,Person> personMap = new HashMap<String, Person>();
        LinkedHashMap<String,Person> linkedHashMap = new LinkedHashMap<String, Person>();
        personMap.put("name:l",new Person("l"));
        personMap.put("name:q",new Person("q"));
        personMap.put("name:c",new Person("c"));
        Set<String> keys = personMap.keySet();
        Set<String> keysCompared = new TreeSet<String>();
        keysCompared.addAll(keys);
        for(String key:keysCompared){
            linkedHashMap.put(key,personMap.get(key));
        }
        System.out.println(linkedHashMap);
    }

    @Test
    public void test3(){
        Map<String,String> map = new HashMap<>();
        map.put("testKey","testValue");
        for(Map.Entry<String,String> entry:map.entrySet())
            System.out.print(entry.getKey() + ":" + entry.getValue());
    }
}
