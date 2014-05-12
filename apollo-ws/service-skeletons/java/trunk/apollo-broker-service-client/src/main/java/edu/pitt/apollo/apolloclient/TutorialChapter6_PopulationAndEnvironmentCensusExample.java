package edu.pitt.apollo.apolloclient;

import java.net.MalformedURLException;
import java.util.List;

import edu.pitt.apollo.examples.TutorialChapter2_ExampleConfig;
import edu.pitt.apollo.types.v2_0_1.GetPopulationAndEnvironmentCensusResult;
import edu.pitt.apollo.types.v2_0_1.GetScenarioLocationCodesSupportedBySimulatorResult;
import edu.pitt.apollo.types.v2_0_1.PopulationAndEnvironmentCensus;
import edu.pitt.apollo.types.v2_0_1.SoftwareIdentification;

public class TutorialChapter6_PopulationAndEnvironmentCensusExample extends TutorialChapter2_BasicRunSimulationExample {

	public TutorialChapter6_PopulationAndEnvironmentCensusExample() throws MalformedURLException {
		super();
	}

	public void getPopulationAndEnvironmentCensus(String apolloLocationCode) {
		// first, make sure the apolloLocationCode is supported by the simulator
		SoftwareIdentification softwareIdentification = TutorialChapter2_ExampleConfig.getSoftwareIdentificationForSimulator(); 
		GetScenarioLocationCodesSupportedBySimulatorResult result = getPort().getScenarioLocationCodesSupportedBySimulator(softwareIdentification);
				

		System.out.println("Call to getScenarioLocationCodesSupportedBySimulator() returned with result: "
				+ result.getMethodCallStatus().getStatus());

		List<String> locationCodes = result.getLocationCodes();
		for (int i = 0; i < locationCodes.size(); i++) {
			if (locationCodes.get(i).equals(apolloLocationCode)) { // the
																	// location
																	// code is
																	// supported!
				System.out.println("PopulationAndEnvironmentCensus for ApolloLocationCode " + apolloLocationCode + " is:");

				GetPopulationAndEnvironmentCensusResult getPopulationAndEnvironmentCensusResult = getPort()
						.getPopulationAndEnvironmentCensus(softwareIdentification, apolloLocationCode);

				PopulationAndEnvironmentCensus populationAndEnvironmentCensus = getPopulationAndEnvironmentCensusResult
						.getPopulationAndEnvironmentCensus();

				parsePopululationAndEnvironmentCensus(populationAndEnvironmentCensus, 0);
			}
		}
	}

	private void parsePopululationAndEnvironmentCensus(PopulationAndEnvironmentCensus populationAndEnvironmentCensus,
			int recursionLevel) {
		String indent = "";
		for (int i = 0; i < recursionLevel; i++)
			indent += "\t";

		System.out.println(indent + "Description: " + populationAndEnvironmentCensus.getDescription());
		System.out.println(indent + "Location Code: " + populationAndEnvironmentCensus.getLocation().getApolloLocationCode());
		System.out.println(indent + "Date: " + populationAndEnvironmentCensus.getReferenceDate());
		System.out
				.println(indent + "Name of Administrative Unit: " + populationAndEnvironmentCensus.getNameOfAdministativeUnit());

		System.out.println(indent + "Number of People: " + populationAndEnvironmentCensus.getNumberOfPeople());
		System.out.println(indent + "Number of Schools: " + populationAndEnvironmentCensus.getNumberOfSchools());
		System.out.println(indent + "Number of Workplaces: " + populationAndEnvironmentCensus.getNumberOfWorkplaces());
		System.out.println(indent + "Number of Schools:" + populationAndEnvironmentCensus.getNumberOfSchools());
		for (int i = 0; i < populationAndEnvironmentCensus.getSubLocationCensuses().size(); i++) {
			System.out.println(indent + "Subpopulation: " + i);
			parsePopululationAndEnvironmentCensus(populationAndEnvironmentCensus.getSubLocationCensuses().get(i),
					recursionLevel + 1);
		}
		System.out.println();
	}

	public static void main(String[] args) throws MalformedURLException {
		new TutorialChapter6_PopulationAndEnvironmentCensusExample().getPopulationAndEnvironmentCensus("42");
	}
}
