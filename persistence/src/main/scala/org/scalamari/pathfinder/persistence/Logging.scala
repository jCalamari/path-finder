package org.scalamari.pathfinder.persistence

import org.slf4j.{Logger, LoggerFactory}

private[pathfinder] trait Logging {

  // TODO move to domain and abstract
  protected lazy val logger: Logger = LoggerFactory.getLogger(this.getClass)

}
