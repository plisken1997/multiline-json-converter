package com.plisken1997.converter.formater

import scala.util.{Failure, Success, Try}
import java.nio.file.{Files, Path, Paths, StandardOpenOption}
import java.io.InputStream
import scala.annotation.tailrec


object JsonFlatFileFormater {
  /**
    *
    * @param srcPath
    * @param destPath
    * @return
    */
  def formatFile(srcPath: String, destPath: String): Try[String] =
    if (Files.notExists(Paths.get(srcPath))) Failure(new RuntimeException(s"$srcPath not found"))
    else {
      val input = Files.newInputStream(Paths.get(srcPath), StandardOpenOption.READ)
      val ln = '\n'.toByte
      val separator = ','.toByte

      createDestIfNotExists(destPath)
        .map { dest =>
          writeFile(input) { row =>
            Files.write(dest, row, StandardOpenOption.APPEND)
            Files.write(dest, Array(ln), StandardOpenOption.APPEND)
          }
          Success(dest.getFileName.toString)
        }
        .getOrElse(Failure(new RuntimeException("unexpected failure...")))
    }

  /**
    *
    * @param dest
    * @return
    */
  private def createDestIfNotExists(dest: String): Option[Path] = {
    val p = Paths.get(dest)
    if (Files.exists(p)) Some(p)
    else Some(Files.createFile(p))
  }

  /**
    *
    * @param input
    * @param writer
    * @return
    */
  private def writeFile(input: InputStream)(writer: Array[Byte] => Unit): List[Array[Byte]] = {
    val step = 1024

    @tailrec
    def smt(input: InputStream, acc: List[Array[Byte]], partialJson: String, pos: Int)(writer: Array[Byte] => Unit): List[Array[Byte]] = {
      val a: Array[Byte] = Array.ofDim[Byte](step)

      val r = input.read(a, 0, step)
      println(s"readline $step bytes at block $pos...")

      val max = 1000000

      if (-1 == r || pos > max) acc
      else {
        val jsonBuffer = partialJson + new String(a)
        JsonExtractor.extractJson(jsonBuffer) match {
          case Right((json, buffer)) => {
            writer(json.toCharArray.map(_.toByte))
            smt(input, acc ++ List(a), buffer, pos + 1)(writer)
          }
          case Left(b) => smt(input, acc ++ List(a), b, pos + 1)(writer)
        }
      }
    }

    smt(input, Nil, "", 0)(writer)
  }
}
