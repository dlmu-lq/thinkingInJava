package top.itlq.thinkingInJava.exceptions_12;

import java.io.FileWriter;
import java.io.PrintWriter;
import java.io.StringWriter;

public class TestFillInStackTrace {
    static void f() throws Exception {
        g();
    }
    static void g() throws Exception {
        throw new Exception("test");
    }
    public static void main(String...args){
        try {
            try {
                f();
            } catch (Exception e) {
                System.out.println("caught first");
                e.printStackTrace(System.out);
                throw (Exception) e.fillInStackTrace();
            }
        }catch (Exception e){
            System.out.println("caught second with out first info:");
//            e.printStackTrace(System.out);
            try {
                PrintWriter printWriter = new PrintWriter(new FileWriter(
                        TestFillInStackTrace.class.getResource("/").getPath()
                                + "exception/printStackTraceFile",true));
                e.printStackTrace(printWriter);
                printWriter.flush();
                printWriter.close();
            }catch (Exception e2){
                e2.printStackTrace();
            }
        }
        try {
            try {
                f();
            } catch (Exception e) {
                System.out.println("caught first");
                e.printStackTrace(System.out);
                throw e;
            }
        }catch (Exception e){
            System.out.println("caught second with first info:");
            e.printStackTrace(System.out);
        }
    }
}
