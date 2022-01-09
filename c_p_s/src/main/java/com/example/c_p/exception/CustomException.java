package com.example.c_p.exception;

/**
 * @param message defines an Exception message in String format
 * @return returns an Exception message correlating to the custom Exception
 */
public class CustomException extends Exception {

	private static final long serialVersionUID = 1L;

	public CustomException(String message) {
		super(message);
	}
}
