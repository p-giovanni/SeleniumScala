//
// File: ChromeDriverManager.scalar.scala
// Author(s): Ing. Giovanni Rizzardi - Autumn 2019
// Project: poc-webtest
//

package selenium_common

import java.net.URL

import org.apache.logging.log4j.{LogManager, Logger}
import org.openqa.selenium.Platform
import org.openqa.selenium.edge.{EdgeDriverService, EdgeOptions}
import org.openqa.selenium.remote.{CapabilityType, RemoteWebDriver}

object EdgeRemoteDriverManager {
    val log:Logger = LogManager.getLogger("EdgeRemoteDriverManager")

    def createDriver(platform:String, hub:String): Either[Error, RemoteWebDriver] = {
        log.info("createDriver >>")
        val edgeDriver:Either[Error, RemoteWebDriver] = try {
            val edgeOptions:EdgeOptions = new EdgeOptions()
            edgeOptions.setCapability(CapabilityType.PLATFORM_NAME,
                                      Platform.WINDOWS.asInstanceOf[Object])
            val driver = new RemoteWebDriver(new URL(hub), edgeOptions)
            Right(driver)
        } catch {
            case ex: Throwable => {
                val msg = s"Edge remote driver cannot be created - ${ex.getClass} - ${ex.getMessage} - ${ex.getCause}."
                log.error(msg)
                Left(new Error(msg))
            }
        }
        log.info(s"createDriver (${edgeDriver.isRight}) <<")
        edgeDriver
    }
}

