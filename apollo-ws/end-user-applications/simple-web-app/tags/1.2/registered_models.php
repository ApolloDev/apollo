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

// get the registered services

    $apolloResponse = $client->getRegisteredServices();
    $serviceRecords = $apolloResponse->serviceRecords;

    $ret->data = $serviceRecords;
} catch (Exception $e) {
    $ret->exception = $e;
}

echo json_encode($ret);
?>
