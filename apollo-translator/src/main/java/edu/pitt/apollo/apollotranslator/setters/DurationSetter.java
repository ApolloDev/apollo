package edu.pitt.apollo.apollotranslator.setters;

import edu.pitt.apollo.apollotranslator.ApolloTranslationEngine;
import edu.pitt.apollo.apollotranslator.exception.ApolloSetterException;
import edu.pitt.apollo.apollotranslator.types.translator.SetterReturnObject;
import edu.pitt.apollo.types.v4_0_2.Duration;
import edu.pitt.apollo.types.v4_0_2.FixedDuration;
import edu.pitt.apollo.types.v4_0_2.UncertainDuration;
import edu.pitt.apollo.types.v4_0_2.UnitOfTimeEnum;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * Author: Nick Millett
 * Email: nick.millett@gmail.com
 * Date: Mar 21, 2014
 * Time: 3:46:36 PM
 * Class: DurationSetter
 * IDE: NetBeans 6.9.1
 */
public class DurationSetter<T extends Duration> extends AbstractTypedSetter<T> {

    private static final String UNIT_OF_TIME = "unitOfTime";
//    private static final String SINGLE_VALUE = "value";
//    private static final String PROBABILITY_DISTRIBUTION = "probabilityDistribution";

    public DurationSetter() {
    }

    public DurationSetter(ApolloTranslationEngine apolloTranslationEngine, String prefix, String section) {
        super(prefix, section, apolloTranslationEngine);
    }

    private List<SetterReturnObject> setUnitOfTime(UnitOfTimeEnum unit) throws ApolloSetterException {
        return setValue(UNIT_OF_TIME, unit.toString(), section);
    }

//    private List<SetterReturnObject> setValue(Double value) throws ApolloSetterException {
//        return setValue(SINGLE_VALUE, value.toString(), section);
//    }

//    private List<SetterReturnObject> setProbabilityDistribution(ProbabilityDistribution dist) throws ApolloSetterException {
//        ProbabilityDistributionSetter setter = new ProbabilityDistributionSetter(apolloTranslationEngine, type + "." + PROBABILITY_DISTRIBUTION, section);
//        return setter.set(dist);
//    }

    @Override
    public List<SetterReturnObject> set(Duration t) throws ApolloSetterException {
        List<SetterReturnObject> list = new ArrayList<SetterReturnObject>();
        list.addAll(setUnitOfTime(t.getUnitOfTime()));
        if (t instanceof FixedDuration) {
            FixedDurationSetter setter = new FixedDurationSetter(apolloTranslationEngine, type, section);
            list.addAll(setter.set((FixedDuration) t));
        } else if (t instanceof UncertainDuration) {
            UncertainDurationSetter setter = new UncertainDurationSetter(apolloTranslationEngine, type, section);
            list.addAll(setter.set((UncertainDuration) t));
        }
        return list;
    }
}
