package edu.pitt.apollo.examples.runsimulationmessages;

import edu.pitt.apollo.types.v3_0_2.ApolloSoftwareTypeEnum;;
import edu.pitt.apollo.services_common.v3_0_2.Authentication;
import edu.pitt.apollo.types.v3_0_2.SoftwareIdentification;
import edu.pitt.apollo.simulator_service_types.v3_0_2.RunSimulationMessage;
import edu.pitt.apollo.types.v3_0_2.*;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * Author: Nick Millett
 * Email: nick.millett@gmail.com
 * Date: Sep 3, 2014
 * Time: 10:25:12 AM
 * Class: DengueRunSimulationMessageBuilder
 */
public class DengueRunSimulationMessageBuilder extends AbstractRunSimulationMessageBuilder {

    private static final double DEFAULT_ASYMPTOMTIC_INFECTION_FRACTION = 0.75;
    private static final String DEFAULT_RUN_LENGTH = "112";
    private static final String DEFAULT_INCITS = "AU35005";
    private static final double DEFAULT_LATENT_PERIOD = 2d;
    private static final double DEFAULT_INFECTIOUS_PERIOD = 6d;

    private MosquitoReproduction getMosquitoReproduction() {

        MosquitoReproduction reproduction = new MosquitoReproduction();

        FixedDuration gonotrophicPeriodDuration = new FixedDuration();
        gonotrophicPeriodDuration.setUnitOfTime(UnitOfTimeEnum.DAY);
        gonotrophicPeriodDuration.setValue(3);
        reproduction.setGonotrophicPeriodDuration(gonotrophicPeriodDuration);

        BigInteger eggsPerBrood = new BigInteger("24");
        reproduction.setEggsPerBrood(eggsPerBrood);

        BigInteger minEggsPerOviposition = new BigInteger("10");
        reproduction.setMinimumEggsPerOviposition(minEggsPerOviposition);

        Rate ageDependentFecundityReduction = new Rate();
        ageDependentFecundityReduction.setNumeratorUnitOfMeasure(UnitOfMeasureEnum.PERCENT);
        ageDependentFecundityReduction.setDenominatorUnitOfMeasure(UnitOfMeasureEnum.HOURS);
        ageDependentFecundityReduction.setValue(0.0);
        reproduction.setAgeDependentFecundityReduction(ageDependentFecundityReduction);

        return reproduction;
    }

    private Behavior getHumanBehavior() {

        HumanBehavior humanBehavior = new HumanBehavior();
        humanBehavior.setBuildingAffinity(0.35);
        Rate speedOfMovement = new Rate();
        speedOfMovement.setDenominatorUnitOfMeasure(UnitOfMeasureEnum.HOURS);
        speedOfMovement.setNumeratorUnitOfMeasure(UnitOfMeasureEnum.METERS);
        speedOfMovement.setValue(7.5);
        humanBehavior.setSpeedOfMovement(speedOfMovement);

        return humanBehavior;
    }

    private Behavior getMosquitoBehavior() {
        MosquitoBehavior mosquitoBehavior = new MosquitoBehavior();
        ProbabilisticParameter biteProbability = new ProbabilisticParameter();
        biteProbability.setProbability(0.9);
        mosquitoBehavior.setBiteProbability(biteProbability);

        Distance biteRadius = new Distance();
        biteRadius.setUnitOfDistance(UnitOfDistanceEnum.METERS);
        biteRadius.setValue(5.0);
        mosquitoBehavior.setBiteRadius(biteRadius);

        FixedDuration timeBetweenBites = new FixedDuration();
        timeBetweenBites.setUnitOfTime(UnitOfTimeEnum.HOUR);
        timeBetweenBites.setValue(8.0);
        mosquitoBehavior.setTimeBetweenBites(timeBetweenBites);

        Rate migrationSpeed = new Rate();
        migrationSpeed.setNumeratorUnitOfMeasure(UnitOfMeasureEnum.METERS);
        migrationSpeed.setDenominatorUnitOfMeasure(UnitOfMeasureEnum.HOURS);
        migrationSpeed.setValue(4.0);
        mosquitoBehavior.setMigrationSpeed(migrationSpeed);

        double shadeAffinity = 0.9;
        mosquitoBehavior.setShadeAffinity(shadeAffinity);

        Distance maleMatingRadius = new Distance();
        maleMatingRadius.setUnitOfDistance(UnitOfDistanceEnum.METERS);
        maleMatingRadius.setValue(1.0);
        mosquitoBehavior.setMaleMatingRadius(maleMatingRadius);

        double matingProbability = 0.1;
        mosquitoBehavior.setMatingProbability(matingProbability);

        Distance straightTravelAfterOviposit = new Distance();
        straightTravelAfterOviposit.setUnitOfDistance(UnitOfDistanceEnum.METERS);
        straightTravelAfterOviposit.setValue(5.0);
        mosquitoBehavior.setStraightTravelAfterOviposit(straightTravelAfterOviposit);

        return mosquitoBehavior;
    }

