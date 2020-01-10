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

class CheckBoxPage(override  val driver:WebDriver, override  val pageDecription:Map[String, PageElementDescriptor])
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
}

