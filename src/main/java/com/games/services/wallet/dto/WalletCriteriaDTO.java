package com.games.services.wallet.dto;

import com.games.services.wallet.exception.ErrorConstants;

import javax.validation.constraints.NotNull;

public class WalletCriteriaDTO {

	@NotNull(message = "Wallet userId" + ErrorConstants.CANNOT_BE_EMPTY)
	private Long userId;

	@NotNull(message = "Wallet currencyCode" + ErrorConstants.CANNOT_BE_EMPTY)
	private String currencyCode;

	public WalletCriteriaDTO(){

	}

	public Long getUserId() {
		return userId;
	}

	public String getCurrencyCode() {
		return currencyCode;
	}
}
