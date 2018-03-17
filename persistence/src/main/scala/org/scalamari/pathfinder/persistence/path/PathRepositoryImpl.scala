package org.scalamari.pathfinder.persistence.path

import com.arangodb.ArangoDatabase
import com.arangodb.model.TransactionOptions
import com.arangodb.velocypack.VPackSlice
import org.scalamari.pathfinder.domain.path.PathRepository
import org.scalamari.pathfinder.domain.{DomainError, UnknownDomainError}
import org.scalamari.pathfinder.model.{Edge, NodeId, Path}
import org.scalamari.pathfinder.persistence.Logging
import org.scalamari.pathfinder.persistence.path.PathErrorMessages._
import org.scalamari.pathfinder.persistence.path.PathTransactions._

import scala.collection.JavaConverters._
import scala.concurrent.{ExecutionContext, Future}
import scala.util.control.NonFatal

private[pathfinder] final class PathRepositoryImpl(database: ArangoDatabase)(implicit ec: ExecutionContext) extends PathRepository with Logging {

  override def createPath(path: Path): Future[Either[DomainError, Path]] =
    Future {
      val params: Map[String, AnyRef] = Map("path" -> path)
      Right {
        database.transaction(createPathTransaction, classOf[Path], new TransactionOptions().params(params).writeCollections("edges"))
      }
    } recover {
      case NonFatal(thrown) =>
        val message = CreatePathErrorMessage
        logger.error(message, thrown)
        Left(UnknownDomainError(message, Some(thrown)))
    }

  override def findPaths(fromNodeId: NodeId, toNodeId: NodeId, maxDepth: Int = 3): Future[Either[DomainError, Vector[Path]]] =
    Future {
      val params: Map[String, AnyRef] = Map(
        "source" -> fromNodeId.value,
        "target" -> toNodeId.value,
        "depth" -> s"$maxDepth"
      )
      Right {
        database.transaction(findPathsTransaction, classOf[VPackSlice], new TransactionOptions().params(params.asJava))
          .arrayIterator()
          .asScala
          .map(slice => database.util().deserialize(slice, classOf[Path]))
          .toVector
      }
    } recover {
      case NonFatal(thrown) =>
        val message = findPathsErrorMessage(fromNodeId, toNodeId)
        logger.error(message, thrown)
        Left(UnknownDomainError(message, Some(thrown)))
    }

}
