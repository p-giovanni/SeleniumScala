/*
 * File: Page.scala
 * Author(s): Ing. Giovanni Rizzardi - Autumn 2019
 * Project: poc-webtest
 */

package selenium_common

import scala.jdk.CollectionConverters._
import common.PageElementDescriptor
import org.apache.logging.log4j.{LogManager, Logger}
import org.openqa.selenium.support.ui.{ExpectedConditions, WebDriverWait}
import org.openqa.selenium.{By, JavascriptExecutor, WebDriver, WebElement}

import scala.collection.immutable.Map
import scala.util.Try

abstract class Page(val driver:WebDriver, val pageDecription:Map[String, PageElementDescriptor]) {
    val log:Logger = LogManager.getLogger(classOf[Page])
    val retryNumber = 3;
    val seleniumExceptions:List[String] = List(classOf[org.openqa.selenium.TimeoutException].toString,
                                               classOf[org.openqa.selenium.StaleElementReferenceException].toString)

    def verifyPage(): Either[Error, Boolean]

    /**
      *
      * @param numOfRetries
      * @param exceptionsList
      * @param fn
      * @tparam T
      * @return
      */
    def retry[T](numOfRetries: Int, exceptionsList: List[String] )(fn: => T): Either[Throwable,T] = {
        log.info(s"retry (${numOfRetries}) >>")
        val opRetVal = Try[T] {
            val fnRv = fn
            fnRv
        }
        val retVal:Either[Throwable,T] = if(opRetVal.isSuccess) {
            log.info("Operation success.")
            Right(opRetVal.get)
        } else {
            opRetVal.failed.get match {
                case ex if(exceptionsList contains ex.getClass.toString) => {
                    log.debug(s"Exception got is in list (${ex.toString}) - retry...")
                    if(numOfRetries > 0) {
                        retry(numOfRetries - 1, exceptionsList) {fn}
                    } else {
                        log.warn("Max number of retry reached - giving up.")
                        Left(opRetVal.failed.get)
                    }
                }
                case ex => {
                    log.error(s"Exception got: ${opRetVal.failed.get.toString}")
                    Left(opRetVal.failed.get)
                }
            }
        }
        log.info(s"retry (${retVal.isRight}) <<")
        retVal
    }
    /**
      *
      * @param buttonName
      * @return
      */
    def clickButton(buttonName:String): Either[Error, Boolean] = {
        log.info(s"clickButton (${buttonName}) >>")
        val rv = if(pageDecription == null) {
            Left(new Error("Page description null."))
        } else {
            val rv = getElement(pageDecription.getOrElse(buttonName, null)).fold(
                e => Left(e),
                webElement => {
                    val clickResult = Try {
                        scrollToElement(webElement)
                        webElement.click()
                        Thread.sleep(1000)
                    }
                    if (clickResult.isSuccess) {
                        Right(true)
                    }
                    else {
                        Left(new Error(clickResult.failed.get.toString))
                    }
                }
            )
            rv
        }
        log.info(s"clickButton (${rv.isRight}) <<")
        rv
    }
    /**
      *
      * @param textToSend
      * @param locatorName
      * @return
      */
    def sendTextTo(textToSend: String, locatorName:String): Either[Error, Boolean] = {
        log.info(s"sendTextToSumOne (${textToSend} - ${locatorName}) >>")
        val rv = getElement(pageDecription.getOrElse(locatorName ,null)).fold(
            e => Left(e),
            webElement => {
                val opResult = Try {
                    log.debug(s"Send text: ${textToSend}")
                    webElement.sendKeys(textToSend)
                }
                if(opResult.isSuccess) {
                    Right(true)
                } else {
                    log.error(s"Error sending text value - ${opResult.failed.toString}")
                    Left(new Error(opResult.failed.toString))
                }
            }
        )
        log.info(s"sendTextToSumOne (${rv.isRight}) <<")
        rv
    }
    /**
      *
      * @param elementName
      * @return
      */
    def getElementText(elementName:String): Either[Error, String] = {
        log.info(s"getElementText >>")
        val element:Either[Error, WebElement] = getElement(pageDecription.getOrElse(elementName ,null));
        val text:Either[Error, String] = element match {
            case Left(e) => Left(e)
            case Right(webElement) => {
                val txt = retry(retryNumber, List(classOf[org.openqa.selenium.TimeoutException].toString)) {
                    val eleTxt:String = webElement.getText
                    eleTxt
                }
                txt match {
                    case Left(ex) => Left(new Error(ex.toString))
                    case Right(t) => {
                        log.debug(s"Text read: ${t}")
                        Right(t)
                    }
                }
            }
        }
        log.info(s"getElementText (${text.isRight}) <<")
        text
    }
    /**
      *
      * @param element
      * @param op
      * @return
      */
    def performAction(element:WebElement, op: WebElement => Unit): Either[Error, Boolean] = {
        log.info(s"performAction >>")
        val opRv = retry(retryNumber, seleniumExceptions){op(element)}
        val rv = opRv.fold(
            e => {Left(new Error(e.toString))},
            v => {Right(true)}
        )
        log.info(s"performAction (${rv.isRight}) <<")
        rv
    }

    /**
      *
      * @param element
      */
    def scrollToElement(element:WebElement): Unit = {
        log.info("scrollToElement >>")
        val js = driver.asInstanceOf[JavascriptExecutor]
        js.executeScript("arguments[0].scrollIntoView(true);", element);
        log.info("scrollToElement <<")
    }
    /**
      *
      * @param descriptor
      * @return
      */
    def getElement(descriptor:PageElementDescriptor): Either[Error, WebElement] = {
        log.info("getElement >>")
        val element:Either[Error, WebElement] = if(descriptor != null) {
            getAccessPath(descriptor).fold(
                e => {
                    log.error(s"Locator not found: ${descriptor.path}")
                    Left(e)
                },
                locator => {
                    val result = retry(retryNumber, seleniumExceptions) {
                        if(descriptor.timeout > 0) {
                            log.debug("Wait for element visibility.")
                            val wait = new WebDriverWait(driver, descriptor.timeout)
                            wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
                        }
                        val element_list = driver.findElements(locator)
                        log.debug(s"Element list size: ${element_list.size()}")
                        if(element_list.isEmpty) {
                            val msg = s"Empty list for ${locator.toString}."
                            new Exception(msg)
                        } else {
                            log.debug("Select the visible object in the list.")
                            val sList:List[WebElement] = element_list.asScala.toList
                            val vElement = sList.filter(e => e.isDisplayed).head
                            vElement
                        }
                    }
                    result.fold(e => {Left(new Error(e.toString))}, v => {Right(v.asInstanceOf[WebElement])})
                })
        } else {
            Left(new Error("Null descriptor."))
        }
        log.info(s"getElement (${element.isRight}) <<")
        element
    }
    /**
      *
      * @param pageElementDescriptor
      * @return
      */
    def getAccessPath(pageElementDescriptor: PageElementDescriptor): Either[Error, By] = {
        log.info(s"getAccessPath (${pageElementDescriptor.path}) >>")
        val accessPath:Either[Error, By] = pageElementDescriptor.pathType match {
            case "id" => {
                Right(By.id(pageElementDescriptor.path))
            }
            case "xpath" => {
                Right(By.xpath(pageElementDescriptor.path))
            }
            case _ => {
                Left(new Error(s"Type <${pageElementDescriptor.pathType}> not known."))
            }
        }
        log.info(s"getAccessPath (${accessPath.isRight}) <<")
        accessPath
    }
}