    private LifeCycle getMosquitoLifeCycle() {


        LifeCycle cycle = new LifeCycle();

        LifeStageWithDurationAndMortality eggLifeStage = new LifeStageWithDurationAndMortality();
        eggLifeStage.setStage(DevelopmentalStageEnum.EGG);
        FixedDuration eggDuration = new FixedDuration();
        eggDuration.setUnitOfTime(UnitOfTimeEnum.DAY);
        eggDuration.setValue(4);
        eggLifeStage.setDuration(eggDuration);
        Rate eggMortalityRate = new Rate();
        eggMortalityRate.setDenominatorUnitOfMeasure(UnitOfMeasureEnum.DAYS);
        eggMortalityRate.setNumeratorUnitOfMeasure(UnitOfMeasureEnum.PERCENT);
        eggMortalityRate.setValue(1.0);
        eggLifeStage.setMortalityRate(eggMortalityRate);

        cycle.getLifeStagesWithDurationsAndMortalities().add(eggLifeStage);

        LifeStageWithDurationAndMortality larvalLifeStage = new LifeStageWithDurationAndMortality();
        larvalLifeStage.setStage(DevelopmentalStageEnum.LARVAL_AND_PUPAL);
        FixedDuration larvalDuration = new FixedDuration();
        larvalDuration.setUnitOfTime(UnitOfTimeEnum.DAY);
        larvalDuration.setValue(14);
        larvalLifeStage.setDuration(larvalDuration);
        ClaraDensityDependentMortalityFunction larvalMortalityFunction = new ClaraDensityDependentMortalityFunction();
        larvalMortalityFunction.setOmega(1.0);
        larvalMortalityFunction.setSigma(2.5);
        larvalLifeStage.setMortalityFunction(larvalMortalityFunction);

        cycle.getLifeStagesWithDurationsAndMortalities().add(larvalLifeStage);

        LifeStageWithDurationAndMortality adultLifeStage = new LifeStageWithDurationAndMortality();
        adultLifeStage.setStage(DevelopmentalStageEnum.ADULT_FORM);
        FixedDuration adultDuration = new FixedDuration();
        adultDuration.setUnitOfTime(UnitOfTimeEnum.DAY);
        adultDuration.setValue(21);
        adultLifeStage.setDuration(adultDuration);
        Rate adultMortalityRate = new Rate();
        adultMortalityRate.setDenominatorUnitOfMeasure(UnitOfMeasureEnum.DAYS);
        adultMortalityRate.setNumeratorUnitOfMeasure(UnitOfMeasureEnum.PERCENT);
        adultMortalityRate.setValue(11.0);
        adultLifeStage.setMortalityRate(adultMortalityRate);

        cycle.getLifeStagesWithDurationsAndMortalities().add(adultLifeStage);

        return cycle;
    }

