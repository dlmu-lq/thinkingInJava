package top.itlq.thinkingInJava.exceptions_12;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.logging.Logger;

public class LogException extends Exception{
    private static final Logger logger = Logger.getLogger("test");
    public LogException(){
        StringWriter sw = new StringWriter();
        printStackTrace(new PrintWriter(sw));
        logger.warning(sw.toString());
    }
    public static void f() throws LogException{
        throw new LogException();
    }
    public static void main(String...args){
        try {
            f();
        } catch (LogException e) {
            System.out.println(1); // 先与Exception的构造方法
            System.out.println("Caught Exception" + e);
        }
    }
}
