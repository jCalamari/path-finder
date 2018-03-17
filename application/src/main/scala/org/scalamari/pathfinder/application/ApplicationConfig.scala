package org.scalamari.pathfinder.application

import com.typesafe.config.{Config, ConfigFactory}
import org.scalamari.pathfinder.persistence.PersistenceConfig

private[application] final case class ApplicationConfig(host: String, port: Int, applicationName: String, persistence: PersistenceConfig)

private[application] object ApplicationConfig {

  def apply(config: Config = ConfigFactory.load()): ApplicationConfig = {
    val persistence = PersistenceConfig(
      host = config.getString("path-finder.persistence.host"),
      port = config.getInt("path-finder.persistence.port"))
    ApplicationConfig(
      host = config.getString("path-finder.application.host"),
      port = config.getInt("path-finder.application.port"),
      applicationName = "path-finder",
      persistence = persistence
    )
  }

}
