package edu.pitt.apollo.apollotranslator.setters;

import edu.pitt.apollo.apollotranslator.ApolloTranslationEngine;
import edu.pitt.apollo.apollotranslator.exception.ApolloSetterException;
import edu.pitt.apollo.apollotranslator.types.translator.SetterReturnObject;
import edu.pitt.apollo.types.v4_0_1.ClaraDensityDependentMortalityFunction;
import edu.pitt.apollo.types.v4_0_1.MortalityFunction;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * Author: Nick Millett
 * Email: nick.millett@gmail.com
 * Date: Sep 3, 2014
 * Time: 4:03:15 PM
 * Class: MortalityFunctionSetter
 */
public class MortalityFunctionSetter<T extends MortalityFunction> extends AbstractTypedSetter<T> {

	public MortalityFunctionSetter() {

	}

	public MortalityFunctionSetter(ApolloTranslationEngine apolloTranslationEngine, String prefix, String section) {
		super(prefix, section, apolloTranslationEngine);
	}

	@Override
	public List<SetterReturnObject> set(T t) throws ApolloSetterException {

		List<SetterReturnObject> list = new ArrayList<SetterReturnObject>();
		if (t instanceof ClaraDensityDependentMortalityFunction) {
			ClaraDensityDependentMortalityFunctionSetter setter = new ClaraDensityDependentMortalityFunctionSetter(apolloTranslationEngine, type, section);
			list.addAll(setter.set((ClaraDensityDependentMortalityFunction) t));
		} else {
			throw new ApolloSetterException("Type of MortalityFunction not recognized");
		}

		return list;
	}

}
