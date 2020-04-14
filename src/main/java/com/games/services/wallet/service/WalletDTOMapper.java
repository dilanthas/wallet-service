package com.games.services.wallet.service;

import com.games.services.wallet.dto.WalletBalanceDTO;
import com.games.services.wallet.dto.WalletDTO;
import com.games.services.wallet.model.Wallet;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class WalletDTOMapper {

	private ModelMapper modelMapper;

	@Autowired
	public WalletDTOMapper(ModelMapper modelMapper) {
		this.modelMapper = modelMapper;
	}

	public WalletDTO mapToDto(Wallet wallet) {
		return modelMapper.map(wallet, WalletDTO.class);
	}

	public WalletBalanceDTO mapToWalletBalanceDto(Wallet wallet) {
		return modelMapper.map(wallet, WalletBalanceDTO.class);
	}

}
