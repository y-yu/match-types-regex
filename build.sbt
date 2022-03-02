ThisBuild / version := "0.1.0-SNAPSHOT"

ThisBuild / scalaVersion := "3.1.2-RC1"

lazy val root = (project in file("."))
  .settings(
    name := "MatchTypesRegex"
  )
