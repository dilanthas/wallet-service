package com.games.services.wallet.dto;

import com.games.services.wallet.exception.ErrorConstants;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TransactionDTO {

	@NotNull(message = "Transaction walletId" + ErrorConstants.CANNOT_BE_EMPTY)
	private Long walletId;

	@NotNull(message = "Transaction transactionRef" + ErrorConstants.CANNOT_BE_EMPTY)
	@NotBlank(message = "Transaction transactionRef" + ErrorConstants.CANNOT_BE_EMPTY)
	private String transactionRef;

	@NotNull(message = "Transaction transactionType" + ErrorConstants.CANNOT_BE_EMPTY)
	@NotBlank(message = "Transaction transactionType" + ErrorConstants.CANNOT_BE_EMPTY)
	private String typeCode;

	@Min(0)
	@NotNull(message = "Transaction amount" + ErrorConstants.CANNOT_BE_EMPTY)
	private BigDecimal amount;

	@NotNull(message = "Transaction currencyCode" + ErrorConstants.CANNOT_BE_EMPTY)
	@NotBlank(message = "Transaction currencyCode" + ErrorConstants.CANNOT_BE_EMPTY)
	private String currencyCode;

	@NotNull(message = "Transaction description" + ErrorConstants.CANNOT_BE_EMPTY)
	@NotBlank(message = "Transaction description" + ErrorConstants.CANNOT_BE_EMPTY)
	private String description;

	private Date transactionDate;

}
