/**
 * 一个随机创建Pet的抽象类，配置类型方式可以不同方式具体实现
 */
package top.itlq.thinkingInJava.typeinfo_14.classObject;

import top.itlq.thinkingInJava.typeinfo_14.classObject.pets.Pet;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public abstract class PetCreator {
    private Random random = new Random(35);

    public abstract List<Class<? extends Pet>> types();

    public Pet randomPet(){
        int n = random.nextInt(types().size());
        try {
            return types().get(n).getDeclaredConstructor(String.class).newInstance("");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public Pet[] createArray(int size){
        Pet [] re = new Pet[size];
        for(int i=0;i<re.length;i++){
            re[i] = randomPet();
        }
        return re;
    }

    public List<Pet> createList(int size){
        List<Pet> re = new ArrayList<>();
        Collections.addAll(re,createArray(size));
        return re;
    }
}
