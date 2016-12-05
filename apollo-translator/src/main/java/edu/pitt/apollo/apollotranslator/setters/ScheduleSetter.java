package edu.pitt.apollo.apollotranslator.setters;

import edu.pitt.apollo.apollotranslator.ApolloTranslationEngine;
import edu.pitt.apollo.apollotranslator.exception.ApolloSetterException;
import edu.pitt.apollo.apollotranslator.types.translator.SetterReturnObject;
import edu.pitt.apollo.types.v4_0.Schedule;
import edu.pitt.apollo.types.v4_0.ScheduleElement;
import edu.pitt.apollo.types.v4_0.UnitOfMeasureEnum;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by nem41 on 8/3/15.
 */
public class ScheduleSetter extends AbstractTypedSetter<Schedule> {

    private static final String UNIT_OF_MEASURE = "unitOfMeasure";
    private static final String SCHEDULE_ELEMENTS = "scheduleElements";

    public ScheduleSetter() {
    }

    public ScheduleSetter(ApolloTranslationEngine apolloTranslationEngine, String prefix, String section) {
        super(prefix, section, apolloTranslationEngine);
    }

    public List<SetterReturnObject> setUnitOfMeasure(UnitOfMeasureEnum unitOfMeasureEnum) throws ApolloSetterException {
        return setValue(UNIT_OF_MEASURE, unitOfMeasureEnum.toString(), section);
    }

    public List<SetterReturnObject> setScheduleElements(List<ScheduleElement> scheduleElements) throws ApolloSetterException {
        List<SetterReturnObject> sroList;
        if (scheduleElements != null && scheduleElements.size() > 0) {

            sroList = setValue(SCHEDULE_ELEMENTS, "(list values described below)", section);
            ListSetter setter = new ListSetter(ScheduleElementSetter.class, ScheduleElement.class,
                    scheduleElements, apolloTranslationEngine, section, type + "." + SCHEDULE_ELEMENTS);

            List<SetterReturnObject> result = setter.set();

            sroList.get(0).setSubApolloParameters(result);
        } else {
            sroList = setValue(SCHEDULE_ELEMENTS, PARAM_IS_NOT_SET_LABEL, section);
        }

        return sroList;
    }

    @Override
    public List<SetterReturnObject> set(Schedule schedule) throws ApolloSetterException {
        List<SetterReturnObject> list = new ArrayList<>();
        list.addAll(setUnitOfMeasure(schedule.getUnitOfMeasure()));
        list.addAll(setScheduleElements(schedule.getScheduleElements()));
        return list;
    }
}
