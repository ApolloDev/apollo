/**
 * 
 */
package edu.pitt.apollo.apollotranslator.setters;

import java.util.List;

import edu.pitt.apollo.apollotranslator.ApolloTranslationEngine;
import edu.pitt.apollo.apollotranslator.exception.ApolloSetterException;
import edu.pitt.apollo.apollotranslator.types.translator.SetterReturnObject;

/**
 * .simulatorIdentification.softwareDeveloper
 * .simulatorIdentification.softwareName
 * .simulatorIdentification.softwareVersion
 * .simulatorIdentification.softwareType
 * 
 */
public class SimulatorIdentificationSetter extends AbstractSetter {

        private static final String SECTION = "SIMULATOR IDENTIFICATION";
	public static final String SOFTWARE_DEVELOPER__FIELD = "simulatorIdentification.softwareDeveloper";
	public static final String SOFTWARE_NAME_FIELD = "simulatorIdentification.softwareName";
	public static final String SOFTWARE_VERSION_FIELD = "simulatorIdentification.softwareVersion";
	public static final String SOFTWARE_TYPE_FIELD = "simulatorIdentification.softwareType";

	/**
	 * @param apolloTranslationEngine
	 */
	public SimulatorIdentificationSetter(
			ApolloTranslationEngine apolloTranslationEngine) {
		super(apolloTranslationEngine);
	}

	public List<SetterReturnObject> setSoftwareDeveloper(String value) throws ApolloSetterException {
		return setValue(SOFTWARE_DEVELOPER__FIELD, value, SECTION);
	};

	public List<SetterReturnObject> setSoftwareName(String value) throws ApolloSetterException {
		return setValue(SOFTWARE_NAME_FIELD, value, SECTION);
	};

	public List<SetterReturnObject> setSoftwareVersion(String value) throws ApolloSetterException {
		return setValue(SOFTWARE_VERSION_FIELD, value, SECTION);
	};

	public List<SetterReturnObject> setSoftwareType(String value) throws ApolloSetterException {
		return setValue(SOFTWARE_TYPE_FIELD, value, SECTION);
	};

}
