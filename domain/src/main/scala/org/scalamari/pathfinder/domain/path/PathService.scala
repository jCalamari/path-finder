package org.scalamari.pathfinder.domain.path

import org.scalamari.pathfinder.domain.DomainError
import org.scalamari.pathfinder.model.{Path, VertexId}

import scala.concurrent.Future

private[pathfinder] trait PathService {

  def createPath(path: Path): Future[Either[DomainError, Path]]

  def findPaths(fromVertexId: VertexId, toVertexId: VertexId): Future[Either[DomainError, Vector[Path]]]

}

private[pathfinder] object PathService {

  def apply(repository: PathRepository): PathService = new PathServiceImpl(repository)

}