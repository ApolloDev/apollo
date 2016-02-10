package edu.pitt.apollo.apolloservice.methods.run;

import edu.pitt.apollo.connector.RunManagerServiceConnector;
import edu.pitt.apollo.exception.RunManagementException;
import edu.pitt.apollo.services_common.v4_0.Authentication;
import edu.pitt.apollo.services_common.v4_0.MethodCallStatus;
import java.math.BigInteger;

/**
 *
 * @author nem41
 */
public class GetRunStatusMethod extends BaseRunManagementServiceAccessorMethod {

	public GetRunStatusMethod(RunManagerServiceConnector connector) {
		super(connector);
	}
	
	public GetRunStatusMethod(String runManagerServiceUrl) {
		super(runManagerServiceUrl);
	}
	
	public MethodCallStatus getRunStatus(BigInteger runId, Authentication authentication) {
		
		MethodCallStatus status;
		try {
			status = connector.getRunStatus(runId, authentication);
		} catch (RunManagementException ex) {
			status = getFailedMethodCallStatus("Exception getting run status for run: " + ex.getMessage());
		}
		
		return status;
	}
}
