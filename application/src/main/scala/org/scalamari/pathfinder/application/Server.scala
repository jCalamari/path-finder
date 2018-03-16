package org.scalamari.pathfinder.application

import akka.actor.ActorSystem
import akka.http.scaladsl.Http
import akka.http.scaladsl.server.Route
import akka.stream.ActorMaterializer
import com.arangodb.velocypack.module.jdk8.VPackJdk8Module
import com.arangodb.velocypack.module.scala.VPackScalaModule
import com.arangodb.{ArangoDB, ArangoDatabase}
import org.scalamari.pathfinder.domain.path.{PathRepository, PathService}
import org.scalamari.pathfinder.persistence.path.PathRepositoryImpl

import scala.concurrent.{ExecutionContextExecutor, Future}

private[application] final class Server(config: ServerConfig) {

  def start: Future[ServerShutdownHook] = {
    implicit val system: ActorSystem = ActorSystem(config.serverName)
    implicit val materializer: ActorMaterializer = ActorMaterializer()
    implicit val executionContext: ExecutionContextExecutor = system.dispatcher

    val routeRepository: PathRepository = new PathRepositoryImpl(buildArangoDatabase)
    val pathService: PathService = PathService(routeRepository)
    val pathRoute = new PathRoute(pathService)
    val route = Route.seal(pathRoute.route)

    Http().bindAndHandle(route, config.host, config.port).map { binding =>
      new ServerShutdownHook {
        override def shutdown(): Future[Unit] = {
          binding.unbind().map(_ => ())
        }
      }
    }
  }

  private def buildArangoDatabase: ArangoDatabase = {
    new ArangoDB.Builder()
      .registerModule(new VPackScalaModule)
      .registerModule(new VPackJdk8Module)
      .build()
      .db(config.serverName)
  }

}
