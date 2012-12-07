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
 *
 * @author Yang Hu <yah14@pitt.edu>
 */

require_once AROOT . '/apollo/type.inc';
require_once AROOT . '/apollo/apollo.inc';

require_once AROOT . '/models/seir/constant.inc';

function seir_exec($params) {
	$classmap = array ('ModelID' => 'ModelID', 'RunResultRequestObjType' => 'RunResultRequestObjType' );

	$apollo = new apollo();

	$client = new SoapClient ( $apollo->getWSDL(), array ('trace' => true, 'classmap' => $classmap ) );

	$id = seir_get_modelid ();
	$runObj = $client->getDefaultSEIRModelRunRequestObj ( array ('arg0' => $id ) );

	//Fill in the correct parameters
	$runObj = $runObj->return;
	$runObj->ModelID = $id;
	$runObj->Parameters->registered->Username = "";
	$runObj->Parameters->registered->Password = "";

	//fillinRecognized
	$recognized = $runObj->Parameters->registered;
	$recognized->TemporalGranularity = $params['TemporalGranularity'];
	$recognized->TemporalIncrement= $params['TemporalIncrement'];
	$recognized->RunLength = $params['RunLength'];
	$recognized->AverageDurationOfInfectiousness = $params['AverageDurationOfInfectiousness'];
	$recognized->AverageLatentPeriod = $params['AverageLatentPeriod'];
	$recognized->PercentCompliant = $params['PercentCompliant'];
	$recognized->r0 = $params['r0'];
	$recognized->VaccinationSchedule->value = $params['VaccinationSchedule'];

	//InitialCompartmentSizes
	$recognized->InitialCompartmentSizes->Susceptible->Number = $params['Susceptible'];
	$recognized->InitialCompartmentSizes->Exposed = $params['Exposed'];
	$recognized->InitialCompartmentSizes->Infectious = $params['Infectious'];
	$recognized->InitialCompartmentSizes->Recovered = $params['Recovered'];
	//end InitialCompartmentSizes

	//$temporalGranularity = $runObj->Parameters->Recognized->TemporalGranularity;

	$apolloResponse = $client->runSEIRModelRequest ( array ('arg0' => $runObj ) );
	$apolloResponse = $apolloResponse->return;

	if ($apolloResponse->id == - 1) { //cache hit
		$apolloResponse = $apolloResponse->cachedResult;
	} else {
		$runId = $apolloResponse->id;

		while ( true ) {
			$status = $client->getStatus ( array ('arg0' => $runId ) );
			$status = $status->return;
			if (strcmp ( $status, 'COMPLETED' ) == 0) {
				$rrro = new RunResultRequestObjType ( $runId, '', '' );
				$apolloResponse = $client->getSEIRModelRunResults ( array ('arg0' => $rrro ) );
				$apolloResponse = $apolloResponse->return;
				break;
			} else
				sleep ( 1 ); //sleep for one second
		}
	}

	//		$curves ['Susceptible'] = $apolloResponse->return->results->Recognized->EpiCurve->Susceptible->value;
	//		$curves ['Exposed'] = $apolloResponse->return->results->Recognized->EpiCurve->Exposed->value;
	//		$curves ['Infectious'] = $apolloResponse->return->results->Recognized->EpiCurve->Infectious->value;
	//		$curves ['Recovered'] = $apolloResponse->return->results->Recognized->EpiCurve->Recovered->value;
	$curves ['Incidence'] = $apolloResponse->results->registered->IncidenceCurve->value;

	return $curves;
}
?>