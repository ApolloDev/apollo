package edu.pitt.apollo.apolloservice.methods.run;

import edu.pitt.apollo.ApolloServiceQueue;
import edu.pitt.apollo.apolloservice.thread.WaitForTranslationAndStartRunThread;
import edu.pitt.apollo.exception.RunManagementException;
import edu.pitt.apollo.exception.SimulatorServiceException;
import edu.pitt.apollo.services_common.v3_0_0.Authentication;
import edu.pitt.apollo.services_common.v3_0_0.MethodCallStatus;
import edu.pitt.apollo.services_common.v3_0_0.MethodCallStatusEnum;
import edu.pitt.apollo.services_common.v3_0_0.RunMessage;
import edu.pitt.apollo.services_common.v3_0_0.RunResult;
import java.math.BigInteger;

/**
 *
 * @author nem41
 */
public abstract class InsertAndStartRunMethod extends BaseRunManagementServiceAccessorMethod {

	private final ApolloServiceQueue queue;

	public InsertAndStartRunMethod(String runManagerServiceUrl, ApolloServiceQueue queue) {
		super(runManagerServiceUrl);
		this.queue = queue;
	}

	public RunResult insertAndStartRun(RunMessage message, Authentication authentication) {

		RunResult runResult = new RunResult();
		MethodCallStatus status;
		BigInteger runId;
		try {
			runId = connector.insertRun(message);
		} catch (RunManagementException ex) {
			status = getFailedMethodCallStatus("Exception inserting run: " + ex.getMessage());
			runResult.setMethodCallStatus(status);
			return runResult;
		}

		WaitForTranslationAndStartRunThread thread = new WaitForTranslationAndStartRunThread(runId, queue,
				getSuccessfulMethodCallStatus(), runManagerServiceUrl, authentication);
		queue.addThreadToQueueAndRun(thread);

		status = new MethodCallStatus();
		status.setStatus(MethodCallStatusEnum.STAGING);
		status.setMessage("The run is being staged");

		runResult.setRunId(runId);
		runResult.setMethodCallStatus(status);
		return runResult;
	}

	protected abstract MethodCallStatus getSuccessfulMethodCallStatus();
}
