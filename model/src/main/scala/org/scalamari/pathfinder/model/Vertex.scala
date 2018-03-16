package org.scalamari.pathfinder.model

private[pathfinder] final case class VertexId(value: String)

private[pathfinder] final case class VertexName(value: String)

private[pathfinder] final case class Vertex(id: VertexId, name: VertexName)
