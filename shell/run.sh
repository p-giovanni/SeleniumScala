#!/bin/bash
#
# File: run.sh
# Authors(s): Giovanni Rizzardi - Autumn 2019
#

SHELL_DIR=$(dirname ${PWD}/$0)
JAR_DIR="./build/libs/"
JAR_NAME="poc-webtest-1.0-SNAPSHOT.jar"
GECKO_DRIVER="/home/add-on/selenium-browser-drivers/geckodriver"
CHROME_DRIVER="/home/add-on/selenium-browser-drivers/chromedriver"
LOG_DIR="/tmp/"

java -Dwebdriver.gecko.driver=${GECKO_DRIVER} -Dwebdriver.chrome.driver=${CHROME_DRIVER} -DLOG_DIR=${LOG_DIR} -jar ${JAR_DIR}/${JAR_NAME} $1 $2 $3 $4 $5 $6 $7 $8 $9 ${10} ${11}

