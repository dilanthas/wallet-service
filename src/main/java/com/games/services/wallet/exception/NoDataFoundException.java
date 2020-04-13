package com.games.services.wallet.exception;

public class NoDataFoundException extends RuntimeException {

	public static final String NO_DATA_FOUND_ERROR = "%s with id %s not found in the system";

	public NoDataFoundException(String message) {
		super(message);
	}

	public NoDataFoundException(String data, Long id) {
		super(String.format(NO_DATA_FOUND_ERROR, data, id));
	}
}
