package org.scalamari.pathfinder.domain.path

import org.scalamari.pathfinder.domain.DomainError
import org.scalamari.pathfinder.model.{Path, VertexId}

import scala.concurrent.Future

private[domain] final class PathServiceImpl(pathRepository: PathRepository) extends PathService {

  override def findPaths(fromVertexId: VertexId, toVertexId: VertexId): Future[Either[DomainError, Vector[Path]]] = {
    pathRepository.findPaths(fromVertexId, toVertexId)
  }

  override def createPath(path: Path): Future[Either[DomainError, Path]] = {
    pathRepository.createPath(path)
  }

}