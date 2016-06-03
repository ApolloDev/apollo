package edu.pitt.apollo.dataservice.methods;

import edu.pitt.apollo.utilities.JsonUtils;
import edu.pitt.apollo.exception.JsonUtilsException;
import edu.pitt.apollo.data_service_types.v3_1_0.DataRetrievalRequestMessage;
import edu.pitt.apollo.dataservice.accessors.DatabaseAccessor;
import edu.pitt.apollo.dataservice.accessors.DatabaseAccessorFactory;
import edu.pitt.apollo.db.exceptions.ApolloDatabaseException;
import edu.pitt.apollo.exception.DataServiceException;
import edu.pitt.apollo.exception.UnrecognizedMessageTypeException;
import edu.pitt.apollo.services_common.v3_1_0.Authentication;
import edu.pitt.apollo.services_common.v3_1_0.FileAndURLDescription;
import java.math.BigInteger;
import java.util.Map;

/**
 *
 * @author nem41
 */
public class RunJobMethodFactory {

	private static final String RUN_DATA_SEVICE_MESSAGE_FILENAME = "data_retrieval_request_message.json";

	private static String getMessageContent(BigInteger runId, Authentication authentication) throws UnrecognizedMessageTypeException, ApolloDatabaseException, DataServiceException {
		DatabaseAccessor databaseAccessor = DatabaseAccessorFactory.getDatabaseAccessor(authentication);
		Map<BigInteger, FileAndURLDescription> contentMap = databaseAccessor.getListOfFilesForRunId(runId, RUN_DATA_SEVICE_MESSAGE_FILENAME, authentication);

		if (contentMap.size() != 1) {
			throw new DataServiceException("Expected 1 message to run data service job but there were " + contentMap.size());
		}

		String messageContent = databaseAccessor.getContentForContentId(contentMap.keySet().iterator().next(), authentication);
		return messageContent;
	}
	
	public static RunJobMethod getDataServiceMethod(BigInteger runId, Authentication authentication) throws DataServiceException, UnrecognizedMessageTypeException, ApolloDatabaseException {

		String messageContent = getMessageContent(runId, authentication);
		
		JsonUtils jsonUtils = new JsonUtils();
		RunJobMethod method;

		try {
			DataRetrievalRequestMessage message = (DataRetrievalRequestMessage) jsonUtils.getObjectFromJson(messageContent, DataRetrievalRequestMessage.class);
			method = new RunJobToGetOutputFilesURLAsZipMethod(message, runId);
		} catch (JsonUtilsException jue) {
			throw new DataServiceException("Could not parse run data service message as a known message type");
		}

		return method;
	}

}
