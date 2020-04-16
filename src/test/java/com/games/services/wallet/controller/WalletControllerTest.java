package com.games.services.wallet.controller;

import com.games.services.wallet.exception.ErrorConstants;
import com.games.services.wallet.entity.Currency;
import com.games.services.wallet.entity.Wallet;
import com.games.services.wallet.service.WalletService;
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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
public class WalletControllerTest {

	private MockMvc mockMvc;

	@Autowired
	private WebApplicationContext webApplicationContext;

	@MockBean
	private WalletService service;

	Wallet wallet;

	Currency currency;

	Long walletId;

	Long userId;

	@Before
	public void init() {

		mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();

		walletId = Long.valueOf(1);
		userId = Long.valueOf(12);
		currency = Currency.builder().code("SEK").name("Swedish Crona").build();
		wallet = Wallet.builder().lastUpdated(new Date()).id(walletId).userId(userId).currency(currency)
				.amount(BigDecimal.valueOf(100)).build();
	}

	@Test
	public void shouldReturnWalletWithGivenId() throws Exception {

		// Given
		Mockito.when(service.getWalletById(walletId)).thenReturn(wallet);

		// When
		mockMvc.perform(get("/wallets/" + walletId)).andExpect(status().isOk())
				.andExpect(jsonPath("$.id", is(wallet.getId().intValue())))
				.andExpect(jsonPath("$.currencyCode", is(currency.getCode())))
				.andExpect(jsonPath("$.userId", is(wallet.getUserId().intValue())))
				.andExpect(jsonPath("$.amount", is(wallet.getAmount().intValue())));

	}

	@Test
	public void shouldCreateWalletForGivenCriteria() throws Exception {

		// Given
		Long userId = Long.valueOf(12);
		String currencyCode = "SEK";
		String walletCriteria = "{\"userId\":\"" + 12 + "\",\"currencyCode\":\"" + currencyCode + "\"}";

		Mockito.when(service.createWallet(userId, currency.getCode())).thenReturn(wallet);

		// When

		mockMvc.perform(post("/wallets").content(walletCriteria).contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.id", is(wallet.getId().intValue())))
				.andExpect(jsonPath("$.userId", is(wallet.getUserId().intValue())))
				.andExpect(jsonPath("$.currencyCode", is(currency.getCode())))
				.andExpect(jsonPath("$.amount", is(wallet.getAmount().intValue())));

	}

	@Test
	public void shouldReturnCorrectErrorMessageForEmptyCurrency() throws Exception {

		// Given
		Long userId = Long.valueOf(12);
		String walletCriteria = "{\"userId\":\"" + 12 + "\",\"currencyCode\":\"\"}";
		String errorMessage = "Wallet currencyCode" + ErrorConstants.CANNOT_BE_EMPTY;

		Mockito.when(service.createWallet(userId, currency.getCode())).thenReturn(wallet);

		// When

		mockMvc.perform(post("/wallets").content(walletCriteria).contentType(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$.errorMessage", is(errorMessage)))
		;
	}

	@Test
	public void shouldReturnBadRequestForInCompleteWalletCriteria() throws Exception {

		// Given
		Long userId = Long.valueOf(12);
		String walletCriteria = "{\"userId\":\"" + 12 + "\"}";

		Mockito.when(service.createWallet(userId, currency.getCode())).thenReturn(wallet);

		// When

		mockMvc.perform(post("/wallets").content(walletCriteria).contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isBadRequest());
	}
}
