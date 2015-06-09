package edu.pitt.apollo.restrunmanagerserviceconnector;

import edu.pitt.apollo.connector.RunManagerServiceConnector;
import edu.pitt.apollo.exception.RunManagementException;
import edu.pitt.apollo.exception.JobRunningServiceException;
import edu.pitt.apollo.restserviceconnectorcommon.RestServiceUtils;
import edu.pitt.apollo.restserviceconnectorcommon.exception.RestServiceException;
import edu.pitt.apollo.services_common.v3_0_0.*;

import java.math.BigInteger;
import java.util.List;
import org.springframework.web.client.RestTemplate;

/**
 *
 * @author nem41
 */
public class RestRunManagerServiceConnector extends RunManagerServiceConnector {

	RestTemplate template = new RestTemplate();
	private final String restServiceUrl;
	
	public RestRunManagerServiceConnector(String url) {
		super(url);
		restServiceUrl = serviceUrl + "/ws/";
	}

	// some helper methods
	private void checkResponse(Response response) throws RunManagementException {
		try {
			RestServiceUtils.checkResponseCode(response);
		} catch (RestServiceException ex) {
			throw new RunManagementException(ex.getMessage());
		}
	}

	// helper methods
	private void makeGetRequestAndCheckResponse(String uri) throws RunManagementException {
		Response response = template.getForObject(uri, Response.class);
		checkResponse(response);
	}

	private void makePostRequestAndCheckResponse(String uri, Object object) throws RunManagementException {
		Response response = template.postForObject(uri, object, Response.class);
		checkResponse(response);
	}

	private <T> T makeGetRequestCheckResponseAndGetObject(String uri, Class<T> clazz) throws RunManagementException {
		Response response = template.getForObject(uri, Response.class);
		return checkResponseAndGetObject(response, clazz);
	}

	private <T> List<T> makeGetRequestCheckResponseAndGetObjects(String uri, Class<T> clazz) throws RunManagementException {
		Response response = template.getForObject(uri, Response.class);
		return checkResponseAndGetObjects(response, clazz);
	}

	private <T> T checkResponseAndGetObject(Response response, Class<T> clazz) throws RunManagementException {
		try {
			RestServiceUtils.checkResponseCode(response);
			return RestServiceUtils.getObjectFromResponseBody(response, clazz);
		} catch (RestServiceException ex) {
			throw new RunManagementException(ex.getMessage());
		}
	}

	private <T> List<T> checkResponseAndGetObjects(Response response, Class<T> clazz) throws RunManagementException {
		try {
			RestServiceUtils.checkResponseCode(response);
			return RestServiceUtils.getObjectsFromResponseBody(response, clazz);
		} catch (RestServiceException ex) {
			throw new RunManagementException(ex.getMessage());
		}
	}
	
	// implementation methods
	@Override
	public List<BigInteger> getRunIdsAssociatedWithSimulationGroupForRun(BigInteger runId, Authentication authentication) throws RunManagementException {
		String uri = restServiceUrl + "run/" + runId + "/rungroup?" + RestServiceUtils.getUsernameAndPasswordQueryParams(authentication);
		return makeGetRequestCheckResponseAndGetObjects(uri, BigInteger.class);
	}

	@Override
	public SoftwareIdentification getSoftwareIdentificationForRun(BigInteger runId, Authentication authentication) throws RunManagementException {

		String uri = restServiceUrl + "run/" + runId + "/softwareIdentification?" + RestServiceUtils.getUsernameAndPasswordQueryParams(authentication);
		return makeGetRequestCheckResponseAndGetObject(uri, SoftwareIdentification.class);
	}

	@Override
	public BigInteger insertRun(RunMessage message) throws RunManagementException {
		String uri = restServiceUrl + "runs?" + RestServiceUtils.getUsernameAndPasswordQueryParams(message.getAuthentication());

		try {
			Request request = RestServiceUtils.getRequestObjectWithSerializedBody(message);
			Response response = template.postForObject(uri, request, Response.class);
			return checkResponseAndGetObject(response, BigInteger.class);
		} catch (RestServiceException ex) {
			throw new RunManagementException(ex.getMessage());
		}
	}

