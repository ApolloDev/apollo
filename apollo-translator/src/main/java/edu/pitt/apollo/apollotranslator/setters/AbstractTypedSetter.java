package edu.pitt.apollo.apollotranslator.setters;

import java.util.ArrayList;
import java.util.List;

import edu.pitt.apollo.apollotranslator.ApolloTranslationEngine;
import edu.pitt.apollo.apollotranslator.exception.ApolloSetterException;
import edu.pitt.apollo.apollotranslator.types.translator.SetterReturnObject;

public abstract class AbstractTypedSetter<T> extends AbstractSetter {

    String type, section;
    List<SetterReturnObject> results = new ArrayList<SetterReturnObject>();

    public AbstractTypedSetter() {
        
    }
    
    public AbstractTypedSetter(String type, String section,
            ApolloTranslationEngine apolloTranslationEngine) {
        super(apolloTranslationEngine);
        this.type = type;
        this.section = section;
    }

    public String getSection() {
        return section;
    }

    public void setSection(String section) {
        this.section = section;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    protected List<SetterReturnObject> setValue(String fieldName, String fieldValue, String section) throws ApolloSetterException {
        String newType;
        if (fieldName.isEmpty()) {
            newType = type;
        } else {
            newType = type + "." + fieldName;
        }
        
        return super.setValue(newType, fieldValue, section);
    }

    public abstract List<SetterReturnObject> set(T t) throws ApolloSetterException;

//    public List<SetterReturnObject> set(List<T> tList) throws ApolloSetterException {
//
//        // make a new list here to avoid conflict when the "set" method is called
//        List<SetterReturnObject> resultList = new ArrayList<SetterReturnObject>();
//        if (tList != null && tList.size() > 0) {
//            String originalType = type;
//            for (int i = 0; i < tList.size(); i++) {
//                T t = tList.get(i);
//
//                // we need to append the index of the list to the type
//                type = originalType + "[" + i + "]";
//                resultList.addAll(set(t));
//            }
//
//            type = originalType;
//        } else {
//            resultList.addAll(setValue("", PARAM_IS_NOT_SET_LABEL, section));
//        }
//
//        return resultList;
//    }
}
