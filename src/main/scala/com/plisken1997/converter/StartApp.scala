package com.plisken1997.converter

import java.nio.file.{Files, Path, Paths}

import com.plisken1997.converter.cliparser.CliParser
import com.plisken1997.converter.writer.PrettyJSONFileWriter
import com.plisken1997.formater.Formater

import scala.util.{Failure, Success, Try}

object StartApp {
  lazy val home = Paths.get("").toAbsolutePath.toString

  def main(args: Array[String]): Unit = {
    val format = CliParser.parse(args) match {
      case Some(config) => handleFormatFile(config.source, config.dest)
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
  private def handleFormatFile(srcFile: Option[String], destFile: Option[String]): Try[String] = {
    def formatRelativePath(path: String) = if (path.startsWith("/")) path else s"$home/$path"

    lazy val defaultOutputPath = s"$home/output-json.json"
    lazy val flatJSONFile = srcFile.map(formatRelativePath)
    val targetJSONFile = destFile.map(formatRelativePath).getOrElse(defaultOutputPath)

    if (Files.exists(Paths.get(targetJSONFile))) {
      Failure(new RuntimeException(s"File $targetJSONFile already exists"))
    } else {
      println(s"Read JSON from $flatJSONFile to $targetJSONFile")
      PrettyJSONFileWriter
        .createWriter(targetJSONFile)
        .flatMap { writer =>
          flatJSONFile
            .map(Formater.read(_)(writer))
            .getOrElse(Failure(new RuntimeException("Input json file should be given")))
        }
    }
  }
}
