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

//require_once AROOT . '/simulator/constant.inc';

abstract class ControlMeasure {

    public $description;

}

class AntiviralTreatmentControlMeasure extends ControlMeasure {
    
}

class VaccinationControlMeasure extends ControlMeasure {
    
}

class SchoolClosureControlMeasure extends ControlMeasure {
    
}

//class

function exec_simulator($params, $simulatorDev, $simulatorName, $simulatorVer) {
//    $classmap = array('ModelID' => 'ModelID', 'RunResultRequestObjType' => 'RunResultRequestObjType');
//    $settings = parse_ini_file(AROOT . '\config.ini');

    $apollo = new apollo();

    $classMap = array('ControlMeasure' => 'ControlMeasure',
        'AntiviralTreatmentControlMeasure' => 'AntiviralTreatmentControlMeasure',
        'VaccinationControlMeasure' => 'VaccinationControlMeasure',
        'SchoolClosureControlMeasure' => 'SchoolClosureControlMeasure');
    $client = new SoapClient($apollo->getWSDL(), array('trace' => true, 'classmap' => $classMap));

//	$id = seir_get_modelid ();
//	$runObj = $client->getDefaultSEIRModelRunRequestObj ( array ('arg0' => $id ) );


    $SimulatorConfiguration = new stdClass();



    // simulator identification
    $SoftwareIdentification = new stdClass();
    $SoftwareIdentification->softwareDeveloper = urldecode($simulatorDev);
    $SoftwareIdentification->softwareName = urldecode($simulatorName);
    $SoftwareIdentification->softwareVersion = urldecode($simulatorVer);
    $SoftwareIdentification->softwareType = 'simulator';

    $SimulatorConfiguration->simulatorIdentification = $SoftwareIdentification;
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
    $totalPopulation = 1218494.0;
    $SimulatedPopulation = new stdClass();
    $SimulatedPopulation->populationLocation = $params['PopulationLocation'];
    $SPopulationDiseaseState = new stdClass();
    $SPopulationDiseaseState->diseaseState = 'Susceptible';
    $SPopulationDiseaseState->fractionOfPopulation = $params['Susceptible'] / $totalPopulation;
    $EPopulationDiseaseState = new stdClass();
    $EPopulationDiseaseState->diseaseState = 'Exposed';
    $EPopulationDiseaseState->fractionOfPopulation = $params['Exposed'] / $totalPopulation;
    $IPopulationDiseaseState = new stdClass();
    $IPopulationDiseaseState->diseaseState = 'Infectious';
    $IPopulationDiseaseState->fractionOfPopulation = $params['Infectious'] / $totalPopulation;
    $RPopulationDiseaseState = new stdClass();
    $RPopulationDiseaseState->diseaseState = 'Recovered';
    $RPopulationDiseaseState->fractionOfPopulation = $params['Recovered'] / $totalPopulation;
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
//    $AntiviralControlMeasure = new stdClass();
//    ce = $params['AntiviralControlMeasureCompliance'];
//    $pvalue = array();
//    for ($i = 0; $i < 45; $i++) {
//        $pvalue[] = '0.0';
//    }

    $useAntiviralControlMeasure = $params['AntiviralControlMeasure'];
    $useVaccinationControlMeasure = $params['VaccineControlMeasure'];
    $useSchoolClosure = $params['SchoolClosureControlMeasure'];
    $useSchoolReactiveCm = $params['SchoolClosureReactiveTriggerControlMeasure'];
    $useSchoolFixedCm = $params['SchoolClosureFixedStartTimeControlMeasure'];
    $useFixedStartTimeControlMeasures = false;

    $FixedStartTimeControlMeasures = array();
    $antiviralFixedStartTimeControlMeasure = new stdClass();
    $antiviralTreatmentControlMeasure = new AntiviralTreatmentControlMeasure();

    if ($useAntiviralControlMeasure == 'true') {

        $useFixedStartTimeControlMeasures = true;
        $antiviralTreatmentControlMeasure->antiviralSupplySchedule = $params['AntiviralSupplySchedule'];
        $antiviralTreatmentControlMeasure->antiviralTreatmentAdministrationCapacity = $params['AntiviralAdministrationSchedule'];
        $AntiviralTreatment = new stdClass();
        $TreatmentEfficacyOverTime = new stdClass();
        $AntiviralEfficacy = array();
        $AntiviralEfficacy[] = $params['AntiviralTreatmentEfficacy'];
        $TreatmentEfficacyOverTime->efficacyValues = $AntiviralEfficacy;
        $AntiviralTreatment->antiviralTreatmentEfficacy = $TreatmentEfficacyOverTime;
        $AntiviralTreatment->description = 'treatment';
        $AntiviralTreatment->antiviralId = 'id';
        $AntiviralTreatment->hostOrganism = 'organism';
        $AntiviralTreatment->numDosesInTreatmentCourse = 1;
        $antiviralTreatmentControlMeasure->antiviralTreatment = $AntiviralTreatment;
        $antiviralTreatmentControlMeasure->controlMeasureCompliance = $params['AntiviralControlMeasureCompliance'];
        $antiviralTreatmentControlMeasure->description = 'antiviral';
        $antiviralTreatmentControlMeasure->controlMeasureResponseDelay = 0.0;
        $antiviralTreatmentControlMeasure->treatmentReactiveEndPointFraction = 1.0;

        $TargetPriorityPopulation = new stdClass();
        $TargetPriorityPopulation->label = 'newly sick';
        $TargetPriorityPopulation->fractionOfTargetPopulationToPrioritize = 1.0;
        $TargetPriorityPopulation->priority = 1;
        
        $PopulationStrataDefinition = new stdClass();
        $DiseaseState = 'newly_sick';
        $PopulationStrataDefinition->diseaseStates = $DiseaseState;
        $TargetPriorityPopulation->targetPopulationDefinition = $PopulationStrataDefinition;
        
        $antiviralTreatmentControlMeasure->controlMeasureTargetPopulationsAndPrioritization = $TargetPriorityPopulation;

        $AntiviralTreatmentControlMeasureVar = new SoapVar($antiviralTreatmentControlMeasure,
                        SOAP_ENC_OBJECT,
                        get_class($antiviralTreatmentControlMeasure),
                        'http://types.apollo.pitt.edu/');

//    $AntiviralTreatmentControlMeasureVar->description = 'antiviral';

        $antiviralFixedStartTimeControlMeasure->controlMeasure = $AntiviralTreatmentControlMeasureVar;
        $antiviralFixedStartTime = new stdClass();
        $antiviralFixedStartTime->fixedStartTime = $params['AntiviralTreatmentFixedStartTime'];
        $antiviralFixedStartTimeControlMeasure->controlMeasureFixedStartTime = $antiviralFixedStartTime;

        $FixedStartTimeControlMeasures[] = $antiviralFixedStartTimeControlMeasure;
    }

    // vaccination control measure
    $vaccFixedStartTimeControlMeasure = new stdClass();
    $vaccinationControlMeasure = new VaccinationControlMeasure();

    if ($useVaccinationControlMeasure == 'true') {

        $useFixedStartTimeControlMeasures = true;
        $vaccinationControlMeasure->vaccineSupplySchedule = $params['VaccinationSupplySchedule'];
        $vaccinationControlMeasure->vaccinationAdministrationCapacity = $params['VaccinationAdministrationSchedule'];
        $Vaccination = new stdClass();
        $TreatmentEfficacyOverTime = new stdClass();
        $VaccinationEfficacy = array();
        $VaccinationEfficacy[] = $params['VaccineEfficacy'];
        $TreatmentEfficacyOverTime->efficacyValues = $VaccinationEfficacy;
        $Vaccination->vaccinationEfficacy = $TreatmentEfficacyOverTime;
        $Vaccination->description = 'treatment';
        $Vaccination->vaccineId = 'id';
        $Vaccination->hostOrganism = 'organism';
        $Vaccination->numDosesInVaccinationCourse = $params['NumberofDosesinVaccinationCourse'];
        $vaccinationControlMeasure->vaccination = $Vaccination;
        $vaccinationControlMeasure->controlMeasureCompliance = $params['VaccinationControlMeasureCompliance'];
        $vaccinationControlMeasure->description = 'vaccination';
        $vaccinationControlMeasure->controlMeasureResponseDelay = $params['VaccinationResponseDelay'];
        $vaccinationControlMeasure->vaccinationReactiveEndPointFraction = $params['VaccinationReactiveEndPointFraction'];
        $vaccinationControlMeasure->controlMeasureNamedPrioritizationScheme = $params['VaccinationNamedPrioritizationScheme'];

        $VaccinationControlMeasureVar = new SoapVar($vaccinationControlMeasure,
                        SOAP_ENC_OBJECT,
                        get_class($vaccinationControlMeasure),
                        'http://types.apollo.pitt.edu/');


        $vaccFixedStartTimeControlMeasure->controlMeasure = $VaccinationControlMeasureVar;
        $vaccFixedStartTime = new stdClass();
        $vaccFixedStartTime->fixedStartTime = $params['VaccinationFixedStartTime'];
        $vaccFixedStartTimeControlMeasure->controlMeasureFixedStartTime = $vaccFixedStartTime;

        $FixedStartTimeControlMeasures[] = $vaccFixedStartTimeControlMeasure;
    }

    $ControlMeasures = new stdClass();

    if ($useSchoolClosure) {
        if ($useSchoolReactiveCm) {

            $ReactiveControlMeasures = array();
            $ReactiveControlMeasure = new stdClass();
            $schoolClosureControlMeasure = new SchoolClosureControlMeasure();
            $schoolClosureControlMeasure->schoolClosureDuration = $params['SchoolClosureReactiveDuration'];
            $schoolClosureControlMeasure->schoolClosureTargetFacilities = $params['SchoolClosureReactiveTargetFacilities'];
            $schoolClosureControlMeasure->controlMeasureResponseDelay = $params['SchoolClosureReactiveResponseDelay'];
            $schoolClosureControlMeasure->controlMeasureCompliance = $params['SchoolClosureReactiveCompliance'];
            $schoolClosureControlMeasure->controlMeasureNamedPrioritizationScheme = 'none';
            $schoolClosureControlMeasure->description = 'school closure';

            $SchoolClosureControlMeasureVar = new SoapVar($schoolClosureControlMeasure,
                            SOAP_ENC_OBJECT,
                            get_class($schoolClosureControlMeasure),
                            'http://types.apollo.pitt.edu/');

            $ReactiveTriggersDefinition = new stdClass();
            $ReactiveTriggersDefinition->reactiveControlMeasureTest = $params['ReactiveControlMeasureTest'];
            $ReactiveTriggersDefinition->reactiveControlMeasureThreshold = $params['ReactiveControlMeasureThreshold'];
            $ReactiveTriggersDefinition->ascertainmentFraction = $params['AscertainmentFraction'];
            $ReactiveTriggersDefinition->ascertainmentDelay = $params['AscertainmentDelay'];

            $ReactiveControlMeasure->controlMeasure = $SchoolClosureControlMeasureVar;
            $ReactiveControlMeasure->controlMeasureReactiveTriggersDefinition = $ReactiveTriggersDefinition;
            $ReactiveControlMeasures[] = $ReactiveControlMeasure;

            $ControlMeasures->reactiveControlMeasures = $ReactiveControlMeasures;
        } else if ($useSchoolFixedCm) {

            $useFixedStartTimeControlMeasures = true;
            $schoolFixedStartTimeControlMeasure = new stdClass();

            $schoolClosureControlMeasure = new SchoolClosureControlMeasure();
            $schoolClosureControlMeasure->schoolClosureDuration = $params['SchoolClosureFixedDuration'];
            $schoolClosureControlMeasure->schoolClosureTargetFacilities = $params['SchoolClosureFixedTargetFacilities'];
            $schoolClosureControlMeasure->controlMeasureResponseDelay = $params['SchoolClosureFixedResponseDelay'];
            $schoolClosureControlMeasure->controlMeasureCompliance = $params['SchoolClosureFixedCompliance'];
            $schoolClosureControlMeasure->controlMeasureNamedPrioritizationScheme = 'none';
            $schoolClosureControlMeasure->description = 'school closure';

            $SchoolClosureControlMeasureVar = new SoapVar($schoolClosureControlMeasure,
                            SOAP_ENC_OBJECT,
                            get_class($schoolClosureControlMeasure),
                            'http://types.apollo.pitt.edu/');

            $schoolFixedStartTimeControlMeasure->controlMeasure = $SchoolClosureControlMeasureVar;
            $vaccFixedStartTime = new stdClass();
            $vaccFixedStartTime->fixedStartTime = $params['SchoolClosureFixedStartTime'];
            $schoolFixedStartTimeControlMeasure->controlMeasureFixedStartTime = $vaccFixedStartTime;

            $FixedStartTimeControlMeasures[] = $schoolFixedStartTimeControlMeasure;
        }
    }

    if ($useFixedStartTimeControlMeasures) {
        $ControlMeasures->fixedStartTimeControlMeasures = $FixedStartTimeControlMeasures;
    }

    // Assign the control measures to the simulator configuration
    $SimulatorConfiguration->controlMeasures = $ControlMeasures;

    $apolloResponse = $client->runSimulation(array('simulatorConfiguration' => $SimulatorConfiguration));

    $runId = $apolloResponse->runId;
//    ChromePhp::log($client->__getLastRequest());
//    if ($useVaccinationControlMeasure == 'true') {
//
//        // do no vacc run
//        // create antiviral object again
//        $FixedStartTimeControlMeasures = array();
//
//        $FixedStartTimeControlMeasure = new stdClass();
//        $antiviralTreatmentControlMeasure = new AntiviralTreatmentControlMeasure();
//        $antiviralTreatmentControlMeasure->antiviralSupplySchedule = $params['AntiviralSupplySchedule'];
//        $antiviralTreatmentControlMeasure->antiviralTreatmentAdministrationCapacity = $params['AntiviralAdministrationSchedule'];
//        $AntiviralTreatment = new stdClass();
//        $TreatmentEfficacyOverTime = new stdClass();
//        $AntiviralEfficacy = array();
//        $AntiviralEfficacy[] = $params['AntiviralTreatmentEfficacy'];
//        $TreatmentEfficacyOverTime->efficacyValues = $AntiviralEfficacy;
//        $AntiviralTreatment->antiviralTreatmentEfficacy = $TreatmentEfficacyOverTime;
//        $AntiviralTreatment->description = 'treatment';
//        $AntiviralTreatment->antiviralId = 'id';
//        $AntiviralTreatment->hostOrganism = 'organism';
//        $AntiviralTreatment->numDosesInTreatmentCourse = 1;
//        $antiviralTreatmentControlMeasure->antiviralTreatment = $AntiviralTreatment;
//        $antiviralTreatmentControlMeasure->controlMeasureCompliance = $params['AntiviralControlMeasureCompliance'];
//        $antiviralTreatmentControlMeasure->description = 'antiviral';
//        $antiviralTreatmentControlMeasure->controlMeasureResponseDelay = 0.0;
//        $antiviralTreatmentControlMeasure->treatmentReactiveEndPointFraction = 1.0;
//
//        $AntiviralTreatmentControlMeasureVar = new SoapVar($antiviralTreatmentControlMeasure,
//                        SOAP_ENC_OBJECT,
//                        get_class($antiviralTreatmentControlMeasure),
//                        'http://types.apollo.pitt.edu/');
//
//        $FixedStartTimeControlMeasure->controlMeasure = $AntiviralTreatmentControlMeasureVar;
//        $FixedStartTimeControlMeasure->controlMeasureFixedStartTime = 0;
//
//        $FixedStartTimeControlMeasures[] = $FixedStartTimeControlMeasure;
//
//        // vaccination control measure
//        $FixedStartTimeControlMeasure = new stdClass();
//        $vaccinationControlMeasure = new VaccinationControlMeasure();
//        $vaccinationControlMeasure->vaccineSupplySchedule = $params['VaccinationSupplySchedule'];
//        $vaccinationControlMeasure->vaccinationAdministrationCapacity = $params['VaccinationAdministrationSchedule'];
//        $Vaccination = new stdClass();
//        $TreatmentEfficacyOverTime = new stdClass();
//        $VaccinationEfficacy = array();
//        $VaccinationEfficacy[] = 0.0;
//        $TreatmentEfficacyOverTime->efficacyValues = $VaccinationEfficacy;
//        $Vaccination->vaccinationEfficacy = $TreatmentEfficacyOverTime;
//        $Vaccination->description = 'treatment';
//        $Vaccination->vaccineId = 'id';
//        $Vaccination->hostOrganism = 'organism';
//        $Vaccination->numDosesInVaccinationCourse = 1;
//        $vaccinationControlMeasure->vaccination = $Vaccination;
//        $vaccinationControlMeasure->controlMeasureCompliance = 0.0;
//        $vaccinationControlMeasure->description = 'vaccination';
//        $vaccinationControlMeasure->controlMeasureResponseDelay = 0.0;
//        $vaccinationControlMeasure->vaccinationReactiveEndPointFraction = 1.0;
//
//        $VaccinationControlMeasureVar = new SoapVar($vaccinationControlMeasure,
//                        SOAP_ENC_OBJECT,
//                        get_class($vaccinationControlMeasure),
//                        'http://types.apollo.pitt.edu/');
//
//        $FixedStartTimeControlMeasure->controlMeasure = $VaccinationControlMeasureVar;
//        $FixedStartTimeControlMeasure->controlMeasureFixedStartTime = 0;
//
//        $FixedStartTimeControlMeasures[] = $FixedStartTimeControlMeasure;
//
//        $ControlMeasures = new stdClass();
//        $ControlMeasures->fixedStartTimeControlMeasures = $FixedStartTimeControlMeasures;
//        $SimulatorConfiguration->controlMeasures = $ControlMeasures;
//
//        $apolloResponse = $client->runSimulation(array('simulatorConfiguration' => $SimulatorConfiguration));
//        $runId = $apolloResponse->runId . ';' . $runId; // put no vacc runId first 
//    }
//    $runId = '';
//    if (urldecode($simulatorName) == 'FRED') {
//        $runId = 'UPitt,PSC,CMU_FRED_2.0.1_unicron.psc.edu1364583570';
//    } else {
//        $runId = 'UPitt_SEIR_1.0_329;UPitt_SEIR_1.0_328';
//    }

    $ret['runId'] = $runId;
    $ret['simulatorDeveloper'] = $SoftwareIdentification->softwareDeveloper;
    $ret['simulatorName'] = $SoftwareIdentification->softwareName;
    $ret['simulatorVersion'] = $SoftwareIdentification->softwareVersion;

    return $ret;
}

?>