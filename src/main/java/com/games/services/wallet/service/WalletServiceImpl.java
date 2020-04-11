package com.games.services.wallet.service;

import com.games.services.wallet.model.Wallet;
import com.games.services.wallet.repository.WalletRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.Optional;

@Service
public class WalletServiceImpl implements WalletService {

	@Autowired
	WalletRepository repository;

	@Override
	public Wallet getWalletById(@NotNull Long id) {
		Optional<Wallet> optionalWallet = repository.findById(id);

		return optionalWallet.get();
	}

	@Override
	public Wallet getWalletByUserId(@NotNull Long userId) {
		return repository.findByUserId(userId);
	}

	@Override
	public BigDecimal getWalletBalanceByUserId(@NotNull Long userId) {
		Wallet wallet = repository.findByUserId(userId);
		return wallet.getAmount();
	}
}
