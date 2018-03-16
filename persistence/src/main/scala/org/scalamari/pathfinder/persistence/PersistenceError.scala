package org.scalamari.pathfinder.persistence

import org.scalamari.pathfinder.domain.DomainError

private[pathfinder] sealed trait PersistenceError extends DomainError
