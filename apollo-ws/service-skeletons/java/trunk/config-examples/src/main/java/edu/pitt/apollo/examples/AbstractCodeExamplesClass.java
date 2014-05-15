package edu.pitt.apollo.examples;

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
import edu.pitt.apollo.types.v2_0_1.InfectionAcquisition;
import edu.pitt.apollo.types.v2_0_1.InfectionAcquisitionFromInfectiousHost;
import edu.pitt.apollo.types.v2_0_1.InfectionStateEnum;
import edu.pitt.apollo.types.v2_0_1.InfectiousDisease;
import edu.pitt.apollo.types.v2_0_1.InfectiousDiseaseScenario;
import edu.pitt.apollo.types.v2_0_1.Location;
import edu.pitt.apollo.types.v2_0_1.PopulationInfectionAndImmunityCensus;
import edu.pitt.apollo.types.v2_0_1.PopulationInfectionAndImmunityCensusData;
import edu.pitt.apollo.types.v2_0_1.PopulationInfectionAndImmunityCensusDataCell;
import edu.pitt.apollo.types.v2_0_1.ProbabilisticParameter;
import edu.pitt.apollo.types.v2_0_1.SimulatorTimeSpecification;
import edu.pitt.apollo.types.v2_0_1.SoftwareIdentification;
import edu.pitt.apollo.types.v2_0_1.UnitOfTimeEnum;

public abstract class AbstractCodeExamplesClass {
	
	public enum SimulatorIdentificationEnum {
		FRED, SEIR
	}
	
