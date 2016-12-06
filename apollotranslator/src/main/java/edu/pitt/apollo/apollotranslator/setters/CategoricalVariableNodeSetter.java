package edu.pitt.apollo.apollotranslator.setters;

import edu.pitt.apollo.apollotranslator.ApolloTranslationEngine;
import edu.pitt.apollo.apollotranslator.exception.ApolloSetterException;
import edu.pitt.apollo.apollotranslator.types.translator.SetterReturnObject;
import edu.pitt.apollo.types.v4_0_1.CategoricalVariableEnum;
import edu.pitt.apollo.types.v4_0_1.CategoricalVariableNode;
import edu.pitt.apollo.types.v4_0_1.CategoryValueNode;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * Author: Nick Millett
 * Email: nick.millett@gmail.com
 * Date: Mar 21, 2014
 * Time: 4:15:41 PM
 * Class: ConditioningVariableSetter
 * IDE: NetBeans 6.9.1
 */
public class CategoricalVariableNodeSetter extends AbstractTypedSetter<CategoricalVariableNode> {

    private static final String NAME = "name";
    private static final String CATEGORIES = "categories";

    public CategoricalVariableNodeSetter() {
    }

    public CategoricalVariableNodeSetter(ApolloTranslationEngine apolloTranslationEngine, String prefix, String section) {
        super(prefix, section, apolloTranslationEngine);
    }

    private List<SetterReturnObject> setName(CategoricalVariableEnum variable) throws ApolloSetterException {
        return setValue(NAME, variable.toString(), section);
    }

    private List<SetterReturnObject> setCategories(List<CategoryValueNode> categories) throws ApolloSetterException {
        ListSetter setter = new ListSetter(CategorySetter.class,
                CategoryValueNode.class, categories, apolloTranslationEngine, section, type + "." + CATEGORIES);

        return setter.set();
    }

    @Override
    public List<SetterReturnObject> set(CategoricalVariableNode t) throws ApolloSetterException {
        List<SetterReturnObject> list = new ArrayList<SetterReturnObject>();

        list.addAll(setName(t.getName()));
        list.addAll(setCategories(t.getCategories()));

        return list;
    }
}
