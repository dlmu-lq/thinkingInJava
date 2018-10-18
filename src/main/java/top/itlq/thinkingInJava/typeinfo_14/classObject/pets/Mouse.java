/**
 *
 */
package top.itlq.thinkingInJava.typeinfo_14.classObject.pets;

import top.itlq.thinkingInJava.typeinfo_14.factory.Factory;

public class Mouse extends Rodent {
    public Mouse(String name){super(name);}
    public static class MouseFactory implements Factory<Mouse> {
        @Override
        public Mouse create() {
            return new Mouse("factoryMouse");
        }
    }
}
