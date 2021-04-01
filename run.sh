#!/usr/bin/env bash
set -e

function kill() {
  echo "killing java instances..."
  killall java
  echo "Shutting down docker compose..."
  docker-compose rm -f -s -v
}

trap "kill" 2

echo "Running docker compose..."
docker-compose up -d

echo "Wait for kafka became fully available..."
sleep 10

echo "Running eureka instance..."
nohup mvn -pl eureka spring-boot:run >run.log 2>&1 &
sleep 5

echo "Running statistics instance..."
nohup mvn -pl statistics spring-boot:run >>run.log 2>&1 &
sleep 5

echo "Running api instance..."
nohup mvn -pl api spring-boot:run >>run.log 2>&1 &
sleep 5

tail -f run.log
