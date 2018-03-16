package org.scalamari.pathfinder.application

import scala.concurrent.Future

private[application] trait ServerShutdownHook {

  def shutdown(): Future[Unit]

}
