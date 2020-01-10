//
// File: App.scala
// Author(s): Ing. Giovanni Rizzardi - Autumn 2019
// Project: poc-webtest
//

package poc_seleniumeasy

import org.apache.commons.cli._
import org.apache.logging.log4j.{LogManager, Logger}
import org.openqa.selenium.WebDriver
import selenium_common.{DriverFactory}
import common.{SiteConfigurations, SiteConfigurationsFactory}
import poc_seleniumeasy.pages.Home

/**
  *
  */
object App {
    val log:Logger = LogManager.getLogger(classOf[App])

    def main(args: Array[String]): Unit = {
        log.fatal("****************** BEGIN ******************")

        val siteConfigFileName = "seleniumeasy-site-configuration.json"
        val cmdLine:Either[Error, CommandLine] = CmdLineParameters.parse(args);
        val siteConfiguration:Either[Error, SiteConfigurations] = cmdLine match {
            case Left(error) => Left(error)
            case Right(args) => {
                val siteConf =  SiteConfigurationsFactory.load(siteConfigFileName)
                siteConf
            }
        }
        val webDriver:Either[Error, WebDriver] = siteConfiguration match {
            case Left(error) => Left(error)
            case Right(sConf) => {
                val commandLine:CommandLine = cmdLine.getOrElse(null)
                val remote:Boolean = commandLine.hasOption("remote")
                val platform:String = commandLine.hasOption("platform") match {
                    case true => commandLine.getOptionValue("platform")
                    case false => "UNDEF"
                }
                val hub:String = commandLine.hasOption("hub-url") match {
                    case true => commandLine.getOptionValue("hub-url")
                    case false => siteConfiguration.fold(e => "CANNOT BE", v => v.gridHub)
                }
                val driver = DriverFactory.create(commandLine.getOptionValue("browser")
                                                 ,platform
                                                 ,hub
                                                 ,remote)
                driver
            }
        }
        webDriver match {
            case Left(error) => Left(error)
            case Right(driver) => {
                try {
                    val url = cmdLine.getOrElse(null).getOptionValue("url")
                    log.debug(s"Navigate to ${url}")
                    driver.navigate.to(url)
                    driver.manage().window().maximize();
                    Right(true)
                } catch {
                    case ex: Throwable => {
                        val msg = s"The driver cannot be created - ${ex.getClass} - ${ex.getMessage} - ${ex.getCause}."
                        log.error(msg)
                        Left(new Error(msg))
                    }
                }
            }
        }
        Thread.sleep(2000)
        webDriver.map(driver => driver.quit())

        log.fatal("****************** END ******************")
    }
}