    private WolbachiaControlMeasure getWolbachiaControlMeasure() {

        WolbachiaControlMeasure strategy = new WolbachiaControlMeasure();
        setBaseInfectiousDiseaseControlMeasure(strategy);

        FixedDuration endDuration = new FixedDuration();
        endDuration.setValue(0d);
        endDuration.setUnitOfTime(UnitOfTimeEnum.DAY);

        TemporalTriggerDefinition time = new TemporalTriggerDefinition();
        time.setTimeScale(TimeScaleEnum.SIMULATOR_TIME_SCALE);
        time.setTimeSinceTimeScaleZero(endDuration);
        strategy.getControlMeasureStopTime().add(time);

        strategy.setWolbachiaSitesEveryNth(new BigInteger("4"));
        strategy.setWolbachiaSeedAdultsPerHouse(new BigInteger("14"));
        strategy.setClearWolbachiaOnMigration(Boolean.FALSE);
        strategy.setWolbachiaReleaseSites(WolbachiaReleaseSiteEnum.ROADS);

        FixedDuration releaseInterval = new FixedDuration();
        releaseInterval.setUnitOfTime(UnitOfTimeEnum.DAY);
        releaseInterval.setValue(7.0);
        strategy.setWolbachiaReleaseInterval(releaseInterval);

        strategy.setWolbachiaEffectOnEggMortalityRate(0.0);
        strategy.setWolbachiaEffectOnLarvalMortalityRate(0.0);
        strategy.setWolbachiaEffectOnAdultMortalityRate(0.15);
        strategy.setWolbachiaEffectOnFecundity(0.15);
        strategy.setWolbachiaEffectOnMatingProbability(0.0);
        strategy.setWolbachiaEffectOnLeakageRate(0.0);
        strategy.setWolbachiaEffectOnVectorialCapacity(0.0);
        strategy.setWolbachiaPreReleaseAdultSuppressionEfficacy(0.0);
        strategy.setWolbachiaPreReleaseLarvalSuppressionEfficacy(0.0);

        return strategy;
    }

    private IndoorResidualSprayingVectorControlMeasure getIRSControlMeasure() {

        IndoorResidualSprayingVectorControlMeasure strategy = new IndoorResidualSprayingVectorControlMeasure();
        setBaseInfectiousDiseaseControlMeasure(strategy);

        ((TemporalTriggerDefinition) strategy.getControlMeasureStartTime().get(0)).getTimeSinceTimeScaleZero().setValue(113);
        strategy.setFractionOfVectorIndividualsAffected(0.8);

        Distance coverRadius = new Distance();
        coverRadius.setUnitOfDistance(UnitOfDistanceEnum.METERS);
        coverRadius.setValue(100);
        strategy.setCoverRadius(coverRadius);

        return strategy;
    }

    private LarvicideControlMeasure getLarvicideControlMeasure() {

        LarvicideControlMeasure strategy = new LarvicideControlMeasure();
        setBaseInfectiousDiseaseControlMeasure(strategy);

        ((TemporalTriggerDefinition) strategy.getControlMeasureStartTime().get(0)).getTimeSinceTimeScaleZero().setValue(113);

        Distance coverRadius = new Distance();
        coverRadius.setUnitOfDistance(UnitOfDistanceEnum.METERS);
        coverRadius.setValue(100);
        strategy.setCoverRadius(coverRadius);

        return strategy;
    }

    private ContainerReductionControlMeasure getContainerReductionControlMeasure() {

        ContainerReductionControlMeasure strategy = new ContainerReductionControlMeasure();
        setBaseInfectiousDiseaseControlMeasure(strategy);

        ((TemporalTriggerDefinition) strategy.getControlMeasureStartTime().get(0)).getTimeSinceTimeScaleZero().setValue(113);

        Distance coverRadius = new Distance();
        coverRadius.setUnitOfDistance(UnitOfDistanceEnum.METERS);
        coverRadius.setValue(100);
        strategy.setCoverRadius(coverRadius);

        strategy.setFractionReductionOfEggs(0.6);
        strategy.setFractionReductionOfLarvae(0.6);

        return strategy;

    }

    private TransmissionProbability getMosquitoToHumanTransmissionProbability() {

        TransmissionProbability probability = new TransmissionProbability();
        probability.setContactDefinition(ContactDefinitionEnum.INFECTED_MOSQUITO_BITES_SUSCEPTIBLE_HUMAN);
        probability.setProbability(0.9);

        return probability;
    }

    private TransmissionProbability getHumanToMosquitoTransmissionProbability() {

        TransmissionProbability probability = new TransmissionProbability();
        probability.setContactDefinition(ContactDefinitionEnum.SUSCEPTIBLE_MOSQUITO_BITES_INFECTIOUS_HUMAN);
        probability.setProbability(0.9);

        return probability;
    }

    private AbioticThingCensus getAbioticThingCensus() {

        OvipositionSiteCensus census = new OvipositionSiteCensus();
        census.setAbioticThing(AbioticThingEnum.OVIPOSITION_SITE);
        census.setSimulatorTime(0);

        census.setReferenceDate(getDate());

        census.setBreteauIndex(new BigInteger("200"));
        census.setCarryingCapacityPerHouse(33.75);
        census.setHeterogenousCarryingCapacity(Boolean.TRUE);

        census.setRatioOfOutdoorToIndoorOvisites(0.5);

        census.setMaximumRatioOfOutdoorCarryingCapacityToIndoor(2.0);

        return census;
    }

