package com.games.services.wallet.exception;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ErrorMessage {

	private String errorMessage;
	private Map<String, Object> additionalData = new HashMap<>();
	private Date timestamp;

}
