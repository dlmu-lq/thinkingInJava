/**
 * 仓鼠
 */
package top.itlq.thinkingInJava.typeinfo_14.pets;

import top.itlq.thinkingInJava.typeinfo_14.factory.Factory;

public class Hamster extends Rodent {
    public Hamster(String name){super(name);}
    public static class Factory implements top.itlq.thinkingInJava.typeinfo_14.factory.Factory<Hamster> {
        @Override
        public Hamster create() {
            return new Hamster("factoryHamster");
        }
    }
}
