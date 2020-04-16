package com.games.services.wallet.service;

import com.games.services.wallet.exception.NoDataFoundException;
import com.games.services.wallet.exception.WalletException;
import com.games.services.wallet.model.Currency;
import com.games.services.wallet.model.Wallet;
import com.games.services.wallet.repository.CurrencyRepository;
import com.games.services.wallet.repository.WalletRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.Date;

import static com.games.services.wallet.exception.ErrorConstants.CURRENCY_MISMATCH_WITH_WALLET;
import static com.games.services.wallet.exception.ErrorConstants.CURRENCY_NOT_SUPPORTED;
import static com.games.services.wallet.exception.ErrorConstants.WALLET_EXISTS_FOR_USER;
import static com.games.services.wallet.exception.ErrorConstants.WALLET_ID_NOT_FOUND;
import static com.games.services.wallet.exception.ErrorConstants.WALLET_NOT_FOUND_FOR_USER;

@Service
public class WalletServiceImpl implements WalletService {

	private Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private WalletRepository walletRepository;

	@Autowired
	private CurrencyRepository currencyRepository;

	private String WALLET_ENTITY_NAME = "Wallet";

	@Autowired
	private BalanceCalculator balanceCalculator;

	/**
	 * Find wallet by wallet id
	 *
	 * @param id wallet id
	 * @return - Wallet
	 */
	@Override
	public Wallet getWalletById(@NotNull Long id) {

		return walletRepository.findById(id).orElseThrow(() -> new NoDataFoundException(WALLET_ENTITY_NAME, id));

	}

	/**
	 * Find wallet by user id
	 *
	 * @param userId
	 * @return
	 */
	@Override
	public Wallet getWalletByUserId(@NotNull Long userId) {
		Wallet wallet = walletRepository.findByUserId(userId);

		if (wallet == null) {
			throw new NoDataFoundException(String.format(WALLET_NOT_FOUND_FOR_USER, userId));
		}
		return wallet;
	}

	/**
	 * Create and return wallet for given criterria
	 *
	 * @param userId
	 * @param currencyCode
	 * @return Wallet
	 * @throws WalletException
	 */
	@Override
	public Wallet createWallet(Long userId, String currencyCode) throws WalletException {

		logger.debug("Wallet Criteria userId: " + userId + " currency :" + currencyCode);

		Currency currency = currencyRepository.findById(currencyCode).orElseThrow(
				() -> new WalletException(String.format(CURRENCY_NOT_SUPPORTED, currencyCode),
						HttpStatus.BAD_REQUEST.value()));

		// Currently one user can have one wallet only.if wallet already exists for the given user throw exception
		Wallet existingWallet = walletRepository.findByUserId(userId);

		if (existingWallet != null) {
			throw new WalletException(String.format(WALLET_EXISTS_FOR_USER, userId), HttpStatus.BAD_REQUEST.value());
		}

		Wallet wallet = walletRepository
				.save(Wallet.builder().userId(userId).amount(BigDecimal.ZERO).currency(currency).lastUpdated(new Date())
						.build());

		return wallet;
	}

	/**
	 * Update wallet balance based on the transaction type per user
	 *
	 * @param userId
	 * @param amount
	 * @param currencyCode
	 * @param transactionType
	 * @return
	 * @throws WalletException - For debit transactions if the wallet does not have enough balance it will throw WalletException
	 */
	@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.SERIALIZABLE, rollbackFor = WalletException.class)
	@Override
	public Wallet updateWalletAmount(Long userId, BigDecimal amount, String currencyCode, String transactionType)
			throws WalletException {

		logger.debug("Updating wallet for user : " + userId + " amount :" + amount + " currency :" + currencyCode);

		Wallet wallet = walletRepository.findByUserId(userId);

		if (wallet == null) {
			throw new NoDataFoundException(String.format(WALLET_ID_NOT_FOUND, userId));
		}

		String walletCurrency = wallet.getCurrency().getCode();

		if (!walletCurrency.equals(currencyCode)) {
			throw new WalletException(String.format(CURRENCY_MISMATCH_WITH_WALLET, walletCurrency, currencyCode),
					HttpStatus.BAD_REQUEST.value());
		}

		// Calculate the actual balance of the wallet
		BigDecimal actualAmount = balanceCalculator.calculateWalletBalance(wallet.getAmount(), amount, transactionType);

		logger.debug("New wallet balance : " + actualAmount + " walletId :" + wallet.getId());

		wallet.setAmount(actualAmount);
		wallet.setLastUpdated(new Date());

		return walletRepository.save(wallet);
	}

}
