package org.scalamari.pathfinder.persistence.path

private[path] object PathTransactions {

  val findPathsTransaction: String =
    s"""
       |FOR v,e,p IN 1..@depth ANY 'nodes/'@source GRAPH 'graph'
       |  FILTER v.id == @target
       |  RETURN path
    """.stripMargin

  val createPathTransaction: String =
    s"""
       |function(path) {
       |
       |  var arangodb = require("@arangodb");
       |  var db = arangodb.db;
       |  var _ = require("lodash");
       |
       |  var documents = _.map(path.edges, function(edge) {
       |    return {
       |      "_from": "nodes/" + edge.fromNodeId,
       |      "_to": "nodes/" + edge.toNodeId,
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
