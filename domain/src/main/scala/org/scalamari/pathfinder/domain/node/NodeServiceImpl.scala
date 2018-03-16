package org.scalamari.pathfinder.domain.node
import org.scalamari.pathfinder.domain.DomainError
import org.scalamari.pathfinder.model.{Node, NodeId}

import scala.concurrent.Future

private[pathfinder] final class NodeServiceImpl(nodeRepository: NodeRepository) extends NodeService {

  override def getNode(id: NodeId): Future[Either[DomainError, Node]] = {
    nodeRepository.getNode(id)
  }

  override def createNode(node: Node): Future[Either[DomainError, Node]] = {
    nodeRepository.createNode(node)
  }
}