    @Override
    protected SoftwareIdentification getSoftwareIdentification() {
        SoftwareIdentification softwareId = new SoftwareIdentification();
        softwareId.setSoftwareDeveloper("PSC");
        softwareId.setSoftwareName("Clara");
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

        ApolloPathogenCode code = new ApolloPathogenCode();
        code.setCladeName("Dengue virus 1");
        code.setNcbiTaxonId("11053");

        // the infection for mosquito to human transmission
        Infection mosquitoToHumanInfection = new Infection();
        mosquitoToHumanInfection.setPathogen(code);
        mosquitoToHumanInfection.setHost("9606");

        InfectiousDisease disease = new InfectiousDisease();
        disease.setDisease("Dengue");
        disease.setCausalPathogen(code);
        disease.setSpeciesWithDisease("9606");

        DiseaseOutcomeWithProbability dowp = new DiseaseOutcomeWithProbability();
        dowp.setDiseaseOutcome(DiseaseOutcomeEnum.ASYMPTOMATIC);
        ProbabilisticParameter asymtptomaticProb = new ProbabilisticParameter();
        asymtptomaticProb.setProbability(DEFAULT_ASYMPTOMTIC_INFECTION_FRACTION);
        dowp.setProbability(asymtptomaticProb);
        disease.getDiseaseOutcomesWithProbabilities().add(dowp);
        mosquitoToHumanInfection.getInfectiousDiseases().add(disease);

        InfectionAcquisitionFromInfectedHost infectionAcquisitionFromMosquito = new InfectionAcquisitionFromInfectedHost();
        infectionAcquisitionFromMosquito.setInfectedHost("7159");
        infectionAcquisitionFromMosquito.getTransmissionProbabilities().add(getMosquitoToHumanTransmissionProbability());

        FixedDuration infectiousPeriod = new FixedDuration();
        infectiousPeriod.setUnitOfTime(UnitOfTimeEnum.DAY);
        infectiousPeriod.setValue(DEFAULT_INFECTIOUS_PERIOD);
        infectionAcquisitionFromMosquito.setInfectiousPeriodDuration(infectiousPeriod);

        FixedDuration latentPeriod = new FixedDuration();
        latentPeriod.setUnitOfTime(UnitOfTimeEnum.DAY);
        latentPeriod.setValue(DEFAULT_LATENT_PERIOD);
        infectionAcquisitionFromMosquito.setLatentPeriodDuration(latentPeriod);

        mosquitoToHumanInfection.getInfectionAcquisitionsFromInfectedHosts().add(infectionAcquisitionFromMosquito);
        infections.add(mosquitoToHumanInfection);

        // the human to mosquito infection
        Infection humanToMosquitoInfection = new Infection();
        humanToMosquitoInfection.setPathogen(code);
        humanToMosquitoInfection.setHost("7159");

        humanToMosquitoInfection.getInfectiousDiseases().add(disease);

        InfectionAcquisitionFromInfectedHost infectionAcquisitionFromHuman = new InfectionAcquisitionFromInfectedHost();
        infectionAcquisitionFromHuman.setInfectedHost("9606");
        infectionAcquisitionFromHuman.getTransmissionProbabilities().add(getHumanToMosquitoTransmissionProbability());
        infectionAcquisitionFromHuman.setLatentPeriodDuration(latentPeriod);
        infectionAcquisitionFromHuman.setInfectiousPeriodDuration(latentPeriod);

        humanToMosquitoInfection.getInfectionAcquisitionsFromInfectedHosts().add(infectionAcquisitionFromHuman);
        infections.add(humanToMosquitoInfection);

        return infections;
    }

