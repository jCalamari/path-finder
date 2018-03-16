package org.scalamari.pathfinder.application

import org.scalamari.pathfinder.domain.{DomainError, UnknownDomainError}
import org.scalamari.pathfinder.model.{Edge, Path, VertexId}
import spray.json._

private[application] trait JsonProtocol extends DefaultJsonProtocol {

  implicit val vertexIdFormat = new JsonFormat[VertexId] {

    override def write(obj: VertexId): JsValue = JsString(obj.value)

    override def read(json: JsValue): VertexId = VertexId(json.convertTo[String])
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

}

private[application] object JsonProtocol extends JsonProtocol
