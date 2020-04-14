package com.games.services.wallet.service;

import com.games.services.wallet.exception.NoDataFoundException;
import com.games.services.wallet.exception.WalletException;
import com.games.services.wallet.model.Currency;
import com.games.services.wallet.model.Wallet;
import com.games.services.wallet.repository.CurrencyRepository;
import com.games.services.wallet.repository.WalletRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.Date;

import static com.games.services.wallet.exception.ErrorConstants.CURRENCY_MISMATCH_WITH_WALLET;
import static com.games.services.wallet.exception.ErrorConstants.CURRENCY_NOT_SUPPORTED;
import static com.games.services.wallet.exception.ErrorConstants.WALLET_ID_NOT_FOUND;
import static com.games.services.wallet.exception.ErrorConstants.WALLET_NOT_FOUND_FOR_USER;

@Service
public class WalletServiceImpl implements WalletService {

	private WalletRepository walletRepository;

	private CurrencyRepository currencyRepository;

	private String WALLET_ENTITY_NAME = "Wallet";

	private WalletDTOMapper walletDtoMapper;

	private BalanceCalculator balanceCalculator;

	@Autowired
	public WalletServiceImpl(WalletRepository walletRepository, CurrencyRepository currencyRepository,
			WalletDTOMapper walletDtoMapper,BalanceCalculator balanceCalculator) {
		this.walletRepository = walletRepository;
		this.currencyRepository = currencyRepository;
		this.walletDtoMapper = walletDtoMapper;
		this.balanceCalculator = balanceCalculator;
	}

	@Override
	public Wallet getWalletById(@NotNull Long id) throws WalletException {

		return walletRepository.findById(id).orElseThrow(() ->  new NoDataFoundException(WALLET_ENTITY_NAME, id));

	}

	@Override
	public Wallet getWalletByUserId(@NotNull Long userId) throws WalletException {
		Wallet wallet = walletRepository.findByUserId(userId);

		if (wallet == null) {
			throw new NoDataFoundException(String.format(WALLET_NOT_FOUND_FOR_USER,userId));
		}
		return wallet;
	}

	@Override
	public Wallet getWalletBalanceByUserId(@NotNull Long userId) throws WalletException {
		Wallet wallet = walletRepository.findByUserId(userId);

		if (wallet == null) {
			throw new NoDataFoundException(String.format(WALLET_NOT_FOUND_FOR_USER,userId));
		}

		return wallet;
	}

	@Override
	public Wallet createWallet(Long userId, String currencyCode) throws WalletException {

		Currency currency = currencyRepository.findById(currencyCode).orElseThrow(() ->new WalletException(String.format(CURRENCY_NOT_SUPPORTED, currencyCode), HttpStatus.BAD_REQUEST.value()));


		Wallet wallet = walletRepository.save(Wallet.builder().userId(userId).amount(BigDecimal.ZERO).currency(currency).lastUpdated(new Date()).build());

		return wallet;
	}

	@Override
	public Wallet updateWalletAmount(Long walletId, BigDecimal amount, String currencyCode, String transactionType)
			throws WalletException {

		Wallet wallet = walletRepository.findById(walletId).orElseThrow(()-> new NoDataFoundException(String.format(WALLET_ID_NOT_FOUND, walletId)));

		String walletCurrency = wallet.getCurrency().getCode();

		if (!walletCurrency.equals(currencyCode)) {
			throw new WalletException(String.format(CURRENCY_MISMATCH_WITH_WALLET, walletCurrency, currencyCode),
					HttpStatus.BAD_REQUEST.value());
		}

		BigDecimal actualAmount = balanceCalculator.calculateWalletBalance(wallet.getAmount(), amount, transactionType);

		wallet.setAmount(actualAmount);
		wallet.setLastUpdated(new Date());

		return walletRepository.save(wallet);
	}


}
