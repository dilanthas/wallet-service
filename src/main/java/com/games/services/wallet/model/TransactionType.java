package com.games.services.wallet.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "transaction_types")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TransactionType {

	@Id
	@NotNull(message = "Transaction type code cannot be empty")
	@Column(name = "code")
	private String code;

	@NotNull(message = "Transaction type name cannot be empty")
	@Column(name = "name")
	private String name;

}
