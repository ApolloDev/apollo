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

require_once 'models\seir\default_param.inc';
require_once 'models\compartment\default_param.inc';
require_once 'models\agent_based\default_param.inc';

use models\seir as seir;
use models\compartment as compartment;
use models\agent_based as agent_based;

$modelName = $_GET['model'];

if (strcmp($modelName, 'SEIR') == 0)
	$param_struct = seir\get_default_param_structure();
if (strcmp($modelName, 'Compartment') == 0)
	$param_struct = compartment\get_default_param_structure();
if (strcmp($modelName, 'AgentBased') == 0)
	$param_struct = agent_based\get_default_param_structure();

 echo json_encode($param_struct);
?>