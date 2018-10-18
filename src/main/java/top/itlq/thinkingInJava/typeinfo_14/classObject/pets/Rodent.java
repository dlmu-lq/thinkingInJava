/**
 * 啮齿动物
 */
package top.itlq.thinkingInJava.typeinfo_14.classObject.pets;

import top.itlq.thinkingInJava.typeinfo_14.factory.Factory;

public class Rodent extends Pet {
    public Rodent(String name){super(name);}
    public static class RodentFactory implements Factory<Rodent> {
        @Override
        public Rodent create() {
            return new Rodent("factoryRodent");
        }
    }
}
