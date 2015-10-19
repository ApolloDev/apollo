package edu.pitt.apollo.examples.runsimulationmessages;

import edu.pitt.apollo.services_common.v3_0_2.ApolloSoftwareTypeEnum;
import edu.pitt.apollo.services_common.v3_0_2.Authentication;
import edu.pitt.apollo.services_common.v3_0_2.SoftwareIdentification;
import edu.pitt.apollo.simulator_service_types.v3_0_2.RunSimulationMessage;
import edu.pitt.apollo.types.v3_0_2.*;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 *
 * Author: Nick Millett
 * Email: nick.millett@gmail.com
 * Date: Oct 22, 2014
 * Time: 12:19:52 PM
 * Class: EbolaRunSimulationMessageBuilder
 */
public class EbolaRunSimulationMessageBuilder extends AbstractRunSimulationMessageBuilder {

	private static final double DEFAULT_LATENT_PERIOD = 2d;
	private static final double DEFAULT_INFECTIOUS_PERIOD = 6d;
	private static final double DEFAULT_ASYMPTOMTIC_INFECTION_FRACTION = 0.33;
	private static final double DEFAULT_R0 = 1.3;
	private static final String DEFAULT_RUN_LENGTH = "150";
	private static final String DEFAULT_INCITS = "SL";

	@Override
	protected SoftwareIdentification getSoftwareIdentification() {
		SoftwareIdentification softwareId = new SoftwareIdentification();
		softwareId.setSoftwareDeveloper("John");
		softwareId.setSoftwareName("Ebola SEIR");
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
		code.setCladeName("Zaire ebolavirus");
		code.setNcbiTaxonId("186538");
		infection.setPathogen(code);
		infection.setHost("9606");

		InfectiousDisease disease = new InfectiousDisease();

		disease.setDisease("Ebola");
		disease.setCausalPathogen(code);
		disease.setSpeciesWithDisease("9606");

		DiseaseOutcomeWithProbability dowp = new DiseaseOutcomeWithProbability();
		dowp.setDiseaseOutcome(DiseaseOutcomeEnum.ASYMPTOMATIC);
		ProbabilisticParameter asymtptomaticProb = new ProbabilisticParameter();
		asymtptomaticProb.setProbability(DEFAULT_ASYMPTOMTIC_INFECTION_FRACTION);
		dowp.setProbability(asymtptomaticProb);
		disease.getDiseaseOutcomesWithProbabilities().add(dowp);

		dowp = new DiseaseOutcomeWithProbability();
		dowp.setDiseaseOutcome(DiseaseOutcomeEnum.DEATH);
		ProbabilisticParameter deathProb = new ProbabilisticParameter();
		deathProb.setProbability(0.2);
		dowp.setProbability(deathProb);
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

		MeanWithConfidenceInterval r0 = new MeanWithConfidenceInterval();
		r0.setMean(DEFAULT_R0);
		r0.setConfidence(new BigInteger("95"));
		reproductionNumber.setUncertainValue(r0);

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
        population.setCount(new BigInteger("6000000"));

		PopulationInfectionAndImmunityCensus census = new PopulationInfectionAndImmunityCensus();

		census.setDescription("Sierra Leone");
		census.setReferenceDate(getDate());
		census.setLocation(getLocation());

		ApolloPathogenCode code = new ApolloPathogenCode();
		code.setNcbiTaxonId("186538");
		census.setPathogen(code);

		PopulationInfectionAndImmunityCensusData data = new PopulationInfectionAndImmunityCensusData();
		data.setLocation(getLocation());

		PopulationInfectionAndImmunityCensusDataCell susceptibleCell = new PopulationInfectionAndImmunityCensusDataCell();
		susceptibleCell.setInfectionState(InfectionStateEnum.SUSCEPTIBLE);
		susceptibleCell.setFractionInState(0.95);

		PopulationInfectionAndImmunityCensusDataCell exposedCell = new PopulationInfectionAndImmunityCensusDataCell();
		exposedCell.setInfectionState(InfectionStateEnum.LATENT);
		exposedCell.setFractionInState(0.0);

		PopulationInfectionAndImmunityCensusDataCell infectiousCell = new PopulationInfectionAndImmunityCensusDataCell();
		infectiousCell.setInfectionState(InfectionStateEnum.INFECTIOUS);
		infectiousCell.setFractionInState(0.05);

		PopulationInfectionAndImmunityCensusDataCell recoveredCell = new PopulationInfectionAndImmunityCensusDataCell();
		recoveredCell.setInfectionState(InfectionStateEnum.RECOVERED);
		recoveredCell.setFractionInState(0.0);

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

	@Override
	protected Authentication getAuthentication() {
		Authentication auth = new Authentication();
		auth.setRequesterId("apollo_user");
		auth.setRequesterPassword("password");
		return auth;
	}

	@Override
	public RunSimulationMessage getRunSimulationMessage(Set<ControlMeasureTypeEnum> controlMeasureTypes) {
		RunSimulationMessage simulatorConfiguration = getBaseRunSimulationMessage();

		return simulatorConfiguration;
	}

}
