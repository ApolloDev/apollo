package edu.pitt.apollo.examples.runsimulationmessages;

import edu.pitt.apollo.services_common.v4_0_2.Authentication;
import edu.pitt.apollo.simulator_service_types.v4_0_2.RunSimulationMessage;
import edu.pitt.apollo.types.v4_0_2.*;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Set;

/**
 *
 * Author: Nick Millett
 Email: nick.millett@gmail.com
 Date: Sep 3, 2014
 Time: 10:28:35 AM
 Class: AbstractRunSimulationMessageBuilder
 */
public abstract class AbstractRunSimulationMessageBuilder {

	public enum ControlMeasureTypeEnum {

		NO_AV_CM, AV_CM, NO_VACC_CM, VACC_CM, ALL_SC_CM_FIXED, ALL_SC_CM_REACTIVE,
		INDIVIDUAL_SC_CM_FIXED, INDIVIDUAL_SC_CM_REACTIVE, NO_SC_CM, NO_VI_CM, VI_CM,
		NO_VQ_CM, VQ_CM, NO_LSL_CM, LSL_CM, NO_DRUG_CM, DRUG_CM, WOLBACHIA,
		INDOOR_RESIDUAL_SPRAYING, LARVICIDE, CONTAINER_REDUCTION, INSECTICIDE_TREATED_NET;
	}

	public enum DefReproductionNumber {

		R0, GES, BETA, TRANSMISSION_PROBABILITY;
	}

	protected abstract SoftwareIdentification getSoftwareIdentification();

	protected abstract SimulatorTimeSpecification getSimulatorTimeSpeficiation();

	protected abstract List<Infection> getInfections();

	protected abstract List<Population> getPopulations();

	protected abstract Location getLocation();

	protected abstract Authentication getAuthentication();

	public abstract RunSimulationMessage getRunSimulationMessage(Set<ControlMeasureTypeEnum> controlMeasureTypes);

	protected RunSimulationMessage getBaseRunSimulationMessage() {

		RunSimulationMessage message = new RunSimulationMessage();

		message.setSoftwareIdentification(getSoftwareIdentification());
		message.setSimulatorTimeSpecification(getSimulatorTimeSpeficiation());
		message.setInfectiousDiseaseScenario(getBaseInfectiousDiseaseScenario());

		return message;
	}

	protected InfectiousDiseaseScenario getBaseInfectiousDiseaseScenario() {

		InfectiousDiseaseScenario scenario = new InfectiousDiseaseScenario();
		scenario.getInfections().addAll(getInfections());
		scenario.getPopulations().addAll(getPopulations());
		scenario.setScenarioDate(getDate());
        scenario.setScenarioLocation(getLocation());

		return scenario;
	}

	protected XMLGregorianCalendar getDate() {
		XMLGregorianCalendar date;
		try {
			GregorianCalendar c = new GregorianCalendar();
			c.set(2009, 8, 8);
			date = DatatypeFactory.newInstance().newXMLGregorianCalendar(c);
		} catch (DatatypeConfigurationException ex) {
			throw new RuntimeException("Datatype configuration exception in SimulatorConfigurationFactory");
		}

		return date;
	}

	protected void setBaseInfectiousDiseaseControlMeasure(InfectiousDiseaseControlMeasure strategy) {

		FixedDuration responseDelay = new FixedDuration();
		responseDelay.setValue(1d);
		responseDelay.setUnitOfTime(UnitOfTimeEnum.DAY);
		strategy.setControlMeasureResponseDelay(responseDelay);

		FixedDuration standDownDelay = new FixedDuration();
		standDownDelay.setValue(10d);
		standDownDelay.setUnitOfTime(UnitOfTimeEnum.DAY);
		strategy.setControlMeasureStandDownDelay(standDownDelay);

		FixedDuration startDuration = new FixedDuration();
		startDuration.setValue(0d);
		startDuration.setUnitOfTime(UnitOfTimeEnum.DAY);

		TemporalTriggerDefinition time = new TemporalTriggerDefinition();
		time.setTimeScale(TimeScaleEnum.SIMULATOR_TIME_SCALE);
		time.setTimeSinceTimeScaleZero(startDuration);
		strategy.getControlMeasureStartTime().add(time);

		strategy.setDescription("base infectious disease control strategy");

		strategy.setLocation(getLocation());

	}
}
