package org.scalamari.pathfinder.persistence.path

private[path] object PathTransactions {

  // TODO make configurable
  private val traversalDepth = 2

  val findPathsTransaction: String =
    s"""
       |FOR v,e,p IN 1..$traversalDepth ANY @source GRAPH 'graph'
       |  FILTER v._id == @target
       |  RETURN p
    """.stripMargin

  val createPathTransaction: String =
    s"""
       |function(params) {
       |
       |  var arangodb = require("@arangodb");
       |  var db = arangodb.db;
       |  var _ = require("lodash");
       |  var path = params["path"] || {};
       |
       |  var documents = _.map(path.edges, function(edge) {
       |    return {
       |      "_from": "nodes/" + edge.fromNodeId,
       |      "_to": "nodes/" + edge.toNodeId,
       |      "fromNodeId": edge.fromNodeId,
       |      "toNodeId": edge.toNodeId,
       |      "metadata": edge.metadata
       |    }
       |  });
       |
       |  for(doc in documents) {
       |    db.edges.insert(documents[doc]);
       |  }
       |
       |  return path;
       |
       |}
     """.stripMargin

}
