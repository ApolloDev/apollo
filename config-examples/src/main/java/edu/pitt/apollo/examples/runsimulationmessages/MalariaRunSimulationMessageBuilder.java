package edu.pitt.apollo.examples.runsimulationmessages;

import edu.pitt.apollo.services_common.v4_0_2.Authentication;
import edu.pitt.apollo.simulator_service_types.v4_0_2.RunSimulationMessage;
import edu.pitt.apollo.types.v4_0_2.*;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;
import java.math.BigInteger;
import java.util.*;

/**
 * Created by nem41 on 7/23/15.
 */
public class MalariaRunSimulationMessageBuilder extends AbstractRunSimulationMessageBuilder {

    private static final double DEFAULT_INFECTIOUS_PERIOD = 8.0;
    private static final double DEFAULT_LATENT_PERIOD = 2.0;

    @Override
    protected SoftwareIdentification getSoftwareIdentification() {
        SoftwareIdentification softwareIdentification = new SoftwareIdentification();
        softwareIdentification.setSoftwareDeveloper("");
        softwareIdentification.setSoftwareName("OpenMalaria");
        softwareIdentification.setSoftwareVersion("2.1");
        return softwareIdentification;
    }

    @Override
    protected SimulatorTimeSpecification getSimulatorTimeSpeficiation() {

        SimulatorTimeSpecification timeSpecification = new SimulatorTimeSpecification();
        timeSpecification.setNumberOfUnitsOfTimeInOneSimulatorTimeStep(5);
        timeSpecification.setUnitOfTimeForSimulatorTimeStep(UnitOfTimeEnum.DAY);
        timeSpecification.setRunLength(new BigInteger("200"));
        return timeSpecification;
    }

    @Override
    protected List<Infection> getInfections() {
        List<Infection> infections = new ArrayList<Infection>();

        Infection infection = new Infection();

        ApolloPathogenCode code = new ApolloPathogenCode();
        code.setCladeName("Plasmodium falciparum");
        code.setNcbiTaxonId("5833");
        infection.setPathogen(code);
        infection.setHost("9606");

        InfectionAcquisitionFromInfectedHost iafih = new InfectionAcquisitionFromInfectedHost();
        iafih.setInfectedHost("7165");

        FixedDuration infectiousPeriod = new FixedDuration();
        infectiousPeriod.setUnitOfTime(UnitOfTimeEnum.DAY);
        infectiousPeriod.setValue(DEFAULT_INFECTIOUS_PERIOD);

        FixedDuration latentPeriod = new FixedDuration();
        latentPeriod.setUnitOfTime(UnitOfTimeEnum.DAY);
        latentPeriod.setValue(DEFAULT_LATENT_PERIOD);

        Rate eir = new Rate();
        eir.setValue(50d);
        eir.setNumeratorUnitOfMeasure(UnitOfMeasureEnum.INOCULATIONS);
        eir.setDenominatorUnitOfMeasure(UnitOfMeasureEnum.PER_INDIVIDUAL_PER_YEAR);
        iafih.setInoculationRate(eir);

        iafih.setInfectiousPeriodDuration(infectiousPeriod);
        iafih.setLatentPeriodDuration(latentPeriod);

        infection.getInfectionAcquisitionsFromInfectedHosts().add(iafih);

        iafih = new InfectionAcquisitionFromInfectedHost();
        iafih.setInfectedHost("7173");

        eir = new Rate();
        eir.setValue(75d);
        eir.setNumeratorUnitOfMeasure(UnitOfMeasureEnum.INOCULATIONS);
        eir.setDenominatorUnitOfMeasure(UnitOfMeasureEnum.PER_INDIVIDUAL_PER_YEAR);
        iafih.setInoculationRate(eir);

        iafih.setInfectiousPeriodDuration(infectiousPeriod);
        iafih.setLatentPeriodDuration(latentPeriod);

        infection.getInfectionAcquisitionsFromInfectedHosts().add(iafih);

        iafih = new InfectionAcquisitionFromInfectedHost();
        iafih.setInfectedHost("62324");

        eir = new Rate();
        eir.setValue(100d);
        eir.setNumeratorUnitOfMeasure(UnitOfMeasureEnum.INOCULATIONS);
        eir.setDenominatorUnitOfMeasure(UnitOfMeasureEnum.PER_INDIVIDUAL_PER_YEAR);
        iafih.setInoculationRate(eir);

        iafih.setInfectiousPeriodDuration(infectiousPeriod);
        iafih.setLatentPeriodDuration(latentPeriod);

        infection.getInfectionAcquisitionsFromInfectedHosts().add(iafih);

        infections.add(infection);

        return infections;
    }

