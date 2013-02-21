<?php

/*

  Copyright (C) 2011 University of Pittsburgh

  This file is part of Apollo.

  Apollo is free software: you can redistribute it and/or modify
  it under the terms of the GNU Lesser General Public License as
  published by the Free Software Foundation, either version 3 of
  the License, or (at your option) any later version.

  Apollo is distributed in the hope that it will be useful,
  but WITHOUT ANY WARRANTY; without even the implied warranty of
  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
  GNU Lesser General Public License for more details.

  You should have received a copy of the GNU Lesser General Public
  License along with Apollo.  If not, see <http://www.gnu.org/licenses/>.

 */

/**
 *
 * @author Yang Hu <yah14@pitt.edu>
 */
require_once AROOT . '/apollo/type.inc';
require_once AROOT . '/apollo/apollo.inc';

require_once AROOT . '/models/seir/constant.inc';

function seir_exec($params, $simulatorDev, $simulatorName, $simulatorVer) {
//    $classmap = array('ModelID' => 'ModelID', 'RunResultRequestObjType' => 'RunResultRequestObjType');

//    $settings = parse_ini_file(AROOT . '\config.ini');

    $apollo = new apollo();

    $client = new SoapClient($apollo->getWSDL(), array('trace' => true));

//	$id = seir_get_modelid ();
//	$runObj = $client->getDefaultSEIRModelRunRequestObj ( array ('arg0' => $id ) );

    $SimulatorConfiguration = new stdClass();
    // simulator identification
    $SimulatorIdentification = new stdClass();
    $SimulatorIdentification->simulatorDeveloper = urldecode($simulatorDev);
    $SimulatorIdentification->simulatorName = urldecode($simulatorName);
    $SimulatorIdentification->simulatorVersion = urldecode($simulatorVer);
    $SimulatorConfiguration->simulatorIdentification = $SimulatorIdentification;
//    ChromePhp::log($SimulatorIdentification->simulatorDeveloper);
    // authentication
    $Authentication = new stdClass();
    $Authentication->requesterId = $params['RequesterID'];
    $Authentication->requesterPassword = $params['RequesterPassword'];
    $SimulatorConfiguration->authentication = $Authentication;
    // simulator time specification
    $SimulatorTimeSpecification = new stdClass();
    $SimulatorTimeSpecification->timeStepUnit = $params['TimeStepUnit'];
    $SimulatorTimeSpecification->timeStepValue = $params['TimeStepValue'];
    $SimulatorTimeSpecification->runLength = $params['RunLength'];
    $SimulatorConfiguration->simulatorTimeSpecification = $SimulatorTimeSpecification;
//      ChromePhp::log($SimulatorTimeSpecification->timeStepUnit);
    // population initialization
    $SimulatedPopulation = new stdClass();
    $SimulatedPopulation->populationLocation = $params['PopulationLocation'];
    $SPopulationDiseaseState = new stdClass();
    $SPopulationDiseaseState->diseaseState = 'Susceptible';
    $SPopulationDiseaseState->popCount = $params['Susceptible'];
    $EPopulationDiseaseState = new stdClass();
    $EPopulationDiseaseState->diseaseState = 'Exposed';
    $EPopulationDiseaseState->popCount = $params['Exposed'];
    $IPopulationDiseaseState = new stdClass();
    $IPopulationDiseaseState->diseaseState = 'Infectious';
    $IPopulationDiseaseState->popCount = $params['Infectious'];
    $RPopulationDiseaseState = new stdClass();
    $RPopulationDiseaseState->diseaseState = 'Recovered';
    $RPopulationDiseaseState->popCount = $params['Recovered'];
    $SimulatedPopulation->populationDiseaseState = array($SPopulationDiseaseState, $EPopulationDiseaseState, $IPopulationDiseaseState,
        $RPopulationDiseaseState);
    $SimulatorConfiguration->populationInitialization = $SimulatedPopulation;
    // disease
    $Disease = new stdClass();
    $Disease->diseaseName = $params['DiseaseName'];
    $Disease->infectiousPeriod = $params['InfectiousPeriod'];
    $Disease->latentPeriod = $params['LatentPeriod'];
    $Disease->reproductionNumber = $params['ReproductionNumber'];
    $Disease->asymptomaticInfectionFraction = $params['AsymptomaticInfectionFraction'];
    $SimulatorConfiguration->disease = $Disease;
    // antiviral control measure
    $AntiviralControlMeasure = new stdClass();
    $AntiviralControlMeasure->antiviralCmCompliance = $params['AntiviralControlMeasureCompliance'];
    $AntiviralControlMeasure->antiviralSupplySchedule = $params['AntiviralSupplySchedule'];
    $AntiviralControlMeasure->antiviralAdminSchedule = $params['AntiviralAdministrationSchedule'];
    $AntiviralControlMeasure->antiviralEfficacy = $params['AntiviralEfficacy'];
    $AntiviralControlMeasure->antiviralEfficacyDelay = $params['AntiviralEfficacyDelay'];
    $SimulatorConfiguration->antiviralControlMeasure = $AntiviralControlMeasure;
    // vaccination control measure
    $VaccinationControlMeasure = new stdClass();
    $VaccinationControlMeasure->vaccineCmCompliance = $params['VaccineControlMeasureCompliance'];
    $VaccinationControlMeasure->vaccineSupplySchedule = $params['VaccinationSupplySchedule'];
    $VaccinationControlMeasure->vaccinationAdminSchedule = $params['VaccinationAdministrationSchedule'];
    $VaccinationControlMeasure->vaccineEfficacy = $params['VaccineEfficacy'];
    $VaccinationControlMeasure->vaccineEfficacyDelay = $params['VaccineEfficacyDelay'];
    $SimulatorConfiguration->vaccinationControlMeasure = $VaccinationControlMeasure;

//    $epidemic_model_input->vaccination_control_measure = $vaccination_control_measure;
//    $epidemic_model_input = new stdClass();
//    // simulator configuration
//    $simulator_configuration = new stdClass();
//    $simulator_configuration->time_step_unit = $params['TimeStepUnit'];
//    $simulator_configuration->time_step_value = $params['TimeStepValue'];
//    $simulator_configuration->run_length = $params['RunLength'];
//    $simulator_configuration->pop_count = $params['Susceptible'] + $params['Exposed'] + $params['Infectious'] + $params['Recovered'];
////    $simulator_configuration->population_location = $jurisdiction;
//    $epidemic_model_input->simulator_configuration = $simulator_configuration;
//    // simulator identification
//    $simulator_identification = new stdClass();
//    $simulator_identification->simulator_developer = 'dev';
//    $simulator_identification->simulator_name = 'name';
//    $simulator_identification->simulator_version = 'version';
//    $epidemic_model_input->simulator_identification = $simulator_identification;
//    // disease dynamics
//    $disease_dynamics = new stdClass();
//    $disease_dynamics->infectious_period = $params['InfectiousPeriod'];
//    $disease_dynamics->latent_period = $params['LatentPeriod'];
//    $disease_dynamics->reproduction_number = $params['ReproductionNumber'];
//    $disease_dynamics->asymptomatic_infection_fraction = $params['AsymptomaticInfectionFraction'];
//    $simulated_population = array('Susceptible', 'Exposed', 'Infectious', 'Recovered');
//    $disease_dynamics->simulated_population = $simulated_population;
//    $pop_count = array($params['Susceptible'], $params['Exposed'], $params['Infectious'], $params['Recovered']);
//    $disease_dynamics->pop_count = $pop_count;
//    $epidemic_model_input->disease_dynamics = $disease_dynamics;
//    // vaccine control measure
//    $vaccination_control_measure = new stdClass();
//    $vaccination_control_measure->vaccine_cm_compliance = $params['VaccineControlMeasureCompliance'];
//    $vaccination_control_measure->vaccine_efficacy = $params['VaccineEfficacy'];
//    $vaccination_control_measure->vaccine_efficacy_delay = $params['VaccineEfficacyDelay'];
//    $vaccination_control_measure->vaccine_supply_schedule = $params['VaccinationSupplySchedule'];
//    $vaccination_control_measure->vaccination_admin_schedule = $params['VaccinationAdministrationSchedule'];
//    $epidemic_model_input->vaccination_control_measure = $vaccination_control_measure;
//    // antiviral control measure
    //**************************** OLD OBJECTS *******************************************
    //Fill in the correct parameters
//    $runObj = $runObj->return;
//    $runObj->ModelID = $id;
//    $runObj->Parameters->registered->Username = "";
//    $runObj->Parameters->registered->Password = "";
    //fillinRecognized
//    $recognized = $runObj->Parameters->registered;
//    $recognized->TemporalGranularity = $params['TemporalGranularity'];
//    $recognized->TemporalIncrement = $params['TemporalIncrement'];
//    $recognized->RunLength = $params['RunLength'];
//    $recognized->AverageDurationOfInfectiousness = $params['AverageDurationOfInfectiousness'];
//    $recognized->AverageLatentPeriod = $params['AverageLatentPeriod'];
//    $recognized->PercentCompliant = $params['PercentCompliant'];
//    $recognized->r0 = $params['r0'];
//    $recognized->VaccinationSchedule->value = $params['VaccinationSchedule'];
    //InitialCompartmentSizes
//    $recognized->InitialCompartmentSizes->Susceptible->Number = $params['Susceptible'];
//    $recognized->InitialCompartmentSizes->Exposed = $params['Exposed'];
//    $recognized->InitialCompartmentSizes->Infectious = $params['Infectious'];
//    $recognized->InitialCompartmentSizes->Recovered = $params['Recovered'];
    //end InitialCompartmentSizes
    //$temporalGranularity = $runObj->Parameters->Recognized->TemporalGranularity;
    //************************************************************************************
    $apolloResponse = $client->runSimulation(array('simulatorConfiguration' => $SimulatorConfiguration));
//    $apolloResponse = $apolloResponse->return;
//    if ($apolloResponse->id == - 1) { //cache hit
//        $apolloResponse = $apolloResponse->cachedResult;
//    } else {
//    $runId = '';
//    if ($SimulatorIdentification->simulatorName == 'FRED') {
//        $runId = 'UPitt,PSC,CMU_FRED_2.0.1_231742';
//    } else {
//        $runId = 'upitt_seir_1.0_19';
//    }
    $runId = $apolloResponse->runId;
//    $runId = 'UPitt,PSC,CMU_FRED_2.0.1_231631';
//        $runId = '5';
//    ChromePhp::log($runId);
////    ChromePhp::log($params['TimeStepUnit']);
//
//    $ServiceRecord = new stdClass();
//    $ServiceRecord->simulatorIdentification = $SimulatorIdentification;


//
//
//    while (true) {
//        $status = $client->getRunStatus(array('runId' => $runId, 'serviceRecord' => $ServiceRecord));
////        ChromePhp::log($status->runStatus->status);
////        ChromePhp::log($runId);
////        ChromePhp::log($runId->runStatus->message);
////        ChromePhp::log($runId->runStatus->status);
////        $status = $status->return;
//        if (strcmp($status->runStatus->status, 'completed') == 0) {
////            $rrro = new getResult($runId, '', '');
////            $apolloResponse = $client->getResult(array('arg0' => $rrro));
////            $apolloResponse = $apolloResponse->return;
//            break;
//        } else {
//            set_time_limit(20);
//            ChromePhp::log($status->runStatus->status);
//            ChromePhp::log($status->runStatus->message);
//            sleep(5); //sleep for five seconds
//        }
//    }
//    
    // run visualizer
//    $VisualizerConfiguration = new stdClass();
//    // visualizer identification
//    $VisualizerIdentification = new stdClass();
//    $VisualizerIdentification->visualizerDeveloper = 'nick';
//    $VisualizerIdentification->visualizerName = 'viztest';
//    $VisualizerIdentification->visualizerVersion = '1.0';
//    $VisualizerConfiguration->visualizerIdentification = $VisualizerIdentification;
//    // authentication
//    $VisualizerConfiguration->authentication = $Authentication;
//    // visualization options
//    $VisualizationOptions = new stdClass();
//    $VisualizationOptions->runId = $runId;
//    $VisualizationOptions->location = '';
//    $VisualizationOptions->outputFormat = '';
//    $VisualizerConfiguration->visualizationOptions = $VisualizationOptions;
//
//    // run the visualizer
//    $apolloResponse = $client->runVisualization(array('visualizerConfiguration' => $VisualizerConfiguration));
//
//    $VisualizerOutputResourceList = $apolloResponse->visualizerOutputResource;
//    $diseaseStatesURL = '';
//    $incidenceURL = '';
//
//    foreach ($VisualizerOutputResourceList as $visOutResource) {
//
//        if ($visOutResource->description == 'Disease states') {
//            $diseaseStatesURL = $visOutResource->URL;
//        } else if ($visOutResource->description == 'Incidence') {
//            $incidenceURL = $visOutResource->URL;
//        }
//    }
//
//    ChromePhp::log($diseaseStatesURL);
//    ChromePhp::log($incidenceURL);
//
//    // create run status object
//    $ServiceRecord = new stdClass();
//    $ServiceRecord->visualizerIdentification = $VisualizerIdentification;
//
//    while (true) {
//        $status = $client->getRunStatus(array('runId' => $runId, 'serviceRecord' => $ServiceRecord));
////        ChromePhp::log($runId);
////        ChromePhp::log($runId->runStatus->message);
////        ChromePhp::log($runId->runStatus->status);
////        $status = $status->return;
//        if (strcmp($status->runStatus->status, 'completed') == 0) {
////            $rrro = new getResult($runId, '', '');
////            $apolloResponse = $client->getResult(array('arg0' => $rrro));
////            $apolloResponse = $apolloResponse->return;
//            break;
//        } else {
////            set_time_limit(20);
//            ChromePhp::log($status->runStatus->message);
//            ChromePhp::log($status->runStatus->status);
//            sleep(5); //sleep for 10 seconds
//        }
//    }


//
//    $runID = 'UPitt,PSC,CMU_FRED_2.0.1_230125';
//    // get the output
//    define('CLIENT_LONG_PASSWORD', 1);
//    $mysqli = new mysqli($settings['host'], $settings['user'], $settings['password'], $settings['database']);
//    $result = $mysqli->query("select time_step, disease_state, pop_count "
//            . "from SIMULATOR_OUTPUT "
//            . "where disease_state = 'susceptible' "
//            . "or disease_state = 'exposed' "
//            . "or disease_state = 'infectous' "
//            . "or disease_state = 'recovered' "
//            . "group by time_step, disease_state");
//    if (!$result) {
//        throw new Exception("Database Error [{$mysqli->error}] {$mysqli->error}");
//    }
//
//    $susceptible = array();
//    $exposed = array();
//    $infectious = array();
//    $recovered = array();
//
//    $result->data_seek(0);
//    while ($row = $result->fetch_assoc()) {
//        $exposed_name = $row['disease_state'];
//        if ($exposed_name != 'exposed') {
//            trigger_error("Missing exposed count for time step", E_USER_ERROR);
//        }
//        $exposed[] = floatval($row['pop_count']);
//
//        $row = $result->fetch_assoc();
//        $infectious_name = $row['disease_state'];
//        if ($infectious_name != 'infectous') {
//            trigger_error("Missing infectious count for time step", E_USER_ERROR);
//        }
//        $infectious[] = floatval($row['pop_count']);
//
//        $row = $result->fetch_assoc();
//        $recovered_name = $row['disease_state'];
//        if ($recovered_name != 'recovered') {
//            trigger_error("Missing recovered count for time step", E_USER_ERROR);
//        }
//        $recovered[] = floatval($row['pop_count']);
//
//        $row = $result->fetch_assoc();
//        $susceptible_name = $row['disease_state'];
//        if ($susceptible_name != 'susceptible') {
//            trigger_error("Missing susceptible count for time step", E_USER_ERROR);
//        }
//        $susceptible[] = floatval($row['pop_count']);
//    }
//
//    $newly_exposed = array();
//    $result = $mysqli->query("select time_step, sum(pop_count) as population from SIMULATOR_OUTPUT where disease_state = 'newly_exposed' group by time_step");
//    if (!$result) {
//        throw new Exception("Database Error [{$mysqli->error}] {$mysqli->error}");
//    }
//
//    $result->data_seek(0);
//    while ($row = $result->fetch_assoc()) {
//
//        $newly_exposed[] = floatval($row['population']);
//    }
//    		$curves ['Susceptible'] = $apolloResponse->return->results->Recognized->EpiCurve->Susceptible->value;
//    		$curves ['Exposed'] = $apolloResponse->return->results->Recognized->EpiCurve->Exposed->value;
//    		$curves ['Infectious'] = $apolloResponse->return->results->Recognized->EpiCurve->Infectious->value;
//    		$curves ['Recovered'] = $apolloResponse->return->results->Recognized->EpiCurve->Recovered->value;
//    $curves ['Incidence'] = $apolloResponse->results->registered->IncidenceCurve->value;
//    $curves ['Susceptible'] = $susceptible;
//    $curves ['Exposed'] = $exposed;
//    $curves ['Infectious'] = $infectious;
//    $curves ['Recovered'] = $recovered;
//    $curves['Newly Exposed'] = $newly_exposed;

//    $urls['disease_states'] = $diseaseStatesURL;
//    $urls['incidence'] = $incidenceURL;

      $ret['runId'] = $runId;
      $ret['simulatorDeveloper'] = $SimulatorIdentification->simulatorDeveloper;
      $ret['simulatorName'] = $SimulatorIdentification->simulatorName;
      $ret['simulatorVersion'] = $SimulatorIdentification->simulatorVersion;
    
    return $ret;
}

?>