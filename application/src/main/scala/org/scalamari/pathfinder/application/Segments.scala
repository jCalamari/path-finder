package org.scalamari.pathfinder.application

import akka.http.scaladsl.model.Uri
import akka.http.scaladsl.model.Uri.Path
import akka.http.scaladsl.server.PathMatcher.{Matched, Unmatched}
import akka.http.scaladsl.server.{PathMatcher, PathMatcher1}
import org.scalamari.pathfinder.model.NodeId

private[application] object Segments {

  object IdSegment extends PathMatcher1[NodeId] {

    override def apply(path: Uri.Path): PathMatcher.Matching[Tuple1[NodeId]] = path match {
      case Path.Segment(segment, tail) if segment.nonEmpty => Matched(tail, Tuple1(NodeId(segment)))
      case _ => Unmatched
    }
  }

}
