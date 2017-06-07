name := "akka-cluster-test"

scalaVersion := "2.11.8"

libraryDependencies ++= Seq(
	"com.typesafe.akka" %% "akka-actor" % "2.5.1",
  "com.typesafe.akka" %% "akka-remote" % "2.5.1",
	"com.typesafe.akka" %% "akka-cluster" % "2.5.1",
  "com.typesafe.akka" %% "akka-cluster-tools" % "2.5.1"
)
