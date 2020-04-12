package com.games.services.wallet.service;

import com.games.services.wallet.exception.WalletException;
import com.games.services.wallet.model.Currency;
import com.games.services.wallet.model.Wallet;
import com.games.services.wallet.repository.CurrencyRepository;
import com.games.services.wallet.repository.WalletRepository;
import com.games.services.wallet.util.BalanceCalculator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.Date;
import java.util.Optional;

import static com.games.services.wallet.exception.ErrorMessage.CURRENCY_MISMATCH_WITH_WALLET;
import static com.games.services.wallet.exception.ErrorMessage.CURRENCY_NOT_SUPPORTED;
import static com.games.services.wallet.exception.ErrorMessage.WALLET_ID_NOT_FOUND;

@Service
public class WalletServiceImpl implements WalletService {

	@Autowired
	private WalletRepository walletRepository;

	@Autowired
	private CurrencyRepository currencyRepository;

	@Override
	public Wallet getWalletById(@NotNull Long id) throws WalletException {
		Optional<Wallet> optionalWallet = walletRepository.findById(id);
		if(!optionalWallet.isPresent()){
			throw new WalletException(String.format(WALLET_ID_NOT_FOUND,id), HttpStatus.BAD_REQUEST.value());
		}
		return optionalWallet.get();
	}

	@Override
	public Wallet getWalletByUserId(@NotNull Long userId) throws WalletException {
		return walletRepository.findByUserId(userId);
	}

	@Override
	public BigDecimal getWalletBalanceByUserId(@NotNull Long userId) throws WalletException {
		Wallet wallet = walletRepository.findByUserId(userId);
		return wallet.getAmount();
	}

	@Override
	public Wallet createWallet(Long userId, String currencyCode) throws WalletException {

		Optional<Currency> currencyOp = currencyRepository.findById(currencyCode);
		if(!currencyOp.isPresent()){
			throw new WalletException(String.format(CURRENCY_NOT_SUPPORTED,currencyCode), HttpStatus.BAD_REQUEST.value());
		}
		return walletRepository.save(new Wallet(userId,BigDecimal.ZERO,currencyOp.get()));
	}

	@Override
	public Wallet updateWalletAmount(Long walletId, BigDecimal amount, String currencyCode, String transactionType) throws WalletException {
		Optional<Currency> currencyOp = currencyRepository.findById(currencyCode);
		if(!currencyOp.isPresent()){
			throw new WalletException(String.format(CURRENCY_NOT_SUPPORTED,currencyCode), HttpStatus.BAD_REQUEST.value());
		}

		Optional<Wallet> walletOp = walletRepository.findById(walletId);
		if(!walletOp.isPresent()){
			throw new WalletException(String.format(WALLET_ID_NOT_FOUND,walletId), HttpStatus.BAD_REQUEST.value());
		}

		Wallet wallet = walletOp.get();
		String walletCurrency = wallet.getCurrency().getCode();

		if(!walletCurrency.equals(currencyCode)){
			throw new WalletException(String.format(CURRENCY_MISMATCH_WITH_WALLET,walletCurrency,currencyCode), HttpStatus.BAD_REQUEST.value());
		}

		BigDecimal actualAmount = BalanceCalculator.calculateWalletBalance(wallet.getAmount(),amount,transactionType);

		wallet.setAmount(actualAmount);
		wallet.setLastUpdated(new Date());

		return walletRepository.save(wallet);
	}

}
