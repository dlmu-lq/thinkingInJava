package top.itlq.thinkingInJava.exceptions_12;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.logging.Logger;

public class ExceptionDemo {

    private static final Logger logger = Logger.getLogger("demo");
    static void logException(Exception e){
        StringWriter sw = new StringWriter();
        e.printStackTrace(new PrintWriter(sw));
        logger.severe(sw.toString());
    }

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
                logException(e);
                start -- ;
            }
        }
    }
}
