<?php

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

require_once __DIR__ . '/util/apollo.inc';

$ret = new Response();

$apollo = new apollo();

$client = new SoapClient($apollo->getWSDL(), array('trace' => true));

// get the registered services
$apolloResponse = $client->getRegisteredServices();
$serviceRecords = $apolloResponse->serviceRecords;

$ret->data = $serviceRecords;

echo json_encode($ret);

?>
