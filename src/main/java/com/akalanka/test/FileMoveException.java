package com.akalanka.test;

/**
 * Exception specific to file move operation.
 * 
 * @author akalanka
 *
 */
public class FileMoveException extends Exception {

	public FileMoveException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public FileMoveException(String message, Throwable cause) {
		super(message, cause);
	}

	public FileMoveException(String message) {
		super(message);
	}
	
	

}
