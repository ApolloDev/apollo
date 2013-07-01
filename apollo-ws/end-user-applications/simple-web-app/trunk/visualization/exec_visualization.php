<?php

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

require_once __DIR__ . '/../util/apollo.inc';
//require_once __DIR__ . '/../chromephp/ChromePhp.php';
$ret = new Response();

$apollo = new apollo();

try {
    $client = new SoapClient($apollo->getWSDL(), array('trace' => true));
    $runId = $_GET ['runId'];
    $vizDev = $_GET['vizDev'];
    $vizName = $_GET['vizName'];
    $vizVer = $_GET['vizVer'];
    $location = $_GET['location']; // this location isn't currently used by any visualizers
//$location = $_GET['location'];
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
    $VisualizationOptions->location = $location;
    $VisualizationOptions->outputFormat = 'ogg';
    $VisualizerConfiguration->visualizationOptions = $VisualizationOptions;

//ChromePhp::log($VisualizerConfiguration);


    $apolloResponse = $client->runVisualization(array('visualizerConfiguration' => $VisualizerConfiguration));

    $UrlOutputResourceList = null;

    if (property_exists($apolloResponse->visualizerResult, "visualizerOutputResource")) {
        $UrlOutputResourceList = $apolloResponse->visualizerResult->visualizerOutputResource;
        $diseaseStatesURL = '';
        $incidenceURL = '';

        $urls = array();

        if (strpos($runId, ':') !== false || $vizName == 'GAIA') {
            $urls[$UrlOutputResourceList->description] = $UrlOutputResourceList->URL;
        } else {

            foreach ($UrlOutputResourceList as $visOutResource) {
                $urls[$visOutResource->description] = $visOutResource->URL;
            }
        }

        $result['urls'] = $urls;
        $result['runId'] = $runId;
    } else {
        $result['urls'] = array();
        $result['runId'] = $apolloResponse->visualizerResult->runId;
    }
    $result['visualizerDeveloper'] = $SoftwareIdentification->softwareDeveloper;
    $result['visualizerName'] = $SoftwareIdentification->softwareName;
    $result['visualizerVersion'] = $SoftwareIdentification->softwareVersion;

    $ret->data = $result;
} catch (Exception $e) {
    $ret->exception = $e;
}

echo json_encode($ret);
?>
