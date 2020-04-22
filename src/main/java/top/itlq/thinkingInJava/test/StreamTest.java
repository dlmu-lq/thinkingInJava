package top.itlq.thinkingInJava.test;

import org.junit.Test;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

public class StreamTest {
    @Test
    public void test(){
        Map<String,Integer> map = new HashMap<>(){{
           put("1",1);
           put("2",2);
        }};
        System.out.println(
                map.entrySet().stream()
                        .collect(Collectors.toMap(e->e.getKey(),e->e.getValue() + 1)));
    }
}