    @Override
    protected List<Population> getPopulations() {
        List<PopulationInfectionAndImmunityCensus> censuses = new ArrayList<PopulationInfectionAndImmunityCensus>();

        Population population = new Population();
        population.setTaxonId("9606");
        population.setLocation(getLocation().getApolloLocationCode());
        population.setCount(new BigInteger("25000"));

        PopulationInfectionAndImmunityCensus census = new PopulationInfectionAndImmunityCensus();

        census.setDescription("Mozambique");
        census.setReferenceDate(getDate());
        census.setLocation(getLocation());

        ApolloPathogenCode code = new ApolloPathogenCode();
        code.setNcbiTaxonId("5855");
        census.setPathogen(code);

        PopulationInfectionAndImmunityCensusData data = new PopulationInfectionAndImmunityCensusData();
        data.setLocation(getLocation());

        PopulationInfectionAndImmunityCensusDataCell susceptibleCell = new PopulationInfectionAndImmunityCensusDataCell();
        susceptibleCell.setInfectionState(InfectionStateEnum.SUSCEPTIBLE);
        susceptibleCell.setFractionInState(0.02);
        AgeRangeCategoryDefinition ageRangeCategoryDefinition = new AgeRangeCategoryDefinition();
		Bound lb = new Bound();
		lb.setFiniteBoundary(0d);
		lb.setFiniteBoundaryType(FiniteBoundaryTypeEnum.INCLUSIVE);
		ageRangeCategoryDefinition.setLowerBound(lb);
		
		Bound ub = new Bound();
		ub.setFiniteBoundary(5d);
		ub.setFiniteBoundaryType(FiniteBoundaryTypeEnum.INCLUSIVE);
		ageRangeCategoryDefinition.setUpperBound(ub);
        //TODO: Fix this
       /* ageRangeCategoryDefinition.setLowerBound(new BigInteger("0"));
        ageRangeCategoryDefinition.setUpperBound(new BigInteger("5"));*/
        ageRangeCategoryDefinition.setUnitOfTimeForLowerBound(UnitOfTimeEnum.YEAR);
        ageRangeCategoryDefinition.setUnitOfTimeForUpperBound(UnitOfTimeEnum.YEAR);
        susceptibleCell.setAgeRange(ageRangeCategoryDefinition);
        data.getCensusDataCells().add(susceptibleCell);

        susceptibleCell = new PopulationInfectionAndImmunityCensusDataCell();
        susceptibleCell.setInfectionState(InfectionStateEnum.SUSCEPTIBLE);
        susceptibleCell.setFractionInState(0.08);
        ageRangeCategoryDefinition = new AgeRangeCategoryDefinition();
		lb = new Bound();
		lb.setFiniteBoundary(5d);
		lb.setFiniteBoundaryType(FiniteBoundaryTypeEnum.INCLUSIVE);
		ageRangeCategoryDefinition.setLowerBound(lb);
		
		ub = new Bound();
		ub.setFiniteBoundary(12d);
		ub.setFiniteBoundaryType(FiniteBoundaryTypeEnum.INCLUSIVE);
		ageRangeCategoryDefinition.setUpperBound(ub);
        //TODO: Fix this
      /*  ageRangeCategoryDefinition.setLowerBound(new BigInteger("5"));
        ageRangeCategoryDefinition.setUpperBound(new BigInteger("12"));*/
        ageRangeCategoryDefinition.setUnitOfTimeForLowerBound(UnitOfTimeEnum.YEAR);
        ageRangeCategoryDefinition.setUnitOfTimeForUpperBound(UnitOfTimeEnum.YEAR);
        susceptibleCell.setAgeRange(ageRangeCategoryDefinition);
        data.getCensusDataCells().add(susceptibleCell);

        susceptibleCell = new PopulationInfectionAndImmunityCensusDataCell();
        susceptibleCell.setInfectionState(InfectionStateEnum.SUSCEPTIBLE);
        susceptibleCell.setFractionInState(0.2);
        ageRangeCategoryDefinition = new AgeRangeCategoryDefinition();
		lb = new Bound();
		lb.setFiniteBoundary(12d);
		lb.setFiniteBoundaryType(FiniteBoundaryTypeEnum.INCLUSIVE);
		ageRangeCategoryDefinition.setLowerBound(lb);
		
		ub = new Bound();
		ub.setFiniteBoundary(25d);
		ub.setFiniteBoundaryType(FiniteBoundaryTypeEnum.INCLUSIVE);
		ageRangeCategoryDefinition.setUpperBound(ub);
        //TODO:Fix this
      /*  ageRangeCategoryDefinition.setLowerBound(new BigInteger("12"));
        ageRangeCategoryDefinition.setUpperBound(new BigInteger("25"));*/
        ageRangeCategoryDefinition.setUnitOfTimeForLowerBound(UnitOfTimeEnum.YEAR);
        ageRangeCategoryDefinition.setUnitOfTimeForUpperBound(UnitOfTimeEnum.YEAR);
        susceptibleCell.setAgeRange(ageRangeCategoryDefinition);
        data.getCensusDataCells().add(susceptibleCell);

        susceptibleCell = new PopulationInfectionAndImmunityCensusDataCell();
        susceptibleCell.setInfectionState(InfectionStateEnum.SUSCEPTIBLE);
        susceptibleCell.setFractionInState(0.5);
        ageRangeCategoryDefinition = new AgeRangeCategoryDefinition();
		lb = new Bound();
		lb.setFiniteBoundary(25d);
		lb.setFiniteBoundaryType(FiniteBoundaryTypeEnum.INCLUSIVE);
		ageRangeCategoryDefinition.setLowerBound(lb);
		
		ub = new Bound();
		ub.setFiniteBoundary(50d);
		ub.setFiniteBoundaryType(FiniteBoundaryTypeEnum.INCLUSIVE);
		ageRangeCategoryDefinition.setUpperBound(ub);
        //TODO:Fix this
      /*  ageRangeCategoryDefinition.setLowerBound(new BigInteger("25"));
        ageRangeCategoryDefinition.setUpperBound(new BigInteger("50"));*/
        ageRangeCategoryDefinition.setUnitOfTimeForLowerBound(UnitOfTimeEnum.YEAR);
        ageRangeCategoryDefinition.setUnitOfTimeForUpperBound(UnitOfTimeEnum.YEAR);
        susceptibleCell.setAgeRange(ageRangeCategoryDefinition);
        data.getCensusDataCells().add(susceptibleCell);

        susceptibleCell = new PopulationInfectionAndImmunityCensusDataCell();
        susceptibleCell.setInfectionState(InfectionStateEnum.SUSCEPTIBLE);
        susceptibleCell.setFractionInState(0.2);
        ageRangeCategoryDefinition = new AgeRangeCategoryDefinition();
		lb = new Bound();
		lb.setFiniteBoundary(50d);
		lb.setFiniteBoundaryType(FiniteBoundaryTypeEnum.INCLUSIVE);
		ageRangeCategoryDefinition.setLowerBound(lb);
		
		ub = new Bound();
		ub.setFiniteBoundary(100d);
		ub.setFiniteBoundaryType(FiniteBoundaryTypeEnum.INCLUSIVE);
		ageRangeCategoryDefinition.setUpperBound(ub);
        //TODO:Fix this
      /*  ageRangeCategoryDefinition.setLowerBound(new BigInteger("50"));
        ageRangeCategoryDefinition.setUpperBound(new BigInteger("100"));*/
        ageRangeCategoryDefinition.setUnitOfTimeForLowerBound(UnitOfTimeEnum.YEAR);
        ageRangeCategoryDefinition.setUnitOfTimeForUpperBound(UnitOfTimeEnum.YEAR);
        susceptibleCell.setAgeRange(ageRangeCategoryDefinition);
        data.getCensusDataCells().add(susceptibleCell);

        census.setCensusData(data);

        censuses.add(census);
        population.getInfectionAndImmunityCensuses().add(census);
        List<Population> populations = new ArrayList<>();
        populations.add(population);

        return populations;
    }

