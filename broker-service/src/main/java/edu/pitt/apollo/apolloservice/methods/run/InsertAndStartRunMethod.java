package edu.pitt.apollo.apolloservice.methods.run;

import edu.pitt.apollo.ApolloServiceQueue;
import edu.pitt.apollo.apolloservice.thread.WaitForTranslationAndStartRunThread;
import edu.pitt.apollo.exception.RunManagementException;
import edu.pitt.apollo.services_common.v4_0_1.*;

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
		InsertRunResult insertRunResult;
		try {
			insertRunResult = connector.insertRun(message, authentication);
		} catch (RunManagementException ex) {
			status = getFailedMethodCallStatus("Exception inserting run: " + ex.getMessage());
			runResult.setMethodCallStatus(status);
			return runResult;
		}

		status = new MethodCallStatus();
		status.setStatus(MethodCallStatusEnum.STAGING);
		if (!insertRunResult.isRunCached()) {
			WaitForTranslationAndStartRunThread thread = new WaitForTranslationAndStartRunThread(insertRunResult.getRunId(), queue,
					getSuccessfulMethodCallStatus(), runManagerServiceUrl, authentication);
			queue.addThreadToQueueAndRun(thread);

			status.setMessage("The run is being staged");
		} else {
			status.setMessage("The run is cached");
		}

		runResult.setRunId(insertRunResult.getRunId());
		runResult.setMethodCallStatus(status);

		return runResult;
	}

	protected abstract MethodCallStatus getSuccessfulMethodCallStatus();
}
