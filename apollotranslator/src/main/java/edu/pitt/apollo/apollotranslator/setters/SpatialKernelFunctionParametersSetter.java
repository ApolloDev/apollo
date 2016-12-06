package edu.pitt.apollo.apollotranslator.setters;

import java.util.ArrayList;
import java.util.List;

import edu.pitt.apollo.apollotranslator.ApolloTranslationEngine;
import edu.pitt.apollo.apollotranslator.exception.ApolloSetterException;
import edu.pitt.apollo.apollotranslator.types.translator.SetterReturnObject;
import edu.pitt.apollo.types.v4_0_1.SpatialKernelFunctionParameters;

public class SpatialKernelFunctionParametersSetter extends
		AbstractTypedSetter<SpatialKernelFunctionParameters> {

//	private static final String SECTION = "SPATIAL KERNEL FUNCTION PARAMETERS";
	public static final String TYPE_FIELD = "type";
	public static final String A0_FIELD = "a0";
	public static final String A1_FIELD = "a1";
	public static final String B0_FIELD = "b0";
	public static final String B1_FIELD = "b1";
	public static final String C1_FIELD = "c1";
	public static final String CUTOFF_FIELD = "cutoff";

	public static final String[] fields = { TYPE_FIELD, A0_FIELD, A1_FIELD,
			B0_FIELD, B1_FIELD, C1_FIELD, CUTOFF_FIELD };

	public SpatialKernelFunctionParametersSetter(
			ApolloTranslationEngine apolloTranslationEngine, String type,
			String section) {
		super(type, section, apolloTranslationEngine);
	}

//	public SpatialKernelFunctionParametersSetter(
//			ApolloTranslationEngine apolloTranslationEngine) {
//		super("spatialKernalFunctionParameters", section, apolloTranslationEngine);
//		// TODO Auto-generated constructor stub
//	}

//	public SpatialKernelFunctionParametersSetter(String type,
//			ApolloTranslationEngine apolloTranslationEngine) {
//		super(type, SECTION, apolloTranslationEngine);
//	}

	public List<SetterReturnObject> setTypeField(String value)
			throws ApolloSetterException {
		return setValue(TYPE_FIELD, value, section);
	}

	public List<SetterReturnObject> setA0(String value)
			throws ApolloSetterException {
		return setValue(A0_FIELD, value, section);

	}

	public List<SetterReturnObject> setA1(String value)
			throws ApolloSetterException {
		return setValue(A1_FIELD, value, section);

	}

	public List<SetterReturnObject> setB0(String value)
			throws ApolloSetterException {
		return setValue(B0_FIELD, value, section);

	}

	public List<SetterReturnObject> setB1(
			String value) throws ApolloSetterException {
		return setValue(B1_FIELD,
				value, section);
	}

	public List<SetterReturnObject> setC1(
			String value) throws ApolloSetterException {
		return setValue(C1_FIELD,
				value, section);
	}
	
	public List<SetterReturnObject> setCutoff(
			String value) throws ApolloSetterException {
		return setValue(CUTOFF_FIELD,
				value, section);
	}


	public List<SetterReturnObject> setSpatialKernelFunctionParameters()
			throws ApolloSetterException {
		return setValue("", AbstractSetter.GENERIC_IS_NOT_NULL_LABEL, section);
	}

	@Override
	public List<SetterReturnObject> set(
			SpatialKernelFunctionParameters spatialKernelFunctionParameters)
			throws ApolloSetterException {
		List<SetterReturnObject> sroList = new ArrayList<SetterReturnObject>();
		sroList.addAll(setSpatialKernelFunctionParameters());

                sroList.addAll(setTypeField(spatialKernelFunctionParameters.getType()));
		sroList.addAll(setA0(String.valueOf(spatialKernelFunctionParameters.getA0())));
		sroList.addAll(setA1(String.valueOf(spatialKernelFunctionParameters.getA1())));
		sroList.addAll(setB0(String.valueOf(spatialKernelFunctionParameters.getB0())));
		sroList.addAll(setB1(String.valueOf(spatialKernelFunctionParameters.getB1())));
		sroList.addAll(setC1(String.valueOf(spatialKernelFunctionParameters.getC1())));
		sroList.addAll(setCutoff(String.valueOf(spatialKernelFunctionParameters.getCutoff())));
		
		return sroList;

	}
}
