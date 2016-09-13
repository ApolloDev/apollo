package edu.pitt.apollo.apollotranslator.setters;

import edu.pitt.apollo.apollotranslator.ApolloTranslationEngine;
import edu.pitt.apollo.apollotranslator.exception.ApolloSetterException;
import edu.pitt.apollo.apollotranslator.types.translator.SetterReturnObject;
import edu.pitt.apollo.types.v4_0.ProbabilityValuePair;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * Author: Nick Millett
 * Email: nick.millett@gmail.com
 * Date: Mar 21, 2014
 * Time: 3:52:17 PM
 * Class: ProbabilityValuePairSetter
 * IDE: NetBeans 6.9.1
 */
public class ProbabilityValuePairSetter extends AbstractTypedSetter<ProbabilityValuePair> {

    private static final String PROBABILITY = "probability";
    private static final String VALUE = "value";

    public ProbabilityValuePairSetter() {
    }

    public ProbabilityValuePairSetter(ApolloTranslationEngine apolloTranslationEngine, String prefix, String section) {
        super(prefix, section, apolloTranslationEngine);
    }

    private List<SetterReturnObject> setProbability(double probability) throws ApolloSetterException {
        return setValue(PROBABILITY, Double.toString(probability), section);
    }

    private List<SetterReturnObject> setValue(double value) throws ApolloSetterException {
        return setValue(VALUE, Double.toString(value), section);
    }

    @Override
    public List<SetterReturnObject> set(ProbabilityValuePair t) throws ApolloSetterException {
        List<SetterReturnObject> list = new ArrayList<SetterReturnObject>();

        list.addAll(setProbability(t.getProbability()));
        list.addAll(setValue(t.getValue()));

        return list;
    }
}
