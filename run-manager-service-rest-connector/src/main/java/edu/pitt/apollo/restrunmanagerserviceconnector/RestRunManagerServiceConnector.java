package edu.pitt.apollo.restrunmanagerserviceconnector;

import edu.pitt.apollo.connector.RunManagerServiceConnector;
import edu.pitt.apollo.exception.DatastoreException;
import edu.pitt.apollo.exception.RunManagementException;
import edu.pitt.apollo.exception.JobRunningServiceException;
import edu.pitt.apollo.restserviceconnectorcommon.RestServiceUtils;
import edu.pitt.apollo.restserviceconnectorcommon.exception.RestServiceException;
import edu.pitt.apollo.services_common.v4_0.*;
import edu.pitt.apollo.types.v4_0.SoftwareIdentification;

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
		String uri = restServiceUri + "run/" + runId + "/rungroup?" + RestServiceUtils.getUsernameAndPasswordQueryParams(authentication);
		try {
			return restServiceUtils.makeGetRequestCheckResponseAndGetObjects(uri, BigInteger.class);
		} catch (RestServiceException ex) {
			throw new RunManagementException(ex.getMessage());
		}
	}

	@Override
	public SoftwareIdentification getSoftwareIdentificationForRun(BigInteger runId, Authentication authentication) throws RunManagementException {

		String uri = restServiceUri + "run/" + runId + "/softwareIdentification?" + RestServiceUtils.getUsernameAndPasswordQueryParams(authentication);
		try {
			return restServiceUtils.makeGetRequestCheckResponseAndGetObject(uri, SoftwareIdentification.class);
		} catch (RestServiceException ex) {
			throw new RunManagementException(ex.getMessage());
		}
	}

	@Override
	public InsertRunResult insertRun(RunMessage message) throws RunManagementException {
		String uri = restServiceUri + "runs?" + RestServiceUtils.getUsernameAndPasswordQueryParams(message.getAuthentication());
		try {
			return restServiceUtils.makePostRequestCheckResponseAndGetObject(uri, message, InsertRunResult.class);
		} catch (RestServiceException ex) {
			throw new RunManagementException(ex.getMessage());
		}
	}

	@Override
	public void updateStatusOfRun(BigInteger runId, MethodCallStatusEnum statusEnumToSet, String messageToSet, Authentication authentication) throws RunManagementException {
		String uri = restServiceUri + "run/" + runId + "/status?" + RestServiceUtils.getUsernameAndPasswordQueryParams(authentication);
		uri += "&methodCallStatusEnum=" + statusEnumToSet + "&statusMessage=" + messageToSet;
		try {
			restServiceUtils.makePostRequestAndCheckResponse(uri, "");
		} catch (RestServiceException ex) {
			throw new RunManagementException(ex.getMessage());
		}
	}

	@Override
	public void updateLastServiceToBeCalledForRun(BigInteger runId, SoftwareIdentification softwareIdentification, Authentication authentication) throws RunManagementException {
		String uri = "run/" + runId + "/lastServiceToBeCalled?" + RestServiceUtils.getUsernameAndPasswordQueryParams(authentication);
		uri += "&softwareName=" + softwareIdentification.getSoftwareName() + "&softwareVersion=" + softwareIdentification.getSoftwareVersion()
				+ "&softwareDeveloper=" + softwareIdentification.getSoftwareDeveloper() + "&softwareTypeEnum=" + softwareIdentification.getSoftwareType();
		try {
			restServiceUtils.makePostRequestAndCheckResponse(uri, "");
		} catch (RestServiceException ex) {
			throw new RunManagementException(ex.getMessage());
		}
	}

	@Override
	public SoftwareIdentification getLastServiceToBeCalledForRun(BigInteger runId, Authentication authentication) throws RunManagementException {
		String uri = restServiceUri + "run/" + runId + "/lastServiceToBeCalled?" + RestServiceUtils.getUsernameAndPasswordQueryParams(authentication);
		try {
			return restServiceUtils.makeGetRequestCheckResponseAndGetObject(uri, SoftwareIdentification.class);
		} catch (RestServiceException ex) {
			throw new RunManagementException(ex.getMessage());
		}
	}

	@Override
	public void addRunIdsToSimulationGroupForRun(BigInteger runId, List<BigInteger> runIds, Authentication authentication) throws RunManagementException {
		String uri = restServiceUri + "run/" + runId + "/rungroup?" + RestServiceUtils.getUsernameAndPasswordQueryParams(authentication);

		StringBuilder listOfRunIds = new StringBuilder();
		for (BigInteger id : runIds) {
			listOfRunIds.append(id).append(",");
		}
		listOfRunIds.deleteCharAt(listOfRunIds.length() - 1);
		uri += "&runIdsToAssociate=" + listOfRunIds.toString();
		try {
			restServiceUtils.makePostRequestAndCheckResponse(uri, "");
		} catch (RestServiceException ex) {
			throw new RunManagementException(ex.getMessage());
		}
	}

	@Override
	public void removeRunData(BigInteger runId, Authentication authentication) throws RunManagementException {
		String uri = restServiceUri + "run/" + runId + "?" + RestServiceUtils.getUsernameAndPasswordQueryParams(authentication);
		try {
			restServiceUtils.makePostRequestAndCheckResponse(uri, "");
		} catch (RestServiceException ex) {
			throw new RunManagementException(ex.getMessage());
		}
	}

	@Override
	public MethodCallStatus getRunStatus(BigInteger runId, Authentication authentication) throws RunManagementException {
		String uri = restServiceUri + "run/" + runId + "/status?" + RestServiceUtils.getUsernameAndPasswordQueryParams(authentication);
		try {
			return restServiceUtils.makeGetRequestCheckResponseAndGetObject(uri, MethodCallStatus.class);
		} catch (RestServiceException ex) {
			throw new RunManagementException(ex.getMessage());
		}
	}

	@Override
	public void run(BigInteger runId, Authentication authentication) throws JobRunningServiceException {
		String uri = restServiceUri + "run/" + runId + "?" + RestServiceUtils.getUsernameAndPasswordQueryParams(authentication);
		uri += "&action=" + RunActionEnum.START;
		try {
			restServiceUtils.makePostRequestAndCheckResponse(uri, "");
		} catch (RestServiceException ex) {
			throw new JobRunningServiceException(ex.getMessage());
		}
	}

	@Override
	public void terminate(BigInteger runId, Authentication authentication) throws JobRunningServiceException {
		String uri = "run/" + runId + "?" + RestServiceUtils.getUsernameAndPasswordQueryParams(authentication);
		uri += "&action=" + RunActionEnum.TERMINATE;
		try {
			restServiceUtils.makePostRequestAndCheckResponse(uri, "");
		} catch (RestServiceException ex) {
			throw new JobRunningServiceException(ex.getMessage());
		}
	}

	@Override
	public List<ServiceRecord> getListOfRegisteredSoftwareRecords(Authentication authentication) throws DatastoreException {
		String uri = restServiceUri + "software?" + RestServiceUtils.getUsernameAndPasswordQueryParams(authentication);
		try {
			return restServiceUtils.makeGetRequestCheckResponseAndGetObjects(uri, ServiceRecord.class);
		} catch (RestServiceException ex) {
			throw new DatastoreException(ex.getMessage());
		}
	}

	@Override
	public String getURLForSoftwareIdentification(SoftwareIdentification softwareId, Authentication authentication) throws DatastoreException {
		String uri = restServiceUri + "software/url?" + RestServiceUtils.getUsernameAndPasswordQueryParams(authentication);
		uri += "&softwareName=" + softwareId.getSoftwareName() + "&softwareVersion=" + softwareId.getSoftwareVersion()
				+ "&softwareDeveloper=" + softwareId.getSoftwareDeveloper() + "&softwareTypeEnum=" + softwareId.getSoftwareType();
		try {
			return restServiceUtils.makeGetRequestCheckResponseAndGetObject(uri, String.class);
		} catch (RestServiceException ex) {
			throw new DatastoreException(ex.getMessage());
		}
	}

	@Override
	public void addRole(SoftwareIdentification softwareIdentification, boolean canRunSoftware, boolean allowPrivilegedRequest, String roleDescription, Authentication authentication) throws DatastoreException {
		throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
	}

	@Override
	public void deleteUser(String username, Authentication authentication) throws DatastoreException {
		throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
	}

	@Override
	public void addUserRole(String username, String userPasssword, SoftwareIdentification softwareIdentification, boolean canRunSoftware, boolean canRequestPrivileged, Authentication authentication) throws DatastoreException {
		throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
	}

	@Override
	public void addUser(String userId, String userPassword, String userEmail, Authentication authentication) throws DatastoreException {
		throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
	}

	@Override
	public void authenticateUser(Authentication authentication) throws DatastoreException {
		throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
	}

	@Override
	public void authorizeUser(Authentication authentication, SoftwareIdentification softwareIdentification, boolean requestToRunSoftware) throws DatastoreException {
		throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
	}
}
