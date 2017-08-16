package edu.pitt.apollo.apollotranslator.setters;

import edu.pitt.apollo.apollotranslator.ApolloTranslationEngine;
import edu.pitt.apollo.apollotranslator.exception.ApolloSetterException;
import edu.pitt.apollo.apollotranslator.types.translator.SetterReturnObject;
import edu.pitt.apollo.types.v4_0_2.LogisticalSystemNode;
import edu.pitt.apollo.types.v4_0_2.Schedule;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by nem41 on 8/3/15.
 */
public class LogisticalSystemNodeSetter extends AbstractTypedSetter<LogisticalSystemNode> {

    private static final String DESCRIPTION = "description";
    private static final String CAPACITY_SCHEDULE = "capacitySchedule";
    private static final String OUTPUT_SCHEDULE = "outputSchedule";
    private static final String CHILDREN = "children";

    public LogisticalSystemNodeSetter() {
    }

    public LogisticalSystemNodeSetter(ApolloTranslationEngine apolloTranslationEngine, String prefix, String section) {
        super(prefix, section, apolloTranslationEngine);
    }

    public List<SetterReturnObject> setDescription(String description) throws ApolloSetterException {
        return setValue(DESCRIPTION, description, section);
    }

    public List<SetterReturnObject> setSchedule(String name, Schedule schedule) throws ApolloSetterException {
        ScheduleSetter setter = new ScheduleSetter(apolloTranslationEngine, type + "." + name, section);
        return setter.set(schedule);
    }

    public List<SetterReturnObject> setChildren(List<LogisticalSystemNode> children) throws ApolloSetterException {
        ListSetter listSetter = new ListSetter(LogisticalSystemNodeSetter.class,
                LogisticalSystemNode.class, children, apolloTranslationEngine, section, type + "." + CHILDREN);
        return listSetter.set();
    }

    @Override
    public List<SetterReturnObject> set(LogisticalSystemNode logisticalSystemNode) throws ApolloSetterException {
        List<SetterReturnObject> list = new ArrayList<>();
        if (logisticalSystemNode.getDescription() != null) {
            list.addAll(setDescription(logisticalSystemNode.getDescription()));
        }
        if (logisticalSystemNode.getCapacitySchedule() != null) {
            list.addAll(setSchedule(CAPACITY_SCHEDULE, logisticalSystemNode.getCapacitySchedule()));
        }
        if (logisticalSystemNode.getOutputSchedule() != null) {
            list.addAll(setSchedule(OUTPUT_SCHEDULE, logisticalSystemNode.getOutputSchedule()));
        }
        if (logisticalSystemNode.getChildren() != null && logisticalSystemNode.getChildren().size() > 0) {
            list.addAll(setChildren(logisticalSystemNode.getChildren()));
        }

        return list;
    }
}
