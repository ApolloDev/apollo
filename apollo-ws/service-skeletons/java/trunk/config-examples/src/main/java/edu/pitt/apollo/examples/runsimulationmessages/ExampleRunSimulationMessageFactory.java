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
package edu.pitt.apollo.examples.runsimulationmessages;

import java.math.BigInteger;
import java.util.Calendar;
import java.util.GregorianCalendar;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;

import edu.pitt.apollo.types.v2_0_1.ApolloPathogenCode;
import edu.pitt.apollo.types.v2_0_1.ApolloSoftwareTypeEnum;
import edu.pitt.apollo.types.v2_0_1.Authentication;
import edu.pitt.apollo.types.v2_0_1.DiseaseOutcomeEnum;
import edu.pitt.apollo.types.v2_0_1.DiseaseOutcomeWithProbability;
import edu.pitt.apollo.types.v2_0_1.FixedDuration;
import edu.pitt.apollo.types.v2_0_1.Infection;
import edu.pitt.apollo.types.v2_0_1.InfectionAcquisitionFromInfectiousHost;
import edu.pitt.apollo.types.v2_0_1.InfectionStateEnum;
import edu.pitt.apollo.types.v2_0_1.InfectiousDisease;
import edu.pitt.apollo.types.v2_0_1.InfectiousDiseaseScenario;
import edu.pitt.apollo.types.v2_0_1.Location;
import edu.pitt.apollo.types.v2_0_1.LocationDefinition;
import edu.pitt.apollo.types.v2_0_1.PopulationInfectionAndImmunityCensus;
import edu.pitt.apollo.types.v2_0_1.PopulationInfectionAndImmunityCensusData;
import edu.pitt.apollo.types.v2_0_1.PopulationInfectionAndImmunityCensusDataCell;
import edu.pitt.apollo.types.v2_0_1.ProbabilisticParameter;
import edu.pitt.apollo.types.v2_0_1.RunSimulationMessage;
import edu.pitt.apollo.types.v2_0_1.SimulatorTimeSpecification;
import edu.pitt.apollo.types.v2_0_1.SoftwareIdentification;
import edu.pitt.apollo.types.v2_0_1.UnitOfTimeEnum;

public class ExampleRunSimulationMessageFactory extends AbstractCodeExamplesClass {

	public static final String INCITS_CODE_FOR_ALLEGHENY_COUNTY = "42003";
	public static final String H1N1 = "114727";
	public static final String HUMAN = "9606";
	public static final String H1N1_VACCINE_ONTOLOGY_ID = "0001599";

	public static final BigInteger SIMULATION_RUN_LENGTH = new BigInteger("90");
	public static final UnitOfTimeEnum UNIT_OF_TIME_FOR_SIMULATOR_TIME_STEP = UnitOfTimeEnum.DAY;
	public static final double NUMBER_OF_UNITS_OF_TIME_IN_ONE_SIMULATOR_TIME_STEP = 1.0;

	public static final String REQUESTER_ID = "TutorialUser";
	public static final String REQUESTER_PASSWORD = "TutorialPassword";

	public static RunSimulationMessage getRunSimulationMessage() {
		RunSimulationMessage message = new RunSimulationMessage();
		message.setInfectiousDiseaseScenario(getInfectiousDiseaseScenario());
		message.setAuthentication(getAuthentication(REQUESTER_ID, REQUESTER_PASSWORD));
		message.setSimulatorIdentification(getSoftwareIdentificationForSimulator(SimulatorIdentificationEnum.FRED));
		message.setSimulatorTimeSpecification(getSimulatorTimeSpecification(SIMULATION_RUN_LENGTH,
				UNIT_OF_TIME_FOR_SIMULATOR_TIME_STEP, NUMBER_OF_UNITS_OF_TIME_IN_ONE_SIMULATOR_TIME_STEP));
		return message;
	}

	protected static InfectiousDiseaseScenario getInfectiousDiseaseScenario() {
		final String pathogenTaxonIdForScenario = H1N1;
		final String hostTaxonIdForScenario = HUMAN;

		final String descriptionOfScenarioLocation = "Allegheny County, Pennsylvania";
		final String apolloLocationCodeForScenario = INCITS_CODE_FOR_ALLEGHENY_COUNTY;

		final int scenarioYear = 2009, scenarioMonth = Calendar.SEPTEMBER, scenarioDay = 1;

		final String scenarioDisease = "Influenza";
		final DiseaseOutcomeEnum symptomaticDiseaseOutcome = DiseaseOutcomeEnum.SYMPTOMATIC;
		final double probabilityHostBecomesSymptomaticDuringInfection = 0.7;

		final UnitOfTimeEnum unitOfTimeForDurationVariables = UnitOfTimeEnum.DAY;
		final double infectiousPeriodDurationForScenarioDisease = 6.0;
		final double latentPeriodDurationForScenarioDisease = 2.0;
		final double reproductionNumberForScenarioDisease = 1.3;

		final double fractionOfHostPopulationSusceptibleToPathogen = 0.94;
		final double fractionOfHostPopulationInfectedWithPathogenButNotInfectious = 0.0;
		final double fractionOfHostPopulationInfectedWithPathogenAndInfectious = 0.01;
		final double fractionOfHostPopulationImmuneToPathogen = 0.05;

		Location scenarioLocation = getLocationGivenApolloLocationCode(apolloLocationCodeForScenario);

		XMLGregorianCalendar scenarioDate = getXmlDateGivenYearMonthDay(scenarioYear, scenarioMonth,
				scenarioDay);

		Infection scenarioInfection = getInfection(pathogenTaxonIdForScenario, hostTaxonIdForScenario,
				unitOfTimeForDurationVariables, infectiousPeriodDurationForScenarioDisease,
				latentPeriodDurationForScenarioDisease, reproductionNumberForScenarioDisease);

		InfectiousDisease scenarioInfectiousDisease = getInfectiousDisease(scenarioDisease,
				pathogenTaxonIdForScenario, hostTaxonIdForScenario, symptomaticDiseaseOutcome,
				probabilityHostBecomesSymptomaticDuringInfection);

		PopulationInfectionAndImmunityCensus scenarioPopulationInfectionAndImmunityCensus = getPopulationInfectionAndImmunityCensus(
				descriptionOfScenarioLocation, INCITS_CODE_FOR_ALLEGHENY_COUNTY, scenarioDate,
				pathogenTaxonIdForScenario, hostTaxonIdForScenario,
				fractionOfHostPopulationSusceptibleToPathogen,
				fractionOfHostPopulationInfectedWithPathogenButNotInfectious,
				fractionOfHostPopulationInfectedWithPathogenAndInfectious,
				fractionOfHostPopulationImmuneToPathogen);

		return getInfectiousDiseaseScenario(scenarioLocation, scenarioDate, scenarioInfection,
				scenarioInfectiousDisease, scenarioPopulationInfectionAndImmunityCensus);
	}

}