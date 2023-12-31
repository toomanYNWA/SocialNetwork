name := """social-network"""
organization := "com.example"

version := "1.0-SNAPSHOT"

lazy val root = (project in file(".")).enablePlugins(PlayScala)

scalaVersion := "2.13.10"


libraryDependencies ++= Seq(
  guice,
  "org.scalatestplus.play" %% "scalatestplus-play" % "5.0.0" % Test,
  "mysql" % "mysql-connector-java" % "8.0.28",
  "com.typesafe.play" %% "play-slick-evolutions" % "5.0.0",
  "com.pauldijou" %% "jwt-play" % "5.0.0",
  "com.pauldijou" %% "jwt-core" % "5.0.0",
  "org.mindrot" % "jbcrypt" % "0.4"

)
//libraryDependencies += "com.github.jwt-scala" %% "jwt-core" % "9.2.0"

//libraryDependencies ++= Seq(
//  "com.auth0" % "jwks-rsa" % "0.6.1"
//)
//
//libraryDependencies ++= Seq(
//  guice,
//  "org.scalatestplus.play" %% "scalatestplus-play" % "5.0.0" % Test,
//  "mysql" % "mysql-connector-java" % "8.0.27",
//  "com.typesafe.play" %% "play-slick" % "5.0.0",
//  "com.typesafe.play" %% "play-slick-evolutions" % "5.0.0"
//)

// Adds additional packages into Twirl
//TwirlKeys.templateImports += "com.example.controllers._"

// Adds additional packages into conf/routes
// play.sbt.routes.RoutesKeys.routesImport += "com.example.binders._"
