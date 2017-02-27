package edu.pitt.apollo.apolloservice.methods;

import edu.pitt.apollo.services_common.v4_0_1.MethodCallStatus;
import edu.pitt.apollo.services_common.v4_0_1.MethodCallStatusEnum;

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
