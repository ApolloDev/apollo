package edu.pitt.apollo.apollotranslator.setters;

import edu.pitt.apollo.apollotranslator.ApolloTranslationEngine;
import edu.pitt.apollo.apollotranslator.types.translator.SetterReturnObject;
import edu.pitt.apollo.apollotranslator.exception.ApolloSetterException;
import edu.pitt.apollo.types.v4_0_1.Vaccination;
import edu.pitt.apollo.types.v4_0_1.VaccinationEfficacyForSimulatorConfiguration;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * Author: Nick Millett
 * Email: nick.millett@gmail.com
 * Date: Sep 18, 2013
 * Time: 2:03:42 PM
 * Class: VaccinationSetter
 * IDE: NetBeans 6.9.1
 */
public class VaccinationSetter extends TreatmentSetter<Vaccination> {

    private static final String SECTION = IndividualTreatmentControlMeasureSetter.VACCINATION_CONTROL_STRATEGY_SECTION;
    private static final String VACCINE_ID = "vaccineId";
    private static final String VACCINATION_EFFICACIES = "vaccinationEfficacies";

    public VaccinationSetter(ApolloTranslationEngine apolloTranslatorEngine, String type) {
        super(type, SECTION, apolloTranslatorEngine);
    }

    private List<SetterReturnObject> setVaccineId(String identifier) throws ApolloSetterException {
        return setValue(VACCINE_ID, identifier, SECTION);
    }

    private List<SetterReturnObject> setVaccinationEfficacies(List<VaccinationEfficacyForSimulatorConfiguration> vaccinationEfficacies) throws ApolloSetterException {

        List<SetterReturnObject> sroList;
        if (vaccinationEfficacies != null && vaccinationEfficacies.size() > 0) {

            sroList = setValue(VACCINATION_EFFICACIES, "(list values described below)", section);
            ListSetter setter = new ListSetter(VaccinationEfficacyForSimulatorConfigurationSetter.class, VaccinationEfficacyForSimulatorConfiguration.class,
                    vaccinationEfficacies, apolloTranslationEngine, section, type + "." + VACCINATION_EFFICACIES);

            List<SetterReturnObject> result = setter.set();

            sroList.get(0).setSubApolloParameters(result);
        } else {
            sroList = setValue(VACCINATION_EFFICACIES, PARAM_IS_NOT_SET_LABEL, section);
        }

        return sroList;
    }

    public List<SetterReturnObject> set(Vaccination treatment) throws ApolloSetterException {

        List<SetterReturnObject> list = new ArrayList<SetterReturnObject>();
        list.addAll(setTreatment(treatment));
        list.addAll(setVaccineId(treatment.getVaccineId()));
        list.addAll(setVaccinationEfficacies(treatment.getVaccinationEfficacies()));

        return list;
    }
}
