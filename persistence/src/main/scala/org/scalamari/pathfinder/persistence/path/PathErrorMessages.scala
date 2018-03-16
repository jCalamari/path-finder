package org.scalamari.pathfinder.persistence.path

import org.scalamari.pathfinder.model.NodeId

private[path] object PathErrorMessages {

  val CreatePathErrorMessage = "Creating path failed."

  def findPathsErrorMessage(fromNodeId: NodeId, toNodeId: NodeId): String = {
    s"Finding paths from $fromNodeId to $toNodeId failed."
  }

}
