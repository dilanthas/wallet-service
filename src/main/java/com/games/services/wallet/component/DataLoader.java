package com.games.services.wallet.component;

import com.games.services.wallet.model.Currency;
import com.games.services.wallet.model.TransactionType;
import com.games.services.wallet.repository.CurrencyRepository;
import com.games.services.wallet.repository.TransactionTypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.Optional;

@Component
public class DataLoader {

	@Autowired
	private CurrencyRepository currencyRepository;

	@Autowired
	private TransactionTypeRepository transactionTypeRepository;

	@PostConstruct
	public void loadData() {

		Optional<Currency> currencySek = currencyRepository.findById("SEK");
		if(!currencySek.isPresent()){
			currencyRepository.save(Currency.builder().code("SEK").name("Swedish Korona").build());
		}

		Optional<Currency> currencyEur = currencyRepository.findById("EUR");
		if(!currencyEur.isPresent()){
			currencyRepository.save(Currency.builder().code("EUR").name("EURO").build());
		}

		Optional<TransactionType> crTransactionType = transactionTypeRepository.findById("CR");
		if(!crTransactionType.isPresent()){
			transactionTypeRepository.save(TransactionType.builder().code("CR").name("Credit").build());
		}

		Optional<TransactionType> dbTransactionType = transactionTypeRepository.findById("DB");
		if(!dbTransactionType.isPresent()){
			transactionTypeRepository.save(TransactionType.builder().code("DB").name("Debit").build());
		}
	}
}
