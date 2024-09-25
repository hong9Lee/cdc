CREATE DATABASE IF NOT EXISTS cdc;
CREATE USER IF NOT EXISTS 'cdc_user'@'%' IDENTIFIED BY 'cdc_user';
GRANT ALL PRIVILEGES ON cdc.* TO 'cdc_user'@'%';
FLUSH PRIVILEGES;

-- Debezium 사용자를 위한 설정
CREATE USER 'debezium'@'%' IDENTIFIED BY 'debezium_password';
GRANT ALL PRIVILEGES ON *.* TO 'debezium'@'%';
FLUSH PRIVILEGES;
