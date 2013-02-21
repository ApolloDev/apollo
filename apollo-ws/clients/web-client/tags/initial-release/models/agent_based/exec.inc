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
//namespace models\agent_based;

require_once AROOT . '/apollo/type.inc';
require_once AROOT . '/apollo/apollo.inc';

require_once AROOT . '/models/agent_based/constant.inc';

function agent_based_exec($params) {
	$classmap = array ('ModelID' => 'ModelID', 'RunResultRequestObjType' => 'RunResultRequestObjType' );

	$apollo = new apollo();
	$client = new SoapClient ( $apollo->getWSDL (), array ('trace' => true, 'classmap' => $classmap ) );

	$id = agent_based_get_modelid ();
	$runObj = $client->getDefaultAgentBasedModelRunRequestObj ( array ('arg0' => $id ) );

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

	$recognized->NumberOfRuns = $params['NumberOfRuns'];
	$recognized->RandomSeed = $params['RandomSeed'];
	$recognized->PercentImmune = $params['PercentImmune'];

	//VaccinationScheduleControlMeasureInfo
	$recognized->VaccinationScheduleControlMeasureInfo->VaccineTemporalDistribution->value = $params['VaccineTemporalDistribution'];
	$recognized->VaccinationScheduleControlMeasureInfo->VaccinationPrioritizationScheme = $params['VaccinationPrioritizationScheme'];
	$recognized->VaccinationScheduleControlMeasureInfo->Compliance = $params['Compliance'];

	//SchoolClosureControlMeasureInfo
	$recognized->SchoolClosureControlMeasureInfo->PolicyType = $params['PolicyType'];
	$recognized->SchoolClosureControlMeasureInfo->Threshold = $params['Threshold'];
	$recognized->SchoolClosureControlMeasureInfo->ClosurePeriod = $params['ClosurePeriod'];
	$recognized->SchoolClosureControlMeasureInfo->ClosureDelay = $params['ClosureDelay'];

	$apolloResponse = $client->runAgentBaseModelRequest ( array ('arg0' => $runObj ) );
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
				$apolloResponse = $client->getAgentBasedModelRunResults ( array ('arg0' => $rrro ) );
				$apolloResponse = $apolloResponse->return;
				break;
			} else
				sleep ( 1 ); //sleep for one second
		}
	}

	//		$curves ['Susceptible'] = $apolloResponse->return->results->registered->EpiCurve->Susceptible->value;
	//		$curves ['Exposed'] = $apolloResponse->return->results->registered->EpiCurve->Exposed->value;
	//		$curves ['Infectious'] = $apolloResponse->return->results->registered->EpiCurve->Infectious->value;
	//		$curves ['Recovered'] = $apolloResponse->return->results->registered->EpiCurve->Recovered->value;
	$curves ['Incidence'] = $apolloResponse->results->registered->IncidenceCurve->value;

//	$curves = Array();
	return $curves;
}
?>