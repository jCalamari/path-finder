package org.scalamari.pathfinder.application

import akka.http.scaladsl.model.Uri
import akka.http.scaladsl.model.Uri.Path
import akka.http.scaladsl.server.PathMatcher.{Matched, Unmatched}
import akka.http.scaladsl.server.{PathMatcher, PathMatcher1}
import org.scalamari.pathfinder.model.VertexId

private[application] object Segments {

  object IdSegment extends PathMatcher1[VertexId] {

    override def apply(path: Uri.Path): PathMatcher.Matching[Tuple1[VertexId]] = path match {
      case Path.Segment(segment, tail) if segment.nonEmpty => Matched(tail, Tuple1(VertexId(segment)))
      case _ => Unmatched
    }
  }

}
