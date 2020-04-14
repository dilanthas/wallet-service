package com.games.services.wallet.service;

import com.games.services.wallet.dto.TransactionDTO;
import com.games.services.wallet.model.Transaction;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class TransactionDTOMapper {

	private ModelMapper modelMapper;

	@Autowired
	public TransactionDTOMapper(ModelMapper modelMapper) {
		this.modelMapper = modelMapper;
	}

	public TransactionDTO mapToDto(Transaction transaction){
		return modelMapper.map(transaction,TransactionDTO.class);
	}

	public List<TransactionDTO> mapToDto(List<Transaction> transactions){
		return transactions.stream()
				.map(this::mapToDto)
				.collect(Collectors.toList());
	}
}
