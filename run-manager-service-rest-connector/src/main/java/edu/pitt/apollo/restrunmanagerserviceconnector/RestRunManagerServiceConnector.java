package edu.pitt.apollo.restrunmanagerserviceconnector;

import edu.pitt.apollo.connector.RunManagerServiceConnector;
import edu.pitt.apollo.exception.DatastoreException;
import edu.pitt.apollo.exception.JobRunningServiceException;
import edu.pitt.apollo.exception.RunManagementException;
import edu.pitt.apollo.restserviceconnectorcommon.RestServiceUtils;
import edu.pitt.apollo.restserviceconnectorcommon.exception.RestServiceException;
import edu.pitt.apollo.services_common.v4_0_1.*;
import edu.pitt.apollo.types.v4_0_1.SoftwareIdentification;

import java.math.BigInteger;
import java.util.List;

/**
 *
 * @author nem41
 */
public class RestRunManagerServiceConnector extends RunManagerServiceConnector {

	private final String restServiceUri;
	private final RestServiceUtils restServiceUtils = new RestServiceUtils();

	public RestRunManagerServiceConnector(String url) {
		super(url);
		restServiceUri = serviceUrl + "/ws/";
	}

	// implementation methods
	@Override
	public List<BigInteger> getRunIdsAssociatedWithSimulationGroupForRun(BigInteger runId, Authentication authentication) throws RunManagementException {
		String uri = restServiceUri + "run/" + runId + "/rungroup";
		try {
			return restServiceUtils.makeGetRequestCheckResponseAndGetObjects(uri, authentication, BigInteger.class);
		} catch (RestServiceException ex) {
			throw new RunManagementException(ex.getMessage());
		}
	}

	@Override
	public SoftwareIdentification getSoftwareIdentificationForRun(BigInteger runId, Authentication authentication) throws RunManagementException {

		String uri = restServiceUri + "run/" + runId + "/softwareIdentification";
		try {
			return restServiceUtils.makeGetRequestCheckResponseAndGetObject(uri, authentication, SoftwareIdentification.class);
		} catch (RestServiceException ex) {
			throw new RunManagementException(ex.getMessage());
		}
	}

	@Override
	public InsertRunResult insertRun(RunMessage message, Authentication authentication) throws RunManagementException {
		String uri = restServiceUri + "runs";
		try {
			return restServiceUtils.makePostRequestCheckResponseAndGetObject(uri, authentication, message, InsertRunResult.class);
		} catch (RestServiceException ex) {
			throw new RunManagementException(ex.getMessage());
		}
	}

	@Override
	public void updateStatusOfRun(BigInteger runId, MethodCallStatusEnum statusEnumToSet, String messageToSet, Authentication authentication) throws RunManagementException {
		String uri = restServiceUri + "run/" + runId + "/status?";
		uri += "&methodCallStatusEnum=" + statusEnumToSet + "&statusMessage=" + messageToSet;
		try {
			restServiceUtils.makePostRequestAndCheckResponse(uri, authentication, "");
		} catch (RestServiceException ex) {
			throw new RunManagementException(ex.getMessage());
		}
	}

	@Override
	public void updateLastServiceToBeCalledForRun(BigInteger runId, SoftwareIdentification softwareIdentification, Authentication authentication) throws RunManagementException {
		String uri = "run/" + runId + "/lastServiceToBeCalled?";
		uri += "&softwareName=" + softwareIdentification.getSoftwareName() + "&softwareVersion=" + softwareIdentification.getSoftwareVersion()
				+ "&softwareDeveloper=" + softwareIdentification.getSoftwareDeveloper() + "&softwareTypeEnum=" + softwareIdentification.getSoftwareType();
		try {
			restServiceUtils.makePostRequestAndCheckResponse(uri, authentication, "");
		} catch (RestServiceException ex) {
			throw new RunManagementException(ex.getMessage());
		}
	}

