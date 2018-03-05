name := "SparkScalaJustWorks"

version := "0.1"

mainClass in (Compile , run):= Some("example.WordCount")
mainClass in assembly := Some("example.WordCount")

// Spark 2.2.0 is compatible with Scala 2.11.x. Don't use 2.12 or greater.
scalaVersion := "2.11.12"

val sparkVersion = "2.2.0"
val sparkTestingVersion = "2.2.0_0.7.4"

// Fix the "Incompatible Jackson version: 2.7.3" error
dependencyOverrides += "com.fasterxml.jackson.core" % "jackson-core" % "2.8.9"
dependencyOverrides += "com.fasterxml.jackson.core" % "jackson-databind" % "2.8.9"
dependencyOverrides += "com.fasterxml.jackson.module" % "jackson-module-scala_2.11" % "2.8.9"

libraryDependencies ++= Seq(
  "org.apache.spark" %% "spark-core" % sparkVersion % "provided",
  "org.apache.spark" %% "spark-sql" % sparkVersion % "provided",
  "org.apache.hadoop" % "hadoop-client" % "2.8.1" % "provided",
  "org.scala-lang" % "scala-reflect" % "2.11.11",
  "com.opencsv" % "opencsv" % "4.0", // CSV reader library
  "com.hortonworks.registries" % "schema-registry-client" % "0.3.0" exclude ("org.slf4j", "log4j-over-slf4j"),
  "org.apache.avro" % "avro" % "1.8.2", // Object serialization
  "com.github.scopt" %% "scopt" % "3.7.0", // Commando line parser
  "org.scalatest" % "scalatest_2.11" % "3.0.3" % "test",
  "joda-time" % "joda-time" % "2.9.9", // Date and time utilities
  "com.holdenkarau" % "spark-testing-base_2.11" % sparkTestingVersion
)

// Fix the deduplication issue
assemblyOption in assembly := (assemblyOption in assembly).value.copy(includeScala = false)
assemblyJarName in assembly := s"${name.value}_${scalaBinaryVersion.value}-${version.value}.jar"
assemblyMergeStrategy in assembly := {
  case PathList("META-INF", xs @ _*) => MergeStrategy.discard
  case x => MergeStrategy.first
}

// Fix the "java.lang.NoClassDefFoundError" for spark-core and other classes marked as "provided"
run in Compile := Defaults.runTask(fullClasspath in Compile, mainClass in (Compile, run), runner in (Compile, run)).evaluated
runMain in Compile := Defaults.runMainTask(fullClasspath in Compile, runner in(Compile, run)).evaluated