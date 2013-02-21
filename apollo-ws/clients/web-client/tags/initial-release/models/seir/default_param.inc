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
//namespace models\seir;

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

function seir_get_default_param_structure() {
	$SEIR = new stdClass;

	$left = 0;
	$rowid = - 1;

	$classmap = array ('ModelID' => 'ModelID', 'RunResultRequestObjType' => 'RunResultRequestObjType' );

	$apollo = new apollo();
	$client = new SoapClient ($apollo->getWSDL(), array ('trace' => true, 'classmap' => $classmap ) );

	$id = seir_get_modelid();
	$runObj = $client->getDefaultSEIRModelRunRequestObj ( array ('arg0' => $id ) );

	$recognized = $runObj->return->Parameters->registered;

	$regIconHtml = get_reg_icon_text();
	$unregIconHtml = get_unreg_icon_text();

	//begin Recognized Parameters
//	$SEIR->rows [] = array ('id' => ++ $rowid, 'cell' => array ($rowid + 1, $regIconHtml, 'Recognized Parameters', '', '', '',
//		0, ++ $left, $left + 27, false, false ) );

	//shared
	$pname = get_time_granularity_text();
	$pvalue = get_value($recognized, deleteSpace($pname) );
	$SEIR->rows [] = array ('id' => ++ $rowid, 'cell' => array ($rowid + 1, $regIconHtml, $pname, $pvalue, '', '', 0, ++ $left, ++ $left, true, false ) );

	$pname = get_temporal_increment_text();
	$pvalue = get_value($recognized, deleteSpace($pname) );
	$SEIR->rows [] = array ('id' => ++ $rowid, 'cell' => array ($rowid + 1, $regIconHtml, $pname, $pvalue, '', '', 0, ++ $left, ++ $left, true, false ) );

	$pname = get_run_length_text();
	$pvalue = get_value($recognized, deleteSpace($pname) );
	$SEIR->rows [] = array ('id' => ++ $rowid, 'cell' => array ($rowid + 1, $regIconHtml, $pname, $pvalue, '', '', 0, ++ $left, ++ $left, true, false ) );

//	$pname = get_username_text();
//	$pvalue = get_value($recognized, $pname);
//	$SEIR->rows [] = array ('id' => ++ $rowid, 'cell' => array ($rowid + 1, $pname, $pvalue, '', 1, ++ $left, ++ $left, true, false ) );

//	$pname = get_password_text();
//	$pvalue = get_value($recognized, $pname);
//	$SEIR->rows [] = array ('id' => ++ $rowid, 'cell' => array ($rowid + 1, $pname, $pvalue, '', 1, ++ $left, ++ $left, true, false ) );
	//end shared

	$pname = 'Average Duration Of Infectiousness';
	$pvalue = get_value($recognized, deleteSpace($pname) );
	$SEIR->rows [] = array ('id' => ++ $rowid, 'cell' => array ($rowid + 1, $regIconHtml, $pname, $pvalue, '', '', 0, ++ $left, ++ $left, true, false ) );

	$pname = 'Average Latent Period';
	$pvalue = get_value($recognized, deleteSpace($pname) );
	$SEIR->rows [] = array ('id' => ++ $rowid, 'cell' => array ($rowid + 1, $regIconHtml, $pname, $pvalue, '', '', 0, ++ $left, ++ $left, true, false ) );

	$pname = 'Percent Compliant';
	$pvalue = get_value($recognized, deleteSpace($pname) );
	$SEIR->rows [] = array ('id' => ++ $rowid, 'cell' => array ($rowid + 1, $regIconHtml, $pname, $pvalue, '', '', 0, ++ $left, ++ $left, true, false ) );

	$pname = 'r0';
	$pvalue = get_value($recognized, deleteSpace($pname) );
	$SEIR->rows [] = array ('id' => ++ $rowid, 'cell' => array ($rowid + 1, $regIconHtml, $pname, $pvalue, '', '', 0, ++ $left, ++ $left, true, false ) );

	$pname = 'Vaccination Schedule';
	$pvalue = get_value($recognized, deleteSpace($pname) )->value;
	$SEIR->rows [] = array ('id' => ++ $rowid, 'cell' => array ($rowid + 1, $regIconHtml, $pname, get_edit_hint_text(),
		'models/seir/vaccination_schedule.html', json_encode($pvalue), 0, ++ $left, ++ $left, true, false ) );

	//new level under InitialCompartmentSizes
	$basePath = 'Initial Compartment Sizes'; //create base folder entry
	$SEIR->rows [] = array ('id' => ++ $rowid, 'cell' => array ($rowid + 1, $regIconHtml, $basePath, '', '', '', 0, ++ $left, $left + 9, false, false ) );
	$basePath = deleteSpace($basePath);

	$pname = 'Susceptible';
	$pvalue = get_value($recognized, $basePath . '.' . deleteSpace($pname)  . '.' . 'Number');
	$SEIR->rows [] = array ('id' => ++ $rowid, 'cell' => array ($rowid + 1, '', $pname, $pvalue, '', '', 1, ++ $left, ++ $left, true, false ) );

	$pname = 'Exposed';
	$pvalue = get_value($recognized, $basePath . '.' . deleteSpace($pname) );
	$SEIR->rows [] = array ('id' => ++ $rowid, 'cell' => array ($rowid + 1, '', $pname, $pvalue, '', '', 1, ++ $left, ++ $left, true, false ) );

	$pname = 'Infectious';
	$pvalue = get_value($recognized, $basePath . '.' . deleteSpace($pname) );
	$SEIR->rows [] = array ('id' => ++ $rowid, 'cell' => array ($rowid + 1, '', $pname, $pvalue, '', '', 1, ++ $left, ++ $left, true, false ) );

	$pname = 'Recovered';
	$pvalue = get_value($recognized, $basePath . '.' . deleteSpace($pname) );
	$SEIR->rows [] = array ('id' => ++ $rowid, 'cell' => array ($rowid + 1, '', $pname, $pvalue, '', '', 1, ++ $left, ++ $left, true, false ) );
	// end InitialCompartmentSizes

	//end Recognized Parameters

	//begin Rnrecognized Parameters
//	$left += 1;
//	$SEIR->rows [] = array ('id' => ++ $rowid, 'cell' => array ($rowid + 1, $unregIconHtml, 'Unrecognized Parameters', '', '', '', 0, ++ $left, ++$left, false, false ) );

	$SEIR->records = count($SEIR->rows);
	$SEIR->page = 1;
	$SEIR->total = count($SEIR->rows);


	return $SEIR;
}
?>