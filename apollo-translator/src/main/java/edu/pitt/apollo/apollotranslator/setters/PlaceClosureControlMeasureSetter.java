package edu.pitt.apollo.apollotranslator.setters;

import java.util.ArrayList;
import java.util.List;

import edu.pitt.apollo.apollotranslator.ApolloTranslationEngine;
import edu.pitt.apollo.apollotranslator.exception.ApolloSetterException;
import edu.pitt.apollo.apollotranslator.types.translator.SetterReturnObject;
import edu.pitt.apollo.types.v4_0.Duration;
import edu.pitt.apollo.types.v4_0.PlaceClosureControlMeasure;
import edu.pitt.apollo.types.v4_0.PlaceEnum;

public class PlaceClosureControlMeasureSetter extends InfectiousDiseaseControlMeasureSetter<PlaceClosureControlMeasure> {

//    public static final String SCHOOL_CLOSURE_CM_TYPE_PREFIX = "schoolClosureControlMeasure";
    public static final String SECTION = "PLACE CLOSURE CONTROL STRATEGY";
    private static final String PLACE_CLASS = "placeClass";
    private static final String CLOSE_INDIVIDUAL_PLACES_INDEPENDENTLY = "closeIndividualPlacesIndependently";
    private static final String CLOSURE_PERIOD = "closurePeriod";
    private static final String HOUSEHOLD_TRANSMISSION_MULTIPLIER = "householdTransmissionMultiplier";
    private static final String COMMUNITY_TRANSMISSION_MULTIPLIER = "communityTransmissionMultiplier";

    public PlaceClosureControlMeasureSetter() {
    }

    public PlaceClosureControlMeasureSetter(ApolloTranslationEngine apolloTranslationEngine, String prefix, String section) {
        super(apolloTranslationEngine, prefix, section);
    }

    private List<SetterReturnObject> setPlaceClass(PlaceEnum place) throws ApolloSetterException {
        return setValue(PLACE_CLASS, place.toString(), SECTION);
    }

    private List<SetterReturnObject> setCloseIndividualPlacesIndependently(boolean value) throws ApolloSetterException {
        return setValue(CLOSE_INDIVIDUAL_PLACES_INDEPENDENTLY, Boolean.toString(value), SECTION);
    }

    private List<SetterReturnObject> setClosurePeriod(Duration duration) throws ApolloSetterException {
        DurationSetter setter = new DurationSetter(apolloTranslationEngine, type + "." + CLOSURE_PERIOD, SECTION);
        return setter.set(duration);
    }

    private List<SetterReturnObject> setHouseholdTransmissionMultiplier(double value) throws ApolloSetterException {
        return setValue(HOUSEHOLD_TRANSMISSION_MULTIPLIER, Double.toString(value), SECTION);
    }

    private List<SetterReturnObject> setCommunityTransmissionMultiplier(double value) throws ApolloSetterException {
        return setValue(COMMUNITY_TRANSMISSION_MULTIPLIER, Double.toString(value), SECTION);
    }

    @Override
    public List<SetterReturnObject> set(PlaceClosureControlMeasure strategy) throws ApolloSetterException {
        List<SetterReturnObject> list = new ArrayList<SetterReturnObject>();
        list.addAll(setInfectiousDiseaseControlMeasure(strategy));
        list.addAll(setPlaceClass(strategy.getPlaceClass()));
        list.addAll(setCloseIndividualPlacesIndependently(strategy.isCloseIndividualPlacesIndependently()));
        list.addAll(setClosurePeriod(strategy.getClosurePeriod()));
        if (strategy.getHouseholdTransmissionMultiplier() != null) {
            list.addAll(setHouseholdTransmissionMultiplier(strategy.getHouseholdTransmissionMultiplier()));
        }
        if (strategy.getCommunityTransmissionMultiplier() != null) {
            list.addAll(setCommunityTransmissionMultiplier(strategy.getCommunityTransmissionMultiplier()));
        }

        return list;
    }
}
