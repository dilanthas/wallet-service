package com.games.services.wallet.repository;

import com.games.services.wallet.entity.TransactionType;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TransactionTypeRepository extends JpaRepository<TransactionType,String> {

}
