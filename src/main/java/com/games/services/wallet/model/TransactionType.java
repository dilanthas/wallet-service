package com.games.services.wallet.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "transaction_types")
public class TransactionType {

	@Id
	@NotNull(message = "Transaction type code cannot be empty")
	@Column(name = "code")
	protected String code;

	@NotNull(message = "Transaction type name cannot be empty")
	@Column(name = "name")
	private String name;


	private TransactionType() {

	}

	public TransactionType(Long id, String name, String code) {
		this.name = name;
		this.code = code;
	}

	public String getName() {
		return name;
	}

	public String getCode() {
		return code;
	}
}
