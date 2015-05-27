package edu.pitt.apollo.runmanagerservice.serviceaccessors;

import edu.pitt.apollo.visualizer_service_types.v3_0_0.RunVisualizationMessage;

import java.math.BigInteger;

/**
 * Created by jdl50 on 5/21/15.
 */
public class DataServiceAccessorForRunningVisualizations extends DataServiceAccessor {

	private final RunVisualizationMessage message;
	
	public DataServiceAccessorForRunningVisualizations(RunVisualizationMessage message) {
		this.message = message;
	}

//    @Override
//    public BigInteger[] insertRunIntoDatabase(BigInteger memberOfSimulationGroupIdOrNull) throws DataServiceException {
//        return null;  //WHERE is this? return connector.addVisualizationRun(message);
//    }
}
