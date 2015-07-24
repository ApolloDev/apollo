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
import edu.pitt.apollo.library_service_types.v3_0_2.AddLibraryItemContainerMessage;
import edu.pitt.apollo.library_service_types.v3_0_2.AddLibraryItemContainerResult;
import edu.pitt.apollo.library_service_types.v3_0_2.AddReviewerCommentMessage;
import edu.pitt.apollo.library_service_types.v3_0_2.AddReviewerCommentResult;
import edu.pitt.apollo.library_service_types.v3_0_2.CatalogEntry;
import edu.pitt.apollo.library_service_types.v3_0_2.ChangeLogEntry;
import edu.pitt.apollo.library_service_types.v3_0_2.CommentFromLibrary;
import edu.pitt.apollo.library_service_types.v3_0_2.GetChangeLogForLibraryItemsModifiedSinceDateTimeMessage;
import edu.pitt.apollo.library_service_types.v3_0_2.GetChangeLogForLibraryItemsModifiedSinceDateTimeResult;
import edu.pitt.apollo.library_service_types.v3_0_2.GetCommentsMessage;
import edu.pitt.apollo.library_service_types.v3_0_2.GetCommentsResult;
import edu.pitt.apollo.library_service_types.v3_0_2.GetDiffMessage;
import edu.pitt.apollo.library_service_types.v3_0_2.GetDiffResult;
import edu.pitt.apollo.library_service_types.v3_0_2.GetGroupsMessage;
import edu.pitt.apollo.library_service_types.v3_0_2.GetLibraryItemContainerMessage;
import edu.pitt.apollo.library_service_types.v3_0_2.GetLibraryItemContainerResult;
import edu.pitt.apollo.library_service_types.v3_0_2.GetLibraryItemURNsMessage;
import edu.pitt.apollo.library_service_types.v3_0_2.GetLibraryItemURNsResult;
import edu.pitt.apollo.library_service_types.v3_0_2.GetReleaseVersionMessage;
import edu.pitt.apollo.library_service_types.v3_0_2.GetReleaseVersionResult;
import edu.pitt.apollo.library_service_types.v3_0_2.GetVersionsMessage;
import edu.pitt.apollo.library_service_types.v3_0_2.GetVersionsResult;
import edu.pitt.apollo.library_service_types.v3_0_2.LibraryActionTypeEnum;
import edu.pitt.apollo.library_service_types.v3_0_2.LibraryItemContainer;
import edu.pitt.apollo.library_service_types.v3_0_2.ModifyGroupOwnershipMessage;
import edu.pitt.apollo.library_service_types.v3_0_2.ModifyGroupOwnershipResult;
import edu.pitt.apollo.library_service_types.v3_0_2.QueryMessage;
import edu.pitt.apollo.library_service_types.v3_0_2.QueryResult;
import edu.pitt.apollo.library_service_types.v3_0_2.SetLibraryItemAsNotReleasedMessage;
import edu.pitt.apollo.library_service_types.v3_0_2.SetLibraryItemAsNotReleasedResult;
import edu.pitt.apollo.library_service_types.v3_0_2.SetReleaseVersionMessage;
import edu.pitt.apollo.library_service_types.v3_0_2.SetReleaseVersionResult;
import edu.pitt.apollo.library_service_types.v3_0_2.UpdateLibraryItemContainerMessage;
import edu.pitt.apollo.library_service_types.v3_0_2.UpdateLibraryItemContainerResult;
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
import edu.pitt.apollo.types.v3_0_2.AaaDummyType;
import edu.pitt.apollo.types.v3_0_2.AbioticEcosystem;
import edu.pitt.apollo.types.v3_0_2.AbioticEcosystemCensus;
import edu.pitt.apollo.types.v3_0_2.AbioticEcosystemElementCensusDescription;
import edu.pitt.apollo.types.v3_0_2.AbioticEcosystemEnum;
import edu.pitt.apollo.types.v3_0_2.AgeGroupEfficacy;
import edu.pitt.apollo.types.v3_0_2.AgeRangeCategoryDefinition;
import edu.pitt.apollo.types.v3_0_2.AntiviralTreatment;
import edu.pitt.apollo.types.v3_0_2.AntiviralTreatmentEfficacy;
import edu.pitt.apollo.types.v3_0_2.ApolloIndexableItem;
import edu.pitt.apollo.types.v3_0_2.ApolloIndexableItemTypeEnum;
import edu.pitt.apollo.types.v3_0_2.ApolloPathogenCode;
import edu.pitt.apollo.types.v3_0_2.ArrayAxis;
import edu.pitt.apollo.types.v3_0_2.ArrayDimensionsDefinition;
import edu.pitt.apollo.types.v3_0_2.BaseCaseResult;
import edu.pitt.apollo.types.v3_0_2.BayesianNetwork;
import edu.pitt.apollo.types.v3_0_2.BayesianNetworkStructureType;
import edu.pitt.apollo.types.v3_0_2.BehaviorEnum;
import edu.pitt.apollo.types.v3_0_2.BioticEcosystem;
import edu.pitt.apollo.types.v3_0_2.BorderControlStrategy;
import edu.pitt.apollo.types.v3_0_2.CartesianCircleLocationDefinition;
import edu.pitt.apollo.types.v3_0_2.CaseCount;
import edu.pitt.apollo.types.v3_0_2.CaseCountArray;
import edu.pitt.apollo.types.v3_0_2.CaseCountCategory;
import edu.pitt.apollo.types.v3_0_2.CaseDefinition;
import edu.pitt.apollo.types.v3_0_2.CaseDefinitionEnum;
import edu.pitt.apollo.types.v3_0_2.CaseList;
import edu.pitt.apollo.types.v3_0_2.CaseQuarantineControlStrategy;
import edu.pitt.apollo.types.v3_0_2.CaseRecord;
import edu.pitt.apollo.types.v3_0_2.CaseRecordCategoricalVariable;
import edu.pitt.apollo.types.v3_0_2.CaseRecordCategoricalVariables;
import edu.pitt.apollo.types.v3_0_2.CaseVariableAndValue;
import edu.pitt.apollo.types.v3_0_2.Category;
import edu.pitt.apollo.types.v3_0_2.CategoryDefinition;
import edu.pitt.apollo.types.v3_0_2.Census;
import edu.pitt.apollo.types.v3_0_2.CensusData;
import edu.pitt.apollo.types.v3_0_2.ClaraDensityDependentMortalityFunction;
import edu.pitt.apollo.types.v3_0_2.CompartmentalModelPopulationAndEnvironmentCensus;
import edu.pitt.apollo.types.v3_0_2.ConditionalIndividualBehavior;
import edu.pitt.apollo.types.v3_0_2.ConditionalProbabilityDistribution;
import edu.pitt.apollo.types.v3_0_2.ConditionalProbabilityTable;
import edu.pitt.apollo.types.v3_0_2.ConditioningVariable;
import edu.pitt.apollo.types.v3_0_2.ConditioningVariableEnum;
import edu.pitt.apollo.types.v3_0_2.ContactDefinition;
import edu.pitt.apollo.types.v3_0_2.ContactDefinitionEnum;
import edu.pitt.apollo.types.v3_0_2.ContactModelForCommunity;
import edu.pitt.apollo.types.v3_0_2.ContactModelForHousehold;
import edu.pitt.apollo.types.v3_0_2.ContactModelForPlace;
import edu.pitt.apollo.types.v3_0_2.ContactModelForSetting;
import edu.pitt.apollo.types.v3_0_2.ContainerReductionControlStrategy;
import edu.pitt.apollo.types.v3_0_2.ContaminatedThingCensus;
import edu.pitt.apollo.types.v3_0_2.ContaminatedThingCensusData;
import edu.pitt.apollo.types.v3_0_2.Contamination;
import edu.pitt.apollo.types.v3_0_2.ContaminationAcquisition;
import edu.pitt.apollo.types.v3_0_2.ContinuousParametricProbabilityDistribution;
import edu.pitt.apollo.types.v3_0_2.ContinuousUniformDistribution;
import edu.pitt.apollo.types.v3_0_2.ControlStrategyTargetPopulationsAndPrioritization;
import edu.pitt.apollo.types.v3_0_2.CountType;
import edu.pitt.apollo.types.v3_0_2.DecisionAlternative;
import edu.pitt.apollo.types.v3_0_2.DecisionAnalysis;
import edu.pitt.apollo.types.v3_0_2.DevelopmentalStageEnum;
import edu.pitt.apollo.types.v3_0_2.DiscreteNonparametricProbabilityDistribution;
import edu.pitt.apollo.types.v3_0_2.DiscreteParametricProbabilityDistribution;
import edu.pitt.apollo.types.v3_0_2.DiseaseOutcomeCategoryDefinition;
import edu.pitt.apollo.types.v3_0_2.DiseaseOutcomeEnum;
import edu.pitt.apollo.types.v3_0_2.DiseaseOutcomeWithLocationDateTime;
import edu.pitt.apollo.types.v3_0_2.DiseaseOutcomeWithProbability;
import edu.pitt.apollo.types.v3_0_2.DiseaseSurveillanceCapability;
import edu.pitt.apollo.types.v3_0_2.DiseaseSurveillanceTriggerDefinition;
import edu.pitt.apollo.types.v3_0_2.Distance;
import edu.pitt.apollo.types.v3_0_2.DoubleCount;
import edu.pitt.apollo.types.v3_0_2.DrugTreatment;
import edu.pitt.apollo.types.v3_0_2.DrugTreatmentEfficacyForSimulatorConfiguration;
import edu.pitt.apollo.types.v3_0_2.Duration;
import edu.pitt.apollo.types.v3_0_2.Ecosystem;
import edu.pitt.apollo.types.v3_0_2.Epidemic;
import edu.pitt.apollo.types.v3_0_2.EpidemicPeriod;
import edu.pitt.apollo.types.v3_0_2.EpidemicPeriodBoundaryDefinitionEnum;
import edu.pitt.apollo.types.v3_0_2.ExpectedUtility;
import edu.pitt.apollo.types.v3_0_2.FixedDuration;
import edu.pitt.apollo.types.v3_0_2.FractionOfThingContaminated;
import edu.pitt.apollo.types.v3_0_2.GammaDistribution;
import edu.pitt.apollo.types.v3_0_2.GeNIEXMLType;
import edu.pitt.apollo.types.v3_0_2.GenderCategoryDefinition;
import edu.pitt.apollo.types.v3_0_2.GenderEnum;
import edu.pitt.apollo.types.v3_0_2.GesInfectiousnessParameterSet;
import edu.pitt.apollo.types.v3_0_2.GesParametersForContactAndTransmission;
import edu.pitt.apollo.types.v3_0_2.Individual;
//import edu.pitt.apollo.types.v3_0_2.IndividualHumanBehavior;
//import edu.pitt.apollo.types.v3_0_2.IndividualLifeCycle;
//import edu.pitt.apollo.types.v3_0_2.IndividualMosquitoBehavior;
//import edu.pitt.apollo.types.v3_0_2.IndividualMosquitoReproduction;
import edu.pitt.apollo.types.v3_0_2.IndividualTreatmentControlStrategy;
import edu.pitt.apollo.types.v3_0_2.IndividualTreatmentEnum;
import edu.pitt.apollo.types.v3_0_2.IndoorResidualSprayingVectorControlStrategy;
import edu.pitt.apollo.types.v3_0_2.Infection;
//import edu.pitt.apollo.types.v3_0_2.InfectionAcquisitionFromContaminatedThing;
//import edu.pitt.apollo.types.v3_0_2.InfectionAcquisitionFromInfectiousHost;
import edu.pitt.apollo.types.v3_0_2.InfectionStateEnum;
import edu.pitt.apollo.types.v3_0_2.InfectiousDisease;
import edu.pitt.apollo.types.v3_0_2.InfectiousDiseaseControlStrategy;
import edu.pitt.apollo.types.v3_0_2.InfectiousDiseaseDecisionModel;
import edu.pitt.apollo.types.v3_0_2.InfectiousDiseaseScenario;
import edu.pitt.apollo.types.v3_0_2.IntegerCount;
import edu.pitt.apollo.types.v3_0_2.Interval;
import edu.pitt.apollo.types.v3_0_2.IntervalBoundaryDefinitionEnum;
import edu.pitt.apollo.types.v3_0_2.LabTestAndResult;
import edu.pitt.apollo.types.v3_0_2.LarvicideControlStrategy;
import edu.pitt.apollo.types.v3_0_2.LibraryItem;
import edu.pitt.apollo.types.v3_0_2.LifeStageWithDurationAndMortality;
import edu.pitt.apollo.types.v3_0_2.Location;
import edu.pitt.apollo.types.v3_0_2.LocationDefinition;
import edu.pitt.apollo.types.v3_0_2.LocationPolygon;
import edu.pitt.apollo.types.v3_0_2.LogNormalDistribution;
import edu.pitt.apollo.types.v3_0_2.MeanMedianMinimumMaximum;
import edu.pitt.apollo.types.v3_0_2.MeanWithConfidenceInterval;
import edu.pitt.apollo.types.v3_0_2.MeanWithStandardDeviation;
import edu.pitt.apollo.types.v3_0_2.MortalityFunction;
import edu.pitt.apollo.types.v3_0_2.MultiGeometry;
import edu.pitt.apollo.types.v3_0_2.NamedMultiGeometry;
import edu.pitt.apollo.types.v3_0_2.NamedPrioritizationSchemeEnum;
import edu.pitt.apollo.types.v3_0_2.NonApolloParameter;
import edu.pitt.apollo.types.v3_0_2.NonparametricProbabilityDistribution;
import edu.pitt.apollo.types.v3_0_2.OccupationEnum;
import edu.pitt.apollo.types.v3_0_2.OperatorEnum;
import edu.pitt.apollo.types.v3_0_2.OvipositionSiteCensus;
import edu.pitt.apollo.types.v3_0_2.ParameterValue;
import edu.pitt.apollo.types.v3_0_2.ParametricProbabilityDistribution;
import edu.pitt.apollo.types.v3_0_2.PlaceCategoryDefinition;
import edu.pitt.apollo.types.v3_0_2.PlaceClosureControlStrategy;
import edu.pitt.apollo.types.v3_0_2.PlaceEnum;
import edu.pitt.apollo.types.v3_0_2.PlaceVisited;
import edu.pitt.apollo.types.v3_0_2.Population;
import edu.pitt.apollo.types.v3_0_2.PopulationAndEnvironmentCensus;
import edu.pitt.apollo.types.v3_0_2.PopulationCensusDescription;
import edu.pitt.apollo.types.v3_0_2.PopulationInfectionAndImmunityCensus;
import edu.pitt.apollo.types.v3_0_2.PopulationInfectionAndImmunityCensusData;
import edu.pitt.apollo.types.v3_0_2.PopulationInfectionAndImmunityCensusDataCell;
import edu.pitt.apollo.types.v3_0_2.PopulationInfectionSurvey;
import edu.pitt.apollo.types.v3_0_2.PopulationSerologySurvey;
import edu.pitt.apollo.types.v3_0_2.PopulationStratificationEnum;
import edu.pitt.apollo.types.v3_0_2.PopulationTreatmentCensus;
import edu.pitt.apollo.types.v3_0_2.PopulationTreatmentCensusData;
import edu.pitt.apollo.types.v3_0_2.PopulationTreatmentCensusDataCell;
import edu.pitt.apollo.types.v3_0_2.PreEpidemicEcosystemCensus;
import edu.pitt.apollo.types.v3_0_2.ProbabilisticParameter;
import edu.pitt.apollo.types.v3_0_2.ProbabilityDistribution;
import edu.pitt.apollo.types.v3_0_2.ProbabilityValuePair;
import edu.pitt.apollo.types.v3_0_2.PrototypicalProbabilityFunction;
import edu.pitt.apollo.types.v3_0_2.Rate;
import edu.pitt.apollo.types.v3_0_2.RealDateSpanCategoryDefinition;
import edu.pitt.apollo.types.v3_0_2.RealTimePointCategoryDefinition;
import edu.pitt.apollo.types.v3_0_2.RealTimeSpanCategoryDefinition;
import edu.pitt.apollo.types.v3_0_2.Reference;
import edu.pitt.apollo.types.v3_0_2.RelativeRiskDataSet;
import edu.pitt.apollo.types.v3_0_2.ReproductionNumber;
import edu.pitt.apollo.types.v3_0_2.RequesterIdentification;
import edu.pitt.apollo.types.v3_0_2.RingIndividualTreatmentControlStrategy;
import edu.pitt.apollo.types.v3_0_2.ScenarioCartesianOrigin;
import edu.pitt.apollo.types.v3_0_2.SeasonalityFunctionParameters;
import edu.pitt.apollo.types.v3_0_2.SensitivityAnalysis;
import edu.pitt.apollo.types.v3_0_2.SensitivityAnalysisResult;
import edu.pitt.apollo.types.v3_0_2.SimulatorTimeRange;
import edu.pitt.apollo.types.v3_0_2.SimulatorTimeSpecification;
import edu.pitt.apollo.types.v3_0_2.SimulatorTypeEnum;
import edu.pitt.apollo.types.v3_0_2.SourceOfInfectionCategoryDefinition;
import edu.pitt.apollo.types.v3_0_2.SourceOfInfectionEnum;
import edu.pitt.apollo.types.v3_0_2.SpatialKernelFunctionParameters;
import edu.pitt.apollo.types.v3_0_2.TargetPopulationDefinition;
import edu.pitt.apollo.types.v3_0_2.TargetPopulationEnum;
import edu.pitt.apollo.types.v3_0_2.TargetPriorityPopulation;
import edu.pitt.apollo.types.v3_0_2.TemporalArrayDimensionsDefinition;
import edu.pitt.apollo.types.v3_0_2.TemporalTriggerDefinition;
import edu.pitt.apollo.types.v3_0_2.TimeAxisCategoryLabels;
import edu.pitt.apollo.types.v3_0_2.TimeDefinition;
import edu.pitt.apollo.types.v3_0_2.TimeScaleEnum;
import edu.pitt.apollo.types.v3_0_2.TimeSpanCategoryDefinition;
import edu.pitt.apollo.types.v3_0_2.TransmissionProbability;
import edu.pitt.apollo.types.v3_0_2.TransmissionTree;
import edu.pitt.apollo.types.v3_0_2.TravelRestrictionControlStrategy;
import edu.pitt.apollo.types.v3_0_2.Treatment;
import edu.pitt.apollo.types.v3_0_2.TreatmentContraindication;
import edu.pitt.apollo.types.v3_0_2.TreatmentEfficacy;
import edu.pitt.apollo.types.v3_0_2.TreatmentPreventableOutcomeEnum;
import edu.pitt.apollo.types.v3_0_2.TreatmentStateEnum;
import edu.pitt.apollo.types.v3_0_2.TreatmentSurveillanceCapability;
import edu.pitt.apollo.types.v3_0_2.TreatmentSurveillanceTriggerDefinition;
import edu.pitt.apollo.types.v3_0_2.TreatmentSystemLogistics;
import edu.pitt.apollo.types.v3_0_2.TriggerDefinition;
import edu.pitt.apollo.types.v3_0_2.UncertainDuration;
import edu.pitt.apollo.types.v3_0_2.UnconditionalProbabilityDistribution;
import edu.pitt.apollo.types.v3_0_2.UnitOfDistanceEnum;
import edu.pitt.apollo.types.v3_0_2.UnitOfMeasureEnum;
import edu.pitt.apollo.types.v3_0_2.UnitOfTimeEnum;
import edu.pitt.apollo.types.v3_0_2.UtilityFunction;
import edu.pitt.apollo.types.v3_0_2.Vaccination;
import edu.pitt.apollo.types.v3_0_2.VaccinationEfficacyConditionedOnTimeSinceDose;
import edu.pitt.apollo.types.v3_0_2.VaccinationEfficacyForSimulatorConfiguration;
import edu.pitt.apollo.types.v3_0_2.VaccinationEfficacyInferred;
import edu.pitt.apollo.types.v3_0_2.VaccinationEfficacyMeasured;
import edu.pitt.apollo.types.v3_0_2.VaccinationEfficacyStudy;
import edu.pitt.apollo.types.v3_0_2.Vaccine;
import edu.pitt.apollo.types.v3_0_2.VaccineContraindications;
import edu.pitt.apollo.types.v3_0_2.VariableCategoryDefinition;
import edu.pitt.apollo.types.v3_0_2.VectorControlStrategy;
import edu.pitt.apollo.types.v3_0_2.WeibullDistribution;
import edu.pitt.apollo.types.v3_0_2.WithinGroupTransmissionProbability;
import edu.pitt.apollo.types.v3_0_2.WolbachiaControlStrategy;
import edu.pitt.apollo.types.v3_0_2.WolbachiaReleaseSiteEnum;
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
//			IndividualBehavior.class,
//			IndividualHumanBehavior.class,
//			IndividualLifeCycle.class,
//			IndividualMosquitoBehavior.class,
//			IndividualMosquitoReproduction.class,
			IndividualTreatmentControlStrategy.class,
			IndividualTreatmentEnum.class,
			IndoorResidualSprayingVectorControlStrategy.class,
			Infection.class,
//			InfectionAcquisitionFromContaminatedThing.class,
//			InfectionAcquisitionFromInfectiousHost.class,
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
