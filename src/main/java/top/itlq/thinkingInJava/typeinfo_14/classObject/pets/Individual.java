package top.itlq.thinkingInJava.typeinfo_14.classObject.pets;

import top.itlq.thinkingInJava.typeinfo_14.factory.Factory;

public class Individual {
    public String name;

    public Individual(String name){
        this.name = name;
    }
    public static class IndividualFactory implements Factory<Individual> {
        @Override
        public Individual create() {
            return new Individual("factoryIndividual");
        }
    }
}
