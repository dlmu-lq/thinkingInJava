package top.itlq.thinkingInJava.string_13.methods;

import org.junit.Test;

import java.util.Arrays;

public class TestStringMethod {
    @Test
    public void test1(){
        String s1 = "string";
        System.out.println("s1:" + s1);
        // length()
        System.out.println("s1.length(): " + s1.length());
        // charAt()
        System.out.println("s1.charAt(s1.length() - 2): " + s1.charAt(s1.length() - 2));
        // getCharts()
        char [] reArr = new char[4];
        s1.getChars(1,s1.length() - 2,reArr,1);
        System.out.println("s1.getChars(1,s1.length() - 2,reArr,1): reArr :" + Arrays.toString(reArr));
        // getBytes()
        byte [] bytes = s1.getBytes();
        System.out.println("s1.getBytes():" + Arrays.toString(bytes));
        System.out.println("new String(s1.getBytes()):" + new String(s1.getBytes()));
        System.out.println("中.getBytes():" + Arrays.toString("中".getBytes()));
        // toCharArray()
        System.out.println("s1.toCharArray()" + Arrays.toString(s1.toCharArray()));
        // equals equalsIgnoreCase
        System.out.println("\"strIng\".equals(s1):" + "strIng".equals(s1));
        System.out.println("\"strIng\".equalsIgnoreCase(s1):" + "strIng".equalsIgnoreCase(s1));
        // compareTo
        System.out.println("\"string\".compareTo(s1):"+"string".compareTo(s1));
        System.out.println("\"astring\".compareTo(s1):"+"astring".compareTo(s1));
        System.out.println("\"zstring\".compareTo(s1):"+"zstring".compareTo(s1));
        // contains(CharSquence ) CharSquence 主要包括String StringBuilder StringBuffer
        System.out.println("s1.contains(\"a\"):"+s1.contains("a"));
        System.out.println("s1.contains(\"st\"):"+s1.contains("st"));
        System.out.println("s1.contains(\"sr\"):"+s1.contains("sr"));
        // contentEquals (String CharSquence)
        System.out.println("s1.contentEquals(new StringBuilder(\"string\")):"+s1.contentEquals(new StringBuilder("string")));
        System.out.println("s1.contentEquals(\"string\"):"+s1.contentEquals("string"));
        // regionMatcher()
        System.out.println("s1.regionMatches(1,\"trina\",0,4):"+s1.regionMatches(1,"trina",0,4));
        // startsWith() endsWith()
        System.out.println("s1.startsWith(\"st\"):"+s1.startsWith("st"));
        System.out.println("s1.endsWith(\"ng\"):"+s1.endsWith("ng"));
        // indexOf() lastIndexOf()
        System.out.println("s1.indexOf(\"tr\"):"+s1.indexOf("tr"));
        System.out.println("s1.indexOf('a'):"+s1.indexOf('a'));
        System.out.println("\"aabbvcca\".lastIndexOf(\"a\"):"+("aabbvcca".lastIndexOf("a")));
        System.out.println("\"aabbvcca\".lastIndexOf('a',3):"+("aabbvcca".lastIndexOf('a',3))); // todo what?
        //substring() subSequence()
        System.out.println("s1.substring(0,2)"+s1.substring(0,2));
        System.out.println("new StringBuilder(s1).subSequence(0,2)"+new StringBuilder(s1).subSequence(0,2));
        // concat() 返回新String对象，如果没变化返回原对象
        System.out.println("s1.concat(\"\") == s1:"+(s1.concat("") == s1));
        System.out.println("s1.concat(\"1\"):"+(s1.concat("1")));
        // replace() 如果没变化，返回原对象
        System.out.println("s1.replace(new StringBuffer(\"g\"),\"a\"):"+s1.replace(new StringBuffer("g"),"a"));
        // toLowerCase() toUpperCase()
        System.out.println("s1.toLowerCase():"+s1.toLowerCase());
        System.out.println("s1.toLowerCase() == s1:" + (s1.toLowerCase() == s1));
        System.out.println("s.toUpperCase():"+s1.toUpperCase());
        // trim()
        System.out.println("\"     a sb   \".trim():"+("     a sb   ".trim()));
        // valueOf()
        System.out.println("String.valueOf(123)"+String.valueOf(123));
        // intern
        String a = s1.intern();
        System.out.println("s1.intern():" + a);
    }
}
