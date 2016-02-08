package edu.pitt.apollo.runmanagerservice.thread;

import edu.pitt.apollo.apollo_service_types.v4_0.RunInfectiousDiseaseTransmissionExperimentMessage;
import edu.pitt.apollo.exception.DataServiceException;
import edu.pitt.apollo.exception.JobRunningServiceException;
import edu.pitt.apollo.exception.RunManagementException;
import edu.pitt.apollo.runmanagerservice.datastore.accessors.DatastoreAccessImpl;
import edu.pitt.apollo.runmanagerservice.methods.run.ApolloServiceErrorHandler;
import edu.pitt.apollo.runmanagerservice.serviceaccessors.JobRunningServiceAccessor;
import edu.pitt.apollo.services_common.v4_0.Authentication;
import edu.pitt.apollo.services_common.v4_0.MethodCallStatusEnum;
import edu.pitt.apollo.types.v4_0.SoftwareIdentification;

import java.math.BigInteger;

/**
 * Author: Nick Millett Email: nick.millett@gmail.com Date: Apr 3, 2014 Time: 11:56:31 AM Class: RunSimulationThread IDE: NetBeans 6.9.1
 */
public class RunInfectiousDiseaseTransmissionExperimentThread extends RunApolloServiceThread {

    private final RunInfectiousDiseaseTransmissionExperimentMessage message;

	public RunInfectiousDiseaseTransmissionExperimentThread(BigInteger runId, RunInfectiousDiseaseTransmissionExperimentMessage message, Authentication authentication) {
		super(runId, null, authentication);
        this.message = message;
	}

	@Override
	public void run() {

		DatastoreAccessImpl dataServiceAccessor = new DatastoreAccessImpl();

		String url;

        // for now use the first software listed in the message, should probably fix this in the future
        SoftwareIdentification softwareId
                = message.getInfectiousDiseaseTransmissionExperimentSpecification().getInfectiousDiseaseTransmissionModelIds().get(0);

		try {
			url = dataServiceAccessor.getURLForSoftwareIdentification(softwareId, authentication);
		} catch (DataServiceException e) {
			ApolloServiceErrorHandler.reportError("Error getting URL for software identification, error was:" + e.getMessage(), runId, authentication);
			return;
		}

		try {
            dataServiceAccessor.updateStatusOfRun(runId, MethodCallStatusEnum.CALLED_SIMULATOR,
                    "Attempting to call simulator", authentication);
			JobRunningServiceAccessor simulatorServiceAccessor = new JobRunningServiceAccessor(url, softwareId);
			simulatorServiceAccessor.run(runId, authentication);
		} catch (JobRunningServiceException | RunManagementException ex) {
			ApolloServiceErrorHandler.reportError("Error calling simulator, error was:" + ex.getMessage(), runId, authentication);
			return;
		}

		try {
			dataServiceAccessor.updateLastServiceToBeCalledForRun(runId, softwareId, authentication);
		} catch (DataServiceException e) {
			ApolloServiceErrorHandler.reportError("Unable to update last service to be called for run, error was:" + e.getMessage(), runId, authentication);
		}
	}
}
