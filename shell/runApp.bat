@Rem #-------------------------------------------------------------------------
@Rem # File: runApp.bat
@Rem # Project: 
@Rem # Auhtor(s): Ing. Giovanni Rizzardi - Spring 2019
@Rem #-------------------------------------------------------------------------

HOME_PATH=
PATH=%HOME_PATH%\Documents\vbox\grid;%PATH%
java -Dwebdriver.ie.driver="%HOME_PATH%\Documents\vbox\grid\IEDriverServer.exe" -Dwebdriver.gecko.driver="%HOME_PATH%\Documents\vbox\grid\geckodriver.exe" -jar .\poc-webtest-1.0-SNAPSHOT.jar -u https://www.seleniumeasy.com/test/ -b edge  
pause
