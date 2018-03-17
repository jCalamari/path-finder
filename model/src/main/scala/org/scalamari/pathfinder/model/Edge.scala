package org.scalamari.pathfinder.model

private[pathfinder] final case class Edge(fromNodeId: NodeId, toNodeId: NodeId, metadata: Map[String, String])
