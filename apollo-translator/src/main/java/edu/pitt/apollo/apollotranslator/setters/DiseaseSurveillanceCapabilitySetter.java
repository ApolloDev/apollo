package edu.pitt.apollo.apollotranslator.setters;

import edu.pitt.apollo.apollotranslator.ApolloTranslationEngine;
import edu.pitt.apollo.apollotranslator.exception.ApolloSetterException;
import edu.pitt.apollo.apollotranslator.types.translator.SetterReturnObject;
import edu.pitt.apollo.types.v4_0_2.*;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * Author: Nick Millett
 * Email: nick.millett@gmail.com
 * Date: Mar 24, 2014
 * Time: 10:47:26 AM
 * Class: DiseaseSurveillanceCapabilitySetter
 * IDE: NetBeans 6.9.1
 */
public class DiseaseSurveillanceCapabilitySetter extends AbstractTypedSetter<DiseaseSurveillanceCapability> {

    private static final String LOCATION = "location";
    private static final String PATHOGEN = "pathogen";
    private static final String SPECIES_OF_CASE = "speciesOfCase";
    private static final String CASE_DEFINITION = "caseDefinition";
    private static final String SENSITIVITY_OF_CASE_DETECTION = "sensitivityOfCaseDetection";
    private static final String SPECIFICITY_OF_CASE_DETECTION = "specificityOfCaseDetection";
    private static final String TIME_DELAY_OF_CASE_DETECTION = "timeDelayOfCaseDetection";
    
    public DiseaseSurveillanceCapabilitySetter(ApolloTranslationEngine apolloTranslationEngine, String prefix, String section) {
        super(prefix, section, apolloTranslationEngine);
    }
    
    private List<SetterReturnObject> setLocation(Location location) throws ApolloSetterException {
        LocationSetter setter = new LocationSetter(apolloTranslationEngine, type + "." + LOCATION, section);
        return setter.set(location);
    }
    
    private List<SetterReturnObject> setPathogen(ApolloPathogenCode code) throws ApolloSetterException {
        ApolloPathogenCodeSetter setter = new ApolloPathogenCodeSetter(apolloTranslationEngine, type + "." + PATHOGEN, section);
        return setter.set(code);
    }
    
    private List<SetterReturnObject> setSpeciesOfCase(String species) throws ApolloSetterException {
        return setValue(SPECIES_OF_CASE, species, section);
    }
    
    private List<SetterReturnObject> setCaseDefinition(DiseaseOutcomeEnum outcome) throws ApolloSetterException {
        return setValue(CASE_DEFINITION, outcome.toString(), section);
    }
    
    private List<SetterReturnObject> setSensitivityOfCaseDetection(double probability) throws ApolloSetterException {
        return setValue(SENSITIVITY_OF_CASE_DETECTION, Double.toString(probability), section);
    }
    
    private List<SetterReturnObject> setSpecificityOfCaseDetection(double probability) throws ApolloSetterException {
        return setValue(SPECIFICITY_OF_CASE_DETECTION, Double.toString(probability), section);
    }
    
    private List<SetterReturnObject> setTimeDelayOfCaseDetection(Duration duration) throws ApolloSetterException {
        DurationSetter setter = new DurationSetter(apolloTranslationEngine, type + "." + TIME_DELAY_OF_CASE_DETECTION, section);
        return setter.set(duration);
    }
    
    @Override
    public List<SetterReturnObject> set(DiseaseSurveillanceCapability t) throws ApolloSetterException {
        List<SetterReturnObject> list = new ArrayList<SetterReturnObject>();
        list.addAll(setLocation(t.getLocation()));
        list.addAll(setPathogen(t.getPathogen()));
        list.addAll(setSpeciesOfCase(t.getSpeciesOfCase()));
        list.addAll(setCaseDefinition(t.getCaseDefinition()));
        list.addAll(setSensitivityOfCaseDetection(t.getSensitivityOfCaseDetection()));
        list.addAll(setSpecificityOfCaseDetection(t.getSpecificityOfCaseDetection()));
        list.addAll(setTimeDelayOfCaseDetection(t.getTimeDelayOfCaseDetection()));
        
        return list;
    }
    
}
