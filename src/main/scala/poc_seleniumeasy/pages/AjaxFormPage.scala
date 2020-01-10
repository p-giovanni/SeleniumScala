//
// File: Home.scala
// Author(s): Ing. Giovanni Rizzardi - Autumn 2019
// Project: poc-webtest
//

package poc_seleniumeasy.pages

import common.PageElementDescriptor
import org.openqa.selenium.WebDriver
import selenium_common.Page

import scala.collection.immutable.Map

class AjaxFormPage(override  val driver:WebDriver, override  val pageDecription:Map[String, PageElementDescriptor])
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
    /**
      *
      * @param text
      * @return
      */
    def sendTextToName(text:String): Either[Error, Boolean] = {
        log.info(s"sendTextToName (${text}) >>")
        val rv = sendTextTo(text, "name field")
        log.info(s"sendTextToName (${rv.isRight}) <<")
        rv
    }
    /**
      *
      * @return
      */
    def checkNameFieldInError(): Either[Error, Boolean] = {
        log.info(s"checkNameFieldInError >>")
        val rv = getElement(pageDecription.getOrElse("name field red" ,null)).fold(
            e => Left(new Error(e.toString)),
            v => Right(true)
        )
        log.info(s"checkNameFieldInError (${rv.isRight}) <<")
        rv
    }
    /**
      *
      * @param text
      * @return
      */
    def sendTextToComment(text:String): Either[Error, Boolean] = {
        log.info(s"sendTextToComment (${text}) >>")
        val rv = sendTextTo(text, "comment field")
        log.info(s"sendTextToComment (${rv.isRight})<<")
        rv
    }
    /**
      *
      * @return
      */
    def checkSendOperationResult(): Either[Error, Boolean] = {
        log.info(s"checkSendOperationResult >>")
        val rv = getElement(pageDecription.getOrElse("submit result element" ,null)).fold(
            e => Left(new Error(e.toString)),
            v => Right(true)
        )
        log.info(s"checkSendOperationResult (${rv.isRight})<<")
        rv
    }
    /**
      *
      * @return
      */
    def clickSubmitButton(): Either[Error, Boolean] = {
        clickButton("submit button")
    }
}

