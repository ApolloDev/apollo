package edu.pitt.apollo.apolloclient;

import java.net.MalformedURLException;

public class TutorialChapter6_PopulationAndEnvironmentCensusExample extends TutorialWebServiceClient {

	public TutorialChapter6_PopulationAndEnvironmentCensusExample() throws MalformedURLException {
		super();
		// TODO Auto-generated constructor stub
	}
/*
	public TutorialChapter6_PopulationAndEnvironmentCensusExample() throws MalformedURLException {
		super();
	}

	public void getPopulationAndEnvironmentCensus(String apolloLocationCode) {
		SoftwareIdentification fredSoftwareIdentification = ExampleRunSimulationMessageFactory
				.getSoftwareIdentificationForSimulator(SimulatorIdentificationEnum.FRED);

		List<String> locationCodesSupportedBySimulator = getScenarioLocationCodesSupportedBySimulatorOrNull(fredSoftwareIdentification);

		for (int i = 0; i < locationCodesSupportedBySimulator.size(); i++) {
			if (locationCodesSupportedBySimulator.get(i).equals(apolloLocationCode)) { 
				System.out.println("PopulationAndEnvironmentCensus for ApolloLocationCode "
						+ apolloLocationCode + " is:");

		

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
	*/
}
