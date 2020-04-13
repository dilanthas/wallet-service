package com.games.services.wallet.controller;

import com.games.services.wallet.dto.WalletBalanceDTO;
import com.games.services.wallet.dto.WalletCriteriaDTO;
import com.games.services.wallet.dto.WalletDTO;
import com.games.services.wallet.exception.WalletException;
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

@RestController
public class WalletController {

	private WalletService walletService;

	@Autowired
	public WalletController(WalletService walletService){
		this.walletService = walletService;
	}

	@ResponseBody
	@GetMapping("/wallets/{id}")
	public WalletDTO getWalletById(@PathVariable("id") long id) throws WalletException {
		return walletService.getWalletById(id);
	}

	@ResponseBody
	@GetMapping("/wallets/user/{id}")
	public WalletDTO getWalletByUserId(@PathVariable("id") long userId) throws WalletException{
		return walletService.getWalletByUserId(userId);
	}

	@ResponseBody
	@GetMapping("/wallets/user/{id}/balance")
	public WalletBalanceDTO getWalletBalanceByUserId(@PathVariable("id") long userId) throws WalletException{
		return walletService.getWalletBalanceByUserId(userId);
	}

	@PostMapping(value = "/wallets",  produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public WalletDTO createWallet(@Valid @RequestBody WalletCriteriaDTO walletCriteriaDTO) throws WalletException{
		WalletDTO wallet = walletService.createWallet( walletCriteriaDTO.getUserId(), walletCriteriaDTO.getCurrencyCode());
		return wallet;
	}



}
