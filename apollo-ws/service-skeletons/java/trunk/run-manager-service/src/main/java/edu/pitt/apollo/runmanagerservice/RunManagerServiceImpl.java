package edu.pitt.apollo.runmanagerservice;

import edu.pitt.apollo.apollo_service_types.v3_0_0.RunSimulationsMessage;
import edu.pitt.apollo.runmanagerservice.serviceaccessors.DataServiceAccessor;
import edu.pitt.apollo.services_common.v3_0_0.Authentication;
import edu.pitt.apollo.services_common.v3_0_0.RunResult;
import edu.pitt.apollo.services_common.v3_0_0.TerminateRunRequest;
import edu.pitt.apollo.services_common.v3_0_0.TerminteRunResult;
import edu.pitt.apollo.simulator_service_types.v3_0_0.RunSimulationMessage;
import edu.pitt.apollo.synthetic_population_service_types.v3_0_0.RunSyntheticPopulationGenerationMessage;
import edu.pitt.apollo.synthetic_population_service_types.v3_0_0.SyntheticPopulationGenerationResult;
import edu.pitt.apollo.visualizer_service_types.v3_0_0.RunVisualizationMessage;

import java.math.BigInteger;

/**
 * Created by jdl50 on 6/3/15.
 */
public class RunManagerServiceImpl implements RunManagerServiceEI {



    @Override
    public SyntheticPopulationGenerationResult runSyntheticPopulationGeneration(RunSyntheticPopulationGenerationMessage runSyntheticPopulationGenerationMessage) {
        return null;
    }

    @Override
    public RunResult addSimulation(RunSimulationMessage runSimulationMessage) {
        DataServiceAccessor dataServiceAccessor = new DataServiceAccessor();
        dataServiceAccessor.insertRun(runSimulationMessage)
    }

    @Override
    public RunResult runSimulation(BigInteger runIdentification, Authentication authentication) {
        return null;
    }

    @Override
    public TerminteRunResult terminateRun(TerminateRunRequest terminateRunRequest) {
        return null;
    }

    @Override
    public RunResult addSimulations(RunSimulationsMessage runSimulationsMessage) {
        return null;
    }

    @Override
    public RunResult runSimulations(BigInteger batchRunIdentificiation, Authentication authentication) {
        return null;
    }

    @Override
    public TerminteRunResult terminateRuns(TerminateRunRequest terminateRunRequest) {
        return null;
    }

    @Override
    public RunResult runVisualization(BigInteger runIdentification, Authentication authentication) {
        return null;
    }

    @Override
    public RunResult addVisualization(RunVisualizationMessage runVisualizationMessage) {
        return null;
    }

    @Override
    public TerminateRunRequest terminateVisualization(TerminateRunRequest terminateRunRequest) {
        return null;
    }
}
