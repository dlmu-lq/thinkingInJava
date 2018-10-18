package top.itlq.thinkingInJava.typeinfo_14.classObject.pets;

import top.itlq.thinkingInJava.typeinfo_14.factory.Factory;

public class Pet extends Individual{
    public Pet(String name){super(name);}
    public static class PetFactory implements Factory<Pet> {
        @Override
        public Pet create() {
            return new Pet("factoryPet");
        }
    }
}
