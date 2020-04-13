package com.games.services.wallet.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class WalletBalanceDTO {

	private BigDecimal amount;

	private String currencyCode;
}
