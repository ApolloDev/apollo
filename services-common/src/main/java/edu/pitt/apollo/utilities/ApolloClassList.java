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

import edu.pitt.apollo.apollo_service_types.v3_0_0.RunSimulationsMessage;
import edu.pitt.apollo.data_service_types.v3_0_0.AddRoleMessage;
import edu.pitt.apollo.data_service_types.v3_0_0.AddRoleResult;
import edu.pitt.apollo.data_service_types.v3_0_0.AddRoleToUserMessage;
import edu.pitt.apollo.data_service_types.v3_0_0.AddTextDataContentMessage;
import edu.pitt.apollo.data_service_types.v3_0_0.AddTextDataContentResult;
import edu.pitt.apollo.data_service_types.v3_0_0.AddUserMessage;
import edu.pitt.apollo.data_service_types.v3_0_0.AddUserResult;
import edu.pitt.apollo.data_service_types.v3_0_0.AssociateContentWithRunIdMessage;
import edu.pitt.apollo.data_service_types.v3_0_0.AssociateContentWithRunIdResult;
import edu.pitt.apollo.data_service_types.v3_0_0.AssociateFileWithRunIdMessage;
import edu.pitt.apollo.data_service_types.v3_0_0.AssociateFileWithRunIdResult;
import edu.pitt.apollo.data_service_types.v3_0_0.AssociateURLWithRunIdMessage;
import edu.pitt.apollo.data_service_types.v3_0_0.AssociateURLWithRunIdResult;
import edu.pitt.apollo.data_service_types.v3_0_0.ContentIdAndDescription;
import edu.pitt.apollo.data_service_types.v3_0_0.DataRetrievalRequestMessage;
import edu.pitt.apollo.data_service_types.v3_0_0.DbContentDataFormatEnum;
import edu.pitt.apollo.data_service_types.v3_0_0.DbContentDataType;
import edu.pitt.apollo.data_service_types.v3_0_0.DeleteUserMessage;
import edu.pitt.apollo.data_service_types.v3_0_0.DeleteUserResult;
import edu.pitt.apollo.data_service_types.v3_0_0.GetAllOutputFilesURLAsZipMessage;
import edu.pitt.apollo.data_service_types.v3_0_0.GetAllOutputFilesURLAsZipResult;
import edu.pitt.apollo.data_service_types.v3_0_0.GetDataContentForSoftwareMessage;
import edu.pitt.apollo.data_service_types.v3_0_0.GetDataContentForSoftwareResult;
import edu.pitt.apollo.data_service_types.v3_0_0.GetFileContentMessage;
import edu.pitt.apollo.data_service_types.v3_0_0.GetFileContentResult;
import edu.pitt.apollo.data_service_types.v3_0_0.GetListOfRegisteredSoftwareMessage;
import edu.pitt.apollo.data_service_types.v3_0_0.GetListOfRegisteredSoftwareResult;
import edu.pitt.apollo.data_service_types.v3_0_0.GetOutputFilesURLAsZipMessage;
import edu.pitt.apollo.data_service_types.v3_0_0.GetOutputFilesURLAsZipResult;
import edu.pitt.apollo.data_service_types.v3_0_0.GetOutputFilesURLsMessage;
import edu.pitt.apollo.data_service_types.v3_0_0.GetOutputFilesURLsResult;
import edu.pitt.apollo.data_service_types.v3_0_0.GetRunDataDescriptionIdMessage;
import edu.pitt.apollo.data_service_types.v3_0_0.GetRunDataDescriptionIdResult;
import edu.pitt.apollo.data_service_types.v3_0_0.GetRunIdsAssociatedWithSimulationGroupMessage;
import edu.pitt.apollo.data_service_types.v3_0_0.GetRunIdsAssociatedWithSimulationGroupResult;
import edu.pitt.apollo.data_service_types.v3_0_0.GetRunInformationMessage;
import edu.pitt.apollo.data_service_types.v3_0_0.GetSoftwareIdentificationForRunMessage;
import edu.pitt.apollo.data_service_types.v3_0_0.GetSoftwareIdentificationForRunResult;
import edu.pitt.apollo.data_service_types.v3_0_0.GetSoftwareIdentificationKeyForRunMessage;
import edu.pitt.apollo.data_service_types.v3_0_0.GetSoftwareIdentificationKeyForRunResult;
import edu.pitt.apollo.data_service_types.v3_0_0.GetSoftwareIdentificationKeyFromSoftwareIdentificationMessage;
import edu.pitt.apollo.data_service_types.v3_0_0.GetSoftwareIdentificationKeyFromSoftwareIdentificationResult;
import edu.pitt.apollo.data_service_types.v3_0_0.GetStatusOfRunMessage;
import edu.pitt.apollo.data_service_types.v3_0_0.GetStatusOfRunResult;
import edu.pitt.apollo.data_service_types.v3_0_0.GetURLContentMessage;
import edu.pitt.apollo.data_service_types.v3_0_0.GetURLContentResult;
import edu.pitt.apollo.data_service_types.v3_0_0.GetURLForSoftwareIdentificationMessage;
import edu.pitt.apollo.data_service_types.v3_0_0.GetURLForSoftwareIdentificationResult;
import edu.pitt.apollo.data_service_types.v3_0_0.ListFilesMessage;
import edu.pitt.apollo.data_service_types.v3_0_0.ListFilesResult;
import edu.pitt.apollo.data_service_types.v3_0_0.ListOutputFilesForSoftwareMessage;
import edu.pitt.apollo.data_service_types.v3_0_0.ListOutputFilesForSoftwareResult;
import edu.pitt.apollo.data_service_types.v3_0_0.ListURLsMessage;
import edu.pitt.apollo.data_service_types.v3_0_0.ListURLsResult;
import edu.pitt.apollo.data_service_types.v3_0_0.RemoveRunDataMessage;
import edu.pitt.apollo.data_service_types.v3_0_0.RemoveRunDataResult;
import edu.pitt.apollo.data_service_types.v3_0_0.RunIdAndFiles;
import edu.pitt.apollo.data_service_types.v3_0_0.RunInformation;
import edu.pitt.apollo.data_service_types.v3_0_0.URLForFileAndRunId;
import edu.pitt.apollo.data_service_types.v3_0_0.UpdateLastServiceToBeCalledForRunMessage;
import edu.pitt.apollo.data_service_types.v3_0_0.UpdateLastServiceToBeCalledForRunResult;
import edu.pitt.apollo.data_service_types.v3_0_0.UpdateStatusOfRunMessage;
import edu.pitt.apollo.data_service_types.v3_0_0.UpdateStatusOfRunResult;
import edu.pitt.apollo.library_service_types.v3_0_0.AddLibraryItemContainerMessage;
import edu.pitt.apollo.library_service_types.v3_0_0.AddLibraryItemContainerResult;
import edu.pitt.apollo.library_service_types.v3_0_0.AddReviewerCommentMessage;
import edu.pitt.apollo.library_service_types.v3_0_0.AddReviewerCommentResult;
import edu.pitt.apollo.library_service_types.v3_0_0.CatalogEntry;
import edu.pitt.apollo.library_service_types.v3_0_0.ChangeLogEntry;
import edu.pitt.apollo.library_service_types.v3_0_0.CommentFromLibrary;
import edu.pitt.apollo.library_service_types.v3_0_0.GetChangeLogForLibraryItemsModifiedSinceDateTimeMessage;
import edu.pitt.apollo.library_service_types.v3_0_0.GetChangeLogForLibraryItemsModifiedSinceDateTimeResult;
import edu.pitt.apollo.library_service_types.v3_0_0.GetCommentsMessage;
import edu.pitt.apollo.library_service_types.v3_0_0.GetCommentsResult;
import edu.pitt.apollo.library_service_types.v3_0_0.GetDiffMessage;
import edu.pitt.apollo.library_service_types.v3_0_0.GetDiffResult;
import edu.pitt.apollo.library_service_types.v3_0_0.GetGroupsMessage;
import edu.pitt.apollo.library_service_types.v3_0_0.GetLibraryItemContainerMessage;
import edu.pitt.apollo.library_service_types.v3_0_0.GetLibraryItemContainerResult;
import edu.pitt.apollo.library_service_types.v3_0_0.GetLibraryItemURNsMessage;
import edu.pitt.apollo.library_service_types.v3_0_0.GetLibraryItemURNsResult;
import edu.pitt.apollo.library_service_types.v3_0_0.GetReleaseVersionMessage;
import edu.pitt.apollo.library_service_types.v3_0_0.GetReleaseVersionResult;
import edu.pitt.apollo.library_service_types.v3_0_0.GetVersionsMessage;
import edu.pitt.apollo.library_service_types.v3_0_0.GetVersionsResult;
import edu.pitt.apollo.library_service_types.v3_0_0.LibraryActionTypeEnum;
import edu.pitt.apollo.library_service_types.v3_0_0.LibraryItemContainer;
import edu.pitt.apollo.library_service_types.v3_0_0.ModifyGroupOwnershipMessage;
import edu.pitt.apollo.library_service_types.v3_0_0.ModifyGroupOwnershipResult;
import edu.pitt.apollo.library_service_types.v3_0_0.QueryMessage;
import edu.pitt.apollo.library_service_types.v3_0_0.QueryResult;
import edu.pitt.apollo.library_service_types.v3_0_0.SetLibraryItemAsNotReleasedMessage;
import edu.pitt.apollo.library_service_types.v3_0_0.SetLibraryItemAsNotReleasedResult;
import edu.pitt.apollo.library_service_types.v3_0_0.SetReleaseVersionMessage;
import edu.pitt.apollo.library_service_types.v3_0_0.SetReleaseVersionResult;
import edu.pitt.apollo.library_service_types.v3_0_0.UpdateLibraryItemContainerMessage;
import edu.pitt.apollo.library_service_types.v3_0_0.UpdateLibraryItemContainerResult;
import edu.pitt.apollo.services_common.v3_0_0.ApolloSoftwareTypeEnum;
import edu.pitt.apollo.services_common.v3_0_0.Authentication;
import edu.pitt.apollo.services_common.v3_0_0.ContentDataFormatEnum;
import edu.pitt.apollo.services_common.v3_0_0.ContentDataTypeEnum;
import edu.pitt.apollo.services_common.v3_0_0.FileAndURLDescription;
import edu.pitt.apollo.services_common.v3_0_0.InsertRunResult;
import edu.pitt.apollo.services_common.v3_0_0.MethodCallStatus;
import edu.pitt.apollo.services_common.v3_0_0.MethodCallStatusEnum;
import edu.pitt.apollo.services_common.v3_0_0.ObjectSerializationInformation;
import edu.pitt.apollo.services_common.v3_0_0.Request;
import edu.pitt.apollo.services_common.v3_0_0.RequestMeta;
import edu.pitt.apollo.services_common.v3_0_0.Response;
import edu.pitt.apollo.services_common.v3_0_0.ResponseMeta;
import edu.pitt.apollo.services_common.v3_0_0.Role;
import edu.pitt.apollo.services_common.v3_0_0.RunActionEnum;
import edu.pitt.apollo.services_common.v3_0_0.RunIdentificationAndLabel;
import edu.pitt.apollo.services_common.v3_0_0.RunMessage;
import edu.pitt.apollo.services_common.v3_0_0.RunResult;
import edu.pitt.apollo.services_common.v3_0_0.RunStatus;
import edu.pitt.apollo.services_common.v3_0_0.SerializationFormat;
import edu.pitt.apollo.services_common.v3_0_0.ServiceRecord;
import edu.pitt.apollo.services_common.v3_0_0.ServiceRegistrationRecord;
import edu.pitt.apollo.services_common.v3_0_0.ServiceResult;
import edu.pitt.apollo.services_common.v3_0_0.SoftwareIdentification;
import edu.pitt.apollo.services_common.v3_0_0.SoftwareLicenseIdentification;
import edu.pitt.apollo.services_common.v3_0_0.SoftwareOutputStratificationGranularity;
import edu.pitt.apollo.services_common.v3_0_0.TerminateRunRequest;
import edu.pitt.apollo.services_common.v3_0_0.TerminteRunResult;
import edu.pitt.apollo.services_common.v3_0_0.UrlOutputResource;
import edu.pitt.apollo.simulator_service_types.v3_0_0.GetPopulationAndEnvironmentCensusResult;
import edu.pitt.apollo.simulator_service_types.v3_0_0.GetScenarioLocationCodesSupportedBySimulatorResult;
import edu.pitt.apollo.simulator_service_types.v3_0_0.RunSimulationMessage;
import edu.pitt.apollo.simulator_service_types.v3_0_0.RunSimulationResult;
import edu.pitt.apollo.synthetic_population_service_types.v3_0_0.GetConfigurationFileForSimulationResult;
import edu.pitt.apollo.synthetic_population_service_types.v3_0_0.RunSyntheticPopulationGenerationMessage;
import edu.pitt.apollo.synthetic_population_service_types.v3_0_0.SyntheticPopulationGenerationResult;
import edu.pitt.apollo.synthetic_population_service_types.v3_0_0.SyntheticPopulationRunStatusMessage;
import edu.pitt.apollo.types.v3_0_0.AaaDummyType;
import edu.pitt.apollo.types.v3_0_0.AbioticEcosystem;
import edu.pitt.apollo.types.v3_0_0.AbioticEcosystemCensus;
import edu.pitt.apollo.types.v3_0_0.AbioticEcosystemElementCensusDescription;
import edu.pitt.apollo.types.v3_0_0.AbioticEcosystemEnum;
import edu.pitt.apollo.types.v3_0_0.AgeGroupEfficacy;
import edu.pitt.apollo.types.v3_0_0.AgeRangeCategoryDefinition;
import edu.pitt.apollo.types.v3_0_0.AntiviralTreatment;
import edu.pitt.apollo.types.v3_0_0.AntiviralTreatmentEfficacy;
import edu.pitt.apollo.types.v3_0_0.ApolloIndexableItem;
import edu.pitt.apollo.types.v3_0_0.ApolloIndexableItemTypeEnum;
import edu.pitt.apollo.types.v3_0_0.ApolloPathogenCode;
import edu.pitt.apollo.types.v3_0_0.ArrayAxis;
import edu.pitt.apollo.types.v3_0_0.ArrayDimensionsDefinition;
import edu.pitt.apollo.types.v3_0_0.BaseCaseResult;
import edu.pitt.apollo.types.v3_0_0.BayesianNetwork;
import edu.pitt.apollo.types.v3_0_0.BayesianNetworkStructureType;
import edu.pitt.apollo.types.v3_0_0.BehaviorEnum;
import edu.pitt.apollo.types.v3_0_0.BioticEcosystem;
import edu.pitt.apollo.types.v3_0_0.BorderControlStrategy;
import edu.pitt.apollo.types.v3_0_0.CartesianCircleLocationDefinition;
import edu.pitt.apollo.types.v3_0_0.CaseCount;
import edu.pitt.apollo.types.v3_0_0.CaseCountArray;
import edu.pitt.apollo.types.v3_0_0.CaseCountCategory;
import edu.pitt.apollo.types.v3_0_0.CaseDefinition;
import edu.pitt.apollo.types.v3_0_0.CaseDefinitionEnum;
import edu.pitt.apollo.types.v3_0_0.CaseList;
import edu.pitt.apollo.types.v3_0_0.CaseQuarantineControlStrategy;
import edu.pitt.apollo.types.v3_0_0.CaseRecord;
import edu.pitt.apollo.types.v3_0_0.CaseRecordCategoricalVariable;
import edu.pitt.apollo.types.v3_0_0.CaseRecordCategoricalVariables;
import edu.pitt.apollo.types.v3_0_0.CaseVariableAndValue;
import edu.pitt.apollo.types.v3_0_0.Category;
import edu.pitt.apollo.types.v3_0_0.CategoryDefinition;
import edu.pitt.apollo.types.v3_0_0.Census;
import edu.pitt.apollo.types.v3_0_0.CensusData;
import edu.pitt.apollo.types.v3_0_0.ClaraDensityDependentMortalityFunction;
import edu.pitt.apollo.types.v3_0_0.CompartmentalModelPopulationAndEnvironmentCensus;
import edu.pitt.apollo.types.v3_0_0.ConditionalIndividualBehavior;
import edu.pitt.apollo.types.v3_0_0.ConditionalProbabilityDistribution;
import edu.pitt.apollo.types.v3_0_0.ConditionalProbabilityTable;
import edu.pitt.apollo.types.v3_0_0.ConditioningVariable;
import edu.pitt.apollo.types.v3_0_0.ConditioningVariableEnum;
import edu.pitt.apollo.types.v3_0_0.ContactDefinition;
import edu.pitt.apollo.types.v3_0_0.ContactDefinitionEnum;
import edu.pitt.apollo.types.v3_0_0.ContactModelForCommunity;
import edu.pitt.apollo.types.v3_0_0.ContactModelForHousehold;
import edu.pitt.apollo.types.v3_0_0.ContactModelForPlace;
import edu.pitt.apollo.types.v3_0_0.ContactModelForSetting;
import edu.pitt.apollo.types.v3_0_0.ContainerReductionControlStrategy;
import edu.pitt.apollo.types.v3_0_0.ContaminatedThingCensus;
import edu.pitt.apollo.types.v3_0_0.ContaminatedThingCensusData;
import edu.pitt.apollo.types.v3_0_0.Contamination;
import edu.pitt.apollo.types.v3_0_0.ContaminationAcquisition;
import edu.pitt.apollo.types.v3_0_0.ContinuousParametricProbabilityDistribution;
import edu.pitt.apollo.types.v3_0_0.ContinuousUniformDistribution;
import edu.pitt.apollo.types.v3_0_0.ControlStrategyTargetPopulationsAndPrioritization;
import edu.pitt.apollo.types.v3_0_0.CountType;
import edu.pitt.apollo.types.v3_0_0.DecisionAlternative;
import edu.pitt.apollo.types.v3_0_0.DecisionAnalysis;
import edu.pitt.apollo.types.v3_0_0.DevelopmentalStageEnum;
import edu.pitt.apollo.types.v3_0_0.DiscreteNonparametricProbabilityDistribution;
import edu.pitt.apollo.types.v3_0_0.DiscreteParametricProbabilityDistribution;
import edu.pitt.apollo.types.v3_0_0.DiseaseOutcomeCategoryDefinition;
import edu.pitt.apollo.types.v3_0_0.DiseaseOutcomeEnum;
import edu.pitt.apollo.types.v3_0_0.DiseaseOutcomeWithLocationDateTime;
import edu.pitt.apollo.types.v3_0_0.DiseaseOutcomeWithProbability;
import edu.pitt.apollo.types.v3_0_0.DiseaseSurveillanceCapability;
import edu.pitt.apollo.types.v3_0_0.DiseaseSurveillanceTriggerDefinition;
import edu.pitt.apollo.types.v3_0_0.Distance;
import edu.pitt.apollo.types.v3_0_0.DoubleCount;
import edu.pitt.apollo.types.v3_0_0.DrugTreatment;
import edu.pitt.apollo.types.v3_0_0.DrugTreatmentEfficacyForSimulatorConfiguration;
import edu.pitt.apollo.types.v3_0_0.Duration;
import edu.pitt.apollo.types.v3_0_0.Ecosystem;
import edu.pitt.apollo.types.v3_0_0.Epidemic;
import edu.pitt.apollo.types.v3_0_0.EpidemicPeriod;
import edu.pitt.apollo.types.v3_0_0.EpidemicPeriodBoundaryDefinitionEnum;
import edu.pitt.apollo.types.v3_0_0.ExpectedUtility;
import edu.pitt.apollo.types.v3_0_0.FixedDuration;
import edu.pitt.apollo.types.v3_0_0.FractionOfThingContaminated;
import edu.pitt.apollo.types.v3_0_0.GammaDistribution;
import edu.pitt.apollo.types.v3_0_0.GeNIEXMLType;
import edu.pitt.apollo.types.v3_0_0.GenderCategoryDefinition;
import edu.pitt.apollo.types.v3_0_0.GenderEnum;
import edu.pitt.apollo.types.v3_0_0.GesInfectiousnessParameterSet;
import edu.pitt.apollo.types.v3_0_0.GesParametersForContactAndTransmission;
import edu.pitt.apollo.types.v3_0_0.Individual;
import edu.pitt.apollo.types.v3_0_0.IndividualBehavior;
import edu.pitt.apollo.types.v3_0_0.IndividualHumanBehavior;
import edu.pitt.apollo.types.v3_0_0.IndividualLifeCycle;
import edu.pitt.apollo.types.v3_0_0.IndividualMosquitoBehavior;
import edu.pitt.apollo.types.v3_0_0.IndividualMosquitoReproduction;
import edu.pitt.apollo.types.v3_0_0.IndividualTreatmentControlStrategy;
import edu.pitt.apollo.types.v3_0_0.IndividualTreatmentEnum;
import edu.pitt.apollo.types.v3_0_0.IndoorResidualSprayingVectorControlStrategy;
import edu.pitt.apollo.types.v3_0_0.Infection;
import edu.pitt.apollo.types.v3_0_0.InfectionAcquisitionFromContaminatedThing;
import edu.pitt.apollo.types.v3_0_0.InfectionAcquisitionFromInfectiousHost;
import edu.pitt.apollo.types.v3_0_0.InfectionStateEnum;
import edu.pitt.apollo.types.v3_0_0.InfectiousDisease;
import edu.pitt.apollo.types.v3_0_0.InfectiousDiseaseControlStrategy;
import edu.pitt.apollo.types.v3_0_0.InfectiousDiseaseDecisionModel;
import edu.pitt.apollo.types.v3_0_0.InfectiousDiseaseScenario;
import edu.pitt.apollo.types.v3_0_0.IntegerCount;
import edu.pitt.apollo.types.v3_0_0.Interval;
import edu.pitt.apollo.types.v3_0_0.IntervalBoundaryDefinitionEnum;
import edu.pitt.apollo.types.v3_0_0.LabTestAndResult;
import edu.pitt.apollo.types.v3_0_0.LarvicideControlStrategy;
import edu.pitt.apollo.types.v3_0_0.LibraryItem;
import edu.pitt.apollo.types.v3_0_0.LifeStageWithDurationAndMortality;
import edu.pitt.apollo.types.v3_0_0.Location;
import edu.pitt.apollo.types.v3_0_0.LocationDefinition;
import edu.pitt.apollo.types.v3_0_0.LocationPolygon;
import edu.pitt.apollo.types.v3_0_0.LogNormalDistribution;
import edu.pitt.apollo.types.v3_0_0.MeanMedianMinimumMaximum;
import edu.pitt.apollo.types.v3_0_0.MeanWithConfidenceInterval;
import edu.pitt.apollo.types.v3_0_0.MeanWithStandardDeviation;
import edu.pitt.apollo.types.v3_0_0.MortalityFunction;
import edu.pitt.apollo.types.v3_0_0.MultiGeometry;
import edu.pitt.apollo.types.v3_0_0.NamedMultiGeometry;
import edu.pitt.apollo.types.v3_0_0.NamedPrioritizationSchemeEnum;
import edu.pitt.apollo.types.v3_0_0.NonApolloParameter;
import edu.pitt.apollo.types.v3_0_0.NonparametricProbabilityDistribution;
import edu.pitt.apollo.types.v3_0_0.OccupationEnum;
import edu.pitt.apollo.types.v3_0_0.OperatorEnum;
import edu.pitt.apollo.types.v3_0_0.OvipositionSiteCensus;
import edu.pitt.apollo.types.v3_0_0.ParameterValue;
import edu.pitt.apollo.types.v3_0_0.ParametricProbabilityDistribution;
import edu.pitt.apollo.types.v3_0_0.PlaceCategoryDefinition;
import edu.pitt.apollo.types.v3_0_0.PlaceClosureControlStrategy;
import edu.pitt.apollo.types.v3_0_0.PlaceEnum;
import edu.pitt.apollo.types.v3_0_0.PlaceVisited;
import edu.pitt.apollo.types.v3_0_0.Population;
import edu.pitt.apollo.types.v3_0_0.PopulationAndEnvironmentCensus;
import edu.pitt.apollo.types.v3_0_0.PopulationCensusDescription;
import edu.pitt.apollo.types.v3_0_0.PopulationInfectionAndImmunityCensus;
import edu.pitt.apollo.types.v3_0_0.PopulationInfectionAndImmunityCensusData;
import edu.pitt.apollo.types.v3_0_0.PopulationInfectionAndImmunityCensusDataCell;
import edu.pitt.apollo.types.v3_0_0.PopulationInfectionSurvey;
import edu.pitt.apollo.types.v3_0_0.PopulationSerologySurvey;
import edu.pitt.apollo.types.v3_0_0.PopulationStratificationEnum;
import edu.pitt.apollo.types.v3_0_0.PopulationTreatmentCensus;
import edu.pitt.apollo.types.v3_0_0.PopulationTreatmentCensusData;
import edu.pitt.apollo.types.v3_0_0.PopulationTreatmentCensusDataCell;
import edu.pitt.apollo.types.v3_0_0.PreEpidemicEcosystemCensus;
import edu.pitt.apollo.types.v3_0_0.ProbabilisticParameter;
import edu.pitt.apollo.types.v3_0_0.ProbabilityDistribution;
import edu.pitt.apollo.types.v3_0_0.ProbabilityValuePair;
import edu.pitt.apollo.types.v3_0_0.PrototypicalProbabilityFunction;
import edu.pitt.apollo.types.v3_0_0.Rate;
import edu.pitt.apollo.types.v3_0_0.RealDateSpanCategoryDefinition;
import edu.pitt.apollo.types.v3_0_0.RealTimePointCategoryDefinition;
import edu.pitt.apollo.types.v3_0_0.RealTimeSpanCategoryDefinition;
import edu.pitt.apollo.types.v3_0_0.Reference;
import edu.pitt.apollo.types.v3_0_0.RelativeRiskDataSet;
import edu.pitt.apollo.types.v3_0_0.ReproductionNumber;
import edu.pitt.apollo.types.v3_0_0.RequesterIdentification;
import edu.pitt.apollo.types.v3_0_0.RingIndividualTreatmentControlStrategy;
import edu.pitt.apollo.types.v3_0_0.ScenarioCartesianOrigin;
import edu.pitt.apollo.types.v3_0_0.SeasonalityFunctionParameters;
import edu.pitt.apollo.types.v3_0_0.SensitivityAnalysis;
import edu.pitt.apollo.types.v3_0_0.SensitivityAnalysisResult;
import edu.pitt.apollo.types.v3_0_0.SimulatorTimeRange;
import edu.pitt.apollo.types.v3_0_0.SimulatorTimeSpecification;
import edu.pitt.apollo.types.v3_0_0.SimulatorTypeEnum;
import edu.pitt.apollo.types.v3_0_0.SourceOfInfectionCategoryDefinition;
import edu.pitt.apollo.types.v3_0_0.SourceOfInfectionEnum;
import edu.pitt.apollo.types.v3_0_0.SpatialKernelFunctionParameters;
import edu.pitt.apollo.types.v3_0_0.TargetPopulationDefinition;
import edu.pitt.apollo.types.v3_0_0.TargetPopulationEnum;
import edu.pitt.apollo.types.v3_0_0.TargetPriorityPopulation;
import edu.pitt.apollo.types.v3_0_0.TemporalArrayDimensionsDefinition;
import edu.pitt.apollo.types.v3_0_0.TemporalTriggerDefinition;
import edu.pitt.apollo.types.v3_0_0.TimeAxisCategoryLabels;
import edu.pitt.apollo.types.v3_0_0.TimeDefinition;
import edu.pitt.apollo.types.v3_0_0.TimeScaleEnum;
import edu.pitt.apollo.types.v3_0_0.TimeSpanCategoryDefinition;
import edu.pitt.apollo.types.v3_0_0.TransmissionProbability;
import edu.pitt.apollo.types.v3_0_0.TransmissionTree;
import edu.pitt.apollo.types.v3_0_0.TravelRestrictionControlStrategy;
import edu.pitt.apollo.types.v3_0_0.Treatment;
import edu.pitt.apollo.types.v3_0_0.TreatmentContraindication;
import edu.pitt.apollo.types.v3_0_0.TreatmentEfficacy;
import edu.pitt.apollo.types.v3_0_0.TreatmentPreventableOutcomeEnum;
import edu.pitt.apollo.types.v3_0_0.TreatmentStateEnum;
import edu.pitt.apollo.types.v3_0_0.TreatmentSurveillanceCapability;
import edu.pitt.apollo.types.v3_0_0.TreatmentSurveillanceTriggerDefinition;
import edu.pitt.apollo.types.v3_0_0.TreatmentSystemLogistics;
import edu.pitt.apollo.types.v3_0_0.TriggerDefinition;
import edu.pitt.apollo.types.v3_0_0.UncertainDuration;
import edu.pitt.apollo.types.v3_0_0.UnconditionalProbabilityDistribution;
import edu.pitt.apollo.types.v3_0_0.UnitOfDistanceEnum;
import edu.pitt.apollo.types.v3_0_0.UnitOfMeasureEnum;
import edu.pitt.apollo.types.v3_0_0.UnitOfTimeEnum;
import edu.pitt.apollo.types.v3_0_0.UtilityFunction;
import edu.pitt.apollo.types.v3_0_0.Vaccination;
import edu.pitt.apollo.types.v3_0_0.VaccinationEfficacyConditionedOnTimeSinceDose;
import edu.pitt.apollo.types.v3_0_0.VaccinationEfficacyForSimulatorConfiguration;
import edu.pitt.apollo.types.v3_0_0.VaccinationEfficacyInferred;
import edu.pitt.apollo.types.v3_0_0.VaccinationEfficacyMeasured;
import edu.pitt.apollo.types.v3_0_0.VaccinationEfficacyStudy;
import edu.pitt.apollo.types.v3_0_0.Vaccine;
import edu.pitt.apollo.types.v3_0_0.VaccineContraindications;
import edu.pitt.apollo.types.v3_0_0.VariableCategoryDefinition;
import edu.pitt.apollo.types.v3_0_0.VectorControlStrategy;
import edu.pitt.apollo.types.v3_0_0.WeibullDistribution;
import edu.pitt.apollo.types.v3_0_0.WithinGroupTransmissionProbability;
import edu.pitt.apollo.types.v3_0_0.WolbachiaControlStrategy;
import edu.pitt.apollo.types.v3_0_0.WolbachiaReleaseSiteEnum;
import edu.pitt.apollo.visualizer_service_types.v3_0_0.GetVisualizerOutputResourcesResult;
import edu.pitt.apollo.visualizer_service_types.v3_0_0.RunVisualizationMessage;
import edu.pitt.apollo.visualizer_service_types.v3_0_0.RunVisualizationResult;

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
			InsertRunResult.class,
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
			GetVersionsMessage.class,
			GetVersionsResult.class,
			LibraryActionTypeEnum.class,
			LibraryItemContainer.class,
			ModifyGroupOwnershipMessage.class,
			ModifyGroupOwnershipResult.class,
			QueryMessage.class,
			QueryResult.class,
			SetLibraryItemAsNotReleasedMessage.class,
			SetLibraryItemAsNotReleasedResult.class,
			SetReleaseVersionMessage.class,
			SetReleaseVersionResult.class,
			UpdateLibraryItemContainerMessage.class,
			UpdateLibraryItemContainerResult.class,
			ApolloSoftwareTypeEnum.class,
			Authentication.class,
			ContentDataFormatEnum.class,
			ContentDataTypeEnum.class,
			FileAndURLDescription.class,
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
			GetVisualizerOutputResourcesResult.class,
			RunVisualizationMessage.class,
			RunVisualizationResult.class,
			org.eclipse.persistence.jaxb.xmlmodel.ObjectFactory.class
		};
	}
}
