package top.itlq.thinkingInJava.typeinfo_14.pets;

import top.itlq.thinkingInJava.typeinfo_14.factory.Factory;

public class Individual {
    public String name;

    public Individual(String name){
        this.name = name;
    }
    public static class Factory implements top.itlq.thinkingInJava.typeinfo_14.factory.Factory<Individual> {
        @Override
        public Individual create() {
            return new Individual("factoryIndividual");
        }
    }
}
