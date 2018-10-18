package top.itlq.thinkingInJava.typeinfo_14.classObject.pets;

import top.itlq.thinkingInJava.typeinfo_14.factory.Factory;

public class Cat extends Pet {
    public Cat(String name){super(name);}
    public static class CatFactory implements Factory<Cat>{
        @Override
        public Cat create() {
            return new Cat("factoryCat");
        }
    }
}
