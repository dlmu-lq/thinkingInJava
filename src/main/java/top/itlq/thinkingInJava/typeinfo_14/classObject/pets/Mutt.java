package top.itlq.thinkingInJava.typeinfo_14.classObject.pets;

import top.itlq.thinkingInJava.typeinfo_14.factory.Factory;

public class Mutt extends Dog{
    public Mutt(String name){super(name);}
    public static class MuttFactory implements Factory<Mutt> {
        @Override
        public Mutt create() {
            return new Mutt("factoryMutt");
        }
    }
}
