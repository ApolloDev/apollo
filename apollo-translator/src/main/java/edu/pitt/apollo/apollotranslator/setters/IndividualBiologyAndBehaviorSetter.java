package edu.pitt.apollo.apollotranslator.setters;

import edu.pitt.apollo.apollotranslator.ApolloTranslationEngine;
import edu.pitt.apollo.apollotranslator.exception.ApolloSetterException;
import edu.pitt.apollo.apollotranslator.types.translator.SetterReturnObject;
import edu.pitt.apollo.types.v4_0_2.Behavior;
import edu.pitt.apollo.types.v4_0_2.IndividualBiologyAndBehavior;
import edu.pitt.apollo.types.v4_0_2.LifeCycle;
import edu.pitt.apollo.types.v4_0_2.Reproduction;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by nem41 on 8/10/15.
 */
public class IndividualBiologyAndBehaviorSetter extends AbstractTypedSetter<IndividualBiologyAndBehavior> {

    private static final String LIFE_CYCLE = "lifeCycle";
    private static final String REPRODUCTION = "reproduction";
    private static final String BEHAVIOR = "behavior";

    public IndividualBiologyAndBehaviorSetter() {
    }

    public IndividualBiologyAndBehaviorSetter(ApolloTranslationEngine apolloTranslationEngine, String type, String section) {
        super(type, section, apolloTranslationEngine);
    }

    private List<SetterReturnObject> setLifeCycle(LifeCycle lifeCycle) throws ApolloSetterException {
        LifeCycleSetter setter = new LifeCycleSetter(apolloTranslationEngine, type + "." + LIFE_CYCLE, section);
        return setter.set(lifeCycle);
    }

    private List<SetterReturnObject> setReproduction(Reproduction reproduction) throws ApolloSetterException {
        ReproductionSetter setter = new ReproductionSetter(apolloTranslationEngine, type + "." + REPRODUCTION, section);
        return setter.set(reproduction);
    }

    private List<SetterReturnObject> setBehavior(Behavior behavior) throws ApolloSetterException {
        BehaviorSetter setter = new BehaviorSetter(apolloTranslationEngine, type + "." + BEHAVIOR, section);
        return setter.set(behavior);
    }

    @Override
    public List<SetterReturnObject> set(IndividualBiologyAndBehavior individualBiologyAndBehavior) throws ApolloSetterException {
        List<SetterReturnObject> list = new ArrayList<>();
        if (individualBiologyAndBehavior.getLifeCycle() != null) {
            list.addAll(setLifeCycle(individualBiologyAndBehavior.getLifeCycle()));
        }
        if (individualBiologyAndBehavior.getReproduction() != null) {
            list.addAll(setReproduction(individualBiologyAndBehavior.getReproduction()));
        }
        if (individualBiologyAndBehavior.getBehavior() != null) {
            list.addAll(setBehavior(individualBiologyAndBehavior.getBehavior()));
        }

        return list;
    }
}
