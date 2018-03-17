import sbt._
import Keys._

object Dependencies {

  object Versions {
    val akkaHttp      = "10.1.0"
    val akkaStreams   = "2.5.11"
    val arangoDB      = "4.3.3"
    val arangoDBJdk8  = "1.0.3"
    val arangoDBScala = "1.0.1"
    val logback       = "1.2.3"
  }

  object Libraries {
    val akkaHttp            = "com.typesafe.akka"       %% "akka-http"                            % Versions.akkaHttp
    val akkaHttpSprayJson   = "com.typesafe.akka"       %% "akka-http-spray-json"                 % Versions.akkaHttp
    val akkaStreams         = "com.typesafe.akka"       %% "akka-stream"                          % Versions.akkaStreams
    val arangoDB            = "com.arangodb"            %  "arangodb-java-driver"                 % Versions.arangoDB
    val arangoDBJdk8        = "com.arangodb"            %  "velocypack-module-jdk8"               % Versions.arangoDBJdk8
    val arangoDBScala       = "com.arangodb"            %  "velocypack-module-scala"              % Versions.arangoDBScala
    val logback             = "ch.qos.logback"          %  "logback-classic"                      % Versions.logback
  }

  lazy val persistence = libraryDependencies ++= Seq(
    Libraries.arangoDB,
    Libraries.arangoDBJdk8,
    Libraries.arangoDBScala,
    Libraries.logback
  )

  lazy val application = libraryDependencies ++= Seq(
    Libraries.akkaHttp,
    Libraries.akkaHttpSprayJson,
    Libraries.akkaStreams
  )

}
