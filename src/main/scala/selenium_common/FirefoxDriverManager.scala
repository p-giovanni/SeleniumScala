//
// File: ChromeDriverManager.scalar.scala
// Author(s): Ing. Giovanni Rizzardi - Autumn 2019
// Project: poc-webtest
//

package selenium_common

import org.apache.logging.log4j.{LogManager, Logger}
import org.openqa.selenium.WebDriver
import org.openqa.selenium.firefox.FirefoxDriver

object FirefoxDriverManager {
    val log:Logger = LogManager.getLogger("FirefoxDriverManager")

    def createDriver(): Either[Error, WebDriver] = {
        log.info("createDriver >>")
        val firefoxDriver:Either[Error, WebDriver] = try {
            val driver = new FirefoxDriver()
            Right(driver)
        } catch {
            case ex: Throwable => {
                val msg = s"Firefox driver cannot be created - ${ex.getClass} - ${ex.getMessage} - ${ex.getCause}."
                log.error(msg)
                Left(new Error(msg))
            }
        }
        log.info("createDriver <<")
        firefoxDriver
    }
}