    private IndoorResidualSprayingVectorControlMeasure getIRSControlMeasure(XMLGregorianCalendar startDate) {

        IndoorResidualSprayingVectorControlMeasure strategy = new IndoorResidualSprayingVectorControlMeasure();
        setBaseInfectiousDiseaseControlMeasure(strategy);

        ((TemporalTriggerDefinition) strategy.getControlMeasureStartTime().get(0)).getTimeSinceTimeScaleZero().setValue(0);
        strategy.setFractionOfVectorIndividualsAffected(0.8);

        LogisticalSystem logisticalSystem = new LogisticalSystem();
        logisticalSystem.setProduct("Insecticide");
        LogisticalSystemNode outputNode = new LogisticalSystemNode();
        Schedule outputSchedule = new Schedule();
        outputNode.setOutputSchedule(outputSchedule);
        outputSchedule.setUnitOfMeasure(UnitOfMeasureEnum.INDIVIDUAL_TREATMENTS);


        Calendar calendar = startDate.toGregorianCalendar();

        try {
            for (int i = 0; i < 30; i++) {
                ScheduleElement element = new ScheduleElement();
                GregorianCalendar gregorianCalendar = new GregorianCalendar();
                gregorianCalendar.setTimeInMillis(calendar.getTimeInMillis());
                element.setDateTime(DatatypeFactory.newInstance().newXMLGregorianCalendar(gregorianCalendar));
                element.setQuantity(BigInteger.valueOf(500 + i));
                calendar.add(Calendar.DATE, 5);
                outputSchedule.getScheduleElements().add(element);
            }
        } catch (DatatypeConfigurationException ex) {
            throw new RuntimeException("DatatypeConfigurationException: " + ex.getMessage());
        }

        logisticalSystem.getLogisticalSystemNodes().add(outputNode);
        strategy.getLogisticalSystems().add(logisticalSystem);
		
		
		TemplatedInfectiousDiseaseControlMeasureUrlForSoftware templatedInfectiousDiseaseControlMeasureUrlForSoftware
				= new TemplatedInfectiousDiseaseControlMeasureUrlForSoftware();
		templatedInfectiousDiseaseControlMeasureUrlForSoftware.getSoftwareIdentification().add(getSoftwareIdentification());
		templatedInfectiousDiseaseControlMeasureUrlForSoftware.setControlMeasureTemplateUrl("https://git.isg.pitt.edu/simulatorservices/simulatorinputtemplates/raw/master/openmalaria/by-location-name/mozambique/v32/irs-1.xml");
		strategy.getTemplatedInfectiousDiseaseControlMeasureUrlsForSoftware().add(templatedInfectiousDiseaseControlMeasureUrlForSoftware);

        return strategy;
    }

