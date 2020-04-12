package com.games.services.wallet.service;

import com.games.services.wallet.exception.WalletException;
import com.games.services.wallet.model.Wallet;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

public interface WalletService {

	Wallet getWalletById(@NotNull Long id) throws WalletException;

	Wallet getWalletByUserId(@NotNull Long userId) throws WalletException;

	BigDecimal getWalletBalanceByUserId(@NotNull Long userId) throws WalletException;

	Wallet createWallet(Long userId,String currencyCode) throws WalletException;

	Wallet updateWalletAmount(Long walletId, BigDecimal amount, String currency,String transactionType) throws WalletException;

}
