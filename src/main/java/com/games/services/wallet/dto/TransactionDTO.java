package com.games.services.wallet.dto;

import com.games.services.wallet.exception.ErrorConstants;
import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.Date;

@Data
public class TransactionDTO {

	@NotNull(message = "Transaction walletId" + ErrorConstants.CANNOT_BE_EMPTY)
	private Long walletId;

	@NotNull(message = "Transaction transactionRef" + ErrorConstants.CANNOT_BE_EMPTY)
	private String transactionRef;

	@NotNull(message = "Transaction transactionType" + ErrorConstants.CANNOT_BE_EMPTY)
	private String typeCode;

	@Min(0)
	@NotNull(message = "Transaction amount" + ErrorConstants.CANNOT_BE_EMPTY)
	private BigDecimal amount;

	@NotNull(message = "Transaction currencyCode" + ErrorConstants.CANNOT_BE_EMPTY)
	private String currencyCode;

	@NotNull(message = "Transaction description" + ErrorConstants.CANNOT_BE_EMPTY)
	private String description;

	private Date transactionDate;

}
