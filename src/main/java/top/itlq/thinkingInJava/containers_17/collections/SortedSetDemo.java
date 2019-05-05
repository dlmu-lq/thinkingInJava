package top.itlq.thinkingInJava.containers_17.collections;

import java.util.Collections;
import java.util.Iterator;
import java.util.SortedSet;
import java.util.TreeSet;

public class SortedSetDemo {
    public static void main(String...args){
        SortedSet sortedSet = new TreeSet<String>();
        Collections.addAll(sortedSet,"one two three four five six seven eight".split(" "));
        Iterator iterator = sortedSet.iterator();
        for(int i=0;i<=6;i++){
            if(i == 3){
                System.out.println(iterator.next());
            }else if(i == 6){
                System.out.println(iterator.next());
            }else{
                iterator.next();
            }
        }
    }
}
