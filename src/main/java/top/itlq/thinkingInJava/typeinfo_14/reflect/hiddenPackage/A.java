package top.itlq.thinkingInJava.typeinfo_14.reflect.hiddenPackage;

public class A {
    private int i = 1;
    private Integer j = 1;
    private String s = "1s";
    private final String finalS = "1s";

    private void test(){
        System.out.println("running A's private test()");
    }

    @Override
    public String toString() {
        return "i:" + i + "," + "j:" + j + "," + "s:" + s +"," + "finalS" + finalS;
    }
}