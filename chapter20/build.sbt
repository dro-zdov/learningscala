val scala3Version = "3.1.1"

lazy val root = project
  .in(file("."))
  .settings(
    name := "chapter",
    version := "0.1.0-SNAPSHOT",

    scalaVersion := scala3Version,
    libraryDependencies += "org.scala-lang.modules" %% "scala-parser-combinators" % "2.1.1"
  )
