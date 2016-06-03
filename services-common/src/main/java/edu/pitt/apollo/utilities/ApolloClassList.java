/*
 * Copyright 2015 nem41.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package edu.pitt.apollo.utilities;

import edu.pitt.apollo.apollo_service_types.v3_1_0.RunInfectiousDiseaseTransmissionExperimentMessage;
import edu.pitt.apollo.apollo_service_types.v3_1_0.RunSimulationsMessage;
import edu.pitt.apollo.data_service_types.v3_1_0.AddRoleMessage;
import edu.pitt.apollo.data_service_types.v3_1_0.AddRoleResult;
import edu.pitt.apollo.data_service_types.v3_1_0.AddRoleToUserMessage;
import edu.pitt.apollo.data_service_types.v3_1_0.AddTextDataContentMessage;
import edu.pitt.apollo.data_service_types.v3_1_0.AddTextDataContentResult;
import edu.pitt.apollo.data_service_types.v3_1_0.AddUserMessage;
import edu.pitt.apollo.data_service_types.v3_1_0.AddUserResult;
import edu.pitt.apollo.data_service_types.v3_1_0.AssociateContentWithRunIdMessage;
import edu.pitt.apollo.data_service_types.v3_1_0.AssociateContentWithRunIdResult;
import edu.pitt.apollo.data_service_types.v3_1_0.AssociateFileWithRunIdMessage;
import edu.pitt.apollo.data_service_types.v3_1_0.AssociateFileWithRunIdResult;
import edu.pitt.apollo.data_service_types.v3_1_0.AssociateURLWithRunIdMessage;
import edu.pitt.apollo.data_service_types.v3_1_0.AssociateURLWithRunIdResult;
import edu.pitt.apollo.data_service_types.v3_1_0.ContentIdAndDescription;
import edu.pitt.apollo.data_service_types.v3_1_0.DataRetrievalRequestMessage;
import edu.pitt.apollo.data_service_types.v3_1_0.DbContentDataFormatEnum;
import edu.pitt.apollo.data_service_types.v3_1_0.DbContentDataType;
import edu.pitt.apollo.data_service_types.v3_1_0.DeleteUserMessage;
import edu.pitt.apollo.data_service_types.v3_1_0.DeleteUserResult;
import edu.pitt.apollo.data_service_types.v3_1_0.GetAllOutputFilesURLAsZipMessage;
import edu.pitt.apollo.data_service_types.v3_1_0.GetAllOutputFilesURLAsZipResult;
import edu.pitt.apollo.data_service_types.v3_1_0.GetDataContentForSoftwareMessage;
import edu.pitt.apollo.data_service_types.v3_1_0.GetDataContentForSoftwareResult;
import edu.pitt.apollo.data_service_types.v3_1_0.GetFileContentMessage;
import edu.pitt.apollo.data_service_types.v3_1_0.GetFileContentResult;
import edu.pitt.apollo.data_service_types.v3_1_0.GetListOfRegisteredSoftwareMessage;
import edu.pitt.apollo.data_service_types.v3_1_0.GetListOfRegisteredSoftwareResult;
import edu.pitt.apollo.data_service_types.v3_1_0.GetOutputFilesURLAsZipMessage;
import edu.pitt.apollo.data_service_types.v3_1_0.GetOutputFilesURLAsZipResult;
import edu.pitt.apollo.data_service_types.v3_1_0.GetOutputFilesURLsMessage;
import edu.pitt.apollo.data_service_types.v3_1_0.GetOutputFilesURLsResult;
import edu.pitt.apollo.data_service_types.v3_1_0.GetRunDataDescriptionIdMessage;
import edu.pitt.apollo.data_service_types.v3_1_0.GetRunDataDescriptionIdResult;
import edu.pitt.apollo.data_service_types.v3_1_0.GetRunIdsAssociatedWithSimulationGroupMessage;
import edu.pitt.apollo.data_service_types.v3_1_0.GetRunIdsAssociatedWithSimulationGroupResult;
import edu.pitt.apollo.data_service_types.v3_1_0.GetRunInformationMessage;
import edu.pitt.apollo.data_service_types.v3_1_0.GetSoftwareIdentificationForRunMessage;
import edu.pitt.apollo.data_service_types.v3_1_0.GetSoftwareIdentificationForRunResult;
import edu.pitt.apollo.data_service_types.v3_1_0.GetSoftwareIdentificationKeyForRunMessage;
import edu.pitt.apollo.data_service_types.v3_1_0.GetSoftwareIdentificationKeyForRunResult;
import edu.pitt.apollo.data_service_types.v3_1_0.GetSoftwareIdentificationKeyFromSoftwareIdentificationMessage;
import edu.pitt.apollo.data_service_types.v3_1_0.GetSoftwareIdentificationKeyFromSoftwareIdentificationResult;
import edu.pitt.apollo.data_service_types.v3_1_0.GetStatusOfRunMessage;
import edu.pitt.apollo.data_service_types.v3_1_0.GetStatusOfRunResult;
import edu.pitt.apollo.data_service_types.v3_1_0.GetURLContentMessage;
import edu.pitt.apollo.data_service_types.v3_1_0.GetURLContentResult;
import edu.pitt.apollo.data_service_types.v3_1_0.GetURLForSoftwareIdentificationMessage;
import edu.pitt.apollo.data_service_types.v3_1_0.GetURLForSoftwareIdentificationResult;
import edu.pitt.apollo.data_service_types.v3_1_0.ListFilesMessage;
import edu.pitt.apollo.data_service_types.v3_1_0.ListFilesResult;
import edu.pitt.apollo.data_service_types.v3_1_0.ListOutputFilesForSoftwareMessage;
import edu.pitt.apollo.data_service_types.v3_1_0.ListOutputFilesForSoftwareResult;
import edu.pitt.apollo.data_service_types.v3_1_0.ListURLsMessage;
import edu.pitt.apollo.data_service_types.v3_1_0.ListURLsResult;
import edu.pitt.apollo.data_service_types.v3_1_0.RemoveRunDataMessage;
import edu.pitt.apollo.data_service_types.v3_1_0.RemoveRunDataResult;
import edu.pitt.apollo.data_service_types.v3_1_0.RunIdAndFiles;
import edu.pitt.apollo.data_service_types.v3_1_0.RunInformation;
import edu.pitt.apollo.data_service_types.v3_1_0.URLForFileAndRunId;
import edu.pitt.apollo.data_service_types.v3_1_0.UpdateLastServiceToBeCalledForRunMessage;
import edu.pitt.apollo.data_service_types.v3_1_0.UpdateLastServiceToBeCalledForRunResult;
import edu.pitt.apollo.data_service_types.v3_1_0.UpdateStatusOfRunMessage;
import edu.pitt.apollo.data_service_types.v3_1_0.UpdateStatusOfRunResult;
import edu.pitt.apollo.library_service_types.v3_1_0.*;

import edu.pitt.apollo.services_common.v3_1_0.Authentication;
import edu.pitt.apollo.services_common.v3_1_0.ContentDataFormatEnum;
import edu.pitt.apollo.services_common.v3_1_0.ContentDataTypeEnum;
import edu.pitt.apollo.services_common.v3_1_0.FileAndURLDescription;
import edu.pitt.apollo.services_common.v3_1_0.InsertRunResult;
import edu.pitt.apollo.services_common.v3_1_0.MethodCallStatus;
import edu.pitt.apollo.services_common.v3_1_0.MethodCallStatusEnum;
import edu.pitt.apollo.services_common.v3_1_0.ObjectSerializationInformation;
import edu.pitt.apollo.services_common.v3_1_0.Request;
import edu.pitt.apollo.services_common.v3_1_0.RequestMeta;
import edu.pitt.apollo.services_common.v3_1_0.Response;
import edu.pitt.apollo.services_common.v3_1_0.ResponseMeta;
import edu.pitt.apollo.services_common.v3_1_0.Role;
import edu.pitt.apollo.services_common.v3_1_0.RunActionEnum;
import edu.pitt.apollo.services_common.v3_1_0.RunIdentificationAndLabel;
import edu.pitt.apollo.services_common.v3_1_0.RunMessage;
import edu.pitt.apollo.services_common.v3_1_0.RunResult;
import edu.pitt.apollo.services_common.v3_1_0.RunStatus;
import edu.pitt.apollo.services_common.v3_1_0.SerializationFormat;
import edu.pitt.apollo.services_common.v3_1_0.ServiceRecord;
import edu.pitt.apollo.services_common.v3_1_0.ServiceRegistrationRecord;
import edu.pitt.apollo.services_common.v3_1_0.ServiceResult;

import edu.pitt.apollo.services_common.v3_1_0.SoftwareOutputStratificationGranularity;
import edu.pitt.apollo.services_common.v3_1_0.TerminateRunRequest;
import edu.pitt.apollo.services_common.v3_1_0.TerminteRunResult;
import edu.pitt.apollo.services_common.v3_1_0.UrlOutputResource;
import edu.pitt.apollo.simulator_service_types.v3_1_0.GetPopulationAndEnvironmentCensusResult;
import edu.pitt.apollo.simulator_service_types.v3_1_0.GetScenarioLocationCodesSupportedBySimulatorResult;
import edu.pitt.apollo.simulator_service_types.v3_1_0.RunSimulationMessage;
import edu.pitt.apollo.simulator_service_types.v3_1_0.RunSimulationResult;
import edu.pitt.apollo.synthetic_population_service_types.v3_1_0.GetConfigurationFileForSimulationResult;
import edu.pitt.apollo.synthetic_population_service_types.v3_1_0.RunSyntheticPopulationGenerationMessage;
import edu.pitt.apollo.synthetic_population_service_types.v3_1_0.SyntheticPopulationGenerationResult;
import edu.pitt.apollo.synthetic_population_service_types.v3_1_0.SyntheticPopulationRunStatusMessage;
import edu.pitt.apollo.types.v3_1_0.*;
//import edu.pitt.apollo.types.v3_1_0.IndividualHumanBehavior;
//import edu.pitt.apollo.types.v3_1_0.IndividualLifeCycle;
//import edu.pitt.apollo.types.v3_1_0.IndividualMosquitoBehavior;
//import edu.pitt.apollo.types.v3_1_0.IndividualMosquitoReproduction;
//import edu.pitt.apollo.types.v3_1_0.InfectionAcquisitionFromContaminatedThing;
//import edu.pitt.apollo.types.v3_1_0.InfectionAcquisitionFromInfectiousHost;
import edu.pitt.apollo.visualizer_service_types.v3_1_0.GetVisualizerOutputResourcesResult;
import edu.pitt.apollo.visualizer_service_types.v3_1_0.RunVisualizationMessage;
import edu.pitt.apollo.visualizer_service_types.v3_1_0.RunVisualizationResult;

/**
 *
 * @author nem41
 */
