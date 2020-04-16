package com.games.services.wallet.controller;

import com.games.services.wallet.component.WalletDTOMapper;
import com.games.services.wallet.dto.WalletBalanceDTO;
import com.games.services.wallet.dto.WalletDTO;
import com.games.services.wallet.dto.criteria.WalletCriteriaDTO;
import com.games.services.wallet.exception.WalletException;
import com.games.services.wallet.service.WalletService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
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

	private WalletDTOMapper walletDTOMapper;

	@Autowired
	public WalletController(WalletService walletService, WalletDTOMapper walletDTOMapper){
		this.walletService = walletService;
		this.walletDTOMapper = walletDTOMapper;
	}

	@GetMapping("/wallets/{id}")
	public ResponseEntity<WalletDTO> getWalletById(@PathVariable("id") long id)  {
		return new ResponseEntity<>(walletDTOMapper.mapToDto(walletService.getWalletById(id)), HttpStatus.OK);
	}

	@GetMapping("/wallets/user/{id}")
	public ResponseEntity<WalletDTO> getWalletByUserId(@PathVariable("id") long userId) {
		return new ResponseEntity<>(walletDTOMapper.mapToDto(walletService.getWalletByUserId(userId)), HttpStatus.OK);
	}

	@ResponseBody
	@GetMapping("/wallets/user/{id}/balance")
	public ResponseEntity<WalletBalanceDTO> getWalletBalanceByUserId(@PathVariable("id") long userId) {
		return new ResponseEntity<>(walletDTOMapper.mapToWalletBalanceDto(walletService.getWalletByUserId(userId)), HttpStatus.OK);

	}

	@PostMapping(value = "/wallets",  produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public ResponseEntity<WalletDTO> createWallet(@Valid @RequestBody WalletCriteriaDTO walletCriteriaDTO) throws WalletException{
		return new ResponseEntity<>(walletDTOMapper.mapToDto(walletService.createWallet( walletCriteriaDTO.getUserId(), walletCriteriaDTO.getCurrencyCode())), HttpStatus.OK);

	}



}
