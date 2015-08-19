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

import edu.pitt.apollo.apollo_service_types.v3_0_2.RunSimulationsMessage;
import edu.pitt.apollo.data_service_types.v3_0_2.AddRoleMessage;
import edu.pitt.apollo.data_service_types.v3_0_2.AddRoleResult;
import edu.pitt.apollo.data_service_types.v3_0_2.AddRoleToUserMessage;
import edu.pitt.apollo.data_service_types.v3_0_2.AddTextDataContentMessage;
import edu.pitt.apollo.data_service_types.v3_0_2.AddTextDataContentResult;
import edu.pitt.apollo.data_service_types.v3_0_2.AddUserMessage;
import edu.pitt.apollo.data_service_types.v3_0_2.AddUserResult;
import edu.pitt.apollo.data_service_types.v3_0_2.AssociateContentWithRunIdMessage;
import edu.pitt.apollo.data_service_types.v3_0_2.AssociateContentWithRunIdResult;
import edu.pitt.apollo.data_service_types.v3_0_2.AssociateFileWithRunIdMessage;
import edu.pitt.apollo.data_service_types.v3_0_2.AssociateFileWithRunIdResult;
import edu.pitt.apollo.data_service_types.v3_0_2.AssociateURLWithRunIdMessage;
import edu.pitt.apollo.data_service_types.v3_0_2.AssociateURLWithRunIdResult;
import edu.pitt.apollo.data_service_types.v3_0_2.ContentIdAndDescription;
import edu.pitt.apollo.data_service_types.v3_0_2.DataRetrievalRequestMessage;
import edu.pitt.apollo.data_service_types.v3_0_2.DbContentDataFormatEnum;
import edu.pitt.apollo.data_service_types.v3_0_2.DbContentDataType;
import edu.pitt.apollo.data_service_types.v3_0_2.DeleteUserMessage;
import edu.pitt.apollo.data_service_types.v3_0_2.DeleteUserResult;
import edu.pitt.apollo.data_service_types.v3_0_2.GetAllOutputFilesURLAsZipMessage;
import edu.pitt.apollo.data_service_types.v3_0_2.GetAllOutputFilesURLAsZipResult;
import edu.pitt.apollo.data_service_types.v3_0_2.GetDataContentForSoftwareMessage;
import edu.pitt.apollo.data_service_types.v3_0_2.GetDataContentForSoftwareResult;
import edu.pitt.apollo.data_service_types.v3_0_2.GetFileContentMessage;
import edu.pitt.apollo.data_service_types.v3_0_2.GetFileContentResult;
import edu.pitt.apollo.data_service_types.v3_0_2.GetListOfRegisteredSoftwareMessage;
import edu.pitt.apollo.data_service_types.v3_0_2.GetListOfRegisteredSoftwareResult;
import edu.pitt.apollo.data_service_types.v3_0_2.GetOutputFilesURLAsZipMessage;
import edu.pitt.apollo.data_service_types.v3_0_2.GetOutputFilesURLAsZipResult;
import edu.pitt.apollo.data_service_types.v3_0_2.GetOutputFilesURLsMessage;
import edu.pitt.apollo.data_service_types.v3_0_2.GetOutputFilesURLsResult;
import edu.pitt.apollo.data_service_types.v3_0_2.GetRunDataDescriptionIdMessage;
import edu.pitt.apollo.data_service_types.v3_0_2.GetRunDataDescriptionIdResult;
import edu.pitt.apollo.data_service_types.v3_0_2.GetRunIdsAssociatedWithSimulationGroupMessage;
import edu.pitt.apollo.data_service_types.v3_0_2.GetRunIdsAssociatedWithSimulationGroupResult;
import edu.pitt.apollo.data_service_types.v3_0_2.GetRunInformationMessage;
import edu.pitt.apollo.data_service_types.v3_0_2.GetSoftwareIdentificationForRunMessage;
import edu.pitt.apollo.data_service_types.v3_0_2.GetSoftwareIdentificationForRunResult;
import edu.pitt.apollo.data_service_types.v3_0_2.GetSoftwareIdentificationKeyForRunMessage;
import edu.pitt.apollo.data_service_types.v3_0_2.GetSoftwareIdentificationKeyForRunResult;
import edu.pitt.apollo.data_service_types.v3_0_2.GetSoftwareIdentificationKeyFromSoftwareIdentificationMessage;
import edu.pitt.apollo.data_service_types.v3_0_2.GetSoftwareIdentificationKeyFromSoftwareIdentificationResult;
import edu.pitt.apollo.data_service_types.v3_0_2.GetStatusOfRunMessage;
import edu.pitt.apollo.data_service_types.v3_0_2.GetStatusOfRunResult;
import edu.pitt.apollo.data_service_types.v3_0_2.GetURLContentMessage;
import edu.pitt.apollo.data_service_types.v3_0_2.GetURLContentResult;
import edu.pitt.apollo.data_service_types.v3_0_2.GetURLForSoftwareIdentificationMessage;
import edu.pitt.apollo.data_service_types.v3_0_2.GetURLForSoftwareIdentificationResult;
import edu.pitt.apollo.data_service_types.v3_0_2.ListFilesMessage;
import edu.pitt.apollo.data_service_types.v3_0_2.ListFilesResult;
import edu.pitt.apollo.data_service_types.v3_0_2.ListOutputFilesForSoftwareMessage;
import edu.pitt.apollo.data_service_types.v3_0_2.ListOutputFilesForSoftwareResult;
import edu.pitt.apollo.data_service_types.v3_0_2.ListURLsMessage;
import edu.pitt.apollo.data_service_types.v3_0_2.ListURLsResult;
import edu.pitt.apollo.data_service_types.v3_0_2.RemoveRunDataMessage;
import edu.pitt.apollo.data_service_types.v3_0_2.RemoveRunDataResult;
import edu.pitt.apollo.data_service_types.v3_0_2.RunIdAndFiles;
import edu.pitt.apollo.data_service_types.v3_0_2.RunInformation;
import edu.pitt.apollo.data_service_types.v3_0_2.URLForFileAndRunId;
import edu.pitt.apollo.data_service_types.v3_0_2.UpdateLastServiceToBeCalledForRunMessage;
import edu.pitt.apollo.data_service_types.v3_0_2.UpdateLastServiceToBeCalledForRunResult;
import edu.pitt.apollo.data_service_types.v3_0_2.UpdateStatusOfRunMessage;
import edu.pitt.apollo.data_service_types.v3_0_2.UpdateStatusOfRunResult;
import edu.pitt.apollo.library_service_types.v3_0_2.*;
import edu.pitt.apollo.services_common.v3_0_2.ApolloSoftwareTypeEnum;
import edu.pitt.apollo.services_common.v3_0_2.Authentication;
import edu.pitt.apollo.services_common.v3_0_2.ContentDataFormatEnum;
import edu.pitt.apollo.services_common.v3_0_2.ContentDataTypeEnum;
import edu.pitt.apollo.services_common.v3_0_2.FileAndURLDescription;
import edu.pitt.apollo.services_common.v3_0_2.InsertRunResult;
import edu.pitt.apollo.services_common.v3_0_2.MethodCallStatus;
import edu.pitt.apollo.services_common.v3_0_2.MethodCallStatusEnum;
import edu.pitt.apollo.services_common.v3_0_2.ObjectSerializationInformation;
import edu.pitt.apollo.services_common.v3_0_2.Request;
import edu.pitt.apollo.services_common.v3_0_2.RequestMeta;
import edu.pitt.apollo.services_common.v3_0_2.Response;
import edu.pitt.apollo.services_common.v3_0_2.ResponseMeta;
import edu.pitt.apollo.services_common.v3_0_2.Role;
import edu.pitt.apollo.services_common.v3_0_2.RunActionEnum;
import edu.pitt.apollo.services_common.v3_0_2.RunIdentificationAndLabel;
import edu.pitt.apollo.services_common.v3_0_2.RunMessage;
import edu.pitt.apollo.services_common.v3_0_2.RunResult;
import edu.pitt.apollo.services_common.v3_0_2.RunStatus;
import edu.pitt.apollo.services_common.v3_0_2.SerializationFormat;
import edu.pitt.apollo.services_common.v3_0_2.ServiceRecord;
import edu.pitt.apollo.services_common.v3_0_2.ServiceRegistrationRecord;
import edu.pitt.apollo.services_common.v3_0_2.ServiceResult;
import edu.pitt.apollo.services_common.v3_0_2.SoftwareIdentification;
import edu.pitt.apollo.services_common.v3_0_2.SoftwareLicenseIdentification;
import edu.pitt.apollo.services_common.v3_0_2.SoftwareOutputStratificationGranularity;
import edu.pitt.apollo.services_common.v3_0_2.TerminateRunRequest;
import edu.pitt.apollo.services_common.v3_0_2.TerminteRunResult;
import edu.pitt.apollo.services_common.v3_0_2.UrlOutputResource;
import edu.pitt.apollo.simulator_service_types.v3_0_2.GetPopulationAndEnvironmentCensusResult;
import edu.pitt.apollo.simulator_service_types.v3_0_2.GetScenarioLocationCodesSupportedBySimulatorResult;
import edu.pitt.apollo.simulator_service_types.v3_0_2.RunSimulationMessage;
import edu.pitt.apollo.simulator_service_types.v3_0_2.RunSimulationResult;
import edu.pitt.apollo.synthetic_population_service_types.v3_0_2.GetConfigurationFileForSimulationResult;
import edu.pitt.apollo.synthetic_population_service_types.v3_0_2.RunSyntheticPopulationGenerationMessage;
import edu.pitt.apollo.synthetic_population_service_types.v3_0_2.SyntheticPopulationGenerationResult;
import edu.pitt.apollo.synthetic_population_service_types.v3_0_2.SyntheticPopulationRunStatusMessage;
import edu.pitt.apollo.types.v3_0_2.*;
//import edu.pitt.apollo.types.v3_0_2.IndividualHumanBehavior;
//import edu.pitt.apollo.types.v3_0_2.IndividualLifeCycle;
//import edu.pitt.apollo.types.v3_0_2.IndividualMosquitoBehavior;
//import edu.pitt.apollo.types.v3_0_2.IndividualMosquitoReproduction;
//import edu.pitt.apollo.types.v3_0_2.InfectionAcquisitionFromContaminatedThing;
//import edu.pitt.apollo.types.v3_0_2.InfectionAcquisitionFromInfectiousHost;
import edu.pitt.apollo.visualizer_service_types.v3_0_2.GetVisualizerOutputResourcesResult;
import edu.pitt.apollo.visualizer_service_types.v3_0_2.RunVisualizationMessage;
import edu.pitt.apollo.visualizer_service_types.v3_0_2.RunVisualizationResult;

