package top.itlq.thinkingInJava.typeinfo_14.classObject.pet;

import top.itlq.thinkingInJava.typeinfo_14.pets.Pet;

import java.util.ArrayList;
import java.util.List;

public class ForNameCreator extends PetCreator{
    private static List<Class<? extends Pet>> types = new ArrayList<Class<? extends Pet>>();

    private static String[] typeNames = {
            "top.itlq.thinkingInJava.typeinfo_14.pets.Cat",
            "top.itlq.thinkingInJava.typeinfo_14.pets.Dog",
            "top.itlq.thinkingInJava.typeinfo_14.pets.Hamster",
            "top.itlq.thinkingInJava.typeinfo_14.pets.Manx",
            "top.itlq.thinkingInJava.typeinfo_14.pets.Mouse",
            "top.itlq.thinkingInJava.typeinfo_14.pets.Mutt",
            "top.itlq.thinkingInJava.typeinfo_14.pets.Pug",
            "top.itlq.thinkingInJava.typeinfo_14.pets.Rat",
            "top.itlq.thinkingInJava.typeinfo_14.pets.Rodent",
    };

    private static void load(){
        for(String typeName:typeNames){
            try {
                types.add((Class<? extends Pet>) Class.forName(typeName));
            } catch (ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
        }
    }

    static {load();}

    @Override
    public List<Class<? extends Pet>> types() {
        return types;
    }

    public static void main(String...args){
        ForNameCreator creator = new ForNameCreator();
        System.out.println(creator.createList(10));
    }
}
