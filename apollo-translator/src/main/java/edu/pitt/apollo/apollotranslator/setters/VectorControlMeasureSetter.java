package edu.pitt.apollo.apollotranslator.setters;

import edu.pitt.apollo.apollotranslator.ApolloTranslationEngine;
import edu.pitt.apollo.apollotranslator.exception.ApolloSetterException;
import edu.pitt.apollo.apollotranslator.types.translator.SetterReturnObject;
import edu.pitt.apollo.types.v4_0_2.*;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * Author: Nick Millett
 * Email: nick.millett@gmail.com
 * Date: Sep 3, 2014
 * Time: 5:11:55 PM
 * Class: VectorControlMeasureSetter
 */
public class VectorControlMeasureSetter<T extends VectorControlMeasure> extends InfectiousDiseaseControlMeasureSetter<T> {

	public static final String VECTOR_CONTROL_STRATEGY_SECTION = "VECTOR CONTROL STRATEGY";
	private static final String VECTOR_TAXON_ID = "vectorTaxonId";

	public VectorControlMeasureSetter() {

	}

	public VectorControlMeasureSetter(ApolloTranslationEngine apolloTranslationEngine, String prefix, String section) {
		super(apolloTranslationEngine, prefix, section);
	}

	private List<SetterReturnObject> setVectorTaxonId(String vectorTaxonId) throws ApolloSetterException {
		return setValue(VECTOR_TAXON_ID, vectorTaxonId, section);
	}

	private List<SetterReturnObject> setVectorControlMeasure(VectorControlMeasure t) throws ApolloSetterException {

		if (t instanceof WolbachiaControlMeasure) {
			WolbachiaControlMeasureSetter setter = new WolbachiaControlMeasureSetter(apolloTranslationEngine, type, section);
			return setter.set((WolbachiaControlMeasure) t);
		} else if (t instanceof IndoorResidualSprayingVectorControlMeasure) {
			IndoorResidualSprayingVectorControlMeasureSetter setter = new IndoorResidualSprayingVectorControlMeasureSetter(apolloTranslationEngine, type, section);
			return setter.set((IndoorResidualSprayingVectorControlMeasure) t);
		} else if (t instanceof LarvicideControlMeasure) {
			LarvicideControlMeasureSetter setter = new LarvicideControlMeasureSetter(apolloTranslationEngine, type, section);
			return setter.set((LarvicideControlMeasure) t);
		} else if (t instanceof ContainerReductionControlMeasure) {
			ContainerReductionControlMeasureSetter setter = new ContainerReductionControlMeasureSetter(apolloTranslationEngine, type, section);
			return setter.set((ContainerReductionControlMeasure) t);
		} else if (t instanceof InsecticideTreatedNetControlMeasure) {
            InsecticideTreatedNetControlMeasureSetter setter = new InsecticideTreatedNetControlMeasureSetter(apolloTranslationEngine, type, section);
            return setter.set((InsecticideTreatedNetControlMeasure) t);
        }
        else {
			throw new ApolloSetterException("Unrecognized type of VectorControlMeasure: " + t.getClass().getCanonicalName());
		}
	}

	@Override
	public List<SetterReturnObject> set(VectorControlMeasure t) throws ApolloSetterException {
//		this.section = VECTOR_CONTROL_STRATEGY_SECTION;

		if (t instanceof WolbachiaControlMeasure) {
			this.section = WolbachiaControlMeasureSetter.SECTION;
		} else if (t instanceof IndoorResidualSprayingVectorControlMeasure) {
			this.section = IndoorResidualSprayingVectorControlMeasureSetter.SECTION;
		} else if (t instanceof LarvicideControlMeasure) {
			this.section = LarvicideControlMeasureSetter.SECTION;
		} else if (t instanceof ContainerReductionControlMeasure) {
			this.section = ContainerReductionControlMeasureSetter.SECTION;
		} else if (t instanceof InsecticideTreatedNetControlMeasure) {
            this.section = InsecticideTreatedNetControlMeasureSetter.SECTION;
        }
        else {
			throw new ApolloSetterException("Unrecognized type of VectorControlMeasure: " + t.getClass().getCanonicalName());
		}

		List<SetterReturnObject> list = new ArrayList<SetterReturnObject>();
		list.addAll(setInfectiousDiseaseControlMeasure(t));
		if (t.getVectorTaxonId() != null) {
			list.addAll(setVectorTaxonId(t.getVectorTaxonId()));
		}
		list.addAll(setVectorControlMeasure(t));

		return list;
	}

}
