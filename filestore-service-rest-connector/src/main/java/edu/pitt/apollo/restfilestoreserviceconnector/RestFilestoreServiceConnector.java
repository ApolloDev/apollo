package edu.pitt.apollo.restfilestoreserviceconnector;

import edu.pitt.apollo.connector.FilestoreServiceConnector;
import edu.pitt.apollo.exception.FilestoreException;
import edu.pitt.apollo.filestore_service_types.v4_0.FileIdentification;
import edu.pitt.apollo.restserviceconnectorcommon.RestServiceUtils;
import edu.pitt.apollo.restserviceconnectorcommon.exception.RestServiceException;
import edu.pitt.apollo.services_common.v4_0.*;

import java.math.BigInteger;
import java.util.List;

/**
 *
 * @author nem41
 */
public class RestFilestoreServiceConnector extends FilestoreServiceConnector {

	private final String restServiceUri;
	private final RestServiceUtils restServiceUtils = new RestServiceUtils();

	public RestFilestoreServiceConnector(String url) {
		super(url);
		restServiceUri = serviceUrl + "/ws/files/";
	}

	@Override
	public void uploadFile(BigInteger runId, String urlToFile,
			FileIdentification fileIdentification, Authentication authentication) throws FilestoreException {
		String uri = restServiceUri + runId + "?" + "urlToFile=" + urlToFile + "&"
                + RestServiceUtils.getUsernameAndPasswordQueryParams(authentication);
		try {
			restServiceUtils.makePostRequestAndCheckResponse(uri, fileIdentification);
		} catch (RestServiceException ex) {
			throw new FilestoreException(ex.getMessage());
		}
	}

	@Override
	public String getUrlOfFile(BigInteger runId, String filename,
			ContentDataFormatEnum fileFormat, ContentDataTypeEnum fileType, Authentication authentication) throws FilestoreException {
		String uri = restServiceUri + runId + "/url?fileName=" + filename + "&fileFormat=" + fileFormat
				+ "&fileType=" + fileType + "&" + RestServiceUtils.getUsernameAndPasswordQueryParams(authentication);
		try {
			return restServiceUtils.makeGetRequestCheckResponseAndGetObject(uri, String.class);
		} catch (RestServiceException ex) {
			throw new FilestoreException(ex.getMessage());
		}
	}

	@Override
	public MethodCallStatus getStatusOfFileUpload(BigInteger runId, String filename,
			ContentDataFormatEnum fileFormat, ContentDataTypeEnum fileType, Authentication authentication) throws FilestoreException {
		String uri = restServiceUri + runId + "/status?fileName=" + filename + "&fileFormat=" + fileFormat
				+ "&fileType=" + fileType + "&" + RestServiceUtils.getUsernameAndPasswordQueryParams(authentication);
		try {
			return restServiceUtils.makeGetRequestCheckResponseAndGetObject(uri, MethodCallStatus.class);
		} catch (RestServiceException ex) {
			throw new FilestoreException(ex.getMessage());
		}
	}

	@Override
	public List<FileIdentification> listFilesForRun(BigInteger runId, Authentication authentication) throws FilestoreException {
		String uri = restServiceUri + runId + "?" + RestServiceUtils.getUsernameAndPasswordQueryParams(authentication);
		try {
			return restServiceUtils.makeGetRequestCheckResponseAndGetObjects(uri, FileIdentification.class);
		} catch (RestServiceException ex) {
			throw new FilestoreException(ex.getMessage());
		}
	}

}
