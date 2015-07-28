package edu.pitt.apollo.examples.runsimulationmessages;

import java.util.Calendar;
import java.util.GregorianCalendar;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;

import edu.pitt.apollo.types.v3_0_2.*;


public class ExampleInfectiousDiseaseScenario {

	public static final String INCITS_CODE_FOR_ALLEGHENY_COUNTY = "42003";
	
	public static final String H1N1_VACCINE_ONTOLOGY_ID = "0001599";

	private static Infection getInfection(String pathogenTaxonId, String hostTaxonId, 
			UnitOfTimeEnum unitOfTimeEnum, double infectiousPeriod, double latentPeriod, 
			double reproductionNumber) {
		
		Infection infection = new Infection();

		ApolloPathogenCode pathogen = new ApolloPathogenCode();
		pathogen.setNcbiTaxonId(pathogenTaxonId);
		infection.setPathogen(pathogen);
		infection.setHost(hostTaxonId);


		InfectionAcquisitionFromInfectedHost InfectionAcquisitionFromInfectedHost =
						getInfectionAcquisitionFromInfectedHost(
								hostTaxonId, reproductionNumber, latentPeriod, 
								infectiousPeriod, unitOfTimeEnum);
		infection.getInfectionAcquisitionsFromInfectiousHosts().add(InfectionAcquisitionFromInfectedHost);

		return infection;
	}


	private static InfectionAcquisitionFromInfectedHost getInfectionAcquisitionFromInfectedHost(

			String infectiousHostTaxonId, double reproductionNumber, double latentPeriod, double infectiousPeriod,
			UnitOfTimeEnum unitOfTimeEnum) {

		FixedDuration infectiousPeriodDuration = new FixedDuration();
		infectiousPeriodDuration.setUnitOfTime(unitOfTimeEnum);
		infectiousPeriodDuration.setValue(infectiousPeriod);

		FixedDuration latentPeriodDuration = new FixedDuration();
		latentPeriodDuration.setUnitOfTime(unitOfTimeEnum);
		latentPeriodDuration.setValue(latentPeriod);

        InfectionAcquisitionFromInfectedHost infectionAcquisitionFromInfectiouHost =

				new InfectionAcquisitionFromInfectedHost();
		
		
		ReproductionNumber reproNum = new ReproductionNumber();
		reproNum.setExactValue(reproductionNumber);
		
		infectionAcquisitionFromInfectiouHost.getBasicReproductionNumbers().add(reproNum);
		infectionAcquisitionFromInfectiouHost.setInfectedHost(infectiousHostTaxonId);
		infectionAcquisitionFromInfectiouHost.setInfectiousPeriodDuration(infectiousPeriodDuration);
		infectionAcquisitionFromInfectiouHost.setLatentPeriodDuration(latentPeriodDuration);
		infectionAcquisitionFromInfectiouHost.setInfectedHost(infectiousHostTaxonId);

		return infectionAcquisitionFromInfectiouHost;
	}

	private static InfectiousDisease getInfectiousDisease(String diseaseId, String pathogenTaxonId, String hostTaxonId,
			DiseaseOutcomeEnum diseaseOutcomeEnum, double probablyOfDiseaseOutcome) {
		InfectiousDisease disease = new InfectiousDisease();
		disease.setDisease(diseaseId);
		disease.setSpeciesWithDisease(hostTaxonId);
		ApolloPathogenCode pathogen = new ApolloPathogenCode();
		pathogen.setNcbiTaxonId(pathogenTaxonId);
		disease.setCausalPathogen(pathogen);
		disease.getDiseaseOutcomesWithProbabilities().add(
				getProbabilitySymptomaticForDisease(diseaseOutcomeEnum, probablyOfDiseaseOutcome));
		return disease;
	}

