package edu.pitt.apollo.apollotranslator.setters;

import edu.pitt.apollo.apollotranslator.ApolloTranslationEngine;
import edu.pitt.apollo.apollotranslator.exception.ApolloSetterException;
import edu.pitt.apollo.apollotranslator.types.translator.SetterReturnObject;
import edu.pitt.apollo.types.v4_0.ClaraDensityDependentMortalityFunction;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * Author: Nick Millett
 Email: nick.millett@gmail.com
 Date: Sep 3, 2014
 Time: 4:04:48 PM
 Class: ClaraDensityDependentMortalityFunctionSetter
 */
public class ClaraDensityDependentMortalityFunctionSetter extends MortalityFunctionSetter<ClaraDensityDependentMortalityFunction> {

	private static final String DESCRIPTION = "description";
	private static final String OMEGA = "omega";
	private static final String SIGMA = "sigma";
	
	public ClaraDensityDependentMortalityFunctionSetter() {
		
	}
	
	public ClaraDensityDependentMortalityFunctionSetter(ApolloTranslationEngine apolloTranslationEngine, String prefix, String section) {
		super(apolloTranslationEngine, prefix, section);
	}
	
	private List<SetterReturnObject> setDescription(String description) throws ApolloSetterException {
		return setValue(DESCRIPTION, description, section);
	}
	
	private List<SetterReturnObject> setOmega(Double theta) throws ApolloSetterException {
		return setValue(OMEGA, theta.toString(), section);
	}
	
	private List<SetterReturnObject> setSigma(Double sigma) throws ApolloSetterException {
		return setValue(SIGMA, sigma.toString(), section);
	}
	
	@Override
	public List<SetterReturnObject> set(ClaraDensityDependentMortalityFunction t) throws ApolloSetterException {
		List<SetterReturnObject> list = new ArrayList<SetterReturnObject>();
		if (t.getDescription() != null) {
			list.addAll(setDescription(t.getDescription()));
		}
		list.addAll(setOmega(t.getOmega()));
		list.addAll(setSigma(t.getSigma()));
		return list;
	}

	
	
}
