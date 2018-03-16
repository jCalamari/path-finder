package org.scalamari.pathfinder.domain

trait DomainError {

  def errorId: String

  def message: String

  def throwable: Option[Throwable]

}

final case class UnknownDomainError(message: String, throwable: Option[Throwable]) extends DomainError {
  override val errorId: String = "unknown-error"
}
