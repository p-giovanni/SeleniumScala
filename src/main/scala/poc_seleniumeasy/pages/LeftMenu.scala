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

class LeftMenu(override  val driver:WebDriver, override  val pageDecription:Map[String, PageElementDescriptor])
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
      * @param pgDescription
      * @return
      */
    def getFormPage(pgDescription:Map[String, PageElementDescriptor]): Either[Error, FormPage] = {
        log.info("getFormPage >>")
        val clickResult = for{
            clickRv1 <- clickInputForms()
            clickRv2 <- clickInputFormsSimpleFormDemo()
        } yield (clickRv1, clickRv2)
        val page = if(clickResult.isLeft) {
            Left(new Error(s"Navigate to form page failed - ${clickResult.toString}."))
        } else {
            val pageObject = new FormPage(driver, pgDescription)
            pageObject.verifyPage().fold(
                e => {
                    val msg = s"Failed to click on menu - ${e.toString}."
                    log.error(msg)
                    Left(new Error(msg))
                },
                v => {
                    Right(pageObject)
                }
            )
        }
        log.info(s"getFormPage (${page.isRight}) <<")
        page
    }
    /**
      *
      * @param pgDescription
      * @return
      */
    def getCheckboxPage(pgDescription:Map[String, PageElementDescriptor]): Either[Error, CheckBoxPage] = {
        log.info("getCheckboxPage >>")
        val clickResult = for{
            clickRv1 <- clickInputForms()
            clickRv2 <- clickInputFormsCheckBoxDemo()
        } yield (clickRv1, clickRv2)
        val page = if(clickResult.isLeft) {
            Left(new Error(s"Navigate to checkbox page failed - ${clickResult.toString}."))
        } else {
            val pageObject = new CheckBoxPage(driver, pgDescription)
            pageObject.verifyPage().fold(
                e => {
                    val msg = s"Failed to click on menu - ${e.toString}."
                    log.error(msg)
                    Left(new Error(msg))
                },
                v => {
                    Right(pageObject)
                }
            )
        }
        log.info(s"getCheckboxPage (${page.isRight}) <<")
        page
    }
    /**
      *
      * @param pgDescription
      * @return
      */
    def getRadioButtonPage(pgDescription:Map[String, PageElementDescriptor]): Either[Error, RadioButtonPage] = {
        log.info("getRadioButtonPage >>")
        val clickResult = for{
            clickRv1 <- clickInputForms()
            clickRv2 <- clickInputFormRadioButtonDemo()
        } yield (clickRv1, clickRv2)
        val page = if(clickResult.isLeft) {
            Left(new Error(s"Navigate to radiobutton page failed - ${clickResult.toString}."))
        } else {
            val pageObject = new RadioButtonPage(driver, pgDescription)
            pageObject.verifyPage().fold(
                e => {
                    val msg = s"Failed to click on menu - ${e.toString}."
                    log.error(msg)
                    Left(new Error(msg))
                },
                v => {
                    Right(pageObject)
                }
            )
        }
        log.info(s"getRadioButtonPage (${page.isRight}) <<")
        page
    }
    /**
      *
      * @param pgDescription
      * @return
      */
    def getDropDownListPage(pgDescription:Map[String, PageElementDescriptor]): Either[Error, DropDownListPage] = {
        log.info("getDropDownListPage >>")
        val clickResult = for{
            clickRv1 <- clickInputForms()
            clickRv2 <- clickInputDropDownListDemo()
        } yield (clickRv1, clickRv2)
        val page = if(clickResult.isLeft) {
            Left(new Error(s"Navigate to drop down page failed - ${clickResult.toString}."))
        } else {
            val pageObject = new DropDownListPage(driver, pgDescription)
            pageObject.verifyPage().fold(
                e => {
                    val msg = s"Failed to click on menu - ${e.toString}."
                    log.error(msg)
                    Left(new Error(msg))
                },
                v => {
                    Right(pageObject)
                }
            )
        }
        log.info(s"getDropDownListPage (${page.isRight}) <<")
        page
    }
    /**
      *
      * @param pgDescription
      * @return
      */
    def getInputFormPage(pgDescription:Map[String, PageElementDescriptor]): Either[Error, DropDownListPage] = {
        log.info("getInputFormPage >>")
        val clickResult = for{
            clickRv1 <- clickInputForms()
            clickRv2 <- clickInputInputFormDemo()
        } yield (clickRv1, clickRv2)
        val page = if(clickResult.isLeft) {
            Left(new Error(s"Navigate to input form page failed - ${clickResult.toString}."))
        } else {
            val pageObject = new DropDownListPage(driver, pgDescription)
            pageObject.verifyPage().fold(
                e => {
                    val msg = s"Failed to click on menu - ${e.toString}."
                    log.error(msg)
                    Left(new Error(msg))
                },
                v => {
                    Right(pageObject)
                }
            )
        }
        log.info(s"getInputFormPage (${page.isRight}) <<")
        page
    }
    /**
      *
      * @param pgDescription
      * @return
      */
    def getAjaxFormPage(pgDescription:Map[String, PageElementDescriptor]): Either[Error, AjaxFormPage] = {
        log.info("getAjaxFormPage >>")
        val clickResult = for{
            clickRv1 <- clickInputForms()
            clickRv2 <- clickInputAjaxFormDemo()
        } yield (clickRv1, clickRv2)
        val page = if(clickResult.isLeft) {
            Left(new Error(s"Navigate to ajax form page failed - ${clickResult.toString}."))
        } else {
            val pageObject = new AjaxFormPage(driver, pgDescription)
            pageObject.verifyPage().fold(
                e => {
                    val msg = s"Failed to click on menu - ${e.toString}."
                    log.error(msg)
                    Left(new Error(msg))
                },
                v => {
                    Right(pageObject)
                }
            )
        }
        log.info(s"getAjaxFormPage (${page.isRight}) <<")
        page
    }
    /**
      *
      * @param pgDescription
      * @return
      */
    def getJQueryDropDownPage(pgDescription:Map[String, PageElementDescriptor]): Either[Error, JQueryDropDownPage] = {
        log.info("getJQueryDropDownPage >>")
        val clickResult = for{
            clickRv1 <- clickInputForms()
            clickRv2 <- clickInputJQueryDropDownDemo()
        } yield (clickRv1, clickRv2)
        val page = if(clickResult.isLeft) {
            Left(new Error(s"Navigate to jQuery drop down page failed - ${clickResult.toString}."))
        } else {
            val pageObject = new JQueryDropDownPage(driver, pgDescription)
            pageObject.verifyPage().fold(
                e => {
                    val msg = s"Failed to click on menu - ${e.toString}."
                    log.error(msg)
                    Left(new Error(msg))
                },
                v => {
                    Right(pageObject)
                }
            )
        }
        log.info(s"getJQueryDropDownPage (${page.isRight}) <<")
        page
    }
    def clickInputFormsCheckBoxDemo(): Either[Error, Boolean] = {
        clickButton("form.checkbox demo")
    }
    def clickInputFormsSimpleFormDemo(): Either[Error, Boolean] = {
        clickButton("form.simple form demo")
    }
    def clickInputFormRadioButtonDemo(): Either[Error, Boolean] = {
        clickButton("form.radio buttons demo")
    }
    def clickInputDropDownListDemo(): Either[Error, Boolean] = {
        clickButton("form.select dropdown list")
    }
    def clickInputInputFormDemo(): Either[Error, Boolean] = {
        clickButton("form.input form submit")
    }
    def clickInputAjaxFormDemo(): Either[Error, Boolean] = {
        clickButton("form.ajax form submit")
    }
    def clickInputJQueryDropDownDemo(): Either[Error, Boolean] = {
        clickButton("form.jquery select dropdown")
    }
    def clickInputForms(): Either[Error, Boolean] = {
        clickButton("input forms")
    }
    def clickDatePicker(): Either[Error, Boolean] = {
        clickButton("date pickers")
    }
    def clickTables(): Either[Error, Boolean] = {
        clickButton("table")
    }
    def clickProgressBarAndSliders(): Either[Error, Boolean] = {
        clickButton("progress bars and sliders")
    }
    def clickAlertsAndModals(): Either[Error, Boolean] = {
        clickButton("alerts and modals")
    }
    def clickListBox(): Either[Error, Boolean] = {
        clickButton("list box")
    }
    def clickOthers(): Either[Error, Boolean] = {
        clickButton("others")
    }
}

