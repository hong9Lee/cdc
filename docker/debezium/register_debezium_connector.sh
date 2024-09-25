#!/bin/bash

echo "Registering Debezium MySQL Connector..."

while ! curl -s http://kafka-connect:8083/; do
  echo "Waiting for Kafka Connect to start..."
  sleep 5
done

curl -i -X POST http://localhost:8083/connectors/ -H "Accept:application/json" \
  -H "Content-Type:application/json" \
  -d '{
    "name": "mysql-order-connector",
    "config": {
      "connector.class": "io.debezium.connector.mysql.MySqlConnector",
      "tasks.max": "1",
      "database.hostname": "mysql",
      "database.port": "3306",
      "database.user": "debezium",
      "database.password": "debezium_password",
      "database.server.id": "184054",
      "database.server.name": "mysql_order_server",
      "table.include.list": "cdc.orders",
      "database.history.kafka.bootstrap.servers": "kafka:9092",
      "database.history.kafka.topic": "schema-changes.inventory",
      "topic.prefix": "mysql-order",
      "serverTimezone": "Asia/Seoul",
      "schema.history.internal.kafka.bootstrap.servers": "kafka:9092",
      "schema.history.internal.kafka.topic": "dbhistory.inventory"
    }
  }'


echo "Debezium MySQL Connector registered successfully."
