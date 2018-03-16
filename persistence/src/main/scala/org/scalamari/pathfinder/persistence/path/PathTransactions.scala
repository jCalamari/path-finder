package org.scalamari.pathfinder.persistence.path

private[path] object PathTransactions {

  def findPathsTransaction(collectionName: String): String =
    s"""
       |FOR target, unused, path IN @depth ANY @source $collectionName
       |  FILTER target.id == @target
       |  RETURN path
    """.stripMargin

  val createPathTransaction =
    s"""
       |function(edges) {
       |
       |  var arangodb = require("@arangodb");
       |  var db = arangodb.db;
       |  var _ = require("lodash");
       |
       |  var documents = _.map(edges, function(edge) {
       |    return {
       |      "_from": edge.fromVertexId,
       |      "_to": edge.toVertexId
       |    }
       |  });
       |
       |  for(doc in documents) {
       |    db.edges.insert(documents[doc]);
       |  }
       |
       |  return edges;
       |
       |}
     """.stripMargin

}
