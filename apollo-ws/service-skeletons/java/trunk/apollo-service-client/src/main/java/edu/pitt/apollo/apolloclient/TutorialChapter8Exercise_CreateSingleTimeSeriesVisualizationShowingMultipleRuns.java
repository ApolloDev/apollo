package edu.pitt.apollo.apolloclient;

import java.net.MalformedURLException;

import edu.pitt.apollo.types.v2_0.MethodCallStatusEnum;
import edu.pitt.apollo.types.v2_0.RunAndSoftwareIdentification;
import edu.pitt.apollo.types.v2_0.RunVisualizationMessage;
import edu.pitt.apollo.types.v2_0.UrlOutputResource;
import edu.pitt.apollo.types.v2_0.VisualizationOptions;
import edu.pitt.apollo.types.v2_0.VisualizerResult;

public class TutorialChapter8Exercise_CreateSingleTimeSeriesVisualizationShowingMultipleRuns extends TutorialChapter2_BasicRunSimulationExample {

	public TutorialChapter8Exercise_CreateSingleTimeSeriesVisualizationShowingMultipleRuns() throws MalformedURLException {
		super();
	}
	
	public void createVisualzation(String ... simulatorRunIds) {
		
		RunVisualizationMessage runVisualizationMessage = new RunVisualizationMessage();
		runVisualizationMessage.setAuthentication(getAuthentication());
		runVisualizationMessage.setVisualizerIdentification(getSoftwareIdentifiationForTimeSeriesVisualizer());
		VisualizationOptions options = new VisualizationOptions();
		
		//pass all runId's to "setRunId()" delimited by the ";" character
		//let's hope there are no semicolons in the runId!
		String runIdString = "";
		for (String simulatorRunId : simulatorRunIds) {
			runIdString += simulatorRunId + ":";
		}
		runIdString = runIdString.substring(0, runIdString.length()-1);
		System.out.println(runIdString);
		options.setRunId(runIdString);
		options.setLocation("42003");
		options.setOutputFormat("default");
		runVisualizationMessage.setVisualizationOptions(options);

		VisualizerResult visualizerResult = getPort().runVisualization(runVisualizationMessage);
				
		
		RunAndSoftwareIdentification runAndSoftwareIdentification = new RunAndSoftwareIdentification();
		runAndSoftwareIdentification.setSoftwareId(getSoftwareIdentifiationForTimeSeriesVisualizer());
		runAndSoftwareIdentification.setRunId(visualizerResult.getRunId());

		if (checkStatusOfWebServiceCall(runAndSoftwareIdentification).getStatus() == MethodCallStatusEnum.COMPLETED) {
			System.out.println("The following resources were returned from the " + getSoftwareIdentifiationForTimeSeriesVisualizer().getSoftwareName() +
					" visualizer:");
			for (UrlOutputResource r : visualizerResult.getVisualizerOutputResource()) {
				System.out.println("\t" + r.getURL());
			}
		}
		
	}

	/**
	 * @param args
	 * @throws MalformedURLException 
	 */
	public static void main(String[] args) throws MalformedURLException {
		TutorialChapter8Exercise_CreateSingleTimeSeriesVisualizationShowingMultipleRuns exercise = new TutorialChapter8Exercise_CreateSingleTimeSeriesVisualizationShowingMultipleRuns();
		//..309 is the non-vaccination run
		//..564 is the run with vaccination
		exercise.createVisualzation("UPitt,PSC,CMU_FRED_2.0.1_i_1392150158", "UPitt,PSC,CMU_FRED_2.0.1_i_1392159091");

	}

}
