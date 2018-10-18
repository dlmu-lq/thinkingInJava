/**
 *
 */
package top.itlq.thinkingInJava.typeinfo_14.pets;

import top.itlq.thinkingInJava.typeinfo_14.factory.Factory;

public class Mouse extends Rodent {
    public Mouse(String name){super(name);}
    public static class Factory implements top.itlq.thinkingInJava.typeinfo_14.factory.Factory<Mouse> {
        @Override
        public Mouse create() {
            return new Mouse("factoryMouse");
        }
    }
}
