package edu.pitt.apollo.restbrokerserviceconnector;

import edu.pitt.apollo.connector.BrokerServiceConnector;
import edu.pitt.apollo.data_service_types.v4_0.AssociateContentWithRunIdMessage;
import edu.pitt.apollo.data_service_types.v4_0.ContentIdAndDescription;
import edu.pitt.apollo.exception.DataServiceException;
import edu.pitt.apollo.exception.RunManagementException;
import edu.pitt.apollo.exception.JobRunningServiceException;
import edu.pitt.apollo.restserviceconnectorcommon.RestServiceUtils;
import edu.pitt.apollo.restserviceconnectorcommon.exception.RestServiceException;
import edu.pitt.apollo.services_common.v4_0.*;
import edu.pitt.apollo.types.v4_0.SoftwareIdentification;

import java.math.BigInteger;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author nem41
 */
public class RestBrokerServiceConnector extends BrokerServiceConnector {

	private final String restServiceUri;
	private final RestServiceUtils restServiceUtils = new RestServiceUtils();

	public RestBrokerServiceConnector(String url) {
		super(url);
		restServiceUri = serviceUrl + "/ws/";
	}

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
		throw new JobRunningServiceException("Not supported yet");
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
    public void associateContentWithRunId(BigInteger runId, String content, SoftwareIdentification sourceSoftware, SoftwareIdentification destinationSoftware, String contentLabel, ContentDataFormatEnum contentDataFormat, ContentDataTypeEnum contentDataType, Authentication authentication) throws DataServiceException {

        String uri = restServiceUri + "run/" + runId;
        if (contentDataFormat.equals(ContentDataFormatEnum.URL)) {
            uri += "/files";
        } else {
            uri += "/urls";
        }
        uri += "?" + RestServiceUtils.getUsernameAndPasswordQueryParams(authentication);

        AssociateContentWithRunIdMessage message = new AssociateContentWithRunIdMessage();
        message.setAuthentication(authentication);
        message.setContentDataFormat(contentDataFormat);
        message.setContentDataType(contentDataType);
        message.setContentLabel(contentLabel);
        message.setDestinationSoftware(destinationSoftware);
        message.setSourceSoftware(sourceSoftware);
        message.setFileContentOrUrl(content);

        try {
            restServiceUtils.makePostRequestAndCheckResponse(uri, message);
        } catch (RestServiceException ex) {
            throw new DataServiceException(ex.getMessage());
        }
    }

    @Override
    public Map<BigInteger, FileAndURLDescription> getListOfFilesForRunId(BigInteger runId, Authentication authentication) throws DataServiceException {
        String uri = restServiceUri + "run/" + runId + "/files?" + RestServiceUtils.getUsernameAndPasswordQueryParams(authentication);
        try {
            List<ContentIdAndDescription> contentIdsAndDescriptions = restServiceUtils.makeGetRequestCheckResponseAndGetObjects(uri, ContentIdAndDescription.class);
            Map<BigInteger, FileAndURLDescription> map = new HashMap<>();
            for (ContentIdAndDescription contentIdAndDescription : contentIdsAndDescriptions) {
                map.put(contentIdAndDescription.getContentId(), contentIdAndDescription.getContentDescription());
            }
            return map;
        } catch (RestServiceException ex) {
            throw new DataServiceException(ex.getMessage());
        }
    }

    @Override
    public Map<BigInteger, FileAndURLDescription> getListOfURLsForRunId(BigInteger runId, Authentication authentication) throws DataServiceException {
        String uri = restServiceUri + "run/" + runId + "/urls?" + RestServiceUtils.getUsernameAndPasswordQueryParams(authentication);
        try {
            List<ContentIdAndDescription> contentIdsAndDescriptions = restServiceUtils.makeGetRequestCheckResponseAndGetObjects(uri, ContentIdAndDescription.class);
            Map<BigInteger, FileAndURLDescription> map = new HashMap<>();
            for (ContentIdAndDescription contentIdAndDescription : contentIdsAndDescriptions) {
                map.put(contentIdAndDescription.getContentId(), contentIdAndDescription.getContentDescription());
            }
            return map;
        } catch (RestServiceException ex) {
            throw new DataServiceException(ex.getMessage());
        }
    }

    @Override
    public void removeFileAssociationWithRun(BigInteger runId, BigInteger fileId, Authentication authentication) throws DataServiceException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public String getContentForContentId(BigInteger contentId, Authentication authentication) throws DataServiceException {
        String uri = restServiceUri + "content/" + contentId + "?" + RestServiceUtils.getUsernameAndPasswordQueryParams(authentication);
        try {
            return restServiceUtils.makeGetRequestCheckResponseAndGetObject(uri, String.class);
        } catch (RestServiceException ex) {
            throw new DataServiceException(ex.getMessage());
        }
    }

    @Override
    public String getURLForSoftwareIdentification(SoftwareIdentification softwareIdentification, Authentication authentication) throws DataServiceException {
        String uri = restServiceUri + "software/url?" + RestServiceUtils.getUsernameAndPasswordQueryParams(authentication);
        uri += "&softwareName=" + softwareIdentification.getSoftwareName() + "&softwareVersion=" + softwareIdentification.getSoftwareVersion()
                + "&softwareDeveloper=" + softwareIdentification.getSoftwareDeveloper() + "&softwareTypeEnum=" + softwareIdentification.getSoftwareType();
        try {
            return restServiceUtils.makeGetRequestCheckResponseAndGetObject(uri, String.class);
        } catch (RestServiceException ex) {
            throw new DataServiceException(ex.getMessage());
        }
    }

    @Override
    public List<ServiceRegistrationRecord> getListOfRegisteredSoftwareRecords(Authentication authentication) throws DataServiceException {
        String uri = restServiceUri + "software?" + RestServiceUtils.getUsernameAndPasswordQueryParams(authentication);
        try {
            return restServiceUtils.makeGetRequestCheckResponseAndGetObjects(uri, ServiceRegistrationRecord.class);
        } catch (RestServiceException ex) {
            throw new DataServiceException(ex.getMessage());
        }
    }


}
