package top.itlq.thinkingInJava.exceptions_12;

public class SimpleException extends Exception {
    public SimpleException(String exception){
        super(exception);
    }
    public static void f1() throws SimpleException{
        throw new SimpleException("test");
    }

    public static void main(String [] args){
        try {
            f1();
        }catch (SimpleException e){
            e.printStackTrace(System.out);
        }
    }
}
class MyException extends SimpleException{
    public MyException(){
        super("myException");
    }
}

