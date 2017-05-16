package com.plisken1997.converter.formater

import scala.annotation.tailrec

/**
  * Created by rbacconnier on 17/05/2017.
  */
object JsonExtractor {
  /**
    *
    * @param chunck
    * @return
    */
  def extractJson(chunck: String): Either[String, (String, String)] = {
    // @todo handle empty strung for chunk
    val pos = extractor(1, chunck.toCharArray.toList.tail, 1)
    if (pos == -1) {
      Left(chunck)
    } else {
      Right(chunck.substring(0, pos), chunck.substring(pos))
    }
  }

  /**
    *
    * @param open
    * @param content
    * @param pos
    * @return
    */
  @tailrec
  private def extractor(open: Int, content: List[Char], pos: Int): Int = {
    if (content.isEmpty) -1
    else {
      if (content.head == '}') {
        if (open - 1 == 0) pos + 1
        else extractor(open - 1, content.tail, pos + 1)
      }
      else {
        if (content.head == '{') extractor(open + 1, content.tail, pos + 1)
        else extractor(open, content.tail, pos + 1)
      }
    }
  }
}
