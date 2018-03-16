package org.scalamari.pathfinder.application

import akka.actor.ActorSystem
import akka.http.scaladsl.Http
import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.server.Route
import akka.http.scaladsl.util.FastFuture._
import akka.stream.ActorMaterializer
import com.arangodb.velocypack.module.jdk8.VPackJdk8Module
import com.arangodb.velocypack.module.scala.VPackScalaModule
import com.arangodb.{ArangoDB, ArangoDatabase}
import org.scalamari.pathfinder.domain.node.{NodeRepository, NodeService}
import org.scalamari.pathfinder.domain.path.{PathRepository, PathService}
import org.scalamari.pathfinder.persistence.node.NodeRepositoryImpl
import org.scalamari.pathfinder.persistence.path.PathRepositoryImpl

import scala.concurrent.{ExecutionContextExecutor, Future}

private[application] final class Server(config: ServerConfig) {

  def start: Future[ServerShutdownHook] = {
    implicit val system: ActorSystem = ActorSystem(config.serverName)
    implicit val materializer: ActorMaterializer = ActorMaterializer()
    implicit val executionContext: ExecutionContextExecutor = system.dispatcher

    val arango = buildArangoDatabase
    val nodeRepository: NodeRepository = new NodeRepositoryImpl(arango)
    val nodeService: NodeService = NodeService(nodeRepository)
    val nodeRoute = new NodeRoute(nodeService)
    val routeRepository: PathRepository = new PathRepositoryImpl(arango)
    val pathService: PathService = PathService(routeRepository)
    val pathRoute = new PathRoute(pathService)
    val route = Route.seal(pathRoute.route ~ nodeRoute.route)

    Http().bindAndHandle(route, config.host, config.port).map { binding =>
      new ServerShutdownHook {
        override def shutdown(): Future[Unit] = {
          binding.unbind().fast.map(_ => ())
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
