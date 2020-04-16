package com.games.services.wallet.service;

import com.games.services.wallet.dto.TransactionDTO;
import com.games.services.wallet.exception.NoDataFoundException;
import com.games.services.wallet.exception.WalletException;
import com.games.services.wallet.model.Transaction;
import com.games.services.wallet.model.TransactionType;
import com.games.services.wallet.model.Wallet;
import com.games.services.wallet.repository.TransactionRepository;
import com.games.services.wallet.repository.TransactionTypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static com.games.services.wallet.exception.ErrorConstants.TRANSACTION_REF_ALREADY_EXISTS;
import static com.games.services.wallet.exception.ErrorConstants.UNSUPPORTED_TRANSACTION_TYPE;

@Service
public class TransactionServiceImpl implements TransactionService {

	@Autowired
	private TransactionRepository transactionRepository;

	@Autowired
	private TransactionTypeRepository transactionTypeRepository;

	@Autowired
	private WalletService walletService;

	@Transactional(isolation = Isolation.SERIALIZABLE, propagation = Propagation.REQUIRED, rollbackFor = WalletException.class)
	@Override
	public Transaction createTransaction(TransactionDTO transactionDTO)
			throws WalletException, NoDataFoundException {
		try {
			String transactionType = transactionDTO.getTypeCode();
			Optional<TransactionType> transactionTypeOp = transactionTypeRepository.findById(transactionType);
			if (!transactionTypeOp.isPresent()) {
				throw new NoDataFoundException(String.format(UNSUPPORTED_TRANSACTION_TYPE, transactionType));
			}

			Wallet wallet = walletService.updateWalletAmount(transactionDTO.getUserId(), transactionDTO.getAmount(),
					transactionDTO.getCurrencyCode(), transactionType);

			Transaction transaction = Transaction.builder().transactionRef(transactionDTO.getTransactionRef()).wallet(wallet)
					.type(transactionTypeOp.get()).amount(transactionDTO.getAmount()).currency(wallet.getCurrency())
					.description(transactionDTO.getDescription()).transactionDate(new Date()).build();

			return transactionRepository.save(transaction);

		}
		catch (DataIntegrityViolationException ex) {
			throw new WalletException(String.format(TRANSACTION_REF_ALREADY_EXISTS, transactionDTO.getTransactionRef()),
					HttpStatus.BAD_REQUEST.value());
		}

	}

	@Override
	public List<Transaction> getAllTransactionByUser(Long userId) throws WalletException {
		Wallet wallet = walletService.getWalletByUserId(userId);
		List<Transaction> transactions = wallet.getTransactions();

		// Sort by the transaction date to get the latest transactions first
		transactions.sort(Comparator.comparing(Transaction::getTransactionDate).reversed());

		return transactions;
	}
}
