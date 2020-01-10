//
// File: ChromeDriverManager.scalar.scala
// Author(s): Ing. Giovanni Rizzardi - Autumn 2019
// Project: poc-webtest
//

package selenium_common

import java.net.URL

import org.apache.logging.log4j.{LogManager, Logger}
import org.openqa.selenium.Platform
import org.openqa.selenium.firefox.FirefoxOptions
import org.openqa.selenium.remote.{CapabilityType, DesiredCapabilities, RemoteWebDriver}

object FirefoxRemoteDriverManager {
    val log:Logger = LogManager.getLogger("FirefoxRemoteDriverManager")

    def createDriver(platform:String, hub:String): Either[Error, RemoteWebDriver] = {
        log.info("createDriver >>")
        val firefoxDriver:Either[Error, RemoteWebDriver] = try {
            val firefoxOptions:FirefoxOptions = new FirefoxOptions()
            firefoxOptions.setCapability(CapabilityType.PLATFORM_NAME,
                                         platform.toLowerCase match {
                                             case "windows" => Platform.WINDOWS.asInstanceOf[Object]
                                             case "linux" => Platform.LINUX.asInstanceOf[Object]
                                             case _ => {
                                                 val msg = s"Platform ${platform} not known."
                                                 log.error(msg)
                                                 throw new Exception(msg)
                                             }
                                         })
            val driver = new RemoteWebDriver(new URL(hub), firefoxOptions)
            Right(driver)
        } catch {
            case ex: Throwable => {
                val msg = s"Firefox remote driver cannot be created - ${ex.getClass} - ${ex.getMessage} - ${ex.getCause}."
                log.error(msg)
                Left(new Error(msg))
            }
        }
        log.info(s"createDriver (${firefoxDriver.isRight}) <<")
        firefoxDriver
    }
}
