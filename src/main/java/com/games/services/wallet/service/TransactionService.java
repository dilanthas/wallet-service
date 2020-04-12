package com.games.services.wallet.service;

import com.games.services.wallet.exception.WalletException;
import com.games.services.wallet.model.Transaction;

import java.math.BigDecimal;

public interface TransactionService {

	Transaction createTransaction(Long transactionId,Long walletId,String transactionType, BigDecimal amount,String currencyCode) throws WalletException;
}
