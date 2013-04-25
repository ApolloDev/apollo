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
//namespace models\PARAMS;
//require_once 'constant.inc';

require_once AROOT . '/util/misc.inc';
require_once AROOT . '/util/object_helper.inc';
require_once AROOT . '/util/shared_parameters.inc';

require_once AROOT . '/apollo/type.inc';
require_once AROOT . '/apollo/apollo.inc';

//require_once AROOT . '/chromephp/ChromePhp.php';
//for cell
// total is 11
// predefine is 6
// custom is 5
//(rowid, registered, name, value, url, extradata, level, left, right, leaf, expend)

function get_default_param_structure() {
    $PARAMS = new stdClass;

    $apolloUriTermMap = array(
        "Time Step Unit" => "<http://purl.obolibrary.org/obo/APOLLO_SV_00000005>",
        "Time Step Value" => "<http://purl.obolibrary.org/obo/APOLLO_SV_00000012>",
        "Run Length" => "<http://purl.obolibrary.org/obo/APOLLO_SV_00000015>",
        "Population Location" => "<http://purl.obolibrary.org/obo/APOLLO_SV_00000039>",
        "Susceptible" => "<http://purl.obolibrary.org/obo/APOLLO_SV_00000018>",
        "Exposed" => "<http://purl.obolibrary.org/obo/APOLLO_SV_00000019>",
        "Infectious" => "<http://purl.obolibrary.org/obo/APOLLO_SV_00000020>",
        "Recovered" => "<http://purl.obolibrary.org/obo/APOLLO_SV_00000021>",
        "Disease Name" => "<http://purl.obolibrary.org/obo/APOLLO_SV_00000042>",
        "Infectious Period" => "<http://purl.obolibrary.org/obo/APOLLO_SV_00000016>",
        "Latent Period" => "<http://purl.obolibrary.org/obo/APOLLO_SV_00000017>",
        "Reproduction Number" => "<http://purl.obolibrary.org/obo/APOLLO_SV_00000043>",
        "Asymptomatic Infection Fraction" => "<http://purl.obolibrary.org/obo/APOLLO_SV_00000045>",
        "Vaccine Control Measure Compliance" => "<http://purl.obolibrary.org/obo/APOLLO_SV_00000024>",
        "Vaccination Supply Schedule" => "<http://purl.obolibrary.org/obo/APOLLO_SV_00000046>",
        "Vaccination Administration Schedule" => "<http://purl.obolibrary.org/obo/APOLLO_SV_00000023>",
        "Vaccine Efficacy" => "<http://purl.obolibrary.org/obo/APOLLO_SV_00000025>",
        "Vaccine Efficacy Delay" => "<http://purl.obolibrary.org/obo/APOLLO_SV_00000044>",
        "Control Measure Response Delay" => "<http://purl.obolibrary.org/obo/APOLLO_SV_00000133>",
        "Vaccination Reactive End Point Fraction" => "<http://purl.obolibrary.org/obo/APOLLO_SV_00000150>",
        "Control Measure Compliance" => "<http://purl.obolibrary.org/obo/APOLLO_SV_00000139>",
        "Antiviral Supply Schedule" => "<http://purl.obolibrary.org/obo/APOLLO_SV_00000051>",
        "Antiviral Administration Schedule" => "<http://purl.obolibrary.org/obo/APOLLO_SV_00000052>",
        "Antiviral Treatment Efficacy" => "<http://purl.obolibrary.org/obo/APOLLO_SV_00000048>",
        "Target Populations and Prioritization" => "<http://purl.obolibrary.org/obo/APOLLO_SV_00000127>",
        "School Closure Target Facilities" => "<http://purl.obolibrary.org/obo/APOLLO_SV_00000126>",
        "School Closure Duration" => "<http://purl.obolibrary.org/obo/APOLLO_SV_00000124>",
        "Reactive Control Measure Test" => "<http://purl.obolibrary.org/obo/APOLLO_SV_00000130>",
        "Reactive Control Measure Threshold" => "<http://purl.obolibrary.org/obo/APOLLO_SV_00000131>",
        "Ascertainment Fraction" => "<http://purl.obolibrary.org/obo/APOLLO_SV_00000135>",
        "Ascertainment Delay" => "<http://purl.obolibrary.org/obo/APOLLO_SV_00000134>",
        "Exposed" => "<http://purl.obolibrary.org/obo/APOLLO_SV_00000038>",
        "Susceptible" => "<http://purl.obolibrary.org/obo/APOLLO_SV_00000072>",
        "Infectious" => "<http://purl.obolibrary.org/obo/APOLLO_SV_00000040>",
        "Recovered" => "<http://purl.obolibrary.org/obo/APOLLO_SV_00000041>",
        "Number of Doses in Vaccination Course" => "not_defined",
        "Fixed Start Time" => "not_defined",
        "Treatment Reactive End Point Fraction" => "not_defined",
        "Number of Doses in Treatment Course" => "not_defined",
        "Control Measure Prioritization Scheme" => "not_defined",
    );

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

    // load the definitions from the apollo_sv_definitions file
    $toolTipTermArray = array();
    $lines = file(AROOT . "/apollo_sv_terms_v3.tab");
    foreach ($lines as $line_num => $line) {
        $splitLine = explode("\t", $line);
//        $line = mb_convert_encoding($line, "UTF-8");
        $toolTipTermArray[$splitLine[0]] = trim($splitLine[2]);
//        if ($line_num == 14) {
//        }
    }
    $toolTipTermArray["not_defined"] = "Definition not defined in ontology";

    $defaultRunLength = 150;

//    ChromePhp::log($apolloUriTermMap['Reproduction Number']);
//    ChromePhp::log($toolTipTermArray['<http://purl.obolibrary.org/obo/APOLLO_SV_00000043>']);
//    if ($handle) {
//        while (($buffer = fgets($handle)) !== false) {
//            ChromePhp::log($buffer);
////            $line = explode("\t", $buffer);
////            ChromePhp::log($line[3]);
//            
//        }
//        if (!feof($handle)) {
//            ChromePhp::log( "Error: unexpected fgets() fail\n");
//        }
//    } else {
//        echo "Could not get file handle\n";
//    }
    //begin Recognized Parameters
//	$PARAMS->rows [] = array ('id' => ++ $rowid, 'cell' => array ($rowid + 1,  'Recognized Parameters', '', '', '',
//		0, ++ $left, $left + 27, false, false ) );
    //shared
//    $pname = get_initial_date_text();
//    $pvalue = "2009-08-01";
//    $PARAMS->rows [] = array('id' => ++$rowid, 'cell' => array($rowid + 1,  $pname, $pvalue, '', '', 0, ++$left, ++$left, true, false));
//    $pname = $sectionParagraph . '--- Simulator Identification Section ---</p>';
//    $pvalue = '';
//    $PARAMS->rows [] = array('id' => ++$rowid, 'cell' => array($rowid + 1, $pname, $pvalue, '', '', 0, ++$left, ++$left, true, false));
//
//    $pname = 'Simulator Developer';
//    $pvalue = 'UPitt,PSC,CMU';
//    $PARAMS->rows [] = array('id' => ++$rowid, 'cell' => array($rowid + 1, $pname, $pvalue, '', '', 0, ++$left, ++$left, true, false));
//
//    $pname = 'Simulator Name';
//    $pvalue = 'FRED';
//    $PARAMS->rows [] = array('id' => ++$rowid, 'cell' => array($rowid + 1, $pname, $pvalue, '', '', 0, ++$left, ++$left, true, false));
//
//    $pname = 'Simulator Version';
//    $pvalue = '2.0.1';
//    $PARAMS->rows [] = array('id' => ++$rowid, 'cell' => array($rowid + 1, $pname, $pvalue, '', '', 0, ++$left, ++$left, true, false));

    $pname = $sectionParagraph . '--- Authentication Section ---</p>';
    $pvalue = '';
    $PARAMS->rows [] = array('id' => ++$rowid, 'cell' => array($rowid + 1, $pname, $pvalue, '', '', '', 0, ++$left, ++$left, true, false));

    $pname = 'Requester ID';
    $pvalue = 'UpittUser';
    $PARAMS->rows [] = array('id' => ++$rowid, 'cell' => array($rowid + 1, $pname, $pvalue, '', '', '', 0, ++$left, ++$left, true, false));


    $pname = 'Requester Password';
    $pvalue = 'UpittPassword';
    $PARAMS->rows [] = array('id' => ++$rowid, 'cell' => array($rowid + 1, $pname, $pvalue, '', '', '', 0, ++$left, ++$left, true, false));

    $pname = $sectionParagraph . '--- Simulator Time Specification Section ---</p>';
    $pvalue = '';
    $PARAMS->rows [] = array('id' => ++$rowid, 'cell' => array($rowid + 1, $pname, $pvalue, '', '', '', 0, ++$left, ++$left, true, false));


    $pname = get_time_step_unit_text();
    $pvalue = '';
    $PARAMS->rows [] = array('id' => ++$rowid, 'cell' => array($rowid + 1, $pname, $pvalue, '', '', 'The time unit such as “hours” or “days” for each time step of a simulation.  Time step unit is always used with time step value.', 0, ++$left, ++$left, true, false));

    $pname = get_time_step_value_text();
    $pvalue = '1';
    $PARAMS->rows [] = array('id' => ++$rowid, 'cell' => array($rowid + 1, $pname, $pvalue, '', '', $toolTipTermArray[$apolloUriTermMap[get_time_step_value_text()]], 0, ++$left, ++$left, true, false));

    $pname = get_run_length_text();
    $pvalue = $defaultRunLength;
    $PARAMS->rows [] = array('id' => ++$rowid, 'cell' => array($rowid + 1, $pname, $pvalue, '', '', $toolTipTermArray[$apolloUriTermMap[get_run_length_text()]], 0, ++$left, ++$left, true, false));

    $pname = $sectionParagraph . '--- Simulated Population Section ---</p>';
    $pvalue = '';
    $PARAMS->rows [] = array('id' => ++$rowid, 'cell' => array($rowid + 1, $pname, $pvalue, '', '', '', 0, ++$left, ++$left, true, false));

    $pname = 'Population Location';
    $pvalue = '42003';
    $PARAMS->rows [] = array('id' => ++$rowid, 'cell' => array($rowid + 1, $pname, $pvalue, '', '', $toolTipTermArray[$apolloUriTermMap['Population Location']], 0, ++$left, ++$left, true, false));

    $basePath = 'Initial Compartment Sizes'; //create base folder entry
    $PARAMS->rows [] = array('id' => ++$rowid, 'cell' => array($rowid + 1, $basePath, '', '', '', '', 0, ++$left, $left + 9, false, false));
    $basePath = deleteSpace($basePath);

    $pname = 'Susceptible';
    $pvalue = '1155854';
    $PARAMS->rows [] = array('id' => ++$rowid, 'cell' => array($rowid + 1, $pname, $pvalue, '', '', $toolTipTermArray[$apolloUriTermMap['Susceptible']], 1, ++$left, ++$left, true, false));

    $pname = 'Exposed';
    $pvalue = '6550';
    $PARAMS->rows [] = array('id' => ++$rowid, 'cell' => array($rowid + 1, $pname, $pvalue, '', '', $toolTipTermArray[$apolloUriTermMap['Exposed']], 1, ++$left, ++$left, true, false));
    
    $pname = 'Infectious';
    $pvalue = '7350';
    $PARAMS->rows [] = array('id' => ++$rowid, 'cell' => array($rowid + 1, $pname, $pvalue, '', '', $toolTipTermArray[$apolloUriTermMap['Infectious']], 1, ++$left, ++$left, true, false));

    $pname = 'Recovered';
    $pvalue = '48740';
    $PARAMS->rows [] = array('id' => ++$rowid, 'cell' => array($rowid + 1, $pname, $pvalue, '', '', $toolTipTermArray[$apolloUriTermMap['Recovered']], 1, ++$left, ++$left, true, false));


    $pname = $sectionParagraph . '--- Disease Section ---</p>';
    $pvalue = '';
    $PARAMS->rows [] = array('id' => ++$rowid, 'cell' => array($rowid + 1, $pname, $pvalue, '', '', '', 0, ++$left, ++$left, true, false));

    $pname = 'Disease Name';
    $pvalue = 'Influenza';
    $PARAMS->rows [] = array('id' => ++$rowid, 'cell' => array($rowid + 1, $pname, $pvalue, '', '', $toolTipTermArray[$apolloUriTermMap['Disease Name']], 0, ++$left, ++$left, true, false));

    $pname = 'Infectious Period';
    $pvalue = '4.0';
    $PARAMS->rows [] = array('id' => ++$rowid, 'cell' => array($rowid + 1, $pname, $pvalue, '', '', $toolTipTermArray[$apolloUriTermMap['Infectious Period']], 0, ++$left, ++$left, true, false));

    $pname = 'Latent Period';
    $pvalue = '2.0';
    $PARAMS->rows [] = array('id' => ++$rowid, 'cell' => array($rowid + 1, $pname, $pvalue, '', '', $toolTipTermArray[$apolloUriTermMap['Latent Period']], 0, ++$left, ++$left, true, false));

    $pname = 'Reproduction Number';
    $pvalue = '1.3';
    $PARAMS->rows [] = array('id' => ++$rowid, 'cell' => array($rowid + 1, $pname, $pvalue, '', '', $toolTipTermArray[$apolloUriTermMap['Reproduction Number']], 0, ++$left, ++$left, true, false));

    $pname = 'Asymptomatic Infection Fraction';
    $pvalue = '0.5';
    $PARAMS->rows [] = array('id' => ++$rowid, 'cell' => array($rowid + 1, $pname, $pvalue, '', '', $toolTipTermArray[$apolloUriTermMap['Asymptomatic Infection Fraction']], 0, ++$left, ++$left, true, false));

    $pname = $sectionParagraph . '--- Control Measures Section ---</p>';
    $pvalue = '';
    $PARAMS->rows [] = array('id' => ++$rowid, 'cell' => array($rowid + 1, $pname, $pvalue, '', '', '', 0, ++$left, ++$left, true, false));

    $basePath = 'Antiviral Control Measure'; //create base folder entry
    $PARAMS->rows [] = array('id' => ++$rowid, 'cell' => array($rowid + 1, $basePath, 0.0, '', '', '', 0, ++$left, $left + 19, false, false));
    $basePath = deleteSpace($basePath);

    $pname = 'Control Measure Compliance';
    $pvalue = '0.8';
    $PARAMS->rows [] = array('id' => ++$rowid, 'cell' => array($rowid + 1, $pname, $pvalue, '', '', $toolTipTermArray[$apolloUriTermMap['Control Measure Compliance']], 1, ++$left, ++$left, true, false));

    $pname = 'Control Measure Response Delay';
    $pvalue = '0.0';
    $PARAMS->rows [] = array('id' => ++$rowid, 'cell' => array($rowid + 1, $pname, $pvalue, '', '', $toolTipTermArray[$apolloUriTermMap['Control Measure Response Delay']], 1, ++$left, ++$left, true, false));

    $pname = 'Fixed Start Time';
    $pvalue = '0';
    $PARAMS->rows [] = array('id' => ++$rowid, 'cell' => array($rowid + 1, $pname, $pvalue, '', '', $toolTipTermArray[$apolloUriTermMap['Fixed Start Time']], 1, ++$left, ++$left, true, false));

    $pname = 'Antiviral Supply Schedule';
    $pvalue = array();
    for ($i = 0; $i < $defaultRunLength; $i++) {
        $pvalue[] = 2000.0;
    }
    $PARAMS->rows [] = array('id' => ++$rowid, 'cell' => array($rowid + 1, $pname, get_edit_hint_text(),
            'simulator/antiviral_supply_schedule.html', json_encode($pvalue), $toolTipTermArray[$apolloUriTermMap['Antiviral Supply Schedule']], 1, ++$left, ++$left, true, false));

    $pname = 'Antiviral Administration Schedule';
    $pvalue = array();
    for ($i = 0; $i < $defaultRunLength; $i++) {
        $pvalue[] = 2000.0;
    }
    $PARAMS->rows [] = array('id' => ++$rowid, 'cell' => array($rowid + 1, $pname, get_edit_hint_text(),
            'simulator/antiviral_admin_schedule.html', json_encode($pvalue), $toolTipTermArray[$apolloUriTermMap['Antiviral Administration Schedule']], 1, ++$left, ++$left, true, false));

    $pname = 'Antiviral Treatment Efficacy';
    $pvalue = '0.7';
    $PARAMS->rows [] = array('id' => ++$rowid, 'cell' => array($rowid + 1, $pname, $pvalue, '', '', $toolTipTermArray[$apolloUriTermMap['Antiviral Treatment Efficacy']], 1, ++$left, ++$left, true, false));


    $pname = 'Number of Doses in Treatment Course';
    $pvalue = '1';
    $PARAMS->rows [] = array('id' => ++$rowid, 'cell' => array($rowid + 1, $pname, $pvalue, '', '', $toolTipTermArray[$apolloUriTermMap['Number of Doses in Treatment Course']], 1, ++$left, ++$left, true, false));

    $pname = 'Treatment Reactive End Point Fraction';
    $pvalue = '1.0';
    $PARAMS->rows [] = array('id' => ++$rowid, 'cell' => array($rowid + 1, $pname, $pvalue, '', '', $toolTipTermArray[$apolloUriTermMap['Treatment Reactive End Point Fraction']], 1, ++$left, ++$left, true, false));

    $pname = 'Target Populations and Prioritization';
    $pvalue = '';
    $PARAMS->rows [] = array('id' => ++$rowid, 'cell' => array($rowid + 1, $pname, get_edit_hint_text(),
            'simulator/av_target_priority_pop.html', $pvalue, $toolTipTermArray[$apolloUriTermMap['Target Populations and Prioritization']], 1, ++$left, ++$left, true, false));

//    $pname = $sectionParagraph . '--- Vaccination Control Measure Section ---</p>';
//    $pvalue = '';
//    $PARAMS->rows [] = array('id' => ++$rowid, 'cell' => array($rowid + 1, $pname, $pvalue, '', '', '', 0, ++$left, ++$left, true, false));

    $basePath = 'Vaccine Control Measure'; //create base folder entry
    $PARAMS->rows [] = array('id' => ++$rowid, 'cell' => array($rowid + 1, $basePath, 0.0, '', '', '', 0, ++$left, $left + 19, false, false));
    $basePath = deleteSpace($basePath);


    $pname = 'Control Measure Compliance';
    $pvalue = '0.9';
    $PARAMS->rows [] = array('id' => ++$rowid, 'cell' => array($rowid + 1, $pname, $pvalue, '', '', $toolTipTermArray[$apolloUriTermMap['Control Measure Compliance']], 1, ++$left, ++$left, true, false));

    $pname = 'Control Measure Response Delay';
    $pvalue = '0';
    $PARAMS->rows [] = array('id' => ++$rowid, 'cell' => array($rowid + 1, $pname, $pvalue, '', '', $toolTipTermArray[$apolloUriTermMap['Control Measure Response Delay']], 1, ++$left, ++$left, true, false));

    $pname = 'Control Measure Prioritization Scheme';
    $pvalue = '';
    $PARAMS->rows [] = array('id' => ++$rowid, 'cell' => array($rowid + 1, $pname, $pvalue, '', '', $toolTipTermArray[$apolloUriTermMap['Control Measure Prioritization Scheme']], 1, ++$left, ++$left, true, false));

    $pname = 'Fixed Start Time';
    $pvalue = '0';
    $PARAMS->rows [] = array('id' => ++$rowid, 'cell' => array($rowid + 1, $pname, $pvalue, '', '', $toolTipTermArray[$apolloUriTermMap['Fixed Start Time']], 1, ++$left, ++$left, true, false));

    $pname = 'Vaccination Supply Schedule';
    $pvalue = array();
    for ($i = 0; $i < $defaultRunLength; $i++) {

        if ($i < 24) {
            $pvalue[] = 0.0;
        } else if ($i < 80) {
            $pvalue[] = 3500.0;
        } else if ($i < 110) {
            $pvalue[] = 10000.0;
        } else {
            $pvalue[] = 3500.0;
        }
    }

    $PARAMS->rows [] = array('id' => ++$rowid, 'cell' => array($rowid + 1, $pname, get_edit_hint_text(),
            'simulator/vaccination_supply_schedule.html', json_encode($pvalue), $toolTipTermArray[$apolloUriTermMap['Vaccination Supply Schedule']], 1, ++$left, ++$left, true, false));

    $pname = 'Vaccination Administration Schedule';
    $pvalue = array();
    for ($i = 0; $i < $defaultRunLength; $i++) {
        $pvalue[] = 5000.0;
    }
    $PARAMS->rows [] = array('id' => ++$rowid, 'cell' => array($rowid + 1, $pname, get_edit_hint_text(),
            'simulator/vaccination_admin_schedule.html', json_encode($pvalue), $toolTipTermArray[$apolloUriTermMap['Vaccination Administration Schedule']], 1, ++$left, ++$left, true, false));

    $pname = 'Vaccine Efficacy';
    $pvalue = '0.7';
    $PARAMS->rows [] = array('id' => ++$rowid, 'cell' => array($rowid + 1, $pname, $pvalue, '', '', $toolTipTermArray[$apolloUriTermMap['Vaccine Efficacy']], 1, ++$left, ++$left, true, false));


    $pname = 'Number of Doses in Vaccination Course';
    $pvalue = '1';
    $PARAMS->rows [] = array('id' => ++$rowid, 'cell' => array($rowid + 1, $pname, $pvalue, '', '', $toolTipTermArray[$apolloUriTermMap['Number of Doses in Vaccination Course']], 1, ++$left, ++$left, true, false));

    $pname = 'Vaccination Reactive End Point Fraction';
    $pvalue = '1.0';
    $PARAMS->rows [] = array('id' => ++$rowid, 'cell' => array($rowid + 1, $pname, $pvalue, '', '', $toolTipTermArray[$apolloUriTermMap['Vaccination Reactive End Point Fraction']], 1, ++$left, ++$left, true, false));


    $basePath = 'School Closure Control Measure'; //create base folder entry
    $PARAMS->rows [] = array('id' => ++$rowid, 'cell' => array($rowid + 1, $basePath, 0.0, '', '', '', 0, ++$left, $left + 31, false, false));
    $basePath = deleteSpace($basePath);

    $basePath = 'School Closure Reactive Trigger Control Measure'; //create base folder entry
    $PARAMS->rows [] = array('id' => ++$rowid, 'cell' => array($rowid + 1, $basePath, 0.0, '', '', '', 1, ++$left, $left + 17, false, false));
    $basePath = deleteSpace($basePath);

    $pname = 'Control Measure Compliance';
    $pvalue = '1.0';
    $PARAMS->rows [] = array('id' => ++$rowid, 'cell' => array($rowid + 1, $pname, $pvalue, '', '', $toolTipTermArray[$apolloUriTermMap['Control Measure Compliance']], 2, ++$left, ++$left, true, false));

    $pname = 'Control Measure Response Delay';
    $pvalue = '2';
    $PARAMS->rows [] = array('id' => ++$rowid, 'cell' => array($rowid + 1, $pname, $pvalue, '', '',  $toolTipTermArray[$apolloUriTermMap['Control Measure Response Delay']], 2, ++$left, ++$left, true, false));

    $pname = 'School Closure Target Facilities';
    $pvalue = '';
    $PARAMS->rows [] = array('id' => ++$rowid, 'cell' => array($rowid + 1, $pname, $pvalue, '', '', $toolTipTermArray[$apolloUriTermMap['School Closure Target Facilities']], 2, ++$left, ++$left, true, false));

    $pname = 'School Closure Duration';
    $pvalue = '56';
    $PARAMS->rows [] = array('id' => ++$rowid, 'cell' => array($rowid + 1, $pname, $pvalue, '', '', $toolTipTermArray[$apolloUriTermMap['School Closure Duration']], 2, ++$left, ++$left, true, false));

    $pname = 'Reactive Control Measure Test';
    $pvalue = 'Individual_schools';
    $PARAMS->rows [] = array('id' => ++$rowid, 'cell' => array($rowid + 1, $pname, $pvalue, '', '', $toolTipTermArray[$apolloUriTermMap['Reactive Control Measure Test']], 2, ++$left, ++$left, true, false));

    $pname = 'Reactive Control Measure Threshold';
    $pvalue = '0.03';
    $PARAMS->rows [] = array('id' => ++$rowid, 'cell' => array($rowid + 1, $pname, $pvalue, '', '', $toolTipTermArray[$apolloUriTermMap['Reactive Control Measure Threshold']], 2, ++$left, ++$left, true, false));

    $pname = 'Ascertainment Fraction';
    $pvalue = '1.0';
    $PARAMS->rows [] = array('id' => ++$rowid, 'cell' => array($rowid + 1, $pname, $pvalue, '', '', $toolTipTermArray[$apolloUriTermMap['Ascertainment Fraction']], 2, ++$left, ++$left, true, false));

    $pname = 'Ascertainment Delay';
    $pvalue = '0';
    $PARAMS->rows [] = array('id' => ++$rowid, 'cell' => array($rowid + 1, $pname, $pvalue, '', '', $toolTipTermArray[$apolloUriTermMap['Ascertainment Delay']], 2, ++$left, ++$left, true, false));

    $basePath = 'School Closure Fixed Start Time Control Measure'; //create base folder entry
    $PARAMS->rows [] = array('id' => ++$rowid, 'cell' => array($rowid + 1, $basePath, 0.0, '', '', '', 1, ++$left, $left + 11, false, false));
    $basePath = deleteSpace($basePath);

    $pname = 'Control Measure Compliance';
    $pvalue = '1.0';
    $PARAMS->rows [] = array('id' => ++$rowid, 'cell' => array($rowid + 1, $pname, $pvalue, '', '', $toolTipTermArray[$apolloUriTermMap['Control Measure Compliance']], 2, ++$left, ++$left, true, false));

    $pname = 'Control Measure Response Delay';
    $pvalue = '2';
    $PARAMS->rows [] = array('id' => ++$rowid, 'cell' => array($rowid + 1, $pname, $pvalue, '', '',  $toolTipTermArray[$apolloUriTermMap['Control Measure Response Delay']], 2, ++$left, ++$left, true, false));

    $pname = 'School Closure Target Facilities';
    $pvalue = '';
    $PARAMS->rows [] = array('id' => ++$rowid, 'cell' => array($rowid + 1, $pname, $pvalue, '', '', $toolTipTermArray[$apolloUriTermMap['School Closure Target Facilities']], 2, ++$left, ++$left, true, false));

    $pname = 'School Closure Duration';
    $pvalue = '56';
    $PARAMS->rows [] = array('id' => ++$rowid, 'cell' => array($rowid + 1, $pname, $pvalue, '', '', $toolTipTermArray[$apolloUriTermMap['School Closure Duration']], 2, ++$left, ++$left, true, false));

    $pname = 'Fixed Start Time';
    $pvalue = '0';
    $PARAMS->rows [] = array('id' => ++$rowid, 'cell' => array($rowid + 1, $pname, $pvalue, '', '', $toolTipTermArray[$apolloUriTermMap['Fixed Start Time']], 2, ++$left, ++$left, true, false));


    // extra row to help fix hidden scroll bar on OSX
    $pname = '';
    $pvalue = '';
    $PARAMS->rows [] = array('id' => ++$rowid, 'cell' => array($rowid + 1, $pname, $pvalue, '', '', '', 0, ++$left, ++$left, true, false));

    //new level under InitialCompartmentSizes
//    $basePath = 'Initial Compartment Sizes'; //create base folder entry
//    $PARAMS->rows [] = array('id' => ++$rowid, 'cell' => array($rowid + 1, $basePath, '', '', '', 0, ++$left, $left + 9, false, false));
//    $basePath = deleteSpace($basePath);
//
//    $pname = 'Susceptible';
//    $pvalue = '1157474';
//    $PARAMS->rows [] = array('id' => ++$rowid, 'cell' => array($rowid + 1, $pname, $pvalue, '', '', 1, ++$left, ++$left, true, false));
//
//    $pname = 'Exposed';
//    $pvalue = '0';
//    $PARAMS->rows [] = array('id' => ++$rowid, 'cell' => array($rowid + 1, $pname, $pvalue, '', '', 1, ++$left, ++$left, true, false));
//
//
//    $pname = 'Infectious';
//    $pvalue = '100';
//    $PARAMS->rows [] = array('id' => ++$rowid, 'cell' => array($rowid + 1, $pname, $pvalue, '', '', 1, ++$left, ++$left, true, false));
//
//    $pname = 'Recovered';
//    $pvalue = '60920';
//    $PARAMS->rows [] = array('id' => ++$rowid, 'cell' => array($rowid + 1, $pname, $pvalue, '', '', 1, ++$left, ++$left, true, false));
    // end InitialCompartmentSizes
    //end Recognized Parameters
    //begin Rnrecognized Parameters
//	$left += 1;
//	$PARAMS->rows [] = array ('id' => ++ $rowid, 'cell' => array ($rowid + 1, $unregIconHtml, 'Unrecognized Parameters', '', '', '', 0, ++ $left, ++$left, false, false ) );

    $PARAMS->records = count($PARAMS->rows);
    $PARAMS->page = 1;
    $PARAMS->total = count($PARAMS->rows);


    return $PARAMS;
}

?>