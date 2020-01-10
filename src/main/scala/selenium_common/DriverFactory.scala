//
// File: DriverFactory.scala
// Author(s): Ing. Giovanni Rizzardi - Autumn 2019
// Project: poc-webtest
//

package selenium_common

import org.apache.logging.log4j.{LogManager, Logger}
import org.openqa.selenium.WebDriver

import scala.util.{Try, Failure, Success}

object DriverFactory {
    val log:Logger = LogManager.getLogger("DriverFactory")

    /**
      *
      * @param browserName
      * @param platform
      * @param remote
      * @return
      */
    def create(browserName:String, platform:String, hub:String, remote:Boolean): Either[Error, WebDriver] = {
        log.info(s"create (${browserName} - ${platform} - ${hub}) >>")
        val osName:String = System.getProperty("os.name").toLowerCase
        log.debug(s"Os name: ${osName}.")

        val driver = browserName match {
            case "firefox" => {
                if(remote) {
                    log.info("Firefox driver creation ...")
                    FirefoxRemoteDriverManager.createDriver(platform, hub)
                } else {
                    log.info("Firefox driver creation ...")
                    FirefoxDriverManager.createDriver
                }
            }
            case "chrome" => {
                if(remote) {
                    log.info("Chrome remote driver creation ...")
                    ChromeRemoteDriverManager.createDriver(platform, hub)
                } else {
                    log.info("Chrome driver creation ...")
                    ChromeDriverManager.createDriver
                }
            }
            case "explorer" => {
                if(remote) {
                    log.info("Internet Explorer remote driver creation ...")
                    ExplorerRemoteDriverManager.createDriver(platform, hub)
                } else {
                    if(osName.equalsIgnoreCase("Linux")) {
                        val msg = "Cannot create Explorer driver for Linux"
                        log.fatal(msg)
                        Left(new Error(msg))
                    } else {
                        log.info("Internet Explorer local driver creation ...")
                        ExplorerDriverManager.createDriver
                    }
                }
            }
            case "edge" => {
                if(remote) {
                    log.info("Edge remote driver creation ...")
                    EdgeRemoteDriverManager.createDriver(platform, hub)
                } else {
                    if(osName.equalsIgnoreCase("Linux")) {
                        val msg = "Cannot create Explorer driver for Linux"
                        log.fatal(msg)
                        Left(new Error(msg))
                    } else {
                        log.info("Edge local driver creation ...")
                        EdgeDriverManager.createDriver
                    }
                }
            }
            case _ => {
                val msg = s"Unknown browser name ${browserName}."
                log.error(msg)
                Left(new Error(msg))
            }
        }
        log.info(s"create (${driver.isRight}) <<")
        driver
    }
}
