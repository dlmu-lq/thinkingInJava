package top.itlq.thinkingInJava.typeinfo_14.classObject.pets;

import top.itlq.thinkingInJava.typeinfo_14.factory.Factory;

public class Rat extends Rodent {
    public Rat(String name){super(name);}
    public static class RatFactory implements Factory<Rat> {
        @Override
        public Rat create() {
            return new Rat("factoryRat");
        }
    }
}
