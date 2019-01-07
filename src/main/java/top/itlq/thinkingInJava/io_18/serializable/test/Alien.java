package top.itlq.thinkingInJava.io_18.serializable.test;

import java.io.Serializable;

public class Alien implements Serializable {
    public String s1;
    public Alien(){
        s1 = String.valueOf(Math.random());
    }
}
