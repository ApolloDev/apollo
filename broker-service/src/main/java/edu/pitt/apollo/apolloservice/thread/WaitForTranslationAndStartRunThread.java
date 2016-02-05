package edu.pitt.apollo.apolloservice.thread;

import edu.pitt.apollo.ApolloServiceQueue;
import edu.pitt.apollo.ApolloServiceThread;
import edu.pitt.apollo.connector.RunManagerServiceConnector;
import edu.pitt.apollo.exception.DataServiceException;
import edu.pitt.apollo.exception.RunManagementException;
import edu.pitt.apollo.exception.JobRunningServiceException;
import edu.pitt.apollo.restrunmanagerserviceconnector.RestRunManagerServiceConnector;
import edu.pitt.apollo.services_common.v4_0.Authentication;
import edu.pitt.apollo.services_common.v4_0.MethodCallStatus;
import edu.pitt.apollo.services_common.v4_0.MethodCallStatusEnum;
import java.math.BigInteger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author nem41
 */
public class WaitForTranslationAndStartRunThread extends ApolloServiceThread {
	
	static org.slf4j.Logger logger = LoggerFactory.getLogger(WaitForTranslationAndStartRunThread.class);
	
	private static final long THREAD_SLEEP_TIME = 5000;
	private final String runManagerServiceUrl;
	private final Authentication authentication;
	private final MethodCallStatus statusOnceServiceCalled;
	
	public WaitForTranslationAndStartRunThread(BigInteger runId, ApolloServiceQueue queue,
			MethodCallStatus statusOnceServiceCalled, String runManagerServiceUrl, Authentication authentication) {
		super(runId, queue);
		this.runManagerServiceUrl = runManagerServiceUrl;
		this.authentication = authentication;
		this.statusOnceServiceCalled = statusOnceServiceCalled;
	}
	
	@Override
	public void runApolloService() {
		RunManagerServiceConnector connector = new RestRunManagerServiceConnector(runManagerServiceUrl);
		MethodCallStatus statusOfRun;
		boolean runTranslated = false;
		try {
			while (!runTranslated) {
				
				try {
					statusOfRun = connector.getRunStatus(runId, authentication);
				} catch (DataServiceException ex) {
					connector.updateStatusOfRun(runId, MethodCallStatusEnum.FAILED, "Could not get status for run " + runId + ": " + ex.getMessage(), authentication);
					return;
				}
				MethodCallStatusEnum statusEnum = statusOfRun.getStatus();
				switch (statusEnum) {
					
					case FAILED:
						return; // can't do anything else
					case TRANSLATION_COMPLETED:
						runTranslated = true;
						break;
					default: {
						try {
							Thread.sleep(THREAD_SLEEP_TIME);
						} catch (InterruptedException ex) {
						}
					}
					break;
				}
			}

			try {
                // update status first, or it may override the service we are trying to call updating the status
                //
                // not updating status now, because the run manager looks for TRANSLATION_COMPLETED before starting
                // connector.updateStatusOfRun(runId, statusOnceServiceCalled.getStatus(), statusOnceServiceCalled.getMessage(), authentication);
                connector.run(runId, authentication);
			} catch (JobRunningServiceException ex) {
				connector.updateStatusOfRun(runId, MethodCallStatusEnum.FAILED, "The service call failed for run ID " + runId + ": " + ex.getMessage(), authentication);
			}
		} catch (RunManagementException ex) {
			// this means the run status could not be updated
			logger.error("Exception updating status for run " + runId + ": " + ex.getMessage());
		}
	}
	
}