	@Override
	public void updateStatusOfRun(BigInteger runId, MethodCallStatusEnum statusEnumToSet, String messageToSet, Authentication authentication) throws RunManagementException {
		String uri = restServiceUrl + "run/" + runId + "/status?" + RestServiceUtils.getUsernameAndPasswordQueryParams(authentication);
		uri += "&methodCallStatusEnum=" + statusEnumToSet + "&statusMessage=" + messageToSet;
		makeGetRequestAndCheckResponse(uri);
	}

	@Override
	public void updateLastServiceToBeCalledForRun(BigInteger runId, SoftwareIdentification softwareIdentification, Authentication authentication) throws RunManagementException {
		String uri = "run/" + runId + "/lastServiceToBeCalled?" + RestServiceUtils.getUsernameAndPasswordQueryParams(authentication);
		uri += "&softwareName=" + softwareIdentification.getSoftwareName() + "&softwareVersion=" + softwareIdentification.getSoftwareVersion()
				+ "&softwareDeveloper=" + softwareIdentification.getSoftwareDeveloper() + "&softwareTypeEnum=" + softwareIdentification.getSoftwareType();
		makeGetRequestAndCheckResponse(uri);
	}

	@Override
	public SoftwareIdentification getLastServiceToBeCalledForRun(BigInteger runId, Authentication authentication) throws RunManagementException {
		String uri = restServiceUrl + "run/" + runId + "/lastServiceToBeCalled?" + RestServiceUtils.getUsernameAndPasswordQueryParams(authentication);
		return makeGetRequestCheckResponseAndGetObject(uri, SoftwareIdentification.class);
	}

	@Override
	public void addRunIdsToSimulationGroupForRun(BigInteger runId, List<BigInteger> runIds, Authentication authentication) throws RunManagementException {
		String uri = restServiceUrl + "run/" + runId + "/rungroup?" + RestServiceUtils.getUsernameAndPasswordQueryParams(authentication);

		StringBuilder listOfRunIds = new StringBuilder();
		for (BigInteger id : runIds) {
			listOfRunIds.append(id).append(",");
		}
		listOfRunIds.deleteCharAt(listOfRunIds.length() - 1);
		uri += "&runIdsToAssociate=" + listOfRunIds.toString();

		makePostRequestAndCheckResponse(uri, "");
	}

	@Override
	public void removeRunData(BigInteger runId, Authentication authentication) throws RunManagementException {
		String uri = restServiceUrl + "run/" + runId + "?" + RestServiceUtils.getUsernameAndPasswordQueryParams(authentication);
		makePostRequestAndCheckResponse(uri, "");
	}

	@Override
	public MethodCallStatus getRunStatus(BigInteger runId, Authentication authentication) throws RunManagementException {
		String uri = restServiceUrl + "run/" + runId + "/status?" + RestServiceUtils.getUsernameAndPasswordQueryParams(authentication);
		return makeGetRequestCheckResponseAndGetObject(uri, MethodCallStatus.class);
	}

	@Override
	public void run(BigInteger runId, Authentication authentication) throws JobRunningServiceException {
		String uri = "run/" + runId + "?" + RestServiceUtils.getUsernameAndPasswordQueryParams(authentication);
		uri += "&action=" + RunActionEnum.START;
		try {
			makePostRequestAndCheckResponse(uri, "");
		} catch (RunManagementException ex) {
			throw new JobRunningServiceException(ex.getMessage());
		}
	}

	@Override
	public void terminate(BigInteger runId, Authentication authentication) throws JobRunningServiceException {
		String uri = "run/" + runId + "?" + RestServiceUtils.getUsernameAndPasswordQueryParams(authentication);
		uri += "&action=" + RunActionEnum.TERMINATE;
		try {
			makePostRequestAndCheckResponse(uri, "");
		} catch (RunManagementException ex) {
			throw new JobRunningServiceException(ex.getMessage());
		}
	}
}
