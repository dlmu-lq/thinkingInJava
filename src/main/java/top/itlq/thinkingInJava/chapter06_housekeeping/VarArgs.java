package top.itlq.thinkingInJava.chapter06_housekeeping;

public class VarArgs {
    public static void main(String[] args) {
        // 当做一个对象
        f((Object) new Integer[]{1,2,3});
        // 当做一个数组
        f(new Integer[]{1,2,3});
        // 当做一个对象
        f(new int[]{1,2,3});
        // 当做一个数组
        f((Object[]) new Integer[]{1,2,3});
    }

    static void f(Object...args){
        for(Object object:args){
            System.out.println(object);
        }
    }
}
