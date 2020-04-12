package com.games.services.wallet.exception;

public class WalletException extends Exception {

	private int errorCode;

	public WalletException(String message , int errorCode){
		super(message);
		this.errorCode = errorCode;
	}

	public int getErrorCode() {
		return errorCode;
	}
}
