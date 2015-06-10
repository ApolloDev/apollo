package edu.pitt.apollo.apolloservice.methods.run;

import edu.pitt.apollo.ApolloServiceQueue;
import edu.pitt.apollo.services_common.v3_0_0.MethodCallStatus;
import edu.pitt.apollo.services_common.v3_0_0.MethodCallStatusEnum;

/**
 *
 * @author nem41
 */
public class InsertAndStartDataServiceJobMethod extends InsertAndStartRunMethod {

	public InsertAndStartDataServiceJobMethod(String runManagerServiceUrl, ApolloServiceQueue queue) {
		super(runManagerServiceUrl, queue);
	}

	@Override
	protected MethodCallStatus getSuccessfulMethodCallStatus() {
		MethodCallStatus status = new MethodCallStatus();
		status.setMessage("The data service has been called to start the job");
		status.setStatus(MethodCallStatusEnum.CALLED_DATA_SERVICE);
		return status;
	}

}
