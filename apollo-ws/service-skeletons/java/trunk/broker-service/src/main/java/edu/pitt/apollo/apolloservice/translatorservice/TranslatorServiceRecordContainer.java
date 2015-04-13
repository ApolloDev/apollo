package edu.pitt.apollo.apolloservice.translatorservice;

import edu.pitt.apollo.db.ApolloDbUtils;
import edu.pitt.apollo.db.exceptions.ApolloDatabaseException;
import edu.pitt.apollo.services_common.v3_0_0.ServiceRegistrationRecord;
import edu.pitt.apollo.services_common.v3_0_0.SoftwareIdentification;

import java.util.Map;

/**
 *
 * Author: Nick Millett
 * Email: nick.millett@gmail.com
 * Date: May 9, 2014
 * Time: 10:49:49 AM
 * Class: TranslatorServiceRecordContainer
 * IDE: NetBeans 6.9.1
 */
public class TranslatorServiceRecordContainer {

	private static final ServiceRegistrationRecord TRANSLATOR_SERVICE_RECORD;

	static {

		ServiceRegistrationRecord tempTranslatorServiceRecord = null;
		try (ApolloDbUtils dbUtils = new ApolloDbUtils()){
			Map<Integer, ServiceRegistrationRecord> softwareIdMap = dbUtils.getRegisteredSoftware();
			for (Integer id : softwareIdMap.keySet()) {
				SoftwareIdentification softwareId = softwareIdMap.get(id).getSoftwareIdentification();
				if (softwareId.getSoftwareName().toLowerCase().equals("translator")) {
					tempTranslatorServiceRecord = softwareIdMap.get(id);
					break;
				}
			}
		} catch (ApolloDatabaseException ex) {
			throw new ExceptionInInitializerError("ApolloDatabaseException attempting to load the translator service record: "
					+ ex.getMessage());
		}

		if (tempTranslatorServiceRecord == null) {
			throw new ExceptionInInitializerError("Translator ServiceRegistrationRecord object could not be initialized");
		}

		TRANSLATOR_SERVICE_RECORD = tempTranslatorServiceRecord;

	}

	public static SoftwareIdentification getTranslatorSoftwareIdentification() {
		return TRANSLATOR_SERVICE_RECORD.getSoftwareIdentification();
	}

	public static ServiceRegistrationRecord getTranslatorServiceRegistrationRecord() {
		return TRANSLATOR_SERVICE_RECORD;
	}
}
