/**
 * 指定基础类型的类别计数器，包括对基类，子类
 * 使用递归
 * isAssignedFrom
 * todo 有bug
 */
package top.itlq.thinkingInJava.typeinfo_14.classObject;

import org.junit.Test;
import top.itlq.thinkingInJava.typeinfo_14.classObject.pets.Pet;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TypeCount extends HashMap<Class,Integer> {
    private Class baseType;
    public TypeCount(Class baseType){
        this.baseType = baseType;
    }

    public void count(Object obj){
        Class objClass = obj.getClass();
        countClass(objClass);
    }

    private void countClass(Class cl){
        if(cl != null && baseType.isAssignableFrom(cl)){
            put(cl,containsKey(cl) ? get(cl) + 1 : 1);
            countClass(cl.getSuperclass());
        }
    }

    public String toString(){
        StringBuilder sb = new StringBuilder("{");
        for(Map.Entry<Class,Integer> entry:entrySet()){
            sb.append(entry.getKey().getSimpleName());
            sb.append("=");
            sb.append(entry.getValue());
            sb.append(", ");
        }
        sb.delete(sb.length() - 2,sb.length());
        sb.append("}");
        return sb.toString();
    }

    public static void main(String...args){
        List<Pet> pets = new LiteralPetCreator().createList(20);
        TypeCount typeCount = new TypeCount(Pet.class);
        TypeCount typeCount1 = new TypeCount(Object.class);
        for(Pet pet:pets){
            typeCount.count(pet);
            typeCount1.count(pet);
        }
        System.out.println(typeCount);
        System.out.println(typeCount1);
    }
}
