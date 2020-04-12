package com.games.services.wallet.repository;

import com.games.services.wallet.model.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TransactionRepository extends JpaRepository<Transaction,Long> {

}
