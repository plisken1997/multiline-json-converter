package com.plisken1997.formater

import java.time.LocalDateTime
import scala.util.{Success, Try}
import com.plisken1997.converter.formater.JsonFlatFileFormater

object Formater {

  /**
    *
    * @param src
    * @param dest
    * @return
    */
  def prepareFile(src: String, dest: String): Try[String] = withDateLog[Try[String]] { () =>
    for {
      destfile <- JsonFlatFileFormater.formatFile(src, dest)
      _ <- Success(println(s"${destfile} created !"))
    } yield (destfile)
  }

  /**
    *
    * @param fn
    * @tparam A
    * @return
    */
  private def withDateLog[A](fn: () => A): A =  {
    println(s"jsonfile format started at ${LocalDateTime.now()}")
    val res = fn()
    println(s"jsonfile format ended at ${LocalDateTime.now()}")
    res
  }
}
