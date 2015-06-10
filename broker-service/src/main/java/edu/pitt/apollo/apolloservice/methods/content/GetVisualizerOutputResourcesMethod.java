package edu.pitt.apollo.apolloservice.methods.content;

import edu.pitt.apollo.exception.DataServiceException;
import edu.pitt.apollo.services_common.v3_0_0.Authentication;
import edu.pitt.apollo.services_common.v3_0_0.FileAndURLDescription;
import edu.pitt.apollo.services_common.v3_0_0.MethodCallStatus;
import edu.pitt.apollo.services_common.v3_0_0.MethodCallStatusEnum;
import edu.pitt.apollo.services_common.v3_0_0.UrlOutputResource;
import edu.pitt.apollo.visualizer_service_types.v3_0_0.GetVisualizerOutputResourcesResult;

import java.math.BigInteger;
import java.util.Map;

/**
 * Author: Nick Millett Email: nick.millett@gmail.com Date: May 9, 2014 Time: 2:05:11 PM Class: GetVisualizerOutputResourcesMethod IDE: NetBeans 6.9.1
 */
public class GetVisualizerOutputResourcesMethod extends BaseDataServiceAccessorMethod {

	public GetVisualizerOutputResourcesMethod(String dataServiceUrl) {
		super(dataServiceUrl);
	}

	public GetVisualizerOutputResourcesResult getVisualizerOutputResources(BigInteger runId, Authentication authentication) {

		GetVisualizerOutputResourcesResult result = new GetVisualizerOutputResourcesResult();
		MethodCallStatus status;

		try {
			Map<BigInteger, FileAndURLDescription> urlsMap = connector.getListOfURLsForRunId(runId, authentication);

			for (BigInteger urlId : urlsMap.keySet()) {

				FileAndURLDescription description = urlsMap.get(urlId);
				UrlOutputResource resource = new UrlOutputResource();

				String url = connector.getContentForContentId(urlId, authentication);
				resource.setURL(url);
				resource.setDescription(description.getName());
				result.getUrlOutputResources().add(resource);
			}
		} catch (DataServiceException ex) {
			status = getFailedMethodCallStatus("Exception getting urls from data service: " + ex.getMessage());
			result.setMethodCallStatus(status);
			return result;
		}

		status = new MethodCallStatus();
		status.setMessage("The urls were successfully obtained.");
		status.setStatus(MethodCallStatusEnum.COMPLETED);
		result.setMethodCallStatus(status);
		return result;
	}
}
