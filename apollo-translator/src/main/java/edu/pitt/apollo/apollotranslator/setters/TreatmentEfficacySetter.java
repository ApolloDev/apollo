package edu.pitt.apollo.apollotranslator.setters;

import edu.pitt.apollo.apollotranslator.ApolloTranslationEngine;
import edu.pitt.apollo.apollotranslator.exception.ApolloSetterException;
import edu.pitt.apollo.apollotranslator.types.translator.SetterReturnObject;
import edu.pitt.apollo.types.v4_0_2.ApolloPathogenCode;
import edu.pitt.apollo.types.v4_0_2.TreatmentEfficacy;
import edu.pitt.apollo.types.v4_0_2.TreatmentPreventableOutcomeEnum;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * Author: Nick Millett
 * Email: nick.millett@gmail.com
 * Date: Mar 21, 2014
 * Time: 2:00:45 PM
 * Class: TreatmentEfficacySetter
 * IDE: NetBeans 6.9.1
 */
public abstract class TreatmentEfficacySetter<T extends TreatmentEfficacy> extends AbstractTypedSetter<T> {

    private static final String HOST_IDENTIFIER = "hostIdentifier";
    private static final String STRAIN_IDENTIFIER = "strainIdentifier";
    private static final String FOR_TREATMENT_PREVENTABLE_OUTCOME = "forTreatmentPreventableOutcome";
    private static final String VACCINE_IDENTIFIER = "vaccineIdentifier";
    private static final String DRUG_IDENTIFIER = "drugIdentifier";

    public TreatmentEfficacySetter() {
        
    }
    
    public TreatmentEfficacySetter(String type, String section,
            ApolloTranslationEngine apolloTranslationEngine) {
        super(type, section, apolloTranslationEngine);
    }

    private List<SetterReturnObject> setHostIdentifier(String id) throws ApolloSetterException {
        return setValue(HOST_IDENTIFIER, id, section);
    }

    private List<SetterReturnObject> setStrainIdentifier(ApolloPathogenCode code) throws ApolloSetterException {
        ApolloPathogenCodeSetter setter = new ApolloPathogenCodeSetter(apolloTranslationEngine, type + "." + STRAIN_IDENTIFIER, section);
        return setter.set(code);
    }

    private List<SetterReturnObject> setForTreatmentPreventableOutcome(TreatmentPreventableOutcomeEnum outcome) throws ApolloSetterException {
        return setValue(FOR_TREATMENT_PREVENTABLE_OUTCOME, outcome.toString(), section);
    }

    private List<SetterReturnObject> setVaccineIdentifier(String id) throws ApolloSetterException {
        return setValue(VACCINE_IDENTIFIER, id, section);
    }

    private List<SetterReturnObject> setDrugIdentifier(String id) throws ApolloSetterException {
        return setValue(DRUG_IDENTIFIER, id, section);
    }

    public List<SetterReturnObject> setTreatmentEfficacy(TreatmentEfficacy t) throws ApolloSetterException {

        List<SetterReturnObject> result = new ArrayList<SetterReturnObject>();

        result.addAll(setHostIdentifier(t.getHostIdentifier()));
        result.addAll(setStrainIdentifier(t.getStrainIdentifier()));
        result.addAll(setForTreatmentPreventableOutcome(t.getForTreatmentPreventableOutcome()));
        return result;
    }
}
