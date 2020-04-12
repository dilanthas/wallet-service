package com.games.services.wallet.dto;

import com.games.services.wallet.exception.ErrorMessage;

import javax.validation.constraints.NotNull;

public class WalletDTO {

	@NotNull(message = "Wallet userId" + ErrorMessage.CANNOT_BE_EMPTY)
	private Long userId;

	@NotNull(message = "Wallet currencyCode" + ErrorMessage.CANNOT_BE_EMPTY)
	private String currencyCode;

	private WalletDTO(){

	}

	public Long getUserId() {
		return userId;
	}

	public String getCurrencyCode() {
		return currencyCode;
	}
}
