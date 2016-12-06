package edu.pitt.apollo.db.exceptions;

import edu.pitt.apollo.db.exceptions.ApolloDatabaseException;

public class ApolloDatabaseKeyNotFoundException extends ApolloDatabaseException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1182514340181821837L;

	public ApolloDatabaseKeyNotFoundException() {

	}

	public ApolloDatabaseKeyNotFoundException(String message) {
		super(message);

	}

	public ApolloDatabaseKeyNotFoundException(Throwable cause) {
		super(cause);

	}

	public ApolloDatabaseKeyNotFoundException(String message, Throwable cause) {
		super(message, cause);

	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {


	}

}
