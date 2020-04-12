package com.games.services.wallet.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.Date;

@Entity
@Table(name = "transactions")
public class Transaction {

	@Id
	@Column(name = "id", nullable = false)
	private Long id;

	@NotNull(message = "Transaction cannot be empty")
	@OneToOne
	@JoinColumn(name = "type_id")
	private TransactionType type;

	@Min(0)
	@NotNull(message = "Transaction cannot be empty")
	@Column(name = "amount")
	private BigDecimal amount;

	@NotNull(message = "Transaction currency cannot be empty")
	@OneToOne
	@JoinColumn(name = "currency_id")
	private Currency currency;

	@NotNull(message = "Transaction date cannot be empty")
	@Column(name = "transaction_date")
	@Temporal(TemporalType.TIMESTAMP)
	private Date transactionDate;

	@NotNull(message = "Transaction wallet cannot be empty")
	@ManyToOne(fetch = FetchType.LAZY)
	private Wallet wallet;

	private Transaction() {

	}

	public Transaction(Long id, TransactionType type, BigDecimal amount, Currency currency,
			Wallet wallet) {
		this.id = id;
		this.type = type;
		this.amount = amount;
		this.currency = currency;
		this.wallet = wallet;
		this.transactionDate = new Date();
	}

	public Long getId() {
		return id;
	}

	public TransactionType getType() {
		return type;
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public Currency getCurrency() {
		return currency;
	}

	public Date getTransactionDate() {
		return transactionDate;
	}

}
