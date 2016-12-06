package edu.pitt.apollo.apollotranslator.setters;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

import edu.pitt.apollo.apollotranslator.ApolloTranslationEngine;
import edu.pitt.apollo.apollotranslator.exception.ApolloSetterException;
import edu.pitt.apollo.apollotranslator.types.translator.SetterReturnObject;
import edu.pitt.apollo.types.v4_0_1.ApolloPathogenCode;
import edu.pitt.apollo.types.v4_0_1.Duration;
import edu.pitt.apollo.types.v4_0_1.Treatment;
import edu.pitt.apollo.types.v4_0_1.TreatmentContraindication;

/**
 *
 * Author: Nick Millett
 * Email: nick.millett@gmail.com
 * Date: Sep 18, 2013
 * Time: 12:59:48 PM
 * Class: TreatmentSetter
 * IDE: NetBeans 6.9.1
 */
public abstract class TreatmentSetter<T extends Treatment> extends AbstractTypedSetter<T> {

    private static final String TREATMENT_DESCRIPTION = "description";
    private static final String SPECIES_OF_TREATED_ORGANISMS = "speciesOfTreatedOrganism";
    private static final String NUM_DOSES_IN_TREATMENT_COURSE = "numDosesInTreatmentCourse";
    private static final String TREATMENT_CONTRAINDICATIONS = "treatmentContraindications";
//    private static final String TREATMENT_EFFICACIES = "treatmentEfficacies";
    private static final String PATHOGEN = "pathogen";
//    private static final String VACCINE_ID = "vaccineId";
//    private static final String DRUG_ID = "drugId";
    private static final String DURATION_OF_TREATMENT_COURSE = "durationOfTreatmentCourse";
//    String type, section;

    public TreatmentSetter(String type, String section, ApolloTranslationEngine apolloTranslatorEngine) {
        super(type, section, apolloTranslatorEngine);
//        this.type = type;
//        this.section = section;
    }

//    @Override
//    protected List<SetterReturnObject> setValue(String fieldName, String fieldValue, String section) throws ApolloSetterException {
//        String newType;
//        if (fieldName.isEmpty()) {
//            newType = type;
//        } else {
//            newType = type + "." + fieldName;
//        }
//        return super.setValue(newType, fieldValue, section);
//    }

    private List<SetterReturnObject> setTreatmentDescription(String description) throws ApolloSetterException {
        return setValue(TREATMENT_DESCRIPTION, description, section);
    }

    private List<SetterReturnObject> setTreatmentSpeciesOfTreatedOrganisms(String id) throws ApolloSetterException {
        return setValue(SPECIES_OF_TREATED_ORGANISMS, id, section);
    }

    private List<SetterReturnObject> setTreatmentNumDosesInTreatmentCourse(BigInteger numDoses) throws ApolloSetterException {
        return setValue(NUM_DOSES_IN_TREATMENT_COURSE, numDoses.toString(), section);
    }

    private List<SetterReturnObject> setPathogen(ApolloPathogenCode code) throws ApolloSetterException {
        ApolloPathogenCodeSetter setter = new ApolloPathogenCodeSetter(apolloTranslationEngine, type + "." + PATHOGEN, section);
        return setter.set(code);
    }

//    private List<SetterReturnObject> setVaccineId(String id) throws ApolloSetterException {
//        return setValue(VACCINE_ID, id, section);
//    }
//
//    private List<SetterReturnObject> setDrugId(String id) throws ApolloSetterException {
//        return setValue(DRUG_ID, id, section);
//    }

    private List<SetterReturnObject> setDurationOfTreatmentCourse(Duration duration) throws ApolloSetterException {
        DurationSetter setter = new DurationSetter(apolloTranslationEngine, type + "." + DURATION_OF_TREATMENT_COURSE, section);
        return setter.set(duration);
    }

    private List<SetterReturnObject> setTreatmentContraindications(List<TreatmentContraindication> contraindications) throws ApolloSetterException {
        List<SetterReturnObject> sroList;
        if (contraindications != null && contraindications.size() > 0) {

            sroList = setValue(TREATMENT_CONTRAINDICATIONS, "(list values described below)", section);
            ListSetter setter = new ListSetter(TreatmentContraindicationSetter.class, TreatmentContraindication.class,
                    contraindications, apolloTranslationEngine, section, type + "." + TREATMENT_CONTRAINDICATIONS);

//            VaccinationEfficacyForSimulatorConfigurationSetter vaccinationEfficacySetter = new VaccinationEfficacyForSimulatorConfigurationSetter(apolloTranslationEngine, type
//                    + "." + VACCINATION_EFFICACIES, section);

            List<SetterReturnObject> result = setter.set();

            sroList.get(0).setSubApolloParameters(result);
        } else {
            sroList = setValue(TREATMENT_CONTRAINDICATIONS, PARAM_IS_NOT_SET_LABEL, section);
        }

        return sroList;
    }

//    private List<SetterReturnObject> setVaccinationEfficacies(List<TreatmentEfficacy> efficacies) throws ApolloSetterException {
//        ListSetter setter = new ListSetter(VaccinationEfficacyForSimulatorConfigurationSetter.class,
//                VaccinationEfficacyForSimulatorConfiguration.class,
//                efficacies, apolloTranslationEngine, section, type + "." + TREATMENT_EFFICACIES);
//
//        return setter.set();
//    }
//
//    private List<SetterReturnObject> setAntiviralTreatmentEfficacies(List<TreatmentEfficacy> efficacies) throws ApolloSetterException {
//        ListSetter setter = new ListSetter(AntiviralTreatmentEfficacySetter.class,
//                AntiviralTreatmentEfficacy.class,
//                efficacies, apolloTranslationEngine, section, type + "." + TREATMENT_EFFICACIES);
//
//        return setter.set();
//    }
//
//    private List<SetterReturnObject> setTreatmentEfficacies(List<TreatmentEfficacy> efficacies) throws ApolloSetterException {
//
//        List<SetterReturnObject> sroList = new ArrayList<SetterReturnObject>();
//        sroList.addAll(setVaccinationEfficacies(efficacies));
//        sroList.addAll(setAntiviralTreatmentEfficacies(efficacies));
//
//        return sroList;
//    }

    public List<SetterReturnObject> setTreatment(T treatment) throws ApolloSetterException {
        List<SetterReturnObject> list = new ArrayList<SetterReturnObject>();


        list.addAll(setTreatmentDescription(treatment.getDescription()));
        list.addAll(setTreatmentNumDosesInTreatmentCourse(treatment.getNumDosesInTreatmentCourse()));
        list.addAll(setTreatmentSpeciesOfTreatedOrganisms(treatment.getSpeciesOfTreatedOrganism()));
        list.addAll(setTreatmentContraindications(treatment.getTreatmentContraindications()));
        list.addAll(setPathogen(treatment.getPathogen()));
//        if (treatment.getVaccineId() != null) {
//            list.addAll(setVaccineId(treatment.getVaccineId()));
//        }
//
//        if (treatment.getDrugId() != null) {
//            list.addAll(setDrugId(treatment.getDrugId()));
//        }

        list.addAll(setDurationOfTreatmentCourse(treatment.getDurationOfTreatmentCourse()));
//        list.addAll(setTreatmentEfficacies(treatment.getTreatmentEfficacies()));

        return list;

    }
}
