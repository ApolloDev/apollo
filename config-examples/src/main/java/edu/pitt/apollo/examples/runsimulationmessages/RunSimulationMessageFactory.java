package edu.pitt.apollo.examples.runsimulationmessages;

import edu.pitt.apollo.examples.exception.ConfigurationExampleException;
import edu.pitt.apollo.simulator_service_types.v3_0_2.RunSimulationMessage;

import java.util.Set;

/**
 *
 * Author: Nick Millett
 * Email: nick.millett@gmail.com
 * Date: Sep 3, 2014
 * Time: 3:10:45 PM
 * Class: RunSimulationMessageFactory
 */
public class RunSimulationMessageFactory {

	public enum RunSimulationMessageDiseaseType {

		INFLUENZA, ANTHRAX, DENGUE, EBOLA, MALARIA
	}

	public static RunSimulationMessage getRunSimulationMessage(RunSimulationMessageDiseaseType type, Set<AbstractRunSimulationMessageBuilder.ControlMeasureTypeEnum> controlMeasureTypes) throws ConfigurationExampleException {

		AbstractRunSimulationMessageBuilder builder;
		switch (type) {

			case INFLUENZA:
				builder = new InfluenzaRunSimulationMessageBuilder();
				break;
			case ANTHRAX:
				builder = new AnthraxRunSimulationMessageBuilder();
				break;
			case DENGUE:
				builder = new DengueRunSimulationMessageBuilder();
				break;
			case EBOLA:
				builder = new EbolaRunSimulationMessageBuilder();
				break;
			case MALARIA:
				builder = new MalariaRunSimulationMessageBuilder();
				break;
			default:
				throw new ConfigurationExampleException("Unrecognized RunSimulationMEssageDiseaseType of \"" + type + "\" in RunSimulationMessageFactory");
		}

		return builder.getRunSimulationMessage(controlMeasureTypes);
	}
}