	public static SoftwareIdentification getSoftwareIdentificationForSimulator(
			SimulatorIdentificationEnum simulatorEnum) {

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

	public static Authentication getAuthentication(String requesterId, String password) {
		Authentication authentication = new Authentication();
		authentication.setRequesterId(requesterId);
		authentication.setRequesterPassword(password);
		return authentication;
	}

	protected static Infection getInfection(String pathogenTaxonId, String hostTaxonId,
			UnitOfTimeEnum unitOfTimeEnum, double infectiousPeriod, double latentPeriod,
			double reproductionNumber) {
		Infection infection = new Infection();

		ApolloPathogenCode pathogen = new ApolloPathogenCode();
		pathogen.setNcbiTaxonId(pathogenTaxonId);
		infection.setPathogenTaxonId(pathogen);
		infection.setHostTaxonId(hostTaxonId);

		FixedDuration infectiousPeriodDuration = new FixedDuration();
		infectiousPeriodDuration.setUnitOfTime(unitOfTimeEnum);
		infectiousPeriodDuration.setValue(infectiousPeriod);
		infection.setInfectiousPeriodDuration(infectiousPeriodDuration);

		FixedDuration latentPeriodDuration = new FixedDuration();
		latentPeriodDuration.setUnitOfTime(unitOfTimeEnum);
		latentPeriodDuration.setValue(latentPeriod);
		infection.setLatentPeriodDuration(latentPeriodDuration);

		InfectionAcquisition infectionAcquisition = getInfectionAcquisition(pathogenTaxonId, hostTaxonId,
				reproductionNumber);
		infection.getInfectionAcquisition().add(infectionAcquisition);

		return infection;
	}

	private static InfectionAcquisition getInfectionAcquisition(String pathogenTaxonId, String hostTaxonId,
			double reproductionNumber) {
		ApolloPathogenCode apolloPathogenCode = new ApolloPathogenCode();
		apolloPathogenCode.setNcbiTaxonId(pathogenTaxonId);

		InfectionAcquisitionFromInfectiousHost infectionAcquisitionFromInfectiouHost = new InfectionAcquisitionFromInfectiousHost();
		infectionAcquisitionFromInfectiouHost.setBasicReproductionNumber(reproductionNumber);
		infectionAcquisitionFromInfectiouHost.setInfectiousHostTaxonId(hostTaxonId);

		InfectionAcquisition infectionAcquisition = new InfectionAcquisition();
		infectionAcquisition.setPathogenTaxonId(apolloPathogenCode);
		infectionAcquisition.setSusceptibleHostTaxonId(hostTaxonId);
		infectionAcquisition.setFromInfectiousHost(infectionAcquisitionFromInfectiouHost);
		return infectionAcquisition;
	}

	protected static InfectiousDisease getInfectiousDisease(String diseaseId, String pathogenTaxonId,
			String hostTaxonId, DiseaseOutcomeEnum diseaseOutcomeEnum, double probablyOfDiseaseOutcome) {
		InfectiousDisease disease = new InfectiousDisease();
		disease.setDiseaseId(diseaseId);
		disease.setSpeciesWithDisease(hostTaxonId);
		ApolloPathogenCode pathogen = new ApolloPathogenCode();
		pathogen.setNcbiTaxonId(pathogenTaxonId);
		disease.setCausalPathogen(pathogen);
		disease.getDiseaseOutcomesWithProbabilities().add(
				getProbabilitySymptomaticForDisease(diseaseOutcomeEnum, probablyOfDiseaseOutcome));
		return disease;
	}

	protected static XMLGregorianCalendar getXmlDateGivenYearMonthDay(int year, int month, int day) {
		GregorianCalendar calendar = new GregorianCalendar();
		calendar.set(Calendar.YEAR, year);
		calendar.set(Calendar.MONTH, month);
		calendar.set(Calendar.DAY_OF_MONTH, month);
		calendar.set(Calendar.HOUR, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		calendar.set(Calendar.MILLISECOND, 0);

		XMLGregorianCalendar scenarioDate = null;
		try {
			scenarioDate = DatatypeFactory.newInstance().newXMLGregorianCalendar(calendar);
			return scenarioDate;
		} catch (DatatypeConfigurationException e) {
			System.out.println("Error!  Unable to set date, error was:" + e.getMessage());
			System.exit(-1);
		}
		return null;
	}

	protected static Location getLocationGivenApolloLocationCode(String apolloLocationCode) {
		Location location = new Location();
		location.setApolloLocationCode(apolloLocationCode);
		return location;
	}

	protected static InfectiousDiseaseScenario getInfectiousDiseaseScenario(Location scenarioLocation,
			XMLGregorianCalendar scenarioDate, Infection scenarioInfection,
			InfectiousDisease scenarioInfectiousDisease,
			PopulationInfectionAndImmunityCensus scenarioPopulationInfectionAndImmunityCensus) {
		InfectiousDiseaseScenario scenario = new InfectiousDiseaseScenario();
		scenario.setLocation(scenarioLocation);
		scenario.setScenarioDate(scenarioDate);
		scenario.getInfections().add(scenarioInfection);
		scenario.getDiseases().add(scenarioInfectiousDisease);
		scenario.getPopulationInfectionAndImmunityCensuses()
				.add(scenarioPopulationInfectionAndImmunityCensus);
		return scenario;
	}

	protected static PopulationInfectionAndImmunityCensusDataCell getPopulationInfectionAndImmunityCensusDataCell(
			InfectionStateEnum infectionStateEnum, double fractionInInfectionState) {
		PopulationInfectionAndImmunityCensusDataCell populationInfectionAndImmunityCensusDataCell = new PopulationInfectionAndImmunityCensusDataCell();
		populationInfectionAndImmunityCensusDataCell.setInfectionState(infectionStateEnum);
		populationInfectionAndImmunityCensusDataCell.setFractionInInfectionState(fractionInInfectionState);
		return populationInfectionAndImmunityCensusDataCell;
	}

	protected static PopulationInfectionAndImmunityCensus getPopulationInfectionAndImmunityCensus(
			String censusDescription, String apolloLocationCodeForCensus,
			XMLGregorianCalendar censusReferenceDate, String pathogenTaxonId, String hostTaxonId,
			double fractionSusceptible, double fractionExposed, double fractionInfectious,
			double fractionRecovered) {

		Location locationDescribedByCensus = new Location();
		locationDescribedByCensus.setApolloLocationCode(apolloLocationCodeForCensus);

		ApolloPathogenCode apolloPathogenCodeForCensus = new ApolloPathogenCode();
		apolloPathogenCodeForCensus.setNcbiTaxonId(pathogenTaxonId);

		PopulationInfectionAndImmunityCensusDataCell susceptibleCell = getPopulationInfectionAndImmunityCensusDataCell(
				InfectionStateEnum.SUSCEPTIBLE, fractionSusceptible);
		PopulationInfectionAndImmunityCensusDataCell exposedCell = getPopulationInfectionAndImmunityCensusDataCell(
				InfectionStateEnum.EXPOSED, fractionExposed);
		PopulationInfectionAndImmunityCensusDataCell infectiousCell = getPopulationInfectionAndImmunityCensusDataCell(
				InfectionStateEnum.INFECTIOUS, fractionInfectious);
		PopulationInfectionAndImmunityCensusDataCell recoveredCell = getPopulationInfectionAndImmunityCensusDataCell(
				InfectionStateEnum.RECOVERED, fractionRecovered);

		PopulationInfectionAndImmunityCensusData populationInfectionAndImmunityCensusData = new PopulationInfectionAndImmunityCensusData();
		populationInfectionAndImmunityCensusData.setLocation(locationDescribedByCensus);
		populationInfectionAndImmunityCensusData.getCensusDataCells().add(susceptibleCell);
		populationInfectionAndImmunityCensusData.getCensusDataCells().add(exposedCell);
		populationInfectionAndImmunityCensusData.getCensusDataCells().add(infectiousCell);
		populationInfectionAndImmunityCensusData.getCensusDataCells().add(recoveredCell);

		PopulationInfectionAndImmunityCensus census = new PopulationInfectionAndImmunityCensus();
		census.setDescription(censusDescription);
		census.setLocation(locationDescribedByCensus);
		census.setReferenceDate(censusReferenceDate);
		census.setCensusData(populationInfectionAndImmunityCensusData);
		census.setPopulationSpecies(hostTaxonId);
		census.setPathogen(apolloPathogenCodeForCensus);
		return census;
	}

	private static DiseaseOutcomeWithProbability getProbabilitySymptomaticForDisease(
			DiseaseOutcomeEnum diseaseOutcomeEnum, double probabilityOfDiseaseOutcome) {
		ProbabilisticParameter probabilityOfDiseaseOutcomeParamter = new ProbabilisticParameter();
		probabilityOfDiseaseOutcomeParamter.setProbability(probabilityOfDiseaseOutcome);

		DiseaseOutcomeWithProbability diseaseOutcomeWithProbability = new DiseaseOutcomeWithProbability();
		diseaseOutcomeWithProbability.setDiseaseOutcome(diseaseOutcomeEnum);
		diseaseOutcomeWithProbability.setProbability(probabilityOfDiseaseOutcomeParamter);

		return diseaseOutcomeWithProbability;
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
