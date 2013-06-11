<?php

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

require_once __DIR__ . '/util/apollo.inc';

$ret = new Response();

$apollo = new apollo();

try {
    $client = new SoapClient($apollo->getWSDL(), array('trace' => true));


    $runId = $_GET ['runId'];
    $dev = $_GET['dev'];
    $name = $_GET['name'];
    $ver = $_GET['ver'];
    $type = $_GET['type'];

    $RunAndSoftwareIdentification = new stdClass();
    $SoftwareIdentification = new stdClass();
    $SoftwareIdentification->softwareDeveloper = $dev;
    $SoftwareIdentification->softwareName = $name;
    $SoftwareIdentification->softwareVersion = $ver;
    if ($type == 'simulator') {
        $SoftwareIdentification->softwareType = 'simulator';
    } else {
        $SoftwareIdentification->softwareType = 'visualizer';
    }
    
//    ChromePhp::log($SoftwareIdentification->softwareDeveloper);
//    ChromePhp::log($SoftwareIdentification->softwareName);
//    ChromePhp::log($SoftwareIdentification->softwareVersion);
//    ChromePhp::log($SoftwareIdentification->softwareType);
    
    $RunAndSoftwareIdentification->softwareId = $SoftwareIdentification;
    $RunAndSoftwareIdentification->runId = $runId;
    if (strpos($runId, ";") == FALSE || $type == 'visualization') {

//        if ($name == 'GAIA') {
//            $statText['status_normal'] = 'completed';
//            $statText['message_normal'] = 'run is completed';
//            $statText['status_novacc'] = 'null';
//            $statText['message_novacc'] = 'null';
//        } else {

        $status = $client->getRunStatus(array('runAndSoftwareIdentification' => $RunAndSoftwareIdentification));
        $statText['status_normal'] = $status->runStatus->status;
        $statText['message_normal'] = $status->runStatus->message;
        $statText['status_novacc'] = 'null';
        $statText['message_novacc'] = 'null';
//        }
    } else {

        $runIdList = explode(';', $runId);

        $RunAndSoftwareIdentification->runId = $runIdList[1];
        $status = $client->getRunStatus(array('runAndSoftwareIdentification' => $RunAndSoftwareIdentification));
        $statText['status_normal'] = $status->runStatus->status;
        $statText['message_normal'] = $status->runStatus->message;

        $RunAndSoftwareIdentification->runId = $runIdList[0];
        $status = $client->getRunStatus(array('runAndSoftwareIdentification' => $RunAndSoftwareIdentification));
        $statText['status_novacc'] = $status->runStatus->status;
        $statText['message_novacc'] = $status->runStatus->message;
    }

    $ret->data = $statText;
    echo json_encode($ret);
} catch (Exception $e) {
    $statText['status'] = 'UNKNOWN';
    $statText['message'] = 'Unable to get run status from the simulator web service';

    $ret->data = $statText;
    echo json_encode($ret);
}
?>
