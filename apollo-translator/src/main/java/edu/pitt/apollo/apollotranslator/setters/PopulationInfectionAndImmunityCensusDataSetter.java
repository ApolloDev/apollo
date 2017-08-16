package edu.pitt.apollo.apollotranslator.setters;

import edu.pitt.apollo.apollotranslator.ApolloTranslationEngine;
import edu.pitt.apollo.apollotranslator.exception.ApolloSetterException;
import edu.pitt.apollo.apollotranslator.types.translator.SetterReturnObject;
import edu.pitt.apollo.types.v4_0_2.PopulationInfectionAndImmunityCensusData;
import edu.pitt.apollo.types.v4_0_2.PopulationInfectionAndImmunityCensusDataCell;

import javax.xml.datatype.XMLGregorianCalendar;
import java.util.ArrayList;
import java.util.List;

public class PopulationInfectionAndImmunityCensusDataSetter extends AbstractTypedSetter<PopulationInfectionAndImmunityCensusData> {

//    public static final String DESCRIPTION_FIELD = "description";
    public static final String DATE_FIELD = "date";
    public static final String LOCATION_FIELD = "location";
    public static final String CENSUS_DATA_CELLS_FIELD = "censusDataCells";
    public static final String EXCEPTION_SUBPOPULATIONS_FIELD = "exceptionSubPopulations";

    public PopulationInfectionAndImmunityCensusDataSetter(String type, String section,
            ApolloTranslationEngine apolloTranslationEngine) {
        super(type, section, apolloTranslationEngine);
    }

    private List<SetterReturnObject> setDate(XMLGregorianCalendar value) throws ApolloSetterException {
        return setValue(DATE_FIELD, value.toString(), section);
    }

    private List<SetterReturnObject> setPopulationInfectionAndImmunityCensusDataCells(List<PopulationInfectionAndImmunityCensusDataCell> dataCells) throws ApolloSetterException {

        List<SetterReturnObject> sroList;
        if (dataCells != null && dataCells.size() > 0) {

            sroList = setValue(CENSUS_DATA_CELLS_FIELD, "(list values described below)", section);
            ListSetter setter = new ListSetter(PopulationInfectionAndImmunityCensusDataCellSetter.class, PopulationInfectionAndImmunityCensusDataCell.class,
                    dataCells, apolloTranslationEngine, section, type + "." + CENSUS_DATA_CELLS_FIELD);

//            VaccinationEfficacySetter vaccinationEfficacySetter = new VaccinationEfficacySetter(apolloTranslationEngine, type
//                    + "." + VACCINATION_EFFICACIES, section);

            List<SetterReturnObject> result = setter.set();

            sroList.get(0).setSubApolloParameters(result);
        } else {
            sroList = setValue(CENSUS_DATA_CELLS_FIELD, PARAM_IS_NOT_SET_LABEL, section);
        }

        return sroList;
    }

    public List<SetterReturnObject> setPopulationInfectionAndImmunityCensusData() throws ApolloSetterException {
        return setValue("", AbstractSetter.GENERIC_IS_NOT_NULL_LABEL, section);
    }

    @Override
    public List<SetterReturnObject> set(PopulationInfectionAndImmunityCensusData t) throws ApolloSetterException {

        List<SetterReturnObject> list = new ArrayList<SetterReturnObject>();
        list.addAll(setPopulationInfectionAndImmunityCensusData());

        if (t.getDate() != null) {
            list.addAll(setDate(t.getDate()));
        }

        LocationSetter lds = new LocationSetter(apolloTranslationEngine, type + "."
                + LOCATION_FIELD, section);
        list.addAll(lds.set(t.getLocation()));

        list.addAll(setPopulationInfectionAndImmunityCensusDataCells(t.getCensusDataCells()));

        list.addAll(setValue(EXCEPTION_SUBPOPULATIONS_FIELD, "(none specified)", section));

        return list;

    }
}
