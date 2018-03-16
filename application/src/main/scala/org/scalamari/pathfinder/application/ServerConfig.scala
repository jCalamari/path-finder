package org.scalamari.pathfinder.application

import com.typesafe.config.{Config, ConfigFactory}

private[application] final case class ServerConfig(host: String, port: Int, serverName: String)

private[application] object ServerConfig {

  def apply(config: Config = ConfigFactory.load()): ServerConfig = {
    ServerConfig(config.getString("server.host"), config.getInt("server.port"), "path-finder")
  }

}