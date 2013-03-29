<?php

require_once AROOT . '/simulator/exec.inc';
//require_once AROOT . '/simulator/compartment/exec.inc';
//require_once AROOT . '/models/agent_based/exec.inc';

require_once AROOT . '/simulator/default_param.inc';

//require_once AROOT . '/simulator/compartment/default_param.inc';
//require_once AROOT . '/models/agent_based/default_param.inc';

class apollo {

    function __construct() {
        //init snomed db
        $this->snomedDB ['Influenza'] = 442696006;
        $this->snomedDB ['Anthrax'] = 21927003;
    }

    /**
     * @return the $wsdl
     */
    public function getWSDL() {
        return $this->wsdl;
    }

    /**
     * @param field_type $wsdl
     */
    public function setWSDL($wsdl) {
        $this->wsdl = $wsdl;
    }

    /**
     * @return the $snomedDB
     */
    public function getSNOMEDCode($diseaseName) {
        return $this->snomedDB [$diseaseName];
    }

    public function exec($params, $simulatorDev, $simulatorName, $simulatorVer) {

        return exec_simulator($params, $simulatorDev, $simulatorName, $simulatorVer);
    }

    public function parameters() {
        return get_default_param_structure();
    }

//	private $wsdl = 'http://research.rods.pitt.edu/ApolloModelService/services/ServiceManagerPort?wsdl';
//	private $wsdl = 'http://130.49.206.36:8080/ApolloModelService/services/ServiceManagerPort?wsdl';
//        private $wsdl = 'http://research3.rods.pitt.edu:9001/SeirEpiModelService/services/seirepimodelsimulator?wsdl';
    private $wsdl = 'http://localhost:8080/apolloservice/services/apolloservice?wsdl';
//	private $snomedDB = array();
}

?>