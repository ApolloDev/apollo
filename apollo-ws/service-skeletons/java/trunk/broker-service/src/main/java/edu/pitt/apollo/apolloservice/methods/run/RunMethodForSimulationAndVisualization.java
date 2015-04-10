package edu.pitt.apollo.apolloservice.methods.run;

import edu.pitt.apollo.apolloservice.types.ReturnObjectForRun;
import edu.pitt.apollo.db.ApolloDbUtils;
import edu.pitt.apollo.services_common.v3_0_0.Authentication;
import edu.pitt.apollo.services_common.v3_0_0.RunResult;
import edu.pitt.apollo.services_common.v3_0_0.SoftwareIdentification;

/**
 *
 * Author: Nick Millett
 * Email: nick.millett@gmail.com
 * Date: Jan 22, 2015
 * Time: 12:17:43 PM
 * Class: RunMethodForSimulationAndVisualization
 */
public class RunMethodForSimulationAndVisualization extends RunMethod {

	public RunMethodForSimulationAndVisualization(Authentication authentication, SoftwareIdentification softwareIdentification, ApolloDbUtils dbUtils, Object message) {
		super(authentication, softwareIdentification, message);
	}

	@Override
	public ReturnObjectForRun getReturnObjectForRun(RunResult runResult) {
		ReturnObjectForRun returnObj = new ReturnObjectForRun();
		returnObj.setStatus(runResult.getMethodCallStatus());
		returnObj.setObjectToReturnFromBroker(runResult);
		return returnObj;
	}

}
