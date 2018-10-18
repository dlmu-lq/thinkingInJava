package top.itlq.thinkingInJava.typeinfo_14.classObject.pet;

import org.junit.Test;
import top.itlq.thinkingInJava.typeinfo_14.pets.Pet;

import java.util.HashMap;
import java.util.Map;

public class PetCount extends HashMap<Class<? extends Pet>,Integer> {

    public PetCount(){
        for(Class<? extends Pet> cl:LiteralPetCreator.allTypes){
            put(cl,0);
        }
    }

    /**
     * 利用Map内部entrySet属性
     * @param pet
     */
    public void count(Pet pet){
        for(Map.Entry<Class<? extends Pet>,Integer> entry: entrySet()){
            if(entry.getKey().isInstance(pet))
                put(entry.getKey(),entry.getValue()+ 1);
        }
    }

    public String toString(){
        StringBuilder sb = new StringBuilder("{");
        for(Map.Entry<Class<? extends Pet>,Integer> entry:entrySet()){
            sb.append(entry.getKey().getSimpleName());
            sb.append("=");
            sb.append(entry.getValue());
            sb.append(", ");
        }
        sb.delete(sb.length() - 2,sb.length());
        sb.append("}");
        return sb.toString();
    }

    @Test
    public void test(){
        LiteralPetCreator creator = new LiteralPetCreator();
        PetCount count = new PetCount();
        for(Pet pet:creator.createList(100))
            count.count(pet);
        System.out.println(count);
    }
}
