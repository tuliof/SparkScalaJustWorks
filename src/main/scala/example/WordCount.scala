package example

import org.apache.spark.{SparkConf, SparkContext}

object WordCount extends App {
  val conf = new SparkConf()
    .setAppName("Simple Application")
    .setMaster("local")
  val sc = new SparkContext(conf)

//  val stream: InputStream = getClass.getResourceAsStream("/lorem_ipsum.txt")
//  val inputFile = scala.io.Source.fromInputStream( stream ).mkString
  val inputFile = "src/main/resources/lorem_ipsum.txt"
  val outputFile = "C://Users//Tulio//Desktop//result" + (System.currentTimeMillis() / 1000)

  val input = sc.textFile(inputFile)
  input.flatMap(_.split(" "))
    .map(w => (w.toLowerCase.replaceAll("[^a-z]",""), 1))
    .reduceByKey(_+_)
    .saveAsTextFile(outputFile)

  sc.stop()
}
