package com.games.services.wallet.service;

import com.games.services.wallet.dto.WalletBalanceDTO;
import com.games.services.wallet.dto.WalletDTO;
import com.games.services.wallet.exception.NoDataFoundException;
import com.games.services.wallet.exception.WalletException;
import com.games.services.wallet.model.Currency;
import com.games.services.wallet.model.Wallet;
import com.games.services.wallet.repository.CurrencyRepository;
import com.games.services.wallet.repository.WalletRepository;
import com.games.services.wallet.util.BalanceCalculator;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.Date;
import java.util.Optional;

import static com.games.services.wallet.exception.ErrorConstants.CURRENCY_MISMATCH_WITH_WALLET;
import static com.games.services.wallet.exception.ErrorConstants.CURRENCY_NOT_SUPPORTED;
import static com.games.services.wallet.exception.ErrorConstants.WALLET_ID_NOT_FOUND;
import static com.games.services.wallet.exception.ErrorConstants.WALLET_NOT_FOUND_FOR_USER;

@Service
public class WalletServiceImpl implements WalletService {

	private WalletRepository walletRepository;

	private CurrencyRepository currencyRepository;

	private String WALLET_ENTITY_NAME = "Wallet";

	private ModelMapper modelMapper;

	private DTOMapper dtoMapper;

	@Autowired
	public WalletServiceImpl(WalletRepository walletRepository, CurrencyRepository currencyRepository,ModelMapper modelMapper,DTOMapper dtoMapper) {
		this.walletRepository = walletRepository;
		this.currencyRepository = currencyRepository;
		this.modelMapper = modelMapper;
		this.dtoMapper = dtoMapper;
	}

	@Override
	public WalletDTO getWalletById(@NotNull Long id) throws WalletException {

		Optional<Wallet> optionalWallet = walletRepository.findById(id);
		if (!optionalWallet.isPresent()) {
			throw new NoDataFoundException(WALLET_ENTITY_NAME, id);
		}

		return dtoMapper.convertToWalletDto(optionalWallet.get());
	}

	@Override
	public WalletDTO getWalletByUserId(@NotNull Long userId) throws WalletException {
		Wallet wallet = walletRepository.findByUserId(userId);

		if (wallet == null) {
			throw new NoDataFoundException(String.format(WALLET_NOT_FOUND_FOR_USER,userId));
		}
		return dtoMapper.convertToWalletDto(wallet);
	}

	@Override
	public WalletBalanceDTO getWalletBalanceByUserId(@NotNull Long userId) throws WalletException {
		Wallet wallet = walletRepository.findByUserId(userId);

		if (wallet == null) {
			throw new NoDataFoundException(String.format(WALLET_NOT_FOUND_FOR_USER,userId));
		}

		return dtoMapper.convertToWalletBalanceDto(wallet);
	}

	@Override
	public WalletDTO createWallet(Long userId, String currencyCode) throws WalletException {

		Optional<Currency> currencyOp = currencyRepository.findById(currencyCode);
		if (!currencyOp.isPresent()) {
			throw new WalletException(String.format(CURRENCY_NOT_SUPPORTED, currencyCode), HttpStatus.BAD_REQUEST.value());
		}
		Wallet wallet = walletRepository.save(new Wallet(userId, BigDecimal.ZERO, currencyOp.get()));

		return dtoMapper.convertToWalletDto(wallet);
	}

	@Override
	public Wallet updateWalletAmount(Long walletId, BigDecimal amount, String currencyCode, String transactionType)
			throws WalletException {
		Optional<Currency> currencyOp = currencyRepository.findById(currencyCode);
		if (!currencyOp.isPresent()) {
			throw new WalletException(String.format(CURRENCY_NOT_SUPPORTED, currencyCode), HttpStatus.BAD_REQUEST.value());
		}

		Optional<Wallet> walletOp = walletRepository.findById(walletId);
		if (!walletOp.isPresent()) {
			throw new WalletException(String.format(WALLET_ID_NOT_FOUND, walletId), HttpStatus.BAD_REQUEST.value());
		}

		Wallet wallet = walletOp.get();
		String walletCurrency = wallet.getCurrency().getCode();

		if (!walletCurrency.equals(currencyCode)) {
			throw new WalletException(String.format(CURRENCY_MISMATCH_WITH_WALLET, walletCurrency, currencyCode),
					HttpStatus.BAD_REQUEST.value());
		}

		BigDecimal actualAmount = BalanceCalculator.calculateWalletBalance(wallet.getAmount(), amount, transactionType);

		wallet.setAmount(actualAmount);
		wallet.setLastUpdated(new Date());

		return walletRepository.save(wallet);
	}

}
