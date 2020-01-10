//
// File: CmdLineParameters.scala
// Author(s): Ing. Giovanni Rizzardi - Autumn 2019
// Project: poc-webtest
//

package poc_seleniumeasy

import java.lang.Error

import org.apache.commons.cli._
import org.apache.logging.log4j.{LogManager, Logger}

/**
  *
  */
object CmdLineParameters {
    val log:Logger = LogManager.getLogger(CmdLineParameters.toString)

    /**
      *
      * @param args
      * @return
      */
    def parse(args: Array[String]): Either[Error, CommandLine] = {
        log.info("parse >>")
        val browser:Option = new Option("b", "browser", true, "Browser name (firefox, chrome, ecc.).");
        browser.setRequired(true)

        val url:Option = new Option("u", "url", true, "Url to connect.");
        url.setRequired(true)

        val remote:Option = new Option("r", "remote", false, "Require a remote web driver (the grid).");
        val platform:Option = new Option("p", "platform", true, "Set the required platform w (Windows)|l (Linux).");
        val hub:Option = new Option("h", "hub-url", true, "The grid hub url.");

        val options:Options = new Options()
        options.addOption(browser)
        options.addOption(url)
        options.addOption(remote)
        options.addOption(platform)
        options.addOption(hub)

        val cmdLine:Either[Error, CommandLine] = try {
            val parser:CommandLineParser = new DefaultParser
            val cLine:CommandLine = parser.parse(options, args)
            Right(cLine)
        } catch {
            case ex: Throwable => {
                val msg = s"(parse) Exception caught: ${ex.getClass.getName} - ${ex.getCause} - ${ex.getMessage}"
                log.error(msg)
                Left(new Error(msg))
            }
        }
        log.info(s"parse (${cmdLine.isRight}) <<")
        cmdLine
    }
}

