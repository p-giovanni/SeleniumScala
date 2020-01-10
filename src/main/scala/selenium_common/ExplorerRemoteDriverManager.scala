//
// File: ChromeDriverManager.scalar.scala
// Author(s): Ing. Giovanni Rizzardi - Autumn 2019
// Project: poc-webtest
//

package selenium_common

import java.net.URL

import org.apache.logging.log4j.{LogManager, Logger}
import org.openqa.selenium.Platform
import org.openqa.selenium.chrome.ChromeOptions
import org.openqa.selenium.ie.InternetExplorerOptions
import org.openqa.selenium.remote.{CapabilityType, RemoteWebDriver}

object ExplorerRemoteDriverManager {
    val log:Logger = LogManager.getLogger("ExplorerRemoteDriverManager")

    def createDriver(platform:String, hub:String): Either[Error, RemoteWebDriver] = {
        log.info("createDriver >>")
        val explorerDriver:Either[Error, RemoteWebDriver] = try {
            val explorerOptions:InternetExplorerOptions = new InternetExplorerOptions()
            explorerOptions.setCapability(CapabilityType.PLATFORM_NAME,
                                          Platform.WINDOWS.asInstanceOf[Object])
            explorerOptions.requireWindowFocus()
            val driver = new RemoteWebDriver(new URL(hub), explorerOptions)
            Right(driver)
        } catch {
            case ex: Throwable => {
                val msg = s"Internet Explorer remote driver cannot be created - ${ex.getClass} - ${ex.getMessage} - ${ex.getCause}."
                log.error(msg)
                Left(new Error(msg))
            }
        }
        log.info(s"createDriver (${explorerDriver.isRight}) <<")
        explorerDriver
    }
}

