package edu.pitt.apollo.runmanagerserviceclient;

import edu.pitt.apollo.types.v4_0_1.*;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;
import java.util.Calendar;
import java.util.GregorianCalendar;

/**
 * Author: Nick Millett
 * Email: nick.millett@gmail.com
 * Date: Aug 28, 2014
 * Time: 11:22:08 AM
 * Class: InfectiousDiseaseScenarioCreator
 */
public class InfectiousDiseaseScenarioCreator {

    private static final String INFECTIOUS_HOST_TAXON_ID = "9606"; // human
    private static final String PATHOGEN = "114727";
    private static final String DISEASE_ID = "Influenza";
    private static final double DEFAULT_ASYMPTOMATIC_PROBABILITY = 0.33;
    private static final double DEFAULT_LATENT_PERIOD = 2.0;
    private static final double DEFAULT_INFECTIOUS_PERIOD = 8.0;
    private static final double DEFAULT_R0 = 1.3;
    private static final double DEFAULT_SUSCEPTIBLE_FRACTION = 1.0;
    private static final double DEFAULT_EXPOSED_FRACTION = 0.0;
    private static final double DEFAULT_INFECTIOUS_FRACTION = 0.0;
    private static final double DEFAULT_RECOVERED_FRACTION = 0.0;

    private static ApolloPathogenCode getApolloPathogenCode() {

        ApolloPathogenCode code = new ApolloPathogenCode();
        code.setNcbiTaxonId(PATHOGEN);

        return code;
    }

    private static Location getLocation(String location) {
        Location apolloLocation = new Location();
        apolloLocation.setApolloLocationCode(location);
        return apolloLocation;
    }

    private static XMLGregorianCalendar getScenarioDate() throws DatatypeConfigurationException {
        // return a fixed date so cached results work
        GregorianCalendar calendar = new GregorianCalendar();
        calendar.set(Calendar.YEAR, 2009);
        calendar.set(Calendar.MONTH, 7);
        calendar.set(Calendar.DAY_OF_MONTH, 3);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
//		calendar.set(field, value);

        XMLGregorianCalendar scenarioDate;
        scenarioDate = DatatypeFactory.newInstance().newXMLGregorianCalendar(calendar);
        return scenarioDate;

    }

    public static InfectiousDiseaseScenario getBaseInfectiousDiseaseScenario(String location) throws DatatypeConfigurationException {

        InfectiousDiseaseScenario scenario = new InfectiousDiseaseScenario();

        scenario.setScenarioLocation(getLocation(location));
        scenario.setScenarioDate(getScenarioDate());
        scenario.getInfections().add(getBaseInfection());

        Population population = new Population();
        population.setLocation(location);
        population.setTaxonId(INFECTIOUS_HOST_TAXON_ID);
        population.getInfectionAndImmunityCensuses().add(getBasePopulationInfectionAndImmunityCensus(location));

        scenario.getPopulations().add(population);

        return scenario;
    }

    private static PopulationInfectionAndImmunityCensus getBasePopulationInfectionAndImmunityCensus(String location) {

        PopulationInfectionAndImmunityCensus census = new PopulationInfectionAndImmunityCensus();
        PopulationInfectionAndImmunityCensusData censusData = new PopulationInfectionAndImmunityCensusData();

        PopulationInfectionAndImmunityCensusDataCell cell = new PopulationInfectionAndImmunityCensusDataCell();
        cell.setInfectionState(InfectionStateEnum.SUSCEPTIBLE);
        cell.setFractionInState(DEFAULT_SUSCEPTIBLE_FRACTION);
        censusData.getCensusDataCells().add(cell);

        cell = new PopulationInfectionAndImmunityCensusDataCell();
        cell.setInfectionState(InfectionStateEnum.LATENT);
        cell.setFractionInState(DEFAULT_EXPOSED_FRACTION);
        censusData.getCensusDataCells().add(cell);

        cell = new PopulationInfectionAndImmunityCensusDataCell();
        cell.setInfectionState(InfectionStateEnum.INFECTIOUS);
        cell.setFractionInState(DEFAULT_INFECTIOUS_FRACTION);
        censusData.getCensusDataCells().add(cell);

        cell = new PopulationInfectionAndImmunityCensusDataCell();
        cell.setInfectionState(InfectionStateEnum.RECOVERED);
        cell.setFractionInState(DEFAULT_RECOVERED_FRACTION);
        censusData.getCensusDataCells().add(cell);

        censusData.setLocation(getLocation(location));

        census.setCensusData(censusData);
        census.setPathogen(getApolloPathogenCode());
        census.setSimulatorTime(0);
        census.setLocation(getLocation(location));

        return census;
    }

    private static Infection getBaseInfection() {

        Infection infection = new Infection();

        infection.getInfectionAcquisitionsFromInfectedHosts().add(getBaseInfectionAquisition());
        infection.setPathogen(getApolloPathogenCode());
        infection.setHost(INFECTIOUS_HOST_TAXON_ID);
        infection.getInfectiousDiseases().add(getBaseInfectiousDisease());

        return infection;
    }

    private static InfectionAcquisitionFromInfectedHost getBaseInfectionAquisition() {

        InfectionAcquisitionFromInfectedHost acquisition = new InfectionAcquisitionFromInfectedHost();

        ReproductionNumber reproductionNumber = new ReproductionNumber();
        reproductionNumber.setExactValue(DEFAULT_R0);
        acquisition.getBasicReproductionNumbers().add(reproductionNumber);

        FixedDuration infectiousPeriodDuration = new FixedDuration();
        infectiousPeriodDuration.setUnitOfTime(UnitOfTimeEnum.DAY);
        infectiousPeriodDuration.setValue(DEFAULT_INFECTIOUS_PERIOD);
        acquisition.setInfectiousPeriodDuration(infectiousPeriodDuration);

        FixedDuration latentPeriodDuration = new FixedDuration();
        latentPeriodDuration.setUnitOfTime(UnitOfTimeEnum.DAY);
        latentPeriodDuration.setValue(DEFAULT_LATENT_PERIOD);
        acquisition.setLatentPeriodDuration(latentPeriodDuration);

        acquisition.setInfectedHost(INFECTIOUS_HOST_TAXON_ID);

        return acquisition;
    }

    private static InfectiousDisease getBaseInfectiousDisease() {

        InfectiousDisease disease = new InfectiousDisease();
        disease.setSpeciesWithDisease(INFECTIOUS_HOST_TAXON_ID);
        disease.setCausalPathogen(getApolloPathogenCode());
        disease.setDisease(DISEASE_ID);

        DiseaseOutcomeWithProbability outcomeWithProbability = new DiseaseOutcomeWithProbability();
        outcomeWithProbability.setDiseaseOutcome(DiseaseOutcomeEnum.ASYMPTOMATIC);
        ProbabilisticParameter probability = new ProbabilisticParameter();
        probability.setProbability(DEFAULT_ASYMPTOMATIC_PROBABILITY);
        outcomeWithProbability.setProbability(probability);

        disease.getDiseaseOutcomesWithProbabilities().add(outcomeWithProbability);

        return disease;
    }

}
