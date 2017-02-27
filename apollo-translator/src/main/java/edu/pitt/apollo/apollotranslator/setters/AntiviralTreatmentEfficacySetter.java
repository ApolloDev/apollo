package edu.pitt.apollo.apollotranslator.setters;

import edu.pitt.apollo.apollotranslator.ApolloTranslationEngine;
import edu.pitt.apollo.apollotranslator.exception.ApolloSetterException;
import edu.pitt.apollo.apollotranslator.types.translator.SetterReturnObject;
import edu.pitt.apollo.types.v4_0_1.AntiviralTreatmentEfficacy;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * Author: Nick Millett
 * Email: nick.millett@gmail.com
 * Date: Mar 21, 2014
 * Time: 2:37:45 PM
 * Class: AntiviralTreatmentEfficacySetter
 * IDE: NetBeans 6.9.1
 */
public class AntiviralTreatmentEfficacySetter extends TreatmentEfficacySetter<AntiviralTreatmentEfficacy> {

    private static final String EFFICACY = "efficacy";

    public AntiviralTreatmentEfficacySetter() {
        
    }
    
    public AntiviralTreatmentEfficacySetter(String type, String section,
            ApolloTranslationEngine apolloTranslationEngine) {
        super(type, section, apolloTranslationEngine);
    }

    public List<SetterReturnObject> setEfficacy(double efficacy) throws ApolloSetterException {
        return setValue(EFFICACY, Double.toString(efficacy), section);
    }

    public List<SetterReturnObject> set(AntiviralTreatmentEfficacy efficacy) throws ApolloSetterException {
        List<SetterReturnObject> result = new ArrayList<SetterReturnObject>();

        result.addAll(setTreatmentEfficacy(efficacy));
        result.addAll(setEfficacy(efficacy.getEfficacy()));

        return result;
    }
}
