enablePlugins(JavaAppPackaging, AshScriptPlugin)

name := "Reversi in Scala"
organization := "de.htwg.se"
version := "2.0"
scalaVersion := "2.12.7"

dockerBaseImage := "openjdk:8-jre-alpine"
packageName in Docker := "reversi"
dockerExposedPorts := Seq(8080)

libraryDependencies ++= Seq(
  "org.scalatest" %% "scalatest" % "3.0.5" % "test",
  "org.scala-lang.modules" % "scala-swing_2.12" % "2.1.1",
  "net.codingwell" %% "scala-guice" % "4.2.3",
  "org.scala-lang.modules" % "scala-xml_2.12" % "1.2.0",
  "com.typesafe.play" %% "play-json" % "2.7.3",
  "ch.qos.logback" % "logback-classic" % "1.2.3",
  "com.typesafe.scala-logging" %% "scala-logging" % "3.9.2",
  "com.typesafe.akka" %% "akka-http"   % "10.1.8",
  "com.typesafe.akka" %% "akka-stream" % "2.5.19",
  "org.apache.httpcomponents" % "httpclient" % "4.5.8",
  "com.typesafe.slick" %% "slick" % "3.3.0",
  "com.h2database" % "h2" % "1.4.199",
  "org.mongodb.scala" %% "mongo-scala-driver" % "2.6.0"
)

coverageExcludedPackages := "de.htwg.se.reversi.aview.gui;de.htwg.se.reversi.Reversi"