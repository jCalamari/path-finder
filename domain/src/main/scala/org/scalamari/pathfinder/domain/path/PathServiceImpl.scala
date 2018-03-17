package org.scalamari.pathfinder.domain.path

import org.scalamari.pathfinder.domain.DomainError
import org.scalamari.pathfinder.model.{Path, NodeId}

import scala.concurrent.Future

private[domain] final class PathServiceImpl(pathRepository: PathRepository) extends PathService {

  override def findPaths(fromNodeId: NodeId, toNodeId: NodeId): Future[Either[DomainError, Vector[Path]]] = {
    pathRepository.findPaths(fromNodeId, toNodeId)
  }

  override def createPath(path: Path): Future[Either[DomainError, Path]] = {
    // to validate path
    pathRepository.createPath(path)
  }

}