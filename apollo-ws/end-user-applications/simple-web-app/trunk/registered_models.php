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

// get the registered services
$apolloResponse = $client->getRegisteredServices();
$serviceRecords = $apolloResponse->serviceRecords;

$ret->data = $serviceRecords;

echo json_encode($ret);

?>
