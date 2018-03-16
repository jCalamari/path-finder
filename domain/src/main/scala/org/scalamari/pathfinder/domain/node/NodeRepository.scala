package org.scalamari.pathfinder.domain.node

import org.scalamari.pathfinder.domain.DomainError
import org.scalamari.pathfinder.model.{Node, NodeId}

import scala.concurrent.Future

trait NodeRepository {

  def createNode(node: Node): Future[Either[DomainError, Node]]

  def getNode(id: NodeId): Future[scala.Either[DomainError, Node]]

}
