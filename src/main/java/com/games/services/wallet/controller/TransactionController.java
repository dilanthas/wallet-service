package com.games.services.wallet.controller;

import com.games.services.wallet.dto.TransactionDTO;
import com.games.services.wallet.exception.WalletException;
import com.games.services.wallet.model.Transaction;
import com.games.services.wallet.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

@RestController
public class TransactionController {

	private TransactionService transactionService;

	@Autowired
	public TransactionController(TransactionService transactionService){
		this.transactionService = transactionService;
	}
	@PostMapping(value = "/transactions", produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public Transaction createTransaction(@Valid @RequestBody TransactionDTO transactionDTO) throws WalletException {
		return transactionService.createTransaction(transactionDTO.getTransactionRef(), transactionDTO.getWalletId(),
				transactionDTO.getTransactionType(), transactionDTO.getAmount(), transactionDTO.getCurrencyCode(),
				transactionDTO.getDescription());
	}

	@ResponseBody
	@GetMapping("/transactions/user/{id}")
	public List<Transaction> getAllTransactionsByUser(@PathVariable Long id) throws WalletException {
		return transactionService.getAllTransactionByUser(id);
	}
}
