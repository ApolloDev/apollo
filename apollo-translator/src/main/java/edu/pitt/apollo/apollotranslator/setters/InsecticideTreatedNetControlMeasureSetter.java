package edu.pitt.apollo.apollotranslator.setters;

import edu.pitt.apollo.apollotranslator.ApolloTranslationEngine;
import edu.pitt.apollo.apollotranslator.exception.ApolloSetterException;
import edu.pitt.apollo.apollotranslator.types.translator.SetterReturnObject;
import edu.pitt.apollo.types.v4_0_1.InsecticideTreatedNetControlMeasure;
import edu.pitt.apollo.types.v4_0_1.Rate;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by nem41 on 8/5/15.
 */
public class InsecticideTreatedNetControlMeasureSetter extends AbstractTypedSetter<InsecticideTreatedNetControlMeasure> {

    public static final String SECTION = "INSECTICIDE TREATED NET CONTROL STRATEGY";
    private static final String NET_HOLING_RATE = "netHolingRate";
    private static final String INSECTICIDE_EFFICACY_DECAY_RATE = "insecticideEfficacyDecayRate";
    private static final String NIGHTLY_PROBABILITY_OF_USE = "nightlyProbabilityOfUse";

    public InsecticideTreatedNetControlMeasureSetter() {
    }

    public InsecticideTreatedNetControlMeasureSetter(ApolloTranslationEngine apolloTranslationEngine, String type, String section) {
        super(type, section, apolloTranslationEngine);
    }

    private List<SetterReturnObject> setNetHolingRate(Rate netHolingRate) throws ApolloSetterException {
        RateSetter setter = new RateSetter(apolloTranslationEngine, type + "." + NET_HOLING_RATE, section);
        return setter.set(netHolingRate);
    }

    private List<SetterReturnObject> setInsecticideEfficacyDecayRate(Rate rate) throws ApolloSetterException {
        RateSetter setter = new RateSetter(apolloTranslationEngine, type + "." + INSECTICIDE_EFFICACY_DECAY_RATE, section);
        return setter.set(rate);
    }

    private List<SetterReturnObject> setNightlyProbabilityOfUse(double probability) throws ApolloSetterException {
        return setValue(NIGHTLY_PROBABILITY_OF_USE, Double.toString(probability), section);
    }

    @Override
    public List<SetterReturnObject> set(InsecticideTreatedNetControlMeasure insecticideTreatedNetControlMeasure) throws ApolloSetterException {
        List<SetterReturnObject> list = new ArrayList<>();
        if (insecticideTreatedNetControlMeasure.getNetHolingRate() != null) {
            list.addAll(setNetHolingRate(insecticideTreatedNetControlMeasure.getNetHolingRate()));
        }
        if (insecticideTreatedNetControlMeasure.getInsecticideEfficacyDecayRate() != null) {
            list.addAll(setInsecticideEfficacyDecayRate(insecticideTreatedNetControlMeasure.getInsecticideEfficacyDecayRate()));
        }
        list.addAll(setNightlyProbabilityOfUse(insecticideTreatedNetControlMeasure.getNightlyProbabilityOfUse()));

        return list;
    }
}
