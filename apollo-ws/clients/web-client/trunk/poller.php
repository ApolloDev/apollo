<?php

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

define('AROOT', getcwd());
require_once AROOT . '/apollo/apollo.inc';

$ret = new Response();

$apollo = new apollo();

try {
    $client = new SoapClient($apollo->getWSDL(), array('trace' => true));


    $runId = $_GET ['runId'];
    $dev = $_GET['dev'];
    $name = $_GET['name'];
    $ver = $_GET['ver'];
    $type = $_GET['type'];

    $ServiceRecord = new stdClass();
    if ($type == 'simulator') {
        $SimulatorIdentification = new stdClass();
        $SimulatorIdentification->simulatorDeveloper = $dev;
        $SimulatorIdentification->simulatorName = $name;
        $SimulatorIdentification->simulatorVersion = $ver;

        $ServiceRecord->simulatorIdentification = $SimulatorIdentification;
    } else {
        $VisualizerIdentification = new stdClass();
        $VisualizerIdentification->visualizerDeveloper = $dev;
        $VisualizerIdentification->visualizerName = $name;
        $VisualizerIdentification->visualizerVersion = $ver;

        $ServiceRecord->visualizerIdentification = $VisualizerIdentification;
    }

    $status = $client->getRunStatus(array('runId' => $runId, 'serviceRecord' => $ServiceRecord));

    $statText['status'] = $status->runStatus->status;
    $statText['message'] = $status->runStatus->message;

//ChromePhp::log($statText['message']);

    $ret->data = $statText;
    echo json_encode($ret);
} catch (Exception $e) {
    $statText['status'] = 'UNKNOWN';
    $statText['message'] = 'Unable to get run status from the simulator web service';

    $ret->data = $statText;
    echo json_encode($ret);
}
?>
