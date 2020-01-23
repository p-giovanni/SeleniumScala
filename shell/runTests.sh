#!/bin/bash
# -----------------------------------------------------
# File: run.sh
# Authors(s): Giovanni Rizzardi - Autumn 2019
# -----------------------------------------------------

SHELL_DIR=$(dirname ${PWD}/$0)
PROJECT_HOME="${SHELL_DIR}/../"
GECKO_DRIVER="/home/add-on/selenium-browser-drivers/geckodriver"
CHROME_DRIVER="/home/add-on/selenium-browser-drivers/chromedriver"

echo $PROJECT_HOME

$PROJECT_HOME/gradlew  -Dbrowser=$1 -Dremote=n -Dplatform=linux -Dwebdriver.gecko.driver=${GECKO_DRIVER} -Dwebdriver.chrome.driver=${CHROME_DRIVER} test

