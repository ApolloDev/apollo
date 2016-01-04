package edu.pitt.apollo.examples.runsimulationmessages;

import edu.pitt.apollo.types.v4_0.ApolloSoftwareTypeEnum;;
import edu.pitt.apollo.services_common.v4_0.Authentication;
import edu.pitt.apollo.types.v4_0.SoftwareIdentification;
import edu.pitt.apollo.simulator_service_types.v4_0.RunSimulationMessage;
import edu.pitt.apollo.types.v4_0.*;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;
import java.math.BigInteger;
import java.util.*;

public class InfluenzaRunSimulationMessageBuilder extends AbstractRunSimulationMessageBuilder {

	// if you change these you will break the tests!
	private static final double DEFAULT_LATENT_PERIOD = 2d;
	private static final double DEFAULT_INFECTIOUS_PERIOD = 6d;
	private static final double DEFAULT_ASYMPTOMTIC_INFECTION_FRACTION = 0.33;
	private static final double DEFAULT_R0 = 1.3;
	private static final String DEFAULT_RUN_LENGTH = "150";
	private static final String DEFAULT_INCITS = "06037";

	@Override
	protected SoftwareIdentification getSoftwareIdentification() {
		SoftwareIdentification softwareId = new SoftwareIdentification();
		softwareId.setSoftwareDeveloper("UPitt,PSC,CMU");
		softwareId.setSoftwareName("FRED");
		softwareId.setSoftwareType(ApolloSoftwareTypeEnum.SIMULATOR);
		softwareId.setSoftwareVersion("1.0");
		return softwareId;
	}

	@Override
	protected SimulatorTimeSpecification getSimulatorTimeSpeficiation() {
		SimulatorTimeSpecification simulatorTimeSpecification = new SimulatorTimeSpecification();
		simulatorTimeSpecification.setRunLength(new BigInteger(
				DEFAULT_RUN_LENGTH));
		simulatorTimeSpecification.setUnitOfTimeForSimulatorTimeStep(UnitOfTimeEnum.DAY);
		simulatorTimeSpecification.setNumberOfUnitsOfTimeInOneSimulatorTimeStep(1d);

		return simulatorTimeSpecification;
	}

	@Override
	protected List<Infection> getInfections() {
		List<Infection> infections = new ArrayList<Infection>();

		Infection infection = new Infection();

		ApolloPathogenCode code = new ApolloPathogenCode();
		code.setCladeName("H1N1");
		code.setNcbiTaxonId("114727");
		infection.setPathogen(code);
		infection.setHost("9606");

		InfectiousDisease disease = new InfectiousDisease();

		disease.setDisease("H1N1");
		disease.setCausalPathogen(code);
		disease.setSpeciesWithDisease("9606");

		DiseaseOutcomeWithProbability dowp = new DiseaseOutcomeWithProbability();
		dowp.setDiseaseOutcome(DiseaseOutcomeEnum.ASYMPTOMATIC);
		ProbabilisticParameter asymtptomaticProb = new ProbabilisticParameter();
		asymtptomaticProb.setProbability(DEFAULT_ASYMPTOMTIC_INFECTION_FRACTION);
		dowp.setProbability(asymtptomaticProb);
		disease.getDiseaseOutcomesWithProbabilities().add(dowp);
		
		infection.getInfectiousDiseases().add(disease);

		InfectionAcquisitionFromInfectedHost iafih = new InfectionAcquisitionFromInfectedHost();
		iafih.setInfectedHost("9606");
		FixedDuration infectiousPeriod = new FixedDuration();
		infectiousPeriod.setUnitOfTime(UnitOfTimeEnum.DAY);
		infectiousPeriod.setValue(DEFAULT_INFECTIOUS_PERIOD);
		iafih.setInfectiousPeriodDuration(infectiousPeriod);

		FixedDuration latentPeriod = new FixedDuration();
		latentPeriod.setUnitOfTime(UnitOfTimeEnum.DAY);
		latentPeriod.setValue(DEFAULT_LATENT_PERIOD);
		iafih.setLatentPeriodDuration(latentPeriod);
		
		ReproductionNumber reproductionNumber = new ReproductionNumber();
		
		reproductionNumber.setExactValue(DEFAULT_R0);
		iafih.getBasicReproductionNumbers().add(reproductionNumber);

		infection.getInfectionAcquisitionsFromInfectedHosts().add(iafih);
		infections.add(infection);

		return infections;
	}

	@Override
	protected List<Population> getPopulations() {
		List<PopulationInfectionAndImmunityCensus> censuses = new ArrayList<PopulationInfectionAndImmunityCensus>();

        Population population = new Population();
        population.setTaxonId("9606");
        population.setLocation(DEFAULT_INCITS);
        population.setCount(new BigInteger("9000000"));

		PopulationInfectionAndImmunityCensus census = new PopulationInfectionAndImmunityCensus();

		census.setDescription("LA County Population");
		census.setReferenceDate(getDate());
		census.setLocation(getLocation());

		ApolloPathogenCode code = new ApolloPathogenCode();
		code.setCladeName("H1N1");
		code.setNcbiTaxonId("114727");
		census.setPathogen(code);

		PopulationInfectionAndImmunityCensusData data = new PopulationInfectionAndImmunityCensusData();
		data.setLocation(getLocation());

		PopulationInfectionAndImmunityCensusDataCell susceptibleCell = new PopulationInfectionAndImmunityCensusDataCell();
		susceptibleCell.setInfectionState(InfectionStateEnum.SUSCEPTIBLE);
		susceptibleCell.setFractionInState(0.94859);

		PopulationInfectionAndImmunityCensusDataCell exposedCell = new PopulationInfectionAndImmunityCensusDataCell();
		exposedCell.setInfectionState(InfectionStateEnum.LATENT);
		exposedCell.setFractionInState(0.00538);

		PopulationInfectionAndImmunityCensusDataCell infectiousCell = new PopulationInfectionAndImmunityCensusDataCell();
		infectiousCell.setInfectionState(InfectionStateEnum.INFECTIOUS);
		infectiousCell.setFractionInState(0.00603);

		PopulationInfectionAndImmunityCensusDataCell recoveredCell = new PopulationInfectionAndImmunityCensusDataCell();
		recoveredCell.setInfectionState(InfectionStateEnum.RECOVERED);
		recoveredCell.setFractionInState(0.04);

		data.getCensusDataCells().add(susceptibleCell);
		data.getCensusDataCells().add(exposedCell);
		data.getCensusDataCells().add(infectiousCell);
		data.getCensusDataCells().add(recoveredCell);

		census.setCensusData(data);

		censuses.add(census);

        population.getInfectionAndImmunityCensuses().add(census);
        List<Population> populations = new ArrayList<>();
        populations.add(population);

		return populations;
	}

	@Override
	protected Location getLocation() {
		Location location = new Location();
		location.setApolloLocationCode(DEFAULT_INCITS);
		return location;
	}

