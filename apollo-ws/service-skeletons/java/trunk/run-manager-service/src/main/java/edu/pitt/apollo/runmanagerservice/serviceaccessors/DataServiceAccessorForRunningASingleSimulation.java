package edu.pitt.apollo.runmanagerservice.serviceaccessors;


import edu.pitt.apollo.runmanagerservice.exception.DataServiceException;
import edu.pitt.apollo.simulator_service_types.v3_0_0.RunSimulationMessage;
import java.math.BigInteger;

/**
 * Created by jdl50 on 5/21/15.
 */
public class DataServiceAccessorForRunningASingleSimulation extends DataServiceAccessorForRunningSimulations {
    
	private final RunSimulationMessage message;
	
	public DataServiceAccessorForRunningASingleSimulation(RunSimulationMessage message) {
		this.message = message;
	}
	
	@Override
    public BigInteger[] insertRunIntoDatabase(BigInteger memberOfSimulationGroupIdOrNull) throws DataServiceException {
        return connector.addSimulationRun(message, memberOfSimulationGroupIdOrNull);
    }
}
