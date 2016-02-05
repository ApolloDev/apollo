package edu.pitt.apollo.apolloservice.methods.run;

import edu.pitt.apollo.ApolloServiceQueue;
import edu.pitt.apollo.services_common.v4_0.MethodCallStatus;
import edu.pitt.apollo.services_common.v4_0.MethodCallStatusEnum;

/**
 *
 * @author nem41
 */
public class InsertAndStartSimulationMethod extends InsertAndStartRunMethod {

	public InsertAndStartSimulationMethod(String runManagerServiceUrl, ApolloServiceQueue queue) {
		super(runManagerServiceUrl, queue);
	}

	@Override
	protected MethodCallStatus getSuccessfulMethodCallStatus() {
		MethodCallStatus status = new MethodCallStatus();
		status.setMessage("The simulator has been called");
		status.setStatus(MethodCallStatusEnum.CALLED_SIMULATOR);
		return status;
	}

}
