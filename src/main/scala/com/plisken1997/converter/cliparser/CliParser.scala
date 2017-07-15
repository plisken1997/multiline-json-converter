package com.plisken1997.converter.cliparser


object CliParser {
  private lazy val parser = new scopt.OptionParser[Config]("scopt") {
    head("scopt", "3.x")

    opt[String]('s', "source").required().action( (s, c) =>
      c.copy(source = Some(s)) ).text("Source is the path to the source JSON file")

    opt[String]('d', "dest").action( (d, c) =>
      c.copy(dest = Some(d)) ).text("Dest is the path to the destination JSON file")

    help("help").text("If a given file path is not absolute, the path will be computed relative from the current directory")
  }

  def parse(args: Seq[String]) = parser.parse(args, Config())
}
