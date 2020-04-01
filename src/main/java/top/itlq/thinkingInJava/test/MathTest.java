package top.itlq.thinkingInJava.test;

import java.math.BigInteger;

public class MathTest {
    public static void main(String...args){
        System.out.println(Math.round(-1.5)); // -1,mysql中结果为-2;
        System.out.println(-7 % 4);
        System.out.println(new BigInteger("-7").remainder(new BigInteger("4")));
        System.out.println(new BigInteger("-7").mod(new BigInteger("4")));
        System.out.println(new BigInteger("-5").mod(new BigInteger("3")));
        System.out.println(Math.floorMod(-7, 4));
    }
}
