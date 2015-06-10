package edu.pitt.apollo.apolloservice.methods;

import edu.pitt.apollo.services_common.v3_0_0.MethodCallStatus;
import edu.pitt.apollo.services_common.v3_0_0.MethodCallStatusEnum;

/**
 *
 * @author nem41
 */
public class BaseMethod {

	public BaseMethod(String serviceUrl) {

	}

	protected MethodCallStatus getFailedMethodCallStatus(String message) {
		MethodCallStatus status = new MethodCallStatus();
		status.setStatus(MethodCallStatusEnum.FAILED);
		status.setMessage(message);
		return status;
	}

}
