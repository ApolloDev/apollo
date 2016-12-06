package edu.pitt.apollo.apollotranslator.setters;

import edu.pitt.apollo.apollotranslator.ApolloTranslationEngine;
import edu.pitt.apollo.apollotranslator.exception.ApolloSetterException;
import edu.pitt.apollo.apollotranslator.types.translator.SetterReturnObject;
import edu.pitt.apollo.types.v4_0_1.TimeAxisCategoryLabels;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * Author: Nick Millett
 * Email: nick.millett@gmail.com
 * Date: Mar 25, 2014
 * Time: 10:44:24 AM
 * Class: TimeAxisCategoryLabelsSetter
 * IDE: NetBeans 6.9.1
 */
public class TimeAxisCategoryLabelsSetter extends AbstractTypedSetter<TimeAxisCategoryLabels> {

    private static final String LABEL = "label";
    private static final String SIMULATOR_TIME_EARLIEST = "simulatorTimeEarliest";
    private static final String SIMULATOR_TIME_LATEST = "simulatorTimeLatest";

    public TimeAxisCategoryLabelsSetter() {
    }

    public TimeAxisCategoryLabelsSetter(String prefix,
            String section, ApolloTranslationEngine apolloTranslationEngine) {
        super(prefix, section, apolloTranslationEngine);
    }

    private List<SetterReturnObject> setLabel(String label) throws ApolloSetterException {
        return setValue(LABEL, label, section);
    }

    private List<SetterReturnObject> setSimulatorTimeEarliest(BigInteger time) throws ApolloSetterException {
        return setValue(SIMULATOR_TIME_EARLIEST, time.toString(), section);
    }

    private List<SetterReturnObject> setSimulatorTimeLatest(BigInteger time) throws ApolloSetterException {
        return setValue(SIMULATOR_TIME_LATEST, time.toString(), section);
    }

    @Override
    public List<SetterReturnObject> set(TimeAxisCategoryLabels t) throws ApolloSetterException {
        List<SetterReturnObject> list = new ArrayList<SetterReturnObject>();
        list.addAll(setLabel(t.getLabel()));
        list.addAll(setSimulatorTimeEarliest(t.getSimulatorTimeEarliest()));
        list.addAll(setSimulatorTimeLatest(t.getSimulatorTimeLatest()));
        return list;
    }
}
