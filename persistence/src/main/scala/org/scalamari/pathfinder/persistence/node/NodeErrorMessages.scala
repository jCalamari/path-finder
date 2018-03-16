package org.scalamari.pathfinder.persistence.node

import org.scalamari.pathfinder.model.NodeId

private[node] object NodeErrorMessages {

  def createNodeErrorMessage(id: NodeId): String = s"Creating node with id [$id] failed."

  def getNodeErrorMessage(id: NodeId): String = s"Getting node with id [$id] failed."

}
