<?php

require_once AROOT . '/models/seir/exec.inc';
require_once AROOT . '/models/compartment/exec.inc';
//require_once AROOT . '/models/agent_based/exec.inc';

require_once AROOT . '/models/seir/default_param.inc';
require_once AROOT . '/models/compartment/default_param.inc';
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
//		switch ($snomed) {
//			case $this->getSNOMEDCode ('Influenza') :
//				if ($modelType == 'Compartment')
					return seir_exec ($params, $simulatorDev, $simulatorName, $simulatorVer);
//				break;

//			case $this->getSNOMEDCode ('Anthrax') :
//				if ($modelType == 'Compartment')
//					return compartment_exec ( $params );
//				break;

//			default :
//				throw new Exception ( 'Can not find right handler! Model Type : ' . $modelType . ' SNOMED : ' . $snomed );
//				break;
//		}
	}

	public function parameters() {
//		switch ($snomed) {
//		case $this->getSNOMEDCode('Influenza'):
//			if ($modelType == 'Compartment');
				return seir_get_default_param_structure();
//		break;

//		case $this->getSNOMEDCode('Anthrax'):
//			if ($modelType == 'Compartment');
//				return compartment_get_default_param_structure();
//		break;

//		default:
//			throw new Exception('Unknow Model Type : ' . $modelType);
//		break;
	}
	

//	private $wsdl = 'http://research.rods.pitt.edu/ApolloModelService/services/ServiceManagerPort?wsdl';
//	private $wsdl = 'http://130.49.206.36:8080/ApolloModelService/services/ServiceManagerPort?wsdl';
//        private $wsdl = 'http://research3.rods.pitt.edu:9001/SeirEpiModelService/services/seirepimodelsimulator?wsdl';
        private $wsdl = 'http://localhost:8070/apolloservice/services/apolloservice?wsdl';
//	private $snomedDB = array();
}

?>