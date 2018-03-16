package org.scalamari.pathfinder.model

private[pathfinder] final case class NodeId(value: String) {
  override def toString: String = value
}

private[pathfinder] final case class NodeName(value: String) {
  override def toString: String = value
}

private[pathfinder] final case class Node(id: NodeId, name: NodeName, metadata: Map[String, String])
