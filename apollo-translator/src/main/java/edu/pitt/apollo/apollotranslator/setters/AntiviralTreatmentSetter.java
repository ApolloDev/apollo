package edu.pitt.apollo.apollotranslator.setters;

import edu.pitt.apollo.apollotranslator.ApolloTranslationEngine;
import edu.pitt.apollo.apollotranslator.exception.ApolloSetterException;
import edu.pitt.apollo.apollotranslator.types.translator.SetterReturnObject;
import edu.pitt.apollo.types.v4_0_1.AntiviralTreatment;
import edu.pitt.apollo.types.v4_0_1.AntiviralTreatmentEfficacy;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * Author: Nick Millett
 * Email: nick.millett@gmail.com
 * Date: Sep 18, 2013
 * Time: 1:11:37 PM
 * Class: AntiviralTreatmentSetter
 * IDE: NetBeans 6.9.1
 */
public class AntiviralTreatmentSetter extends TreatmentSetter<AntiviralTreatment> {

    private static final String SECTION = IndividualTreatmentControlMeasureSetter.ANTIVIRAL_CONTROL_STRATEGY_SECTION;
    private static final String ANTIVIRAL_ID = "antiviralId";
    private static final String ANTIVIRAL_TREATMENT_EFFICACY = "antiviralTreatmentEfficacy";

    public AntiviralTreatmentSetter(ApolloTranslationEngine apolloTranslatorEngine, String type) {
        super(type, SECTION, apolloTranslatorEngine);
    }

    private List<SetterReturnObject> setAntiviralId(String id) throws ApolloSetterException {
        return setValue(ANTIVIRAL_ID, id, SECTION);
    }

    private List<SetterReturnObject> setAntiviralTreatmentEfficacies(List<AntiviralTreatmentEfficacy> efficacies) throws ApolloSetterException {

        List<SetterReturnObject> sroList;
        if (efficacies != null && efficacies.size() > 0) {

            sroList = setValue(ANTIVIRAL_TREATMENT_EFFICACY, "(list values described below)", section);
            ListSetter setter = new ListSetter(AntiviralTreatmentEfficacySetter.class, AntiviralTreatmentEfficacy.class,
                    efficacies, apolloTranslationEngine, section, type + "." + ANTIVIRAL_TREATMENT_EFFICACY);

            List<SetterReturnObject> result = setter.set();

            sroList.get(0).setSubApolloParameters(result);
        } else {
            sroList = setValue(ANTIVIRAL_TREATMENT_EFFICACY, PARAM_IS_NOT_SET_LABEL, section);
        }

        return sroList;
    }

    public List<SetterReturnObject> set(AntiviralTreatment treatment) throws ApolloSetterException {

        List<SetterReturnObject> list = new ArrayList<SetterReturnObject>();
        list.addAll(setTreatment(treatment));
        list.addAll(setAntiviralId(treatment.getAntiviralId()));
        list.addAll(setAntiviralTreatmentEfficacies(treatment.getAntiviralTreatmentEfficacy()));
        return list;
    }
}
