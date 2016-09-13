package edu.pitt.apollo.apollotranslator.setters;

import edu.pitt.apollo.apollotranslator.ApolloTranslationEngine;
import edu.pitt.apollo.apollotranslator.exception.ApolloSetterException;
import edu.pitt.apollo.apollotranslator.types.translator.SetterReturnObject;
import edu.pitt.apollo.types.v4_0.*;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by nem41 on 8/10/15.
 */
public class PopulationSetter extends AbstractTypedSetter<Population> {

    private static final String LOCATION = "location";
    private static final String TAXON_ID = "taxonId";
    private static final String COUNT = "count";
    private static final String INFECTION_AND_IMMUNITY_CENSUSES = "infectionAndImmunityCensuses";
    private static final String INDIVIDUAL_BIOLOGY_AND_BEHAVIOR = "individualBiologyAndBehavior";

    public PopulationSetter() {
    }

    public PopulationSetter(ApolloTranslationEngine apolloTranslationEngine, String type, String section) {
        super(type, section, apolloTranslationEngine);
    }

    private List<SetterReturnObject> setLocation(String location) throws ApolloSetterException {
        return setValue(LOCATION, location, section);
    }

    private List<SetterReturnObject> setTaxonId(String id) throws ApolloSetterException {
        return setValue(TAXON_ID, id, section);
    }

    private List<SetterReturnObject> setCount(BigInteger count) throws ApolloSetterException {
        return setValue(COUNT, count.toString(), section);
    }

    private List<SetterReturnObject> setInfectionAndImmunityCensuses(List<PopulationInfectionAndImmunityCensus> censuses) throws ApolloSetterException {
        List<SetterReturnObject> list = setValue(INFECTION_AND_IMMUNITY_CENSUSES, "(list values described below)", section);
        ListSetter setter = new ListSetter(CensusSetter.class,
                Census.class, censuses, apolloTranslationEngine,
                section, type + "." + INFECTION_AND_IMMUNITY_CENSUSES);

        list.get(0).setSubApolloParameters(setter.set());
        return list;
    }

    private List<SetterReturnObject> setIndividualBiologyAndBehavior(IndividualBiologyAndBehavior individualBiologyAndBehavior) throws ApolloSetterException {
        IndividualBiologyAndBehaviorSetter setter = new IndividualBiologyAndBehaviorSetter(apolloTranslationEngine, type + "." + INDIVIDUAL_BIOLOGY_AND_BEHAVIOR, section);
        return setter.set(individualBiologyAndBehavior);
    }

    @Override
    public List<SetterReturnObject> set(Population population) throws ApolloSetterException {

        List<SetterReturnObject> list = new ArrayList<>();
        list.addAll(setLocation(population.getLocation()));
        list.addAll(setTaxonId(population.getTaxonId()));
        list.addAll(setCount(population.getCount()));
        list.addAll(setInfectionAndImmunityCensuses(population.getInfectionAndImmunityCensuses()));
        if (population.getIndividualBiologyAndBehavior() != null) {
            list.addAll(setIndividualBiologyAndBehavior(population.getIndividualBiologyAndBehavior()));
        }

        return list;
    }
}
