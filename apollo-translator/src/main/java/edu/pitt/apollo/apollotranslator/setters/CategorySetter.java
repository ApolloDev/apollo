package edu.pitt.apollo.apollotranslator.setters;

import edu.pitt.apollo.apollotranslator.ApolloTranslationEngine;
import edu.pitt.apollo.apollotranslator.exception.ApolloSetterException;
import edu.pitt.apollo.apollotranslator.types.translator.SetterReturnObject;
import edu.pitt.apollo.types.v4_0_2.CategoricalVariableNode;
import edu.pitt.apollo.types.v4_0_2.CategoryDefinition;
import edu.pitt.apollo.types.v4_0_2.CategoryValueNode;
import edu.pitt.apollo.types.v4_0_2.UnconditionalProbabilityDistribution;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * Author: Nick Millett
 * Email: nick.millett@gmail.com
 * Date: Mar 21, 2014
 * Time: 4:20:27 PM
 * Class: CategorySetter
 * IDE: NetBeans 6.9.1
 */
public class CategorySetter extends AbstractTypedSetter<CategoryValueNode> {

    private static final String CATEGORY_DEFINITION = "categoryDefinition";
    private static final String UNCONDITIONAL_PROB_DIST = "unconditionalProbabilityDistribution";
    private static final String CONDITIONING_VARIABLE = "nextCategoricalVariable";
	private static final String VALUE = "value";

    public CategorySetter() {
    }

    public CategorySetter(ApolloTranslationEngine apolloTranslationEngine, String prefix, String section) {
        super(prefix, section, apolloTranslationEngine);
    }

    private List<SetterReturnObject> setCategoryDefinition(CategoryDefinition definition) throws ApolloSetterException {
        CategoryDefinitionSetter setter = new CategoryDefinitionSetter(apolloTranslationEngine, type + "." + CATEGORY_DEFINITION, section);
        return setter.set(definition);
    }

    private List<SetterReturnObject> setUnconditionalProbabilityDistribution(UnconditionalProbabilityDistribution dist) throws ApolloSetterException {
        UnconditionalProbabilityDistributionSetter setter = new UnconditionalProbabilityDistributionSetter(apolloTranslationEngine, type + "." + UNCONDITIONAL_PROB_DIST, section);
        return setter.set(dist);
    }

    private List<SetterReturnObject> setConditioningVariable(CategoricalVariableNode variable) throws ApolloSetterException {
        CategoricalVariableNodeSetter setter = new CategoricalVariableNodeSetter(apolloTranslationEngine, type + "." + CONDITIONING_VARIABLE, section);
        return setter.set(variable);
    }
	
	private List<SetterReturnObject> setValue(BigInteger value) throws ApolloSetterException {
		return setValue(VALUE, value.toString(), section);
	}

    @Override
    public List<SetterReturnObject> set(CategoryValueNode t) throws ApolloSetterException {
        List<SetterReturnObject> list = new ArrayList<SetterReturnObject>();
        list.addAll(setCategoryDefinition(t.getCategoryDefinition()));
        if (t.getNextCategoricalVariable()!= null) {
            list.addAll(setConditioningVariable(t.getNextCategoricalVariable()));
        } else if (t.getUnconditionalProbabilityDistribution() != null) {
            list.addAll(setUnconditionalProbabilityDistribution(t.getUnconditionalProbabilityDistribution()));
        } else if (t.getValue() != null) {
			list.addAll(setValue(t.getValue()));
		}

        return list;
    }
}
