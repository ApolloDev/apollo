package edu.pitt.apollo;

import edu.pitt.apollo.types.v3_0_0.*;
import edu.pitt.apollo.types.v3_0_0.ObjectFactory;
import org.eclipse.persistence.jaxb.JAXBContext;
import org.eclipse.persistence.jaxb.JAXBContextProperties;
import org.eclipse.persistence.jaxb.JAXBMarshaller;
import org.eclipse.persistence.jaxb.JAXBUnmarshaller;
import org.eclipse.persistence.jaxb.xmlmodel.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import javax.xml.bind.JAXBException;
import javax.xml.transform.stream.StreamSource;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by jdl50 on 5/15/15.
 */
public class JsonUtils {

    static Logger logger = LoggerFactory.getLogger(JsonUtils.class);

    static Map<Class, JAXBMarshaller> marshallerMap = new HashMap<Class, JAXBMarshaller>();
    

    public final Object getObjectFromJson(String json, Class clazz) throws JsonUtilsException {
        InputStream jsonInputStream = new ByteArrayInputStream(json.getBytes());
        return getObjectFromJson(jsonInputStream, clazz);
    }

    public final Object getObjectFromJson(InputStream jsonInputStream, Class clazz) throws JsonUtilsException {
        Map<String, Object> jaxbProperties = new HashMap<String, Object>(2);
        jaxbProperties.put(JAXBContextProperties.MEDIA_TYPE, "application/json");
        jaxbProperties.put(JAXBContextProperties.JSON_INCLUDE_ROOT, false);
        JAXBContext jc;
        try {
            jc = (JAXBContext) JAXBContext.newInstance(getClassList(clazz), jaxbProperties);
            JAXBUnmarshaller unmarshaller = jc.createUnmarshaller();
            StreamSource ss = new StreamSource(jsonInputStream);
            return unmarshaller.unmarshal(ss, clazz).getValue();
        } catch (JAXBException ex) {
            throw new JsonUtilsException("JAXBException creating object from JSON: " + ex.getMessage());
        }
    }

    public synchronized final ByteArrayOutputStream getJsonBytes(Object obj) throws JAXBException {
        Class clazz = obj.getClass();
        JAXBMarshaller marshaller = null;
//        if (marshallerMap.containsKey(clazz)) {
//            marshaller = marshallerMap.get(clazz);
//        } else {
            Map<String, Object> jaxbProperties = new HashMap<String, Object>(2);
            jaxbProperties.put(JAXBContextProperties.MEDIA_TYPE, "application/json");
//		properties.put(JAXBContextProperties.JSON_INCLUDE_ROOT, false);
            JAXBContext jc = (JAXBContext) JAXBContext.newInstance(getClassList(clazz),
                    jaxbProperties);
            marshaller = jc.createMarshaller();
            marshaller.setProperty(JAXBMarshaller.JAXB_FORMATTED_OUTPUT, true);
//            marshallerMap.put(clazz, marshaller);
//        }

        ByteArrayOutputStream baos = new ByteArrayOutputStream();

        marshaller.marshal(obj, baos);
        return baos;
    }

    public String getJSONString(Object obj) {
        try {

            return getJsonBytes(obj).toString();

        } catch (Exception e) {
            logger.error("Exception encoding {} to JSON.  Error message was: {} ", obj, e.getMessage());
            return null;
        }

    }

