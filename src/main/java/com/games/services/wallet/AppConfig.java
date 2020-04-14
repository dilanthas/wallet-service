package com.games.services.wallet;

import com.games.services.wallet.service.WalletDTOMapper;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {

	@Bean
	public ModelMapper modelMapper() {
		return new ModelMapper();
	}

	public WalletDTOMapper walletDTOMapper(){
		return new WalletDTOMapper(modelMapper());
	}
}
