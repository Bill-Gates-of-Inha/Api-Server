#!/bin/bash

TARGET_PORT = 8080
TARGET_PORT2 = 8081

TARGET_PID=$(lsof -Fp -i TCP:${TARGET_PORT} | grep -Po 'p[0-9]+' | grep -Po '[0-9]+')
TARGET_PID2=$(lsof -Fp -i TCP:${TARGET_PORT2} | grep -Po 'p[0-9]+' | grep -Po '[0-9]+')

if [ ! -z ${TARGET_PID} ]; then
  echo "> Kill WAS running at ${TARGET_PORT}."
  sudo kill ${TARGET_PID}
fi

if [ ! -z ${TARGET_PID2} ]; then
  echo "> Kill WAS running at ${TARGET_PORT2}."
  sudo kill ${TARGET_PID2}
fi

nohup java -jar -Dserver.port=${TARGET_PORT} /home/ubuntu/bill-gates/build/libs/* > /home/ubuntu/nohup.out 2>&1 &
nohup java -jar -Dserver.port=${TARGET_PORT2} -Dspring.profiles.active=alpha /home/ubuntu/bill-gates/build/libs/* > /home/ubuntu/nohup2.out 2>&1 &
echo "> Now new WAS runs at ${TARGET_PORT}."
echo "> Now new WAS runs at ${TARGET_PORT2}."
exit 0
