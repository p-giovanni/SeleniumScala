@Rem #-------------------------------------------------------------------------
@Rem # File: run-node.bat
@Rem # Project: SeleniumScala
@Rem # Auhtor(s): Ing. Giovanni Rizzardi - Winter 2020
@Rem #-------------------------------------------------------------------------

@REM PATH=.\browser-bin;%PATH%
@REM set HUB_IP=10.175.95.84
@REM set HUB_PORT=4444
@REM java -jar .\lib\selenium.jar -browser browserName=firefox -browser browserName=chrome -role node -hub http://%HUB_IP%:%HUB_PORT%/grid/register -host %HUB_IP% -Dwebdriver.firefox.profile=webdriver

PATH=C:\Users\rizzardig\Documents\vbox\grid;%PATH%
java -Dwebdriver.edge.driver="C:\Windows\System32\MicrosoftWebDriver.exe" -Dwebdriver.ie.driver="C:\Users\rizzardig\Documents\vbox\grid\IEDriverServer.exe" -Dwebdriver.gecko.driver="C:\Users\rizzardig\Documents\vbox\grid\geckodriver.exe" -jar .\selenium.jar -nodeConfig .\grid-windows-node.json -role node 
pause
