package edu.pitt.apollo.examples.runsimulationmessages;

import java.math.BigInteger;

import edu.pitt.apollo.types.v2_1_0.ApolloPathogenCode;
import edu.pitt.apollo.types.v2_1_0.ControlStrategyTargetPopulationsAndPrioritization;
import edu.pitt.apollo.types.v2_1_0.DiseaseOutcomeEnum;
import edu.pitt.apollo.types.v2_1_0.DiseaseSurveillanceCapability;
import edu.pitt.apollo.types.v2_1_0.DiseaseSurveillanceTriggerDefinition;
import edu.pitt.apollo.types.v2_1_0.Duration;
import edu.pitt.apollo.types.v2_1_0.FixedDuration;
import edu.pitt.apollo.types.v2_1_0.IndividualTreatmentControlStrategy;
import edu.pitt.apollo.types.v2_1_0.Location;
import edu.pitt.apollo.types.v2_1_0.NamedPrioritizationSchemeEnum;
import edu.pitt.apollo.types.v2_1_0.OperatorEnum;
import edu.pitt.apollo.types.v2_1_0.ProbabilisticParameter;
import edu.pitt.apollo.types.v2_1_0.RunSimulationMessage;
import edu.pitt.apollo.types.v2_1_0.TemporalTriggerDefinition;
import edu.pitt.apollo.types.v2_1_0.TimeScaleEnum;
import edu.pitt.apollo.types.v2_1_0.TreatmentPreventableOutcomeEnum;
import edu.pitt.apollo.types.v2_1_0.TreatmentSystemLogistics;
import edu.pitt.apollo.types.v2_1_0.UnitOfMeasureEnum;
import edu.pitt.apollo.types.v2_1_0.UnitOfTimeEnum;
import edu.pitt.apollo.types.v2_1_0.Vaccination;
import edu.pitt.apollo.types.v2_1_0.VaccinationEfficacyForSimulatorConfiguration;

public class ExampleVaccinationControlStrategy {

	public static final String H1N1_NCBI_TAXON_ID = "114727";
	public static final String HUMAN_NCBI_TAXON_ID = "9606";
	public static final String H1N1_VACCINE_ONTOLOGY_ID = "0001599";

	public ExampleVaccinationControlStrategy() {
		super();
	}

	private ProbabilisticParameter getControlStrategyCompilance() {
		ProbabilisticParameter compliance = new ProbabilisticParameter();
		compliance.setProbability(0.5);
		return compliance;
	}

	private Duration getControlStrategyStandDownDelay() {
		FixedDuration duration = new FixedDuration();
		duration.setUnitOfTime(UnitOfTimeEnum.DAY);
		duration.setValue(0d);
		return duration;
	}

	private DiseaseSurveillanceCapability getDiseaseSurveillanceCapability() {
		Location diseaseSurveillanceLocation = getLocation();

		FixedDuration timeDelayOfCaseDetection = new FixedDuration();
		timeDelayOfCaseDetection.setUnitOfTime(UnitOfTimeEnum.DAY);
		timeDelayOfCaseDetection.setValue(2);

		DiseaseSurveillanceCapability diseaseSurveillanceCapability = new DiseaseSurveillanceCapability();
		diseaseSurveillanceCapability.setLocation(diseaseSurveillanceLocation);
		diseaseSurveillanceCapability.setPathogen(getPathogen());
		diseaseSurveillanceCapability.setSpeciesOfCase(HUMAN_NCBI_TAXON_ID);
		diseaseSurveillanceCapability.setCaseDefinition(DiseaseOutcomeEnum.SYMPTOMATIC);
		diseaseSurveillanceCapability.setSensitivityOfCaseDetection(0.85);
		diseaseSurveillanceCapability.setSpecificityOfCaseDetection(1.0);
		diseaseSurveillanceCapability.setTimeDelayOfCaseDetection(timeDelayOfCaseDetection);

		return diseaseSurveillanceCapability;
	}

	private DiseaseSurveillanceTriggerDefinition getDiseaseSurveillanceTriggerDefinition() {
		DiseaseSurveillanceTriggerDefinition diseaseSurveillanceTriggerDefinition = new DiseaseSurveillanceTriggerDefinition();
		diseaseSurveillanceTriggerDefinition.setReactiveControlStrategyTest("symptomatic people in the population");
		diseaseSurveillanceTriggerDefinition.setReactiveControlStrategyThreshold(50);
		diseaseSurveillanceTriggerDefinition.setReactiveControlStrategyOperator(OperatorEnum.GREATER_THAN_OR_EQUAL);
		diseaseSurveillanceTriggerDefinition.setUnitOfMeasureForThreshold(UnitOfMeasureEnum.CASES);
		diseaseSurveillanceTriggerDefinition.setDiseaseSurveillanceCapability(getDiseaseSurveillanceCapability());
		return diseaseSurveillanceTriggerDefinition;
	}

	private Location getLocation() {
		Location location = new Location();
		location.setApolloLocationCode("42003");
		return location;
	}

	private ApolloPathogenCode getPathogen() {
		ApolloPathogenCode pathogen = new ApolloPathogenCode();
		pathogen.setNcbiTaxonId(H1N1_NCBI_TAXON_ID);
		return pathogen;
	}

	private FixedDuration getResponseDelay() {
		FixedDuration responseDelay = new FixedDuration();
		responseDelay.setUnitOfTime(UnitOfTimeEnum.DAY);
		responseDelay.setValue(0d);
		return responseDelay;
	}

	
	public  RunSimulationMessage addVaccinationControlStrategyToRunSimulationMessage(RunSimulationMessage message) {
		message.getInfectiousDiseaseScenario().getInfectiousDiseaseControlStrategies().add(getVaccinationControlStrategy());
		return message;
	}