	public RunSimulationMessage getRunSimulationMessage(Set<ControlMeasureTypeEnum> controlMeasureTypes) {

		RunSimulationMessage simulatorConfiguration = getBaseRunSimulationMessage();

		Double[] supplyConstrainedVaccinationCapacityTable = new Double[150];
		for (int i = 0; i < 150; i++) {
			supplyConstrainedVaccinationCapacityTable[i] = new Double(3000 + i);
		}


		Location location = getLocation();
        XMLGregorianCalendar date = getDate();

		List<InfectiousDiseaseControlMeasure> strategies = new ArrayList<InfectiousDiseaseControlMeasure>();
		if (controlMeasureTypes.contains(ControlMeasureTypeEnum.VACC_CM)) {
			strategies.add(
					getVacciationControlMeasure("ACIP",
							"The vaccination description", 150, date,
							supplyConstrainedVaccinationCapacityTable, location));
		}

		if (controlMeasureTypes.contains(ControlMeasureTypeEnum.AV_CM)) {
			strategies.add(
					getAntiviralTreatmentControlMeasure("mass", "", date,
							supplyConstrainedVaccinationCapacityTable, location));
		}

		if (controlMeasureTypes.contains(ControlMeasureTypeEnum.ALL_SC_CM_FIXED)) {
			strategies.add(
					getSchoolClosureControlMeasure(
							PlaceEnum.ALL_SCHOOLS,
							"school closure", location, true, false));
		}

		if (controlMeasureTypes.contains(ControlMeasureTypeEnum.ALL_SC_CM_REACTIVE)) {
			strategies.add(
					getSchoolClosureControlMeasure(
							PlaceEnum.ALL_SCHOOLS,
							"school closure", location, false, false));
		}

		if (controlMeasureTypes.contains(ControlMeasureTypeEnum.INDIVIDUAL_SC_CM_FIXED)) {
			strategies.add(
					getSchoolClosureControlMeasure(
							PlaceEnum.ALL_SCHOOLS,
							"school closure", location, true, true));
		}

		if (controlMeasureTypes.contains(ControlMeasureTypeEnum.INDIVIDUAL_SC_CM_REACTIVE)) {
			strategies.add(
					getSchoolClosureControlMeasure(
							PlaceEnum.ALL_SCHOOLS,
							"school closure", location, false, true));
		}

		if (controlMeasureTypes.contains(ControlMeasureTypeEnum.VI_CM)) {
			strategies.add(getVoluntaryIsolationControlMeasure());
		}

		InfectiousDiseaseScenario scenario = simulatorConfiguration.getInfectiousDiseaseScenario();

		if (controlMeasureTypes.contains(ControlMeasureTypeEnum.VQ_CM)) {
			scenario.getNonApolloParameters().add(getVoluntaryQuarantineControlMeasure());
		}

		if (controlMeasureTypes.contains(ControlMeasureTypeEnum.LSL_CM)) {
			scenario.getNonApolloParameters().add(getLiberalSickLeaveControlMeasure());
		}

		scenario.getInfectiousDiseaseControlStrategies().addAll(strategies);

		return simulatorConfiguration;

	}

	public CaseQuarantineControlMeasure getVoluntaryIsolationControlMeasure() {

		CaseQuarantineControlMeasure strategy = new CaseQuarantineControlMeasure();
		strategy.setDescription("voluntary_isolation_control_measure");

		ProbabilisticParameter value = new ProbabilisticParameter();
		value.setProbability(0.65);
		strategy.setCompliance(value);

		FixedDuration quarantinePeriod = new FixedDuration();
		quarantinePeriod.setUnitOfTime(UnitOfTimeEnum.DAY);
		quarantinePeriod.setValue(7d);
		strategy.setQuarantinePeriod(quarantinePeriod);

		setBaseInfectiousDiseaseControlMeasure(strategy);

		return strategy;
	}

	public static NonApolloParameter getVoluntaryQuarantineControlMeasure() {

		NonApolloParameter parameter = new NonApolloParameter();
		parameter.setDescription("voluntary_quarantine_control_measure");
		parameter.setParameterFloatValue(0.75);
		parameter.setParameterName("quarantine");

		return parameter;
	}

	public static NonApolloParameter getLiberalSickLeaveControlMeasure() {

		NonApolloParameter parameter = new NonApolloParameter();
		parameter.setDescription("liberal_sick_leave_control_measure");
		parameter.setParameterName("liberalleave");
		parameter.setParameterFloatValue(0.85);

		return parameter;
	}

