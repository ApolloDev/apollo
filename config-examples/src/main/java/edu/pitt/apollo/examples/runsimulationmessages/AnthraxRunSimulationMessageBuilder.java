package edu.pitt.apollo.examples.runsimulationmessages;

import edu.pitt.apollo.services_common.v3_0_2.ApolloSoftwareTypeEnum;
import edu.pitt.apollo.services_common.v3_0_2.Authentication;
import edu.pitt.apollo.services_common.v3_0_2.SoftwareIdentification;
import edu.pitt.apollo.simulator_service_types.v3_0_2.RunSimulationMessage;
import edu.pitt.apollo.types.v3_0_2.*;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;
import java.math.BigInteger;
import java.util.*;

/**
 * Author: Nick Millett
 * Email: nick.millett@gmail.com
 * Date: Sep 3, 2014
 * Time: 10:54:28 AM
 * Class: AnthraxRunSimulationMessageBuilder
 */
public class AnthraxRunSimulationMessageBuilder extends AbstractRunSimulationMessageBuilder {

    private static final double DEFAULT_LATENT_PERIOD = 2d;
    private static final double DEFAULT_INFECTIOUS_PERIOD = 6d;
    private static final double DEFAULT_ASYMPTOMTIC_INFECTION_FRACTION = 0.33;
    private static final double DEFAULT_R0 = 1.3;
    private static final String DEFAULT_RUN_LENGTH = "150";
    private static final String DEFAULT_INCITS = "06037";

    @Override
    protected SoftwareIdentification getSoftwareIdentification() {
        SoftwareIdentification softwareId = new SoftwareIdentification();
        softwareId.setSoftwareDeveloper("UPitt");
        softwareId.setSoftwareName("Anthrax");
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
        code.setCladeName("Bacillus anthracis");
        code.setNcbiTaxonId("1392");
        infection.setPathogen(code);
        infection.setHost("9606");

        InfectiousDisease disease = new InfectiousDisease();

        disease.setDisease("Anthrax");
        disease.setCausalPathogen(code);
        disease.setSpeciesWithDisease("9606");

        UncertainDuration incubationPeriodDuration = new UncertainDuration();
        LogNormalDistribution incubationPeriodDistribution = new LogNormalDistribution();
        incubationPeriodDistribution.setMean((4.54) / 24);
        incubationPeriodDistribution.setStandardDeviation((0.19) / 24);
        incubationPeriodDuration.setProbabilityDistribution(incubationPeriodDistribution);
        incubationPeriodDuration.setUnitOfTime(UnitOfTimeEnum.DAY);
        disease.setIncubationPeriod(incubationPeriodDuration);

        UncertainDuration prodromalPeriodDuration = new UncertainDuration();
        LogNormalDistribution prodromalPeriodDistribution = new LogNormalDistribution();
        prodromalPeriodDistribution.setMean((4.58) / 24);
        prodromalPeriodDistribution.setStandardDeviation((0.44) / 24);
        prodromalPeriodDuration.setProbabilityDistribution(prodromalPeriodDistribution);
        prodromalPeriodDuration.setUnitOfTime(UnitOfTimeEnum.DAY);
        disease.setProdromalPeriod(prodromalPeriodDuration);

        UncertainDuration fulminantPeriodDuration = new UncertainDuration();
        LogNormalDistribution fulminantPeriodDistribution = new LogNormalDistribution();
        fulminantPeriodDistribution.setMean((3.58) / 24);
        fulminantPeriodDistribution.setStandardDeviation((0.36) / 24);
        fulminantPeriodDuration.setProbabilityDistribution(fulminantPeriodDistribution);
        fulminantPeriodDuration.setUnitOfTime(UnitOfTimeEnum.DAY);
        disease.setFulminantPeriod(fulminantPeriodDuration);

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
        population.setLocation(DEFAULT_INCITS);
        population.setTaxonId("9606");
        population.setCount(new BigInteger("9000000"));

        PopulationInfectionAndImmunityCensus census = new PopulationInfectionAndImmunityCensus();

        census.setDescription("LA County Population");
        census.setReferenceDate(getDate());
        census.setLocation(getLocation());

        ApolloPathogenCode code = new ApolloPathogenCode();
        code.setCladeName("Bacillus anthracis");
        code.setNcbiTaxonId("1392");
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

        Double[] capacityArray = new Double[150];
        for (int i = 0; i < 150; i++) {
            capacityArray[i] = new Double(3000 + i);
        }

        Location location = getLocation();
        XMLGregorianCalendar date = getDate();

        List<InfectiousDiseaseControlStrategy> strategies = new ArrayList<InfectiousDiseaseControlStrategy>();

        InfectiousDiseaseScenario scenario = simulatorConfiguration.getInfectiousDiseaseScenario();

        if (controlMeasureTypes.contains(ControlMeasureTypeEnum.DRUG_CM)) {
            strategies.add(getDrugTreatmentControlStrategy("antibacterial treatment",
                    date, capacityArray, location));
        }

        scenario.getInfectiousDiseaseControlStrategies().addAll(strategies);

        return simulatorConfiguration;

    }

