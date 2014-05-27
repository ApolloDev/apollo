package edu.pitt.apollo.apolloclient;

import java.math.BigInteger;

import edu.pitt.apollo.apolloclient.TutorialWebServiceClient.VisualizerIdentificationEnum;
import edu.pitt.apollo.types.v2_0_1.ApolloSoftwareTypeEnum;
import edu.pitt.apollo.types.v2_0_1.Authentication;
import edu.pitt.apollo.types.v2_0_1.RunIdentificationAndLabel;
import edu.pitt.apollo.types.v2_0_1.RunVisualizationMessage;
import edu.pitt.apollo.types.v2_0_1.SoftwareIdentification;

public class ApolloTypeFactory {
	public static final String REQUESTER_ID = "TutorialUser";
	public static final String REQUESTER_PASSWORD = "TutorialPassword";
	
	protected static Authentication getAuthentication(String requesterId, String requesterPassword) {
		Authentication auth = new Authentication();
		auth.setRequesterId(requesterId);
		auth.setRequesterPassword(requesterPassword);
		return auth;
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
	
	protected static RunVisualizationMessage getRunVisualizationMessage(
			RunIdentificationAndLabel runIdentficationAndLabel,
			VisualizerIdentificationEnum visualizerIdentificationEnum) {
		Authentication visualizerAuthentication = getAuthentication(REQUESTER_ID, REQUESTER_PASSWORD);

		return getRunVisualizationMessage(visualizerAuthentication,
				getSoftwareIdentificationForVisualizer(visualizerIdentificationEnum),
				runIdentficationAndLabel);

	}
	
	public static SoftwareIdentification getSoftwareIdentificationForVisualizer(
			VisualizerIdentificationEnum visualizerEnum) {

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
}
