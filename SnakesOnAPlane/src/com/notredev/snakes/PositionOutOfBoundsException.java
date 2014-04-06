package com.notredev.snakes;

public class PositionOutOfBoundsException extends RuntimeException {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	PositionOutOfBoundsException(String error) {
		super(error);
	}
}
