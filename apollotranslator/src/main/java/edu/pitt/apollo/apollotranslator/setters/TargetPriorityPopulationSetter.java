package edu.pitt.apollo.apollotranslator.setters;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

import edu.pitt.apollo.apollotranslator.ApolloTranslationEngine;
import edu.pitt.apollo.apollotranslator.exception.ApolloSetterException;
import edu.pitt.apollo.apollotranslator.types.translator.SetterReturnObject;
import edu.pitt.apollo.types.v4_0.TargetPopulationDefinition;
import edu.pitt.apollo.types.v4_0.TargetPopulationEnum;
import edu.pitt.apollo.types.v4_0.TargetPriorityPopulation;

/**
 *
 * Author: Nick Millett
 * Email: nick.millett@gmail.com
 * Date: Oct 1, 2013
 * Time: 1:45:53 PM
 * Class: TargetPriorityPopulationSetter
 * IDE: NetBeans 6.9.1
 */
public class TargetPriorityPopulationSetter extends AbstractTypedSetter<TargetPriorityPopulation> {

    public static final String TARGET_PRIORITY_POPULATION_DESCRIPTION = "description";
    public static final String TARGET_PRIORITY_POPULATION_FRACTION_OF_TARGET_POPULATION = "fractionOfTargetPopulationToPrioritize";
    public static final String TARGET_PRIORITY_POPULATION_PRIORITY = "priority";
    public static final String TARGET_PRIORITY_POPULATION_TARGET_POPULATION_DEFINITION = "targetPopulationDefinition";
    public static final String TARGET_PRIORITY_POPULATION_TARGET_POPULATION_DEFINITION_ENUM = "targetPopulationEnum";
//    public static final String TARGET_PRIORITY_POPULATION_POPULATION_DEFINITION_AGE_RANGES = "ageRanges";
//    public static final String TARGET_PRIORITY_POPULATION_POPULATION_DEFINITION_GENDERS = "genders";
//    public static final String TARGET_PRIORITY_POPULATION_POPULATION_DEFINITION_DISEASE_STATES = "diseaseStates";

    public TargetPriorityPopulationSetter() {
    }

    public TargetPriorityPopulationSetter(ApolloTranslationEngine apolloTranslationEngine, String prefix,
            String section) {
        super(prefix, section, apolloTranslationEngine);
    }

    private List<SetterReturnObject> setTargetPriorityPopulationDescription(String label) throws ApolloSetterException {
        return setValue(TARGET_PRIORITY_POPULATION_DESCRIPTION, label, section);
    }

    private List<SetterReturnObject> setTargetPriorityPopulationFractionOfTargetPopulation(double fraction) throws ApolloSetterException {
        return setValue(TARGET_PRIORITY_POPULATION_FRACTION_OF_TARGET_POPULATION,
                Double.toString(fraction), section);
    }

    private List<SetterReturnObject> setTargetPriorityPopulationPriority(BigInteger priority) throws ApolloSetterException {
        return setValue(TARGET_PRIORITY_POPULATION_PRIORITY, priority.toString(), section);
    }

    private List<SetterReturnObject> setTargetPriorityPopulationPopulationStrataDefinition(TargetPopulationDefinition populationStrataDefinition) throws ApolloSetterException {
        TargetPopulationDefinitionSetter setter = new TargetPopulationDefinitionSetter(apolloTranslationEngine, type + "." + TARGET_PRIORITY_POPULATION_TARGET_POPULATION_DEFINITION, section);
        return setter.set(populationStrataDefinition);
    }

    private List<SetterReturnObject> setTargetPriorityPopulationTargetPopulationEnum(TargetPopulationEnum popEnum) throws ApolloSetterException {
        if (popEnum != null) {
            return setValue(TARGET_PRIORITY_POPULATION_TARGET_POPULATION_DEFINITION_ENUM, popEnum.value(), section);
        } else {
            return new ArrayList<SetterReturnObject>();
        }
    }

//    private List<SetterReturnObject> setTargetPriorityPopulationPopulationStrataDefinitionAgeRanges(List<AgeRange> ageRanges) throws ApolloSetterException {
//
//        AgeRangeSetter setter = new AgeRangeSetter(apolloTranslationEngine, type + "." + TARGET_PRIORITY_POPULATION_POPULATION_DEFINITION_AGE_RANGES, section);
//        return setter.set(ageRanges);
//    }
//
//    private List<SetterReturnObject> setTargetPriorityPopulationPopulationStrataDefinitionGenders(List<Gender> genders) throws ApolloSetterException {
//
//        GenderSetter setter = new GenderSetter(apolloTranslationEngine, type + "." + TARGET_PRIORITY_POPULATION_POPULATION_DEFINITION_GENDERS, section);
//        return setter.set(genders);
//    }
//
//    private List<SetterReturnObject> setTargetPriorityPopulationPopulationStrataDefinitionDiseaseStates(List<DiseaseState> diseaseStates) throws ApolloSetterException {
//
//        DiseaseStateSetter setter = new DiseaseStateSetter(apolloTranslationEngine, type + "." + TARGET_PRIORITY_POPULATION_POPULATION_DEFINITION_DISEASE_STATES, section);
//        return setter.set(diseaseStates);
//    }
    @Override
    public List<SetterReturnObject> set(TargetPriorityPopulation population) throws ApolloSetterException {
        List<SetterReturnObject> result = new ArrayList<SetterReturnObject>();
        result.addAll(setTargetPriorityPopulationDescription(population.getDescription()));
        result.addAll(setTargetPriorityPopulationFractionOfTargetPopulation(population.getFractionOfTargetPopulationToPrioritize()));
        result.addAll(setTargetPriorityPopulationPriority(population.getPriority()));
        result.addAll(setTargetPriorityPopulationPopulationStrataDefinition(population.getTargetPopulationDefinition()));
        result.addAll(setTargetPriorityPopulationTargetPopulationEnum(population.getTargetPopulationEnum()));
        
        //        if (population.getTargetPopulationDefinition() != null) {
//            List<AgeRange> ageRangeList = population.getTargetPopulationDefinition().getAgeRanges();
//            result.addAll(setTargetPriorityPopulationPopulationStrataDefinitionAgeRanges(ageRangeList));
//
//            List<Gender> genderList = population.getTargetPopulationDefinition().getGenders();
//            result.addAll(setTargetPriorityPopulationPopulationStrataDefinitionGenders(genderList));
//
//            List<DiseaseState> diseaseStates = population.getTargetPopulationDefinition().getDiseaseStates();
//            result.addAll(setTargetPriorityPopulationPopulationStrataDefinitionDiseaseStates(diseaseStates));
//        }

        return result;
    }
}
