package edu.pitt.apollo.runmanagerservice.serviceaccessors;

import edu.pitt.apollo.runmanagerservice.exception.DataServiceException;
import edu.pitt.apollo.Md5UtilsException;
import edu.pitt.apollo.apollo_service_types.v3_0_0.RunSimulationsMessage;

import java.math.BigInteger;

/**
 * Created by jdl50 on 5/21/15.
 */
public class DataServiceAccessorForRunningMultipleSimulations extends DataServiceAccessorForRunningSimulations {
    
	private final RunSimulationsMessage message;
	
	public DataServiceAccessorForRunningMultipleSimulations(RunSimulationsMessage message) {
		this.message = message;
	}
	
	@Override
    public BigInteger[] insertRunIntoDatabase(BigInteger memberOfSimulationGroupIdOrNull) throws DataServiceException, Md5UtilsException {
        return new BigInteger[0];
    }
}
