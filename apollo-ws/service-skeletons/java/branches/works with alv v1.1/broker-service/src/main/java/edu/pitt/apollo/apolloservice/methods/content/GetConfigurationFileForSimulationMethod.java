package edu.pitt.apollo.apolloservice.methods.content;

import java.io.ByteArrayOutputStream;
import java.math.BigInteger;
import java.util.Map;

import edu.pitt.apollo.apolloservice.database.ApolloDbUtilsContainer;
import edu.pitt.apollo.apolloservice.translatorservice.TranslatorServiceRecordContainer;
import edu.pitt.apollo.db.ApolloDatabaseException;
import edu.pitt.apollo.db.ApolloDbUtils;
import edu.pitt.apollo.types.v2_1_0.GetConfigurationFileForSimulationResult;
import edu.pitt.apollo.types.v2_1_0.MethodCallStatus;
import edu.pitt.apollo.types.v2_1_0.MethodCallStatusEnum;

/**
 *
 * Author: Nick Millett
 * Email: nick.millett@gmail.com
 * Date: May 9, 2014
 * Time: 1:52:25 PM
 * Class: GetConfigurationFileForSimulationMethod
 * IDE: NetBeans 6.9.1
 */
public class GetConfigurationFileForSimulationMethod {

    public static GetConfigurationFileForSimulationResult getConfigurationFileForSimulation(BigInteger runIdentification) {
        
        ApolloDbUtils dbUtils = ApolloDbUtilsContainer.getApolloDbUtils();
        GetConfigurationFileForSimulationResult result = new GetConfigurationFileForSimulationResult();
        MethodCallStatus status = new MethodCallStatus();
        result.setMethodCallStatus(status);

        
        Map<String, ByteArrayOutputStream> map;
        try {
            map = dbUtils.getConfigFilesForSimulation(
            		runIdentification,
                    dbUtils.getSoftwareIdentificationKey(TranslatorServiceRecordContainer.getTranslatorSoftwareIdentification()));
        } catch (ApolloDatabaseException ex) {
            status.setStatus(MethodCallStatusEnum.FAILED);
            status.setMessage(ex.getMessage());
            return result;
        }

        for (String label : map.keySet()) {
            ByteArrayOutputStream stream = map.get(label);
            if (label.equals("config.txt")) {
                String content = stream.toString();
                result.setConfigurationFile(content);
            } else if (label.equals("verbose.html")) {
                String content = stream.toString();
                result.setConfigurationFileInHtmlFormat(content);
            }
        }

        status.setStatus(MethodCallStatusEnum.COMPLETED);

        return result;
    }
}
