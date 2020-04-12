package com.games.services.wallet.model;

import com.games.services.wallet.exception.ErrorMessage;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "wallets")
public class Wallet {

	@Id
	@Column(name = "id",nullable = false)
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotNull(message = "User Id"+ ErrorMessage.CANNOT_BE_EMPTY)
	@Column(name = "user_id")
	private Long userId;

	@Column(name = "amount",nullable = false)
	@NotNull(message = "Wallet amount"+ ErrorMessage.CANNOT_BE_EMPTY)
	private BigDecimal amount;

	@NotNull(message = "Wallet currency"+ ErrorMessage.CANNOT_BE_EMPTY)
	@OneToOne
	@JoinColumn(name = "currency_code")
	private Currency currency;

	@Column(name = "last_updated")
	@Temporal(TemporalType.TIMESTAMP)
	private Date lastUpdated;

	@OneToMany(mappedBy = "wallet",fetch = FetchType.LAZY)
	private List<Transaction> transactions;

	private Wallet() {

	}

	public Wallet(Long userId, BigDecimal amount, Currency currency) {
		this.userId = userId;
		this.amount = amount;
		this.currency = currency;
		this.lastUpdated = new Date();
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

	public Date getLastUpdated() {
		return lastUpdated;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	public void setLastUpdated(Date lastUpdated) {
		this.lastUpdated = lastUpdated;
	}

	public List<Transaction> getTransactions() {
		return transactions;
	}
}
