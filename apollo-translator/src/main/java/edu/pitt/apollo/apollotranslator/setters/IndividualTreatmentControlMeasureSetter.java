package edu.pitt.apollo.apollotranslator.setters;

import edu.pitt.apollo.apollotranslator.ApolloTranslationEngine;
import edu.pitt.apollo.apollotranslator.exception.ApolloSetterException;
import edu.pitt.apollo.apollotranslator.types.translator.SetterReturnObject;
import edu.pitt.apollo.types.v4_0_1.*;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * Author: Nick Millett
 * Email: nick.millett@gmail.com
 * Date: Feb 1, 2014
 * Time: 4:07:30 PM
 * Class: IndividualTreatmentControlMeasureSetter
 * IDE: NetBeans 6.9.1
 */
public class IndividualTreatmentControlMeasureSetter extends InfectiousDiseaseControlMeasureSetter<IndividualTreatmentControlMeasure> {

    public static final String VACCINATION_CONTROL_STRATEGY_SECTION = "VACCINATION CONTROL STRATEGY";
    public static final String ANTIVIRAL_CONTROL_STRATEGY_SECTION = "ANTIVIRAL TREATMENT CONTROL STRATEGY";
	public static final String DRUG_TREATMENT_CONTROL_STRATEGY_SECTION = "DRUG TREATMENT CONTROL STRATEGY";
    public static final String INDIVIDUAL_TREATMENT_FIELD = "individualTreatment";
    public static final String TARGET_POPULATIONS_AND_PRIORITIZATIONS_FIELD = "targetPopulationsAndPrioritizations";
    public static final String COMPLIANCE = "compliance";
    public static final String DELAY_FROM_SYSMPTOMS_TO_TREATMENT = "delayFromSymptomsToTreatment";
    public static final String PATHOGEN = "pathogen";

    public IndividualTreatmentControlMeasureSetter() {
        super();

    }

    public IndividualTreatmentControlMeasureSetter(ApolloTranslationEngine apolloTranslationEngine, String prefix, String section) {
        super(apolloTranslationEngine, prefix, section);
    }

    private List<SetterReturnObject> setIndividualTreatment(Treatment treatment) throws ApolloSetterException {
        List<SetterReturnObject> list = new ArrayList<SetterReturnObject>();
        list.addAll(setValue(INDIVIDUAL_TREATMENT_FIELD, GENERIC_IS_NOT_NULL_LABEL, section));
        // could be different kinds of treatment
        if (treatment instanceof Vaccination) {
            VaccinationSetter setter = new VaccinationSetter(apolloTranslationEngine, type + "." + INDIVIDUAL_TREATMENT_FIELD);
            list.addAll(setter.set((Vaccination) treatment));
        } else if (treatment instanceof AntiviralTreatment) {
            AntiviralTreatmentSetter setter = new AntiviralTreatmentSetter(apolloTranslationEngine, type + "." + INDIVIDUAL_TREATMENT_FIELD);
            list.addAll(setter.set((AntiviralTreatment) treatment));
        } else if (treatment instanceof DrugTreatment) {
			DrugTreatmentSetter setter = new DrugTreatmentSetter(apolloTranslationEngine, type + "." + INDIVIDUAL_TREATMENT_FIELD);
			list.addAll(setter.set((DrugTreatment) treatment));
		}

        return list;
    }

    private List<SetterReturnObject> setPathogen(ApolloPathogenCode code) throws ApolloSetterException {
        ApolloPathogenCodeSetter setter = new ApolloPathogenCodeSetter(apolloTranslationEngine, type + "." + PATHOGEN, section);
        return setter.set(code);
    }

    private List<SetterReturnObject> setCompliance(ProbabilisticParameter compliance) throws ApolloSetterException {
        ProbabilisticParameterSetter setter = new ProbabilisticParameterSetter(apolloTranslationEngine, type + "." + COMPLIANCE, section);
        return setter.set(compliance);
    }

    private List<SetterReturnObject> setDelayFromSymptomsToTreatment(Duration duration) throws ApolloSetterException {
        DurationSetter setter = new DurationSetter(apolloTranslationEngine, type + "." + DELAY_FROM_SYSMPTOMS_TO_TREATMENT, section);
        return setter.set(duration);
    }

    private List<SetterReturnObject> setTargetPopulationsAndPrioritization(ControlMeasureTargetPopulationsAndPrioritization populationsAndPrioritization) throws ApolloSetterException {
        ControlMeasureTargetPopulationsAndPrioritizationSetter setter = new ControlMeasureTargetPopulationsAndPrioritizationSetter(apolloTranslationEngine, type + "." + TARGET_POPULATIONS_AND_PRIORITIZATIONS_FIELD, section);
        return setter.set(populationsAndPrioritization);
    }

    public List<SetterReturnObject> set(IndividualTreatmentControlMeasure strategy) throws ApolloSetterException {

        // we want the section of this strategy to be appropriate for the type
        Treatment treatment = strategy.getIndividualTreatment();
        if (treatment instanceof Vaccination) {
            section = VACCINATION_CONTROL_STRATEGY_SECTION;
        } else if (treatment instanceof AntiviralTreatment) {
            section = ANTIVIRAL_CONTROL_STRATEGY_SECTION;
        } else if (treatment instanceof DrugTreatment) {
			section = DRUG_TREATMENT_CONTROL_STRATEGY_SECTION;
		}

        List<SetterReturnObject> list = new ArrayList<SetterReturnObject>();
        list.addAll(setInfectiousDiseaseControlMeasure(strategy));
        list.addAll(setIndividualTreatment(treatment));
        list.addAll(setTargetPopulationsAndPrioritization(strategy.getTargetPopulationsAndPrioritizations()));
        if (strategy.getDelayFromSymptomsToTreatment() != null) {
            list.addAll(setDelayFromSymptomsToTreatment(strategy.getDelayFromSymptomsToTreatment()));
        }
        list.addAll(setCompliance(strategy.getCompliance()));
        list.addAll(setPathogen(strategy.getPathogen()));
        return list;
    }
}
