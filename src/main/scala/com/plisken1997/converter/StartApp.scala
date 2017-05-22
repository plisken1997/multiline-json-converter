package com.plisken1997.converter

import java.nio.file.{Files, Paths}

import com.plisken1997.converter.parser.CliParser
import com.plisken1997.formater.Formater

import scala.util.{Failure, Success, Try}

/**
  * Created by rbacconnier on 17/05/2017.
  */
object StartApp {
  lazy val home = Paths.get("").toAbsolutePath.toString

  def main(args: Array[String]): Unit = {
    val format = CliParser.parse(args) match {
      case Some(config) => formatFile(config.source, config.dest)
      case _ => Failure(new RuntimeException("invalid command line arguments. You should provide a `source|s` filepath option"))
    }

    format match {
      case Success(msg) => println(msg)
      case Failure(error) => println(s"${error.getMessage}")
    }
  }

  /**
    *
    * @param srcFile
    * @param destFile
    * @return
    */
  private def formatFile(srcFile: Option[String], destFile: Option[String]): Try[String] = {
    lazy val flatJSONFile = srcFile.map{ src =>
      if (src.startsWith("/")) src
      else s"$home/$src"
    }.get

    lazy val niceJSONFile = destFile.map { dest =>
      if (dest.startsWith("/")) dest
      else s"$home/$dest"
    }.getOrElse(s"$home/output-json.json")

    if (Files.exists(Paths.get(niceJSONFile))) {
      Failure(new RuntimeException(s"File $niceJSONFile already exists"))
    } else {
      println(s"Read JSON from $flatJSONFile to $niceJSONFile")
      Formater.prepareFile(flatJSONFile, niceJSONFile)
    }
  }
}
