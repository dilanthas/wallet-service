package com.games.services.wallet.service;

import com.games.services.wallet.exception.WalletException;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;

import static com.games.services.wallet.exception.ErrorConstants.INSUFFICIENT_FUNDS_IN_WALLET;
import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
public class BalanceCalculatorTest {

	@TestConfiguration
	static class BalanceCalculatorTestContConfig {

		@Bean
		public BalanceCalculator calculator() {
			return new BalanceCalculator();
		}

	}

	@Autowired
	private BalanceCalculator calculator;

	private String crTrasnaction = "CR";

	private String dbTransaction = "DB";

	@Test
	public void shouldAddAmountForCreditTransactions() throws Exception {

		// Given
		BigDecimal walletAmount = BigDecimal.valueOf(100);
		BigDecimal transactionAmount = BigDecimal.valueOf(50);

		// WHen
		BigDecimal balance = calculator.calculateWalletBalance(walletAmount, transactionAmount, crTrasnaction);

		// Then
		Assertions.assertEquals(balance, BigDecimal.valueOf(150));
	}

	@Test
	public void shouldDeductAmountForDebitTransactions() throws Exception {

		// Given
		BigDecimal walletAmount = BigDecimal.valueOf(100);
		BigDecimal transactionAmount = BigDecimal.valueOf(50);

		// WHen
		BigDecimal balance = calculator.calculateWalletBalance(walletAmount, transactionAmount, dbTransaction);

		// Then
		Assertions.assertEquals(balance, BigDecimal.valueOf(50));
	}

	@Test
	public void shouldThrowExceptionForInsufficientBalance() throws Exception {

		// Given
		BigDecimal walletAmount = BigDecimal.valueOf(100);
		BigDecimal transactionAmount = BigDecimal.valueOf(150);

		// WHen
		try {
			calculator.calculateWalletBalance(walletAmount, transactionAmount, dbTransaction);
		}
		catch (WalletException ex) {
			assertEquals(ex.getMessage(), INSUFFICIENT_FUNDS_IN_WALLET);
			assertEquals(ex.getErrorCode(), HttpStatus.BAD_REQUEST.value());

		}

	}

	@Test
	public void shouldBeAbleToDebitTheSameAmountAsBalance() throws Exception{

		// Given
		BigDecimal walletAmount = BigDecimal.valueOf(100);
		BigDecimal transactionAmount = BigDecimal.valueOf(100);

		// WHen
		BigDecimal balance = calculator.calculateWalletBalance(walletAmount, transactionAmount, dbTransaction);

		// Then
		Assertions.assertEquals(balance, BigDecimal.valueOf(0));
	}

}
