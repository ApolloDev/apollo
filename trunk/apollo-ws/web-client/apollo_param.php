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

define('AROOT', getcwd());

require_once AROOT . '/apollo/constant.inc';

require_once AROOT . '/models/seir/default_param.inc';
require_once AROOT . '/models/compartment/default_param.inc';
require_once AROOT . '/models/agent_based/default_param.inc';

$modelType = $_GET['model'];
$snomed = $_GET['snomed'];

try {

	switch ($snomed) {
		case get_snomed('Influenza'):
			if ($modelType == 'Compartment');
				$param_struct = seir_get_default_param_structure();
		break;

		case get_snomed('Anthrax'):
			if ($modelType == 'Compartment');
				$param_struct = compartment_get_default_param_structure();
		break;

		default:
			die('Unknow Model Type : ' . $modelType);
		break;
	}

	echo json_encode($param_struct);
}catch (Exception $e){
	echo json_encode($e->getMessage());
}
?>