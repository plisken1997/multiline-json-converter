import java.time.LocalDateTime

import scala.util.{Success, Try}

package object formater {

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
