package edu.pitt.apollo.apollotranslator.setters;

import edu.pitt.apollo.apollotranslator.ApolloTranslationEngine;
import edu.pitt.apollo.apollotranslator.exception.ApolloSetterException;
import edu.pitt.apollo.apollotranslator.types.translator.SetterReturnObject;
import edu.pitt.apollo.types.v4_0_1.FixedDuration;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * Author: Nick Millett
 * Email: nick.millett@gmail.com
 * Date: Apr 3, 2014
 * Time: 1:21:54 PM
 * Class: FixedDurationSetter
 * IDE: NetBeans 6.9.1
 */
public class FixedDurationSetter extends AbstractTypedSetter<FixedDuration> {

    private static final String VALUE = "value";

    public FixedDurationSetter() {
    }

    public FixedDurationSetter(ApolloTranslationEngine apolloTranslationEngine, String prefix, String section) {
        super(prefix, section, apolloTranslationEngine);
    }

    private List<SetterReturnObject> setValue(double value) throws ApolloSetterException {
        return setValue(VALUE, Double.toString(value), section);
    }

    @Override
    public List<SetterReturnObject> set(FixedDuration t) throws ApolloSetterException {
        List<SetterReturnObject> list = new ArrayList<SetterReturnObject>();
        list.addAll(setValue(t.getValue()));
        return list;
    }
}
