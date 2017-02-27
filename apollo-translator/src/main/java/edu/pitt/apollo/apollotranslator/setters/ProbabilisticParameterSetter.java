package edu.pitt.apollo.apollotranslator.setters;

import edu.pitt.apollo.apollotranslator.ApolloTranslationEngine;
import edu.pitt.apollo.apollotranslator.exception.ApolloSetterException;
import edu.pitt.apollo.apollotranslator.types.translator.SetterReturnObject;
import edu.pitt.apollo.types.v4_0_1.ProbabilisticParameter;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * Author: Nick Millett
 * Email: nick.millett@gmail.com
 * Date: Feb 1, 2014
 * Time: 12:16:09 PM
 * Class: ProbabilisticParameterSetter
 * IDE: NetBeans 6.9.1
 */
public class ProbabilisticParameterSetter extends AbstractTypedSetter<ProbabilisticParameter> {

    private static final String VALUE = "probability";
    // TO-DO: implement the probability distribution setters
    private static final String UNCONDITIONAL_PROBABILITY_DISTRIBUTION = "unconditionalProbabilityDistribution";
    private static final String CONDITIONAL_PROBABILITY_DISTRIBUTION = "conditionalProbabilityDistribution";

    public ProbabilisticParameterSetter() {
        
    }
    
    public ProbabilisticParameterSetter(ApolloTranslationEngine apolloTranslationEngine, String prefix, String section) {
        super(prefix, section, apolloTranslationEngine);
    }


    private List<SetterReturnObject> setProbability(Double value) throws ApolloSetterException {
        return setValue(VALUE, value.toString(), section);
    }

    public List<SetterReturnObject> set(ProbabilisticParameter value) throws ApolloSetterException {

        List<SetterReturnObject> list = new ArrayList<SetterReturnObject>();

        list.addAll(setProbability(value.getProbability()));

        return list;
    }
}
