package org.scalamari.pathfinder.application

import akka.http.scaladsl.marshallers.sprayjson.SprayJsonSupport
import akka.http.scaladsl.server.{Directives, Route}
import org.scalamari.pathfinder.application.Segments.IdSegment
import org.scalamari.pathfinder.domain.UnknownDomainError
import org.scalamari.pathfinder.domain.node.NodeService
import org.scalamari.pathfinder.model.Node

import scala.util.{Failure, Success}

private[pathfinder] class NodeRoute(nodeService: NodeService) extends Directives with JsonProtocol with SprayJsonSupport {

  def route: Route = {
    pathPrefix("v1" / "node") {
      (get & path(IdSegment) & pathEndOrSingleSlash) { nodeId =>
        onComplete(nodeService.getNode(nodeId)) {
          case Success(Right(node)) => complete(node)
          case Success(Left(error)) => complete(error)
          case Failure(thrown)      => complete(UnknownDomainError(thrown))
        }
      } ~
        (post & entity(as[Node]) & pathEndOrSingleSlash) { newNode =>
          onComplete(nodeService.createNode(newNode)) {
            case Success(Right(node)) => complete(node)
            case Success(Left(error)) => complete(error)
            case Failure(thrown)      => complete(thrown)
          }
        }
    }
  }

}
