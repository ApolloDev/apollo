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
 * Apollo Web Service Consumer
 *
 * @author Yang Hu <yah14@pitt.edu>
 */
define('AROOT', getcwd());

require_once AROOT . '/apollo/apollo.inc';
require_once AROOT . '/models/misc.inc';

$ret = new Response();

try {
    //snomed code
//    $snomed = $_POST ['SNOMED'];
    //model type
//    $modelType = $_POST ['ModelType'];
    //jurisdiction
//    $jurisdiction = $_POST['Jurisdiction'];
    
    $simulatorDevArray = explode(",", $_POST['simulatorDeveloper']);
    $simulatorNameArray = explode(",", $_POST['simulatorName']);
    $simulatorVerArray = explode(",", $_POST['simulatorVersion']);
    
    //model parameters
    $rawParams = json_decode($_POST ['Parameters']);

    // Define the jason errors.
//	$json_errors = array(
//	    JSON_ERROR_NONE => 'No error has occurred',
//	    JSON_ERROR_DEPTH => 'The maximum stack depth has been exceeded',
//	    JSON_ERROR_CTRL_CHAR => 'Control character error, possibly incorrectly encoded',
//	    JSON_ERROR_SYNTAX => 'Syntax error',
//	);
//	if (json_last_error() != JSON_ERROR_NONE)
//		throw new Exception('Json parse error : '. $json_errors[json_last_error()]. PHP_EOL);
    //cache the model parameters into map
    $params = array();
    foreach ($rawParams as $rawParam) {
        if ($rawParam->isLeaf == false)
            continue;
        //extra saves the complex data in json string
        if (strlen($rawParam->extra) == 0) {
            //if its a single value
            $params[deleteSpace($rawParam->pname)] = $rawParam->value;
        } else {//if its a complex object
            $params[deleteSpace($rawParam->pname)] = json_decode($rawParam->extra);
        }
        
        ChromePhp::log(deleteSpace($rawParam->pname));
    }
    
    $apollo = new apollo();
    
    $runIds = array();
    // execute in loop
    for ($i = 0; $i < count($simulatorDevArray); $i++) {
        $simulatorDev = $simulatorDevArray[$i];
        $simulatorName = $simulatorNameArray[$i];
        $simulatorVer = $simulatorVerArray[$i];
        
        $runIds[] = $apollo->exec($params, $simulatorDev, $simulatorName, $simulatorVer);
    }
    
    
    $ret->data = $runIds;
//    $ret->startDate = $params['StartDate'];
} catch (Exception $e) {
    $ret->exception = $e->getMessage();
}

echo json_encode($ret);
?>