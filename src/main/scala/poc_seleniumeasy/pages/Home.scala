//
// File: Home.scala
// Author(s): Ing. Giovanni Rizzardi - Autumn 2019
// Project: poc-webtest
//

package poc_seleniumeasy.pages

import common.PageElementDescriptor
import org.openqa.selenium.interactions.Actions
import org.openqa.selenium.{WebDriver, WebElement}
import selenium_common.Page

import scala.collection.immutable.Map

class Home(override  val driver:WebDriver, override  val pageDecription:Map[String, PageElementDescriptor])
      extends Page(driver, pageDecription)
{
    /**
      *
      * @return
      */
    def verifyPage(): Either[Error, Boolean] = {
        log.info("verifyPage >>")
        val rv = getElement(pageDecription.getOrElse("start test button" ,null)).fold(
            e => Left(e),
            v => Right(true)
        )
        log.info(s"verifyPage (${rv.isRight}) <<")
        rv
    }
    /**
      *
      * @param baseUrl
      * @return
      */
    def pageSetup(baseUrl: String): Either[Error, Boolean] = {
        log.info("pageSetup >>")
        val retVal = try {
            driver.navigate().to(baseUrl)
            driver.manage().window().maximize();
            verifyPage()
        } catch {
            case ex:Throwable => {
                val msg = s"Exception in setting up the home page - ${ex.getClass} - ${ex.getMessage} - ${ex.getCause}."
                log.error(msg)
                Left(new Error(msg))
            }
        }
        log.info("pageSetup <<")
        retVal
    }
    /**
      *
      * @param element
      */
    def clickStart(element:WebElement): Unit = {
        log.info("clickStart >>")
        val actions = new Actions(driver)
        actions.moveToElement(element)
        actions.build().perform()
        element.click()
        log.info("clickStart <<")
    }
    /**
      *
      * @return
      */
    def clickStartButton(): Either[Error, Boolean] = {
        clickButton("start test button")
    }
    /**
      *
      * @return
      */
    def clickSimpleFormDemo(): Either[Error, Boolean] = {
        log.info("clickSimpleFormDemo >>")
        log.debug(s"Current window: ${driver.getWindowHandle}")
        val element = getElement(pageDecription.getOrElse("simple form demo" ,null))
        val actionRv = element.fold(
            err => {Left(err)},
            element => {
                scrollToElement(element)
                performAction(element, clickStart)
            }
        )
        log.debug(s"Updated window: ${driver.getWindowHandle}")
        log.info(s"clickSimpleFormDemo (${actionRv.isRight}) <<")
        actionRv
    }
}
