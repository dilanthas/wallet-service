package com.games.services.wallet.repository;

import com.games.services.wallet.entity.Currency;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CurrencyRepository extends JpaRepository<Currency,String> {

}
