/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.pitt.apollo.restservice.methods;

import edu.pitt.apollo.exception.DataServiceException;
import edu.pitt.apollo.exception.SerializationException;
import edu.pitt.apollo.exception.UnsupportedSerializationFormatException;
import edu.pitt.apollo.restservice.utils.ResponseMessageBuilder;
import edu.pitt.apollo.restservice.utils.RestDataServiceUtils;
import edu.pitt.apollo.services_common.v3_0_0.SerializationFormat;
import java.math.BigInteger;
import java.util.List;
import org.springframework.http.HttpStatus;

/**
 *
 * @author nem41
 */
public class AddRunIdsToSimulationGroupForRun extends BaseRunManagerServiceAccessorMethod {

	public AddRunIdsToSimulationGroupForRun(String username, String password, SerializationFormat serializationFormat) throws UnsupportedSerializationFormatException {
		super(username, password, serializationFormat);
	}

	public String addRunIdsToSimulationGroupForRun(BigInteger runId, String runIdList) throws UnsupportedSerializationFormatException, SerializationException {

		List<BigInteger> groupIdsAsList = RestDataServiceUtils.getListOfGroupIds(runIdList);

		try {
			impl.addRunIdsToSimulationGroupForRun(runId, groupIdsAsList, authentication);

			responseBuilder.setStatus(HttpStatus.OK, ResponseMessageBuilder.DEFAULT_SUCCESS_MESSAGE);
		} catch (DataServiceException ex) {
			responseBuilder.setStatus(HttpStatus.INTERNAL_SERVER_ERROR, ex.getMessage());
		}

		return serializer.serializeObject(responseBuilder.getResponse());
	}

}
