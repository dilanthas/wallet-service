package com.games.services.wallet.service;

import com.games.services.wallet.dto.WalletBalanceDTO;
import com.games.services.wallet.dto.WalletDTO;
import com.games.services.wallet.model.Wallet;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DTOMapper {

	private ModelMapper modelMapper;

	@Autowired
	public DTOMapper(ModelMapper modelMapper) {
		this.modelMapper = modelMapper;
	}

	public WalletDTO convertToWalletDto(Wallet wallet) {

		WalletDTO dto = modelMapper.map(wallet, WalletDTO.class);
		return dto;
	}

	public WalletBalanceDTO convertToWalletBalanceDto(Wallet wallet) {
		WalletBalanceDTO balanceDTO = modelMapper.map(wallet, WalletBalanceDTO.class);
		return balanceDTO;
	}
}
