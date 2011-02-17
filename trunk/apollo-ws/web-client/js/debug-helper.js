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
 * Debug utility functions 
 *
 * @author Yang Hu <yah14@pitt.edu>
 */
function logex(objToWrite){
	console.log(objToWrite);
}

function logex2(objToWrite){
	console.log(JSON.stringify(objToWrite));
}

function logex2(name, obj){
	console.log(name + " : " + JSON.stringify(objToWrite));
}