package edu.pitt.apollo.dataservice.methods;

import edu.pitt.apollo.JsonUtils;
import edu.pitt.apollo.JsonUtilsException;
import edu.pitt.apollo.data_service_types.v3_0_0.GetOutputFilesURLAsZipMessage;
import edu.pitt.apollo.exception.DataServiceException;
import java.math.BigInteger;

/**
 *
 * @author nem41
 */
public class DataServiceMethodFactory {

	public static DataServiceMethod getDataServiceMethod(String messageContent, BigInteger runId) throws DataServiceException {
		JsonUtils jsonUtils = new JsonUtils();
		DataServiceMethod method;

		try {
			GetOutputFilesURLAsZipMessage message = (GetOutputFilesURLAsZipMessage) jsonUtils.getObjectFromJson(messageContent, GetOutputFilesURLAsZipMessage.class);
			method = new GetOutputFilesURLAsZipMethod(message, runId);
		} catch (JsonUtilsException jue) {
			throw new DataServiceException("Could not parse run data service message as a known message type");
		}

		return method;
	}

}
