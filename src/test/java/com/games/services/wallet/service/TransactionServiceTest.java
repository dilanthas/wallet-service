package com.games.services.wallet.service;

import com.games.services.wallet.dto.TransactionDTO;
import com.games.services.wallet.exception.NoDataFoundException;
import com.games.services.wallet.exception.WalletException;
import com.games.services.wallet.entity.Currency;
import com.games.services.wallet.entity.Transaction;
import com.games.services.wallet.entity.TransactionType;
import com.games.services.wallet.entity.Wallet;
import com.games.services.wallet.repository.TransactionRepository;
import com.games.services.wallet.repository.TransactionTypeRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static com.games.services.wallet.exception.ErrorConstants.INSUFFICIENT_FUNDS_IN_WALLET;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@RunWith(SpringRunner.class)
public class TransactionServiceTest {

	@TestConfiguration
	static class WalletServiceImplTestContConfig {

		@Bean
		public TransactionService transactionService() {
			return new TransactionServiceImpl();
		}
		@Bean
		public ModelMapper modelMapper() {
			return new ModelMapper();
		}

	}



	@MockBean
	TransactionRepository transactionRepository;

	@MockBean
	WalletService walletService;

	@MockBean
	TransactionTypeRepository transactionTypeRepository;

	@Autowired
	TransactionService transactionService;

	@Autowired
	ModelMapper modelMapper;

	private Wallet wallet;

	private Transaction transaction1;

	private Transaction transaction2;

	private TransactionType creditTrs;

	private TransactionType debitTrs;

	private Currency currency;

	private Date trsDate;

	Long userId;

	TransactionDTO trs1Dto;

	@Before
	public void init() throws Exception {

		Long walletId = Long.valueOf(3);
		userId = Long.valueOf(1);
		String currencyCode = "SEK";

		creditTrs = TransactionType.builder().code("CR").build();
		debitTrs = TransactionType.builder().code("DB").build();

		currency = Currency.builder().name("Swedish Krona").code(currencyCode).build();
		wallet = Wallet.builder().id(walletId).userId(userId).amount(BigDecimal.valueOf(100)).currency(currency).lastUpdated(new Date()).build();

		Date trs1Date = new Date();
		String trsRef1 = "xyz";
		BigDecimal trs1Amount = BigDecimal.valueOf(100);
		transaction1 = Transaction.builder().id(Long.valueOf(1)).transactionDate(trs1Date).transactionRef(trsRef1).amount(trs1Amount).type(creditTrs)
				.currency(currency).description("Test").wallet(wallet).build();


		String sDate2="31/12/1998";
		String trsRef2 = "abc";
		Date trs2Date = new SimpleDateFormat("dd/MM/yyyy").parse(sDate2);

		BigDecimal trs2Amount = BigDecimal.valueOf(100);
		transaction2 = Transaction.builder().id(Long.valueOf(2)).transactionDate(trs2Date).transactionRef(trsRef2).amount(trs2Amount).type(debitTrs)
				.currency(currency).description("Test").wallet(wallet).build();

		List<Transaction> transactions = new ArrayList<>();
		transactions.add(transaction2);
		transactions.add(transaction1);

		wallet.setTransactions(transactions);

	}

	@Test
	public void shouldReturnTransactionsByUserId() throws Exception {

		// Given

		Mockito.when(walletService.getWalletByUserId(userId)).thenReturn(wallet);

		// When
		List<Transaction> transactions = transactionService.getAllTransactionByUser(userId);

		// Then
		Assertions.assertEquals(transactions.size(),2);
		Assertions.assertTrue(transactions.contains(transaction1));
		Assertions.assertTrue(transactions.contains(transaction2));

	}

	@Test
	public void shouldReturnTransactionsSortedByDate() throws Exception {

		// Given

		Mockito.when(walletService.getWalletByUserId(userId)).thenReturn(wallet);

		// When
		List<Transaction> transactions = transactionService.getAllTransactionByUser(userId);

		// Then
		Assertions.assertEquals(transactions.size(),2);
		Assertions.assertEquals(transactions.get(0),transaction1);
		Assertions.assertEquals(transactions.get(1),transaction2);

	}

	@Test(expected = NoDataFoundException.class)
	public void shouldFailTransactionsForInvalidTypes() throws Exception{

		// Given
		TransactionDTO dto = modelMapper.map(creditTrs,TransactionDTO.class);
		dto.setTypeCode("TEST");

		Mockito.when(transactionTypeRepository.findById(dto.getTypeCode())).thenReturn(Optional.empty());

		// When
		transactionService.createTransaction(dto);

	}


	@Test
	public void shouldCreateCreditTransaction() throws Exception{

		// Given
		TransactionDTO dto = modelMapper.map(transaction1,TransactionDTO.class);
		Mockito.when(transactionTypeRepository.findById(dto.getTypeCode())).thenReturn(Optional.of(creditTrs));

		Mockito.when(walletService.updateWalletAmount(dto.getUserId(),dto.getAmount(),dto.getCurrencyCode(),dto.getTypeCode())).thenReturn(wallet);

		Mockito.when(transactionRepository.save(Mockito.any(Transaction.class))).thenReturn(transaction1);

		// When
		Transaction transaction = transactionService.createTransaction(dto);

		// Then
		Assertions.assertNotNull(transaction);
		Assertions.assertEquals(transaction.getId(),transaction1.getId());
	}

	@Test
	public void shouldNotSaveTransactionWhenWalletFailed() throws Exception{

		// Given
		TransactionDTO dto = modelMapper.map(transaction1,TransactionDTO.class);
		Mockito.when(transactionTypeRepository.findById(dto.getTypeCode())).thenReturn(Optional.of(creditTrs));

		Mockito.when(walletService.updateWalletAmount(dto.getUserId(),dto.getAmount(),dto.getCurrencyCode(),dto.getTypeCode())).thenThrow(new WalletException(INSUFFICIENT_FUNDS_IN_WALLET, HttpStatus.BAD_REQUEST.value()));


		// When
		try{
			transactionService.createTransaction(dto);
		}catch (Exception ex){
			// Then
			verify(transactionRepository, times(0)).save(Mockito.any(Transaction.class));
		}

		// Then
	}
}
