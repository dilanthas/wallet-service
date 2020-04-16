package com.games.services.wallet.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
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
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Transaction {

	@Id
	@Column(name = "id", nullable = false)
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotNull(message = "Transaction reference cannot be empty")
	@Column(name = "transaction_ref", nullable = false, unique = true)
	private String transactionRef;

	@NotNull(message = "Transaction cannot be empty")
	@OneToOne
	@JoinColumn(name = "type_code")
	private TransactionType type;

	@Min(0)
	@NotNull(message = "Transaction cannot be empty")
	@Column(name = "amount")
	private BigDecimal amount;

	@NotNull(message = "Transaction currency cannot be empty")
	@OneToOne
	@JoinColumn(name = "currency_code")
	private Currency currency;

	@NotNull(message = "Transaction date cannot be empty")
	@Column(name = "transaction_date")
	@Temporal(TemporalType.TIMESTAMP)
	private Date transactionDate;

	@NotNull(message = "Transaction wallet cannot be empty")
	@ManyToOne(fetch = FetchType.LAZY)
	private Wallet wallet;

	@NotNull(message = "Transaction description cannot be empty")
	@Column(name = "description")
	private String description;

	@Override
	public String toString() {
		return "Transaction{" + "id=" + getId() + ", transactionRef=" + transactionRef + ", transactionType=" + type.getCode() +", amount=" + amount.toString() + ", currency=" +
				currency.getCode() + ", transactionDate=" + transactionDate.toString() +", description=" + description + '}';
	}

}