    @Override
    protected List<Population> getPopulations() {

        List<Population> populations = new ArrayList<>();

        Population humanPopulation = new Population();
        humanPopulation.setLocation(getLocation().getApolloLocationCode());
        humanPopulation.setTaxonId("9606");
        humanPopulation.setCount(new BigInteger("142000"));

        PopulationInfectionAndImmunityCensus humanCensus1 = new PopulationInfectionAndImmunityCensus();

        humanCensus1.setDescription("Circle population 1");
        humanCensus1.setReferenceDate(getDate());
        humanCensus1.setSimulatorTime(71);
        humanCensus1.setLocation(getLocation());

        ApolloPathogenCode code = new ApolloPathogenCode();
        code.setCladeName("Dengue virus 1");
        code.setNcbiTaxonId("11053");
        humanCensus1.setPathogen(code);

        PopulationInfectionAndImmunityCensusData humanData1 = new PopulationInfectionAndImmunityCensusData();
        humanData1.setLocation(getHumanLocation1());

        PopulationInfectionAndImmunityCensusDataCell humanSusceptibleCell1 = new PopulationInfectionAndImmunityCensusDataCell();
        humanSusceptibleCell1.setInfectionState(InfectionStateEnum.SUSCEPTIBLE);
        humanSusceptibleCell1.setFractionInState(0.9);

        PopulationInfectionAndImmunityCensusDataCell humanExposedCell1 = new PopulationInfectionAndImmunityCensusDataCell();
        humanExposedCell1.setInfectionState(InfectionStateEnum.LATENT);
        humanExposedCell1.setFractionInState(0.10);

        PopulationInfectionAndImmunityCensusDataCell humaninfectiousCell1 = new PopulationInfectionAndImmunityCensusDataCell();
        humaninfectiousCell1.setInfectionState(InfectionStateEnum.INFECTIOUS);
        humaninfectiousCell1.setFractionInState(0.0);

        PopulationInfectionAndImmunityCensusDataCell humanRecoveredCell1 = new PopulationInfectionAndImmunityCensusDataCell();
        humanRecoveredCell1.setInfectionState(InfectionStateEnum.RECOVERED);
        humanRecoveredCell1.setFractionInState(0.0);

        humanData1.getCensusDataCells().add(humanSusceptibleCell1);
        humanData1.getCensusDataCells().add(humanExposedCell1);
        humanData1.getCensusDataCells().add(humaninfectiousCell1);
        humanData1.getCensusDataCells().add(humanRecoveredCell1);

        humanCensus1.setCensusData(humanData1);
        humanPopulation.getInfectionAndImmunityCensuses().add(humanCensus1);

        IndividualBiologyAndBehavior humanIndividualBiologyAndBehavior = new IndividualBiologyAndBehavior();
        humanIndividualBiologyAndBehavior.setBehavior(getHumanBehavior());

        humanPopulation.setIndividualBiologyAndBehavior(humanIndividualBiologyAndBehavior);

        populations.add(humanPopulation);

//		PopulationInfectionAndImmunityCensus humanCensus2 = new PopulationInfectionAndImmunityCensus();
//
//		humanCensus2.setDescription("Circle population 2");
//		humanCensus2.setReferenceDate(getDate());
//		humanCensus2.setPopulationSpecies("9606");
//		humanCensus2.setSimulatorTime(71);
//		humanCensus2.setPathogen(code);
//		humanCensus2.setLocation(getLocation());
//
//		PopulationInfectionAndImmunityCensusData humanData2 = new PopulationInfectionAndImmunityCensusData();
//		humanData2.setLocation(getHumanLocation2());
//
//		PopulationInfectionAndImmunityCensusDataCell humanSusceptibleCell2 = new PopulationInfectionAndImmunityCensusDataCell();
//		humanSusceptibleCell2.setInfectionState(InfectionStateEnum.SUSCEPTIBLE);
//		humanSusceptibleCell2.setFractionInState(0.75);
//
//		PopulationInfectionAndImmunityCensusDataCell humanExposedCell2 = new PopulationInfectionAndImmunityCensusDataCell();
//		humanExposedCell2.setInfectionState(InfectionStateEnum.LATENT);
//		humanExposedCell2.setFractionInState(0.25);
//
//		PopulationInfectionAndImmunityCensusDataCell humaninfectiousCell2 = new PopulationInfectionAndImmunityCensusDataCell();
//		humaninfectiousCell2.setInfectionState(InfectionStateEnum.INFECTIOUS);
//		humaninfectiousCell2.setFractionInState(0.0);
//
//		PopulationInfectionAndImmunityCensusDataCell humanRecoveredCell2 = new PopulationInfectionAndImmunityCensusDataCell();
//		humanRecoveredCell2.setInfectionState(InfectionStateEnum.RECOVERED);
//		humanRecoveredCell2.setFractionInState(0.0);
//
//		humanData2.getCensusDataCells().add(humanSusceptibleCell2);
//		humanData2.getCensusDataCells().add(humanExposedCell2);
//		humanData2.getCensusDataCells().add(humaninfectiousCell2);
//		humanData2.getCensusDataCells().add(humanRecoveredCell2);
//
//		humanCensus2.setCensusData(humanData2);
//		censuses.add(humanCensus2);

        Population mosquitoPopulation = new Population();
        mosquitoPopulation.setLocation(getLocation().getApolloLocationCode());
        mosquitoPopulation.setTaxonId("7159");
        mosquitoPopulation.setCount(new BigInteger("5000"));

        PopulationInfectionAndImmunityCensus mosquitoCensus = new PopulationInfectionAndImmunityCensus();

        mosquitoCensus.setDescription("Mosquito population");
        mosquitoCensus.setReferenceDate(getDate());
        mosquitoCensus.setPathogen(code);
        mosquitoCensus.setLocation(getLocation());

        PopulationInfectionAndImmunityCensusData mosquitoData = new PopulationInfectionAndImmunityCensusData();
        mosquitoData.setLocation(getLocation());

        PopulationInfectionAndImmunityCensusDataCell mosquitoSusceptibleCell = new PopulationInfectionAndImmunityCensusDataCell();
        mosquitoSusceptibleCell.setInfectionState(InfectionStateEnum.SUSCEPTIBLE);
        mosquitoSusceptibleCell.setFractionInState(0.995);

        PopulationInfectionAndImmunityCensusDataCell mosquitoExposedCell = new PopulationInfectionAndImmunityCensusDataCell();
        mosquitoExposedCell.setInfectionState(InfectionStateEnum.LATENT);
        mosquitoExposedCell.setFractionInState(0.000);

        PopulationInfectionAndImmunityCensusDataCell mosquitoInfectiousCell = new PopulationInfectionAndImmunityCensusDataCell();
        mosquitoInfectiousCell.setInfectionState(InfectionStateEnum.INFECTIOUS);
        mosquitoInfectiousCell.setFractionInState(0.005);

        PopulationInfectionAndImmunityCensusDataCell mosquitoRecoveredCell = new PopulationInfectionAndImmunityCensusDataCell();
        mosquitoRecoveredCell.setInfectionState(InfectionStateEnum.RECOVERED);
        mosquitoRecoveredCell.setFractionInState(0.0);

        mosquitoData.getCensusDataCells().add(mosquitoSusceptibleCell);
        mosquitoData.getCensusDataCells().add(mosquitoExposedCell);
        mosquitoData.getCensusDataCells().add(mosquitoInfectiousCell);
        mosquitoData.getCensusDataCells().add(mosquitoRecoveredCell);

        mosquitoCensus.setCensusData(mosquitoData);

        mosquitoPopulation.getInfectionAndImmunityCensuses().add(mosquitoCensus);

        IndividualBiologyAndBehavior individualMosquitoBiologyAndBehavior = new IndividualBiologyAndBehavior();
        individualMosquitoBiologyAndBehavior.setReproduction(getMosquitoReproduction());
        individualMosquitoBiologyAndBehavior.setLifeCycle(getMosquitoLifeCycle());
        individualMosquitoBiologyAndBehavior.setBehavior(getMosquitoBehavior());

        mosquitoPopulation.setIndividualBiologyAndBehavior(individualMosquitoBiologyAndBehavior);


        populations.add(mosquitoPopulation);

        return populations;
    }

