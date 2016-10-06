/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.pitt.apollo.brokerservicerestfrontend.methods;

import edu.pitt.apollo.exception.DatastoreException;
import edu.pitt.apollo.exception.SerializationException;
import edu.pitt.apollo.exception.UnsupportedSerializationFormatException;
import edu.pitt.apollo.brokerservicerestfrontend.utils.ResponseMessageBuilder;
import edu.pitt.apollo.brokerservicerestfrontend.utils.RestDataServiceUtils;
import edu.pitt.apollo.exception.RunManagementException;
import edu.pitt.apollo.services_common.v4_0.SerializationFormat;
import java.math.BigInteger;
import java.util.List;

import edu.pitt.apollo.utils.UnsupportedAuthorizationTypeException;
import org.springframework.http.HttpStatus;

/**
 *
 * @author nem41
 */
public class AddRunIdsToSimulationGroupForRun extends BaseBrokerServiceAccessorMethod {

	public AddRunIdsToSimulationGroupForRun(SerializationFormat serializationFormat, String authorizationHeader) throws UnsupportedSerializationFormatException, UnsupportedAuthorizationTypeException {
		super(serializationFormat, authorizationHeader);
	}

	public String addRunIdsToSimulationGroupForRun(BigInteger runId, String runIdList) throws UnsupportedSerializationFormatException, SerializationException {

		List<BigInteger> groupIdsAsList = RestDataServiceUtils.getListOfGroupIds(runIdList);

		try {
			impl.addRunIdsToSimulationGroupForRun(runId, groupIdsAsList, authentication);

			responseBuilder.setStatus(HttpStatus.OK, ResponseMessageBuilder.DEFAULT_SUCCESS_MESSAGE);
		} catch (RunManagementException ex) {
			responseBuilder.setStatus(HttpStatus.INTERNAL_SERVER_ERROR, ex.getMessage());
		}

		return serializer.serializeObject(responseBuilder.getResponse());
	}

}
