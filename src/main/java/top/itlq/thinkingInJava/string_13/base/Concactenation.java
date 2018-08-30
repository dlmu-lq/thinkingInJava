package top.itlq.thinkingInJava.string_13.base;

public class Concactenation {
    public static void main(String...args){
        String s1 = "t1";
        String s2 = s1 + 47;
        System.out.print(s2);
        for(int i=0;i<100;i++){
            s1 += s1;
        }
    }
}
