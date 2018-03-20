package org.scalamari.pathfinder.persistence.vpack

import com.arangodb.velocypack.VPack
import org.scalamari.pathfinder.model._
import org.scalatest.concurrent.ScalaFutures
import org.scalatest.{Matchers, WordSpec}

import scala.reflect.ClassTag

class PathFinderModuleSpec extends WordSpec with Matchers with ScalaFutures {

  "PathFinderModule" should {

    "round trip a path with empty edges" in {
      roundTrip(Path(Vector.empty))
    }

    "round trip a non empty path" in {
      roundTrip(Path(Vector(Edge(NodeId("fromId"), NodeId("toId"), Map.empty))))
    }

    "round trip an edge" in {
      roundTrip(Edge(NodeId("fromId"), NodeId("toId"), Map("foo" -> "bar")))
    }

    "round trip a node" in {
      roundTrip(Node(NodeId("id"), NodeName("name"), Map("baz" -> "qux")))
    }

  }

  private def roundTrip[T](value: T)(implicit ct: ClassTag[T]): Unit = {
    val vpack = prepareVPack
    val serialized = vpack.serialize(value)
    val deserialized = vpack.deserialize[T](serialized, ct.runtimeClass.asInstanceOf[Class[T]])
    deserialized shouldBe value
  }

  private def prepareVPack: VPack = {
    new VPack.Builder()
      .registerModule(new PathFinderModule)
      .build()
  }

}
