package edu.pitt.apollo.apollotranslator.setters;

import edu.pitt.apollo.apollotranslator.ApolloTranslationEngine;
import edu.pitt.apollo.apollotranslator.exception.ApolloSetterException;
import edu.pitt.apollo.apollotranslator.types.translator.SetterReturnObject;
import edu.pitt.apollo.types.v4_0.Behavior;
import edu.pitt.apollo.types.v4_0.HumanBehavior;
import edu.pitt.apollo.types.v4_0.MosquitoBehavior;

import java.util.List;

/**
 *
 * Author: Nick Millett
 * Email: nick.millett@gmail.com
 * Date: Sep 2, 2014
 * Time: 1:57:10 PM
 * Class: BehaviorSetter
 */
public class BehaviorSetter extends AbstractTypedSetter<Behavior> {

	public static final String SECTION = "INDIVIDUAL BEHAVIORS";

	public BehaviorSetter() {

	}

	public BehaviorSetter(ApolloTranslationEngine apolloTranslationEngine, String prefix, String section) {
		super(prefix, section, apolloTranslationEngine);
	}

	@Override
	public List<SetterReturnObject> set(Behavior behavior) throws ApolloSetterException {
		AbstractTypedSetter setter;
		if (behavior instanceof HumanBehavior) {
			setter = new HumanBehaviorSetter(apolloTranslationEngine, type, section);
		} else if (behavior instanceof MosquitoBehavior) {
			setter = new MosquitoBehaviorSetter(apolloTranslationEngine, type, section);
		} else {
			throw new ApolloSetterException("Unrecognized individual behavior type in BehaviorSetter");
		}

		return setter.set(behavior);
	}

}
