package top.itlq.thinkingInJava.typeinfo_14.classObject.pets;

import top.itlq.thinkingInJava.typeinfo_14.factory.Factory;

public class Manx extends Pet {
    public Manx(String name){super(name);}

    public static class ManxFactory implements Factory<Manx> {
        @Override
        public Manx create() {
            return new Manx("factoryManx");
        }
    }
}
