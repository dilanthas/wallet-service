package com.games.services.wallet.service;

import com.games.services.wallet.exception.WalletException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

import static com.games.services.wallet.exception.ErrorConstants.INSUFFICIENT_FUNDS_IN_WALLET;

/**
 * Service class to calculate wallet balance
 */
@Service
public class BalanceCalculator {

	public static String TRANSACTION_TYPE_DEBIT = "DB";

	public BigDecimal calculateWalletBalance(BigDecimal walletAmount, BigDecimal transactionAmount, String transactionType)
			throws
			WalletException {

		if (TRANSACTION_TYPE_DEBIT.equals(transactionType)) {
			// If the wallet amount is less than the debit amount , throw exception
			if (walletAmount.compareTo(transactionAmount) < 0) {
				throw new WalletException(INSUFFICIENT_FUNDS_IN_WALLET, HttpStatus.BAD_REQUEST.value());
			}
			return walletAmount.subtract(transactionAmount);
		} else {
			return walletAmount.add(transactionAmount);
		}
	}
}
