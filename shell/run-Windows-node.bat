@Rem #-------------------------------------------------------------------------
@Rem # File: run-Windows-node.bat
@Rem # Project: SeleniumScala
@Rem # Auhtor(s): Ing. Giovanni Rizzardi - Winter 2020
@Rem #-------------------------------------------------------------------------

HOME_PATH=
PATH=%HOME_PATH%\Documents\vbox\grid;%PATH%
java -Dwebdriver.edge.driver="C:\Windows\System32\MicrosoftWebDriver.exe" -Dwebdriver.ie.driver="%HOME_PATH%\Documents\vbox\grid\IEDriverServer.exe" -Dwebdriver.gecko.driver="%HOME_PATH%\Documents\vbox\grid\geckodriver.exe" -jar .\selenium.jar -nodeConfig .\grid-windows-node.json -role node 
pause
