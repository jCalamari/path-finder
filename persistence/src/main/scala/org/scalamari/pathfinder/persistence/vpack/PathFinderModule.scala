package org.scalamari.pathfinder.persistence.vpack

import com.arangodb.velocypack._
import org.scalamari.pathfinder.model.{Edge, Node, Path}

private[pathfinder] final class PathFinderModule extends VPackModule {

  override def setup[C <: VPackSetupContext[C]](context: C): Unit = {
    context.registerDeserializer(classOf[Node], NodeDeserializer)
    context.registerSerializer(classOf[Node], NodeSerializer)

    context.registerDeserializer(classOf[Edge], EdgeDeserializer)
    context.registerSerializer(classOf[Edge], EdgeSerializer)

    context.registerDeserializer(classOf[Path], PathDeserializer)
    context.registerSerializer(classOf[Path], PathSerializer)
  }

}