    public static InfectiousDiseaseControlStrategy getDrugTreatmentControlStrategy(String description,
                                                                                   XMLGregorianCalendar startDate, Double[] supplyConstrainedCapacityTable, Location location) {

        double asympEfficacy = 0.9;
        double prodEfficacy = 0.55;
        double fulEfficacy = 0.55;

        DrugTreatment drugTreatment = new DrugTreatment();
        drugTreatment.setDescription(description);

        drugTreatment.setSpeciesOfTreatedOrganism("9606");
        drugTreatment.setNumDosesInTreatmentCourse(BigInteger.ONE);

        ApolloPathogenCode strain = new ApolloPathogenCode();
        strain.setNcbiTaxonId("1392");
        strain.setCladeName("Bacillus anthracis");

        DrugTreatmentEfficacyForSimulatorConfiguration dte = new DrugTreatmentEfficacyForSimulatorConfiguration();
        ConditionalProbabilityDistribution distribution = new ConditionalProbabilityDistribution();
        ConditioningVariable firstVariable = new ConditioningVariable();
        Category diseaseOutcomeCategory = new Category();
        DiseaseOutcomeCategoryDefinition categoryDefinition = new DiseaseOutcomeCategoryDefinition();
        categoryDefinition.setDiseaseOutcome(DiseaseOutcomeEnum.ASYMPTOMATIC);
        diseaseOutcomeCategory.setCategoryDefinition(categoryDefinition);
        DiscreteNonparametricProbabilityDistribution categoryDistribution = new DiscreteNonparametricProbabilityDistribution();
        ProbabilityValuePair probability = new ProbabilityValuePair();
        probability.setProbability(1.0);
        probability.setValue(asympEfficacy);
        categoryDistribution.getProbabilityValuePairs().add(probability);
        diseaseOutcomeCategory.setUnconditionalProbabilityDistribution(categoryDistribution);
        firstVariable.getCategories().add(diseaseOutcomeCategory);
        firstVariable.setName(ConditioningVariableEnum.DISEASE_OUTCOME);
        distribution.setFirstConditioningVariable(firstVariable);
        dte.setDrugEfficaciesConditionedOnCurrentDiseaseOutcome(distribution);
        dte.setAverageDrugEfficacy((asympEfficacy + prodEfficacy + fulEfficacy) / 3);
        dte.setStrainIdentifier(strain);
        dte.setHostIdentifier("9606");
        dte.setForTreatmentPreventableOutcome(TreatmentPreventableOutcomeEnum.SICKNESS_REQUIRING_MEDICAL_ATTENTION);

        drugTreatment.getDrugTreatmentEfficacy().add(dte);

        dte = new DrugTreatmentEfficacyForSimulatorConfiguration();
        distribution = new ConditionalProbabilityDistribution();
        firstVariable = new ConditioningVariable();
        diseaseOutcomeCategory = new Category();
        categoryDefinition = new DiseaseOutcomeCategoryDefinition();
        categoryDefinition.setDiseaseOutcome(DiseaseOutcomeEnum.SYMPTOMATIC);
        diseaseOutcomeCategory.setCategoryDefinition(categoryDefinition);
        categoryDistribution = new DiscreteNonparametricProbabilityDistribution();
        probability = new ProbabilityValuePair();
        probability.setProbability(1.0);
        probability.setValue(prodEfficacy);
        categoryDistribution.getProbabilityValuePairs().add(probability);
        diseaseOutcomeCategory.setUnconditionalProbabilityDistribution(categoryDistribution);
        firstVariable.getCategories().add(diseaseOutcomeCategory);
        distribution.setFirstConditioningVariable(firstVariable);
        firstVariable.setName(ConditioningVariableEnum.DISEASE_OUTCOME);
        dte.setDrugEfficaciesConditionedOnCurrentDiseaseOutcome(distribution);
        dte.setAverageDrugEfficacy((asympEfficacy + prodEfficacy + fulEfficacy) / 3);
        dte.setStrainIdentifier(strain);
        dte.setHostIdentifier("9606");
        dte.setForTreatmentPreventableOutcome(TreatmentPreventableOutcomeEnum.SICKNESS_REQUIRING_INTENSIVE_CARE);

        drugTreatment.getDrugTreatmentEfficacy().add(dte);

        dte = new DrugTreatmentEfficacyForSimulatorConfiguration();
        distribution = new ConditionalProbabilityDistribution();
        firstVariable = new ConditioningVariable();
        diseaseOutcomeCategory = new Category();
        categoryDefinition = new DiseaseOutcomeCategoryDefinition();
        categoryDefinition.setDiseaseOutcome(DiseaseOutcomeEnum.FULMINANT);
        diseaseOutcomeCategory.setCategoryDefinition(categoryDefinition);
        categoryDistribution = new DiscreteNonparametricProbabilityDistribution();
        probability = new ProbabilityValuePair();
        probability.setProbability(1.0);
        probability.setValue(fulEfficacy);
        categoryDistribution.getProbabilityValuePairs().add(probability);
        diseaseOutcomeCategory.setUnconditionalProbabilityDistribution(categoryDistribution);
        firstVariable.getCategories().add(diseaseOutcomeCategory);
        firstVariable.setName(ConditioningVariableEnum.DISEASE_OUTCOME);
        distribution.setFirstConditioningVariable(firstVariable);
        dte.setDrugEfficaciesConditionedOnCurrentDiseaseOutcome(distribution);
        dte.setAverageDrugEfficacy((asympEfficacy + prodEfficacy + fulEfficacy) / 3);
        dte.setStrainIdentifier(strain);
        dte.setHostIdentifier("9606");
        dte.setForTreatmentPreventableOutcome(TreatmentPreventableOutcomeEnum.DEATH);

        drugTreatment.getDrugTreatmentEfficacy().add(dte);

        ApolloPathogenCode code = new ApolloPathogenCode();
        code.setCladeName("1392");
        code.setNcbiTaxonId("Bacillus anthracis");
        drugTreatment.setPathogen(code);

        FixedDuration duration = new FixedDuration();
        duration.setUnitOfTime(UnitOfTimeEnum.DAY);
        duration.setValue(1d);
        drugTreatment.setDurationOfTreatmentCourse(duration);

        IndividualTreatmentControlStrategy drugTreatmentControlStrategy = new IndividualTreatmentControlStrategy();
        drugTreatmentControlStrategy.setDescription("drug treatment");
        drugTreatmentControlStrategy.setPathogen(code);

        FixedDuration responseDelay = new FixedDuration();
        responseDelay.setUnitOfTime(UnitOfTimeEnum.DAY);
        responseDelay.setValue(2.0);

        drugTreatmentControlStrategy.setControlStrategyResponseDelay(responseDelay);

        ProbabilisticParameter compliance = new ProbabilisticParameter();
        compliance.setProbability(0.2);

        drugTreatmentControlStrategy.setCompliance(compliance);
        drugTreatmentControlStrategy.setIndividualTreatment(drugTreatment);

        FixedDuration startTime = new FixedDuration();
        startTime.setUnitOfTime(UnitOfTimeEnum.DAY);
        startTime.setValue(0);

        TemporalTriggerDefinition trigger = new TemporalTriggerDefinition();
        trigger.setTimeScale(TimeScaleEnum.SIMULATOR_TIME_SCALE);
        trigger.setTimeSinceTimeScaleZero(startTime);
        drugTreatmentControlStrategy.getControlStrategyStartTime().add(trigger);

        FixedDuration stopTmie = new FixedDuration();
        stopTmie.setUnitOfTime(UnitOfTimeEnum.DAY);
        stopTmie.setValue(0);

        TemporalTriggerDefinition stopTrigger = new TemporalTriggerDefinition();
        stopTrigger.setTimeScale(TimeScaleEnum.SIMULATOR_TIME_SCALE);
        stopTrigger.setTimeSinceTimeScaleZero(startTime);
        drugTreatmentControlStrategy.getControlStrategyStopTime().add(trigger);

        FixedDuration standDownDelay = new FixedDuration();
        standDownDelay.setUnitOfTime(UnitOfTimeEnum.DAY);
        standDownDelay.setValue(150d);
        drugTreatmentControlStrategy.setControlStrategyStandDownDelay(standDownDelay);

        ControlStrategyTargetPopulationsAndPrioritization prioritization = new ControlStrategyTargetPopulationsAndPrioritization();
        drugTreatmentControlStrategy.setTargetPopulationsAndPrioritizations(prioritization);


        LogisticalSystem logisticalSystem = new LogisticalSystem();
        logisticalSystem.setProduct("drug treatment");
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
            for (int i = 0; i < supplyConstrainedCapacityTable.length; i++) {
                long dayVal = supplyConstrainedCapacityTable[i].longValue();
                for (int j = 0; j < 24; j++) {
                    ScheduleElement element = new ScheduleElement();
                    GregorianCalendar gregorianCalendar = new GregorianCalendar();
                    gregorianCalendar.setTimeInMillis(calendar.getTimeInMillis());
                    element.setDateTime(DatatypeFactory.newInstance().newXMLGregorianCalendar(gregorianCalendar));
                    element.setQuantity(BigInteger.valueOf(dayVal / 24));
                    calendar.add(Calendar.HOUR, 1);
                    outputSchedule.getScheduleElements().add(element);
                }
            }


            calendar = startDate.toGregorianCalendar();

            for (int i = 0; i < supplyConstrainedCapacityTable.length; i++) {
                long dayVal = 50000l;
                for (int j = 0; j < 24; j++) {
                    ScheduleElement element = new ScheduleElement();
                    GregorianCalendar gregorianCalendar = new GregorianCalendar();
                    gregorianCalendar.setTimeInMillis(calendar.getTimeInMillis());
                    element.setDateTime(DatatypeFactory.newInstance().newXMLGregorianCalendar(gregorianCalendar));
                    element.setQuantity(BigInteger.valueOf(dayVal / 24));
                    calendar.add(Calendar.HOUR, 1);
                    capacitySchedule.getScheduleElements().add(element);
                }
            }
        } catch (DatatypeConfigurationException ex) {
            throw new RuntimeException("DatatypeConfigurationException: " + ex.getMessage());
        }

        outputNode.getChildren().add(capacityNode);
        logisticalSystem.getLogisticalSystemNodes().add(outputNode);

        drugTreatmentControlStrategy.getLogisticalSystems().add(logisticalSystem);

        return drugTreatmentControlStrategy;
    }

    @Override
    protected Authentication getAuthentication() {
        Authentication auth = new Authentication();
        auth.setRequesterId("apollo_user");
        auth.setRequesterPassword("apollo_password");
        return auth;
    }

}
