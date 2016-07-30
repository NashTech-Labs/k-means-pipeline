name := "k-means-pipeline"

version := "0.0.1"

val spark_core = "org.apache.spark" %% "spark-core" % "1.6.2"
val spark_sql = "org.apache.spark" %% "spark-sql" % "1.6.2"
val spark_ml = "org.apache.spark" %% "spark-mllib" % "1.6.2"
val scala_test = "org.scalatest" % "scalatest_2.10" % "3.0.0-M7"

spName := "knoldus/k-means-pipeline"
sparkVersion := "1.6.2"
sparkComponents += "mllib"
sparkComponents += "sql"
sparkComponents += "core"



lazy val commonSettings = Seq(
  organization := "com.knoldus",
  version := "0.1.0",
  scalaVersion := "2.10.6"
)

lazy val root = (project in file(".")).
  settings(commonSettings: _*).
  settings(
    name := "k-means-pipeline",
    libraryDependencies ++= Seq(scala_test)
  )

