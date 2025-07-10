#!/bin/bash

./gradlew clean bootJar

docker compose down
docker compose up --build -d
docker compose ps
