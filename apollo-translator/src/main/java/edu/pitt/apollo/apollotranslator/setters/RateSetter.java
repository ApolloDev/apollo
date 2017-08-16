package edu.pitt.apollo.apollotranslator.setters;

import edu.pitt.apollo.apollotranslator.ApolloTranslationEngine;
import edu.pitt.apollo.apollotranslator.exception.ApolloSetterException;
import edu.pitt.apollo.apollotranslator.types.translator.SetterReturnObject;
import edu.pitt.apollo.types.v4_0_2.ProbabilityDistribution;
import edu.pitt.apollo.types.v4_0_2.Rate;
import edu.pitt.apollo.types.v4_0_2.UnitOfMeasureEnum;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * Author: Nick Millett
 * Email: nick.millett@gmail.com
 * Date: Mar 24, 2014
 * Time: 11:48:23 AM
 * Class: RateSetter
 * IDE: NetBeans 6.9.1
 */
public class RateSetter extends AbstractTypedSetter<Rate> {

    private static final String NUMERATOR_UNIT_OF_MEASURE = "numeratorUnitOfMeasure";
    private static final String DENOMINATOR_UNIT_OF_MEASURE = "denominatorUnitOfMeasure";
    private static final String VALUE = "value";
    private static final String PROBABILITY_DISTRIBUTION = "probabilityDistribution";
    
    public RateSetter() {
        
    }
    
    private List<SetterReturnObject> setNumeratorUnitOfMeasure(UnitOfMeasureEnum unit) throws ApolloSetterException {
        return setValue(NUMERATOR_UNIT_OF_MEASURE, unit.toString(), section);
    }
    
    private List<SetterReturnObject> setDenominatorUnitOfMeasure(UnitOfMeasureEnum unit) throws ApolloSetterException {
        return setValue(DENOMINATOR_UNIT_OF_MEASURE, unit.toString(), section);
    }
    
    private List<SetterReturnObject> setValue(Double value) throws ApolloSetterException {
        return setValue(VALUE, value.toString(), section);
    }
    
    private List<SetterReturnObject> setProbabilityDistribution(ProbabilityDistribution distribution) throws ApolloSetterException {
        ProbabilityDistributionSetter setter = new ProbabilityDistributionSetter(apolloTranslationEngine, type + "." + PROBABILITY_DISTRIBUTION, section);
        return setter.set(distribution);
    }
    
    public RateSetter(ApolloTranslationEngine apolloTranslationEngine, String type, String section) {
        super(type, section, apolloTranslationEngine);
    }
    
    @Override
    public List<SetterReturnObject> set(Rate t) throws ApolloSetterException {
        List<SetterReturnObject> list = new ArrayList<SetterReturnObject>();
        if (t.getNumeratorUnitOfMeasure() != null) {
            list.addAll(setNumeratorUnitOfMeasure(t.getNumeratorUnitOfMeasure()));
        }
        if (t.getDenominatorUnitOfMeasure() != null) {
            list.addAll(setDenominatorUnitOfMeasure(t.getDenominatorUnitOfMeasure()));
        }
        if (t.getValue() != null) {
            list.addAll(setValue(t.getValue()));
        }
        if (t.getProbabilityDistribution() != null) {
            list.addAll(setProbabilityDistribution(t.getProbabilityDistribution()));
        }
        return list;
    }
    
}
