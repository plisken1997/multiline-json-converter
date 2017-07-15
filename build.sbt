name := "Multiline JSON Converter"

version := "0.0.1"

scalaVersion := "2.11.8"


//val circeVersion = "0.8.0"
//libraryDependencies ++= Seq(
//  "io.circe" %% "circe-core",
//  "io.circe" %% "circe-generic",
//  "io.circe" %% "circe-parser"
//).map(_ % circeVersion)


libraryDependencies += "org.scalactic" %% "scalactic" % "3.0.1"
libraryDependencies += "com.github.scopt" %% "scopt" % "3.5.0"
libraryDependencies += "org.scalatest" %% "scalatest" % "3.0.1" % "test"
