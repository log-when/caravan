// See README.md for license details.
// updated by log-when 

ThisBuild / scalaVersion     := "2.12.15"
ThisBuild / version          := "0.1.0"
ThisBuild / organization     := "com.github.merl"

name := "Caravan"
libraryDependencies ++= Seq(
  "edu.berkeley.cs" %% "chisel3" % "3.7-SNAPSHOT",
  "edu.berkeley.cs" %% "chiseltest" % "0.7-SNAPSHOT" % "test",
  "org.scalatest" %% "scalatest" % "3.0.6" % "test",
  "org.scalatestplus" %% "scalacheck-1-14" % "3.2.2.0" % "test",
)
scalacOptions ++= Seq(
  "-Xsource:2.11",
  "-language:reflectiveCalls",
  "-deprecation",
  "-feature",
  "-Xcheckinit"
)
addCompilerPlugin("edu.berkeley.cs" % "chisel3-plugin" % "3.7-SNAPSHOT" cross CrossVersion.full)
addCompilerPlugin("org.scalamacros" % "paradise" % "2.1.1" cross CrossVersion.full)


