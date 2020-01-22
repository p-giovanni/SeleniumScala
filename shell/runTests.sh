#!/bin/bash
#
# File: run.sh
# Authors(s): Giovanni Rizzardi - Autumn 2019
#

SHELL_DIR=$(dirname ${PWD}/$0)
PROJECT_HOME="${SHELL_DIR}/../"
GECKO_DRIVER="/home/giovanni/code-personal/java/AVIVA-POC/bin/geckodriver"
CHROME_DRIVER="/home/giovanni/code-personal/java/AVIVA-POC/bin/chromedriver"

echo $PROJECT_HOME

$PROJECT_HOME/gradlew  -Dbrowser=$1 -Dremote=n -Dplatform=linux -Dwebdriver.gecko.driver=${GECKO_DRIVER} -Dwebdriver.chrome.driver=${CHROME_DRIVER} test

