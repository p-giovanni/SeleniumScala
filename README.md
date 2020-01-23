# SeleniumScala
An experiment in writing a web automation test using the Scala language and Selenium API. 

Here I have explored the usage of Scala and ScalaTest in the web test automantion. 
The code is organizzed in two common packages:
* **selennium_common**: contains some utility classes that wrap the Selenium API. 
The two classed that have to be seen are:
    * **DriverFactory**: to be used to build the right Selenium driver: local or remote and for 
    a given browser (Firefox and Chrome for Linux, Firefox, Edge adn IEsplorer for Windowa);
    * **Page**: an Selenium API Wrapper. All the page objects must derive from this class. 
    It has some usefull methods to access the object (entry field, button, ecc.) inside the
    web page with a waitin engine to cope with asincronous page update or a slow 
    network connection.   
    There is also the main method that decouple the path inside the web page (like
    xpath, css, id search, ecc.) from the code that collect the object and give, to the
    program, a reference to it (an instance of Selenium WebElement).
    See an example of the configuration file here [seleniumeasy-site-configuration.json](./src/test/resources/seleniumeasy-site-configuration.json)     

* **common**: common classes. 

The package **poc_seleniumeasy.pages** contains all the "page objects" to access and test the
test site [SeleniumEasy](https://www.seleniumeasy.com/test/).

## Project status
```diff
! Doing
```
There is a lot of work to do to have a complete example.

## Build
To build the code:

```shell script
./gradlew clean build -x test
```
To compile the tests:
```shell script
./gradlew clean compileTestScala
```
To run the tests I have written a small shell script that build the correct
environment for the tests execution.
Here an example on how to run the test locally (no Selenium grid in that case) and 
use the Firefox browser for the tests.

```shell script
./shell/runTests.sh firefox
```
You will find the test report in that directory: ./build/reports/tests/test/index.html

## Selenium grid
To run the Selenium grid there are the json configuration file and the runs script in 
in the directory [config](./config) and [shell](./shell) 

