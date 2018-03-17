package org.scalamari.pathfinder.application

import akka.http.scaladsl.marshallers.sprayjson.SprayJsonSupport
import akka.http.scaladsl.server.{Directives, Route}
import org.scalamari.pathfinder.application.Segments.IdSegment
import org.scalamari.pathfinder.domain.path.PathService
import org.scalamari.pathfinder.model.{Edge, Path}

import scala.util.{Failure, Success}

private[application] final class PathRoute(pathService: PathService) extends Directives with JsonProtocol with SprayJsonSupport {

  def route: Route = {
    pathPrefix("v1" / "path") {
      (get & path(IdSegment / IdSegment) & pathEndOrSingleSlash) { (fromNodeId, toNodeId) =>
        onComplete(pathService.findPaths(fromNodeId, toNodeId)) {
          case Success(Right(paths)) => complete(paths)
          case Success(Left(error))  => complete(error)
          case Failure(thrown)       => complete(thrown)
        }
      } ~
        (post & path(IdSegment / IdSegment) & entity(as[Map[String, String]]) & pathEndOrSingleSlash) { (fromId, toId, metadata) =>
          val edge = Edge(fromId, toId, metadata)
          onComplete(pathService.createPath(Path(Vector(edge)))) {
            case Success(Right(path)) => complete(path)
            case Success(Left(error)) => complete(error)
            case Failure(thrown)      => complete(thrown)
          }
        }
    }
  }

}
