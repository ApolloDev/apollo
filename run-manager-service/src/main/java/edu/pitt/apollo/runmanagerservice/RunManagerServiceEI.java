package edu.pitt.apollo.runmanagerservice;

import edu.pitt.apollo.apollo_service_types.v4_0_1.RunSimulationsMessage;
import edu.pitt.apollo.services_common.v4_0_1.Authentication;
import edu.pitt.apollo.services_common.v4_0_1.RunResult;
import edu.pitt.apollo.services_common.v4_0_1.TerminateRunRequest;
import edu.pitt.apollo.services_common.v4_0_1.TerminteRunResult;
import edu.pitt.apollo.simulator_service_types.v4_0_1.RunSimulationMessage;
import edu.pitt.apollo.synthetic_population_service_types.v4_0_1.RunSyntheticPopulationGenerationMessage;
import edu.pitt.apollo.synthetic_population_service_types.v4_0_1.SyntheticPopulationGenerationResult;
import edu.pitt.apollo.visualizer_service_types.v4_0_1.RunVisualizationMessage;

import java.math.BigInteger;

public interface RunManagerServiceEI {

    public SyntheticPopulationGenerationResult runSyntheticPopulationGeneration(RunSyntheticPopulationGenerationMessage runSyntheticPopulationGenerationMessage);

    public RunResult addSimulation(RunSimulationMessage runSimulationMessage);
    public RunResult runSimulation(BigInteger runIdentification, Authentication authentication);
    public TerminteRunResult terminateRun(TerminateRunRequest terminateRunRequest);

    public RunResult addSimulations(RunSimulationsMessage runSimulationsMessage);
    public RunResult runSimulations(BigInteger batchRunIdentificiation, Authentication authentication);
    public TerminteRunResult terminateRuns(TerminateRunRequest terminateRunRequest);

    public RunResult runVisualization(BigInteger runIdentification, Authentication authentication);
    public RunResult addVisualization(RunVisualizationMessage runVisualizationMessage);
    public TerminateRunRequest terminateVisualization(TerminateRunRequest terminateRunRequest);
}