/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.pitt.apollo.libraryservicerestfrontend.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 *
 * @author nem41
 */
@ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = "No such enum value")  // 404
public class UnsupportedRunActionException extends Exception {

	public UnsupportedRunActionException(String message) {
		super(message);
	}
	
}
