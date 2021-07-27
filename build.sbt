import Dependencies._

ThisBuild / scalaVersion := "2.13.6"
ThisBuild / version := "0.1.0-SNAPSHOT"
ThisBuild / organization := "com.github.aaabramov"

lazy val root = (project in file("."))
  .settings(
    name := "cats-demo",
    libraryDependencies += scalaTest % Test,
    libraryDependencies ++= circe,
    libraryDependencies += cats
  )