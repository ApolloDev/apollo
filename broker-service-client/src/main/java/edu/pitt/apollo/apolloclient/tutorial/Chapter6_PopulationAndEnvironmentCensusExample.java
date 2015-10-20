/* Copyright 2012 University of Pittsburgh
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License.  You may obtain a copy of
 * the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.  See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */

package edu.pitt.apollo.apolloclient.tutorial;

import java.net.MalformedURLException;
import java.util.List;

import edu.pitt.apollo.apolloclient.tutorial.ApolloServiceTypeFactory.SimulatorIdentificationEnum;
import edu.pitt.apollo.types.v3_1_0.SoftwareIdentification;
import edu.pitt.apollo.types.v3_1_0.PopulationAndEnvironmentCensus;

public class Chapter6_PopulationAndEnvironmentCensusExample {

	public Chapter6_PopulationAndEnvironmentCensusExample() throws MalformedURLException {
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
		SoftwareIdentification fredSoftwareIdentification = ApolloServiceTypeFactory
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
//		System.out.println(indent + "Name of Administrative Unit: "
//				+ populationAndEnvironmentCensus.getNameOfAdministativeUnit());

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
		new Chapter6_PopulationAndEnvironmentCensusExample().getPopulationAndEnvironmentCensus("42");
	}

}
