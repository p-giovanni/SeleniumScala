//
// File: TestFormPage.scala
// Author(s): Ing. Giovanni Rizzardi - Autumn 2019
// Project: poc-webtest
//

package selenium_easy

import org.junit.runner.RunWith
import org.scalatest._
import org.scalatestplus.junit.JUnitRunner
import poc_seleniumeasy.pages.{AjaxFormPage, FormPage, Home, LeftMenu}

@RunWith(classOf[JUnitRunner])
class TestAjaxFormPage extends BaseTest
                   with BeforeAndAfter
                   with GivenWhenThen
{
    before {
        log.info("before >>")
        log.info("before <<")
    }
    after {
        log.info("after >>")
        log.info("after <<")
    }
    info("Test for the AJAX form page of the selenium easy site.")
    feature("""
          |Send a string in the:
          | - name
          | - comment
          |fields and check the result in the asincronous created element.
          |""".stripMargin) {
        scenario("""
              |Send a text to name and comment fields and check the result.
              |""".stripMargin)
        { context =>

            Given("""
                  |Given the home page and the left
                  |menu - go to the Ajax form page.
                  |""".stripMargin)
            val textToSend = "test text"

            val home:Home = new Home(context.driver, context.siteConfigurations.pages.getOrElse("home", null))
            val pageSetupRv = for{
                pageSetUpRv <- home.pageSetup(baseUrl)
                startButtonRv <- home.clickStartButton()
            } yield (pageSetUpRv, startButtonRv)
            if(pageSetupRv.isLeft) {
                val msg = s"Home page setup failed - ${pageSetupRv.toString}."
                log.error(msg)
                fail(msg)
            }
            val leftMenu = new LeftMenu(context.driver, context.siteConfigurations.pages.getOrElse("left menu", null))
            val ajaxFormPage:AjaxFormPage = leftMenu.getAjaxFormPage(context.siteConfigurations.pages.getOrElse("ajax form demo", null)).fold(
                e => {fail("Ajax page not verified.")},
                v => {v}
            )
            When("""
                  |The form test page is displayed and verified
                  |send a string to to first entry filed and click
                  |the submit button.
                  |""".stripMargin)
            val sendTextOp = for{
                txt1Rv <- ajaxFormPage.sendTextToName("Name for name field")
                txt2Rv <- ajaxFormPage.sendTextToComment("Comment for COMMENT field")
                subBtRv <- ajaxFormPage.clickSubmitButton()
            } yield (txt1Rv, txt2Rv, subBtRv)
            if(sendTextOp.isLeft) {
                val msg = s"Cannot send the text in the Ajax form page - ${sendTextOp.toString}."
                log.error(msg)
                fail(msg)
            }
            Then("""
                  |Check the result.
                  |""".stripMargin)

            val sendResult = ajaxFormPage.checkSendOperationResult().left.map(
                e => fail(s"Ajax operation verify failed - ${e.toString}.")
            )
        }
        scenario("""
                   |Send an EMPTY text to name and check the result.
                   |""".stripMargin)
        { context =>

            Given("""
                    |Given the home page and the left
                    |menu - go to the Ajax form page.
                    |""".stripMargin)
            val textToSend = "test text"

            val home:Home = new Home(context.driver, context.siteConfigurations.pages.getOrElse("home", null))
            val pageSetupRv = for{
                pageSetUpRv <- home.pageSetup(baseUrl)
                startButtonRv <- home.clickStartButton()
            } yield (pageSetUpRv, startButtonRv)
            if(pageSetupRv.isLeft) {
                val msg = s"Home page setup failed - ${pageSetupRv.toString}."
                log.error(msg)
                fail(msg)
            }
            val leftMenu = new LeftMenu(context.driver, context.siteConfigurations.pages.getOrElse("left menu", null))
            val ajaxFormPage:AjaxFormPage = leftMenu.getAjaxFormPage(context.siteConfigurations.pages.getOrElse("ajax form demo", null)).fold(
                e => {fail("Ajax page not verified.")},
                v => {v}
            )
            When("""
                   |Send an empty string to the name button
                   |and click the send button.
                   |""".stripMargin)
            val sendTextOp = for{
                txt1Rv <- ajaxFormPage.sendTextToName("")
                subBtRv <- ajaxFormPage.clickSubmitButton()
            } yield (txt1Rv, subBtRv)
            if(sendTextOp.isLeft) {
                val msg = s"Cannot send the text in the Ajax form page - ${sendTextOp.toString}."
                log.error(msg)
                fail(msg)
            }
            Then("""
                   |Check that the name entry field is in
                   |the error state (red border) and the ajax field
                   |must not be present.
                   |""".stripMargin)

            val nameFieldStatusResult = ajaxFormPage.checkNameFieldInError().left.map(
                e => fail(s"Ajax operation verify failed - ${e.toString}.")
            )
            val sendResult = ajaxFormPage.checkSendOperationResult().fold(
                e => true,
                v => fail(s"Error the AJAX field is present with a field in error.")
            )
        }
    }
}
