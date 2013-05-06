<?php

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

require_once __DIR__ . '/../population/location_data.php';

$requestType = $_GET['requestType'];
$location_data = new location_data();
$location_data->initializeLocationData();

if ($requestType == 'states') {
    echo $location_data->getJSONStateList();
} else if ($requestType == 'counties') {
    $state = $_GET ['state'];
    echo $location_data->getJSONCountyListForState($state);
} else if ($requestType == 'otherLocations') {
    
    echo $location_data->getJSONStateOtherLocations();
}
?>
