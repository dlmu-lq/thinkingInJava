package top.itlq.thinkingInJava.typeinfo_14.pets;

import top.itlq.thinkingInJava.typeinfo_14.factory.Factory;

public class Manx extends Pet {
    public Manx(String name){super(name);}

    public static class Factory implements top.itlq.thinkingInJava.typeinfo_14.factory.Factory<Manx> {
        @Override
        public Manx create() {
            return new Manx("factoryManx");
        }
    }
}
