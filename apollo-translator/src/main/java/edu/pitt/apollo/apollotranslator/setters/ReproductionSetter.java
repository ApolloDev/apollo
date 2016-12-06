package edu.pitt.apollo.apollotranslator.setters;

import edu.pitt.apollo.apollotranslator.ApolloTranslationEngine;
import edu.pitt.apollo.apollotranslator.exception.ApolloSetterException;
import edu.pitt.apollo.apollotranslator.types.translator.SetterReturnObject;
import edu.pitt.apollo.types.v4_0_1.MosquitoReproduction;
import edu.pitt.apollo.types.v4_0_1.Reproduction;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * Author: Nick Millett
 * Email: nick.millett@gmail.com
 * Date: Sep 2, 2014
 * Time: 1:57:10 PM
 * Class: BehaviorSetter
 */
public class ReproductionSetter extends AbstractTypedSetter<Reproduction> {

	//public static final String SECTION = "REPRODUCTIONS";

	public ReproductionSetter() {

	}

	public ReproductionSetter(ApolloTranslationEngine apolloTranslationEngine, String prefix, String section) {
		super(prefix, section, apolloTranslationEngine);
	}


	@Override
	public List<SetterReturnObject> set(Reproduction reproduction) throws ApolloSetterException {
		AbstractTypedSetter setter;
        List<SetterReturnObject> list = new ArrayList<>();
		if (reproduction instanceof MosquitoReproduction) {
			setter = new MosquitoReproductionSetter(apolloTranslationEngine, type, section);
		} else {
			throw new ApolloSetterException("Unrecognized reproduction type type in ReproductionSetter");
		}

        list.addAll(setter.set(reproduction));
		return list;
	}

}
