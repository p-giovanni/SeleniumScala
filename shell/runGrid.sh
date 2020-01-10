#!/bin/bash
# File: runGrid.sh
# Author(s): Ing. Giovanni Rizzardi - Autumn 2019
#

SHELL_DIR=$(dirname ${PWD}/$0)

USAGE="Parametri da fornire: -node | -hub"
SERVER_JAR="/media/sf_vbox/grid/selenium.jar"
NODE_CONFIG="${SHELL_DIR}/../config/grid-linux-node.json"
HUB_LOG="/tmp/hub.log"

GECKO_DRIVER="/home/add-on/selenium-browser-drivers/geckodriver"
CHROME_DRIVER="/home/add-on/selenium-browser-drivers/chromedriver"

#-browser "browserName=chrome,version=ANY,maxInstances=10,platform=WINDOWS"
#-browser "browserName=firefox, seleniumProtocol=WebDriver"

if (( $# <= 0 )); then
	echo "Errore: mancano parametri."
	echo ${USAGE}
	exit 1
fi
echo "Elenco parametri:"

case $1 in
	"-hub")
		#java -jar ${SERVER_JAR} -role hub -Dwebdriver.firefox.profile=webdriver
		java -jar ${SERVER_JAR} -debug -role hub -log ${HUB_LOG}
	;;
	"-node")
		  java -jar -Dwebdriver.gecko.driver=${GEKO_DRIVER} -Dwebdriver.chrome.driver=${CHROME_DRIVER} ${SERVER_JAR} -role node -nodeConfig ${NODE_CONFIG}
	;;
	*)
		echo "Parametro errato: \"$1\""
		echo ${USAGE}
		exit 1
esac

exit 0
