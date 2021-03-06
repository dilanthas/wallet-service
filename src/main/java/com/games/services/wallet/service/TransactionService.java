package com.games.services.wallet.service;

import com.games.services.wallet.dto.TransactionDTO;
import com.games.services.wallet.exception.NoDataFoundException;
import com.games.services.wallet.exception.WalletException;
import com.games.services.wallet.entity.Transaction;

import java.util.List;

public interface TransactionService {

	Transaction createTransaction(TransactionDTO transactionDTO) throws WalletException, NoDataFoundException;

	List<Transaction> getAllTransactionByUser(Long userId) throws WalletException;
}
