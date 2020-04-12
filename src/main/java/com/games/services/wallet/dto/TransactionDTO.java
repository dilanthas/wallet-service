package com.games.services.wallet.dto;

import com.games.services.wallet.exception.ErrorMessage;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

public class TransactionDTO {

	@NotNull(message = "Transaction walletId" + ErrorMessage.CANNOT_BE_EMPTY)
	private Long walletId;

	@NotNull(message = "Transaction transactionRef" + ErrorMessage.CANNOT_BE_EMPTY)
	private String transactionRef;

	@NotNull(message = "Transaction transactionType" + ErrorMessage.CANNOT_BE_EMPTY)
	private String transactionType;

	@Min(0)
	@NotNull(message = "Transaction amount" + ErrorMessage.CANNOT_BE_EMPTY)
	private BigDecimal amount;

	@NotNull(message = "Transaction currencyCode" + ErrorMessage.CANNOT_BE_EMPTY)
	private String currencyCode;

	@NotNull(message = "Transaction description" + ErrorMessage.CANNOT_BE_EMPTY)
	private String description;

	private TransactionDTO() {

	}

	public Long getWalletId() {
		return walletId;
	}

	public String getTransactionType() {
		return transactionType;
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public String getCurrencyCode() {
		return currencyCode;
	}

	public String getDescription() {
		return description;
	}

	public String getTransactionRef() {
		return transactionRef;
	}
}
