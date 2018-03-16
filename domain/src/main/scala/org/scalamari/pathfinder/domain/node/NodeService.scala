package org.scalamari.pathfinder.domain.node

import org.scalamari.pathfinder.domain.DomainError
import org.scalamari.pathfinder.model.{Node, NodeId}

import scala.concurrent.Future

private[pathfinder] trait NodeService {

  def createNode(node: Node): Future[Either[DomainError, Node]]

  def getNode(id: NodeId): Future[Either[DomainError, Node]]

}

private[pathfinder] object NodeService {

  def apply(nodeRepository: NodeRepository): NodeService = new NodeServiceImpl(nodeRepository)

}