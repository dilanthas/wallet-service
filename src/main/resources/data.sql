
--
-- Table structure for table `currencies`
--

DROP TABLE IF EXISTS `currencies` cascade;
CREATE TABLE IF NOT EXISTS `currencies` (
  `id` int(10) NOT NULL AUTO_INCREMENT,
  `code` varchar(10) NOT NULL,
  `name` varchar(50) NOT NULL,
  PRIMARY KEY (`id`)
) ;

--
-- Dumping data for table `currencies`
--

INSERT INTO `currencies` (`id`, `code`, `name`) VALUES
(1, 'SEK', 'Swedish Krona'),
(2, 'EUR', 'Euro');

-- --------------------------------------------------------

--
-- Table structure for table `transactions`
--

/*DROP TABLE IF EXISTS `transactions`;*/
CREATE TABLE IF NOT EXISTS `transactions` (
  `id` int(50) NOT NULL AUTO_INCREMENT,
  `wallet_id` int(50) NOT NULL,
  `type_id` int(10) NOT NULL,
  `amount` decimal NOT NULL,
  `currency_id` int(11) NOT NULL,
  `transaction_date` timestamp NOT NULL,
  `updated_by` int(11) NOT NULL,
  `description` text NOT NULL,
  PRIMARY KEY (`id`),
  KEY `wallet_id` (`wallet_id`),
  KEY `type_id` (`type_id`),
  KEY `updated_by` (`updated_by`),
  KEY `currency` (`currency_id`)
) ;

-- --------------------------------------------------------

--
-- Table structure for table `transaction_types`
--

DROP TABLE IF EXISTS `transaction_types` cascade;
CREATE TABLE IF NOT EXISTS `transaction_types` (
  `name` varchar(20) NOT NULL,
  `code` varchar(10) NOT NULL,
  PRIMARY KEY (`code`)
) ;

--
-- Dumping data for table `transaction_types`
--

INSERT INTO `transaction_types` (`name`, `code`) VALUES
( 'Credit', 'CR'),
( 'Debit', 'DB');


--
-- Table structure for table `wallets`
--

DROP TABLE IF EXISTS `wallets` cascade;
CREATE TABLE IF NOT EXISTS `wallets` (
  `id` int(10) NOT NULL AUTO_INCREMENT,
  `user_id` int(50) NOT NULL,
  `amount` decimal(10,0) NOT NULL,
  `currency_id` int(50) NOT NULL,
  `last_updated_by` int(50) NOT NULL,
  `last_updated` datetime NOT NULL,
  PRIMARY KEY (`id`),
  KEY `currency_w` (`currency_id`)
) ;

INSERT INTO `wallets` (`id`, `user_id`, `amount`, `currency_id`, `last_updated_by`, `last_updated`) VALUES (NULL, '1', '100', '2', '2', '2020-04-11 05:07:00');



