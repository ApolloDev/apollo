package edu.pitt.apollo.apolloservice.methods.content;

import edu.pitt.apollo.apolloservice.translatorservice.TranslatorServiceRecordContainer;
import edu.pitt.apollo.db.ApolloDbUtils;
import edu.pitt.apollo.db.exceptions.ApolloDatabaseException;
import edu.pitt.apollo.services_common.v3_0_0.MethodCallStatus;
import edu.pitt.apollo.services_common.v3_0_0.MethodCallStatusEnum;
import edu.pitt.apollo.simulator_service_types.v3_0_0.GetConfigurationFileForSimulationResult;

import java.io.ByteArrayOutputStream;
import java.math.BigInteger;
import java.util.Map;

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

        GetConfigurationFileForSimulationResult result = new GetConfigurationFileForSimulationResult();
        MethodCallStatus status = new MethodCallStatus();
        result.setMethodCallStatus(status);

        
        Map<String, ByteArrayOutputStream> map;
        try (ApolloDbUtils dbUtils = new ApolloDbUtils()){
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
