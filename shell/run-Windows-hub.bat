@Rem #-------------------------------------------------------------------------
@Rem # File: run-Windows-hub.bat
@Rem # Project: SeleniumScala
@Rem # Auhtor(s): Ing. Giovanni Rizzardi - Winter 2020
@Rem #-------------------------------------------------------------------------

HOME_PATH=
PATH=%HOME_PATH%\Documents\vbox\grid;%PATH%
java -jar .\selenium.jar -port 5555 -role hub 
pause
