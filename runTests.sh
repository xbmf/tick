#!/usr/bin/env bash
echo "Running docker compose..."
set -e
docker-compose up -d

echo "Wait for kafka became fully available..."
sleep 10

echo "Running tests"
mvn verify

echo "Shutting down docker compose..."
docker-compose rm -f -s -v
