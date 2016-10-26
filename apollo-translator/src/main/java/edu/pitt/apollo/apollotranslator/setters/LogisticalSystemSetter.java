package edu.pitt.apollo.apollotranslator.setters;

import edu.pitt.apollo.apollotranslator.ApolloTranslationEngine;
import edu.pitt.apollo.apollotranslator.exception.ApolloSetterException;
import edu.pitt.apollo.apollotranslator.types.translator.SetterReturnObject;
import edu.pitt.apollo.types.v4_0.LogisticalSystem;
import edu.pitt.apollo.types.v4_0.LogisticalSystemNode;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by nem41 on 8/3/15.
 */
public class LogisticalSystemSetter extends AbstractTypedSetter<LogisticalSystem> {

    private static final String PRODUCT = "product";
    private static final String LOGISTICAL_SYSTEM_NODES = "logisticalSystemNodes";

    public LogisticalSystemSetter() {
    }

    public LogisticalSystemSetter(ApolloTranslationEngine apolloTranslationEngine, String type, String section) {
        super(type, section, apolloTranslationEngine);
    }

    public List<SetterReturnObject> setProduct(String product) throws ApolloSetterException {
        return setValue(PRODUCT, product, section);
    }

    public List<SetterReturnObject> setLogisticalSystemNodes(List<LogisticalSystemNode> logisticalSystemNodes) throws ApolloSetterException {
        List<SetterReturnObject> sroList;
        if (logisticalSystemNodes != null && logisticalSystemNodes.size() > 0) {

            sroList = setValue(LOGISTICAL_SYSTEM_NODES, "(list values described below)", section);
            ListSetter setter = new ListSetter(LogisticalSystemNodeSetter.class, LogisticalSystemNode.class,
                    logisticalSystemNodes, apolloTranslationEngine, section, type + "." + LOGISTICAL_SYSTEM_NODES);

            List<SetterReturnObject> result = setter.set();

            sroList.get(0).setSubApolloParameters(result);
        } else {
            sroList = setValue(LOGISTICAL_SYSTEM_NODES, PARAM_IS_NOT_SET_LABEL, section);
        }

        return sroList;
    }

    @Override
    public List<SetterReturnObject> set(LogisticalSystem logisticalSystem) throws ApolloSetterException {
        List<SetterReturnObject> list = new ArrayList<>();
        list.addAll(setProduct(logisticalSystem.getProduct()));
        list.addAll(setLogisticalSystemNodes(logisticalSystem.getLogisticalSystemNodes()));
        return list;
    }
}
