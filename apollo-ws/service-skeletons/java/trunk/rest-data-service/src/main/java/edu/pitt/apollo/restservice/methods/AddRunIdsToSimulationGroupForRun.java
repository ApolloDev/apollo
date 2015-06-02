/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.pitt.apollo.restservice.methods;

import edu.pitt.apollo.DataServiceImpl;
import edu.pitt.apollo.exception.DataServiceException;
import edu.pitt.apollo.exception.SerializationException;
import edu.pitt.apollo.exception.UnsupportedSerializationFormatException;
import edu.pitt.apollo.restservice.rest.utils.ResponseMessageBuilder;
import edu.pitt.apollo.restservice.utils.CodeResolver;
import edu.pitt.apollo.services_common.v3_0_0.Authentication;
import edu.pitt.apollo.services_common.v3_0_0.SerializationFormat;
import edu.pitt.apollo.utilities.SerializerFactory;
import edu.pitt.apollo.utilities.Serializer;
import java.math.BigInteger;
import java.util.List;
import org.springframework.http.HttpStatus;

/**
 *
 * @author nem41
 */
public class AddRunIdsToSimulationGroupForRun {

	public static String addRunIdsToSimulationGroupForRun(String username, String password, BigInteger runId, String runIdList, SerializationFormat serializationFormat) throws UnsupportedSerializationFormatException, SerializationException {

		List<BigInteger> groupIdsAsList = CodeResolver.getListOfGroupIds(runIdList);

		DataServiceImpl impl = new DataServiceImpl();

		Authentication authentication = new Authentication();
		authentication.setRequesterId(username);
		authentication.setRequesterPassword(password);

		ResponseMessageBuilder responseBuilder = new ResponseMessageBuilder();

		Serializer serializer = SerializerFactory.getSerializer(serializationFormat, Serializer.APOLLO_NAMESPACE, Serializer.APOLLO_NAMESPACE_TNS_PREFIX);

		try {
			impl.addRunIdsToSimulationGroupForRun(runId, groupIdsAsList, authentication);

			responseBuilder.setStatus(HttpStatus.OK, ResponseMessageBuilder.DEFAULT_SUCCESS_MESSAGE);
		} catch (DataServiceException ex) {
			responseBuilder.setStatus(HttpStatus.INTERNAL_SERVER_ERROR, ex.getMessage());
		}

		return serializer.serializeObject(responseBuilder.getResponse());
	}

}
