package com.games.services.wallet.repository;

import com.games.services.wallet.model.Currency;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CurrencyRepository extends JpaRepository<Currency,String> {

}
