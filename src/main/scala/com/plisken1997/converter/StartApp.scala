package com.plisken1997.converter

import java.nio.file.Paths

import com.plisken1997.formater.Formater

/**
  * Created by rbacconnier on 17/05/2017.
  */
object StartApp {
  def main(args: Array[String]): Unit = {
    initFiles
  }

  // maybe not still relevant
  private def home = Paths.get("").toAbsolutePath.toString

  // @todo yeah, should be given from the command line
  private def srcFile = s"${home}/resources/data/extract_test.json"

  // @todo ... should be given from the command line to !
  private def destFile = s"${home}/resources/data/extract_OK_full.json"

  /**
    *
    * @return
    */
  private def initFiles = {
    lazy val flatTweetsFile = srcFile
    lazy val tweetsFile = destFile

    Formater.prepareFile(flatTweetsFile, tweetsFile)
  }
}
