package edu.pitt.apollo.apollotranslator.setters;

import edu.pitt.apollo.apollotranslator.ApolloTranslationEngine;
import edu.pitt.apollo.apollotranslator.exception.ApolloSetterException;
import edu.pitt.apollo.apollotranslator.types.translator.SetterReturnObject;
import edu.pitt.apollo.types.v4_0_1.Distance;
import edu.pitt.apollo.types.v4_0_1.IndoorResidualSprayingVectorControlMeasure;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * Author: Nick Millett
 Email: nick.millett@gmail.com
 Date: Sep 5, 2014
 Time: 5:34:47 PM
 Class: IndoorResidualSprayingVectorControlMeasureSetter
 */
public class IndoorResidualSprayingVectorControlMeasureSetter extends AbstractTypedSetter<IndoorResidualSprayingVectorControlMeasure> {

	public static final String SECTION = "INDOOR RESIDUAL SPRAYING VECTOR CONTROL STRATEGY";
	private static final String COVER_RADIUS = "coverRadius";
	private static final String FRACTION_OF_VECTOR_INDIVIDUALS_AFFECTED = "fractionOfVectorIndividualsAffected";

	public IndoorResidualSprayingVectorControlMeasureSetter() {

	}

	public IndoorResidualSprayingVectorControlMeasureSetter(ApolloTranslationEngine apolloTranslationEngine, String prefix, String section) {
		super(prefix, section, apolloTranslationEngine);
	}

	private List<SetterReturnObject> setCoverRadius(Distance radius) throws ApolloSetterException {
		DistanceSetter setter = new DistanceSetter(apolloTranslationEngine, type + "." + COVER_RADIUS, section);
		return setter.set(radius);
	}

	private List<SetterReturnObject> setFractionOfAdultsAffected(double fraction) throws ApolloSetterException {
		return setValue(FRACTION_OF_VECTOR_INDIVIDUALS_AFFECTED, Double.toString(fraction), section);
	}

	@Override
	public List<SetterReturnObject> set(IndoorResidualSprayingVectorControlMeasure t) throws ApolloSetterException {
		List<SetterReturnObject> list = new ArrayList<SetterReturnObject>();
        if (t.getCoverRadius() != null) {
            list.addAll(setCoverRadius(t.getCoverRadius()));
        }
		list.addAll(setFractionOfAdultsAffected(t.getFractionOfVectorIndividualsAffected()));

		return list;

	}

}
