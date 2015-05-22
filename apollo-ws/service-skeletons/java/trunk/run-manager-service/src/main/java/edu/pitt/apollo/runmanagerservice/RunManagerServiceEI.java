package edu.pitt.apollo.runmanagerservice;

import edu.pitt.apollo.apollo_service_types.v3_0_0.RunSimulationsMessage;
import edu.pitt.apollo.services_common.v3_0_0.*;
import edu.pitt.apollo.simulator_service_types.v3_0_0.RunSimulationMessage;
import edu.pitt.apollo.synthetic_population_service_types.v3_0_0.RunSyntheticPopulationGenerationMessage;
import edu.pitt.apollo.synthetic_population_service_types.v3_0_0.SyntheticPopulationGenerationResult;
import edu.pitt.apollo.visualizer_service_types.v3_0_0.RunVisualizationMessage;

import java.math.BigInteger;

public interface RunManagerServiceEI {

    public SyntheticPopulationGenerationResult runSyntheticPopulationGeneration(RunSyntheticPopulationGenerationMessage runSyntheticPopulationGenerationMessage);

    public RunResult runSimulation(BigInteger runIdentification, Authentication authentication);

    public RunResult addSimulation(RunSimulationMessage runSimulationMessage);

    public RunResult addSimulations(RunSimulationsMessage runSimulationsMessage);

    public RunResult runSimulations(BigInteger batchRunIdentificiation, Authentication authentication);

    public TerminteRunResult terminateRun(TerminateRunRequest terminateRunRequest);

    public TerminteRunResult terminateRuns(TerminateRunRequest terminateRunRequest);

    public RunResult runVisualization(BigInteger runIdentification, Authentication authentication);

    public RunResult addVisualization(RunVisualizationMessage runVisualizationMessage);
}