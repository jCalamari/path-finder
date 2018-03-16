package org.scalamari.pathfinder.persistence.path

import org.scalamari.pathfinder.model.VertexId

private[path] object PathErrorMessages {

  val CreatePathErrorMessage = "Creating path failed."

  def findPathsErrorMessage(fromVertexId: VertexId, toVertexId: VertexId): String = {
    s"Finding paths from $fromVertexId to $toVertexId failed."
  }

}
