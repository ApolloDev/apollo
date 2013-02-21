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
//namespace apollo;

class ModelID {
	public function __construct($groupid, $modeltype, $modelid, $version) {
		$this->groupid = $groupid;
		$this->modeltype = $modeltype;
		$this->modelid = $modelid;
		$this->version = $version;
		//TODO
		$this->snomed_cid = -1;
	}

	private $groupid;
	private $modeltype;
	private $modelid;
	private $version;
	private $snomed_cid;
};

class RunResultRequestObjType{
	public function __construct($RunId, $Username, $Password) {
		$this->RunId = $RunId;
		$this->Username = $Username;
		$this->Password = $Password;
	}

	private $RunId;
	private $Username;
	private $Password;
	/**
	 * @return the $RunId
	 */
	public function getRunId() {
		return $this->RunId;
	}

	/**
	 * @return the $Username
	 */
	public function getUsername() {
		return $this->Username;
	}

	/**
	 * @return the $Password
	 */
	public function getPassword() {
		return $this->Password;
	}

	/**
	 * @param field_type $RunId
	 */
	public function setRunId($RunId) {
		$this->RunId = $RunId;
	}

	/**
	 * @param field_type $Username
	 */
	public function setUsername($Username) {
		$this->Username = $Username;
	}

	/**
	 * @param field_type $Password
	 */
	public function setPassword($Password) {
		$this->Password = $Password;
	}

};

class Response {
	// The data that apollo want to pass to the client
	public $data;
	// The exception record if something wrong happen
	public $exception;
};
?>