package top.itlq.thinkingInJava.arrays;

import java.util.Arrays;

public class BoxingArrays {
    public static void main(String...args){
        // 一维数组存储的引用
        int[][] arr = new int[2][];
        System.out.println(Arrays.deepToString(arr));
        // 二维数组存的引用，引用指向堆里的对象，对象是实际数组，存储基本类型的值；
        arr[0] = new int[]{1,2};
        arr[1] = new int[]{2,1,0};
        System.out.println(Arrays.deepToString(arr));
    }
}
