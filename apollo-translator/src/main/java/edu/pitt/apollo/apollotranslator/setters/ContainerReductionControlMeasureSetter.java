package edu.pitt.apollo.apollotranslator.setters;

import edu.pitt.apollo.apollotranslator.ApolloTranslationEngine;
import edu.pitt.apollo.apollotranslator.exception.ApolloSetterException;
import edu.pitt.apollo.apollotranslator.types.translator.SetterReturnObject;
import edu.pitt.apollo.types.v4_0.ContainerReductionControlMeasure;
import edu.pitt.apollo.types.v4_0.Distance;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * Author: Nick Millett
 * Email: nick.millett@gmail.com
 * Date: Sep 5, 2014
 * Time: 6:23:46 PM
 * Class: ContainerReductionControlMeasureSetter
 */
public class ContainerReductionControlMeasureSetter extends AbstractTypedSetter<ContainerReductionControlMeasure> {

	public static final String SECTION = "CONTAINER REDUCTION CONTROL STRATEGY";
	private static final String COVER_RADIUS = "coverRadius";
	private static final String FRACTION_REDUCTION_OF_EGGS = "fractionReductionOfEggs";
	private static final String FRACTION_REDUCTION_OF_LARVAE = "fractionReductionOfLarvae";

	public ContainerReductionControlMeasureSetter() {

	}

	public ContainerReductionControlMeasureSetter(ApolloTranslationEngine apolloTranslationEngine, String prefix, String section) {
		super(prefix, section, apolloTranslationEngine);;
	}

	private List<SetterReturnObject> setCoverRadius(Distance radius) throws ApolloSetterException {
		DistanceSetter setter = new DistanceSetter(apolloTranslationEngine, type + "." + COVER_RADIUS, section);
		return setter.set(radius);
	}

	private List<SetterReturnObject> setFractionReductionOfEggs(double fraction) throws ApolloSetterException {
		return setValue(FRACTION_REDUCTION_OF_EGGS, Double.toString(fraction), section);
	}

	private List<SetterReturnObject> setFractionReductionOfLarvae(double fraction) throws ApolloSetterException {
		return setValue(FRACTION_REDUCTION_OF_LARVAE, Double.toString(fraction), section);
	}

	@Override
	public List<SetterReturnObject> set(ContainerReductionControlMeasure t) throws ApolloSetterException {

		List<SetterReturnObject> list = new ArrayList<SetterReturnObject>();
		list.addAll(setCoverRadius(t.getCoverRadius()));
		list.addAll(setFractionReductionOfEggs(t.getFractionReductionOfEggs()));
		list.addAll(setFractionReductionOfLarvae(t.getFractionReductionOfLarvae()));

		return list;
	}

}
