package edu.pitt.apollo.apolloclient;

import java.net.MalformedURLException;
import java.util.List;

import edu.pitt.apollo.examples.runsimulationmessages.AbstractCodeExamplesClass.SimulatorIdentificationEnum;
import edu.pitt.apollo.examples.runsimulationmessages.ExampleRunSimulationMessageFactory;
import edu.pitt.apollo.types.v2_0_1.PopulationAndEnvironmentCensus;
import edu.pitt.apollo.types.v2_0_1.SoftwareIdentification;

public class TutorialChapter6_PopulationAndEnvironmentCensusExample {

	public TutorialChapter6_PopulationAndEnvironmentCensusExample() throws MalformedURLException {
		super();
	}

	private boolean isLocationCodeSupportedBySimulator(SoftwareIdentification softwareIdentification,
			String apolloLocationCode) {
		List<String> locationCodesSupportedBySimulator = TutorialWebServiceClient
				.getScenarioLocationCodesSupportedBySimulatorOrNull(softwareIdentification);

		for (int i = 0; i < locationCodesSupportedBySimulator.size(); i++)
			if (locationCodesSupportedBySimulator.get(i).equals(apolloLocationCode))
				return true;

		return false;
	}

	public void getPopulationAndEnvironmentCensus(String apolloLocationCode) {
		SoftwareIdentification fredSoftwareIdentification = ExampleRunSimulationMessageFactory
				.getSoftwareIdentificationForSimulator(SimulatorIdentificationEnum.FRED);

		if (isLocationCodeSupportedBySimulator(fredSoftwareIdentification, apolloLocationCode)) {
			PopulationAndEnvironmentCensus populationAndEnvironmentCensus = TutorialWebServiceClient
					.getPopulationAndEnvironmentCensus(fredSoftwareIdentification, apolloLocationCode);
			parsePopululationAndEnvironmentCensus(populationAndEnvironmentCensus, 0);
		}

	}

	private void parsePopululationAndEnvironmentCensus(
			PopulationAndEnvironmentCensus populationAndEnvironmentCensus, int recursionLevel) {
		String indent = "";
		for (int i = 0; i < recursionLevel; i++)
			indent += "\t";

		System.out.println(indent + "Description: " + populationAndEnvironmentCensus.getDescription());
		System.out.println(indent + "Location Code: "
				+ populationAndEnvironmentCensus.getLocation().getApolloLocationCode());
		System.out.println(indent + "Date: " + populationAndEnvironmentCensus.getReferenceDate());
		System.out.println(indent + "Name of Administrative Unit: "
				+ populationAndEnvironmentCensus.getNameOfAdministativeUnit());

		System.out
				.println(indent + "Number of People: " + populationAndEnvironmentCensus.getNumberOfPeople());
		System.out.println(indent + "Number of Schools: "
				+ populationAndEnvironmentCensus.getNumberOfSchools());
		System.out.println(indent + "Number of Workplaces: "
				+ populationAndEnvironmentCensus.getNumberOfWorkplaces());
		System.out.println(indent + "Number of Schools:"
				+ populationAndEnvironmentCensus.getNumberOfSchools());
		for (int i = 0; i < populationAndEnvironmentCensus.getSubLocationCensuses().size(); i++) {
			System.out.println(indent + "Subpopulation: " + i);
			parsePopululationAndEnvironmentCensus(populationAndEnvironmentCensus.getSubLocationCensuses()
					.get(i), recursionLevel + 1);
		}
		System.out.println();
	}

	public static void main(String[] args) throws MalformedURLException {
		new TutorialChapter6_PopulationAndEnvironmentCensusExample().getPopulationAndEnvironmentCensus("42");
	}

}
