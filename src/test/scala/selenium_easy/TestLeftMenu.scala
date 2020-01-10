//
// File: TestFormPage.scala
// Author(s): Ing. Giovanni Rizzardi - Autumn 2019
// Project: poc-webtest
//

package selenium_easy

import org.junit.runner.RunWith
import org.scalatest._
import org.scalatestplus.junit.JUnitRunner
import poc_seleniumeasy.pages.{FormPage, Home, LeftMenu}

@RunWith(classOf[JUnitRunner])
class TestLeftMenu extends BaseTest
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
    info("Test the functionality of the left menu.")
    feature("""
          |Click on every menu item, first level only, and
          |verify that aren't broken.
          |""".stripMargin) {
        scenario("""
              |Click on the menu item.
              |""".stripMargin)
        { context =>

            Given("""
                  |Given the home page.
                  |""".stripMargin)
            val home:Home = new Home(context.driver, context.siteConfigurations.pages.getOrElse("home", null))

            val pageSetupRv = for{
                pageSetUpRv <- home.pageSetup(baseUrl)
                startButtonRv <- home.clickStartButton()
            } yield (pageSetUpRv, startButtonRv)
            if(pageSetupRv.isLeft) {
                val msg = s"Home page setup failed - ${pageSetupRv}."
                log.error(msg)
                fail(msg)
            }
            When("""
                  |Click on the left menu item and verify that are
                  |working properly.
                  |""".stripMargin)
            val leftMenu = new LeftMenu(context.driver, context.siteConfigurations.pages.getOrElse("left menu", null))
            leftMenu.verifyPage().left.map(
                e => fail(s"Left menu page not verified - ${e.toString}.")
            )
            Then("""
                  |Click on every item to verify that are working.
                  |""".stripMargin)
            val clickResult = for{
                ifRv <- leftMenu.clickInputForms()
                dpRv <- leftMenu.clickDatePicker()
                tbRv <- leftMenu.clickTables()
                pbRv <- leftMenu.clickProgressBarAndSliders()
                amRv <- leftMenu.clickAlertsAndModals()
                otRv <- leftMenu.clickOthers()
            } yield (ifRv, dpRv, tbRv, pbRv, amRv, otRv)
            if(clickResult.isLeft) {
                val msg = s"Menu item click failure - ${clickResult.left.toString}"
                log.error(msg)
                fail(msg)
            }
        }
    }
    feature("""
              |INPUT FORMS - Click on the menu item, second level, and verify
              |that the displayed page is the right one.
              |""".stripMargin) {
        scenario("""
                   |
                   |""".stripMargin)
        { context =>

            Given("""
                    |Given the home page.
                    |""".stripMargin)
            val home:Home = new Home(context.driver, context.siteConfigurations.pages.getOrElse("home", null))

            val pageSetupRv = for{
                pageSetUpRv <- home.pageSetup(baseUrl)
                startButtonRv <- home.clickStartButton()
            } yield (pageSetUpRv, startButtonRv)
            if(pageSetupRv.isLeft) {
                val msg = s"Home page setup failed - ${pageSetupRv}."
                log.error(msg)
                fail(msg)
            }
            When("""
                   |Click on the left menu item under "Input Forms" and verify
                   |that the displayed pages are correct.
                   |""".stripMargin)
            val leftMenu = new LeftMenu(context.driver, context.siteConfigurations.pages.getOrElse("left menu", null))
            leftMenu.verifyPage().left.map(
                e => fail(s"Left menu page not verified - ${e.toString}.")
            )
            Then("""
                   |Click on every item under "Input Forms" and verify that
                   |the displayed page is the right one.
                   |""".stripMargin)

            leftMenu.getFormPage(context.siteConfigurations.pages.getOrElse("form page", null)).fold(
                e => {
                    fail(s"Failed to click on 'Input Forms' - ${e.toString}")
                },
                pg => {
                    pg.verifyPage()
                }
            ).left.map(e => fail("Form page not verifyed."))

            leftMenu.getCheckboxPage(context.siteConfigurations.pages.getOrElse("checkbox page", null)).fold(
                e => {
                    fail(s"Failed to click on 'Input Forms/Checkbox demo' - ${e.toString}")
                },
                pg => {
                    pg.verifyPage()
                }
            ).left.map(e => fail("Checkbox page not verifyed."))

            leftMenu.getRadioButtonPage(context.siteConfigurations.pages.getOrElse("radiobutton demo", null)).fold(
                e => {
                    fail(s"Failed to click on 'Input Forms/Radiobutton demo' - ${e.toString}")
                },
                pg => {
                    pg.verifyPage()
                }
            ).left.map(e => fail("Radiobutton page not verifyed."))

            leftMenu.getDropDownListPage(context.siteConfigurations.pages.getOrElse("drop down list demo", null)).fold(
                e => {
                    fail(s"Failed to click on 'Input Forms/DropDown list demo' - ${e.toString}")
                },
                pg => {
                    pg.verifyPage()
                }
            ).left.map(e => fail("DropDown list page not verifyed."))

            leftMenu.getInputFormPage(context.siteConfigurations.pages.getOrElse("input form demo", null)).fold(
                e => {
                    fail(s"Failed to click on 'Input Forms/Input form demo' - ${e.toString}")
                },
                pg => {
                    pg.verifyPage()
                }
            ).left.map(e => fail("Input form page not verifyed."))

            leftMenu.getAjaxFormPage(context.siteConfigurations.pages.getOrElse("ajax form demo", null)).fold(
                e => {
                    fail(s"Failed to click on 'Input Forms/Ajax form demo' - ${e.toString}")
                },
                pg => {
                    pg.verifyPage()
                }
            ).left.map(e => fail("Ajax form page not verifyed."))

            leftMenu.getJQueryDropDownPage(context.siteConfigurations.pages.getOrElse("jquery drop down demo", null)).fold(
                e => {
                    fail(s"Failed to click on 'Input Forms/JQuery drop down demo' - ${e.toString}")
                },
                pg => {
                    pg.verifyPage()
                }
            ).left.map(e => fail("JQuery drop down  page not verifyed."))
        }
    }
}

