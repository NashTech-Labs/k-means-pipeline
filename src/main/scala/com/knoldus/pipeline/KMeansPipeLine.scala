package com.knoldus.pipeline

import org.apache.spark.ml.clustering.KMeans
import org.apache.spark.ml.feature.{OneHotEncoder, StringIndexer, VectorAssembler}
import org.apache.spark.ml.{Pipeline, PipelineStage}
import org.apache.spark.sql.{DataFrame, SQLContext}

import scala.collection.immutable.List


class KMeansPipeLine {
  /**
    * @param sQLContext
    * @param DataFrame
    * @param categoricalFeatures name of categorical features as list of String
    * @param k number of clusters
    * @param iterations max number of iterations
    * @return predicted DataFrame including prediction field
    */
  def predict(sQLContext: SQLContext, df: DataFrame, categoricalFeatures: List[String], k: Int, iterations:Int)
  : DataFrame = {
    val numericalFeatures = df.columns.filter(column => !categoricalFeatures.contains(column))
    val indexedCategory = categoricalFeatures.map(feature => feature + "IndexVec")
    val predictOn = indexedCategory.:::(numericalFeatures.toList)
    val indexer = stringIndex(categoricalFeatures)
    val fieldsToOneHotEncode = categoricalFeatures.map(categoricalFeature => categoricalFeature + "Index")
    val oneHotEncoder = oneHotEncode(fieldsToOneHotEncode)
    val kMeans = new KMeans().setK(k).setFeaturesCol("features").setPredictionCol("prediction").setMaxIter(iterations)
    val assembler = vectorAssemble(predictOn)
    val allStages = indexer.:::(oneHotEncoder).:::(List(assembler)).:::(List(kMeans))
    val arrayOfStages: Array[PipelineStage] = allStages.reverse.toArray
    val pipeline = new Pipeline().setStages(arrayOfStages)
    pipeline.fit(df).transform(df).select("prediction",df.columns:_*)
  }

  /**
    * Indexes categorical columns to numerical indexes
    * @param categoricalColumns
    * @return StringIndexer as Pipeline Stage
    */
  private def stringIndex(categoricalColumns: List[String]): List[StringIndexer] = {
    categoricalColumns.map(column => new StringIndexer().setInputCol(column).setOutputCol(column + "Index"))
  }

  /**
    * Generates vectors for indexed columns using OneHotEncoding
    * @param indexedColumns
    * @return List of Encoders for each indexed feature
    */
  private def oneHotEncode(indexedColumns: List[String]): List[OneHotEncoder] = {
    indexedColumns.map(indexedColumn => new OneHotEncoder().setInputCol(indexedColumn).setOutputCol(indexedColumn +
      "Vec"))
  }

  /**
    * Assembles vector of all the features including numerical and categorical columns
    * @param inputColumns
    * @return VectorAssembler
    */
  private def vectorAssemble(inputColumns: List[String]): VectorAssembler = {
    new VectorAssembler().setInputCols(inputColumns.toArray).setOutputCol("features")
  }

}
