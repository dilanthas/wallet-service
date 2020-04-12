package com.games.services.wallet.service;

import com.games.services.wallet.exception.WalletException;
import com.games.services.wallet.model.Transaction;

import java.math.BigDecimal;
import java.util.List;

public interface TransactionService {

	Transaction createTransaction(String transactionRef,Long walletId,String transactionType, BigDecimal amount,String currencyCode,String description) throws WalletException;

	List<Transaction> getAllTransactionByUser(Long userId) throws WalletException;
}
