package top.itlq.thinkingInJava.holdingObjects_11.stack;

import org.junit.Test;

import java.util.LinkedList;

public class TestStack<T> {
    private LinkedList<T> storage = new LinkedList<T>();
    public void push(T t){storage.addFirst(t);}
    public T peek(){return storage.getFirst();}
    public T pop(){return storage.poll();}
    public boolean empty(){return storage.isEmpty();}

    @Override
    public String toString() {
        return storage.toString();
    }

    @Test
    public void test(){
        TestStack<Character> myStack = new TestStack<Character>();
        String aim = "+U+n+c---+e+r+t---+a-+i-+n+t+y---+-+r+u--+l+e+s---";
        for(int i=0;i<aim.length();i++){
            if(aim.charAt(i) == '+'){
                myStack.push(aim.charAt(i + 1));
                i++;
            }else if(aim.charAt(i) == '-'){
                System.out.print(myStack.pop());
            }
        }
    }
}
