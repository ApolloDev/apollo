package edu.pitt.apollo.apollotranslator.setters;

import edu.pitt.apollo.apollotranslator.ApolloTranslationEngine;
import edu.pitt.apollo.apollotranslator.exception.ApolloSetterException;
import edu.pitt.apollo.apollotranslator.types.translator.SetterReturnObject;
import edu.pitt.apollo.types.v4_0.ApolloPathogenCode;
import edu.pitt.apollo.types.v4_0.Location;
import edu.pitt.apollo.types.v4_0.PopulationInfectionAndImmunityCensus;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import javax.xml.datatype.XMLGregorianCalendar;

public class PopulationInfectionAndImmunityCensusSetter extends AbstractTypedSetter<PopulationInfectionAndImmunityCensus> {

//    public static final String SECTION = "POPULATION INFECTION AND IMMUNITY CENSUSES";
	public static final String PATHOGEN_FIELD = "pathogen";
	public static final String CENSUS_DATA_FIELD = "censusData";
	public static final String SIMULATOR_TIME = "simulatorTime";
	public static final String LOCATION = "location";
	
	public PopulationInfectionAndImmunityCensusSetter() {
	}

	public PopulationInfectionAndImmunityCensusSetter(ApolloTranslationEngine apolloTranslationEngine, String type, String section) {
		super(type, section, apolloTranslationEngine);
	}

	private List<SetterReturnObject> setPathogen(ApolloPathogenCode id) throws ApolloSetterException {
		ApolloPathogenCodeSetter setter = new ApolloPathogenCodeSetter(apolloTranslationEngine, type + "." + PATHOGEN_FIELD, section);
		return setter.set(id);
	}

	public List<SetterReturnObject> setPopulationInfectionAndImmunityCensus() throws ApolloSetterException {
		return setValue("", AbstractSetter.GENERIC_IS_NOT_NULL_LABEL, section);
	}

	public List<SetterReturnObject> setSimulatorTime(int time) throws ApolloSetterException {
		return setValue(SIMULATOR_TIME, Integer.toString(time), section);
	}
	
	private List<SetterReturnObject> setLocation(Location location) throws ApolloSetterException {
		LocationSetter setter = new LocationSetter(apolloTranslationEngine, type + "." + LOCATION, section);
		return setter.set(location);
	}

	@Override
	public List<SetterReturnObject> set(PopulationInfectionAndImmunityCensus t) throws ApolloSetterException {
		List<SetterReturnObject> list = new ArrayList<SetterReturnObject>();
//        list.addAll(setPopulationInfectionAndImmunityCensus());
		list.addAll(setPathogen(t.getPathogen()));
		list.addAll(setSimulatorTime(t.getSimulatorTime()));
		list.addAll(setLocation(t.getLocation()));

//        TimeDefinitionSetter tds = new TimeDefinitionSetter(type + "."
//                + DATE_FIELD, SECTION, apolloTranslationEngine);
		PopulationInfectionAndImmunityCensusDataSetter pdcrs = new PopulationInfectionAndImmunityCensusDataSetter(
				type + "." + CENSUS_DATA_FIELD, section, apolloTranslationEngine);
		list.addAll(pdcrs.set(t.getCensusData()));

		return list;
	}
}
