val scala3Version = "3.1.1"

lazy val root = project
  .in(file("."))
  .settings(
    name := "chapter21",
    version := "0.1.0-SNAPSHOT",

    scalaVersion := scala3Version,

  )
