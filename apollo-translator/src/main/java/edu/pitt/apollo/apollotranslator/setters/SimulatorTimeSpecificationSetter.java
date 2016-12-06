package edu.pitt.apollo.apollotranslator.setters;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

import edu.pitt.apollo.apollotranslator.ApolloTranslationEngine;
import edu.pitt.apollo.apollotranslator.exception.ApolloSetterException;
import edu.pitt.apollo.apollotranslator.types.translator.SetterReturnObject;
import edu.pitt.apollo.types.v4_0_1.SimulatorTimeSpecification;
import edu.pitt.apollo.types.v4_0_1.UnitOfTimeEnum;

public class SimulatorTimeSpecificationSetter extends AbstractTypedSetter<SimulatorTimeSpecification> {

        private static final String SECTION = "SIMULATOR TIME SPECIFICATION";
	public static final String RUN_LENGTH = "runLength";
        public static final String UNIT_OF_TIME_FOR_SIMULATIOR_TIME_STEP = "unitOfTimeForSimulatorTimeStep";
        public static final String NUM_UNITS_OF_TIME_IN_ONE_TIME_STEP = "numberOfUnitsOfTimeInOneSimulatorTimeStep";

	public SimulatorTimeSpecificationSetter(
			ApolloTranslationEngine apolloTranslationEngine) {
		super("simulatorTimeSpecification", SECTION, apolloTranslationEngine);
	}

	private List<SetterReturnObject> setRunLength(BigInteger runLength) throws ApolloSetterException {
		return setValue(RUN_LENGTH, runLength.toString(), SECTION);
	}

        private List<SetterReturnObject> setUnitOfTimeForSimulatorTimeStep(UnitOfTimeEnum timeStepUnit) throws ApolloSetterException {
            return setValue(UNIT_OF_TIME_FOR_SIMULATIOR_TIME_STEP, timeStepUnit.value(), SECTION);
        }
        
        private List<SetterReturnObject> setNumberOfUnitsOfTimeInOneSimulatorTimeStep(double timeStepUnit) throws ApolloSetterException {
            return setValue(NUM_UNITS_OF_TIME_IN_ONE_TIME_STEP, Double.toString(timeStepUnit), SECTION);
        }
        
        private List<SetterReturnObject> setSimulatorTimeSpecification() throws ApolloSetterException {
            return setValue("", GENERIC_IS_NOT_NULL_LABEL, SECTION);
        }

    @Override
    public List<SetterReturnObject> set(SimulatorTimeSpecification simulatorTimeSpecification) throws ApolloSetterException {
        
        List<SetterReturnObject> sroList = new ArrayList<SetterReturnObject>();
        
        sroList.addAll(setSimulatorTimeSpecification());
        sroList.addAll(setRunLength(simulatorTimeSpecification.getRunLength()));
        sroList.addAll(setUnitOfTimeForSimulatorTimeStep(simulatorTimeSpecification.getUnitOfTimeForSimulatorTimeStep()));
        sroList.addAll(setNumberOfUnitsOfTimeInOneSimulatorTimeStep(simulatorTimeSpecification.getNumberOfUnitsOfTimeInOneSimulatorTimeStep()));
        
        return sroList;
    }
}
