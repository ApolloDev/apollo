package edu.pitt.apollo.db.exceptions;

import edu.pitt.apollo.db.exceptions.ApolloDatabaseException;

public class ApolloDatabaseRecordNotInsertedException extends ApolloDatabaseException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2901533445932290045L;

	public ApolloDatabaseRecordNotInsertedException() {

	}

	public ApolloDatabaseRecordNotInsertedException(String message) {
		super(message);

	}

	public ApolloDatabaseRecordNotInsertedException(Throwable cause) {
		super(cause);

	}

	public ApolloDatabaseRecordNotInsertedException(String message, Throwable cause) {
		super(message, cause);

	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {


	}

}
