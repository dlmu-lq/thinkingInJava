package top.itlq.thinkingInJava.arrays;

import org.junit.Test;
import top.itlq.thinkingInJava.typeinfo_14.reflect.hiddenPackage.A;

import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;

/**
 * Arrays 实用功能
 */
public class ArraysFunctions {
    /**
     * 利用Comparable,Comparator,Arrays.sort()对数组排序
     */
    @Test
    public void test(){
        Person[] people = {new Person(3),new Person(5),new Person(2),new Person(23)};
        System.out.println("Before sort:" + Arrays.toString(people));
        Arrays.sort(people);
        System.out.println("After sort:" + Arrays.toString(people));
        Arrays.sort(people, new Comparator<Person>() {
            @Override
            public int compare(Person o1, Person o2) {
                return o2.age - o1.age;
            }
        });
        System.out.println("After desc comparator sort:" + Arrays.toString(people));
        Arrays.sort(people, Collections.reverseOrder());
        System.out.println("After reverseOrder() sort:" + Arrays.toString(people));
    }

    static class Person implements Comparable<Person>{
        private int age;

        Person(int age){
            this.age = age;
        }

        @Override
        public int compareTo(Person o) {
            return this.age - o.age;
        }

        public String toString(){
            return "age " + age + " person";
        }
    }

    /**
     *
     */
    @Test
    public void test1(){
        int [] arr = {3,4,2,1,8};
        Arrays.sort(arr);
        System.out.println(Arrays.toString(arr));
        System.out.println(Arrays.binarySearch(arr,8));
        System.out.println(Arrays.binarySearch(arr,9)); // - re - 1 插入点
        System.out.println(Arrays.binarySearch(arr,7));
        System.out.println(Arrays.binarySearch(arr,0));
    }
}
