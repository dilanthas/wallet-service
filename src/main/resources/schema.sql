
--
-- Table structure for table `currencies`
--

/*DROP TABLE IF EXISTS `currencies` cascade*/;
CREATE TABLE IF NOT EXISTS `currencies` (
  `code` varchar(10) NOT NULL,
  `name` varchar(50) NOT NULL,
  PRIMARY KEY (`code`)
) ;



-- --------------------------------------------------------

--
-- Table structure for table `transactions`
--

/*DROP TABLE IF EXISTS `transactions`cascade ;*/
CREATE TABLE IF NOT EXISTS `transactions` (
  `id` int(50) NOT NULL AUTO_INCREMENT,
  `wallet_id` int(50) NOT NULL,
  `transaction_ref` varchar(50) NOT NULL,
  `type_code` varchar(10) NOT NULL,
  `amount` decimal NOT NULL,
  `currency_code` varchar(10) NOT NULL,
  `transaction_date` timestamp NOT NULL,
  `description` text NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `transaction_ref` (`transaction_ref`),
  KEY `wallet_id` (`wallet_id`),
  KEY `type_id` (`type_code`),
  KEY `currency` (`currency_code`)
) ;

-- --------------------------------------------------------

--
-- Table structure for table `transaction_types`
--

/*DROP TABLE IF EXISTS `transaction_types` */;
CREATE TABLE IF NOT EXISTS `transaction_types` (
  `name` varchar(20) NOT NULL,
  `code` varchar(10) NOT NULL,
  PRIMARY KEY (`code`)
) ;




--
-- Table structure for table `wallets`
--

/*DROP TABLE IF EXISTS `wallets` cascade;*/
CREATE TABLE IF NOT EXISTS `wallets` (
  `id` int(10) NOT NULL AUTO_INCREMENT,
  `user_id` int(50) NOT NULL,
  `amount` decimal(10,0) NOT NULL,
  `currency_code` varchar(10) NOT NULL,
  `last_updated` datetime NOT NULL,
  PRIMARY KEY (`id`),
  KEY `currency_w` (`currency_code`)
) ;



