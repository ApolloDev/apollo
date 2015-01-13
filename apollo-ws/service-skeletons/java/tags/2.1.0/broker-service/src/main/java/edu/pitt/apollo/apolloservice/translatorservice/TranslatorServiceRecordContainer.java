package edu.pitt.apollo.apolloservice.translatorservice;

import edu.pitt.apollo.apolloservice.database.ApolloDbUtilsContainer;
import edu.pitt.apollo.db.ApolloDbUtils;
import edu.pitt.apollo.services_common.v2_1_0.ServiceRegistrationRecord;
import edu.pitt.apollo.services_common.v2_1_0.SoftwareIdentification;
import java.sql.SQLException;
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
        ApolloDbUtils dbUtils = ApolloDbUtilsContainer.getApolloDbUtils();

        ServiceRegistrationRecord tempTranslatorServiceRecord = null;
        try {
            Map<Integer, ServiceRegistrationRecord> softwareIdMap = dbUtils.getRegisteredSoftware();
            for (Integer id : softwareIdMap.keySet()) {
                SoftwareIdentification softwareId = softwareIdMap.get(id).getSoftwareIdentification();
                if (softwareId.getSoftwareName().toLowerCase().equals("translator")) {
                    tempTranslatorServiceRecord = softwareIdMap.get(id);
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            throw new ExceptionInInitializerError("ClassNotFoundException attempting to load the translator service record: "
                    + ex.getMessage());
        } catch (SQLException ex) {
            throw new ExceptionInInitializerError("SQLException attempting to load the translator service record: "
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
