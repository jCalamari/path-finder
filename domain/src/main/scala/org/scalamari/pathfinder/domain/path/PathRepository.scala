package org.scalamari.pathfinder.domain.path

import org.scalamari.pathfinder.domain.DomainError
import org.scalamari.pathfinder.model.{Path, NodeId}

import scala.concurrent.Future

private[pathfinder] trait PathRepository {

  def createPath(path: Path): Future[Either[DomainError, Path]]

  def findPaths(fromNodeId: NodeId, toNodeId: NodeId): Future[Either[DomainError, Vector[Path]]]

}
