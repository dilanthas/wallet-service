package com.games.services.wallet.controller;

import com.games.services.wallet.Greeting;
import com.games.services.wallet.model.Wallet;
import com.games.services.wallet.service.WalletService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.concurrent.atomic.AtomicLong;

@RestController
public class WalletController {

	private static final String template = "Hello, %s!";
	private final AtomicLong counter = new AtomicLong();

	@Autowired
	WalletService walletService;

	@GetMapping("/greeting")
	public Greeting greeting(@RequestParam(value = "name", defaultValue = "World") String name) {
		return new Greeting(counter.incrementAndGet(), String.format(template, name));
	}


	@GetMapping("/wallets/{id}")
	public Wallet getWalletById(@PathVariable("id") long id){
		return walletService.getWalletById(id);
	}

	@GetMapping("/wallets/user/{id}")
	public Wallet getWalletByUserId(@PathVariable("id") long userId){
		return walletService.getWalletByUserId(userId);
	}

	@GetMapping("/wallets/user/{id}/balance")
	public BigDecimal getWalletBalanceByUserId(@PathVariable("id") long userId){
		return walletService.getWalletBalanceByUserId(userId);
	}

}