	private ControlStrategyTargetPopulationsAndPrioritization getTargetPopulationsAndPrioritizations() {
		ControlStrategyTargetPopulationsAndPrioritization targetPopulationsAndPrioritization = new ControlStrategyTargetPopulationsAndPrioritization();
		targetPopulationsAndPrioritization.setControlStrategyNamedPrioritizationScheme(NamedPrioritizationSchemeEnum.ACIP);
		return targetPopulationsAndPrioritization;
	}

	private TemporalTriggerDefinition getTemporalTriggerDefinitionForControlStrategyStopTime() {
		FixedDuration fixedDuration = new FixedDuration();
		fixedDuration.setUnitOfTime(UnitOfTimeEnum.MONTH);
		fixedDuration.setValue(2.0);

		TemporalTriggerDefinition temporalTriggerDefinition = new TemporalTriggerDefinition();
		temporalTriggerDefinition.setTimeScale(TimeScaleEnum.CONTROL_STRATEGY_TIME_SCALE);
		temporalTriggerDefinition.setTimeSinceTimeScaleZero(fixedDuration);

		return temporalTriggerDefinition;
	}

	private TreatmentSystemLogistics getTreatmentSystemLogistics() {
		TreatmentSystemLogistics treatmentSystemLogistics = new TreatmentSystemLogistics();
		treatmentSystemLogistics.setDescription("The logistics of an example vaccination control strategy.");
		treatmentSystemLogistics.setLocation(getLocation());

		for (int i = 0; i < 90; i++)
			treatmentSystemLogistics.getSupplySchedulePerDay().add(new BigInteger("3500"));
		treatmentSystemLogistics.setSupplyScheduleUnits(UnitOfMeasureEnum.DAILY_DOSE);

		for (int i = 0; i < 90; i++)
			treatmentSystemLogistics.getAdministrationCapacityPerDay().add(new BigInteger("3500"));
		treatmentSystemLogistics.setAdministrationCapacityUnits(UnitOfMeasureEnum.DAILY_DOSE);

		return treatmentSystemLogistics;
	}

	private Vaccination getVaccination() {
		Vaccination vaccination = new Vaccination();

		/* inherited from Treatment */
		vaccination.setDescription("H1N1 Vaccine");
		vaccination.setSpeciesOfTreatedOrganism(HUMAN_NCBI_TAXON_ID);
		vaccination.setPathogen(getPathogen());
		vaccination.setNumDosesInTreatmentCourse(new BigInteger("1"));
		vaccination.setDurationOfTreatmentCourse(getVaccinationTreatmentCourseDuration());

		/* from Vaccination */
		vaccination.setVaccineId(H1N1_VACCINE_ONTOLOGY_ID);
		vaccination.getVaccinationEfficacies().add(getVaccinationEfficacyForSimulatorConfiguration());

		return vaccination;
	}

	private  IndividualTreatmentControlStrategy getVaccinationControlStrategy() {
		IndividualTreatmentControlStrategy vaccinationControlStrategy = new IndividualTreatmentControlStrategy();

		/* inherited from InfectiousDiseaseControlStrategy */
		vaccinationControlStrategy.setDescription("An example vaccination control strategy.");
		vaccinationControlStrategy.setLocation(getLocation());
		vaccinationControlStrategy.getControlStrategyStartTime().add(getDiseaseSurveillanceTriggerDefinition());
		vaccinationControlStrategy.getControlStrategyStopTime().add(getTemporalTriggerDefinitionForControlStrategyStopTime());
		vaccinationControlStrategy.setControlStrategyResponseDelay(getResponseDelay());
		vaccinationControlStrategy.setControlStrategyStandDownDelay(getControlStrategyStandDownDelay());

		/* from IndividualTreatmentControlStrategy */
		vaccinationControlStrategy.setPathogen(getPathogen());
		vaccinationControlStrategy.setIndividualTreatment(getVaccination());
		// populationTreatmentCensus not set as it is not required
		vaccinationControlStrategy.setCompliance(getControlStrategyCompilance());
		// delayFromSymptomsToTreatment not set as it is not required
		vaccinationControlStrategy.setTargetPopulationsAndPrioritizations(getTargetPopulationsAndPrioritizations());

		return vaccinationControlStrategy;
	}

	private VaccinationEfficacyForSimulatorConfiguration getVaccinationEfficacyForSimulatorConfiguration() {
		VaccinationEfficacyForSimulatorConfiguration vaccinationEfficacyForSimulatorConfiguration = new VaccinationEfficacyForSimulatorConfiguration();

		/* inherited from TreatmentEfficacy */
		vaccinationEfficacyForSimulatorConfiguration.setHostIdentifier(HUMAN_NCBI_TAXON_ID);
		vaccinationEfficacyForSimulatorConfiguration.setStrainIdentifier(getPathogen());
		vaccinationEfficacyForSimulatorConfiguration
				.setForTreatmentPreventableOutcome(TreatmentPreventableOutcomeEnum.INFECTION);

		/* from VaccinationEfficacyForSimulatiorConfiguration */
		vaccinationEfficacyForSimulatorConfiguration.setAverageVaccinationEfficacy(0.47);
		// vaccinationEfficacyByAgeRange is not set as it is not required
		// vaccinationEfficacyByTimeSinceMostRecentDose is not set as it is not required


		return vaccinationEfficacyForSimulatorConfiguration;
	}

	private FixedDuration getVaccinationTreatmentCourseDuration() {
		FixedDuration duration = new FixedDuration();
		duration.setUnitOfTime(UnitOfTimeEnum.DAY);
		duration.setValue(14d);
		return duration;
	};

}
