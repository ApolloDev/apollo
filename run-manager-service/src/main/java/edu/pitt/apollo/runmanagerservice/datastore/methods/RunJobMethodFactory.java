package edu.pitt.apollo.runmanagerservice.datastore.methods;

import edu.pitt.apollo.utilities.JsonUtils;
import edu.pitt.apollo.exception.JsonUtilsException;
import edu.pitt.apollo.data_service_types.v4_0.DataRetrievalRequestMessage;
import edu.pitt.apollo.runmanagerservice.datastore.accessors.DatastoreAccessor;
import edu.pitt.apollo.runmanagerservice.datastore.accessors.DatastoreAccessorFactory;
import edu.pitt.apollo.db.exceptions.ApolloDatabaseException;
import edu.pitt.apollo.exception.DatastoreException;
import edu.pitt.apollo.runmanagerservice.exception.UnrecognizedMessageTypeException;
import edu.pitt.apollo.services_common.v4_0.Authentication;
import edu.pitt.apollo.services_common.v4_0.FileAndURLDescription;
import java.math.BigInteger;
import java.util.Map;

/**
 *
 * @author nem41
 */
public class RunJobMethodFactory {

	private static final String RUN_DATA_SEVICE_MESSAGE_FILENAME = "data_retrieval_request_message.json";

	private static String getMessageContent(BigInteger runId, Authentication authentication) throws UnrecognizedMessageTypeException, ApolloDatabaseException, DatastoreException {
		DatastoreAccessor databaseAccessor = DatastoreAccessorFactory.getDatabaseAccessor();
		Map<BigInteger, FileAndURLDescription> contentMap = databaseAccessor.getListOfFilesForRunId(runId, RUN_DATA_SEVICE_MESSAGE_FILENAME, authentication);

		if (contentMap.size() != 1) {
			throw new DatastoreException("Expected 1 message to run data service job but there were " + contentMap.size());
		}

		String messageContent = databaseAccessor.getContentForContentId(contentMap.keySet().iterator().next(), authentication);
		return messageContent;
	}
	
	public static RunJobMethod getDataServiceMethod(BigInteger runId, Authentication authentication) throws DatastoreException, UnrecognizedMessageTypeException, ApolloDatabaseException {

		String messageContent = getMessageContent(runId, authentication);
		
		JsonUtils jsonUtils = new JsonUtils();
		RunJobMethod method;

		try {
			DataRetrievalRequestMessage message = (DataRetrievalRequestMessage) jsonUtils.getObjectFromJson(messageContent, DataRetrievalRequestMessage.class);
			method = new RunJobToGetOutputFilesURLAsZipMethod(message, runId);
		} catch (JsonUtilsException jue) {
			throw new DatastoreException("Could not parse run data service message as a known message type");
		}

		return method;
	}

}
