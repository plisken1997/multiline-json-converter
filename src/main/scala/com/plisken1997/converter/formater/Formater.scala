package com.plisken1997.formater

import java.time.LocalDateTime
import scala.util.{Success, Try}
import com.plisken1997.converter.formater.JsonFlatFileReader
import com.plisken1997.converter.writer.{IoWritter, JSONWriter}

object Formater {

  /**
    *
    * @param src
    * @return
    */
  def read[T](src: String)(writer: JSONWriter[T]): Try[String] = withDateLog[Try[String]] { () =>
    // val ioWriter:IoWritter[Path] = writer.write(_: Array[Byte])
    JsonFlatFileReader.formatFile(src)(writer)
  }(println)

  /**
    *
    * @param fn
    * @tparam A
    * @return
    */
  private def withDateLog[A](fn: () => A)(logWriter: String => Any): A =  {
    logWriter(s"jsonfile format started at ${LocalDateTime.now()}")
    val res = fn()
    logWriter(s"jsonfile format ended at ${LocalDateTime.now()}")
    res
  }
}
