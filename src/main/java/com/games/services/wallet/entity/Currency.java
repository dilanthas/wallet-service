package com.games.services.wallet.entity;

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
@Table(name = "currencies")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Currency {



	@NotNull(message = "Currency name cannot be empty")
	@Column(name = "name")
	private String name;

	@Id
	@NotNull(message = "Currency code cannot be empty")
	@Column(name = "code")
	protected String code;

}
