#!/bin/bash

echo "====================================="
echo "Start execution"
echo "====================================="

java -jar birthday-email-sender-*.jar --spring.config.name=config
#java -jar birthday-email-sender-*.jar --spring.config.name=config  > log.log 2>&1 &

echo "====================================="
echo "Finished"
echo "====================================="
