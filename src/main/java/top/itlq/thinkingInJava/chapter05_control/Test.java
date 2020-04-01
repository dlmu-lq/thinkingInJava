package top.itlq.thinkingInJava.chapter05_control;

public class Test {
    public static void main(String[] args) {
//        int goto  保留关键字，但没有用；
        outer:
        for(int i : new int[]{1,2,3}){
            for(int j: new int[]{4,5,6}){
                if(i == 1){
                    continue;
                }
                if(i == 2 && j == 5){
                    continue outer;
                }
                if(i == 3){
                    break;
                }
                System.out.println("" + i + " " + j);
            }
        }
    }
}
