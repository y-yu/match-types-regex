import sbt.Keys.libraryDependencies

ThisBuild / version := "0.1.0-SNAPSHOT"

ThisBuild / scalaVersion := "3.1.3"

lazy val root = (project in file("."))
  .settings(
    name := "MatchTypesRegex",
    libraryDependencies += "org.scalatest" %% "scalatest" % "3.2.11" % "test"
  )
