package org.scalamari.pathfinder.domain.path

import org.scalamari.pathfinder.domain.DomainError
import org.scalamari.pathfinder.model.{Path, VertexId}

import scala.concurrent.Future

private[pathfinder] trait PathRepository {

  def createPath(path: Path): Future[Either[DomainError, Path]]

  def findPaths(fromVertexId: VertexId, toVertexId: VertexId, maxDepth: Int = 3): Future[Either[DomainError, Vector[Path]]]

}
