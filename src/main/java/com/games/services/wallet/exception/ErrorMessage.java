package com.games.services.wallet.exception;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class ErrorMessage {

	private String errorCode;
	private String errorMessage;
	private Map<String, Object> additionalData = new HashMap<>();
	private Date timestamp;

}
