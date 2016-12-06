package edu.pitt.apollo.apollotranslator.setters;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

import edu.pitt.apollo.apollotranslator.ApolloTranslationEngine;
import edu.pitt.apollo.apollotranslator.exception.ApolloSetterException;
import edu.pitt.apollo.apollotranslator.types.translator.SetterReturnObject;
import edu.pitt.apollo.types.v4_0_1.AgeRangeCategoryDefinition;
import edu.pitt.apollo.types.v4_0_1.UnitOfTimeEnum;

/**
 *
 * Author: Nick Millett
 * Email: nick.millett@gmail.com
 * Date: Oct 3, 2013
 * Time: 12:06:48 PM
 * Class: AgeRangeCategoryDefinitionSetter
 * IDE: NetBeans 6.9.1
 */
public class AgeRangeCategoryDefinitionSetter extends AbstractTypedSetter<AgeRangeCategoryDefinition> {

    private static final String AGE_RANGE_LOWER_BOUND = "lowerBound";
    private static final String UNIT_OF_TIME_FOR_LOWER_BOUND = "unitOfTimeForLowerBound";
    private static final String AGE_RANGE_UPPER_BOUND = "upperBound";
    private static final String UNIT_OF_TIME_FOR_UPPER_BOUND = "unitOfTimeForUpperBound";

    public AgeRangeCategoryDefinitionSetter() {
    }

    public AgeRangeCategoryDefinitionSetter(ApolloTranslationEngine apolloTranslationEngine, String prefix, String section) {
        super(prefix, section, apolloTranslationEngine);
    }

    private List<SetterReturnObject> setAgeRangeLowerBound(BigInteger lowerBound) throws ApolloSetterException {
        return setValue(AGE_RANGE_LOWER_BOUND, lowerBound.toString(), section);
    }

    private List<SetterReturnObject> setUnitOfTimeForLowerBound(UnitOfTimeEnum time) throws ApolloSetterException {
        return setValue(UNIT_OF_TIME_FOR_LOWER_BOUND, time.toString(), section);
    }

    private List<SetterReturnObject> setAgeRangeUpperBound(BigInteger upperBound) throws ApolloSetterException {
        return setValue(AGE_RANGE_UPPER_BOUND, upperBound.toString(), section);
    }

    private List<SetterReturnObject> setUnitOfTimeForUpperBound(UnitOfTimeEnum time) throws ApolloSetterException {
        return setValue(UNIT_OF_TIME_FOR_UPPER_BOUND, time.toString(), section);
    }

    public List<SetterReturnObject> set(AgeRangeCategoryDefinition ageRange) throws ApolloSetterException {

        List<SetterReturnObject> list = new ArrayList<SetterReturnObject>();
       /* list.addAll(setAgeRangeLowerBound(ageRange.getLowerBound()));
        list.addAll(setUnitOfTimeForLowerBound(ageRange.getUnitOfTimeForLowerBound()));
        list.addAll(setAgeRangeUpperBound(ageRange.getUpperBound()));
        list.addAll(setUnitOfTimeForUpperBound(ageRange.getUnitOfTimeForUpperBound()));*/

        return list;
    }
//    public List<SetterReturnObject> set(List<AgeRange> ageRanges) throws ApolloSetterException {
//
//        if (ageRanges != null && ageRanges.size() > 0) {
//            for (AgeRange range : ageRanges) {
//                results.addAll(set(range));
//            }
//        }
//        
//        return results;
//    }
}
