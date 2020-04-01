package top.itlq.thinkingInJava.chapter12_collections.queue;

import org.junit.Test;

import java.util.LinkedList;
import java.util.Queue;

public class TestQueue {
    /**
     * 一些基本方法
     * offer
     */
    @Test
    public void test1(){
        Queue<Character> cs = new LinkedList<Character>();
        try {
            cs.element();
        }catch (Exception e){
            System.out.println("empty queue element():Exception:" + e.toString());
        }
        System.out.println("empty queue peek():"+cs.peek());
        try {
            cs.remove();
        }catch (Exception e){
            System.out.println("empty queue remove():Exception:" + e.toString());
        }
        System.out.println("empty queue poll():"+cs.poll());
        for(char c: "ab,c".toCharArray()){
            cs.offer(c);
        }
        System.out.println(cs);
        System.out.println(cs.remove('d'));
        System.out.println(cs.remove(','));
        System.out.println(cs.peek());
        System.out.println(cs.poll()); //先进先出
    }
    /**
     * 练习
     */
    public static Command addCommand(Command command,Queue queue){
        queue.offer(command);
        return command;
    }

    public static void excuteCommands(Queue<Command> commands){
        while (!commands.isEmpty())
            commands.poll().operation();
    }

    @Test
    public void test2(){
        Queue<Command> commands = new LinkedList<Command>();
        addCommand(new Command("l"),commands);
        addCommand(new Command("c"),commands);
        addCommand(new Command("q"),commands);
        excuteCommands(commands);
    }
}
