<?php

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

class location_data {

    private $stateCountyLists = array(array());
    private $stateList = array();
    private $stateOtherLocationsList = array();

    function initializeLocationData() {
        $lines = file("2010_census_counties.csv");
        foreach ($lines as $line_num => $line) {
            $splitLine = explode(",", $line);
            $state = $splitLine[5];
            $stateNum = $splitLine[3];
            $county = $splitLine[6];
            $countyNum = $splitLine[4];
            
            if ($state == "STNAME") {
                continue; // skip header line
            }

            if (strlen($stateNum) < 2) {
                $stateNum = '0' . $stateNum;
            }
            if (strlen($countyNum) < 3) {
                if (strlen($countyNum) < 2) {
                    $countyNum = '00' . $countyNum;
                } else {
                    $countyNum = '0' . $countyNum;
                }
            }
            
            $this->stateList[$state] = $stateNum; // this will get overwritten many times, but should be the same each time

            if ($countyNum != 0) {
                $this->stateCountyLists[$stateNum][$stateNum . $countyNum] = $county;
            } else {
                $this->stateCountyLists[$stateNum][$stateNum . $countyNum] = '0'; // zero means the whole state
            }
        }
        
      //  $this->stateList['WA_Seattle-Tacoma-Bellevue_MSA'] = '42660';
      //  $this->stateOtherLocationsList['WA_Seattle-Tacoma-Bellevue_MSA'] = '42660';
        ksort($this->stateList);
    }

    function getJSONCountyListForState($state) {

        echo json_encode($this->stateCountyLists[$state]);
    }

    function getJSONStateList() {
        
        echo json_encode($this->stateList);
    }
    
    function getJSONStateOtherLocations() {
        
        echo json_encode($this->stateOtherLocationsList);
    }
}

?>
