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

//namespace models\compartment;

require_once 'constant.inc';

require_once AROOT . '/models/misc.inc';
require_once AROOT . '/models/object_helper.inc';
require_once AROOT . '/models/shared_parameters.inc';

require_once AROOT . '/apollo/type.inc';
require_once AROOT . '/apollo/apollo.inc';

//for cell
// total is 11
// predefine is 6
// custom is 5
//(rowid, registered, name, value, url, extradata, level, left, right, leaf, expend)

function compartment_get_default_param_structure() {
	$Compartment = new stdClass;

	$left = 0;
	$rowid = - 1;

	$classmap = array ('ModelID' => 'ModelID', 'RunResultRequestObjType' => 'RunResultRequestObjType' );


	$apollo = new apollo();
	$client = new SoapClient ($apollo->getWSDL(), array ('trace' => true, 'classmap' => $classmap ) );

	$id = compartment_get_modelid();
	$runObj = $client->getDefaultCompartmentModelRunRequestObj ( array ('arg0' => $id ) );

	$recognized = $runObj->return->Parameters->registered;

	$regIconHtml = get_reg_icon_text();
	$unregIconHtml = get_unreg_icon_text();

	//begin Recognized Parameters
//	$Compartment->rows [] = array ('id' => ++ $rowid, 'cell' => array ($rowid + 1, 'Recognized Parameters', '', '', '', 0, ++ $left, $left + 27, false, false ) );

	//shared
	$pname = get_time_granularity_text();
	$pvalue = get_value($recognized, deleteSpace($pname));
	$Compartment->rows [] = array ('id' => ++ $rowid, 'cell' => array ($rowid + 1, $regIconHtml, $pname, $pvalue, '', '', 0, ++ $left, ++ $left, true, false ) );

	$pname = get_temporal_increment_text();
	$pvalue = get_value($recognized, deleteSpace($pname));
	$Compartment->rows [] = array ('id' => ++ $rowid, 'cell' => array ($rowid + 1, $regIconHtml, $pname, $pvalue, '', '', 0, ++ $left, ++ $left, true, false ) );

	$pname = get_run_length_text();
	$pvalue = get_value($recognized, deleteSpace($pname));
	$Compartment->rows [] = array ('id' => ++ $rowid, 'cell' => array ($rowid + 1, $regIconHtml, $pname, $pvalue, '', '', 0, ++ $left, ++ $left, true, false ) );

//	$pname = get_username_text();
//	$pvalue = get_value($recognized, $pname);
//	$SEIR->rows [] = array ('id' => ++ $rowid, 'cell' => array ($rowid + 1, $pname, $pvalue, '', 1, ++ $left, ++ $left, true, false ) );

//	$pname = get_password_text();
//	$pvalue = get_value($recognized, $pname);
//	$SEIR->rows [] = array ('id' => ++ $rowid, 'cell' => array ($rowid + 1, $pname, $pvalue, '', 1, ++ $left, ++ $left, true, false ) );
	//end shared

	$pname = 'Begin Treatment Interval';
	$pvalue = get_value($recognized, deleteSpace($pname));
	$Compartment->rows [] = array ('id' => ++ $rowid, 'cell' => array ($rowid + 1, $regIconHtml, $pname, $pvalue, '', '', 0, ++ $left, ++ $left, true, false ) );

	$pname = 'Prophylaxis Efficacy';
	$pvalue = get_value($recognized, deleteSpace($pname));
	$Compartment->rows [] = array ('id' => ++ $rowid, 'cell' => array ($rowid + 1, $regIconHtml, $pname, $pvalue, '', '', 0, ++ $left, ++ $left, true, false ) );

	$pname = 'Prodromal Treatment Efficacy';
	$pvalue = get_value($recognized, deleteSpace($pname));
	$Compartment->rows [] = array ('id' => ++ $rowid, 'cell' => array ($rowid + 1, $regIconHtml, $pname, $pvalue, '', '', 0, ++ $left, ++ $left, true, false ) );

	$pname = 'Fulminant Treatment Efficacy';
	$pvalue = get_value($recognized, deleteSpace($pname));
	$Compartment->rows [] = array ('id' => ++ $rowid, 'cell' => array ($rowid + 1, $regIconHtml, $pname, $pvalue, '', '', 0, ++ $left, ++ $left, true, false ) );

//	$pname = 'ProphylaxisSchedule'; // this variable is empty for now
//	$pvalue = get_value($recognized, $pname)->value;
//	$SEIR->rows [] = array ('id' => ++ $rowid, 'cell' => array ($rowid + 1, $pname, '',
//		'models\seir\vaccination_schedule.html', json_encode($pvalue), 1, ++ $left, ++ $left, true, false ) );

	$pname = 'Initial Compartment Sizes';
	$pvalue = get_value($recognized, deleteSpace($pname))->named_Compartment;
	$Compartment->rows [] = array ('id' => ++ $rowid, 'cell' => array ($rowid + 1, $regIconHtml, $pname, get_edit_hint_text(),
		'models/compartment/initial_compartment_sizes.html', json_encode($pvalue), 0, ++ $left, ++ $left, true, false ) );


//	//new level under InitialCompartmentSizes
//	$basePath = 'InitialCompartmentSizes'; //create base folder entry
//	$SEIR->rows [] = array ('id' => ++ $rowid, 'cell' => array ($rowid + 1, $basePath, '', '', '', 1, ++ $left, $left + 9, false, false ) );
//
//	$pname = 'Susceptible';
//	$pvalue = get_value($recognized, $basePath . '.' . $pname . '.' . 'Number');
//	$SEIR->rows [] = array ('id' => ++ $rowid, 'cell' => array ($rowid + 1, $pname, $pvalue, '', '', 2, ++ $left, ++ $left, true, false ) );
//
//	$pname = 'Exposed';
//	$pvalue = get_value($recognized, $basePath . '.' . $pname);
//	$SEIR->rows [] = array ('id' => ++ $rowid, 'cell' => array ($rowid + 1, $pname, $pvalue, '', '', 2, ++ $left, ++ $left, true, false ) );
//
//	$pname = 'Infectious';
//	$pvalue = get_value($recognized, $basePath . '.' . $pname);
//	$SEIR->rows [] = array ('id' => ++ $rowid, 'cell' => array ($rowid + 1, $pname, $pvalue, '', '', 2, ++ $left, ++ $left, true, false ) );
//
//	$pname = 'Recovered';
//	$pvalue = get_value($recognized, $basePath . '.' . $pname);
//	$SEIR->rows [] = array ('id' => ++ $rowid, 'cell' => array ($rowid + 1, $pname, $pvalue, '', '', 2, ++ $left, ++ $left, true, false ) );
//	// end InitialCompartmentSizes
//
//	//end Recognized Parameters
//
//	//begin Rnrecognized Parameters
//	$left += 2;
//	$SEIR->rows [] = array ('id' => ++ $rowid, 'cell' => array ($rowid + 1, 'Unrecognized Parameters', '', '', '', 0, ++ $left, ++$left, false, false ) );

	$Compartment->records = count($Compartment->rows);
	$Compartment->page = 1;
	$Compartment->total = count($Compartment->rows);

	return $Compartment;
}
?>