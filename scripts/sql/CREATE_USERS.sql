CREATE USER 'CryptoWalletUser'@'localhost' IDENTIFIED BY 'CryptoWalletUserPassword';
CREATE USER 'CryptoWalletUser'@'%' IDENTIFIED BY 'CryptoWalletUserPassword';

GRANT SELECT, INSERT, UPDATE, DELETE ON CryptoWallet.* TO 'CryptoWalletUser'@'localhost' REQUIRE NONE WITH MAX_QUERIES_PER_HOUR 0 MAX_CONNECTIONS_PER_HOUR 0 MAX_UPDATES_PER_HOUR 0 MAX_USER_CONNECTIONS 0;
GRANT SELECT, INSERT, UPDATE, DELETE ON CryptoWallet.* TO 'CryptoWalletUser'@'%' REQUIRE NONE WITH MAX_QUERIES_PER_HOUR 0 MAX_CONNECTIONS_PER_HOUR 0 MAX_UPDATES_PER_HOUR 0 MAX_USER_CONNECTIONS 0;