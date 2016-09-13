package edu.pitt.apollo.apollotranslator.setters;

import edu.pitt.apollo.apollotranslator.ApolloTranslationEngine;
import edu.pitt.apollo.apollotranslator.exception.ApolloSetterException;
import edu.pitt.apollo.apollotranslator.types.translator.SetterReturnObject;
import edu.pitt.apollo.types.v4_0.FixedDuration;
import edu.pitt.apollo.types.v4_0.TemporalTriggerDefinition;
import edu.pitt.apollo.types.v4_0.TimeScaleEnum;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * Author: Nick Millett
 * Email: nick.millett@gmail.com
 * Date: Apr 3, 2014
 * Time: 1:05:44 PM
 * Class: TemporalTriggerDefinitionSetter
 * IDE: NetBeans 6.9.1
 */
public class TemporalTriggerDefinitionSetter extends AbstractTypedSetter<TemporalTriggerDefinition> {
    
    private static final String TIME_SCALE = "timeScale";
    private static final String TIME_SINCE_TIME_SCALE_ZERO = "timeSinceTimeScaleZero";
    
    private List<SetterReturnObject> setTimeScale(TimeScaleEnum scale) throws ApolloSetterException {
        return setValue(TIME_SCALE, scale.toString(), section);
    }
    
    private List<SetterReturnObject> setTimeSinceTimeScaleZero(FixedDuration duration) throws ApolloSetterException {
        DurationSetter setter = new DurationSetter(apolloTranslationEngine, type + "." + TIME_SINCE_TIME_SCALE_ZERO, section);
        return setter.set(duration);
    }
    
    public TemporalTriggerDefinitionSetter(ApolloTranslationEngine apolloTranslationEngine, String prefix, String section) {
        super(prefix, section, apolloTranslationEngine);
    }

    @Override
    public List<SetterReturnObject> set(TemporalTriggerDefinition t) throws ApolloSetterException {
        List<SetterReturnObject> list = new ArrayList<SetterReturnObject>();
        list.addAll(setTimeScale(t.getTimeScale()));
        list.addAll(setTimeSinceTimeScaleZero(t.getTimeSinceTimeScaleZero()));
        return list;
    }
}
