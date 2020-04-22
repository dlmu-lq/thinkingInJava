package top.itlq.thinkingInJava.generics;

import java.util.Comparator;

public class TestSuperT {
    public static <T> T findMax(T[] arr, Comparator<? super T> comparator){
        int maxIndex = 0;
        for(int i=1;i<arr.length;i++){
            if(comparator.compare(arr[i], arr[maxIndex]) > 0){
                maxIndex = i;
            }
        }
        return arr[maxIndex];
    }

    public static void main(String[]args){
        System.out.println((int)'a');
        System.out.println((int)'Z');
        System.out.println(findMax(new String[]{"a", "Z"}, String::compareToIgnoreCase));
        System.out.println(findMax(new String[]{"a", "Z"},String::compareTo));
    }
}
