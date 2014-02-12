package edu.pitt.apollo.apolloclient;

import java.net.MalformedURLException;
import java.util.List;

import edu.pitt.apollo.types.v2_0.GetPopulationAndEnvironmentCensusResult;
import edu.pitt.apollo.types.v2_0.GetScenarioLocationCodesSupportedBySimulatorResult;
import edu.pitt.apollo.types.v2_0.PopulationAndEnvironmentCensus;

public class TutorialChapter6_PopulationAndEnvironmentCensusExample extends TutorialChapter2_BasicRunSimulationExample {

	public TutorialChapter6_PopulationAndEnvironmentCensusExample() throws MalformedURLException {
		super();
	}

	public void getPopulationAndEnvironmentCensus(String apolloLocationCode) {
		
		//first, make sure the apolloLocationCode is supported by the simulator
		GetScenarioLocationCodesSupportedBySimulatorResult result = getPort()
				.getScenarioLocationCodesSupportedBySimulator(getSoftwareIdentificationForSimulator());

		System.out.println("Call to getScenarioLocationCodesSupportedBySimulator() returned with result: "
				+ result.getMethodCallStatus().getStatus());

		List<String> locationCodes = result.getLocationCodes();
		for (int i = 0; i < locationCodes.size(); i++) {
			if (locationCodes.get(i).equals(apolloLocationCode)) { // the location code is supported!
				System.out.println("PopulationAndEnvironmentCensus for ApolloLocationCode " + apolloLocationCode
						+ " is:");

				GetPopulationAndEnvironmentCensusResult getPopulationAndEnvironmentCensusResult = getPort()
						.getPopulationAndEnvironmentCensus(getSoftwareIdentificationForSimulator(), apolloLocationCode);

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
		System.out.println(indent + "Location Code: " + populationAndEnvironmentCensus.getLocation().getLocationCode());
		System.out.println(indent + "Date: " + populationAndEnvironmentCensus.getDate());
		System.out.println(indent + "Name of Administrative Unit: "
				+ populationAndEnvironmentCensus.getNameOfAdministativeUnit());

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
