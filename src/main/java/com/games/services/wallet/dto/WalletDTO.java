package com.games.services.wallet.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class WalletDTO {

	private Long id;

	private Long userId;

	private BigDecimal amount;

	private String currencyCode;
}
