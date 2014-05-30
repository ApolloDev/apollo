package edu.pitt.apollo.apolloclient;

import java.math.BigInteger;

import edu.pitt.apollo.examples.runsimulationmessages.ExampleInfectiousDiseaseScenario;
import edu.pitt.apollo.types.v2_0_1.ApolloSoftwareTypeEnum;
import edu.pitt.apollo.types.v2_0_1.Authentication;
import edu.pitt.apollo.types.v2_0_1.RunIdentificationAndLabel;
import edu.pitt.apollo.types.v2_0_1.RunSimulationMessage;
import edu.pitt.apollo.types.v2_0_1.RunVisualizationMessage;
import edu.pitt.apollo.types.v2_0_1.SimulatorTimeSpecification;
import edu.pitt.apollo.types.v2_0_1.SoftwareIdentification;
import edu.pitt.apollo.types.v2_0_1.UnitOfTimeEnum;

public class ApolloServiceTypeFactory {

	public static final String REQUESTER_ID = "TutorialUser";
	public static final String REQUESTER_PASSWORD = "TutorialPassword";

	public static final BigInteger SIMULATION_RUN_LENGTH = new BigInteger("90");
	public static final UnitOfTimeEnum UNIT_OF_TIME_FOR_SIMULATOR_TIME_STEP = UnitOfTimeEnum.DAY;
	public static final double NUMBER_OF_UNITS_OF_TIME_IN_ONE_SIMULATOR_TIME_STEP = 1.0;

	public enum VisualizerIdentificationEnum {
		GAIA, TIME_SERIES
	}

	public enum SimulatorIdentificationEnum {
		FRED, SEIR
	}

	public static SoftwareIdentification getSoftwareIdentificationForSimulator(SimulatorIdentificationEnum simulatorEnum) {

		SoftwareIdentification softwareId = new SoftwareIdentification();
		softwareId.setSoftwareType(ApolloSoftwareTypeEnum.SIMULATOR);

		switch (simulatorEnum) {
		case FRED:
			softwareId.setSoftwareDeveloper("UPitt,PSC,CMU");
			softwareId.setSoftwareName("FRED");
			softwareId.setSoftwareVersion("2.0.1_i");
			break;
		case SEIR:
			softwareId.setSoftwareDeveloper("UPitt");
			softwareId.setSoftwareName("SEIR");
			softwareId.setSoftwareVersion("1.0");
			break;
		default:
			softwareId = null;
			System.out.println("Unsupported simulatior " + simulatorEnum.toString() + ".");
			System.exit(-1);
		}
		return softwareId;
	}

	public static Authentication getAuthentication(String requesterId, String requesterPassword) {
		Authentication auth = new Authentication();
		auth.setRequesterId(requesterId);
		auth.setRequesterPassword(requesterPassword);
		return auth;
	}

	public static Authentication getAuthentication() {
		Authentication auth = new Authentication();
		auth.setRequesterId(REQUESTER_ID);
		auth.setRequesterPassword(REQUESTER_PASSWORD);
		return auth;
	}

	public static RunVisualizationMessage getRunVisualizationMessage(RunIdentificationAndLabel runIdentficationAndLabel,
			VisualizerIdentificationEnum visualizerIdentificationEnum) {
		Authentication visualizerAuthentication = getAuthentication(REQUESTER_ID, REQUESTER_PASSWORD);

		return getRunVisualizationMessage(visualizerAuthentication,
				getSoftwareIdentificationForVisualizer(visualizerIdentificationEnum), runIdentficationAndLabel);

	}

	public static SoftwareIdentification getSoftwareIdentificationForVisualizer(VisualizerIdentificationEnum visualizerEnum) {

		SoftwareIdentification visualizerId = new SoftwareIdentification();
		visualizerId.setSoftwareType(ApolloSoftwareTypeEnum.VISUALIZER);

		switch (visualizerEnum) {
		case GAIA:
			visualizerId.setSoftwareName("GAIA");
			visualizerId.setSoftwareVersion("1.0");
			visualizerId.setSoftwareDeveloper("PSC");
			break;
		case TIME_SERIES:
			visualizerId.setSoftwareName("Time Series Visualizer");
			visualizerId.setSoftwareVersion("1.0");
			visualizerId.setSoftwareDeveloper("UPitt");
			break;
		default:
			visualizerId = null;
			System.out.println("Unsupported visualizer " + visualizerEnum.toString() + ".");
			System.exit(-1);
		}
		return visualizerId;
	}

	public static RunSimulationMessage getDefaultRunSimulationMessage() {
		RunSimulationMessage message = new RunSimulationMessage();
		message.setInfectiousDiseaseScenario(ExampleInfectiousDiseaseScenario.getScenario());
		message.setAuthentication(getAuthentication(REQUESTER_ID, REQUESTER_PASSWORD));
		message.setSimulatorIdentification(getSoftwareIdentificationForSimulator(SimulatorIdentificationEnum.FRED));
		message.setSimulatorTimeSpecification(getSimulatorTimeSpecification(SIMULATION_RUN_LENGTH,
				UNIT_OF_TIME_FOR_SIMULATOR_TIME_STEP, NUMBER_OF_UNITS_OF_TIME_IN_ONE_SIMULATOR_TIME_STEP));
		return message;
	}

	private static RunVisualizationMessage getRunVisualizationMessage(Authentication visualizerAuthentication,
			SoftwareIdentification visualizerSoftwareIdentification, RunIdentificationAndLabel... runIdentificationsAndLabels) {
		RunVisualizationMessage runVisualizationMessage = new RunVisualizationMessage();
		runVisualizationMessage.setAuthentication(visualizerAuthentication);
		runVisualizationMessage.setVisualizerIdentification(visualizerSoftwareIdentification);
		for (RunIdentificationAndLabel runIdentificationAndLabel : runIdentificationsAndLabels) {
			runVisualizationMessage.getSimulationRunIds().add(runIdentificationAndLabel);
		}
		return runVisualizationMessage;
	}

	protected static SimulatorTimeSpecification getSimulatorTimeSpecification(BigInteger simulationRunLength,
			UnitOfTimeEnum unitOfTimeForSimulatorTimeStep, double numberOfUnitsOfTimeInOneSimulatorTimeStep) {
		SimulatorTimeSpecification timeSpec = new SimulatorTimeSpecification();
		timeSpec.setRunLength(simulationRunLength);
		timeSpec.setUnitOfTimeForSimulatorTimeStep(unitOfTimeForSimulatorTimeStep);
		timeSpec.setNumberOfUnitsOfTimeInOneSimulatorTimeStep(numberOfUnitsOfTimeInOneSimulatorTimeStep);
		return timeSpec;
	}
}
