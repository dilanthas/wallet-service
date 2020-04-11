package com.games.services.wallet.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.List;

@Entity
@Table(name = "wallets")
public class Wallet {

	@Id
	@Column(name = "id",nullable = false)
	private Long id;

	@NotNull(message = "User Id cannot be empty")
	@Column(name = "user_id")
	private Long userId;

	@Column(name = "amount",nullable = false)
	@NotNull(message = "Wallet amount cannot be empty")
	private BigDecimal amount;

	@NotNull(message = "Wallet currency cannot be empty")
	@OneToOne
	@JoinColumn(name = "currency_id")
	private Currency currency;

	@OneToMany(mappedBy = "wallet",fetch = FetchType.LAZY)
	private List<Transaction> transactions;

	private Wallet() {

	}

	public Wallet(Long id, Long userId, BigDecimal amount, Currency currency) {
		this.id = id;
		this.userId = userId;
		this.amount = amount;
		this.currency = currency;
	}

	public Long getId() {
		return id;
	}

	public Long getUserId() {
		return userId;
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public Currency getCurrency() {
		return currency;
	}
}
