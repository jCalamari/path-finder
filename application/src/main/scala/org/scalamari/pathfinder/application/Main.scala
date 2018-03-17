package org.scalamari.pathfinder.application

import scala.concurrent.Await
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.duration._
import scala.util.{Failure, Success}

private[application] object Main {

  private val waitForServerTimeout = 60.seconds

  def main(args: Array[String]): Unit = {
    val config = ApplicationConfig()
    new Application(config).start.onComplete {
      case Success(hook) => sys.addShutdownHook {
        Await.result(hook.shutdown(), waitForServerTimeout)
      }
      case Failure(thrown) => throw new RuntimeException("Server startup failed.", thrown)
    }
  }

}