	private static XMLGregorianCalendar getXmlDateGivenYearMonthDay(int year, int month, int day) {
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

	private static Location getLocationGivenApolloLocationCode(String apolloLocationCode) {
		Location location = new Location();
		location.setApolloLocationCode(apolloLocationCode);
		return location;
	}

	private static InfectiousDiseaseScenario getInfectiousDiseaseScenario(Location location,
			XMLGregorianCalendar scenarioDate, Infection scenarioInfection, InfectiousDisease scenarioInfectiousDisease,
			PopulationInfectionAndImmunityCensus scenarioPopulationInfectionAndImmunityCensus) {
		InfectiousDiseaseScenario scenario = new InfectiousDiseaseScenario();


        EcosystemAtPointInTime eapit = new EcosystemAtPointInTime();
        eapit.setLocation(location);
        eapit.setEcosystemOnScenarioDate(new EcosystemData());

		scenario.setEcosystemOnScenarioDate(eapit);

		scenario.setScenarioDate(scenarioDate);
		scenario.getInfections().add(scenarioInfection);
		scenario.getInfections().get(0).getInfectiousDiseases().add(scenarioInfectiousDisease);
		scenario.getPopulationInfectionAndImmunityCensuses().add(scenarioPopulationInfectionAndImmunityCensus);
		return scenario;
	}

	private static PopulationInfectionAndImmunityCensusDataCell getPopulationInfectionAndImmunityCensusDataCell(
			InfectionStateEnum infectionStateEnum, double fractionInInfectionState) {
		PopulationInfectionAndImmunityCensusDataCell populationInfectionAndImmunityCensusDataCell = new PopulationInfectionAndImmunityCensusDataCell();
		populationInfectionAndImmunityCensusDataCell.setInfectionState(infectionStateEnum);
		populationInfectionAndImmunityCensusDataCell.setFractionInState(fractionInInfectionState);
		return populationInfectionAndImmunityCensusDataCell;
	}

	private static PopulationInfectionAndImmunityCensus getPopulationInfectionAndImmunityCensus(String censusDescription,
			String apolloLocationCodeForCensus, XMLGregorianCalendar censusReferenceDate, String pathogenTaxonId,
			String hostTaxonId, double fractionSusceptible, double fractionExposed, double fractionInfectious,
			double fractionRecovered) {

		Location locationDescribedByCensus = new Location();
		locationDescribedByCensus.setApolloLocationCode(apolloLocationCodeForCensus);

		ApolloPathogenCode apolloPathogenCodeForCensus = new ApolloPathogenCode();
		apolloPathogenCodeForCensus.setNcbiTaxonId(pathogenTaxonId);

		PopulationInfectionAndImmunityCensusDataCell susceptibleCell = getPopulationInfectionAndImmunityCensusDataCell(
				InfectionStateEnum.SUSCEPTIBLE, fractionSusceptible);
		PopulationInfectionAndImmunityCensusDataCell latentCell = getPopulationInfectionAndImmunityCensusDataCell(
				InfectionStateEnum.LATENT, fractionExposed);
		PopulationInfectionAndImmunityCensusDataCell infectiousCell = getPopulationInfectionAndImmunityCensusDataCell(
				InfectionStateEnum.INFECTIOUS, fractionInfectious);
		PopulationInfectionAndImmunityCensusDataCell recoveredCell = getPopulationInfectionAndImmunityCensusDataCell(
				InfectionStateEnum.RECOVERED, fractionRecovered);

		PopulationInfectionAndImmunityCensusData populationInfectionAndImmunityCensusData = new PopulationInfectionAndImmunityCensusData();
		populationInfectionAndImmunityCensusData.setLocation(locationDescribedByCensus);
		populationInfectionAndImmunityCensusData.getCensusDataCells().add(susceptibleCell);
		populationInfectionAndImmunityCensusData.getCensusDataCells().add(latentCell);
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

	private static DiseaseOutcomeWithProbability getProbabilitySymptomaticForDisease(DiseaseOutcomeEnum diseaseOutcomeEnum,
			double probabilityOfDiseaseOutcome) {
		ProbabilisticParameter probabilityOfDiseaseOutcomeParamter = new ProbabilisticParameter();
		probabilityOfDiseaseOutcomeParamter.setProbability(probabilityOfDiseaseOutcome);

		DiseaseOutcomeWithProbability diseaseOutcomeWithProbability = new DiseaseOutcomeWithProbability();
		diseaseOutcomeWithProbability.setDiseaseOutcome(diseaseOutcomeEnum);
		diseaseOutcomeWithProbability.setProbability(probabilityOfDiseaseOutcomeParamter);

		return diseaseOutcomeWithProbability;
	}

	public static InfectiousDiseaseScenario getScenario() {
		final String pathogenTaxonIdForScenario = "114727"; //H1N1
		final String hostTaxonIdForScenario = "9606"; //human

		final String descriptionOfScenarioLocation = "Allegheny County, Pennsylvania";
		final String apolloLocationCodeForScenario = INCITS_CODE_FOR_ALLEGHENY_COUNTY;

		final int scenarioYear = 2009, scenarioMonth = Calendar.SEPTEMBER, scenarioDay = 1;

		final String scenarioDisease = "Influenza";
		final DiseaseOutcomeEnum symptomaticDiseaseOutcome = DiseaseOutcomeEnum.SYMPTOMATIC;
		final double probabilityHostBecomesSymptomaticDuringInfection = 0.7;

		final UnitOfTimeEnum unitOfTimeForDurationVariables = UnitOfTimeEnum.DAY;
		final double infectiousPeriodDurationForScenarioDisease = 6.0;
		final double latentPeriodDurationForScenarioDisease = 2.3;
		final double reproductionNumberForScenarioDisease = 1.3;

		final double fractionOfHostPopulationSusceptibleToPathogen = 0.94;
		final double fractionOfHostPopulationInfectedWithPathogenButNotInfectious = 0.0;
		final double fractionOfHostPopulationInfectedWithPathogenAndInfectious = 0.01;
		final double fractionOfHostPopulationImmuneToPathogen = 0.05;

		XMLGregorianCalendar scenarioDate = getXmlDateGivenYearMonthDay(scenarioYear, scenarioMonth, scenarioDay);
		
		Location scenarioLocation = getLocationGivenApolloLocationCode(apolloLocationCodeForScenario);

		Infection scenarioInfection = getInfection(pathogenTaxonIdForScenario, hostTaxonIdForScenario,
				unitOfTimeForDurationVariables, infectiousPeriodDurationForScenarioDisease,
				latentPeriodDurationForScenarioDisease, reproductionNumberForScenarioDisease);

		InfectiousDisease scenarioInfectiousDisease = getInfectiousDisease(scenarioDisease, pathogenTaxonIdForScenario,
				hostTaxonIdForScenario, symptomaticDiseaseOutcome, probabilityHostBecomesSymptomaticDuringInfection);

		PopulationInfectionAndImmunityCensus scenarioPopulationInfectionAndImmunityCensus = 
						getPopulationInfectionAndImmunityCensus(descriptionOfScenarioLocation,
								INCITS_CODE_FOR_ALLEGHENY_COUNTY, scenarioDate, pathogenTaxonIdForScenario,
								hostTaxonIdForScenario, fractionOfHostPopulationSusceptibleToPathogen,
								fractionOfHostPopulationInfectedWithPathogenButNotInfectious,
								fractionOfHostPopulationInfectedWithPathogenAndInfectious,
								fractionOfHostPopulationImmuneToPathogen);

		return getInfectiousDiseaseScenario(scenarioLocation, scenarioDate, scenarioInfection, scenarioInfectiousDisease,
				scenarioPopulationInfectionAndImmunityCensus);
	}

}