    private List<NonApolloParameter> getNonApolloParameters() {

        List<NonApolloParameter> list = new ArrayList<NonApolloParameter>();

        NonApolloParameter parameter = new NonApolloParameter();
        parameter.setParameterName("backfillEggsDay");
        parameter.setParameterValue(5);
        list.add(parameter);

        parameter = new NonApolloParameter();
        parameter.setParameterName("initialMosquitoToHumanRatio");
        parameter.setParameterValue(6.0);
        list.add(parameter);

        parameter = new NonApolloParameter();
        parameter.setParameterName("maleMosquitoRatio");
        parameter.setParameterValue(0.5);
        list.add(parameter);

        parameter = new NonApolloParameter();
        parameter.setParameterName("peakDay");
        parameter.setParameterValue(126);
        list.add(parameter);

        return list;
    }

    @Override
    protected Location getLocation() {
        Location location = new Location();
        LocationDefinition locationDefinition = new LocationDefinition();
        locationDefinition.getLocationsIncluded().add(DEFAULT_INCITS);
        location.setLocationDefinition(locationDefinition);
        return location;
    }

    private Location getHumanLocation1() {

        Location location = new Location();

        CartesianCircleLocationDefinition circleDefinition = new CartesianCircleLocationDefinition();

        Distance xDistance = new Distance();
        xDistance.setUnitOfDistance(UnitOfDistanceEnum.METERS);
        xDistance.setValue(0.0);
        circleDefinition.setEastWestOffsetFromCartesianCenter(xDistance);

        Distance yDistance = new Distance();
        yDistance.setUnitOfDistance(UnitOfDistanceEnum.METERS);
        yDistance.setValue(0.0);
        circleDefinition.setNorthSouthOffsetFromCartesianCenter(yDistance);

        Distance radius = new Distance();
        radius.setUnitOfDistance(UnitOfDistanceEnum.METERS);
        radius.setValue(100.0);
        circleDefinition.setRadius(radius);

        Distance altitude = new Distance();
        altitude.setUnitOfDistance(UnitOfDistanceEnum.METERS);
        altitude.setValue(0.0);
        circleDefinition.setAltitudeRelativeToCartesianCenter(altitude);

        location.setCartesianCircleLocationDefinition(circleDefinition);
        return location;
    }

