#!/usr/bin/env bash

set -e

docker-compose -f docker-compose-test.yml down

docker-compose -f docker-compose-test.yml up -d

lein run migrate
