package top.itlq.thinkingInJava.typeinfo_14.pets;

import top.itlq.thinkingInJava.typeinfo_14.factory.Factory;

public class Pet extends Individual{
    public Pet(String name){super(name);}
    public static class Factory implements top.itlq.thinkingInJava.typeinfo_14.factory.Factory<Pet> {
        @Override
        public Pet create() {
            return new Pet("factoryPet");
        }
    }
}
