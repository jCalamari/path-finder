package org.scalamari.pathfinder.application

import org.scalamari.pathfinder.domain.DomainError
import org.scalamari.pathfinder.model._
import spray.json._

private[application] trait JsonProtocol extends DefaultJsonProtocol {

  implicit val nodeIdFormat = new FromStringFormat[NodeId](NodeId, _.value)

  implicit val nodeNameFormat = new FromStringFormat[NodeName](NodeName, _.value)

  private[application] class FromStringFormat[T](r: String => T, w: T => String) extends JsonFormat[T] {

    override def read(json: JsValue): T = r(json.convertTo[String])

    override def write(obj: T): JsValue = JsString(w(obj))
  }

  implicit val edgeFormat = jsonFormat2(Edge)

  implicit val pathFormat = jsonFormat1(Path)

  implicit val errorFormat = new RootJsonFormat[DomainError] {

    override def write(obj: DomainError): JsValue = JsObject(
      "errorId" -> obj.errorId.toJson,
      "message" -> obj.message.toJson
    )

    override def read(json: JsValue): DomainError = deserializationError("ErrorFormat: not supported")

  }

  implicit val nodeFormat = jsonFormat3(Node)

}

private[application] object JsonProtocol extends JsonProtocol
