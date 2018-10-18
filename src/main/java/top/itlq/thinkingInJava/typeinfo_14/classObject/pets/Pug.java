package top.itlq.thinkingInJava.typeinfo_14.classObject.pets;

import top.itlq.thinkingInJava.typeinfo_14.factory.Factory;

public class Pug extends Dog{
    public Pug(String name){super(name);}
    public static class PugFactory implements Factory<Pug> {
        @Override
        public Pug create() {
            return new Pug("factoryPug");
        }
    }
}
