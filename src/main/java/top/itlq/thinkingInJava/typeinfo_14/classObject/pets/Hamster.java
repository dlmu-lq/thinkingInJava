/**
 * 仓鼠
 */
package top.itlq.thinkingInJava.typeinfo_14.classObject.pets;

import top.itlq.thinkingInJava.typeinfo_14.factory.Factory;

public class Hamster extends Rodent {
    public Hamster(String name){super(name);}
    public static class HamsterFactory implements Factory<Hamster> {
        @Override
        public Hamster create() {
            return new Hamster("factoryHamster");
        }
    }
}
