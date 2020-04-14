package com.games.services.wallet.service;

import com.games.services.wallet.dto.TransactionDTO;
import com.games.services.wallet.exception.WalletException;
import com.games.services.wallet.model.Transaction;

import java.util.List;

public interface TransactionService {

	Transaction createTransaction(TransactionDTO transactionDTO) throws WalletException;

	List<Transaction> getAllTransactionByUser(Long userId) throws WalletException;
}
