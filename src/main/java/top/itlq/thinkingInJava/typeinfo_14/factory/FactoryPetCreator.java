package top.itlq.thinkingInJava.typeinfo_14.factory;

import org.junit.Test;
import top.itlq.thinkingInJava.typeinfo_14.classObject.pets.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class FactoryPetCreator {
    private static final List<Factory<? extends Pet>> petFactoryList = new ArrayList<>();
    private static final Random random = new Random(32);

    static {
        // generic array creation 不能创建泛型数组
        Collections.addAll(petFactoryList, new Cat.CatFactory(),
                new Dog.DogFactory(),
                new Hamster.HamsterFactory(),
                new Manx.ManxFactory(),
                new Mouse.MouseFactory(),
                new Mutt.MuttFactory(),
                new Pug.PugFactory(),
                new Rat.RatFactory(),
                new Rodent.RodentFactory());
    }

    public Pet createRandom(){
        int i = random.nextInt(petFactoryList.size());
        return petFactoryList.get(i).create();
    }

    public Pet[] createArray(int size){
        Pet[] re = new Pet[size];
        for(int i=0;i<size;i++){
            re[i] = createRandom();
        }
        return re;
    }

    public List<Pet> createList(int size){
        List<Pet> re = new ArrayList<>();
        Collections.addAll(re,createArray(size));
        return re;
    }

    @Test
    public void test(){
        FactoryPetCreator creator = new FactoryPetCreator();
        System.out.print(creator.createList(5));
    }
}
