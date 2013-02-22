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
//require_once AROOT . '/chrome/ChromePhp.php';

//for cell
// total is 11
// predefine is 6
// custom is 5
//(rowid, registered, name, value, url, extradata, level, left, right, leaf, expend)

function seir_get_default_param_structure() {
    $SEIR = new stdClass;

    $left = 0;
    $rowid = - 1;

//    $classmap = array('ModelID' => 'ModelID', 'RunResultRequestObjType' => 'RunResultRequestObjType');
//
//    $apollo = new apollo();
//    $client = new SoapClient($apollo->getWSDL(), array('trace' => true, 'classmap' => $classmap));
//
//    $id = seir_get_modelid();
//    $runObj = $client->getDefaultSEIRModelRunRequestObj(array('arg0' => $id));
//
//    $recognized = $runObj->return->Parameters->registered;

    $regIconHtml = get_reg_icon_text();
    $unregIconHtml = get_unreg_icon_text();
    $sectionParagraph = '<p style="font-weight:bold; margin:0px; font-size:13px; ">';

    //begin Recognized Parameters
//	$SEIR->rows [] = array ('id' => ++ $rowid, 'cell' => array ($rowid + 1,  'Recognized Parameters', '', '', '',
//		0, ++ $left, $left + 27, false, false ) );
    //shared
//    $pname = get_initial_date_text();
//    $pvalue = "2009-08-01";
//    $SEIR->rows [] = array('id' => ++$rowid, 'cell' => array($rowid + 1,  $pname, $pvalue, '', '', 0, ++$left, ++$left, true, false));
//    $pname = $sectionParagraph . '--- Simulator Identification Section ---</p>';
//    $pvalue = '';
//    $SEIR->rows [] = array('id' => ++$rowid, 'cell' => array($rowid + 1, $pname, $pvalue, '', '', 0, ++$left, ++$left, true, false));
//
//    $pname = 'Simulator Developer';
//    $pvalue = 'UPitt,PSC,CMU';
//    $SEIR->rows [] = array('id' => ++$rowid, 'cell' => array($rowid + 1, $pname, $pvalue, '', '', 0, ++$left, ++$left, true, false));
//
//    $pname = 'Simulator Name';
//    $pvalue = 'FRED';
//    $SEIR->rows [] = array('id' => ++$rowid, 'cell' => array($rowid + 1, $pname, $pvalue, '', '', 0, ++$left, ++$left, true, false));
//
//    $pname = 'Simulator Version';
//    $pvalue = '2.0.1';
//    $SEIR->rows [] = array('id' => ++$rowid, 'cell' => array($rowid + 1, $pname, $pvalue, '', '', 0, ++$left, ++$left, true, false));

    $pname = $sectionParagraph . '--- Authentication Section ---</p>';
    $pvalue = '';
    $SEIR->rows [] = array('id' => ++$rowid, 'cell' => array($rowid + 1, $pname, $pvalue, '', '', 0, ++$left, ++$left, true, false));

    $pname = 'Requester ID';
    $pvalue = 'UpittUser';
    $SEIR->rows [] = array('id' => ++$rowid, 'cell' => array($rowid + 1, $pname, $pvalue, '', '', 0, ++$left, ++$left, true, false));


    $pname = 'Requester Password';
    $pvalue = 'UpittPassword';
    $SEIR->rows [] = array('id' => ++$rowid, 'cell' => array($rowid + 1, $pname, $pvalue, '', '', 0, ++$left, ++$left, true, false));

    $pname = $sectionParagraph . '--- Simulator Time Specification Section ---</p>';
    $pvalue = '';
    $SEIR->rows [] = array('id' => ++$rowid, 'cell' => array($rowid + 1, $pname, $pvalue, '', '', 0, ++$left, ++$left, true, false));


    $pname = get_time_step_unit_text();
    $pvalue = '';
    $SEIR->rows [] = array('id' => ++$rowid, 'cell' => array($rowid + 1, $pname, $pvalue, '', '', 0, ++$left, ++$left, true, false));

    $pname = get_time_step_value_text();
    $pvalue = '1';
    $SEIR->rows [] = array('id' => ++$rowid, 'cell' => array($rowid + 1, $pname, $pvalue, '', '', 0, ++$left, ++$left, true, false));

    $pname = get_run_length_text();
    $pvalue = '45';
    $SEIR->rows [] = array('id' => ++$rowid, 'cell' => array($rowid + 1, $pname, $pvalue, '', '', 0, ++$left, ++$left, true, false));

    $pname = $sectionParagraph . '--- Simulated Population Section ---</p>';
    $pvalue = '';
    $SEIR->rows [] = array('id' => ++$rowid, 'cell' => array($rowid + 1, $pname, $pvalue, '', '', 0, ++$left, ++$left, true, false));

    $pname = 'Population Location';
    $pvalue = '42003';
    $SEIR->rows [] = array('id' => ++$rowid, 'cell' => array($rowid + 1, $pname, $pvalue, '', '', 0, ++$left, ++$left, true, false));

    $basePath = 'Initial Compartment Sizes'; //create base folder entry
    $SEIR->rows [] = array('id' => ++$rowid, 'cell' => array($rowid + 1, $basePath, '', '', '', 0, ++$left, $left + 9, false, false));
    $basePath = deleteSpace($basePath);

    $pname = 'Susceptible';
    $pvalue = '983629';
    $SEIR->rows [] = array('id' => ++$rowid, 'cell' => array($rowid + 1, $pname, $pvalue, '', '', 1, ++$left, ++$left, true, false));

    $pname = 'Exposed';
    $pvalue = '24758';
    $SEIR->rows [] = array('id' => ++$rowid, 'cell' => array($rowid + 1, $pname, $pvalue, '', '', 1, ++$left, ++$left, true, false));


    $pname = 'Infectious';
    $pvalue = '20528';
    $SEIR->rows [] = array('id' => ++$rowid, 'cell' => array($rowid + 1, $pname, $pvalue, '', '', 1, ++$left, ++$left, true, false));

    $pname = 'Recovered';
    $pvalue = '189623';
    $SEIR->rows [] = array('id' => ++$rowid, 'cell' => array($rowid + 1, $pname, $pvalue, '', '', 1, ++$left, ++$left, true, false));


    $pname = $sectionParagraph . '--- Disease Section ---</p>';
    $pvalue = '';
    $SEIR->rows [] = array('id' => ++$rowid, 'cell' => array($rowid + 1, $pname, $pvalue, '', '', 0, ++$left, ++$left, true, false));

    $pname = 'Disease Name';
    $pvalue = 'Influenza';
    $SEIR->rows [] = array('id' => ++$rowid, 'cell' => array($rowid + 1, $pname, $pvalue, '', '', 0, ++$left, ++$left, true, false));

    $pname = 'Infectious Period';
    $pvalue = '3.2';
    $SEIR->rows [] = array('id' => ++$rowid, 'cell' => array($rowid + 1, $pname, $pvalue, '', '', 0, ++$left, ++$left, true, false));

    $pname = 'Latent Period';
    $pvalue = '2';
    $SEIR->rows [] = array('id' => ++$rowid, 'cell' => array($rowid + 1, $pname, $pvalue, '', '', 0, ++$left, ++$left, true, false));

    $pname = 'Reproduction Number';
    $pvalue = '1.7';
    $SEIR->rows [] = array('id' => ++$rowid, 'cell' => array($rowid + 1, $pname, $pvalue, '', '', 0, ++$left, ++$left, true, false));

    $pname = 'Asymptomatic Infection Fraction';
    $pvalue = '0.5';
    $SEIR->rows [] = array('id' => ++$rowid, 'cell' => array($rowid + 1, $pname, $pvalue, '', '', 0, ++$left, ++$left, true, false));

//    $pname = $sectionParagraph . '--- Antiviral Control Measure Section ---</p>';
//    $pvalue = '';
//    $SEIR->rows [] = array('id' => ++$rowid, 'cell' => array($rowid + 1, $pname, $pvalue, '', '', 0, ++$left, ++$left, true, false));
//
//    $pname = 'Antiviral Control Measure Compliance';
//    $pvalue = '0.0';
//    $SEIR->rows [] = array('id' => ++$rowid, 'cell' => array($rowid + 1, $pname, $pvalue, '', '', 0, ++$left, ++$left, true, false));
//
//    $pname = 'Antiviral Supply Schedule';
//    $pvalue = array();
//    for ($i = 0; $i < 365; $i++) {
//        $pvalue[] = 0.0;
//    }
//    $SEIR->rows [] = array('id' => ++$rowid, 'cell' => array($rowid + 1, $pname, get_edit_hint_text(),
//            'models/seir/antiviral_supply_schedule.html', json_encode($pvalue), 0, ++$left, ++$left, true, false));
//
//    $pname = 'Antiviral Administration Schedule';
//    $pvalue = array();
//    for ($i = 0; $i < 365; $i++) {
//        $pvalue[] = 0.0;
//    }
//    $SEIR->rows [] = array('id' => ++$rowid, 'cell' => array($rowid + 1, $pname, get_edit_hint_text(),
//            'models/seir/antiviral_admin_schedule.html', json_encode($pvalue), 0, ++$left, ++$left, true, false));
//
//    $pname = 'Antiviral Efficacy';
//    $pvalue = '0.0';
//    $SEIR->rows [] = array('id' => ++$rowid, 'cell' => array($rowid + 1, $pname, $pvalue, '', '', 0, ++$left, ++$left, true, false));
//
//    $pname = 'Antiviral Efficacy Delay';
//    $pvalue = '0.0';
//    $SEIR->rows [] = array('id' => ++$rowid, 'cell' => array($rowid + 1, $pname, $pvalue, '', '', 0, ++$left, ++$left, true, false));

    $pname = $sectionParagraph . '--- Vaccination Control Measure Section ---</p>';
    $pvalue = '';
    $SEIR->rows [] = array('id' => ++$rowid, 'cell' => array($rowid + 1, $pname, $pvalue, '', '', 0, ++$left, ++$left, true, false));

    $pname = 'Vaccine Control Measure Compliance';
    $pvalue = '0.0';
    $SEIR->rows [] = array('id' => ++$rowid, 'cell' => array($rowid + 1, $pname, $pvalue, '', '', 0, ++$left, ++$left, true, false));

    $pname = 'Vaccination Supply Schedule';
    $pvalue = array();
    for ($i = 0; $i < 45; $i++) {
        $pvalue[] = 0.0;
    }

    $SEIR->rows [] = array('id' => ++$rowid, 'cell' => array($rowid + 1, $pname, get_edit_hint_text(),
            'models/seir/vaccination_supply_schedule.html', json_encode($pvalue), 0, ++$left, ++$left, true, false));

    $pname = 'Vaccination Administration Schedule';
    $pvalue = array();
    for ($i = 0; $i < 365; $i++) {
        $pvalue[] = 0.0;
    }
    $SEIR->rows [] = array('id' => ++$rowid, 'cell' => array($rowid + 1, $pname, get_edit_hint_text(),
            'models/seir/vaccination_admin_schedule.html', json_encode($pvalue), 0, ++$left, ++$left, true, false));

    $pname = 'Vaccine Efficacy';
    $pvalue = '0.0';
    $SEIR->rows [] = array('id' => ++$rowid, 'cell' => array($rowid + 1, $pname, $pvalue, '', '', 0, ++$left, ++$left, true, false));

    $pname = 'Vaccine Efficacy Delay';
    $pvalue = '0.0';
    $SEIR->rows [] = array('id' => ++$rowid, 'cell' => array($rowid + 1, $pname, $pvalue, '', '', 0, ++$left, ++$left, true, false));


    //new level under InitialCompartmentSizes
//    $basePath = 'Initial Compartment Sizes'; //create base folder entry
//    $SEIR->rows [] = array('id' => ++$rowid, 'cell' => array($rowid + 1, $basePath, '', '', '', 0, ++$left, $left + 9, false, false));
//    $basePath = deleteSpace($basePath);
//
//    $pname = 'Susceptible';
//    $pvalue = '1157474';
//    $SEIR->rows [] = array('id' => ++$rowid, 'cell' => array($rowid + 1, $pname, $pvalue, '', '', 1, ++$left, ++$left, true, false));
//
//    $pname = 'Exposed';
//    $pvalue = '0';
//    $SEIR->rows [] = array('id' => ++$rowid, 'cell' => array($rowid + 1, $pname, $pvalue, '', '', 1, ++$left, ++$left, true, false));
//
//
//    $pname = 'Infectious';
//    $pvalue = '100';
//    $SEIR->rows [] = array('id' => ++$rowid, 'cell' => array($rowid + 1, $pname, $pvalue, '', '', 1, ++$left, ++$left, true, false));
//
//    $pname = 'Recovered';
//    $pvalue = '60920';
//    $SEIR->rows [] = array('id' => ++$rowid, 'cell' => array($rowid + 1, $pname, $pvalue, '', '', 1, ++$left, ++$left, true, false));
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