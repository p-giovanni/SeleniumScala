/*
 * File: BaseTest.scala
 * Author(s): Ing. Giovanni Rizzardi - Autumn 2019
 * Project: poc-webtest
 */

package selenium_easy

import org.apache.logging.log4j.{LogManager, Logger}
import org.junit.runner.RunWith
import org.openqa.selenium.WebDriver
import org.scalatest.{BeforeAndAfterAll, Outcome}
import org.scalatest.fixture.FeatureSpec
import org.scalatestplus.junit.JUnitRunner
import selenium_common.DriverFactory
import common.{SiteConfigurations, SiteConfigurationsFactory}

@RunWith(classOf[JUnitRunner])
trait BaseTest extends FeatureSpec
      with BeforeAndAfterAll
{
    val log:Logger = LogManager.getLogger(classOf[TestFormPage])

    val configurationFileName = "seleniumeasy-site-configuration.json"
    val properties = System.getProperties()
    val browser:String = System.getProperties().getProperty("browser", "firefox")
    val baseUrl:String = System.getProperties().getProperty("url", "https://www.seleniumeasy.com/test/")
    val hub:String = System.getProperties().getProperty("gridHub", "http://localhost:4444/wd/hub")
    val platform:String = System.getProperties().getProperty("platform", "linux")
    val remote:Boolean = System.getProperties().getProperty("remote", "N").toLowerCase() match {
        case "n" => false
        case "y" => true
        case _ => false
    }

    case class WebTestContext(val driver:WebDriver, val siteConfigurations: SiteConfigurations)
    type FixtureParam = WebTestContext

    def withFixture(test: OneArgTest):Outcome = {
        log.info(s"withFixture (${browser} - ${platform} - ${remote}) >>")
        var driver:WebDriver = null
        val testResult = try {
            driver = DriverFactory.create(browserName = browser, platform = platform, hub = hub, remote) match {
                case Right(d) => d
                case Left(e) => {
                    val msg = s"Cannot create driver for : ${browser}."
                    log.fatal(msg)
                    fail(msg)
                }
            }
            val siteConf:SiteConfigurations =  SiteConfigurationsFactory.load(configurationFileName) match {
                case Right(cfg) => cfg
                case Left(e) => {
                    val msg = s"Cannot read configuration file ${configurationFileName} - ${e.toString}."
                    log.fatal(msg)
                    fail(msg)
                }
            }
            val context:WebTestContext = new WebTestContext(driver, siteConf)

            test(context)

        } finally {
            Thread.sleep(2000)
            driver.quit()
        }
        log.info("withFixture <<")
        testResult
    }

    override def beforeAll(): Unit = {
        log.info("beforeAll >>")
        log.error(s"Create the driver for ${browser}.")
        log.info("beforeAll <<")
        super.beforeAll()
    }
    override def afterAll(): Unit = {
        log.info("afterAll >>")
        log.info("afterAll <<")
        super.beforeAll()
    }

}
