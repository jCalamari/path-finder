package org.scalamari.pathfinder.persistence

import com.arangodb.velocypack.VPackSlice

import scala.collection.JavaConverters._

package object vpack extends VPackInstances {

  private[pathfinder] implicit class RichVPackSlice(value: VPackSlice) {

    def getStringAs[T](key: String, f: String => T): T = {
      Option(value.get(key))
        .map(_.getAsString)
        .map(f)
        .getOrElse(throw new IllegalArgumentException(s"$key not found in [$value]"))
    }

    def getAsMap[T](key: String, v: VPackSlice => T): Map[String, T] = {
      Option(value.get(key))
        .map(_.objectIterator)
        .map(_.asScala)
        .getOrElse(Iterator.empty)
        .map(entry => entry.getKey -> entry.getValue)
        .toMap
        .mapValues(v)
    }

    def getAsVector[T](key: String, v: VPackSlice => T): Vector[T] = {
      Option(value.get(key))
        .map(_.arrayIterator())
        .map(_.asScala)
        .getOrElse(Iterator.empty)
        .map(v)
        .toVector
    }

  }

}
