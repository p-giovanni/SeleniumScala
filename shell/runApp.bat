@Rem #-------------------------------------------------------------------------
@Rem # File: run-node.bat
@Rem # Project: GlamooAutotest
@Rem # Auhtor(s): Ing. Giovanni Rizzardi - Spring 2019
@Rem #-------------------------------------------------------------------------

@REM PATH=.\browser-bin;%PATH%
@REM set HUB_IP=10.175.95.84
@REM set HUB_PORT=4444
@REM java -jar .\lib\selenium.jar -browser browserName=firefox -browser browserName=chrome -role node -hub http://%HUB_IP%:%HUB_PORT%/grid/register -host %HUB_IP% -Dwebdriver.firefox.profile=webdriver

PATH=C:\Users\rizzardig\Documents\vbox\grid;%PATH%
java -Dwebdriver.ie.driver="C:\Users\rizzardig\Documents\vbox\grid\IEDriverServer.exe" -Dwebdriver.gecko.driver="C:\Users\rizzardig\Documents\vbox\grid\geckodriver.exe" -jar .\poc-webtest-1.0-SNAPSHOT.jar -u https://www.seleniumeasy.com/test/ -b edge  
pause
