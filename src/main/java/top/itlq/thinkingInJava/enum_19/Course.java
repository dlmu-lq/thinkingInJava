package top.itlq.thinkingInJava.enum_19;

import java.util.Random;

public enum Course {
    APPETIZER(Food.Appetizer.class),
    MAINCOURSE(Food.MainCourse.class),
    DESSERT(Food.Dessert.class),
    COFFEE(Food.Coffee.class);
    private Food[] values;
    private Random random = new Random();
    private Course(Class<? extends Food> foodClass){
        values = foodClass.getEnumConstants();
    }
    public Food randomSelection(){
        return values[random.nextInt(values.length)];
    }
}
