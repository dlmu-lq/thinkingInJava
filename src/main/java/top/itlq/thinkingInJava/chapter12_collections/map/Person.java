package top.itlq.thinkingInJava.chapter12_collections.map;

public class Person {
    String name;
    public Person(String name){
        this.name = name;
    }
    // 使用自建类做主键要覆盖 需重写 equals  hashCode
    @Override
    public boolean equals(Object person){
        return ((Person)person).name.equals(name);
    }
    @Override
    public int hashCode(){
        return 0;
    }

    public void speek(){
        System.out.println("My name is " + name);
    }
}
