package com.games.services.wallet.exception;

public class ErrorConstants {

	public static final String CANNOT_BE_EMPTY = " cannot be empty";
	public static final String CURRENCY_NOT_SUPPORTED = " Provided currency %s is not supported";
	public static final String WALLET_EXISTS_FOR_USER = "Wallet already exists for userId %s";
	public static final String WALLET_NOT_FOUND_FOR_USER = " No Wallet found for user id %s";
	public static final String USER_NOT_FOUND = "Provided userId %s not found";
	public static final String WALLET_ID_NOT_FOUND = "Provided userId %s is not found";
	public static final String UNSUPPORTED_TRANSACTION_TYPE = "Transaction type %s is not supported";
	public static final String CURRENCY_MISMATCH_WITH_WALLET = "Currency mismatch. Wallet currency is %s and transaction currency is %s";
	public static final String INSUFFICIENT_FUNDS_IN_WALLET = "Insufficient funds in the wallet , to perform the transaction ";
	public static final String TRANSACTION_REF_ALREADY_EXISTS = "Transaction ref %s already exists";

}
