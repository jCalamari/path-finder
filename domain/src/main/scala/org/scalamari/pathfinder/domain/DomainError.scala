package org.scalamari.pathfinder.domain

import org.scalamari.pathfinder.model.NodeId

trait DomainError {

  def errorId: String

  def message: String

  def throwable: Option[Throwable]

}

final case class NodeNotFound(id: NodeId) extends DomainError {
  override val errorId: String = "node-not-found"

  override val message: String = s"Node not found: [$id]"

  override val throwable: Option[Throwable] = None
}

final case class UnknownDomainError(message: String, throwable: Option[Throwable]) extends DomainError {
  override val errorId: String = "unknown-error"
}

object UnknownDomainError {

  def apply(thrown: Throwable): DomainError = UnknownDomainError(thrown.getMessage, Some(thrown))

}
