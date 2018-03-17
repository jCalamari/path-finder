package org.scalamari.pathfinder.application

import scala.concurrent.Future

private[application] trait ShutdownHook {

  def shutdown(): Future[Unit]

}
