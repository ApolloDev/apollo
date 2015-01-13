package edu.pitt.apollo.apolloservice.methods.content;

import java.io.ByteArrayOutputStream;
import java.math.BigInteger;
import java.util.Map;

import edu.pitt.apollo.apolloservice.database.ApolloDbUtilsContainer;
import edu.pitt.apollo.db.ApolloDbUtils;
import edu.pitt.apollo.db.exceptions.ApolloDatabaseException;
import edu.pitt.apollo.services_common.v3_0_0.MethodCallStatus;
import edu.pitt.apollo.services_common.v3_0_0.MethodCallStatusEnum;
import edu.pitt.apollo.services_common.v3_0_0.UrlOutputResource;
import edu.pitt.apollo.visualizer_service_types.v3_0_0.GetVisualizerOutputResourcesResult;

/**
 *
 * Author: Nick Millett
 * Email: nick.millett@gmail.com
 * Date: May 9, 2014
 * Time: 2:05:11 PM
 * Class: GetVisualizerOutputResourcesMethod
 * IDE: NetBeans 6.9.1
 */
public class GetVisualizerOutputResourcesMethod {

    public static GetVisualizerOutputResourcesResult getVisualizerOutputResources(BigInteger runIdentification) {

        GetVisualizerOutputResourcesResult result = new GetVisualizerOutputResourcesResult();
        MethodCallStatus status = new MethodCallStatus();
        result.setMethodCallStatus(status);

        try {
            ApolloDbUtils dbUtils = ApolloDbUtilsContainer.getApolloDbUtils();
            Map<String, ByteArrayOutputStream> map = dbUtils.getDataContentForSoftware(runIdentification,
                    dbUtils.getSoftwareIdentificationKeyForRun(runIdentification), 0);

            if (map.isEmpty()) {
                status.setStatus(MethodCallStatusEnum.FAILED);
                status.setMessage("There were no visualizer resources available for run "
                        + runIdentification);
                return result;
            }

            for (String label : map.keySet()) {
                UrlOutputResource resource = new UrlOutputResource();
                resource.setDescription(label);
                resource.setURL(map.get(label).toString());
                result.getUrlOutputResources().add(resource);
            }

            status.setStatus(MethodCallStatusEnum.COMPLETED);
            status.setMessage("The resources are available");
            return result;

        } catch (ApolloDatabaseException ex) {
            status.setMessage(ex.getMessage());
            status.setStatus(MethodCallStatusEnum.FAILED);
            return result;
        }
    }
}
