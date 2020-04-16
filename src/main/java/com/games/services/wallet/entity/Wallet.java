package com.games.services.wallet.entity;

import com.games.services.wallet.exception.ErrorConstants;
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
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Wallet {

	@Id
	@Column(name = "id",nullable = false)
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotNull(message = "User Id"+ ErrorConstants.CANNOT_BE_EMPTY)
	@Column(name = "user_id")
	private Long userId;

	@Column(name = "amount",nullable = false)
	@NotNull(message = "Wallet amount"+ ErrorConstants.CANNOT_BE_EMPTY)
	private BigDecimal amount;

	@NotNull(message = "Wallet currency"+ ErrorConstants.CANNOT_BE_EMPTY)
	@OneToOne
	@JoinColumn(name = "currency_code")
	private Currency currency;

	@Column(name = "last_updated")
	@Temporal(TemporalType.TIMESTAMP)
	private Date lastUpdated;

	@OneToMany(mappedBy = "wallet",fetch = FetchType.LAZY)
	private List<Transaction> transactions;


	@Override
	public String toString() {
		return "Wallet{" + "id=" + getId() + ", userId=" + userId + ", amount=" + amount.toString() + ", currency=" +
				currency.getCode() + ", lastUpdated=" + lastUpdated.toString() + '}';
	}
}
