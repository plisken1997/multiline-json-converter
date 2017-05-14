
object StartApp {
  def main(args: Array[String]): Unit = {
    initFiles
  }

  private def home = "/Users/rbacconnier/var/www/sandbox/contrib-spark"

  private def srcFile = s"${home}/resources/data/extract_test.json"

  private def destFile = s"${home}/resources/data/extract_OK_full.json"


  /**
    *
    * @return
    */
  private def initFiles = {
    lazy val flatTweetsFile = srcFile
    lazy val tweetsFile = destFile

    formater.prepareFile(flatTweetsFile, tweetsFile)
  }
}
