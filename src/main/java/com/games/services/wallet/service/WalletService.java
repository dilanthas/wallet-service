package com.games.services.wallet.service;

import com.games.services.wallet.dto.WalletBalanceDTO;
import com.games.services.wallet.dto.WalletDTO;
import com.games.services.wallet.exception.WalletException;
import com.games.services.wallet.model.Wallet;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

public interface WalletService {

	WalletDTO getWalletById(@NotNull Long id) throws WalletException;

	WalletDTO getWalletByUserId(@NotNull Long userId) throws WalletException;

	WalletBalanceDTO getWalletBalanceByUserId(@NotNull Long userId) throws WalletException;

	WalletDTO createWallet(Long userId,String currencyCode) throws WalletException;

	Wallet updateWalletAmount(Long walletId, BigDecimal amount, String currency,String transactionType) throws WalletException;

}
