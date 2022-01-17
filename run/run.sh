#!/bin/bash

echo "====================================="
echo "Start execution"
echo "====================================="

java -jar birthday-email-sender-*.jar --spring.config.name=config

echo "====================================="
echo "Finished"
echo "====================================="
