package top.itlq.thinkingInJava.typeinfo_14.pets;

import top.itlq.thinkingInJava.typeinfo_14.factory.Factory;

public class Dog extends Pet{
    public Dog(String name){super(name);}
    public static class Factory implements top.itlq.thinkingInJava.typeinfo_14.factory.Factory<Dog>{
        @Override
        public Dog create() {
            return new Dog("factory Dog");
        }
    }
}
