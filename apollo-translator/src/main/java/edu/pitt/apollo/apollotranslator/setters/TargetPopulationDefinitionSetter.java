package edu.pitt.apollo.apollotranslator.setters;

import edu.pitt.apollo.apollotranslator.ApolloTranslationEngine;
import edu.pitt.apollo.apollotranslator.exception.ApolloSetterException;
import edu.pitt.apollo.apollotranslator.types.translator.SetterReturnObject;
import edu.pitt.apollo.types.v4_0_1.*;

import java.util.List;

/**
 *
 * Author: Nick Millett
 * Email: nick.millett@gmail.com
 * Date: Oct 3, 2013
 * Time: 3:12:51 PM
 * Class: TargetPopulationDefinitionSetter
 * IDE: NetBeans 6.9.1
 */
public class TargetPopulationDefinitionSetter extends AbstractTypedSetter<TargetPopulationDefinition> {
    
    private static final String AGE_RANGE = "ageRange";
    private static final String GENDER = "gender";
    private static final String DISEASE_OUTCOME = "diseaseOutcome";
    private static final String OTHER_STRATIFICATIONS = "otherStratifications";
    
    public TargetPopulationDefinitionSetter(ApolloTranslationEngine apolloTranslationEngine, String type, String section) {
        super(type, section, apolloTranslationEngine);
    }
    
    private List<SetterReturnObject> setAgeRange(AgeRangeCategoryDefinition ageRange) throws ApolloSetterException {
        AgeRangeCategoryDefinitionSetter setter = new AgeRangeCategoryDefinitionSetter(apolloTranslationEngine, type + "." + AGE_RANGE, section);
        
        return setter.set(ageRange);
    }
    
    private List<SetterReturnObject> setGender(GenderEnum gender) throws ApolloSetterException {
        
        GenderCategorySetter genderSetter = new GenderCategorySetter(apolloTranslationEngine, type + "." + GENDER, section);
        return genderSetter.set(gender);
    }
    
    private List<SetterReturnObject> setDiseaseOutcome(DiseaseOutcomeEnum outcome) throws ApolloSetterException {
        
        return setValue(DISEASE_OUTCOME, outcome.toString(), section);
    }
    
    private List<SetterReturnObject> setOtherStratification(PopulationStratificationEnum strat) throws ApolloSetterException {
        return setValue(OTHER_STRATIFICATIONS, strat.toString(), section);
    }
    
    @Override
    public List<SetterReturnObject> set(TargetPopulationDefinition t) throws ApolloSetterException {
        if (t != null) {
            if (t.getAgeRange() != null) {
                results.addAll(setAgeRange(t.getAgeRange()));
            }
            if (t.getSex()!= null) {
                results.addAll(setGender(t.getSex()));
            }
            if (t.getDiseaseOutcome() != null) {
                results.addAll(setDiseaseOutcome(t.getDiseaseOutcome()));
            }
            if (t.getOtherStratification() != null) {
                results.addAll(setOtherStratification(t.getOtherStratification()));
            }
        } else {
//            results.addAll(setValue("", PARAM_IS_NOT_SET_LABEL, section));
        }
        return results;
    }
}
