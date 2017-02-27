package edu.pitt.apollo.apollotranslator.setters;

import edu.pitt.apollo.apollotranslator.ApolloTranslationEngine;
import edu.pitt.apollo.apollotranslator.exception.ApolloSetterException;
import edu.pitt.apollo.apollotranslator.types.translator.SetterReturnObject;
import edu.pitt.apollo.types.v4_0_1.UncertainDuration;
import edu.pitt.apollo.types.v4_0_1.UncertainValue;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * Author: Nick Millett
 * Email: nick.millett@gmail.com
 * Date: Apr 3, 2014
 * Time: 1:27:53 PM
 * Class: UncertainDurationSetter
 * IDE: NetBeans 6.9.1
 */
public class UncertainDurationSetter extends AbstractTypedSetter<UncertainDuration> {

    private static final String PROBABILITY_DISTRIBUTION = "uncertainValue";

    public UncertainDurationSetter() {
    }

    public UncertainDurationSetter(ApolloTranslationEngine apolloTranslationEngine, String prefix, String section) {
        super(prefix, section, apolloTranslationEngine);
    }

    private List<SetterReturnObject> setUncertainValue(UncertainValue value) throws ApolloSetterException {
        UncertainValueSetter setter = new UncertainValueSetter(apolloTranslationEngine, type + "." + PROBABILITY_DISTRIBUTION, section);
        return setter.set(value);
    }

    @Override
    public List<SetterReturnObject> set(UncertainDuration t) throws ApolloSetterException {
        List<SetterReturnObject> list = new ArrayList<SetterReturnObject>();
        list.addAll(setUncertainValue(t.getUncertainValue()));
        return list;
    }
}
