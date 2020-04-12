package com.games.services.wallet.controller;

import com.games.services.wallet.dto.WalletDTO;
import com.games.services.wallet.exception.WalletException;
import com.games.services.wallet.model.Wallet;
import com.games.services.wallet.service.WalletService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.math.BigDecimal;

@RestController
public class WalletController {

	@Autowired
	private WalletService walletService;

	@ResponseBody
	@GetMapping("/wallets/{id}")
	public Wallet getWalletById(@PathVariable("id") long id) throws WalletException {
		return walletService.getWalletById(id);
	}

	@ResponseBody
	@GetMapping("/wallets/user/{id}")
	public Wallet getWalletByUserId(@PathVariable("id") long userId) throws WalletException{
		return walletService.getWalletByUserId(userId);
	}

	@ResponseBody
	@GetMapping("/wallets/user/{id}/balance")
	public BigDecimal getWalletBalanceByUserId(@PathVariable("id") long userId) throws WalletException{
		return walletService.getWalletBalanceByUserId(userId);
	}

	@PostMapping(value = "/wallets",  produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public Wallet createWallet(@Valid @RequestBody WalletDTO walletDTO) throws WalletException{
		Wallet wallet = walletService.createWallet( walletDTO.getUserId(),walletDTO.getCurrencyCode());
		return wallet;
	}



}
