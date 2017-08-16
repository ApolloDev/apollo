package edu.pitt.apollo.apollotranslator.setters;

import edu.pitt.apollo.apollotranslator.ApolloTranslationEngine;
import edu.pitt.apollo.apollotranslator.exception.ApolloSetterException;
import edu.pitt.apollo.apollotranslator.types.translator.SetterReturnObject;
import edu.pitt.apollo.types.v4_0_2.*;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * Author: Nick Millett
 * Email: nick.millett@gmail.com
 * Date: Mar 24, 2014
 * Time: 11:43:42 AM
 * Class: InfectionAcquisitionFromInfectiousHostSetter
 * IDE: NetBeans 6.9.1
 */
public class InfectionAcquisitionFromInfectiousHostSetter extends AbstractTypedSetter<InfectionAcquisitionFromInfectedHost> {

    private static final String INFECTIOUS_HOST_TAXON_ID = "infectiousHostTaxonId";
    public static final String INFECTIOUS_PERIOD_FIELD = "infectiousPeriodDuration";
    public static final String LATENT_PERIOD_FIELD = "latentPeriodDuration";
    private static final String BASIC_REPRODUCTION_NUMBERS = "basicReproductionNumbers";
    private static final String TRANSMISSION_PROBABILITIES = "transmissionProbabilities";
    private static final String INNOCULATION_RATE = "innoculationRate";
    private static final String BETA = "beta";

    public InfectionAcquisitionFromInfectiousHostSetter() {
    }

    public InfectionAcquisitionFromInfectiousHostSetter(ApolloTranslationEngine apolloTranslationEngine, String type, String section) {
        super(type, section, apolloTranslationEngine);
    }

    private List<SetterReturnObject> setInfectiousHostTaxonId(String id) throws ApolloSetterException {
        return setValue(INFECTIOUS_HOST_TAXON_ID, id, section);
    }

    private List<SetterReturnObject> setInfectiousPeriod(Duration duration)
            throws ApolloSetterException {
        DurationSetter setter = new DurationSetter(apolloTranslationEngine, type + "." + INFECTIOUS_PERIOD_FIELD, section);
        return setter.set(duration);

    }

    private List<SetterReturnObject> setLatentPeriod(Duration duration)
            throws ApolloSetterException {
        DurationSetter setter = new DurationSetter(apolloTranslationEngine, type + "." + LATENT_PERIOD_FIELD, section);
        return setter.set(duration);

    }

    private List<SetterReturnObject> setBasicReproductionNumbers(List<ReproductionNumber> numbers) throws ApolloSetterException {
		List<SetterReturnObject> list = new ArrayList<SetterReturnObject>();
            list.addAll(setValue(BASIC_REPRODUCTION_NUMBERS, "(list values described below)", section));
		
		ListSetter setter = new ListSetter(ReproductionNumberSetter.class, 
				ReproductionNumber.class, numbers, apolloTranslationEngine, section, type + "." + BASIC_REPRODUCTION_NUMBERS);
		
		list.get(0).setSubApolloParameters(setter.set());
		return list;
    }

    private List<SetterReturnObject> setBeta(Rate rate) throws ApolloSetterException {
        RateSetter setter = new RateSetter(apolloTranslationEngine, type + "." + BETA, section);
        return setter.set(rate);
    }

    private List<SetterReturnObject> setInnoculationRate(Rate rate) throws ApolloSetterException {
        RateSetter setter = new RateSetter(apolloTranslationEngine, type + "." + INNOCULATION_RATE, section);
        return setter.set(rate);
    }

	private List<SetterReturnObject> setTransmissionProbability(List<TransmissionProbability> probabilities) throws ApolloSetterException {
        List<SetterReturnObject> list = new ArrayList<SetterReturnObject>();
        list.addAll(setValue(TRANSMISSION_PROBABILITIES, "(list values described below)", section));

        ListSetter setter = new ListSetter(TransmissionProbabilitySetter.class,
                TransmissionProbability.class, probabilities, apolloTranslationEngine, section, type + "." + TRANSMISSION_PROBABILITIES);

        list.get(0).setSubApolloParameters(setter.set());
        return list;
	}

    @Override
    public List<SetterReturnObject> set(InfectionAcquisitionFromInfectedHost t) throws ApolloSetterException {
        List<SetterReturnObject> list = new ArrayList<SetterReturnObject>();
        list.addAll(setInfectiousHostTaxonId(t.getInfectedHost()));
        list.addAll(setInfectiousPeriod(t.getInfectiousPeriodDuration()));
        list.addAll(setLatentPeriod(t.getLatentPeriodDuration()));
        if (t.getBasicReproductionNumbers() != null && t.getBasicReproductionNumbers().size() > 0) {
            list.addAll(setBasicReproductionNumbers(t.getBasicReproductionNumbers()));
        } else if (t.getBeta() != null) {
            list.addAll(setBeta(t.getBeta()));
        } else if (t.getTransmissionProbabilities().size() > 0) {
			list.addAll(setTransmissionProbability(t.getTransmissionProbabilities()));
		} else if (t.getInoculationRate()!= null) {
            list.addAll(setInnoculationRate(t.getInoculationRate()));
        }
        return list;
    }
}
