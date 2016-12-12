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


import edu.pitt.apollo.apollo_service_types.v4_0_1.RunInfectiousDiseaseTransmissionExperimentMessage;
import edu.pitt.apollo.apollo_service_types.v4_0_1.RunSimulationsMessage;
import edu.pitt.apollo.data_service_types.v4_0_1.*;
import edu.pitt.apollo.filestore_service_types.v4_0_1.*;
import edu.pitt.apollo.library_service_types.v4_0_1.*;
import edu.pitt.apollo.query_service_types.v4_0_1.RunSimulatorOutputQueryMessage;
import edu.pitt.apollo.services_common.v4_0_1.*;
import edu.pitt.apollo.simulator_service_types.v4_0_1.GetScenarioLocationCodesSupportedBySimulatorResult;
import edu.pitt.apollo.simulator_service_types.v4_0_1.RunSimulationMessage;
import edu.pitt.apollo.simulator_service_types.v4_0_1.RunSimulationResult;
import edu.pitt.apollo.synthetic_population_service_types.v4_0_1.GetConfigurationFileForSimulationResult;
import edu.pitt.apollo.synthetic_population_service_types.v4_0_1.RunSyntheticPopulationGenerationMessage;
import edu.pitt.apollo.synthetic_population_service_types.v4_0_1.SyntheticPopulationGenerationResult;
import edu.pitt.apollo.synthetic_population_service_types.v4_0_1.SyntheticPopulationRunStatusMessage;
import edu.pitt.apollo.types.v4_0_1.*;
import edu.pitt.apollo.visualizer_service_types.v4_0_1.GetVisualizerOutputResourcesResult;
import edu.pitt.apollo.visualizer_service_types.v4_0_1.RunVisualizationMessage;
import edu.pitt.apollo.visualizer_service_types.v4_0_1.RunVisualizationResult;

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
				FileIdentification.class,
				GetFileUrlRequest.class,
				GetFileUrlResult.class,
				ListFilesForRunRequest.class,
				ListFilesForRunResult.class,
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
				LibrarySubCollection.class,
				ModifyGroupOwnershipMessage.class,
				ModifyGroupOwnershipResult.class,
				RevisionAndComments.class,
				SetLibraryItemAsNotReleasedMessage.class,
				SetLibraryItemAsNotReleasedResult.class,
				SetReleaseVersionMessage.class,
				SetReleaseVersionResult.class,
				TextContainer.class,
				UpdateLibraryItemContainerMessage.class,
				UpdateLibraryItemContainerResult.class,
				RunSimulatorOutputQueryMessage.class,
				Authentication.class,
				AuthorizationTypeEnum.class,
				ContentDataFormatEnum.class,
				ContentDataTypeEnum.class,
				FileAndURLDescription.class,
				FileTypeEnum.class,
				GetRegisteredServicesResult.class,
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
				RunStatusRequest.class,
				SerializationFormat.class,
				ServiceRecord.class,
				ServiceRegistrationRecord.class,
				ServiceResult.class,
				SoftwareOutputStratificationGranularity.class,
				TerminateRunRequest.class,
				TerminteRunResult.class,
				UrlOutputResource.class,
				GetConfigurationFileForSimulationResult.class,
				GetScenarioLocationCodesSupportedBySimulatorResult.class,
				RunSimulationMessage.class,
				RunSimulationResult.class,
				GetConfigurationFileForSimulationResult.class,
				RunSyntheticPopulationGenerationMessage.class,
				SyntheticPopulationGenerationResult.class,
				SyntheticPopulationRunStatusMessage.class,
				AbioticEcosystem.class,
				AbioticEcosystemData.class,
				AbioticThingCensus.class,
				AbioticThingEnum.class,
				AgeRangeCategoryDefinition.class,
				AnthraxDiseaseStageVariableValuesEnum.class,
				AntiviralTreatment.class,
				AntiviralTreatmentEfficacy.class,
				ApolloIndexableItem.class,
				ApolloIndexableItemTypeEnum.class,
				ApolloPathogenCode.class,
				ApolloSoftwareTypeEnum.class,
				ArrayDimensionsDefinition.class,
				BaseCaseResult.class,
				BayesianNetwork.class,
				BayesianNetworkStructureType.class,
				Behavior.class,
				BehaviorEnum.class,
				BioticEcosystem.class,
				BioticEcosystemData.class,
				BorderControlMeasure.class,
				Bound.class,
				CartesianCircleLocationDefinition.class,
				CaseCount.class,
				CaseCountArray.class,
				CaseCountArrayDescription.class,
				CaseCountArrayTypeEnum.class,
				CaseDefinition.class,
				CaseDefinitionEnum.class,
				CaseList.class,
				CaseQuarantineControlMeasure.class,
				CaseRecord.class,
				CaseRecordCategoricalVariable.class,
				CaseRecordCategoricalVariables.class,
				CaseVariableAndValue.class,
				CategoricalVariableEnum.class,
				CategoricalVariableNode.class,
				CategoryDefinition.class,
				CategoryValueNode.class,
				Census.class,
				CensusData.class,
				Characteristic.class,
				CharacteristicEnum.class,
				ChickenProductionFacility.class,
				ClaraDensityDependentMortalityFunction.class,
				Colonization.class,
				ColonizationAcquisition.class,
				ConditionalIndividualBehavior.class,
				ConditionalProbabilityDistribution.class,
				ConditionalProbabilityTable.class,
				ConditioningVariable.class,
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
				DateSpanWithNoDataCollection.class,
				DecisionAlternative.class,
				DecolonizationControlMeasure.class,
				DeprecatedCaseCount.class,
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
				DiseaseOutcomeTransition.class,
				DiseaseOutcomeTransitionEnum.class,
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
				EditorNote.class,
				EmploymentStatusCategoryDefinition.class,
				EmploymentStatusEnum.class,
				Epidemic.class,
				EpidemicCaseCounts.class,
				EpidemicForecast.class,
				EpidemicForecasts.class,
				EpidemicOutcomeEnum.class,
				EpidemicPeriod.class,
				EpidemicPeriodEndDefinitionEnum.class,
				EpidemicPeriodStartDefinitionEnum.class,
				EventFrequencyEnum.class,
				ExpectedUtility.class,
				Facility.class,
				FacilityTypeEnum.class,
				FiniteBoundaryTypeEnum.class,
				FixedDuration.class,
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
				IndividualControlMeasureStateEnum.class,
				IndividualDecolonization.class,
				IndividualDecolonizationEnum.class,
				IndividualDisease.class,
				IndividualInfection.class,
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
				InfectionStateEnum.class,
				InfectionStateTransition.class,
				InfectionStateTransitionEnum.class,
				InfectiousDisease.class,
				InfectiousDiseaseControlDecisionModel.class,
				InfectiousDiseaseControlMeasure.class,
				InfectiousDiseaseControlStrategy.class,
				InfectiousDiseaseDecisionAnalysisSpecificationAndResults.class,
				InfectiousDiseaseScenario.class,
				InfectiousDiseaseStageEnum.class,
				InfectiousDiseaseTransmissionExperimentSpecification.class,
				InfinityEnum.class,
				InsecticideTreatedNetControlMeasure.class,
				IntegerCount.class,
				Interval.class,
				IntervalBoundaryDefinitionEnum.class,
				LabTestAndResult.class,
				LarvicideControlMeasure.class,
				LatLongPair.class,
				LContamination.class,
				LiberalSickLeaveControlMeasure.class,
				LifeCycle.class,
				LifeStageWithDurationAndMortality.class,
				LInfection.class,
				LInfectiousDisease.class,
				Location.class,
				LocationCategoryDefinition.class,
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
				MeasuredQuantityRangeCategoryDefinition.class,
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
				RheaHealthcareFacilityEnum.class,
				RingIndividualTreatmentControlMeasure.class,
				ScenarioCartesianOrigin.class,
				Schedule.class,
				ScheduleElement.class,
				SchoolFacility.class,
				SchoolingStatusCategoryDefinition.class,
				SchoolingStatusEnum.class,
				SeasonalityFunctionParameters.class,
				SensitivityAnalysis.class,
				SensitivityAnalysisResult.class,
				SensitivityAnalysisSpecification.class,
				SensitivityAnalysisSpecificationAndResult.class,
				SimulatorCountOutput.class,
				SimulatorCountOutputResult.class,
				SimulatorCountOutputSpecification.class,
				SimulatorTimeRange.class,
				SimulatorTimeSpecification.class,
				SoftwareIdentification.class,
				SoftwareLicenseIdentification.class,
				SourceOfInfectionCategoryDefinition.class,
				SourceOfInfectionEnum.class,
				SpatialGranularityEnum.class,
				SpatialKernelFunctionParameters.class,
				SyntheticEcosystem.class,
				TargetPopulationDefinition.class,
				TargetPopulationEnum.class,
				TargetPriorityPopulation.class,
				TemplatedInfectiousDiseaseControlMeasureUrlForSoftware.class,
				TemporalCountArrayWithUniformIntervals.class,
				TemporalGranularityEnum.class,
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
				VariableAndCategoryDefinitions.class,
				VariableCategoryValueTree.class,
				VectorControlMeasure.class,
				VoluntaryHouseholdQuarantineControlMeasure.class,
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
