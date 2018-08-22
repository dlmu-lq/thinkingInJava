package top.itlq.thinkingInJava.exceptions_12;

public class ExceptionDemo {

    void f(){}

    public static void main(String...args){
        // NullPointerException
        ExceptionDemo demo = null;
        try {
            demo.f();
        }catch (Exception e){
            e.printStackTrace(System.out);
        }
        // 恢复模型
        demo = new ExceptionDemo();
        int flg = 0;
        int start = 7;
        int [] arr = new int[5];
        while (flg == 0){
            try {
                System.out.println(arr[start]);
                flg = 1;
            }catch (ArrayIndexOutOfBoundsException e){
                e.printStackTrace(System.out);
                start -- ;
            }
        }
    }
}
