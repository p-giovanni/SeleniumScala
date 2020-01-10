@Rem #-------------------------------------------------------------------------
@Rem # File: run-node.bat
@Rem # Project: SeleniumScala
@Rem # Auhtor(s): Ing. Giovanni Rizzardi - Winter 2020
@Rem #-------------------------------------------------------------------------

PATH=C:\Users\rizzardig\Documents\vbox\grid;%PATH%
java -jar .\selenium.jar -port 5555 -role hub 
pause
