package top.itlq.thinkingInJava.typeinfo_14.classObject.pets;

import top.itlq.thinkingInJava.typeinfo_14.factory.Factory;

public class Dog extends Pet{
    public Dog(String name){super(name);}
    public static class DogFactory implements Factory<Dog>{
        @Override
        public Dog create() {
            return new Dog("factory Dog");
        }
    }
}
