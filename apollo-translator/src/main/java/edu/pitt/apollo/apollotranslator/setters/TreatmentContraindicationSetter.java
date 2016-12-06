package edu.pitt.apollo.apollotranslator.setters;

import java.util.ArrayList;
import java.util.List;

import edu.pitt.apollo.apollotranslator.ApolloTranslationEngine;
import edu.pitt.apollo.apollotranslator.exception.ApolloSetterException;
import edu.pitt.apollo.apollotranslator.types.translator.SetterReturnObject;
import edu.pitt.apollo.types.v4_0_1.TargetPopulationDefinition;
import edu.pitt.apollo.types.v4_0_1.TreatmentContraindication;

/**
 *
 * Author: Nick Millett
 * Email: nick.millett@gmail.com
 * Date: Feb 25, 2014
 * Time: 12:37:04 PM
 * Class: TreatmentContraindicationSetter
 * IDE: NetBeans 6.9.1
 */
public class TreatmentContraindicationSetter extends AbstractTypedSetter<TreatmentContraindication> {

    private static final String DESCRIPTION = "description";
    private static final String SIMULATOR_REFERENCABLE_POPULATION = "simulatorReferencablePopulation";
    private static final String FRACTION_OF_SIMULATOR_REFERENCABLE_POPULATION = "fractionOfSimulatorReferencablePopulation";

    public TreatmentContraindicationSetter() {
    }

    public TreatmentContraindicationSetter(ApolloTranslationEngine apolloTranslationEngine, String prefix, String section) {
        super(prefix, section, apolloTranslationEngine);
    }

    private List<SetterReturnObject> setDescription(String description) throws ApolloSetterException {
        return setValue(DESCRIPTION, description, section);
    }

    private List<SetterReturnObject> setSimulatorReferencablePopulation(TargetPopulationDefinition strata) throws ApolloSetterException {
        TargetPopulationDefinitionSetter setter = new TargetPopulationDefinitionSetter(apolloTranslationEngine, type + "." + SIMULATOR_REFERENCABLE_POPULATION, section);
        return setter.set(strata);
    }

    private List<SetterReturnObject> setFractionOfSimulatorReferencablePopulation(Double fraction) throws ApolloSetterException {
        return setValue(FRACTION_OF_SIMULATOR_REFERENCABLE_POPULATION, fraction.toString(), section);
    }

    @Override
    public List<SetterReturnObject> set(TreatmentContraindication t) throws ApolloSetterException {
        List<SetterReturnObject> list = new ArrayList<SetterReturnObject>();

        if (t.getDescription() != null) {
            list.addAll(setDescription(t.getDescription()));
        }
        list.addAll(setSimulatorReferencablePopulation(t.getSimulatorReferencablePopulation()));
        list.addAll(setFractionOfSimulatorReferencablePopulation(t.getFractionOfSimulatorReferencablePopulation()));

        return list;
    }
}
