package org.scalamari.pathfinder.persistence.vpack

import com.arangodb.velocypack._
import org.scalamari.pathfinder.model._

private[vpack] trait VPackInstances {

  object NodeDeserializer extends VPackDeserializer[Node] {
    override def deserialize(parent: VPackSlice, vpack: VPackSlice, context: VPackDeserializationContext): Node = {
      val name = vpack.getStringAs("name", NodeName)
      val id = vpack.getStringAs("_key", NodeId)
      val metadata = vpack.getAsMap("metadata", _.getAsString)
      Node(id, name, metadata)
    }
  }

  object NodeSerializer extends VPackSerializer[Node] {
    override def serialize(builder: VPackBuilder, attribute: String, node: Node, context: VPackSerializationContext): Unit = {
      builder.add(ValueType.OBJECT)
      builder.add("_key", node.id.value)
      builder.add("name", node.name.value)
      if (node.metadata.nonEmpty) {
        builder.add("metadata", ValueType.OBJECT)
        node.metadata.foreach { case (key, value) => builder.add(key, value) }
        builder.close()
      }
      builder.close()
    }
  }

  object EdgeDeserializer extends VPackDeserializer[Edge] {
    override def deserialize(parent: VPackSlice, vpack: VPackSlice, context: VPackDeserializationContext): Edge = {
      val fromNodeId = vpack.getStringAs("fromNodeId", NodeId)
      val toNodeId = vpack.getStringAs("toNodeId", NodeId)
      val metadata = vpack.getAsMap("metadata", _.getAsString)
      Edge(fromNodeId, toNodeId, metadata)
    }
  }

  object EdgeSerializer extends VPackSerializer[Edge] {
    override def serialize(builder: VPackBuilder, attribute: String, edge: Edge, context: VPackSerializationContext): Unit = {
      builder.add(attribute, ValueType.OBJECT)
      builder.add("fromNodeId", edge.fromNodeId.value)
      builder.add("toNodeId", edge.toNodeId.value)
      if (edge.metadata.nonEmpty) {
        builder.add("metadata", ValueType.OBJECT)
        edge.metadata.foreach { case (key, value) => builder.add(key, value) }
        builder.close()
      }
      builder.close()
    }
  }

  object PathDeserializer extends VPackDeserializer[Path] {
    override def deserialize(parent: VPackSlice, vpack: VPackSlice, context: VPackDeserializationContext): Path = {
      val edges = vpack.getAsVector("edges", v => EdgeDeserializer.deserialize(vpack, v, context))
      Path(edges)
    }
  }

  object PathSerializer extends VPackSerializer[Path] {
    override def serialize(builder: VPackBuilder, attribute: String, value: Path, context: VPackSerializationContext): Unit = {
      builder.add(attribute, ValueType.OBJECT)
      if (value.edges.nonEmpty) {
        builder.add("edges", ValueType.ARRAY)
        value.edges.foreach(edge => EdgeSerializer.serialize(builder, null, edge, context))
        builder.close()
      }
      builder.close()
    }
  }

}

private[vpack] object VPackInstances extends VPackInstances
