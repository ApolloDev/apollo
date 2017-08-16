package edu.pitt.apollo.apollotranslator.setters;

import edu.pitt.apollo.apollotranslator.ApolloTranslationEngine;
import edu.pitt.apollo.apollotranslator.exception.ApolloSetterException;
import edu.pitt.apollo.apollotranslator.types.translator.SetterReturnObject;
import edu.pitt.apollo.types.v4_0_2.AgeRangeCategoryDefinition;
import edu.pitt.apollo.types.v4_0_2.CategoryDefinition;
import edu.pitt.apollo.types.v4_0_2.DiseaseOutcomeCategoryDefinition;
import edu.pitt.apollo.types.v4_0_2.GenderCategoryDefinition;

import java.util.List;

/**
 *
 * Author: Nick Millett
 * Email: nick.millett@gmail.com
 * Date: Mar 21, 2014
 * Time: 4:24:14 PM
 * Class: CategoryDefinitionSetter
 * IDE: NetBeans 6.9.1
 */
public class CategoryDefinitionSetter extends AbstractTypedSetter<CategoryDefinition> {

    public CategoryDefinitionSetter() {
    }

    public CategoryDefinitionSetter(ApolloTranslationEngine apolloTranslationEngine, String prefix, String section) {
        super(prefix, section, apolloTranslationEngine);
    }

    @Override
    public List<SetterReturnObject> set(CategoryDefinition t) throws ApolloSetterException {
        AbstractTypedSetter setter;
        if (t instanceof AgeRangeCategoryDefinition) {
            setter = new AgeRangeCategoryDefinitionSetter(apolloTranslationEngine, type, section);
        } else if (t instanceof GenderCategoryDefinition) {
            setter = new GenderCategorySetter(apolloTranslationEngine, type, section);
        } else if (t instanceof DiseaseOutcomeCategoryDefinition) {
			setter = new DiseaseOutcomeCategoryDefinitionSetter(apolloTranslationEngine, type, section);
		}else {
            throw new ApolloSetterException("Unrecognized category definition type in CategoryDefinitionSetter");
        }

        return setter.set(t);
    }
}
