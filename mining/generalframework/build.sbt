import Dependencies._

ThisBuild / scalaVersion     := "2.11.12"
ThisBuild / version          := "0.1.0-SNAPSHOT"
ThisBuild / organization     := "com.unict"
ThisBuild / organizationName := "unict"

lazy val root = (project in file("."))
  .settings(
    name := "generalFramework"
  )

libraryDependencies ++= Seq(
  "org.scalatest" %% "scalatest" % "3.2.2" % Test,
  //main libraries for spark 
  ("org.apache.spark" %% "spark-core" % "2.4.5"),
  ("org.apache.spark" %% "spark-mllib" % "2.4.5"),
  ("org.apache.spark" %% "spark-sql" % "2.4.5"),
  ("org.apache.kafka" %% "kafka-streams-scala" % "2.4.1"),
  ("org.apache.kafka" % "kafka-clients" % "2.5.0"),
  ("org.apache.spark" %% "spark-streaming-kafka-0-10" % "2.4.5"),
  ("org.apache.spark" %% "spark-sql-kafka-0-10" % "2.4.5"),
  //for spark streaming to ELK
  ("org.elasticsearch" % "elasticsearch-spark-20_2.11" % "7.7.1"),
  ("org.apache.spark" %% "spark-avro" % "2.4.5"),
  //logging
  ("com.typesafe.scala-logging" %% "scala-logging" % "3.9.0")
)

dependencyOverrides ++= {
  Seq(
    "com.fasterxml.jackson.module" %% "jackson-module-scala" % "2.8.1",
    "com.fasterxml.jackson.core" % "jackson-databind" % "2.8.1",
    "com.fasterxml.jackson.core" % "jackson-core" % "2.8.1"
  )
}

// See https://www.scala-sbt.org/1.x/docs/Using-Sonatype.html for instructions on how to publish to Sonatype.
