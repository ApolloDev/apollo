package edu.pitt.apollo.apollotranslator.setters;

import edu.pitt.apollo.apollotranslator.ApolloTranslationEngine;
import edu.pitt.apollo.apollotranslator.exception.ApolloSetterException;
import edu.pitt.apollo.apollotranslator.types.translator.SetterReturnObject;
import edu.pitt.apollo.types.v4_0_2.Duration;
import edu.pitt.apollo.types.v4_0_2.IndividualTreatmentEnum;
import edu.pitt.apollo.types.v4_0_2.Location;
import edu.pitt.apollo.types.v4_0_2.TreatmentSurveillanceCapability;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * Author: Nick Millett
 * Email: nick.millett@gmail.com
 * Date: Apr 4, 2014
 * Time: 11:34:26 AM
 * Class: TreatmentSurveillanceCapabilitySetter
 * IDE: NetBeans 6.9.1
 */
public class TreatmentSurveillanceCapabilitySetter extends AbstractTypedSetter<TreatmentSurveillanceCapability> {

    private static final String LOCATION = "location";
    private static final String TREATMENT = "treatment";
    private static final String SENSITIVITY_OF_TREATMENT_DETECTION = "sensitivityOfTreatmentDetection";
    private static final String SPECIFICITY_OF_TREATMENT_DETECTION = "specificityOfTreatmentDetection";
    private static final String TIME_DELAY_OF_TREATMENT_DETECTION = "timeDelayOfTreatmentDetection";

    public TreatmentSurveillanceCapabilitySetter() {
    }

    public TreatmentSurveillanceCapabilitySetter(ApolloTranslationEngine apolloTranslationEngine, String prefix, String section) {
        super(prefix, section, apolloTranslationEngine);
    }
    
        private List<SetterReturnObject> setLocation(Location location) throws ApolloSetterException {
        LocationSetter setter = new LocationSetter(apolloTranslationEngine, type + "." + LOCATION, section);
        return setter.set(location);
    }
    
    
    private List<SetterReturnObject> setTreatment(IndividualTreatmentEnum treatment) throws ApolloSetterException {
        return setValue(TREATMENT, treatment.toString(), section);
    }
    
    private List<SetterReturnObject> setSensitivityOfTreatmentDetection(double probability) throws ApolloSetterException {
        return setValue(SENSITIVITY_OF_TREATMENT_DETECTION, Double.toString(probability), section);
    }
    
    private List<SetterReturnObject> setSpecificityOfTreatmentDetection(double probability) throws ApolloSetterException {
        return setValue(SPECIFICITY_OF_TREATMENT_DETECTION, Double.toString(probability), section);
    }
    
    private List<SetterReturnObject> setTimeDelayOfTreatmentDetection(Duration duration) throws ApolloSetterException {
        DurationSetter setter = new DurationSetter(apolloTranslationEngine, type + "." + TIME_DELAY_OF_TREATMENT_DETECTION, section);
        return setter.set(duration);
    }

    @Override
    public List<SetterReturnObject> set(TreatmentSurveillanceCapability t) throws ApolloSetterException {
        List<SetterReturnObject> list = new ArrayList<SetterReturnObject>();
        list.addAll(setLocation(t.getLocation()));
        list.addAll(setTreatment(t.getTreatment()));
        list.addAll(setSensitivityOfTreatmentDetection(t.getSensitivityOfTreatmentDetection()));
        list.addAll(setSpecificityOfTreatmentDetection(t.getSpecificityOfTreatmentDetection()));
        list.addAll(setTimeDelayOfTreatmentDetection(t.getTimeDelayOfTreatmentDetection()));
        return list;
    }
}
