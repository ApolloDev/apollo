package edu.pitt.apollo.apolloservice.methods.run;

import edu.pitt.apollo.ApolloServiceQueue;
import edu.pitt.apollo.services_common.v3_0_0.MethodCallStatus;
import edu.pitt.apollo.services_common.v3_0_0.MethodCallStatusEnum;

/**
 *
 * @author nem41
 */
public class InsertAndStartVisualizationMethod extends InsertAndStartRunMethod {

	public InsertAndStartVisualizationMethod(String runManagerServiceUrl, ApolloServiceQueue queue) {
		super(runManagerServiceUrl, queue);
	}

	@Override
	protected MethodCallStatus getSuccessfulMethodCallStatus() {
		MethodCallStatus status = new MethodCallStatus();
		status.setMessage("The visualizer has been called");
		status.setStatus(MethodCallStatusEnum.CALLED_VISUALIZER);
		return status;
	}

}
