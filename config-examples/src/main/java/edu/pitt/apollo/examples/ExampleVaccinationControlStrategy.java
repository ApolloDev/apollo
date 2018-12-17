package edu.pitt.apollo.examples;

import edu.pitt.apollo.simulator_service_types.v4_0_2.RunSimulationMessage;
import edu.pitt.apollo.types.v4_0_2.*;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;
import java.math.BigInteger;
import java.util.Calendar;
import java.util.GregorianCalendar;

public class ExampleVaccinationControlStrategy {

	public static final String H1N1_NCBI_TAXON_ID = "114727";
	public static final String HUMAN_NCBI_TAXON_ID = "9606";
	public static final String H1N1_VACCINE_ONTOLOGY_ID = "0001599";

	public ExampleVaccinationControlStrategy() {
		super();
	}

	private ProbabilisticParameter getControlMeasureCompilance() {
		ProbabilisticParameter compliance = new ProbabilisticParameter();
		compliance.setProbability(0.5);
		return compliance;
	}

	private Duration getControlMeasureStandDownDelay() {
		FixedDuration duration = new FixedDuration();
		duration.setUnitOfTime(UnitOfTimeEnum.DAY);
		duration.setValue(0d);
		return duration;
	}

	private DiseaseSurveillanceCapability getDiseaseSurveillanceCapability() {
		Location diseaseSurveillanceLocation = getLocation();

		FixedDuration timeDelayOfCaseDetection = new FixedDuration();
		timeDelayOfCaseDetection.setUnitOfTime(UnitOfTimeEnum.DAY);
		timeDelayOfCaseDetection.setValue(new Double(2));

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
		diseaseSurveillanceTriggerDefinition.setReactiveControlMeasureTest("symptomatic people in the population");
		diseaseSurveillanceTriggerDefinition.setReactiveControlMeasureThreshold(50);
		diseaseSurveillanceTriggerDefinition.setReactiveControlMeasureOperator(OperatorEnum.GREATER_THAN_OR_EQUAL);
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

	
	public  RunSimulationMessage addVaccinationControlMeasureToRunSimulationMessage(RunSimulationMessage message) {
		message.getInfectiousDiseaseScenario().getInfectiousDiseaseControlStrategies()
                .add(getVaccinationControlMeasure(message.getInfectiousDiseaseScenario().getScenarioDate()));
		return message;
	}

	private ControlMeasureTargetPopulationsAndPrioritization getTargetPopulationsAndPrioritizations() {
		ControlMeasureTargetPopulationsAndPrioritization targetPopulationsAndPrioritization = new ControlMeasureTargetPopulationsAndPrioritization();
		targetPopulationsAndPrioritization.setControlMeasureNamedPrioritizationScheme(NamedPrioritizationSchemeEnum.ACIP);
		return targetPopulationsAndPrioritization;
	}

	private TemporalTriggerDefinition getTemporalTriggerDefinitionForControlMeasureStopTime() {
		FixedDuration fixedDuration = new FixedDuration();
		fixedDuration.setUnitOfTime(UnitOfTimeEnum.MONTH);
		fixedDuration.setValue(2.0);

		TemporalTriggerDefinition temporalTriggerDefinition = new TemporalTriggerDefinition();
		temporalTriggerDefinition.setTimeScale(TimeScaleEnum.CONTROL_MEASURE_TIME_SCALE);
		temporalTriggerDefinition.setTimeSinceTimeScaleZero(fixedDuration);

		return temporalTriggerDefinition;
	}

	private LogisticalSystem getLogisticalSystem(XMLGregorianCalendar startDate) {

        LogisticalSystem logisticalSystem = new LogisticalSystem();
        logisticalSystem.setProduct("Example vaccine");
        LogisticalSystemNode outputNode = new LogisticalSystemNode();
        Schedule outputSchedule = new Schedule();
        outputNode.setOutputSchedule(outputSchedule);
        outputSchedule.setUnitOfMeasure(UnitOfMeasureEnum.INDIVIDUAL_TREATMENTS);

        Calendar calendar = startDate.toGregorianCalendar();

        LogisticalSystemNode capacityNode = new LogisticalSystemNode();
        Schedule capacitySchedule = new Schedule();
        capacitySchedule.setUnitOfMeasure(UnitOfMeasureEnum.INDIVIDUAL_TREATMENTS);
        capacityNode.setCapacitySchedule(capacitySchedule);

        try {
            for (int i = 0; i < 90; i++) {
                ScheduleElement element = new ScheduleElement();
                GregorianCalendar gregorianCalendar = new GregorianCalendar();
                gregorianCalendar.setTimeInMillis(calendar.getTimeInMillis());
                element.setDateTime(DatatypeFactory.newInstance().newXMLGregorianCalendar(gregorianCalendar));
                element.setQuantity(new BigInteger("3500"));
                outputSchedule.getScheduleElements().add(element);


                ScheduleElement capacityElement = new ScheduleElement();
                capacityElement.setDateTime(DatatypeFactory.newInstance().newXMLGregorianCalendar(gregorianCalendar));
                capacityElement.setQuantity(new BigInteger("3500"));
                capacitySchedule.getScheduleElements().add(capacityElement);

                calendar.add(Calendar.DATE, 1);
            }
        } catch (DatatypeConfigurationException ex) {
            throw new RuntimeException("DataTypeConfigurationException: " + ex.getMessage());
        }

        outputNode.getChildren().add(capacityNode);
        logisticalSystem.getLogisticalSystemNodes().add(outputNode);

        return logisticalSystem;
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

	private  IndividualTreatmentControlMeasure getVaccinationControlMeasure(XMLGregorianCalendar startDate) {
		IndividualTreatmentControlMeasure vaccinationControlMeasure = new IndividualTreatmentControlMeasure();

		/* inherited from InfectiousDiseaseControlMeasure */
		vaccinationControlMeasure.setDescription("An example vaccination control strategy.");
		vaccinationControlMeasure.setLocation(getLocation());
		vaccinationControlMeasure.getControlMeasureStartTime().add(getDiseaseSurveillanceTriggerDefinition());
		vaccinationControlMeasure.getControlMeasureStopTime().add(getTemporalTriggerDefinitionForControlMeasureStopTime());
		vaccinationControlMeasure.setControlMeasureResponseDelay(getResponseDelay());
		vaccinationControlMeasure.setControlMeasureStandDownDelay(getControlMeasureStandDownDelay());
        vaccinationControlMeasure.getLogisticalSystems().add(getLogisticalSystem(startDate));

		/* from IndividualTreatmentControlMeasure */
		vaccinationControlMeasure.setPathogen(getPathogen());
		vaccinationControlMeasure.setIndividualTreatment(getVaccination());
		// populationTreatmentCensus not set as it is not required
		vaccinationControlMeasure.setCompliance(getControlMeasureCompilance());
		// delayFromSymptomsToTreatment not set as it is not required
		vaccinationControlMeasure.setTargetPopulationsAndPrioritizations(getTargetPopulationsAndPrioritizations());

		return vaccinationControlMeasure;
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
