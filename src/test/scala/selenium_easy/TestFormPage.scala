//
// File: TestFormPage.scala
// Author(s): Ing. Giovanni Rizzardi - Autumn 2019
// Project: poc-webtest
//

package selenium_easy

import org.junit.runner.RunWith
import org.openqa.selenium.WebDriver
import org.scalatest._
import org.scalatestplus.junit.JUnitRunner
import poc_seleniumeasy.pages.{FormPage, Home}
import selenium_common.DriverFactory

@RunWith(classOf[JUnitRunner])
class TestFormPage extends BaseTest
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
    info("Test for the form page of the selenium_common easy site.")
    feature("""
          |Send a string to the page forms and check the result.
          |""".stripMargin) {
        scenario("""
              |Single Input Field - send a string and verify the
              |result (on the text object under the field).
              |""".stripMargin)
        { context =>

            Given("""
                  |Given the home page.
                  |""".stripMargin)
            val textToSend = "test text"

            val home:Home = new Home(context.driver, context.siteConfigurations.pages.getOrElse("home", null))
            home.pageSetup(baseUrl).left.map { e => {
                    val msg = s"Failed to navigate to the home page - ${e.toString}."
                    log.fatal(msg)
                    fail(msg)
                }
            }
            home.clickStartButton().left.map { e => {
                    val msg = s"Failed to navigate to the test page - ${e.toString}."
                    log.fatal(msg)
                    fail(msg)
                }
            }
            home.clickSimpleFormDemo().left.map { e => {
                    val msg = s"Failed to navigate to the form page - ${e.toString}."
                    log.fatal(msg)
                    fail(msg)
                }
            }
            When("""
                  |The form test page is displayed and verified
                  |send a string to to first entry filed.
                  |""".stripMargin)

            val pageDescription = context.siteConfigurations.pages.getOrElse("form page", null)
            val formPage = new FormPage(context.driver, pageDescription)
            formPage.verifyPage.left.map { e => {
                    val msg = s"Form page not verified - ${e.toString}."
                    log.fatal(msg)
                    fail(msg)
                }
            }
            formPage.sendTextToSingleField(textToSend).left.map { e => {
                   val msg = s"Text send failed - ${e.toString}."
                    log.fatal(msg)
                    fail(msg)
                }
            }
            formPage.clickShowMessage().left.map { e => {
                    val msg = s"Click failed - ${e.toString}."
                    log.fatal(msg)
                    fail(msg)
                }
            }
            Then("""
                  |Check the result.
                  |""".stripMargin)

            val textFromPage = formPage.getTextSingleField()
            textFromPage match {
                case Left(e) => fail(s"Exception returned - test failed - ${e.toString}")
                case Right(value) => {
                    log.info(s"Returnded page text: ${value}")
                    assert(value == textToSend, s"Check the value: ${textFromPage} must be equal to ${textToSend}")
                }
            }
        }
        scenario("""
                   |Two Input Fields - send a numeric string to each field and verify the
                   |result (on the text object under the field) that shoud be de sum of
                   |the two strings.
                   |""".stripMargin) { context =>
            Given("""
                    |Given the home page.
                    |""".stripMargin)

            val firstNumber = "10"
            val secondNumber = "30"
            val expectedResult = "40"

            val home:Home = new Home(context.driver, context.siteConfigurations.pages.getOrElse("home", null))
                home.pageSetup(baseUrl).left.map { e => {
                    val msg = s"Failed to navigate to the home page - ${e.toString}."
                    log.fatal(msg)
                    fail(msg)
                }
            }
            home.clickStartButton().left.map { e => {
                    val msg = s"Failed to navigate to the test page - ${e.toString}."
                    log.fatal(msg)
                    fail(msg)
                }
            }
            home.clickSimpleFormDemo().left.map { e => {
                    val msg = s"Failed to navigate to the form page - ${e.toString}."
                    log.fatal(msg)
                    fail(msg)
                }
            }
            When("""
                   |The form test page is displayed and verified
                   |send a string to to first entry filed.
                   |""".stripMargin)

            val pageDescription = context.siteConfigurations.pages.getOrElse("form page", null)
            val formPage = new FormPage(context.driver, pageDescription)
            formPage.verifyPage.left.map { e => {
                    val msg = s"Form page not verified - ${e.toString}."
                    log.fatal(msg)
                    fail(msg)
                }
            }
            formPage.sendTextSumOne(firstNumber).fold(
                e => fail("Failed text send to field ONE."),
                v => {
                    formPage.sendTextSumTwo(secondNumber).fold(
                        e => fail("Failed text send to field TWO."),
                        v => formPage.clickGetTotal()
                    )
                })
            Then(s"""
                  |Because both the text (${firstNumber} + ${secondNumber}) sent to the fields are number
                  |a valid number is expected in the total field: ${expectedResult}
                  |""".stripMargin)

            formPage.clickGetTotal().fold(
                e => fail("Test failed - click on total button failed."),
                v => {
                    formPage.getTotal().fold(
                        e => fail("Get total operation failed."),
                        v => assert(expectedResult == v, s"Check the expected result ${expectedResult}")
                    )
                }
            )
        }
        scenario("""
                   |Two Input Fields - send a NON numeric string to one of fields and verify the
                   |result that should display an error.
                   |""".stripMargin) { context =>
            Given("""
                    |Given the home page.
                    |""".stripMargin)

            val firstNumber = "NOT NUMERIC STRING"
            val secondNumber = "30"
            val expectedResult = "NaN"

            val home:Home = new Home(context.driver, context.siteConfigurations.pages.getOrElse("home", null))
            home.pageSetup(baseUrl).left.map { e => {
                    val msg = s"Failed to navigate to the home page - ${e.toString}."
                    log.fatal(msg)
                    fail(msg)
                }
            }
            home.clickStartButton().left.map { e => {
                    val msg = s"Failed to navigate to the test page - ${e.toString}."
                    log.fatal(msg)
                    fail(msg)
                }
            }
            home.clickSimpleFormDemo().left.map { e => {
                    val msg = s"Failed to navigate to the form page - ${e.toString}."
                    log.fatal(msg)
                    fail(msg)
                }
            }
            When("""
                   |The form test page is displayed and verified
                   |send a string to to first entry filed.
                   |""".stripMargin)

            val pageDescription = context.siteConfigurations.pages.getOrElse("form page", null)
            val formPage = new FormPage(context.driver, pageDescription)
            formPage.verifyPage.left.map { e => {
                    val msg = s"Form page not verified - ${e.toString}."
                    log.fatal(msg)
                    fail(msg)
                }
            }
            formPage.sendTextSumOne(firstNumber).fold(
                e => fail("Failed text send to field ONE."),
                v => {
                    formPage.sendTextSumTwo(secondNumber).fold(
                        e => fail("Failed text send to field TWO."),
                        v => formPage.clickGetTotal()
                    )
                })
            Then(s"""
                    |Because both the text (${firstNumber} + ${secondNumber}) sent to the fields are number
                    |a valid number is expected in the total field: ${expectedResult}
                    |""".stripMargin)

            formPage.clickGetTotal().fold(
                e => fail("Test failed - click on total button failed."),
                v => {
                    formPage.getTotal().fold(
                        e => fail("Get total operation failed."),
                        v => assert(expectedResult == v, s"Check the expected result ${expectedResult}")
                    )
                }
            )
        }
    }
}