public class ApolloClassList {

	public static final Class[] classList;

	static {
		classList = new Class[]{
                RunInfectiousDiseaseTransmissionExperimentMessage.class,
                RunSimulationsMessage.class,
                AddRoleMessage.class,
                AddRoleResult.class,
                AddRoleToUserMessage.class,
                AddTextDataContentMessage.class,
                AddTextDataContentResult.class,
                AddUserMessage.class,
                AddUserResult.class,
                AssociateContentWithRunIdMessage.class,
                AssociateContentWithRunIdResult.class,
                AssociateFileWithRunIdMessage.class,
                AssociateFileWithRunIdResult.class,
                AssociateURLWithRunIdMessage.class,
                AssociateURLWithRunIdResult.class,
                ContentIdAndDescription.class,
                DataRetrievalRequestMessage.class,
                DbContentDataFormatEnum.class,
                DbContentDataType.class,
                DeleteUserMessage.class,
                DeleteUserResult.class,
                GetAllOutputFilesURLAsZipMessage.class,
                GetAllOutputFilesURLAsZipResult.class,
                GetDataContentForSoftwareMessage.class,
                GetDataContentForSoftwareResult.class,
                GetFileContentMessage.class,
                GetFileContentResult.class,
                GetListOfRegisteredSoftwareMessage.class,
                GetListOfRegisteredSoftwareResult.class,
                GetOutputFilesURLAsZipMessage.class,
                GetOutputFilesURLAsZipResult.class,
                GetOutputFilesURLsMessage.class,
                GetOutputFilesURLsResult.class,
                GetRunDataDescriptionIdMessage.class,
                GetRunDataDescriptionIdResult.class,
                GetRunIdsAssociatedWithSimulationGroupMessage.class,
                GetRunIdsAssociatedWithSimulationGroupResult.class,
                GetRunInformationMessage.class,
                GetSoftwareIdentificationForRunMessage.class,
                GetSoftwareIdentificationForRunResult.class,
                GetSoftwareIdentificationKeyForRunMessage.class,
                GetSoftwareIdentificationKeyForRunResult.class,
                GetSoftwareIdentificationKeyFromSoftwareIdentificationMessage.class,
                GetSoftwareIdentificationKeyFromSoftwareIdentificationResult.class,
                GetStatusOfRunMessage.class,
                GetStatusOfRunResult.class,
                GetURLContentMessage.class,
                GetURLContentResult.class,
                GetURLForSoftwareIdentificationMessage.class,
                GetURLForSoftwareIdentificationResult.class,
                ListFilesMessage.class,
                ListFilesResult.class,
                ListOutputFilesForSoftwareMessage.class,
                ListOutputFilesForSoftwareResult.class,
                ListURLsMessage.class,
                ListURLsResult.class,
                RemoveRunDataMessage.class,
                RemoveRunDataResult.class,
                RunIdAndFiles.class,
                RunInformation.class,
                UpdateLastServiceToBeCalledForRunMessage.class,
                UpdateLastServiceToBeCalledForRunResult.class,
                UpdateStatusOfRunMessage.class,
                UpdateStatusOfRunResult.class,
                URLForFileAndRunId.class,
                AddLibraryItemContainerMessage.class,
                AddLibraryItemContainerResult.class,
                AddReviewerCommentMessage.class,
                AddReviewerCommentResult.class,
                CatalogEntry.class,
                ChangeLogEntry.class,
                CommentFromLibrary.class,
                GetChangeLogForLibraryItemsModifiedSinceDateTimeMessage.class,
                GetChangeLogForLibraryItemsModifiedSinceDateTimeResult.class,
                GetCommentsMessage.class,
                GetCommentsResult.class,
                GetDiffMessage.class,
                GetDiffResult.class,
                GetGroupsMessage.class,
                GetLibraryItemContainerMessage.class,
                GetLibraryItemContainerResult.class,
                GetLibraryItemURNsMessage.class,
                GetLibraryItemURNsResult.class,
                GetReleaseVersionMessage.class,
                GetReleaseVersionResult.class,
                GetRevisionsResult.class,
                GetVersionsMessage.class,
                LibraryActionTypeEnum.class,
                LibraryCollection.class,
                LibraryItemContainer.class,
                ModifyGroupOwnershipMessage.class,
                ModifyGroupOwnershipResult.class,
                QueryMessage.class,
                QueryResult.class,
                RevisionAndComments.class,
                SetLibraryItemAsNotReleasedMessage.class,
                SetLibraryItemAsNotReleasedResult.class,
                SetReleaseVersionMessage.class,
                SetReleaseVersionResult.class,
                TextContainer.class,
                UpdateLibraryItemContainerMessage.class,
                UpdateLibraryItemContainerResult.class,
                Authentication.class,
                ContentDataFormatEnum.class,
                ContentDataTypeEnum.class,
                FileAndURLDescription.class,
                InsertRunResult.class,
                MethodCallStatus.class,
                MethodCallStatusEnum.class,
                ObjectSerializationInformation.class,
                Request.class,
                RequestMeta.class,
                Response.class,
                ResponseMeta.class,
                Role.class,
                RunActionEnum.class,
                RunIdentificationAndLabel.class,
                RunMessage.class,
                RunResult.class,
                RunStatus.class,
                SerializationFormat.class,
                ServiceRecord.class,
                ServiceRegistrationRecord.class,
                ServiceResult.class,
                SoftwareOutputStratificationGranularity.class,
                TerminateRunRequest.class,
                TerminteRunResult.class,
                UrlOutputResource.class,
                GetConfigurationFileForSimulationResult.class,
                GetPopulationAndEnvironmentCensusResult.class,
                GetScenarioLocationCodesSupportedBySimulatorResult.class,
                RunSimulationMessage.class,
                RunSimulationResult.class,
                GetConfigurationFileForSimulationResult.class,
                RunSyntheticPopulationGenerationMessage.class,
                SyntheticPopulationGenerationResult.class,
                SyntheticPopulationRunStatusMessage.class,
                AaaDummyType.class,
                AbioticEcosystem.class,
                AbioticEcosystemData.class,
                AbioticEcosystemElementCensusDescription.class,
                AbioticThingCensus.class,
                AbioticThingEnum.class,
                AgeGroupEfficacy.class,
                AgeRangeCategoryDefinition.class,
                AntiviralTreatment.class,
                AntiviralTreatmentEfficacy.class,
                ApolloIndexableItem.class,
                ApolloIndexableItemTypeEnum.class,
                ApolloPathogenCode.class,
                ApolloSoftwareTypeEnum.class,
                ArrayAxis.class,
                ArrayDimensionsDefinition.class,
                BaseCaseResult.class,
                BayesianNetwork.class,
                BayesianNetworkStructureType.class,
                Behavior.class,
                BehaviorEnum.class,
                BioticEcosystem.class,
                BioticEcosystemData.class,
                BorderControlMeasure.class,
                CartesianCircleLocationDefinition.class,
                CaseCount.class,
                CaseCountArray.class,
                CaseCountCategory.class,
                CaseDefinition.class,
                CaseDefinitionEnum.class,
                CaseList.class,
                CaseQuarantineControlMeasure.class,
                CaseRecord.class,
                CaseRecordCategoricalVariable.class,
                CaseRecordCategoricalVariables.class,
                CaseSeries.class,
                CasesInAPopulation.class,
                CaseVariableAndValue.class,
                Category.class,
                CategoryDefinition.class,
                Census.class,
                CensusData.class,
                Characteristic.class,
                CharacteristicEnum.class,
                ChickenProductionFacility.class,
                ClaraDensityDependentMortalityFunction.class,
                Colonization.class,
                ColonizationAcquisition.class,
                CompartmentalModelPopulationAndEnvironmentCensus.class,
                ConditionalIndividualBehavior.class,
                ConditionalProbabilityDistribution.class,
                ConditionalProbabilityTable.class,
                ConditioningVariable.class,
                ConditioningVariableEnum.class,
                ContactDefinition.class,
                ContactDefinitionEnum.class,
                ContactIsolationControlMeasure.class,
                ContactModelForCommunity.class,
                ContactModelForHousehold.class,
                ContactModelForPlace.class,
                ContactModelForSetting.class,
                ContainerReductionControlMeasure.class,
                ContaminatedThingCensus.class,
                ContaminatedThingCensusData.class,
                Contamination.class,
                ContaminationAcquisition.class,
                ContinuousParametricProbabilityDistribution.class,
                ContinuousUniformDistribution.class,
                ControlMeasureTargetPopulationsAndPrioritization.class,
                CountType.class,
                DataTypeEnum.class,
                DecisionAlternative.class,
                DecisionModel.class,
                DecolonizationControlMeasure.class,
                DevelopmentalStageEnum.class,
                DisabilityStatusCategoryDefinition.class,
                DisabilityStatusEnum.class,
                DischargeDestinationAndProbability.class,
                DischargePathAndProbability.class,
                DischargePathEnum.class,
                DiscreteNonparametricProbabilityDistribution.class,
                DiscreteParametricProbabilityDistribution.class,
                DiseaseOutcomeCategoryDefinition.class,
                DiseaseOutcomeEnum.class,
                DiseaseOutcomeWithDate.class,
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
                EmploymentStatusCategoryDefinition.class,
                EmploymentStatusEnum.class,
                Epidemic.class,
                EpidemicPeriod.class,
                EpidemicPeriodBoundaryDefinitionEnum.class,
                ExpectedUtility.class,
                Facility.class,
                FacilityTypeEnum.class,
                FixedDuration.class,
                FractionOfThingContaminated.class,
                GammaDistribution.class,
                GenderCategoryDefinition.class,
                GenderEnum.class,
                GeNIEXMLType.class,
                GesInfectiousnessParameterSet.class,
                GesParametersForContactAndTransmission.class,
                HealthcareFacilities.class,
                HealthcareFacility.class,
                HealthcareFacilityCategoryDefinition.class,
                Household.class,
                Households.class,
                HouseholdsData.class,
                HouseholdsPointerToData.class,
                HumanBehavior.class,
                Individual.class,
                IndividualAndEpidemiologicalCharacteristics.class,
                IndividualBiologyAndBehavior.class,
                IndividualDecolonization.class,
                IndividualDecolonizationEnum.class,
                IndividualDisease.class,
                IndividualInfection.class,
                IndividualMovement.class,
                IndividualsAndHouseholds.class,
                IndividualTreatmentControlMeasure.class,
                IndividualTreatmentEnum.class,
                IndoorResidualSprayingVectorControlMeasure.class,
                Infection.class,
                InfectionAcquisitionFromColonizedHost.class,
                InfectionAcquisitionFromContaminatedAbioticThing.class,
                InfectionAcquisitionFromInfectedHost.class,
                InfectionOutcomeEnum.class,
                InfectionOutcomeWithDate.class,
                InfectionSource.class,
                InfectionStateEnum.class,
                InfectiousDisease.class,
                InfectiousDisease2.class,
                InfectiousDiseaseControlDecisionModel.class,
                InfectiousDiseaseControlMeasure.class,
                InfectiousDiseaseControlStrategy.class,
                InfectiousDiseaseDecisionAnalysisSpecificationAndResults.class,
                InfectiousDiseaseScenario.class,
                InfectiousDiseaseTransmissionExperimentSpecification.class,
                InsecticideTreatedNetControlMeasure.class,
                IntegerCount.class,
                Interval.class,
                IntervalBoundaryDefinitionEnum.class,
                LabTestAndResult.class,
                LarvicideControlMeasure.class,
                LatLongPair.class,
                LibraryItem.class,
                LifeCycle.class,
                LifeStageWithDurationAndMortality.class,
                Location.class,
                LocationDefinition.class,
                LocationPolygon.class,
                LogisticalSystem.class,
                LogisticalSystemNode.class,
                LogNormalDistribution.class,
                MaritalStatusCategoryDefinition.class,
                MaritalStatusEnum.class,
                MeanMedianMinimumMaximum.class,
                MeanWithConfidenceInterval.class,
                MeanWithStandardDeviation.class,
                ModelIdAndProbability.class,
                MortalityFunction.class,
                MosquitoBehavior.class,
                MosquitoReproduction.class,
                MultiGeometry.class,
                NamedMultiGeometry.class,
                NamedPrioritizationSchemeEnum.class,
                NonApolloParameter.class,
                NonparametricProbabilityDistribution.class,
                NormalDistribution.class,
                OccupationEnum.class,
                OneWaySensitivityAnalysisOfContinousVariableSpecification.class,
                OperatorEnum.class,
                OvipositionSiteCensus.class,
                ParameterValue.class,
                ParametricProbabilityDistribution.class,
                PlaceCategoryDefinition.class,
                PlaceClosureControlMeasure.class,
                PlaceEnum.class,
                PlaceVisited.class,
                PointerToData.class,
                Population.class,
                PopulationAndEnvironmentCensus.class,
                PopulationCensusDescription.class,
                PopulationColonizationCensus.class,
                PopulationInfectionAndImmunityCensus.class,
                PopulationInfectionAndImmunityCensusData.class,
                PopulationInfectionAndImmunityCensusDataCell.class,
                PopulationInfectionSurvey.class,
                PopulationSerologySurvey.class,
                PopulationStratificationEnum.class,
                PopulationTreatmentCensus.class,
                PopulationTreatmentCensusData.class,
                PopulationTreatmentCensusDataCell.class,
                PopulationXXX.class,
                PreEpidemicEcosystemCensus.class,
                ProbabilisticParameter.class,
                ProbabilityDistribution.class,
                ProbabilityValuePair.class,
                PrototypicalProbabilityFunction.class,
                Rate.class,
                RealDateSpanCategoryDefinition.class,
                RealTimePointCategoryDefinition.class,
                RealTimeSpanCategoryDefinition.class,
                ReceivingDestinationAndProbability.class,
                Reference.class,
                RelativeRiskDataSet.class,
                Reproduction.class,
                ReproductionNumber.class,
                RequesterIdentification.class,
                RheaHealthcareFacilityEnum.class,
                RingIndividualTreatmentControlMeasure.class,
                ScenarioCartesianOrigin.class,
                Schedule.class,
                ScheduleElement.class,
                SchoolFacility.class,
                SchoolingStatusCategoryDefinition.class,
                SchoolingStatusEnum.class,
                SeasonalityFunctionParameters.class,
                SeedInfectionsIntoVectorPopulationControlMeasure.class,
                SensitivityAnalysisSpecification.class,
                SensitivityAnalysisResult.class,
                SimulatorTimeRange.class,
                SimulatorTimeSpecification.class,
                SimulatorTypeEnum.class,
                SoftwareIdentification.class,
                SoftwareLicenseIdentification.class,
                SourceOfInfectionCategoryDefinition.class,
                SourceOfInfectionEnum.class,
                SpatialKernelFunctionParameters.class,
                SyntheticEcosystem.class,
                TargetPopulationDefinition.class,
                TargetPopulationEnum.class,
                TargetPriorityPopulation.class,
                TemplatedInfectiousDiseaseControlMeasureUrlForSoftware.class,
                TemporalArrayDimensionsDefinition.class,
                TemporalTriggerDefinition.class,
                TimeAxisCategoryLabels.class,
                TimeDefinition.class,
                TimeScaleEnum.class,
                TimeSpanCategoryDefinition.class,
                TransmissionProbability.class,
                TransmissionTree.class,
                TravelRestrictionControlMeasure.class,
                Treatment.class,
                TreatmentContraindication.class,
                TreatmentEfficacy.class,
                TreatmentPreventableOutcomeEnum.class,
                TreatmentStateEnum.class,
                TreatmentSurveillanceCapability.class,
                TreatmentSurveillanceTriggerDefinition.class,
                TreatmentSystemOutput.class,
                TriggerDefinition.class,
                TwoWaySensitivityAnalysisOfContinousVariables.class,
                UncertainDuration.class,
                UncertainValue.class,
                UnconditionalProbabilityDistribution.class,
                Unit.class,
                UnitEnum.class,
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
                VectorControlMeasure.class,
                WeibullDistribution.class,
                WithinGroupTransmissionProbability.class,
                WolbachiaControlMeasure.class,
                WolbachiaReleaseSiteEnum.class,
                GetVisualizerOutputResourcesResult.class,
                RunVisualizationMessage.class,
                RunVisualizationResult.class,

                org.eclipse.persistence.jaxb.xmlmodel.ObjectFactory.class
		};
	}
}
