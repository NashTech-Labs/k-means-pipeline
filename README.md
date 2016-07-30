# k-means-pipline
## Using k-means pipline library to predict on categorical values
Include the library jar from here and include it with your spark application. 
```scala
  import com.knoldus.pipeline.KMeansPipeLine

  val kMeans = new KMeansPipeLine()
   val df = sqlContext.createDataFrame(Seq(
    ("a@email.com", 12000,"M"),
    ("b@email.com", 43000,"M"),
    ("c@email.com", 5000,"F"),
    ("d@email.com", 60000,"M")
  )).toDF("email", "income","gender")
  
  val categoricalFeatures = List("gender","email")
  val numberOfClusters = 2
  val iterations = 10
  val predictionResult = kMeans.predict(sqlContext,df,categoricalFeatures,numberOfClusters,iterations)

```
