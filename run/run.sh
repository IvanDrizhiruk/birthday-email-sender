#!/bin/bash

echo "====================================="
echo "Start execution"
echo "====================================="

pkill -9 birthday-email-sender-*.jar

java -jar birthday-email-sender-*.jar --spring.config.name=config
#java -jar birthday-email-sender-*.jar --spring.config.name=config  > log.log 2>&1 &

echo "====================================="
echo "Finished"
echo "====================================="