	@Override
	public SoftwareIdentification getLastServiceToBeCalledForRun(BigInteger runId, Authentication authentication) throws RunManagementException {
		String uri = restServiceUri + "run/" + runId + "/lastServiceToBeCalled";
		try {
			return restServiceUtils.makeGetRequestCheckResponseAndGetObject(uri, authentication, SoftwareIdentification.class);
		} catch (RestServiceException ex) {
			throw new RunManagementException(ex.getMessage());
		}
	}

	@Override
	public void addRunIdsToSimulationGroupForRun(BigInteger runId, List<BigInteger> runIds, Authentication authentication) throws RunManagementException {
		String uri = restServiceUri + "run/" + runId + "/rungroup";

		StringBuilder listOfRunIds = new StringBuilder();
		for (BigInteger id : runIds) {
			listOfRunIds.append(id).append(",");
		}
		listOfRunIds.deleteCharAt(listOfRunIds.length() - 1);
		uri += "&runIdsToAssociate=" + listOfRunIds.toString();
		try {
			restServiceUtils.makePostRequestAndCheckResponse(uri, authentication, "");
		} catch (RestServiceException ex) {
			throw new RunManagementException(ex.getMessage());
		}
	}

	@Override
	public void removeRunData(BigInteger runId, Authentication authentication) throws RunManagementException {
		String uri = restServiceUri + "run/" + runId;
		try {
			restServiceUtils.makePostRequestAndCheckResponse(uri, authentication, "");
		} catch (RestServiceException ex) {
			throw new RunManagementException(ex.getMessage());
		}
	}

	@Override
	public MethodCallStatus getRunStatus(BigInteger runId, Authentication authentication) throws RunManagementException {
		String uri = restServiceUri + "run/" + runId + "/status";
		try {
			return restServiceUtils.makeGetRequestCheckResponseAndGetObject(uri, authentication, MethodCallStatus.class);
		} catch (RestServiceException ex) {
			throw new RunManagementException(ex.getMessage());
		}
	}

	@Override
	public void run(BigInteger runId, Authentication authentication) throws JobRunningServiceException {
		String uri = restServiceUri + "run/" + runId + "?";
		uri += "&action=" + RunActionEnum.START;
		try {
			restServiceUtils.makePostRequestAndCheckResponse(uri, authentication, "");
		} catch (RestServiceException ex) {
			throw new JobRunningServiceException(ex.getMessage());
		}
	}

	@Override
	public void terminate(BigInteger runId, Authentication authentication) throws JobRunningServiceException {
		String uri = "run/" + runId + "?";
		uri += "&action=" + RunActionEnum.TERMINATE;
		try {
			restServiceUtils.makePostRequestAndCheckResponse(uri, authentication, "");
		} catch (RestServiceException ex) {
			throw new JobRunningServiceException(ex.getMessage());
		}
	}

	@Override
	public List<ServiceRecord> getListOfRegisteredSoftwareRecords(Authentication authentication) throws DatastoreException {
		String uri = restServiceUri + "software";
		try {
			return restServiceUtils.makeGetRequestCheckResponseAndGetObjects(uri, authentication, ServiceRecord.class);
		} catch (RestServiceException ex) {
			throw new DatastoreException(ex.getMessage());
		}
	}

	@Override
	public String getURLForSoftwareIdentification(SoftwareIdentification softwareId, Authentication authentication) throws DatastoreException {
		String uri = restServiceUri + "software/url?";
		uri += "&softwareName=" + softwareId.getSoftwareName() + "&softwareVersion=" + softwareId.getSoftwareVersion()
				+ "&softwareDeveloper=" + softwareId.getSoftwareDeveloper() + "&softwareTypeEnum=" + softwareId.getSoftwareType();
		try {
			return restServiceUtils.makeGetRequestCheckResponseAndGetObject(uri, authentication, String.class);
		} catch (RestServiceException ex) {
			throw new DatastoreException(ex.getMessage());
		}
	}
}
