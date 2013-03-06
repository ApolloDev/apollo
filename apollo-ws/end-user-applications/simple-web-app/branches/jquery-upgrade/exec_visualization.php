<?php

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

define('AROOT', getcwd());
require_once AROOT . '/apollo/apollo.inc';

$ret = new Response();

$apollo = new apollo();

$client = new SoapClient($apollo->getWSDL(), array('trace' => true));
$runId = $_GET ['runId'];
$vizDev = $_GET['vizDev'];
$vizName = $_GET['vizName'];
$vizVer = $_GET['vizVer'];

//ChromePhp::log($vizDev);
//ChromePhp::log($vizName);
//ChromePhp::log($vizVer);

// run visualizer
$VisualizerConfiguration = new stdClass();
// authentication
$Authentication = new stdClass();
$Authentication->requesterId = '';
$Authentication->requesterPassword = '';
$VisualizerConfiguration->authentication = $Authentication;
// visualizer identification
$VisualizerIdentification = new stdClass();
$VisualizerIdentification->visualizerDeveloper = $vizDev;
$VisualizerIdentification->visualizerName = $vizName;
$VisualizerIdentification->visualizerVersion = $vizVer;
$VisualizerConfiguration->visualizerIdentification = $VisualizerIdentification;
// authentication
$VisualizerConfiguration->authentication = $Authentication;
// visualization options
$VisualizationOptions = new stdClass();
$VisualizationOptions->runId = $runId;
$VisualizationOptions->location = '42003';
$VisualizationOptions->outputFormat = 'ogg';
$VisualizerConfiguration->visualizationOptions = $VisualizationOptions;

// run the visualizer
$apolloResponse = $client->runVisualization(array('visualizerConfiguration' => $VisualizerConfiguration));

//ChromePhp::log($apolloResponse);

$VisualizerOutputResourceList = $apolloResponse->visualizerOutputResource;
$diseaseStatesURL = '';
$incidenceURL = '';

$urls = array();

if (strpos($runId, ':') !== false || $vizName == 'GAIA') {
    $urls[$VisualizerOutputResourceList->description] = $VisualizerOutputResourceList->URL;
} else {

    foreach ($VisualizerOutputResourceList as $visOutResource) {

//    ChromePhp::log(json_encode($visOutResource));
    $urls[$visOutResource->description] = $visOutResource->URL;

//        if ($visOutResource->description == 'Disease states') {
//            $diseaseStatesURL = $visOutResource->URL;
//        } else if ($visOutResource->description == 'Incidence') {
//            $incidenceURL = $visOutResource->URL;
//        }
    }
}

$result['urls'] = $urls;
$result['runId'] = $runId;
$result['visualizerDeveloper'] = $VisualizerIdentification->visualizerDeveloper;
$result['visualizerName'] = $VisualizerIdentification->visualizerName;
$result['visualizerVersion'] = $VisualizerIdentification->visualizerVersion;
//$result['disease_states_url'] = $diseaseStatesURL;
//$result['incidence_url'] = $incidenceURL;

$ret->data = $result;

echo json_encode($ret);
?>
