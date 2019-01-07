package top.itlq.thinkingInJava.io_18;

import java.io.File;

public class Folders {
    public static void main(String...args){
        StringBuilder sb = new StringBuilder("");
        for(int i=0;i<Integer.valueOf(args[0]);i++){
            sb.append("a\\");
        }
        File dir = new File(sb.toString());
        if(!dir.exists())
            dir.mkdirs();
        System.out.print(sb.toString());
    }
}
