package com.games.services.wallet.service;

import com.games.services.wallet.model.Wallet;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

public interface WalletService {

	public Wallet getWalletById(@NotNull Long id);

	public Wallet getWalletByUserId(@NotNull Long userId);

	public BigDecimal getWalletBalanceByUserId(@NotNull Long userId);

}
