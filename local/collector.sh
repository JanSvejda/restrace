#!/bin/zsh

docker run \
  -p 127.0.0.1:4317:4317 \
  -p 127.0.0.1:4318:4318 \
  -p 127.0.0.1:55679:55679 \
  otel/opentelemetry-collector:0.101.0 \
  2>&1 | tee collector-output.txt

# write a command that calls an endpoint /collect/build using POST with JSON data in the body:
# for i in {1..10000}; do curl -X POST -H "Content-Type: application/json" -d "{\"service\": \"1234\", \"build-id\": \"12345\", \"branch\": \"main\", \"status\": \"success\", \"start-time\": $(date +%s), \"end-time\": $(($(date +%s)+${RANDOM})), \"stages\": [{\"name\": \"labs\", \"start-time\": 1234, \"end-time\": 4321, \"status\": \"success\"}]}" http://localhost:8080/api/v1/collect/build; echo ; sleep 5; done;

# start alloy
# ./alloy-darwin-arm64 run /etc/alloy/config.alloy