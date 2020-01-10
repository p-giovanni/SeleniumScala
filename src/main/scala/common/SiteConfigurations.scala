//
// File: SiteConfigurations.scala
// Author(s): Ing. Giovanni Rizzardi - Autumn 2019
//

package common

import java.lang.Error

import org.apache.logging.log4j.{LogManager, Logger}

import scala.io.Source
import play.api.libs.json.{JsPath, JsResult, JsValue, Json, Reads}
import play.api.libs.functional.syntax._
import play.api.libs.json._

import scala.collection.immutable._

case class PageElementDescriptor(
    val pathType:String,
    val path:String,
    val timeout:Int
)
case class SiteConfigurations(
    val version:String,
    val gridHub:String,
    val pages:Map[String, Map[String, PageElementDescriptor]]
)

object SiteConfigurationsFactory {
    val log:Logger = LogManager.getLogger("SiteConfigurationsFactory")

    /**
      *
      * @param fileName
      * @return
      */
    def load(fileName:String) : Either[Error, SiteConfigurations] = {
        log.info(s"load (${fileName}) >>")

        implicit val elementDescriptor:Reads[PageElementDescriptor] = (
                (JsPath \ "type").read[String]
            and (JsPath \ "path").read[String]
            and (JsPath \ "timeout").read[Int]
        ) (PageElementDescriptor.apply _)

        implicit val checksListRead:Reads[SiteConfigurations] = (
                (JsPath \ "version").read[String]
            and (JsPath \ "grid-hub").read[String]
            and (JsPath \ "pages").read[Map[String, Map[String, PageElementDescriptor]]]
        )(SiteConfigurations.apply _)

        val siteConfig:Either[Error, SiteConfigurations] = try {
            val fileStream = getClass.getResourceAsStream(s"/${fileName}")
            val resourceContent = Source.fromInputStream(fileStream).getLines.mkString

            val json: JsValue = Json.parse(resourceContent)
            val config: JsResult[SiteConfigurations] = json.validate[SiteConfigurations]

            Right(config.get)

        } catch {
            case ex: Throwable => {
                val msg = s"(loadFromResource) Exception caught: ${ex.getClass.getName} - ${ex.getCause} - ${ex.getMessage}"
                log.error(msg)
                Left(new Error(msg))
            }
        }
        log.info(s"load (${siteConfig.isRight}) <<")
        siteConfig
    }
}
