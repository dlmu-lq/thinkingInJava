package top.itlq.thinkingInJava.typeinfo_14.pets;

import top.itlq.thinkingInJava.typeinfo_14.factory.Factory;

public class Cat extends Pet {
    public Cat(String name){super(name);}
    public static class Factory implements top.itlq.thinkingInJava.typeinfo_14.factory.Factory<Cat>{
        @Override
        public Cat create() {
            return new Cat("factoryCat");
        }
    }
}
