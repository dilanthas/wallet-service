package com.games.services.wallet.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class WalletExceptionHandler extends ResponseEntityExceptionHandler {

	Logger logger = LoggerFactory.getLogger(this.getClass());

	@ExceptionHandler(NoDataFoundException.class)
	public ResponseEntity<ErrorMessage> handleNoDataFoundException(
			NoDataFoundException ex, WebRequest request) {

		request.getDescription(false);
		Map<String, Object> extraInfo = new HashMap<>();
		extraInfo.put("extraInfo",request.getDescription(false));

		return new ResponseEntity<>(buildErrorMessage(new Date(),ex.getMessage(),extraInfo), HttpStatus.NOT_FOUND);
	}

	@ExceptionHandler(value
			= { WalletException.class })
	protected ResponseEntity<ErrorMessage> handleWalletException(
			WalletException ex, WebRequest request) {
		logger.error(ex.toString());
		HttpStatus status = HttpStatus.valueOf(ex.getErrorCode());
		Map<String, Object> extraInfo = new HashMap<>();
		extraInfo.put("extraInfo",request.getDescription(false));

		return new ResponseEntity<>(buildErrorMessage(new Date(),ex.getMessage(),extraInfo), status);
	}

	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
			HttpHeaders headers, HttpStatus status,
			WebRequest request) {
		String errorMessage = ex.getBindingResult().getFieldErrors().stream()
				.map(DefaultMessageSourceResolvable::getDefaultMessage)
				.findFirst()
				.orElse(ex.getMessage());
		Map<String, Object> extraInfo = new HashMap<>();
		return new ResponseEntity<>(buildErrorMessage(new Date(),errorMessage,extraInfo),HttpStatus.BAD_REQUEST);

	}

	private ErrorMessage buildErrorMessage(Date currentDate,String message,Map<String, Object> extraInfo){
		return ErrorMessage.builder().timestamp(currentDate).errorMessage(message).additionalData(extraInfo).build();

	}
}
