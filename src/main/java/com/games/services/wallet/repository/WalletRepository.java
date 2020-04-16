package com.games.services.wallet.repository;

import com.games.services.wallet.entity.Wallet;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WalletRepository extends JpaRepository<Wallet,Long> {

	Wallet findByUserId(long userId);
}
