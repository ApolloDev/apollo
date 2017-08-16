package edu.pitt.apollo.apolloclient.tutorial;

import edu.pitt.apollo.examples.ExampleInfectiousDiseaseScenario;
import edu.pitt.apollo.services_common.v4_0_2.Authentication;
import edu.pitt.apollo.services_common.v4_0_2.RunIdentificationAndLabel;
import edu.pitt.apollo.simulator_service_types.v4_0_2.RunSimulationMessage;
import edu.pitt.apollo.types.v4_0_2.ApolloSoftwareTypeEnum;
import edu.pitt.apollo.types.v4_0_2.SimulatorTimeSpecification;
import edu.pitt.apollo.types.v4_0_2.SoftwareIdentification;
import edu.pitt.apollo.types.v4_0_2.UnitOfTimeEnum;
import edu.pitt.apollo.visualizer_service_types.v4_0_2.RunVisualizationMessage;

import java.math.BigInteger;

;

public class ApolloServiceTypeFactory {

	public static final String REQUESTER_ID = "apollo_demo";
	public static final String REQUESTER_PASSWORD = "apollo_demo";
	
	public static final BigInteger SIMULATION_RUN_LENGTH = new BigInteger("90");
	public static final UnitOfTimeEnum UNIT_OF_TIME_FOR_SIMULATOR_TIME_STEP = UnitOfTimeEnum.DAY;
	public static final double NUMBER_OF_UNITS_OF_TIME_IN_ONE_SIMULATOR_TIME_STEP = 1.0;

	public enum VisualizerIdentificationEnum {
		GAIA, TIME_SERIES
	}

	public enum SimulatorIdentificationEnum {
		FRED, SEIR
	}

	public static RunVisualizationMessage getRunVisualizationMessage(SoftwareIdentification visualizerSoftwareIdentification,
			RunIdentificationAndLabel[] runIdentificationsAndLabels) {
		RunVisualizationMessage runVisualizationMessage = new RunVisualizationMessage();
		runVisualizationMessage.setSoftwareIdentification(visualizerSoftwareIdentification);
		for (RunIdentificationAndLabel runIdentificationAndLabel : runIdentificationsAndLabels) {
			runVisualizationMessage.getSimulationRunIds().add(runIdentificationAndLabel);
		}
		return runVisualizationMessage;
	}

	private static SimulatorTimeSpecification getSimulatorTimeSpecification(BigInteger simulationRunLength,
			UnitOfTimeEnum unitOfTimeForSimulatorTimeStep, double numberOfUnitsOfTimeInOneSimulatorTimeStep) {
		SimulatorTimeSpecification timeSpec = new SimulatorTimeSpecification();
		timeSpec.setRunLength(simulationRunLength);
		timeSpec.setUnitOfTimeForSimulatorTimeStep(unitOfTimeForSimulatorTimeStep);
		timeSpec.setNumberOfUnitsOfTimeInOneSimulatorTimeStep(numberOfUnitsOfTimeInOneSimulatorTimeStep);
		return timeSpec;
	}

	private static Authentication getAuthentication() {
		Authentication auth = new Authentication();
		return auth;
	}

	public static RunSimulationMessage getMinimalistRunSimulationMessage(SimulatorIdentificationEnum simulator) {
		RunSimulationMessage message = new RunSimulationMessage();
		message.setInfectiousDiseaseScenario(ExampleInfectiousDiseaseScenario.getScenario());
		message.setSoftwareIdentification(getSoftwareIdentificationForSimulator(simulator));
		message.setSimulatorTimeSpecification(getSimulatorTimeSpecification(SIMULATION_RUN_LENGTH,
				UNIT_OF_TIME_FOR_SIMULATOR_TIME_STEP, NUMBER_OF_UNITS_OF_TIME_IN_ONE_SIMULATOR_TIME_STEP));
		return message;
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
			softwareId.setSoftwareVersion("3.0");
			break;
		default:
			softwareId = null;
			System.out.println("The tutorial does not support the following simulatior " + simulatorEnum.toString() + ".");
			System.exit(-1);
		}
		return softwareId;
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
			System.out.println("The tutorial does not support the following visualizer " + visualizerEnum.toString() + ".");
			System.exit(-1);
		}
		return visualizerId;
	}

}
