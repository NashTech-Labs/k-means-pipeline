package com.knoldus.pipeline

import org.apache.spark.sql.{DataFrame, SQLContext}
import org.apache.spark.{SparkConf, SparkContext}
import org.scalatest.FunSuite

import scala.collection.immutable.List

class KMeansPipeLineTest extends FunSuite {
  val kMeans = new KMeansPipeLine()
  val conf = new SparkConf().setAppName("K-means-pipeline-test").setMaster("local[4]")
  val sc = new SparkContext(conf)
  val sqlContext = new SQLContext(sc)
  val input = sqlContext.createDataFrame(Seq(
    ("a@email.com", 12000,"M"),
    ("b@email.com", 43000,"M"),
    ("c@email.com", 5000,"F"),
    ("d@email.com", 60000,"M")
  )).toDF("email", "income","gender")

  val predictionResult = kMeans.predict(sqlContext,input,List("gender","email"),2,10)
  test("should return data frame") {
    predictionResult.show()
    predictionResult.isInstanceOf[DataFrame]
    sc.stop()
  }

}
