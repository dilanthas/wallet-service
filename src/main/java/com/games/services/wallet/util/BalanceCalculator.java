package com.games.services.wallet.util;

import com.games.services.wallet.exception.WalletException;
import org.springframework.http.HttpStatus;

import java.math.BigDecimal;

import static com.games.services.wallet.exception.ErrorMessage.INSUFFICIENT_FUNDS_IN_WALLET;

public class BalanceCalculator {
	public static String TRANSACTION_TYPE_CREDIT = "CR";
	public static String TRANSACTION_TYPE_DEBIT = "DB";

	public static BigDecimal calculateWalletBalance(Long walletId ,BigDecimal walletAmount , BigDecimal transactionAmount,String transactionType) throws
			WalletException {

		if(TRANSACTION_TYPE_DEBIT.equals(transactionType)){
			if(walletAmount.compareTo(transactionAmount) < 0){
				throw new WalletException(String.format(INSUFFICIENT_FUNDS_IN_WALLET,walletId), HttpStatus.BAD_REQUEST.value());
			}
			return walletAmount.subtract(transactionAmount);
		}else {
			return walletAmount.add(transactionAmount);
		}
	}
}
