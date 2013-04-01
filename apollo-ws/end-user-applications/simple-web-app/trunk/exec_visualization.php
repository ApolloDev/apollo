<?php

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

define('AROOT', getcwd());
require_once AROOT . '/apollo/apollo.inc';
//require_once AROOT . '/ChromePhp/ChromePhp.php';
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
$SoftwareIdentification = new stdClass();
$SoftwareIdentification->softwareDeveloper = $vizDev;
$SoftwareIdentification->softwareName = $vizName;
$SoftwareIdentification->softwareVersion = $vizVer;
$SoftwareIdentification->softwareType = 'visualizer'; // since we are calling a visualizer here
$VisualizerConfiguration->visualizerIdentification = $SoftwareIdentification;
// authentication
$VisualizerConfiguration->authentication = $Authentication;
// visualization options
$VisualizationOptions = new stdClass();
$VisualizationOptions->runId = $runId;
$VisualizationOptions->location = '42003';
$VisualizationOptions->outputFormat = 'ogg';
$VisualizerConfiguration->visualizationOptions = $VisualizationOptions;

// if runId is a list (separated by ;), the first runId is no vaccination,
// and the second is vaccination
// run the visualizer
//if ($vizName != 'GAIA') {
    $apolloResponse = $client->runVisualization(array('visualizerConfiguration' => $VisualizerConfiguration));
//}

$UrlOutputResourceList = null;


//if ($vizName != 'GAIA') {
//ChromePhp::log($apolloResponse);
    $UrlOutputResourceList = $apolloResponse->visualizerResult->visualizerOutputResource;
    $diseaseStatesURL = '';
    $incidenceURL = '';
//} else {
//    $diseaseStatesURL = '';
//    $incidenceURL = '';
//}

$urls = array();

if (strpos($runId, ':') !== false || $vizName == 'GAIA') {
//    if ($vizName == 'GAIA') {
//        $urls['GAIA animation of Allegheny County'] = 'http://warhol-fred.psc.edu/GAIA/gaia.output.1363297461.ogg';
//    } else {
        $urls[$UrlOutputResourceList->description] = $UrlOutputResourceList->URL;
//    }
//    $urls[$VisualizerOutputResourceList->description] = 'http://warhol-fred.psc.edu/GAIA/gaia.output.1363279966.ogg';
} else {

    foreach ($UrlOutputResourceList as $visOutResource) {

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
$result['visualizerDeveloper'] = $SoftwareIdentification->softwareDeveloper;
$result['visualizerName'] = $SoftwareIdentification->softwareName;
$result['visualizerVersion'] = $SoftwareIdentification->softwareVersion;
//$result['disease_states_url'] = $diseaseStatesURL;
//$result['incidence_url'] = $incidenceURL;

$ret->data = $result;

echo json_encode($ret);
?>
