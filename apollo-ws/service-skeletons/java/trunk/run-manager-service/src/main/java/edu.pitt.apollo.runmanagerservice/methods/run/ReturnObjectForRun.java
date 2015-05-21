package edu.pitt.apollo.runmanagerservice.methods.run;

import edu.pitt.apollo.services_common.v3_0_0.MethodCallStatus;

/**
 *
 * Author: Nick Millett
 * Email: nick.millett@gmail.com
 * Date: Jan 22, 2015
 * Time: 12:14:53 PM
 * Class: ReturnObjectForRun
 */
public class ReturnObjectForRun {

	private MethodCallStatus status;
	private Object objectToReturnFromBroker;

	/**
	 * @return the status
	 */
	public MethodCallStatus getStatus() {
		return status;
	}

	/**
	 * @param status the status to set
	 */
	public void setStatus(MethodCallStatus status) {
		this.status = status;
	}

	/**
	 * @return the objectToReturnFromBroker
	 */
	public Object getObjectToReturnFromBroker() {
		return objectToReturnFromBroker;
	}

	/**
	 * @param objectToReturnFromBroker the objectToReturnFromBroker to set
	 */
	public void setObjectToReturnFromBroker(Object objectToReturnFromBroker) {
		this.objectToReturnFromBroker = objectToReturnFromBroker;
	}
}
