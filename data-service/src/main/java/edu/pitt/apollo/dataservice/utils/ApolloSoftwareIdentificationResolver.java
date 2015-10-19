package edu.pitt.apollo.dataservice.utils;

import edu.pitt.apollo.db.ApolloDbUtils;
import edu.pitt.apollo.db.exceptions.ApolloDatabaseException;
import edu.pitt.apollo.services_common.v3_0_2.ServiceRegistrationRecord;
import edu.pitt.apollo.types.v3_0_2.SoftwareIdentification;

import java.util.Map;

public class ApolloSoftwareIdentificationResolver {

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