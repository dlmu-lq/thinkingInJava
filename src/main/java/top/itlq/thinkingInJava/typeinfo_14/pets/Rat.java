package top.itlq.thinkingInJava.typeinfo_14.pets;

import top.itlq.thinkingInJava.typeinfo_14.factory.Factory;

public class Rat extends Rodent {
    public Rat(String name){super(name);}
    public static class Factory implements top.itlq.thinkingInJava.typeinfo_14.factory.Factory<Rat> {
        @Override
        public Rat create() {
            return new Rat("factoryRat");
        }
    }
}