	public static InfectiousDiseaseControlMeasure getAntiviralTreatmentControlMeasure(
			String scheme, String description, XMLGregorianCalendar startDate,
			Double[] supplyConstrainedAntiviralCapacityTable, Location location) {

		AntiviralTreatment antiviralTreatment = new AntiviralTreatment();
		antiviralTreatment.setDescription(description);

//        NcbiTaxonId species = new NcbiTaxonId();
//        species.setGisrnCladeName("human");
//        species.setNcbiTaxonId(BigInteger.ZERO);
		antiviralTreatment.setNumDosesInTreatmentCourse(BigInteger.valueOf(1l));
//        NcbiTaxonId treatedOrganisms = new NcbiTaxonId();
//        treatedOrganisms.setNcbiTaxonId(new BigInteger("1"));
		antiviralTreatment.setSpeciesOfTreatedOrganism("9606");
		antiviralTreatment.setAntiviralId("antiviral drug");

		ApolloPathogenCode strain = new ApolloPathogenCode();
		strain.setNcbiTaxonId("114727");
		strain.setCladeName("H1N1");

		AntiviralTreatmentEfficacy ate = new AntiviralTreatmentEfficacy();
		ate.setEfficacy(0.7);

		ate.setHostIdentifier("9606");
		ate.setStrainIdentifier(strain);
		ate.setForTreatmentPreventableOutcome(TreatmentPreventableOutcomeEnum.INFECTIOUSNESS);

		antiviralTreatment.getAntiviralTreatmentEfficacy().add(ate);

		ApolloPathogenCode code = new ApolloPathogenCode();
		code.setCladeName("H1N1");
		code.setNcbiTaxonId("114727");
		antiviralTreatment.setPathogen(code);

		FixedDuration duration = new FixedDuration();
		duration.setUnitOfTime(UnitOfTimeEnum.DAY);
		duration.setValue(1d);
		antiviralTreatment.setDurationOfTreatmentCourse(duration);

		IndividualTreatmentControlMeasure antiviralTreatmentControlMeasure = new IndividualTreatmentControlMeasure();
		antiviralTreatmentControlMeasure.setDescription("antiviral");
		antiviralTreatmentControlMeasure.setPathogen(code);

		FixedDuration responseDelay = new FixedDuration();
		responseDelay.setUnitOfTime(UnitOfTimeEnum.DAY);
		responseDelay.setValue(2.0);

		antiviralTreatmentControlMeasure.setControlMeasureResponseDelay(responseDelay);

		ProbabilisticParameter compliance = new ProbabilisticParameter();
		compliance.setProbability(0.2);

		antiviralTreatmentControlMeasure.setCompliance(compliance);
		antiviralTreatmentControlMeasure.setIndividualTreatment(antiviralTreatment);

		FixedDuration startTime = new FixedDuration();
		startTime.setUnitOfTime(UnitOfTimeEnum.DAY);
		startTime.setValue(0);

		TemporalTriggerDefinition trigger = new TemporalTriggerDefinition();
		trigger.setTimeScale(TimeScaleEnum.SIMULATOR_TIME_SCALE);
		trigger.setTimeSinceTimeScaleZero(startTime);
		antiviralTreatmentControlMeasure.getControlMeasureStartTime().add(trigger);

		FixedDuration stopTmie = new FixedDuration();
		stopTmie.setUnitOfTime(UnitOfTimeEnum.DAY);
		stopTmie.setValue(0);

		TemporalTriggerDefinition stopTrigger = new TemporalTriggerDefinition();
		stopTrigger.setTimeScale(TimeScaleEnum.SIMULATOR_TIME_SCALE);
		stopTrigger.setTimeSinceTimeScaleZero(startTime);
		antiviralTreatmentControlMeasure.getControlMeasureStopTime().add(trigger);

		FixedDuration standDownDelay = new FixedDuration();
		standDownDelay.setUnitOfTime(UnitOfTimeEnum.DAY);
		standDownDelay.setValue(150d);
		antiviralTreatmentControlMeasure.setControlMeasureStandDownDelay(standDownDelay);

		// antiviralTreatmentControlMeasure.setControlMeasureReactiveEndPointFraction(1d);
		// antiviralTreatmentControlMeasure.setControlMeasureNamedPrioritizationScheme("Sick only");
		TargetPriorityPopulation targetPriorityPopulation = new TargetPriorityPopulation();
		targetPriorityPopulation.setFractionOfTargetPopulationToPrioritize(1.0);
		targetPriorityPopulation.setDescription("symptomatic individuals");
		targetPriorityPopulation.setPriority(BigInteger.valueOf(1));
		TargetPopulationDefinition targetPopulationDefinition = new TargetPopulationDefinition();
		targetPopulationDefinition.setDiseaseOutcome(DiseaseOutcomeEnum.SYMPTOMATIC);

		targetPriorityPopulation.setTargetPopulationDefinition(targetPopulationDefinition);

		ControlMeasureTargetPopulationsAndPrioritization prioritization = new ControlMeasureTargetPopulationsAndPrioritization();
		prioritization.getControlMeasureTargetPopulationsAndPrioritization().add(targetPriorityPopulation);
		antiviralTreatmentControlMeasure.setTargetPopulationsAndPrioritizations(prioritization);
//        antiviralTreatmentControlMeasure
//                .getTargetPopulationsAndPrioritizations().add(
//                targetPriorityPopulation);

        LogisticalSystem logisticalSystem = new LogisticalSystem();
        logisticalSystem.setProduct("Tamiflu");
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
            for (int i = 0; i < supplyConstrainedAntiviralCapacityTable.length; i++) {
                ScheduleElement element = new ScheduleElement();
                GregorianCalendar gregorianCalendar = new GregorianCalendar();
                gregorianCalendar.setTimeInMillis(calendar.getTimeInMillis());
                element.setDateTime(DatatypeFactory.newInstance().newXMLGregorianCalendar(gregorianCalendar));
                element.setQuantity(BigInteger.valueOf(supplyConstrainedAntiviralCapacityTable[i].longValue()));
                calendar.add(Calendar.DATE, 1);
                outputSchedule.getScheduleElements().add(element);
            }


            calendar = startDate.toGregorianCalendar();

            for (int i = 0; i < supplyConstrainedAntiviralCapacityTable.length; i++) {
                ScheduleElement element = new ScheduleElement();
                GregorianCalendar gregorianCalendar = new GregorianCalendar();
                gregorianCalendar.setTimeInMillis(calendar.getTimeInMillis());
                element.setDateTime(DatatypeFactory.newInstance().newXMLGregorianCalendar(gregorianCalendar));
                element.setQuantity(BigInteger.valueOf(supplyConstrainedAntiviralCapacityTable[i].longValue()));
                calendar.add(Calendar.DATE, 1);
                capacitySchedule.getScheduleElements().add(element);
            }
        } catch (DatatypeConfigurationException ex) {
            throw new RuntimeException("DatatypeConfigurationException: " + ex.getMessage());
        }

        outputNode.getChildren().add(capacityNode);
        logisticalSystem.getLogisticalSystemNodes().add(outputNode);

        antiviralTreatmentControlMeasure.getLogisticalSystems().add(logisticalSystem);