    private static Class[] getClassList(Class clazz) {

        Class[] classList = new Class[]{clazz,
                // All ApolloTypes must be here
                AaaDummyType.class,
                AbioticEcosystem.class,
                AbioticEcosystemCensus.class,
                AbioticEcosystemElementCensusDescription.class,
                AbioticEcosystemEnum.class,
                AgeGroupEfficacy.class,
                AgeRangeCategoryDefinition.class,
                AntiviralTreatment.class,
                AntiviralTreatmentEfficacy.class,
                ApolloIndexableItem.class,
                ApolloIndexableItemTypeEnum.class,
                ApolloPathogenCode.class,
                ArrayAxis.class,
                ArrayDimensionsDefinition.class,
                BaseCaseResult.class,
                BayesianNetwork.class,
                BayesianNetworkStructureType.class,
                BehaviorEnum.class,
                BioticEcosystem.class,
                BorderControlStrategy.class,
                CartesianCircleLocationDefinition.class,
                CaseCount.class,
                CaseCountArray.class,
                CaseCountCategory.class,
                CaseDefinition.class,
                CaseDefinitionEnum.class,
                CaseList.class,
                CaseQuarantineControlStrategy.class,
                CaseRecord.class,
                CaseRecordCategoricalVariable.class,
                CaseRecordCategoricalVariables.class,
                CaseVariableAndValue.class,
                Category.class,
                CategoryDefinition.class,
                Census.class,
                CensusData.class,
                ClaraDensityDependentMortalityFunction.class,
                CompartmentalModelPopulationAndEnvironmentCensus.class,
                ConditionalIndividualBehavior.class,
                ConditionalProbabilityDistribution.class,
                ConditionalProbabilityTable.class,
                ConditioningVariable.class,
                ConditioningVariableEnum.class,
                ContactDefinition.class,
                ContactDefinitionEnum.class,
                ContactModelForCommunity.class,
                ContactModelForHousehold.class,
                ContactModelForPlace.class,
                ContactModelForSetting.class,
                ContainerReductionControlStrategy.class,
                ContaminatedThingCensus.class,
                ContaminatedThingCensusData.class,
                Contamination.class,
                ContaminationAcquisition.class,
                ContinuousParametricProbabilityDistribution.class,
                ContinuousUniformDistribution.class,
                ControlStrategyTargetPopulationsAndPrioritization.class,
                CountType.class,
                DecisionAlternative.class,
                DecisionAnalysis.class,
                DevelopmentalStageEnum.class,
                DiscreteNonparametricProbabilityDistribution.class,
                DiscreteParametricProbabilityDistribution.class,
                DiseaseOutcomeCategoryDefinition.class,
                DiseaseOutcomeEnum.class,
                DiseaseOutcomeWithLocationDateTime.class,
                DiseaseOutcomeWithProbability.class,
                DiseaseSurveillanceCapability.class,
                DiseaseSurveillanceTriggerDefinition.class,
                Distance.class,
                DoubleCount.class,
                DrugTreatment.class,
                DrugTreatmentEfficacyForSimulatorConfiguration.class,
                Duration.class,
                Ecosystem.class,
                Epidemic.class,
                EpidemicPeriod.class,
                EpidemicPeriodBoundaryDefinitionEnum.class,
                ExpectedUtility.class,
                FixedDuration.class,
                FractionOfThingContaminated.class,
                GammaDistribution.class,
                GenderCategoryDefinition.class,
                GenderEnum.class,
                GeNIEXMLType.class,
                GesInfectiousnessParameterSet.class,
                GesParametersForContactAndTransmission.class,
                Individual.class,
                IndividualBehavior.class,
                IndividualHumanBehavior.class,
                IndividualLifeCycle.class,
                IndividualMosquitoBehavior.class,
                IndividualMosquitoReproduction.class,
                IndividualTreatmentControlStrategy.class,
                IndividualTreatmentEnum.class,
                IndoorResidualSprayingVectorControlStrategy.class,
                Infection.class,
                InfectionAcquisitionFromContaminatedThing.class,
                InfectionAcquisitionFromInfectiousHost.class,
                InfectionStateEnum.class,
                InfectiousDisease.class,
                InfectiousDiseaseControlStrategy.class,
                InfectiousDiseaseDecisionModel.class,
                InfectiousDiseaseScenario.class,
                IntegerCount.class,
                Interval.class,
                IntervalBoundaryDefinitionEnum.class,
                LabTestAndResult.class,
                LarvicideControlStrategy.class,
                LibraryItem.class,
                LifeStageWithDurationAndMortality.class,
                Location.class,
                LocationDefinition.class,
                LocationPolygon.class,
                LogNormalDistribution.class,
                MeanMedianMinimumMaximum.class,
                MeanWithConfidenceInterval.class,
                MeanWithStandardDeviation.class,
                MortalityFunction.class,
                MultiGeometry.class,
                NamedMultiGeometry.class,
                NamedPrioritizationSchemeEnum.class,
                NonApolloParameter.class,
                NonparametricProbabilityDistribution.class,
                org.eclipse.persistence.jaxb.xmlmodel.ObjectFactory.class,
                OccupationEnum.class,
                OperatorEnum.class,
                OvipositionSiteCensus.class,
                ParameterValue.class,
                ParametricProbabilityDistribution.class,
                PlaceCategoryDefinition.class,
                PlaceClosureControlStrategy.class,
                PlaceEnum.class,
                PlaceVisited.class,
                Population.class,
                PopulationAndEnvironmentCensus.class,
                PopulationCensusDescription.class,
                PopulationInfectionAndImmunityCensus.class,
                PopulationInfectionAndImmunityCensusData.class,
                PopulationInfectionAndImmunityCensusDataCell.class,
                PopulationInfectionSurvey.class,
                PopulationSerologySurvey.class,
                PopulationStratificationEnum.class,
                PopulationTreatmentCensus.class,
                PopulationTreatmentCensusData.class,
                PopulationTreatmentCensusDataCell.class,
                PreEpidemicEcosystemCensus.class,
                ProbabilisticParameter.class,
                ProbabilityDistribution.class,
                ProbabilityValuePair.class,
                PrototypicalProbabilityFunction.class,
                Rate.class,
                RealDateSpanCategoryDefinition.class,
                RealTimePointCategoryDefinition.class,
                RealTimeSpanCategoryDefinition.class,
                Reference.class,
                RelativeRiskDataSet.class,
                ReproductionNumber.class,
                RequesterIdentification.class,
                RingIndividualTreatmentControlStrategy.class,
                ScenarioCartesianOrigin.class,
                SeasonalityFunctionParameters.class,
                SensitivityAnalysis.class,
                SensitivityAnalysisResult.class,
                SimulatorTimeRange.class,
                SimulatorTimeSpecification.class,
                SimulatorTypeEnum.class,
                SourceOfInfectionCategoryDefinition.class,
                SourceOfInfectionEnum.class,
                SpatialKernelFunctionParameters.class,
                TargetPopulationDefinition.class,
                TargetPopulationEnum.class,
                TargetPriorityPopulation.class,
                TemporalArrayDimensionsDefinition.class,
                TemporalTriggerDefinition.class,
                TimeAxisCategoryLabels.class,
                TimeDefinition.class,
                TimeScaleEnum.class,
                TimeSpanCategoryDefinition.class,
                TransmissionProbability.class,
                TransmissionTree.class,
                TravelRestrictionControlStrategy.class,
                Treatment.class,
                TreatmentContraindication.class,
                TreatmentEfficacy.class,
                TreatmentPreventableOutcomeEnum.class,
                TreatmentStateEnum.class,
                TreatmentSurveillanceCapability.class,
                TreatmentSurveillanceTriggerDefinition.class,
                TreatmentSystemLogistics.class,
                TriggerDefinition.class,
                UncertainDuration.class,
                UnconditionalProbabilityDistribution.class,
                UnitOfDistanceEnum.class,
                UnitOfMeasureEnum.class,
                UnitOfTimeEnum.class,
                UtilityFunction.class,
                Vaccination.class,
                VaccinationEfficacyConditionedOnTimeSinceDose.class,
                VaccinationEfficacyForSimulatorConfiguration.class,
                VaccinationEfficacyInferred.class,
                VaccinationEfficacyMeasured.class,
                VaccinationEfficacyStudy.class,
                Vaccine.class,
                VaccineContraindications.class,
                VariableCategoryDefinition.class,
                VectorControlStrategy.class,
                WeibullDistribution.class,
                WithinGroupTransmissionProbability.class,
                WolbachiaControlStrategy.class,
                WolbachiaReleaseSiteEnum.class,
                org.eclipse.persistence.jaxb.xmlmodel.ObjectFactory.class
        };

        return classList;

    }
}