    private InsecticideTreatedNetControlMeasure getITNControlMeasure(XMLGregorianCalendar startDate) {

        InsecticideTreatedNetControlMeasure strategy = new InsecticideTreatedNetControlMeasure();
        setBaseInfectiousDiseaseControlMeasure(strategy);

        ((TemporalTriggerDefinition) strategy.getControlMeasureStartTime().get(0)).getTimeSinceTimeScaleZero().setValue(0);

        LogisticalSystem logisticalSystem = new LogisticalSystem();
        logisticalSystem.setProduct("Insecticide Treated Net");
        LogisticalSystemNode outputNode = new LogisticalSystemNode();
        Schedule outputSchedule = new Schedule();
        outputNode.setOutputSchedule(outputSchedule);
        outputSchedule.setUnitOfMeasure(UnitOfMeasureEnum.INDIVIDUAL_TREATMENTS);


        Calendar calendar = startDate.toGregorianCalendar();

        try {
            for (int i = 0; i < 60; i++) {
                ScheduleElement element = new ScheduleElement();
                GregorianCalendar gregorianCalendar = new GregorianCalendar();
                gregorianCalendar.setTimeInMillis(calendar.getTimeInMillis());
                element.setDateTime(DatatypeFactory.newInstance().newXMLGregorianCalendar(gregorianCalendar));
                element.setQuantity(BigInteger.valueOf(1000 + i * 10));
                calendar.add(Calendar.DATE, 5);
                outputSchedule.getScheduleElements().add(element);
            }
        } catch (DatatypeConfigurationException ex) {
            throw new RuntimeException("DatatypeConfigurationException: " + ex.getMessage());
        }

        logisticalSystem.getLogisticalSystemNodes().add(outputNode);
        strategy.getLogisticalSystems().add(logisticalSystem);

        LogNormalDistribution dist = new LogNormalDistribution();
        dist.setMean(0.5);
        dist.setStandardDeviation(0.2);

        Rate holingRate = new Rate();
        holingRate.setNumeratorUnitOfMeasure(UnitOfMeasureEnum.NET_HOLES);
        holingRate.setDenominatorUnitOfMeasure(UnitOfMeasureEnum.DAYS);
        holingRate.setProbabilityDistribution(dist);

        strategy.setNetHolingRate(holingRate);

        strategy.setNightlyProbabilityOfUse(0.8);

        Rate decayRate = new Rate();
        decayRate.setValue(0.1);
        decayRate.setNumeratorUnitOfMeasure(UnitOfMeasureEnum.PERCENT);
        decayRate.setDenominatorUnitOfMeasure(UnitOfMeasureEnum.DAYS);
        strategy.setInsecticideEfficacyDecayRate(decayRate);

		TemplatedInfectiousDiseaseControlMeasureUrlForSoftware templatedInfectiousDiseaseControlMeasureUrlForSoftware
				= new TemplatedInfectiousDiseaseControlMeasureUrlForSoftware();
		templatedInfectiousDiseaseControlMeasureUrlForSoftware.getSoftwareIdentification().add(getSoftwareIdentification());
		templatedInfectiousDiseaseControlMeasureUrlForSoftware.setControlMeasureTemplateUrl("https://git.isg.pitt.edu/simulatorservices/simulatorinputtemplates/raw/master/openmalaria/by-location-name/mozambique/v32/itn-1.xml");
		strategy.getTemplatedInfectiousDiseaseControlMeasureUrlsForSoftware().add(templatedInfectiousDiseaseControlMeasureUrlForSoftware);

		
        return strategy;
    }

    @Override
    protected Location getLocation() {
        Location location = new Location();
        location.setApolloLocationCode("4953");
        return location;
    }

    @Override
    protected Authentication getAuthentication() {
        Authentication authentication = new Authentication();
        return authentication;
    }

    @Override
    public RunSimulationMessage getRunSimulationMessage(Set<ControlMeasureTypeEnum> controlMeasureTypes) {
        RunSimulationMessage simulatorConfiguration = getBaseRunSimulationMessage();

        InfectiousDiseaseScenario scenario = simulatorConfiguration.getInfectiousDiseaseScenario();
        if (controlMeasureTypes.contains(ControlMeasureTypeEnum.INDOOR_RESIDUAL_SPRAYING)) {
            scenario
                    .getInfectiousDiseaseControlStrategies().add(getIRSControlMeasure(scenario.getScenarioDate()));
        }
        if (controlMeasureTypes.contains(ControlMeasureTypeEnum.INSECTICIDE_TREATED_NET)) {
            scenario.getInfectiousDiseaseControlStrategies().add(getITNControlMeasure(scenario.getScenarioDate()));
        }

        return simulatorConfiguration;
    }
}