		return antiviralTreatmentControlMeasure;
	}

	public static InfectiousDiseaseControlMeasure getVacciationControlMeasure(String scheme,
			String description, int runLength, XMLGregorianCalendar startDate,
			Double[] supplyConstrainedVaccinationCapacityTable, Location location) {

		Vaccination vaccination = new Vaccination();
		vaccination.setDescription("single-dose vaccination of human with univalent H1N1 vaccine");
		vaccination.setDescription("human");
		vaccination.setNumDosesInTreatmentCourse(BigInteger.valueOf(1l));
//        NcbiTaxonId treatedOrganisms = new NcbiTaxonId();
//        treatedOrganisms.setNcbiTaxonId(new BigInteger("1"));
		vaccination.setSpeciesOfTreatedOrganism("9606");

		VaccinationEfficacyForSimulatorConfiguration ve = new VaccinationEfficacyForSimulatorConfiguration();
		ve.setForTreatmentPreventableOutcome(TreatmentPreventableOutcomeEnum.INFECTION);

		vaccination.setVaccineId("H1N1 vaccine");

		ApolloPathogenCode code = new ApolloPathogenCode();
		code.setCladeName("H1N1");
		code.setNcbiTaxonId("114727");
		vaccination.setPathogen(code);

		FixedDuration duration = new FixedDuration();
		duration.setUnitOfTime(UnitOfTimeEnum.DAY);
		duration.setValue(1d);
		vaccination.setDurationOfTreatmentCourse(duration);

		ApolloPathogenCode strain = new ApolloPathogenCode();
		strain.setNcbiTaxonId("114727");
		strain.setCladeName("H1N1");

//        NcbiTaxonId host = new NcbiTaxonId();
//        host.setNcbiTaxonId(BigInteger.ZERO);
		ve.setHostIdentifier("9606");
		ve.setStrainIdentifier(strain);
		ve.setAverageVaccinationEfficacy(0.7);

		VaccinationEfficacyConditionedOnTimeSinceDose vebtsd = new VaccinationEfficacyConditionedOnTimeSinceDose();
		vebtsd.setNumberOfDosesAdministered(new BigInteger("1"));

		TimeAxisCategoryLabels labels = new TimeAxisCategoryLabels();
		labels.setLabel("days");
		labels.setSimulatorTimeEarliest(new BigInteger("0"));
		labels.setSimulatorTimeLatest(new BigInteger("100"));

		vebtsd.getTimeIntervalLabelDefinitions().add(labels);
		ve.setVaccinationEfficaciesConditionedOnTimeSinceMostRecentDose(vebtsd);

		List<Double> efficacies = vebtsd.getVaccinationEfficacyConditionedOnTimeSinceMostRecentDose();

		for (int i = 0; i <= 14; i++) {
			if (i == 14) {
				efficacies.add(1.0);
			} else {
				efficacies.add(0.0);
			}
		}

		vaccination.getVaccinationEfficacies().add(ve);

		// ve = new VaccinationEfficacyForSimulatorConfiguration();
		// ve.setForVaccinationPreventableOutcome(VaccinationPreventableOutcome.INFECTIOUSNESS);
		// ve.setDescription("VE for prevention of the outcome contagiousness");
		// ve.setStrainIdentifier("H1N1");
		// ve.setTreatment(new Treatment());
		// ve.setVaccineIdentifier("H1N1");
		// ve.setAverageVaccinationEfficacy(0.4);
		// vaccination.getVaccinationEfficacies().add(ve);
		//
		// ve = new VaccinationEfficacyForSimulatorConfiguration();
		// ve.setForVaccinationPreventableOutcome(VaccinationPreventableOutcome.SICKNESS_REQUIRING_MEDICAL_ATTENTION);
		// ve.setDescription("VE for preventing outcome of illness, given infection");
		// ve.setStrainIdentifier("H1N1");
		// ve.setTreatment(new Treatment());
		// ve.setVaccineIdentifier("H1N1");
		// ve.setAverageVaccinationEfficacy(0.6);
		// vaccination.getVaccinationEfficacies().add(ve);
		// treatment contraindications
		TargetPopulationDefinition targetPopulationDefinition = new TargetPopulationDefinition();
		AgeRangeCategoryDefinition ageRange = new AgeRangeCategoryDefinition();

		TreatmentContraindication infantsContraindication = new TreatmentContraindication();
		targetPopulationDefinition = new TargetPopulationDefinition();
		ageRange = new AgeRangeCategoryDefinition();
		ageRange.setLowerBound(new BigInteger("0"));
		ageRange.setUpperBound(new BigInteger("6"));
		ageRange.setUnitOfTimeForLowerBound(UnitOfTimeEnum.MONTH);
		ageRange.setUnitOfTimeForUpperBound(UnitOfTimeEnum.MONTH);
		targetPopulationDefinition.setAgeRange(ageRange);
		infantsContraindication.setSimulatorReferencablePopulation(targetPopulationDefinition);
		infantsContraindication.setFractionOfSimulatorReferencablePopulation(1.0);
		infantsContraindication.setDescription("fraction of infants (age <6months) restricted from getting the vaccine.");
		vaccination.getTreatmentContraindications().add(infantsContraindication);

		TreatmentContraindication preSchoolContraindication = new TreatmentContraindication();
		targetPopulationDefinition = new TargetPopulationDefinition();
		ageRange = new AgeRangeCategoryDefinition();
		ageRange.setLowerBound(new BigInteger("0"));
		ageRange.setUpperBound(new BigInteger("4"));
		ageRange.setUnitOfTimeForLowerBound(UnitOfTimeEnum.YEAR);
		ageRange.setUnitOfTimeForUpperBound(UnitOfTimeEnum.YEAR);
		targetPopulationDefinition.setAgeRange(ageRange);
		preSchoolContraindication.setSimulatorReferencablePopulation(targetPopulationDefinition);
		preSchoolContraindication.setFractionOfSimulatorReferencablePopulation(0.1);
		preSchoolContraindication.setDescription("fraction of pre-school age children (ages 0-4) restricted from getting the vaccine");
		vaccination.getTreatmentContraindications().add(preSchoolContraindication);

		TreatmentContraindication schoolAgeContraindication = new TreatmentContraindication();
		targetPopulationDefinition = new TargetPopulationDefinition();
		ageRange = new AgeRangeCategoryDefinition();
		ageRange.setLowerBound(new BigInteger("5"));
		ageRange.setUpperBound(new BigInteger("18"));
		ageRange.setUnitOfTimeForLowerBound(UnitOfTimeEnum.YEAR);
		ageRange.setUnitOfTimeForUpperBound(UnitOfTimeEnum.YEAR);
		targetPopulationDefinition.setAgeRange(ageRange);
		schoolAgeContraindication.setSimulatorReferencablePopulation(targetPopulationDefinition);
		schoolAgeContraindication.setFractionOfSimulatorReferencablePopulation(0.1);
		schoolAgeContraindication.setDescription("fraction of school age children (ages 5-18) restricted from getting the vaccine");
		vaccination.getTreatmentContraindications().add(schoolAgeContraindication);

		TreatmentContraindication youngAdultsContraindication = new TreatmentContraindication();
		targetPopulationDefinition = new TargetPopulationDefinition();
		ageRange = new AgeRangeCategoryDefinition();
		ageRange.setLowerBound(new BigInteger("19"));
		ageRange.setUpperBound(new BigInteger("29"));
		ageRange.setUnitOfTimeForLowerBound(UnitOfTimeEnum.YEAR);
		ageRange.setUnitOfTimeForUpperBound(UnitOfTimeEnum.YEAR);
		targetPopulationDefinition.setAgeRange(ageRange);
		youngAdultsContraindication.setSimulatorReferencablePopulation(targetPopulationDefinition);
		youngAdultsContraindication.setFractionOfSimulatorReferencablePopulation(0.1);
		youngAdultsContraindication.setDescription("fraction of young adults (ages 19-29) restricted from getting the vaccine");
		vaccination.getTreatmentContraindications().add(youngAdultsContraindication);

		TreatmentContraindication adultsContraindication = new TreatmentContraindication();
		targetPopulationDefinition = new TargetPopulationDefinition();
		ageRange = new AgeRangeCategoryDefinition();
		ageRange.setLowerBound(new BigInteger("30"));
		ageRange.setUpperBound(new BigInteger("64"));
		ageRange.setUnitOfTimeForLowerBound(UnitOfTimeEnum.YEAR);
		ageRange.setUnitOfTimeForUpperBound(UnitOfTimeEnum.YEAR);
		targetPopulationDefinition.setAgeRange(ageRange);
		adultsContraindication.setSimulatorReferencablePopulation(targetPopulationDefinition);
		adultsContraindication.setFractionOfSimulatorReferencablePopulation(0.1);
		adultsContraindication.setDescription("fraction of older adults (ages 30-64) restricted from getting the vaccine");
		vaccination.getTreatmentContraindications().add(adultsContraindication);

		TreatmentContraindication elderlyContraindication = new TreatmentContraindication();
		targetPopulationDefinition = new TargetPopulationDefinition();
		ageRange = new AgeRangeCategoryDefinition();
		ageRange.setLowerBound(new BigInteger("65"));
		ageRange.setUpperBound(new BigInteger("150"));
		ageRange.setUnitOfTimeForLowerBound(UnitOfTimeEnum.YEAR);
		ageRange.setUnitOfTimeForUpperBound(UnitOfTimeEnum.YEAR);
		// ageRange.setLowerBound(new BigInteger("700"));
		targetPopulationDefinition.setAgeRange(ageRange);
		elderlyContraindication.setSimulatorReferencablePopulation(targetPopulationDefinition);
		elderlyContraindication.setFractionOfSimulatorReferencablePopulation(0.1);
		elderlyContraindication.setDescription("fraction of elderly (age 65+) restricted from getting the vaccine");
		vaccination.getTreatmentContraindications().add(elderlyContraindication);

		TreatmentContraindication pregnantAdultsContraindication = new TreatmentContraindication();
		targetPopulationDefinition = new TargetPopulationDefinition();
		ageRange = new AgeRangeCategoryDefinition();
		ageRange.setLowerBound(new BigInteger("19"));
		ageRange.setUpperBound(new BigInteger("64"));
		ageRange.setUnitOfTimeForLowerBound(UnitOfTimeEnum.YEAR);
		ageRange.setUnitOfTimeForUpperBound(UnitOfTimeEnum.YEAR);
		targetPopulationDefinition.setAgeRange(ageRange);
		targetPopulationDefinition.setOtherStratification(PopulationStratificationEnum.PREGNANT);
		pregnantAdultsContraindication.setSimulatorReferencablePopulation(targetPopulationDefinition);
		pregnantAdultsContraindication.setFractionOfSimulatorReferencablePopulation(0.0);
		pregnantAdultsContraindication.setDescription("Pregnant adults restricted from getting the vaccine (0 means restricted, 1 means no restriction)");
		vaccination.getTreatmentContraindications().add(pregnantAdultsContraindication);

		// TargetPriorityPopulation tpp = new TargetPriorityPopulation();
		// tpp.setPriority(new BigInteger("3"));
		IndividualTreatmentControlMeasure vaccinationControlMeasure = new IndividualTreatmentControlMeasure();
		vaccinationControlMeasure.setDescription(description);
		vaccinationControlMeasure.setPathogen(code);

		FixedDuration startTime = new FixedDuration();
		startTime.setUnitOfTime(UnitOfTimeEnum.DAY);
		startTime.setValue(0);

		TemporalTriggerDefinition trigger = new TemporalTriggerDefinition();
		trigger.setTimeScale(TimeScaleEnum.SIMULATOR_TIME_SCALE);
		trigger.setTimeSinceTimeScaleZero(startTime);
		vaccinationControlMeasure.getControlMeasureStartTime().add(trigger);

		FixedDuration treatmentDetectionDelay = new FixedDuration();
		treatmentDetectionDelay.setUnitOfTime(UnitOfTimeEnum.DAY);
		treatmentDetectionDelay.setValue(2);
//
//        TemporalTriggerDefinition stopTrigger = new TemporalTriggerDefinition();
//        stopTrigger.setTimeScale(TimeScaleEnum.SIMULATOR_TIME_SCALE);
//        stopTrigger.setTimeSinceTimeScaleZero(startTime);
//        vaccinationControlMeasure.getControlMeasureStopTime().add(stopTrigger);

//        ReactiveStartTime st = new ReactiveStartTime();
//        ReactiveTriggerDefinition rt = new ReactiveTriggerDefinition();
//        DiseaseSurveillanceCapability capacility = new DiseaseSurveillanceCapability();
//        capacility.setCaseDefinition(DiseaseOutcomeEnum.ASYMPTOMATIC);
//        rt.setAscertainmentDelay(new BigInteger("3"));
//        rt.setAscertainmentFraction(0.5);
//        rt.setReactiveControlMeasureTest(description);
//        rt.setReactiveControlMeasureThreshold(0.01);
//        st.setTrigger(rt);
//        vaccinationControlMeasure.setControlMeasureStartTime(fst);
		TreatmentSurveillanceTriggerDefinition stopTrigger = new TreatmentSurveillanceTriggerDefinition();
		TreatmentSurveillanceCapability capability = new TreatmentSurveillanceCapability();
		capability.setLocation(location);
		capability.setSpecificityOfTreatmentDetection(0);
		capability.setSensitivityOfTreatmentDetection(0.01);
		capability.setTimeDelayOfTreatmentDetection(treatmentDetectionDelay);
		capability.setTreatment(IndividualTreatmentEnum.VACCINATION);
//        stopTrigger.se

//        ReactiveStopTime stopTime = new ReactiveStopTime();
//        ReactiveTriggerDefinition stopTrigger = new ReactiveTriggerDefinition();
		stopTrigger.setUnitOfMeasureForThreshold(UnitOfMeasureEnum.PERCENT_OF_POPULATION_VACCINATED);
		stopTrigger.setReactiveControlMeasureThreshold(70);
		stopTrigger.setReactiveControlMeasureOperator(OperatorEnum.GREATER_THAN_OR_EQUAL);
		stopTrigger.setTreatmentSurveillanceCapability(capability);
		vaccinationControlMeasure.getControlMeasureStopTime().add(stopTrigger);

		FixedDuration standDownDelay = new FixedDuration();
		standDownDelay.setUnitOfTime(UnitOfTimeEnum.DAY);
		standDownDelay.setValue(150d);
		vaccinationControlMeasure.setControlMeasureStandDownDelay(standDownDelay);

		// vaccinationControlMeasure.getControlMeasureTargetPopulationsAndPrioritization().add(tpp);
		FixedDuration responseDelay = new FixedDuration();
		responseDelay.setUnitOfTime(UnitOfTimeEnum.DAY);
		responseDelay.setValue(0.0);
		vaccinationControlMeasure.setControlMeasureResponseDelay(responseDelay);

		ProbabilisticParameter compliance = new ProbabilisticParameter();
		compliance.setProbability(0.9);
		vaccinationControlMeasure.setCompliance(compliance);
		// vaccinationControlMeasure.setControlMeasureNamedPrioritizationScheme(scheme);
		vaccinationControlMeasure.setIndividualTreatment(vaccination);

		TargetPopulationDefinition psd;
//        AgeRange ageRange;

		ControlMeasureTargetPopulationsAndPrioritization prioritization = new ControlMeasureTargetPopulationsAndPrioritization();

		TargetPriorityPopulation pop1 = new TargetPriorityPopulation();
		pop1.setDescription("healthcare and emergency services personnel defined as a fraction of working age individuals");
		pop1.setFractionOfTargetPopulationToPrioritize(0.07);
		pop1.setPriority(new BigInteger("1"));
		psd = new TargetPopulationDefinition();

		ageRange = new AgeRangeCategoryDefinition();
		ageRange.setLowerBound(new BigInteger("17"));
		ageRange.setUpperBound(new BigInteger("75"));
		ageRange.setUnitOfTimeForLowerBound(UnitOfTimeEnum.YEAR);
		ageRange.setUnitOfTimeForUpperBound(UnitOfTimeEnum.YEAR);
		psd.setAgeRange(ageRange);
		psd.setOtherStratification(PopulationStratificationEnum.ESSENTIAL_WORKFORCE);

		pop1.setTargetPopulationDefinition(psd);
		prioritization.getControlMeasureTargetPopulationsAndPrioritization().add(pop1);

		TargetPriorityPopulation pop2 = new TargetPriorityPopulation();
		pop2.setDescription("pregnant women defined as a fraction of reproductive age women");
		pop2.setFractionOfTargetPopulationToPrioritize(0.06);
		pop2.setPriority(new BigInteger("1"));
		psd = new TargetPopulationDefinition();

//        ageRange = new AgeRangeCategoryDefinition();
//        ageRange.setLowerBound(new BigInteger("17"));
//        ageRange.setUpperBound(new BigInteger("50"));
//        ageRange.setUnitOfTimeForLowerBound(UnitOfTimeEnum.YEAR);
//        ageRange.setUnitOfTimeForUpperBound(UnitOfTimeEnum.YEAR);
//        psd.setAgeRange(ageRange);
//        psd.setGender(GenderEnum.F);
//        psd.setOtherStratification(PopulationStratificationEnum.PREGNANT);
		pop2.setTargetPopulationDefinition(psd);
		prioritization.getControlMeasureTargetPopulationsAndPrioritization().add(pop2);

		TargetPriorityPopulation pop3 = new TargetPriorityPopulation();
		pop3.setDescription("household members and caregivers of infants < 6 months defined within the simulator procedurally");
		pop3.setFractionOfTargetPopulationToPrioritize(1.0);
		pop3.setPriority(new BigInteger("1"));
		pop3.setTargetPopulationEnum(TargetPopulationEnum.HOUSEHOLD_MEMBER_OF_INFANT);

		prioritization.getControlMeasureTargetPopulationsAndPrioritization().add(pop3);

		TargetPriorityPopulation pop4 = new TargetPriorityPopulation();
		pop4.setDescription("high risk preschoolers defined as a fraction of preschoolers");
		pop4.setFractionOfTargetPopulationToPrioritize(0.089);
		pop4.setPriority(new BigInteger("0"));
		psd = new TargetPopulationDefinition();

		ageRange = new AgeRangeCategoryDefinition();
		ageRange.setLowerBound(new BigInteger("0"));
		ageRange.setUpperBound(new BigInteger("4"));
		ageRange.setUnitOfTimeForLowerBound(UnitOfTimeEnum.YEAR);
		ageRange.setUnitOfTimeForUpperBound(UnitOfTimeEnum.YEAR);
		psd.setAgeRange(ageRange);
		psd.setOtherStratification(PopulationStratificationEnum.HIGH_RISK);

		pop4.setTargetPopulationDefinition(psd);
		prioritization.getControlMeasureTargetPopulationsAndPrioritization().add(pop4);

		TargetPriorityPopulation pop5 = new TargetPriorityPopulation();
		pop5.setDescription("High risk school-age children defined as a fraction of school-age children");
		pop5.setFractionOfTargetPopulationToPrioritize(0.212);
		pop5.setPriority(new BigInteger("0"));
		psd = new TargetPopulationDefinition();

		ageRange = new AgeRangeCategoryDefinition();
		ageRange.setLowerBound(new BigInteger("5"));
		ageRange.setUpperBound(new BigInteger("18"));
		ageRange.setUnitOfTimeForLowerBound(UnitOfTimeEnum.YEAR);
		ageRange.setUnitOfTimeForUpperBound(UnitOfTimeEnum.YEAR);
		psd.setAgeRange(ageRange);
		psd.setOtherStratification(PopulationStratificationEnum.HIGH_RISK);

		pop5.setTargetPopulationDefinition(psd);
		prioritization.getControlMeasureTargetPopulationsAndPrioritization().add(pop5);

		TargetPriorityPopulation pop6 = new TargetPriorityPopulation();
		pop6.setDescription("high risk young adult defined as a fraction of young adults");
		pop6.setFractionOfTargetPopulationToPrioritize(0.212);
		pop6.setPriority(new BigInteger("0"));
		psd = new TargetPopulationDefinition();

		ageRange = new AgeRangeCategoryDefinition();
		ageRange.setLowerBound(new BigInteger("19"));
		ageRange.setUpperBound(new BigInteger("29"));
		ageRange.setUnitOfTimeForLowerBound(UnitOfTimeEnum.YEAR);
		ageRange.setUnitOfTimeForUpperBound(UnitOfTimeEnum.YEAR);
		psd.setAgeRange(ageRange);
		psd.setOtherStratification(PopulationStratificationEnum.HIGH_RISK);

		pop6.setTargetPopulationDefinition(psd);
		prioritization.getControlMeasureTargetPopulationsAndPrioritization().add(pop6);

		TargetPriorityPopulation pop6Pregnant = new TargetPriorityPopulation();
		pop6Pregnant.setDescription("pregnant women");
		pop6Pregnant.setFractionOfTargetPopulationToPrioritize(0.02771);
		pop6Pregnant.setPriority(new BigInteger("1"));
		psd = new TargetPopulationDefinition();

		ageRange = new AgeRangeCategoryDefinition();
		ageRange.setLowerBound(new BigInteger("19"));
		ageRange.setUpperBound(new BigInteger("29"));
		ageRange.setUnitOfTimeForLowerBound(UnitOfTimeEnum.YEAR);
		ageRange.setUnitOfTimeForUpperBound(UnitOfTimeEnum.YEAR);
		psd.setAgeRange(ageRange);
		psd.setOtherStratification(PopulationStratificationEnum.PREGNANT);

		pop6Pregnant.setTargetPopulationDefinition(psd);
		prioritization.getControlMeasureTargetPopulationsAndPrioritization().add(pop6Pregnant);

		TargetPriorityPopulation pop7 = new TargetPriorityPopulation();
		pop7.setDescription("high risk older adults defined as a fraction of older adults");
		pop7.setFractionOfTargetPopulationToPrioritize(0.212);
		pop7.setPriority(new BigInteger("0"));
		psd = new TargetPopulationDefinition();

		ageRange = new AgeRangeCategoryDefinition();
		ageRange.setLowerBound(new BigInteger("30"));
		ageRange.setUpperBound(new BigInteger("64"));
		ageRange.setUnitOfTimeForLowerBound(UnitOfTimeEnum.YEAR);
		ageRange.setUnitOfTimeForUpperBound(UnitOfTimeEnum.YEAR);
		psd.setAgeRange(ageRange);
		psd.setOtherStratification(PopulationStratificationEnum.HIGH_RISK);

		pop7.setTargetPopulationDefinition(psd);
		prioritization.getControlMeasureTargetPopulationsAndPrioritization().add(pop7);

		TargetPriorityPopulation pop7Pregnant = new TargetPriorityPopulation();
		pop7Pregnant.setDescription("pregnant women");
		pop7Pregnant.setFractionOfTargetPopulationToPrioritize(0.02069);
		pop7Pregnant.setPriority(new BigInteger("1"));
		psd = new TargetPopulationDefinition();

		ageRange = new AgeRangeCategoryDefinition();
		ageRange.setLowerBound(new BigInteger("30"));
		ageRange.setUpperBound(new BigInteger("64"));
		ageRange.setUnitOfTimeForLowerBound(UnitOfTimeEnum.YEAR);
		ageRange.setUnitOfTimeForUpperBound(UnitOfTimeEnum.YEAR);
		psd.setAgeRange(ageRange);
		psd.setOtherStratification(PopulationStratificationEnum.PREGNANT);

		pop7Pregnant.setTargetPopulationDefinition(psd);
		prioritization.getControlMeasureTargetPopulationsAndPrioritization().add(pop7Pregnant);

		TargetPriorityPopulation pop8 = new TargetPriorityPopulation();
		pop8.setDescription("high risk elderly defined as a fraction of elderly");
		pop8.setFractionOfTargetPopulationToPrioritize(0.0);
		pop8.setPriority(new BigInteger("0"));
		psd = new TargetPopulationDefinition();

		ageRange = new AgeRangeCategoryDefinition();
		ageRange.setLowerBound(new BigInteger("65"));
		ageRange.setUpperBound(new BigInteger("200"));
		ageRange.setUnitOfTimeForLowerBound(UnitOfTimeEnum.YEAR);
		ageRange.setUnitOfTimeForUpperBound(UnitOfTimeEnum.YEAR);
		psd.setAgeRange(ageRange);
		psd.setOtherStratification(PopulationStratificationEnum.HIGH_RISK);

		pop8.setTargetPopulationDefinition(psd);
		prioritization.getControlMeasureTargetPopulationsAndPrioritization().add(pop8);

		TargetPriorityPopulation pop9 = new TargetPriorityPopulation();
		pop9.setDescription("preschoolers defined as children under 5");
		pop9.setFractionOfTargetPopulationToPrioritize(0.0);
		pop9.setPriority(new BigInteger("1"));
		psd = new TargetPopulationDefinition();

		ageRange = new AgeRangeCategoryDefinition();
		ageRange.setLowerBound(new BigInteger("0"));
		ageRange.setUpperBound(new BigInteger("4"));
		ageRange.setUnitOfTimeForLowerBound(UnitOfTimeEnum.YEAR);
		ageRange.setUnitOfTimeForUpperBound(UnitOfTimeEnum.YEAR);
		psd.setAgeRange(ageRange);

		pop9.setTargetPopulationDefinition(psd);
		prioritization.getControlMeasureTargetPopulationsAndPrioritization().add(pop9);

		TargetPriorityPopulation pop10 = new TargetPriorityPopulation();
		pop10.setDescription("school-age children defined as children age 5-18");
		pop10.setFractionOfTargetPopulationToPrioritize(0.0);
		pop10.setPriority(new BigInteger("1"));
		psd = new TargetPopulationDefinition();

		ageRange = new AgeRangeCategoryDefinition();
		ageRange.setLowerBound(new BigInteger("5"));
		ageRange.setUpperBound(new BigInteger("18"));
		ageRange.setUnitOfTimeForLowerBound(UnitOfTimeEnum.YEAR);
		ageRange.setUnitOfTimeForUpperBound(UnitOfTimeEnum.YEAR);
		psd.setAgeRange(ageRange);

		pop10.setTargetPopulationDefinition(psd);
		prioritization.getControlMeasureTargetPopulationsAndPrioritization().add(
				pop10);

		TargetPriorityPopulation pop11 = new TargetPriorityPopulation();
		pop11.setDescription("young adults define as ages 19-29");
		pop11.setFractionOfTargetPopulationToPrioritize(0.0);
		pop11.setPriority(new BigInteger("1"));
		psd = new TargetPopulationDefinition();

		ageRange = new AgeRangeCategoryDefinition();
		ageRange.setLowerBound(new BigInteger("19"));
		ageRange.setUpperBound(new BigInteger("29"));
		ageRange.setUnitOfTimeForLowerBound(UnitOfTimeEnum.YEAR);
		ageRange.setUnitOfTimeForUpperBound(UnitOfTimeEnum.YEAR);
		psd.setAgeRange(ageRange);

		pop11.setTargetPopulationDefinition(psd);
		prioritization.getControlMeasureTargetPopulationsAndPrioritization().add(
				pop11);

		TargetPriorityPopulation pop12 = new TargetPriorityPopulation();
		pop12.setDescription("older adults defined as people age 30-65");
		pop12.setFractionOfTargetPopulationToPrioritize(0.0);
		pop12.setPriority(new BigInteger("2"));
		psd = new TargetPopulationDefinition();

		ageRange = new AgeRangeCategoryDefinition();
		ageRange.setLowerBound(new BigInteger("30"));
		ageRange.setUpperBound(new BigInteger("64"));
		ageRange.setUnitOfTimeForLowerBound(UnitOfTimeEnum.YEAR);
		ageRange.setUnitOfTimeForUpperBound(UnitOfTimeEnum.YEAR);
		psd.setAgeRange(ageRange);

		pop12.setTargetPopulationDefinition(psd);
		prioritization.getControlMeasureTargetPopulationsAndPrioritization().add(
				pop12);

		TargetPriorityPopulation pop13 = new TargetPriorityPopulation();
		pop13.setDescription("elderly defined as people age 65 and older");
		pop13.setFractionOfTargetPopulationToPrioritize(0.0);
		pop13.setPriority(new BigInteger("0"));
		psd = new TargetPopulationDefinition();

		ageRange = new AgeRangeCategoryDefinition();
		ageRange.setLowerBound(new BigInteger("65"));
		ageRange.setUpperBound(new BigInteger("200"));
		ageRange.setUnitOfTimeForLowerBound(UnitOfTimeEnum.YEAR);
		ageRange.setUnitOfTimeForUpperBound(UnitOfTimeEnum.YEAR);
		psd.setAgeRange(ageRange);

		pop13.setTargetPopulationDefinition(psd);
		prioritization.getControlMeasureTargetPopulationsAndPrioritization().add(
				pop13);

		vaccinationControlMeasure.setTargetPopulationsAndPrioritizations(prioritization);

        LogisticalSystem logisticalSystem = new LogisticalSystem();
        logisticalSystem.setProduct("H1N1 vaccine");
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
            for (int i = 0; i < supplyConstrainedVaccinationCapacityTable.length; i++) {
                ScheduleElement element = new ScheduleElement();
                GregorianCalendar gregorianCalendar = new GregorianCalendar();
                gregorianCalendar.setTimeInMillis(calendar.getTimeInMillis());
                element.setDateTime(DatatypeFactory.newInstance().newXMLGregorianCalendar(gregorianCalendar));
                element.setQuantity(BigInteger.valueOf(supplyConstrainedVaccinationCapacityTable[i].longValue()));
                calendar.add(Calendar.DATE, 1);
                outputSchedule.getScheduleElements().add(element);
            }


            calendar = startDate.toGregorianCalendar();

            for (int i = 0; i < runLength; i++) {
                ScheduleElement element = new ScheduleElement();
                GregorianCalendar gregorianCalendar = new GregorianCalendar();
                gregorianCalendar.setTimeInMillis(calendar.getTimeInMillis());
                element.setDateTime(DatatypeFactory.newInstance().newXMLGregorianCalendar(gregorianCalendar));
                element.setQuantity(BigInteger.valueOf(50000l));
                calendar.add(Calendar.DATE, 1);
                capacitySchedule.getScheduleElements().add(element);
            }
        } catch (DatatypeConfigurationException ex) {
            throw new RuntimeException("DatatypeConfigurationException: " + ex.getMessage());
        }

        outputNode.getChildren().add(capacityNode);
        logisticalSystem.getLogisticalSystemNodes().add(outputNode);

        vaccinationControlMeasure.getLogisticalSystems().add(logisticalSystem);

		return vaccinationControlMeasure;

	}

	public static InfectiousDiseaseControlMeasure getSchoolClosureControlMeasure(
			PlaceEnum placeEnum, String description, Location location,
			boolean fixedStartTime, boolean individualSchools) {

		PlaceClosureControlMeasure schoolClosureControlMeasure = new PlaceClosureControlMeasure();
		schoolClosureControlMeasure.setPlaceClass(placeEnum);
		schoolClosureControlMeasure.setHouseholdTransmissionMultiplier(1.0);
		schoolClosureControlMeasure.setCommunityTransmissionMultiplier(1.0);

		schoolClosureControlMeasure.setCloseIndividualPlacesIndependently(individualSchools);

		if (fixedStartTime) {
			FixedDuration fst = new FixedDuration();
			fst.setValue(0);
			fst.setUnitOfTime(UnitOfTimeEnum.DAY);

			TemporalTriggerDefinition trigger = new TemporalTriggerDefinition();
			trigger.setTimeScale(TimeScaleEnum.SIMULATOR_TIME_SCALE);
			trigger.setTimeSinceTimeScaleZero(fst);

			schoolClosureControlMeasure.getControlMeasureStartTime().add(trigger);
		} else {
//            ReactiveStartTime st = new ReactiveStartTime();
			DiseaseSurveillanceTriggerDefinition rt = new DiseaseSurveillanceTriggerDefinition();

			DiseaseSurveillanceCapability capability = new DiseaseSurveillanceCapability();
			capability.setCaseDefinition(DiseaseOutcomeEnum.SYMPTOMATIC);
			capability.setLocation(location);

			ApolloPathogenCode code = new ApolloPathogenCode();
			code.setCladeName("H1N1");
			code.setNcbiTaxonId("114727");
			capability.setPathogen(code);
			capability.setSensitivityOfCaseDetection(1.0);
			capability.setSpeciesOfCase("human");
			capability.setSpecificityOfCaseDetection(1.0);

			FixedDuration caseDetectionTimeDelay = new FixedDuration();
			caseDetectionTimeDelay.setUnitOfTime(UnitOfTimeEnum.DAY);
			caseDetectionTimeDelay.setValue(3d);
			capability.setTimeDelayOfCaseDetection(caseDetectionTimeDelay);

//            ReactiveTriggerDefinition rt = new ReactiveTriggerDefinition();
			rt.setReactiveControlMeasureTest(description);
			rt.setReactiveControlMeasureThreshold(1);
			rt.setReactiveControlMeasureOperator(OperatorEnum.GREATER_THAN_OR_EQUAL);
			rt.setDiseaseSurveillanceCapability(capability);
			rt.setUnitOfMeasureForThreshold(UnitOfMeasureEnum.PERCENT);
			rt.setDiseaseSurveillanceCapability(capability);
			schoolClosureControlMeasure.getControlMeasureStartTime().add(rt);
		}

//        ControlMeasureTargetPopulationsAndPrioritization prioritization = new ControlMeasureTargetPopulationsAndPrioritization();
//        prioritization.setControlMeasureNamedPrioritizationScheme("None");
		FixedDuration responseDelay = new FixedDuration();
		responseDelay.setValue(2.0);
		responseDelay.setUnitOfTime(UnitOfTimeEnum.DAY);
		schoolClosureControlMeasure.setControlMeasureResponseDelay(responseDelay);

//        FixedStopTime stopTime = new FixedStopTime();
		TemporalTriggerDefinition stopTrigger = new TemporalTriggerDefinition();
		FixedDuration stopTimeDuration = new FixedDuration();
		stopTimeDuration.setUnitOfTime(UnitOfTimeEnum.DAY);
		stopTimeDuration.setValue(150d);
		stopTrigger.setTimeScale(TimeScaleEnum.SIMULATOR_TIME_SCALE);
		stopTrigger.setTimeSinceTimeScaleZero(stopTimeDuration);
		schoolClosureControlMeasure.getControlMeasureStopTime().add(stopTrigger);

		FixedDuration standDownDelay = new FixedDuration();
		standDownDelay.setUnitOfTime(UnitOfTimeEnum.DAY);
		standDownDelay.setValue(150d);
		schoolClosureControlMeasure.setControlMeasureStandDownDelay(standDownDelay);

		FixedDuration closureDuration = new FixedDuration();
		closureDuration.setUnitOfTime(UnitOfTimeEnum.DAY);
		closureDuration.setValue(56d);

		schoolClosureControlMeasure.setDescription(description);
		schoolClosureControlMeasure.setClosurePeriod(closureDuration);
		return schoolClosureControlMeasure;
	}

	@Override
	protected Authentication getAuthentication() {
		Authentication auth = new Authentication();
		auth.setRequesterId("apollo_user");
		auth.setRequesterPassword("preemptiveVaccinationAdminSchedule=100,200,300,400,500");
		return auth;
	}
}
