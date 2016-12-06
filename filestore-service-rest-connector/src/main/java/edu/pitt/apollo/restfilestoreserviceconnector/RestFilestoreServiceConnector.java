package edu.pitt.apollo.restfilestoreserviceconnector;

import edu.pitt.apollo.connector.FilestoreServiceConnector;
import edu.pitt.apollo.exception.FilestoreException;
import edu.pitt.apollo.filestore_service_types.v4_0_1.FileIdentification;
import edu.pitt.apollo.restserviceconnectorcommon.RestServiceUtils;
import edu.pitt.apollo.restserviceconnectorcommon.exception.RestServiceException;
import edu.pitt.apollo.services_common.v4_0_1.Authentication;
import edu.pitt.apollo.services_common.v4_0_1.ContentDataFormatEnum;
import edu.pitt.apollo.services_common.v4_0_1.ContentDataTypeEnum;
import edu.pitt.apollo.services_common.v4_0_1.MethodCallStatus;

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
	public void uploadFile(BigInteger runId, String urlToFile, String filename,
			ContentDataFormatEnum fileFormat, ContentDataTypeEnum fileType, Authentication authentication) throws FilestoreException {
		String uri = restServiceUri + runId + "?" + "urlToFile=" + urlToFile + "&"
				+ "fileName=" + filename + "&fileFormat=" + fileFormat + "&fileType=" + fileType;
		try {
			restServiceUtils.makePostRequestAndCheckResponse(uri, authentication, "");
		} catch (RestServiceException ex) {
			throw new FilestoreException(ex.getMessage());
		}
	}

	@Override
	public String getUrlOfFile(BigInteger runId, String filename,
			ContentDataFormatEnum fileFormat, ContentDataTypeEnum fileType, Authentication authentication) throws FilestoreException {
		String uri = restServiceUri + runId + "/url?fileName=" + filename + "&fileFormat=" + fileFormat
				+ "&fileType=" + fileType;
		try {
			return restServiceUtils.makeGetRequestCheckResponseAndGetObject(uri, authentication, String.class);
		} catch (RestServiceException ex) {
			throw new FilestoreException(ex.getMessage());
		}
	}

	@Override
	public MethodCallStatus getStatusOfFileUpload(BigInteger runId, String filename,
			ContentDataFormatEnum fileFormat, ContentDataTypeEnum fileType, Authentication authentication) throws FilestoreException {
		String uri = restServiceUri + runId + "/status?fileName=" + filename + "&fileFormat=" + fileFormat
				+ "&fileType=" + fileType;
		try {
			return restServiceUtils.makeGetRequestCheckResponseAndGetObject(uri, authentication, MethodCallStatus.class);
		} catch (RestServiceException ex) {
			throw new FilestoreException(ex.getMessage());
		}
	}

	@Override
	public List<FileIdentification> listFilesForRun(BigInteger runId, Authentication authentication) throws FilestoreException {
		String uri = restServiceUri + runId;
		try {
			return restServiceUtils.makeGetRequestCheckResponseAndGetObjects(uri, authentication, FileIdentification.class);
		} catch (RestServiceException ex) {
			throw new FilestoreException(ex.getMessage());
		}
	}

}
