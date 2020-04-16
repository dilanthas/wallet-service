# WALLET MICROSERVICE
wallet microservice running on the JVM that manages credit/debit
transactions on behalf of players

# Requirements
1. Java 8
2. Maven 3

# How to run the application
1. Execute below command to build the application from root folder in the command line
``` 
mvn clean install
``` 
2. To run the application execute below command
``` 
mvn spring-boot:run
``` 

3. Once the application started you can test it by going to
```
http://localhost:8080/hello
```

# API endpoints
1. Create Wallet - Post endpoint
```
http://localhost:8080/wallets
```
Sample request
````
{
	"userId":2,
	"currencyCode":"SEK"
}
````

2. Get wallet by Wallet Id - Get endpoint
````
http://localhost:8080/wallets/{walletId}
````

3. Get wallet By User id - Get endpoint
````
http://localhost:8080/wallets/user/{userId}
````

4. Get wallet Balance By user id - Get endpoint
````
http://localhost:8080/wallets/user/{userId}/balance
````

5. Create Transaction - Post endpoint
````
http://localhost:8080/transactions
````
Sample Request
````
{
	"userId":2,
	"transactionRef":12,
	"typeCode":"DB",
	"amount":50,
	"currencyCode":"SEK",
	"description":"Test transaction"
}
````

6. Get Transaction by user id - Get endpoint
````
http://localhost:8080/transactions/user/{userId}
````

Additionally all the above requests are included as a postman test suite inside resources->postman

# Important
1. Adding new currencies are not implemented . Only two currencies are preloaded at the moment and user will have to use those currency codes when sending requests. Below are the currency codes
````
SEK,EUR
````
2. Only two transaction are supported and those transaction codes need to be used when adding transactions
````
CR - for credit transactions
DB - Debit transactions

````

# Technologies used
1. Java8
2. Spring boot
3. JPA
4. H2






