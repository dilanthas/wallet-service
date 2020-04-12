package com.games.services.wallet.service;

import com.games.services.wallet.exception.WalletException;
import com.games.services.wallet.model.Transaction;
import com.games.services.wallet.model.TransactionType;
import com.games.services.wallet.model.Wallet;
import com.games.services.wallet.repository.CurrencyRepository;
import com.games.services.wallet.repository.TransactionRepository;
import com.games.services.wallet.repository.TransactionTypeRepository;
import com.games.services.wallet.repository.WalletRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static com.games.services.wallet.exception.ErrorMessage.UNSUPPORTED_TRANSACTION_TYPE;

@Service
public class TransactionServiceImpl implements TransactionService {

	@Autowired
	private TransactionRepository transactionRepository;

	@Autowired
	private CurrencyRepository currencyRepository;

	@Autowired
	private TransactionTypeRepository transactionTypeRepository;

	@Autowired
	private WalletRepository walletRepository;

	@Autowired
	private WalletService walletService;

	@Override
	public Transaction createTransaction(String transactionRef, Long walletId, String transactionType,
			BigDecimal amount,
			String currencyCode, String description)
			throws WalletException {
		try {
			Optional<TransactionType> transactionTypeOp = transactionTypeRepository.findById(transactionType);
			if (!transactionTypeOp.isPresent()) {
				throw new WalletException(String.format(UNSUPPORTED_TRANSACTION_TYPE, transactionType),
						HttpStatus.BAD_REQUEST.value());
			}

			Wallet wallet = walletService.updateWalletAmount(walletId, amount, currencyCode, transactionType);

			return transactionRepository
					.save(new Transaction(transactionRef, transactionTypeOp.get(), amount, wallet.getCurrency(), description,
							wallet));

		}
		catch (Exception ex) {
			throw new WalletException(ex.getMessage(), HttpStatus.BAD_REQUEST.value());
		}
	}

	@Override
	public List<Transaction> getAllTransactionByUser(Long userId) throws WalletException {
		Wallet wallet = walletService.getWalletByUserId(userId);
		return wallet.getTransactions();
	}
}
