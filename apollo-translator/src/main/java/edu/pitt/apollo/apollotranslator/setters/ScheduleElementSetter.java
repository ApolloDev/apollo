package edu.pitt.apollo.apollotranslator.setters;

import edu.pitt.apollo.apollotranslator.ApolloTranslationEngine;
import edu.pitt.apollo.apollotranslator.exception.ApolloSetterException;
import edu.pitt.apollo.apollotranslator.types.translator.SetterReturnObject;
import edu.pitt.apollo.types.v4_0_2.ScheduleElement;

import javax.xml.datatype.XMLGregorianCalendar;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by nem41 on 8/3/15.
 */
public class ScheduleElementSetter extends AbstractTypedSetter<ScheduleElement> {

    private static final String QUANTITY = "quantity";
    private static final String DATE_TIME = "dateTime";

    public ScheduleElementSetter() {

    }

    public ScheduleElementSetter(ApolloTranslationEngine apolloTranslationEngine, String prefix, String section) {
        super(prefix, section, apolloTranslationEngine);
    }

    public List<SetterReturnObject> setQuantity(BigInteger quantity) throws ApolloSetterException {
        return setValue(QUANTITY, quantity.toString(), section);
    }

    public List<SetterReturnObject> setDateTime(XMLGregorianCalendar dateTime) throws ApolloSetterException {
        return setValue(DATE_TIME, dateTime.toString(), section);
    }

    @Override
    public List<SetterReturnObject> set(ScheduleElement scheduleElement) throws ApolloSetterException {
        List<SetterReturnObject> list = new ArrayList<>();
        list.addAll(setQuantity(scheduleElement.getQuantity()));
        list.addAll(setDateTime(scheduleElement.getDateTime()));
        return list;
    }
}
