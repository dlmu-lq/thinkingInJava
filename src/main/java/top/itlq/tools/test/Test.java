package top.itlq.tools.test;

import java.text.DecimalFormat;

public class Test {
    public static void main(String...args){
        Double[] ds = new Double[]{1.2,1d,1.232,10.2,1.00};
        for(Double d:ds){
            formatFixed2(d);
            decimalFixed2(d);
        }
    }

    static void formatFixed2(Object n){
        System.out.println(String.format("%.2f",n));
    }

    static void decimalFixed2(Object n){
        DecimalFormat decimalFormat = new DecimalFormat("#.00");
        System.out.println(decimalFormat.format(n));
    }
}
