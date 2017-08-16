package edu.pitt.apollo.apolloservice.methods.run;

import edu.pitt.apollo.ApolloServiceQueue;
import edu.pitt.apollo.services_common.v4_0_2.MethodCallStatus;
import edu.pitt.apollo.services_common.v4_0_2.MethodCallStatusEnum;

/**
 *
 * @author nem41
 */
public class InsertAndStartQueryMethod extends InsertAndStartRunMethod {

	public InsertAndStartQueryMethod(String runManagerServiceUrl, ApolloServiceQueue queue) {
		super(runManagerServiceUrl, queue);
	}

	@Override
	protected MethodCallStatus getSuccessfulMethodCallStatus() {
		MethodCallStatus status = new MethodCallStatus();
		status.setMessage("The query service has been called");
		status.setStatus(MethodCallStatusEnum.CALLED_QUERY_SERVICE);
		return status;
	}

}
