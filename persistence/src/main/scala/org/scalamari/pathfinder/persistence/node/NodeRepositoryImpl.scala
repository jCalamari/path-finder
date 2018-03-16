package org.scalamari.pathfinder.persistence.node

import com.arangodb.ArangoDatabase
import org.scalamari.pathfinder.domain.node.NodeRepository
import org.scalamari.pathfinder.domain.{DomainError, NodeNotFound, UnknownDomainError}
import org.scalamari.pathfinder.model._
import org.scalamari.pathfinder.persistence.node.NodeErrorMessages._

import scala.concurrent.{ExecutionContext, Future}
import scala.util.control.NonFatal

private[pathfinder] final class NodeRepositoryImpl(database: ArangoDatabase)(implicit ec: ExecutionContext) extends NodeRepository {

  private lazy val nodes = database.collection("nodes")

  override def getNode(id: NodeId): Future[Either[DomainError, Node]] = {
    Future {
      Option(nodes.getDocument(id.value, classOf[Node])) match {
        case Some(node) => Right(node)
        case None => Left(NodeNotFound(id))
      }
    } recover {
      case NonFatal(thrown) => Left(UnknownDomainError(getNodeErrorMessage(id), Some(thrown)))
    }
  }

  override def createNode(node: Node): Future[Either[DomainError, Node]] = {
    Future {
      nodes.insertDocument(node)
      Right(node)
    } recover {
      case NonFatal(thrown) => Left(UnknownDomainError(createNodeErrorMessage(node.id), Some(thrown)))
    }
  }

}