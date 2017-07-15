package com.plisken1997.converter.formater

import scala.util.{Failure, Success, Try}
import java.nio.file.{Files, Path, Paths, StandardOpenOption}
import java.io.InputStream

import com.plisken1997.converter.writer.{IoWritter, JSONWriter}

import scala.annotation.tailrec


object JsonFlatFileReader {

  /**
    *
    * @param srcPath
    * @return
    */
  def formatFile[T](srcPath: String)(ioWriter: JSONWriter[T]): Try[String] = {
    assert(Files.exists(Paths.get(srcPath)))
    val input = Files.newInputStream(Paths.get(srcPath), StandardOpenOption.READ)

    writeFile(input)(ioWriter) match {
      case Success(_) => Success("@todo find a nice and usefull success output")
      case _ => Failure(new RuntimeException("unexpected failure..."))
    }
  }

  /**
    *
    * @param input
    * @param writer
    * @return
    */
  private def writeFile[T](input: InputStream)(writer: JSONWriter[T]): Try[List[Array[Byte]]] = {
    val step = 1024
    implicit val ioWriter = writer

    @tailrec
    def smt(input: InputStream, acc: List[Array[Byte]], partialJson: String, pos: Int)(implicit ioWriter: JSONWriter[T]): List[Array[Byte]] = {
      val a: Array[Byte] = Array.ofDim[Byte](step)

      val r = input.read(a, 0, step)
      // @todo log in a clean way...
      println(s"readline $step bytes at block $pos...")

      // @todo find a better way to prevent buffer overflow
      val max = 1000000

      if (-1 == r || pos > max) acc
      else {
        val jsonBuffer = partialJson + new String(a)
        JsonExtractor.extractJson(jsonBuffer) match {
          case Right((json, buffer)) => {
            writer.write(json.toCharArray.map(_.toByte))
            smt(input, acc ++ List(a), buffer, pos + 1)(writer)
          }
          case Left(b) => smt(input, acc ++ List(a), b, pos + 1)(writer)
        }
      }
    }

    Success(smt(input, Nil, "", 0))
  }
}
