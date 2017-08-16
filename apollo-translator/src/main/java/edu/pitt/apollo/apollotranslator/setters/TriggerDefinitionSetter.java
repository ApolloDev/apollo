package edu.pitt.apollo.apollotranslator.setters;

import edu.pitt.apollo.apollotranslator.ApolloTranslationEngine;
import edu.pitt.apollo.apollotranslator.exception.ApolloSetterException;
import edu.pitt.apollo.apollotranslator.types.translator.SetterReturnObject;
import edu.pitt.apollo.types.v4_0_2.DiseaseSurveillanceTriggerDefinition;
import edu.pitt.apollo.types.v4_0_2.TemporalTriggerDefinition;
import edu.pitt.apollo.types.v4_0_2.TreatmentSurveillanceTriggerDefinition;
import edu.pitt.apollo.types.v4_0_2.TriggerDefinition;

import java.util.List;

/**
 *
 * Author: Nick Millett
 * Email: nick.millett@gmail.com
 * Date: Apr 3, 2014
 * Time: 12:59:03 PM
 * Class: TriggerDefinitionSetter
 * IDE: NetBeans 6.9.1
 */
public class TriggerDefinitionSetter extends AbstractTypedSetter<TriggerDefinition> {

    public TriggerDefinitionSetter() {
    }

    public TriggerDefinitionSetter(ApolloTranslationEngine apolloTranslationEngine, String prefix, String section) {
        super(prefix, section, apolloTranslationEngine);
    }

    @Override
    public List<SetterReturnObject> set(TriggerDefinition t) throws ApolloSetterException {

        if (t instanceof DiseaseSurveillanceTriggerDefinition) {
            DiseaseSurveillanceTriggerDefinitionSetter setter = new DiseaseSurveillanceTriggerDefinitionSetter(apolloTranslationEngine, type, section);
            return setter.set((DiseaseSurveillanceTriggerDefinition) t);
        } else if (t instanceof TemporalTriggerDefinition) {
            TemporalTriggerDefinitionSetter setter = new TemporalTriggerDefinitionSetter(apolloTranslationEngine, type, section);
            return setter.set((TemporalTriggerDefinition) t);
        } else if (t instanceof TreatmentSurveillanceTriggerDefinition) {
            TreatmentSurveillanceTriggerDefinitionSetter setter = new TreatmentSurveillanceTriggerDefinitionSetter(apolloTranslationEngine, type, section);
            return setter.set((TreatmentSurveillanceTriggerDefinition) t);
        } else {
            throw new ApolloSetterException("Unrecognized type of TriggerDefinition");
        }
    }
}
