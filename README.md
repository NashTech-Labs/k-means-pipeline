# k-means-pipline
## Using k-means pipline library to predict on categorical values
###Requirements
Spark 1.5.0 or Higher
### How to  
Include this package in your Spark Applications using:
#### spark-shell, pyspark, or spark-submit
<pre>> $SPARK_HOME/bin/spark-shell --packages knoldus:k-means-pipeline:0.0.1</pre>
### sbt
<pre>resolvers += "Spark Packages Repo" at "http://dl.bintray.com/spark-packages/maven"
libraryDependencies += "knoldus" % "k-means-pipeline" % "0.0.1"</pre>
### Maven  
In your pom.xml, add:  
```xml
<dependencies>
  <!-- list of dependencies -->
  <dependency>
    <groupId>knoldus</groupId>
    <artifactId>k-means-pipeline</artifactId>
    <version>0.0.1</version>
  </dependency>
</dependencies>
<repositories>
  <!-- list of other repositories -->
  <repository>
    <id>SparkPackagesRepo</id>
    <url>http://dl.bintray.com/spark-packages/maven</url>
  </repository>
</repositories>
```
Example Snippet:  
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
Issues and bug reports are welcome! 
