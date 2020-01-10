//
// File: Home.scala
// Author(s): Ing. Giovanni Rizzardi - Autumn 2019
// Project: poc-webtest
//

package poc_seleniumeasy.pages

import akka.actor.Status.Success
import common.PageElementDescriptor
import org.openqa.selenium.{WebDriver, WebElement}
import selenium_common.Page

import scala.collection.immutable.Map
import scala.util.{Failure, Try}

class FormPage(override  val driver:WebDriver, override  val pageDecription:Map[String, PageElementDescriptor])
      extends Page(driver, pageDecription)
{
    /**
      *
      * @return
      */
    @Override
    def verifyPage(): Either[Error, Boolean] = {
        log.info("verifyPage >>")
        val rv = getElement(pageDecription.getOrElse("page verify" ,null)).fold(
            e => Left(e),
            v => Right(true)
        )
        log.info(s"verifyPage (${rv.isRight}) <<")
        rv
    }
    def clickGetTotal(): Either[Error, Boolean] = {
        clickButton("get total button")
    }
    def clickShowMessage(): Either[Error, Boolean] = {
        clickButton("show message")
    }
    def sendTextToSingleField(textToSend: String): Either[Error, Boolean] = {
        sendTextTo(textToSend, "single input field")
    }
    def sendTextSumOne(textToSend: String): Either[Error, Boolean] = {
        sendTextTo(textToSend, "field sum one")
    }
    def sendTextSumTwo(textToSend: String): Either[Error, Boolean] = {
        sendTextTo(textToSend, "field sum two")
    }
    def getTextSingleField(): Either[Error, String] = {
        getElementText("show message in page")
    }
    def getTotal(): Either[Error, String] = {
        getElementText("total text")
    }
}

