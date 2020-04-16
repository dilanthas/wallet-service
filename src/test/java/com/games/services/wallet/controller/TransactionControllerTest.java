package com.games.services.wallet.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.games.services.wallet.dto.TransactionDTO;
import com.games.services.wallet.exception.ErrorConstants;
import com.games.services.wallet.model.Currency;
import com.games.services.wallet.model.Transaction;
import com.games.services.wallet.model.TransactionType;
import com.games.services.wallet.model.Wallet;
import com.games.services.wallet.service.TransactionService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.math.BigDecimal;
import java.util.Date;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TransactionControllerTest {

	private MockMvc mockMvc;

	@Autowired
	private WebApplicationContext webApplicationContext;

	@MockBean
	private TransactionService transactionService;

	private ObjectMapper mapper = new ObjectMapper();

	private TransactionDTO dto;

	private Wallet wallet;

	private Transaction transaction;

	@Before
	public void init() {

		mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();

		Long walletId = Long.valueOf(3);
		String transactionRef = "xyz";
		String typeCode = "CR";
		String typeName = "CREDIT";
		BigDecimal trsAmount = BigDecimal.valueOf(50);
		String currencyCode = "SEK";
		String desc = "Test";
		Date trsDate = new Date();
		Long trsId = Long.valueOf(1);

		ObjectMapper mapper = new ObjectMapper();

		dto = new TransactionDTO(walletId,transactionRef,typeCode, trsAmount,currencyCode,desc,trsDate);

		Currency currency = Currency.builder().name("Swedish Krona").code(currencyCode).build();

		wallet = Wallet.builder().id(walletId).amount(BigDecimal.valueOf(100)).currency(currency).build();

		TransactionType transactionType = TransactionType.builder().code(typeCode).name(typeName).build();

		transaction = Transaction.builder().wallet(wallet).transactionDate(trsDate).description(desc).currency(currency).amount(trsAmount).id(trsId).transactionRef(transactionRef).type(transactionType).build();


	}

	@Test
	public void shouldCreateTransactionAndReturn() throws Exception {

		// Given

		String transactionCriteria = mapper.writeValueAsString(dto);

		Mockito.when(transactionService.createTransaction(dto)).thenReturn(transaction);


		// When
		mockMvc.perform(post("/transactions").content(transactionCriteria).contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.walletId", is(wallet.getId().intValue())))
				.andExpect(jsonPath("$.transactionRef", is(transaction.getTransactionRef())))
				.andExpect(jsonPath("$.typeCode", is(transaction.getType().getCode())))
				.andExpect(jsonPath("$.amount", is(transaction.getAmount().intValue())))
				.andExpect(jsonPath("$.currencyCode", is(transaction.getCurrency().getCode())))
				.andExpect(jsonPath("$.description", is(transaction.getDescription())));

	}

	@Test
	public void shouldReturnErrorWhenWalletIdNotProvided() throws Exception{

		// Given
		TransactionDTO currentDto = new TransactionDTO();
		currentDto.setAmount(dto.getAmount());
		currentDto.setTransactionRef(dto.getTransactionRef());
		currentDto.setCurrencyCode(dto.getCurrencyCode());
		currentDto.setDescription(dto.getDescription());
		currentDto.setTypeCode(dto.getTypeCode());


		String transactionCriteria = mapper.writeValueAsString(currentDto);
		String errorMessage = "Transaction walletId"+ErrorConstants.CANNOT_BE_EMPTY;

		// When
		mockMvc.perform(post("/transactions").content(transactionCriteria).contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isBadRequest())
				.andExpect(jsonPath("$.errorMessage", is(errorMessage)));
	}
}
