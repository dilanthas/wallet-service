package com.games.services.wallet.controller;

import com.games.services.wallet.dto.TransactionDTO;
import com.games.services.wallet.exception.WalletException;
import com.games.services.wallet.model.Transaction;
import com.games.services.wallet.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
public class TransactionController {

	@Autowired
	private TransactionService transactionService;

	@PostMapping(value = "/transactions", produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public Transaction createTransaction(@Valid @RequestBody TransactionDTO transactionDTO) throws WalletException {
		return transactionService.createTransaction(transactionDTO.getTransactionId(), transactionDTO.getWalletId(),
				transactionDTO.getTransactionType(), transactionDTO.getAmount(), transactionDTO.getCurrencyCode());
	}
}
