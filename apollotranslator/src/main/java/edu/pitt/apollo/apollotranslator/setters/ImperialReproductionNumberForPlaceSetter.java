//package edu.pitt.apollo.apollotranslator.setters;
//
//import java.util.ArrayList;
//import java.util.List;
//
//import edu.pitt.apollo.apollotranslator.ApolloTranslationEngine;
//import edu.pitt.apollo.apollotranslator.exception.ApolloSetterException;
//import edu.pitt.apollo.apollotranslator.types.translator.SetterReturnObject;
//
//public class ImperialReproductionNumberForPlaceSetter extends
//		AbstractTypedSetter<ImperialReproductionNumberForPlace> {
//
////	private static final String SECTION = "IMPERIAL REPRODUCTION NUMBER";
//	public static final String PLACE_TYPE_FIELD = "placeType";
//	public static final String AVG_IMPERIAL_R0_FIELD = "averageImperialReproductionNumber";
//	public static final String WITHIN_GROUP_TRANSMISSION_PROB_FIELD = "withinGroupTransmissionProbability";
//	public static final String PROB_SYMPTOMATIC_INDIVIDUAL_ABSENT_FIELD = "probSymptomaticIndividualAbsent";
//	public static final String SYMPTOMATIC_ABSENTEE_MULTIPLIER_FIELD = "symptomaticAbsenteeMultiplier";
//	public static final String PROB_SEVERELY_INFECTED_INDIVIDUAL_ABSENT_PROB_FIELD = "probSeverelyInfectedIndividualAbsent";
//	public static final String SEVERELY_INFECTED_ABSENTEE_MULTIPLIER_FIELD = "severelyInfectedAbsenteeMultiplier";
//	public static final String SPATIAL_KERNEL_FUNCTION_PARAMETERS_FIELD = "spatialKernelFunctionParameters";
//
//	// protected ImperialReproductionNumber transmissionCoefficient;
//
//	public static final String[] fields = { PLACE_TYPE_FIELD,
//			AVG_IMPERIAL_R0_FIELD, WITHIN_GROUP_TRANSMISSION_PROB_FIELD,
//			PROB_SYMPTOMATIC_INDIVIDUAL_ABSENT_FIELD,
//			SYMPTOMATIC_ABSENTEE_MULTIPLIER_FIELD,
//			PROB_SEVERELY_INFECTED_INDIVIDUAL_ABSENT_PROB_FIELD,
//			SEVERELY_INFECTED_ABSENTEE_MULTIPLIER_FIELD,
//			SPATIAL_KERNEL_FUNCTION_PARAMETERS_FIELD };
//
//        public ImperialReproductionNumberForPlaceSetter() {
//            
//        }
//        
//	public ImperialReproductionNumberForPlaceSetter(
//			ApolloTranslationEngine apolloTranslationEngine, String type,
//			String section) {
//		super(type, section, apolloTranslationEngine);
//	}
//
////	public ImperialReproductionNumberForPlaceSetter(
////			ApolloTranslationEngine apolloTranslationEngine) {
////		super("infectiousDisease", SECTION, apolloTranslationEngine);
////		// TODO Auto-generated constructor stub
////	}
////
////	public ImperialReproductionNumberForPlaceSetter(String type,
////			ApolloTranslationEngine apolloTranslationEngine) {
////		super(type, SECTION, apolloTranslationEngine);
////	}
//
//	public List<SetterReturnObject> setPlaceType(String value)
//			throws ApolloSetterException {
//		return setValue(PLACE_TYPE_FIELD, value, section);
//	}
//
//	public List<SetterReturnObject> setAverageImperialReproductionNumber(
//			String value) throws ApolloSetterException {
//		return setValue(AVG_IMPERIAL_R0_FIELD, value, section);
//
//	}
//
//	public List<SetterReturnObject> setWithinGroupTransmissionProbability(
//			String value) throws ApolloSetterException {
//		return setValue(WITHIN_GROUP_TRANSMISSION_PROB_FIELD, value, section);
//
//	}
//
//	public List<SetterReturnObject> setProbSymptomaticIndividualAbsent(
//			String value) throws ApolloSetterException {
//		return setValue(PROB_SYMPTOMATIC_INDIVIDUAL_ABSENT_FIELD, value,
//				section);
//
//	}
//
//	public List<SetterReturnObject> setSymptomaticAbsenteeMultiplier(
//			String value) throws ApolloSetterException {
//		return setValue(SYMPTOMATIC_ABSENTEE_MULTIPLIER_FIELD, value, section);
//	}
//
//	public List<SetterReturnObject> setProbSeverelyInfectedIndividualAbsent(
//			String value) throws ApolloSetterException {
//		return setValue(PROB_SEVERELY_INFECTED_INDIVIDUAL_ABSENT_PROB_FIELD,
//				value, section);
//	}
//
//	public List<SetterReturnObject> setSeverelyInfectedAbsenteeMultiplier(
//			String value) throws ApolloSetterException {
//		return setValue(SEVERELY_INFECTED_ABSENTEE_MULTIPLIER_FIELD, value,
//				section);
//	}
//
//	public List<SetterReturnObject> setImperialReproductionNumberForPlace()
//			throws ApolloSetterException {
//		return setValue("", AbstractSetter.GENERIC_IS_NOT_NULL_LABEL, section);
//	}
//
//	@Override
//	public List<SetterReturnObject> set(
//			ImperialReproductionNumberForPlace imperialReproductionNumberForPlace)
//			throws ApolloSetterException {
//		List<SetterReturnObject> sroList = new ArrayList<SetterReturnObject>();
//
//		sroList.addAll(setImperialReproductionNumberForPlace());
//
//		sroList.addAll(setAverageImperialReproductionNumber(String
//				.valueOf(imperialReproductionNumberForPlace
//						.getAverageImperialReproductionNumber())));
//		sroList.addAll(setProbSeverelyInfectedIndividualAbsent(String
//				.valueOf(imperialReproductionNumberForPlace
//						.getProbSeverelyInfectedIndividualAbsent())));
//		sroList.addAll(setPlaceType(imperialReproductionNumberForPlace
//				.getPlaceType().value()));
//		
//		sroList.addAll(setProbSymptomaticIndividualAbsent(String
//				.valueOf(imperialReproductionNumberForPlace
//						.getProbSymptomaticIndividualAbsent())));
//		sroList.addAll(setSeverelyInfectedAbsenteeMultiplier(String
//				.valueOf(imperialReproductionNumberForPlace
//						.getSeverelyInfectedAbsenteeMultiplier())));
//		sroList.addAll(setWithinGroupTransmissionProbability(String
//				.valueOf(imperialReproductionNumberForPlace
//						.getWithinGroupTransmissionProbability())));
//		sroList.addAll(setSymptomaticAbsenteeMultiplier(String
//				.valueOf(imperialReproductionNumberForPlace
//						.getSymptomaticAbsenteeMultiplier())));
//
//		SpatialKernelFunctionParametersSetter skfps = new SpatialKernelFunctionParametersSetter(
//				apolloTranslationEngine, type + "."
//						+ SPATIAL_KERNEL_FUNCTION_PARAMETERS_FIELD, section);
//
//		sroList.addAll(skfps.set(imperialReproductionNumberForPlace
//				.getSpatialKernelFunctionParameters()));
//
//		return sroList;
//
//	}
//}
