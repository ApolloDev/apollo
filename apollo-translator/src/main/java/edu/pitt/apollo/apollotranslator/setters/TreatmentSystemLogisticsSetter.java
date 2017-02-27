package edu.pitt.apollo.apollotranslator.setters;

import edu.pitt.apollo.apollotranslator.ApolloTranslationEngine;
import edu.pitt.apollo.apollotranslator.exception.ApolloSetterException;
import edu.pitt.apollo.apollotranslator.types.translator.SetterReturnObject;
import edu.pitt.apollo.types.v4_0_1.Location;
import edu.pitt.apollo.types.v4_0_1.TreatmentSystemLogistics;
import edu.pitt.apollo.types.v4_0_1.UnitOfMeasureEnum;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by nem41 on 1/12/17.
 */
public class TreatmentSystemLogisticsSetter extends AbstractTypedSetter<TreatmentSystemLogistics> {

    private static final String DESCRIPTION = "description";
    private static final String LOCATION = "location";
    private static final String SUPPLY_SCHEDULE_PER_DAY = "supplySchedulePerDay";
    private static final String SUPPLY_SCHEDULE_UNITS = "supplyScheduleUnits";
    private static final String ADMIN_CAPACITY_PER_DAY = "administrationCapacityPerDay";
    private static final String ADMIN_CAPACITY_UNITS = "administrationCapacityUnits";

    public TreatmentSystemLogisticsSetter() {
    }

    public TreatmentSystemLogisticsSetter(ApolloTranslationEngine apolloTranslationEngine, String prefix, String section) {
        super(prefix, section, apolloTranslationEngine);
    }

    private List<SetterReturnObject> setDescription(String description) throws ApolloSetterException {
        return setValue(DESCRIPTION, description, section);
    }

    private List<SetterReturnObject> setLocation(Location location) throws ApolloSetterException {
        LocationSetter setter = new LocationSetter(apolloTranslationEngine, type + "." + LOCATION, section);
        return setter.set(location);
    }

    public List<SetterReturnObject> setSupplySchedulePerDay(List<BigInteger> supplySchedule) throws ApolloSetterException {
        return setValue(SUPPLY_SCHEDULE_PER_DAY, Arrays.toString(supplySchedule.toArray()), section);
    }

    public List<SetterReturnObject> setSupplyScheduleUnits(UnitOfMeasureEnum unit) throws ApolloSetterException {
        return setValue(SUPPLY_SCHEDULE_UNITS, unit.toString(), section);
    }

    public List<SetterReturnObject> setAdministrationCapacityPerDay(List<BigInteger> adminSchedule) throws ApolloSetterException {
        return setValue(ADMIN_CAPACITY_PER_DAY, Arrays.toString(adminSchedule.toArray()), section);
    }

    public List<SetterReturnObject> setAdministrationCapacityUnits(UnitOfMeasureEnum unit) throws ApolloSetterException {
        return setValue(ADMIN_CAPACITY_UNITS, unit.toString(), section);
    }

    @Override
    public List<SetterReturnObject> set(TreatmentSystemLogistics t) throws ApolloSetterException {

        List<SetterReturnObject> list = new ArrayList<SetterReturnObject>();
        if (t.getDescription() != null) {
            list.addAll(setDescription(t.getDescription()));
        }
        list.addAll(setLocation(t.getLocation()));
        list.addAll(setSupplySchedulePerDay(t.getSupplySchedulePerDay()));
        if (t.getSupplyScheduleUnits() != null) {
            list.addAll(setSupplyScheduleUnits(t.getSupplyScheduleUnits()));
        }
        list.addAll(setAdministrationCapacityPerDay(t.getAdministrationCapacityPerDay()));
        if (t.getAdministrationCapacityUnits() != null) {
            list.addAll(setAdministrationCapacityUnits(t.getAdministrationCapacityUnits()));
        }
        return list;
    }
}
