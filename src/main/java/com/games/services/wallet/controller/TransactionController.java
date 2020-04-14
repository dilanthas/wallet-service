package com.games.services.wallet.controller;

import com.games.services.wallet.dto.TransactionDTO;
import com.games.services.wallet.exception.WalletException;
import com.games.services.wallet.service.TransactionDTOMapper;
import com.games.services.wallet.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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

	private TransactionDTOMapper transactionDTOMapper;

	@Autowired
	public TransactionController(TransactionService transactionService,TransactionDTOMapper transactionDTOMapper){
		this.transactionService = transactionService;
		this.transactionDTOMapper = transactionDTOMapper;
	}


	@PostMapping(value = "/transactions")
	public ResponseEntity<TransactionDTO> createTransaction(@Valid @RequestBody TransactionDTO transactionDTO) throws WalletException {
		return new ResponseEntity<>(transactionDTOMapper.mapToDto(transactionService.createTransaction(transactionDTO)), HttpStatus.OK);

	}

	@ResponseBody
	@GetMapping("/transactions/user/{id}")
	public ResponseEntity<List<TransactionDTO>> getAllTransactionsByUser(@PathVariable Long id) throws WalletException {

		return new ResponseEntity<>(transactionDTOMapper.mapToDto(transactionService.getAllTransactionByUser(id)), HttpStatus.OK);

	}
}
