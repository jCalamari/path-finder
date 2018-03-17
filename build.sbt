name := "path-finder"

def PathFinderProject(name: String): Project = {
  Project(name, file(name))
    .settings(organization := "org.scalamari.pathfinder")
    .settings(scalaVersion := "2.12.4")
    .settings(version := "0.1")
    .settings(scalacOptions ++= Seq(
      "-deprecation",
      "-explaintypes",
      "-feature",
      "-unchecked",
      "-Xfatal-warnings",
      "-target:jvm-1.8",
      "-encoding", "UTF-8",
      "-Ywarn-dead-code",
      "-Ywarn-numeric-widen"
    ))
}

lazy val model = PathFinderProject("model")

lazy val domain = PathFinderProject("domain").dependsOn(model)

lazy val persistence = PathFinderProject("persistence")
  .settings(Dependencies.persistence)
  .dependsOn(domain)

lazy val application = PathFinderProject("application")
  .settings(Dependencies.application)
  .settings(mainClass := Some("org.scalamari.pathfinder.application.Main"))
  .settings(fork := true)
  .settings(packageName := "path-finder")
  .settings(dockerRepository := Some("scalamari"))
  .settings(dockerBaseImage := "openjdk:8u151-jdk-stretch")
  .settings(dockerUpdateLatest := true)
  .enablePlugins(JavaAppPackaging, DockerPlugin)
  .dependsOn(persistence)