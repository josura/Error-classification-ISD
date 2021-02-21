lazy val root = (project in file(".")).
  settings(
    inThisBuild(List(
      organization := "com.unict",
      scalaVersion := "2.11.12"
    )),
    name := "error-classification",
    version := "0.1"
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
  ("org.apache.spark" %% "spark-avro" % "2.4.5")
)
