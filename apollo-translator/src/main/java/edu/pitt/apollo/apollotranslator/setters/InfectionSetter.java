package edu.pitt.apollo.apollotranslator.setters;

import edu.pitt.apollo.apollotranslator.ApolloTranslationEngine;
import edu.pitt.apollo.apollotranslator.exception.ApolloSetterException;
import edu.pitt.apollo.apollotranslator.types.translator.SetterReturnObject;
import edu.pitt.apollo.types.v4_0_1.ApolloPathogenCode;
import edu.pitt.apollo.types.v4_0_1.Infection;
import edu.pitt.apollo.types.v4_0_1.InfectionAcquisitionFromInfectedHost;
import edu.pitt.apollo.types.v4_0_1.InfectiousDisease;

import java.util.ArrayList;
import java.util.List;

public class InfectionSetter extends AbstractTypedSetter<Infection> {

    public static final String PATHOGEN_TAXON_ID_FIELD = "pathogenTaxonId";
    public static final String HOST_TAXON_ID_FIELD = "hostTaxonId";
    public static final String INFECTIOUSNESS_PROFILE = "infectiousnessProfile";
	public static final String INFECTIOUS_DISEASES = "infectiousDiseases";
    public static final String INFECTION_ACQUISITION_FROM_INFECTIOUS_HOST_FIELD = "infectionAcquisitionsFromInfectedHosts";
    public static final String[] fields = {PATHOGEN_TAXON_ID_FIELD,
        HOST_TAXON_ID_FIELD, INFECTION_ACQUISITION_FROM_INFECTIOUS_HOST_FIELD};

    public InfectionSetter() {
    }

    public InfectionSetter(ApolloTranslationEngine apolloTranslationEngine, String type, String section) {
        super(type, section, apolloTranslationEngine);

    }

    private List<SetterReturnObject> setPathogen(ApolloPathogenCode id)
            throws ApolloSetterException {
        ApolloPathogenCodeSetter setter = new ApolloPathogenCodeSetter(apolloTranslationEngine, type + "." + PATHOGEN_TAXON_ID_FIELD, section);
        return setter.set(id);
    }

    private List<SetterReturnObject> setHostTaxonId(String id)
            throws ApolloSetterException {
        return setValue(HOST_TAXON_ID_FIELD, id, section);

    }

//    private List<SetterReturnObject> setInfection()
//            throws ApolloSetterException {
//        return setValue("", AbstractSetter.GENERIC_IS_NOT_NULL_LABEL, section);
//    }

    private List<SetterReturnObject> setInfectionAcquisitionFromInfectiousHost(List<InfectionAcquisitionFromInfectedHost> acquisitions) throws ApolloSetterException {

        List<SetterReturnObject> sroList;
        if (acquisitions != null && acquisitions.size() > 0) {

            sroList = setValue(INFECTION_ACQUISITION_FROM_INFECTIOUS_HOST_FIELD, "(list values described below)", section);
            ListSetter setter = new ListSetter(InfectionAcquisitionFromInfectiousHostSetter.class, InfectionAcquisitionFromInfectedHost.class,
                    acquisitions, apolloTranslationEngine, section, type + "." + INFECTION_ACQUISITION_FROM_INFECTIOUS_HOST_FIELD);

//            VaccinationEfficacySetter vaccinationEfficacySetter = new VaccinationEfficacySetter(apolloTranslationEngine, type
//                    + "." + VACCINATION_EFFICACIES, section);

            List<SetterReturnObject> result = setter.set();

            sroList.get(0).setSubApolloParameters(result);
        } else {
            sroList = setValue(INFECTION_ACQUISITION_FROM_INFECTIOUS_HOST_FIELD, PARAM_IS_NOT_SET_LABEL, section);
        }

        return sroList;

    }

	    private List<SetterReturnObject> setInfectiousDiseases(List<InfectiousDisease> infectiousDiseases) throws ApolloSetterException {

        List<SetterReturnObject> sroList;
        if (infectiousDiseases != null && infectiousDiseases.size() > 0) {

            sroList = setValue(INFECTIOUS_DISEASES, "(list values described below)", section);
            ListSetter setter = new ListSetter(InfectiousDiseaseSetter.class, InfectiousDisease.class,
                    infectiousDiseases, apolloTranslationEngine, section, type + "." + INFECTIOUS_DISEASES);

//            VaccinationEfficacySetter vaccinationEfficacySetter = new VaccinationEfficacySetter(apolloTranslationEngine, type
//                    + "." + VACCINATION_EFFICACIES, section);

            List<SetterReturnObject> result = setter.set();

            sroList.get(0).setSubApolloParameters(result);
        } else {
            sroList = setValue(INFECTIOUS_DISEASES, PARAM_IS_NOT_SET_LABEL, section);
        }

        return sroList;

    }
	
//    private List<SetterReturnObject> setInfectiousnessProfile(ContinuousParametricProbabilityDistribution dist) throws ApolloSetterException {
//        ContinuousParametricProbabilityDistributionSetter setter = new ContinuousParametricProbabilityDistributionSetter(apolloTranslationEngine, type + "." + INFECTIOUSNESS_PROFILE, section);
//        return setter.set(dist);
//    }

    @Override
    public List<SetterReturnObject> set(Infection infection)
            throws ApolloSetterException {
        List<SetterReturnObject> sroList = new ArrayList<SetterReturnObject>();

//        sroList.addAll(setInfection());
        sroList.addAll(setHostTaxonId(infection.getHost()));
        sroList.addAll(setPathogen(infection.getPathogen()));
		sroList.addAll(setInfectiousDiseases(infection.getInfectiousDiseases()));
        sroList.addAll(setInfectionAcquisitionFromInfectiousHost(infection.getInfectionAcquisitionsFromInfectedHosts()));

        return sroList;

    }
}
