package com.games.services.wallet.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "currencies")
public class Currency {

	@Id
	@Column(name = "id",nullable = false)
	private Long id;

	@NotNull(message = "Currency name cannot be empty")
	@Column(name = "name")
	private String name;

	@NotNull(message = "Currency code cannot be empty")
	@Column(name = "code")
	protected String code;

	public Currency(){

	}

	public Currency(String name , String code){
		this.name = name;
		this.code = code;
	}

	public Long getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public String getCode() {
		return code;
	}
}
