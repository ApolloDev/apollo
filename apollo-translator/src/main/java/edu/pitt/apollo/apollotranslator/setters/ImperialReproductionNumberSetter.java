//package edu.pitt.apollo.apollotranslator.setters;
//
//import java.util.ArrayList;
//import java.util.List;
//
//import edu.pitt.apollo.apollotranslator.ApolloTranslationEngine;
//import edu.pitt.apollo.apollotranslator.exception.ApolloSetterException;
//import edu.pitt.apollo.apollotranslator.types.translator.SetterReturnObject;
//
//public class ImperialReproductionNumberSetter extends AbstractTypedSetter<ImperialReproductionNumber> {
//
////    private static final String SECTION = "IMPERIAL REPRODUCTION NUMBER";
//    public static final String AVG_COMMUNITY_IMPERIAL_R0_FIELD = "averageCommunityImperialReproductionNumber";
//    public static final String AVG_HOUSEHOLD_IMPERIAL_R0_FIELD = "averageHouseholdImperialReproductionNumber";
//    public static final String R0_FOR_PLACE_FIELD = "imperialReproductionNumberForPlace";
//    public static final String SEASONALITY_FUNCTION_PARAMS_FIELD = "seasonalityFunctionParameters";
//    public static final String PROB_SYMPTOMATIC_INFECTION_CLINICALLY_DETECTED_FIELD = "probSymptomaticInfectionClinicallyDetected";
//    public static final String SYMPTOMATIC_INFECTION_MULTIPLIER_FIELD = "symptomaticInfectionMultiplier";
//    public static final String PROB_SEVERE_INFECTION_CLINICALLY_DETECTED_FIELD = "probSevereInfectionClinicallyDetected";
//    public static final String SEVERE_INFECTION_MULTIPLIER_FIELD = "severeInfectionMultiplier";
//    // protected ImperialReproductionNumber transmissionCoefficient;
//    public static final String[] fields = {AVG_COMMUNITY_IMPERIAL_R0_FIELD,
//        AVG_HOUSEHOLD_IMPERIAL_R0_FIELD, R0_FOR_PLACE_FIELD,
//        PROB_SYMPTOMATIC_INFECTION_CLINICALLY_DETECTED_FIELD,
//        SYMPTOMATIC_INFECTION_MULTIPLIER_FIELD,
//        PROB_SEVERE_INFECTION_CLINICALLY_DETECTED_FIELD,
//        SEVERE_INFECTION_MULTIPLIER_FIELD};
//
//    public ImperialReproductionNumberSetter(
//            ApolloTranslationEngine apolloTranslationEngine, String type,
//            String section) {
//        super(type, section, apolloTranslationEngine);
//    }
//
////	public ImperialReproductionNumberSetter(
////			ApolloTranslationEngine apolloTranslationEngine) {
////		super("infectiousDisease", SECTION, apolloTranslationEngine);
////		// TODO Auto-generated constructor stub
////	}
////	public ImperialReproductionNumberSetter(String type,
////			ApolloTranslationEngine apolloTranslationEngine) {
////		super(type, SECTION, apolloTranslationEngine);
////	}
//    public List<SetterReturnObject> setAverageCommunityImperialReproductionNumber(
//            String value) throws ApolloSetterException {
//        return setValue(AVG_COMMUNITY_IMPERIAL_R0_FIELD, value, section);
//    }
//
//    public List<SetterReturnObject> setAverageHouseholdImperialReproductionNumber(
//            String value) throws ApolloSetterException {
//        return setValue(AVG_HOUSEHOLD_IMPERIAL_R0_FIELD, value, section);
//
//    }
//
////    public List<SetterReturnObject> setImperialReproductionNumberForPlace(
////            String value) throws ApolloSetterException {
////        return setValue(R0_FOR_PLACE_FIELD, value, SECTION);
////
////    }
//
//    public List<SetterReturnObject> setSeasonalityFunctionParameters(
//            String value) throws ApolloSetterException {
//        return setValue(SEASONALITY_FUNCTION_PARAMS_FIELD, value, section);
//
//    }
//
//    public List<SetterReturnObject> setProbSymptomaticInfectionClinicallyDetected(
//            double reproductionNumber) throws ApolloSetterException {
//        return setValue(PROB_SYMPTOMATIC_INFECTION_CLINICALLY_DETECTED_FIELD,
//                Double.toString(reproductionNumber), section);
//    }
//
//    public List<SetterReturnObject> setSymptomaticInfectionMultiplier(
//            double reproductionNumber) throws ApolloSetterException {
//        return setValue(SYMPTOMATIC_INFECTION_MULTIPLIER_FIELD,
//                Double.toString(reproductionNumber), section);
//    }
//
//    public List<SetterReturnObject> setProbSevereInfectionClinicallyDetected(
//            double reproductionNumber) throws ApolloSetterException {
//        return setValue(PROB_SEVERE_INFECTION_CLINICALLY_DETECTED_FIELD,
//                Double.toString(reproductionNumber), section);
//    }
//
//    public List<SetterReturnObject> setSevereInfectionMultiplier(
//            double reproductionNumber) throws ApolloSetterException {
//        return setValue(SEVERE_INFECTION_MULTIPLIER_FIELD,
//                Double.toString(reproductionNumber), section);
//    }
//
//    public List<SetterReturnObject> setTransmissionCoefficient()
//            throws ApolloSetterException {
//        return setValue("", AbstractSetter.GENERIC_IS_NOT_NULL_LABEL, section);
//    }
//
//    public List<SetterReturnObject> setImperialReproductionNumberForPlace(List<ImperialReproductionNumberForPlace> imperialReproductionNumberForPlaceList) throws ApolloSetterException {
//
//        List<SetterReturnObject> sroList;
//        if (imperialReproductionNumberForPlaceList != null && imperialReproductionNumberForPlaceList.size() > 0) {
//
//            sroList = setValue(R0_FOR_PLACE_FIELD, "(list values described below)", section);
//
//            ListSetter setter = new ListSetter(ImperialReproductionNumberForPlaceSetter.class, ImperialReproductionNumberForPlace.class,
//                    imperialReproductionNumberForPlaceList, apolloTranslationEngine, section, type + "." + R0_FOR_PLACE_FIELD);
//
//            List<SetterReturnObject> result = setter.set();
//
//            sroList.get(0).setSubApolloParameters(result);
//        } else {
//            sroList = setValue(R0_FOR_PLACE_FIELD, PARAM_IS_NOT_SET_LABEL, section);
//        }
//        
//        return sroList;
//    }
//
//    @Override
//    public List<SetterReturnObject> set(ImperialReproductionNumber tc)
//            throws ApolloSetterException {
//        List<SetterReturnObject> sroList = new ArrayList<SetterReturnObject>();
//        sroList.addAll(setTransmissionCoefficient());
//
//        ImperialReproductionNumberForPlace r = tc.getImperialReproductionNumberForPlace().get(0);
//        sroList.addAll(setAverageCommunityImperialReproductionNumber(String.valueOf(tc.getAverageCommunityImperialReproductionNumber())));
//        sroList.addAll(setAverageHouseholdImperialReproductionNumber(String.valueOf(tc.getAverageHouseholdImperialReproductionNumber())));
//        sroList.addAll(setProbSevereInfectionClinicallyDetected(tc.getProbSevereInfectionClinicallyDetected()));
//        sroList.addAll(setProbSymptomaticInfectionClinicallyDetected(tc.getAverageCommunityImperialReproductionNumber()));
//        sroList.addAll(setSevereInfectionMultiplier(tc.getSevereInfectionMultiplier()));
//        sroList.addAll(setSymptomaticInfectionMultiplier(tc.getSymptomaticInfectionMultiplier()));
//
//        sroList.addAll(setImperialReproductionNumberForPlace(tc.getImperialReproductionNumberForPlace()));
//
//
////		ImperialReproductionNumberForPlaceSetter irnops = new ImperialReproductionNumberForPlaceSetter(
////				apolloTranslationEngine, type + "." + R0_FOR_PLACE_FIELD,
////				section);
////		for (ImperialReproductionNumberForPlace irnfp : tc
////				.getImperialReproductionNumberForPlace())
////			sroList.addAll(irnops.set(irnfp));
//
//        SeasonalityFunctionParametersSetter sfps = new SeasonalityFunctionParametersSetter(
//                apolloTranslationEngine, type + "." + SEASONALITY_FUNCTION_PARAMS_FIELD,
//                section);
//        sroList.addAll(sfps.set(tc.getSeasonalityFunctionParameters()));
//
//        return sroList;
//
//    }
//}
