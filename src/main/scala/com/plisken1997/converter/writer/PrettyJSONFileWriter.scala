package com.plisken1997.converter.writer

import java.nio.charset.Charset
import java.nio.file.{Files, Path, Paths, StandardOpenOption}
import scala.util.{Failure, Success, Try}

sealed trait StringWriterUtil {
  protected def toByteArray(str: String): Array[Byte] = str.getBytes(Charset.forName("UTF-8"))
}

sealed class PrettyJSONFileWriter(val destPath: Path)
  extends JSONWriter[Try[Path]]
    with StringWriterUtil {

  val ln = toByteArray(System.getProperty("line.separator"))
  val separator = toByteArray(",")

  /**
    * @todo remove {separator} from last line
    * @param row
    * @return
    */
  def write(row: Array[Byte]): Try[Path] =
    for {
      _ <- writeRow(row)
      _ <- writeRow(separator)
      _ <- writeRow(ln)
    } yield destPath

  /**
    *
    * @param row
    * @return
    */
  private def writeRow(row: Array[Byte]): Try[Path] = {
    try {
      Success(Files.write(destPath, row, StandardOpenOption.APPEND))
    } catch {
      case e: Throwable => Failure(e)
    }
  }
}

object PrettyJSONFileWriter {
  /**
    *
    * @param targetJSONFile
    * @return
    */
  def createWriter(targetJSONFile: String): Try[PrettyJSONFileWriter] =
    createDestIfNotExists(targetJSONFile)
      .map { path =>
        new PrettyJSONFileWriter(path)
      }

  /**
    *
    * @param dest
    * @return
    */
  private def createDestIfNotExists(dest: String): Try[Path] = {
    val p = Paths.get(dest)
    if (Files.exists(p)) Success(p)
    else {
      try {
        Success(Files.createFile(p))
      } catch {
        case e: Throwable => Failure(e)
      }
    }
  }
}