/**
 *
 * @author nem41
 */
public class ApolloClassList {

	public static final Class[] classList;

	static {
		classList = new Class[]{
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
                RevisionCommentList.class,
                SetLibraryItemAsNotReleasedMessage.class,
                SetLibraryItemAsNotReleasedResult.class,
                SetReleaseVersionMessage.class,
                SetReleaseVersionResult.class,
                TextContainer.class,
                UpdateLibraryItemContainerMessage.class,
                UpdateLibraryItemContainerResult.class,
                ApolloSoftwareTypeEnum.class,
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
                SoftwareIdentification.class,
                SoftwareLicenseIdentification.class,
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
                ApolloPathogenCode.class,
                ArrayAxis.class,
                ArrayDimensionsDefinition.class,
                BaseCaseResult.class,
                BayesianNetwork.class,
                BayesianNetworkStructureType.class,
                Behavior.class,
                BehaviorEnum.class,
                BioticEcosystem.class,
                BioticEcosystemData.class,
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
                ContactIsolationControlStrategy.class,
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
                DataTypeEnum.class,
                DecisionAlternative.class,
                DecisionAnalysis.class,
                DecolonizationControlStrategy.class,
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
                IndividualTreatmentControlStrategy.class,
                IndividualTreatmentEnum.class,
                IndoorResidualSprayingVectorControlStrategy.class,
                Infection.class,
                InfectionAcquisitionFromColonizedHost.class,
                InfectionAcquisitionFromContaminatedAbioticThing.class,
                InfectionAcquisitionFromInfectedHost.class,
                InfectionOutcomeEnum.class,
                InfectionOutcomeWithDate.class,
                InfectionStateEnum.class,
                InfectiousDisease.class,
                InfectiousDiseaseControlStrategy.class,
                InfectiousDiseaseDecisionModel.class,
                InfectiousDiseaseScenario.class,
                InsecticideTreatedNetControlStrategy.class,
                IntegerCount.class,
                Interval.class,
                IntervalBoundaryDefinitionEnum.class,
                LabTestAndResult.class,
                LarvicideControlStrategy.class,
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
                OperatorEnum.class,
                OvipositionSiteCensus.class,
                ParameterValue.class,
                ParametricProbabilityDistribution.class,
                PlaceCategoryDefinition.class,
                PlaceClosureControlStrategy.class,
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
                RingIndividualTreatmentControlStrategy.class,
                RootApolloLibraryItem.class,
                RootApolloLibraryItemTypeEnum.class,
                ScenarioCartesianOrigin.class,
                Schedule.class,
                ScheduleElement.class,
                SchoolFacility.class,
                SchoolingStatusCategoryDefinition.class,
                SchoolingStatusEnum.class,
                SeasonalityFunctionParameters.class,
                SensitivityAnalysis.class,
                SensitivityAnalysisResult.class,
                SimulatorTimeRange.class,
                SimulatorTimeSpecification.class,
                SimulatorTypeEnum.class,
                SourceOfInfectionCategoryDefinition.class,
                SourceOfInfectionEnum.class,
                SpatialKernelFunctionParameters.class,
                SyntheticEcosystem.class,
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
                TreatmentSystemOutput.class,
                TriggerDefinition.class,
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
                VectorControlStrategy.class,
                WeibullDistribution.class,
                WithinGroupTransmissionProbability.class,
                WolbachiaControlStrategy.class,
                WolbachiaReleaseSiteEnum.class,
                GetVisualizerOutputResourcesResult.class,
                RunVisualizationMessage.class,
                RunVisualizationResult.class,

                org.eclipse.persistence.jaxb.xmlmodel.ObjectFactory.class
		};
	}
}
