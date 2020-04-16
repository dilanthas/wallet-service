package com.games.services.wallet.service;

import com.games.services.wallet.exception.NoDataFoundException;
import com.games.services.wallet.exception.WalletException;
import com.games.services.wallet.entity.Currency;
import com.games.services.wallet.entity.Wallet;
import com.games.services.wallet.repository.CurrencyRepository;
import com.games.services.wallet.repository.WalletRepository;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.util.Optional;

import static com.games.services.wallet.exception.ErrorConstants.INSUFFICIENT_FUNDS_IN_WALLET;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@RunWith(SpringRunner.class)
public class WalletServiceTest {

	@TestConfiguration
	static class WalletServiceImplTestContConfig {
		@Bean
		public WalletService walletService() {
			return new WalletServiceImpl();
		}
	}

	@MockBean
	private WalletRepository walletRepository;

	@Autowired
	private WalletService walletService;


	@MockBean
	private CurrencyRepository currencyRepository;

	@MockBean
	private BalanceCalculator balanceCalculator;

	private Wallet wallet;

	private Currency currency;

	@Before
	public void init(){

		Long walletId = Long.valueOf(3);
		String currencyCode = "SEK";


		currency = Currency.builder().name("Swedish Krona").code(currencyCode).build();
		wallet = Wallet.builder().id(walletId).amount(BigDecimal.valueOf(100)).currency(currency).build();

	}

	@Test
	public void shouldReturnWalletByUserId() throws Exception{

		// Given
		Long userId = Long.valueOf(1);

		Mockito.when(walletRepository.findByUserId(userId)).thenReturn(wallet);

		// When
		Wallet walletResult = walletService.getWalletByUserId(userId);

		// Then
		Assertions.assertNotNull(walletResult);
		Assert.assertEquals(walletResult.getId(),wallet.getId());
	}

	@Test(expected = NoDataFoundException.class)
	public void shouldThrowNoDataFoundExceptionWhenWalletNotFound(){

		// Given
		Long userId = Long.valueOf(1);

		Mockito.when(walletRepository.findByUserId(userId)).thenReturn(null);

		// When
		walletService.getWalletByUserId(userId);

		// Then

	}

	@Test
	public void shouldCreateWalletForGivenCriteria() throws Exception{

		// Given
		Long userId = Long.valueOf(1);
		Mockito.when(walletRepository.save(Mockito.any(Wallet.class))).thenReturn(wallet);
		Mockito.when(currencyRepository.findById(currency.getCode())).thenReturn(Optional.of(currency));

		// When

		Wallet walletResult = walletService.createWallet(userId,currency.getCode());

		// Then
		Assertions.assertNotNull(walletResult);
		Assertions.assertEquals(walletResult.getId(),wallet.getId());
	}

	@Test(expected = WalletException.class)
	public void shouldReturnExceptionWhenCurrencyNotAvailable() throws Exception{

		// Given
		Long userId = Long.valueOf(1);
		Mockito.when(walletRepository.save(Mockito.any(Wallet.class))).thenReturn(wallet);
		Mockito.when(currencyRepository.findById(currency.getCode())).thenReturn(Optional.empty());

		// When

		walletService.createWallet(userId,currency.getCode());

	}

	@Test
	public void shouldUpdateWalletWhenCredit() throws Exception{

		// Given
		String transactionType = "CR";
		BigDecimal amount = BigDecimal.valueOf(50);

		Mockito.when(walletRepository.findById(wallet.getId())).thenReturn(Optional.of(wallet));

		Mockito.when(balanceCalculator.calculateWalletBalance(wallet.getAmount(),amount,transactionType)).thenReturn(amount);

		Mockito.when(walletRepository.save(wallet)).thenReturn(wallet);

		// When
		Wallet walletResult = walletService.updateWalletAmount(wallet.getId(),amount,currency.getCode(),transactionType);

		// Then
		Assertions.assertNotNull(walletResult);
		Assertions.assertEquals(walletResult.getId(),wallet.getId());
		Assertions.assertEquals(walletResult.getAmount(),amount);
	}

	@Test
	public void shouldUpdateWalletWhenDebit() throws Exception{

		// Given
		String transactionType = "DB";
		BigDecimal amount = BigDecimal.valueOf(50);
		wallet.setAmount(BigDecimal.valueOf(70));
		BigDecimal balance = BigDecimal.valueOf(20);

		Mockito.when(walletRepository.findById(wallet.getId())).thenReturn(Optional.of(wallet));

		Mockito.when(balanceCalculator.calculateWalletBalance(wallet.getAmount(),amount,transactionType)).thenReturn(balance);

		Mockito.when(walletRepository.save(wallet)).thenReturn(wallet);

		// When
		Wallet walletResult = walletService.updateWalletAmount(wallet.getId(),amount,currency.getCode(),transactionType);

		// Then
		Assertions.assertNotNull(walletResult);
		Assertions.assertEquals(walletResult.getId(),wallet.getId());
		Assertions.assertEquals(walletResult.getAmount(),balance);
	}

	@Test
	public void shouldNotUpdateWalletWhenExceptionThrown() throws Exception{

		// Given
		String transactionType = "DB";
		BigDecimal amount = BigDecimal.valueOf(50);
		wallet.setAmount(BigDecimal.valueOf(70));
		BigDecimal balance = BigDecimal.valueOf(20);

		Mockito.when(walletRepository.findById(wallet.getId())).thenReturn(Optional.of(wallet));

		Mockito.when(balanceCalculator.calculateWalletBalance(wallet.getAmount(),amount,transactionType)).thenThrow(new WalletException(INSUFFICIENT_FUNDS_IN_WALLET, HttpStatus.BAD_REQUEST.value()));


		try{
			// When
			walletService.updateWalletAmount(wallet.getId(),amount,currency.getCode(),transactionType);
		}catch (WalletException ex){
			// Then
			verify(walletRepository, times(0)).save(wallet);
		}



	}
}