    private Location getHumanLocation2() {

        Location location = new Location();

        CartesianCircleLocationDefinition circleDefinition = new CartesianCircleLocationDefinition();

        Distance xDistance = new Distance();
        xDistance.setUnitOfDistance(UnitOfDistanceEnum.METERS);
        xDistance.setValue(30.0);
        circleDefinition.setEastWestOffsetFromCartesianCenter(xDistance);

        Distance yDistance = new Distance();
        yDistance.setUnitOfDistance(UnitOfDistanceEnum.METERS);
        yDistance.setValue(20.0);
        circleDefinition.setNorthSouthOffsetFromCartesianCenter(yDistance);

        Distance radius = new Distance();
        radius.setUnitOfDistance(UnitOfDistanceEnum.METERS);
        radius.setValue(5.0);
        circleDefinition.setRadius(radius);

        Distance altitude = new Distance();
        altitude.setUnitOfDistance(UnitOfDistanceEnum.METERS);
        altitude.setValue(0.0);
        circleDefinition.setAltitudeRelativeToCartesianCenter(altitude);

        location.setCartesianCircleLocationDefinition(circleDefinition);
        return location;
    }

    @Override
    protected Authentication getAuthentication() {
        Authentication auth = new Authentication();
        auth.setRequesterId("apollo_user");
        auth.setRequesterPassword("apollo_password");
        return auth;
    }

    @Override
    public RunSimulationMessage getRunSimulationMessage(Set<ControlMeasureTypeEnum> controlMeasureTypes) {
        RunSimulationMessage simulatorConfiguration = getBaseRunSimulationMessage();

        InfectiousDiseaseScenario scenario = simulatorConfiguration.getInfectiousDiseaseScenario();

        scenario.getAbioticThingCensuses().add(getAbioticThingCensus());
        scenario.getNonApolloParameters().addAll(getNonApolloParameters());

        if (controlMeasureTypes.contains(ControlMeasureTypeEnum.WOLBACHIA)) {
            scenario.getInfectiousDiseaseControlStrategies().add(getWolbachiaControlMeasure());
        }
        if (controlMeasureTypes.contains(ControlMeasureTypeEnum.INDOOR_RESIDUAL_SPRAYING)) {
            scenario.getInfectiousDiseaseControlStrategies().add(getIRSControlMeasure());
        }
        if (controlMeasureTypes.contains(ControlMeasureTypeEnum.LARVICIDE)) {
            scenario.getInfectiousDiseaseControlStrategies().add(getLarvicideControlMeasure());
        }
        if (controlMeasureTypes.contains(ControlMeasureTypeEnum.CONTAINER_REDUCTION)) {
            scenario.getInfectiousDiseaseControlStrategies().add(getContainerReductionControlMeasure());
        }

        return simulatorConfiguration;
    }

}
