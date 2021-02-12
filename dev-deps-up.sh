#!/usr/bin/env bash

set -e

docker-compose -f docker-compose-dev.yml down

docker-compose -f docker-compose-dev.yml up -d

lein run migrate
