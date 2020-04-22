package top.itlq.thinkingInJava.chapter03_objects;

public class Test {
    public static void main(String...args){
        Character a = new Character('a');
        char b = 'a';
        // 基本类型 == 会自动拆箱
        System.out.println(a == b);
        // 引用指向的对象不同
        System.out.println(a == new Character(b));
        // Integer对象内部的缓存，会使一定范围内的字面量对象相同，对象应该使用 equals对比
        Character c = 'a';
        Character d = 'a';
        System.out.println(c == d);
        System.out.println(c == new Character(d));
        Character i = '我';
        Character j = '我';
        System.out.println(i == j);
        // new之后，复制到堆

        // Integer对象内部的缓存
        Integer e = 127;
        Integer f = 127;
        System.out.println(e == f); // true
        Integer g = 128;
        Integer h = 128;
        System.out.println(g == h); // false
    }
}
