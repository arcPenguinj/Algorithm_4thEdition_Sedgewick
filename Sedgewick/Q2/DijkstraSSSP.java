//reference:
// https://algs4.cs.princeton.edu/44sp/DijkstraSP.java.html
// https://github.com/reneargento/algorithms-sedgewick-wayne/blob/master/src/chapter4/section4/Exercise23_SourceSinkShortestPaths.java

package Q2;

//pg656 Source-sink shortest paths: Given an edge-weighted digraph, a source vertex s,
//and a target vertex t, find the shortest path from s to t.
//to solve this problem, use Dijkstra's algorithm, but terminate
//the search as soon as t comes off the priority queue

import BookCode.DirectedEdge;
import BookCode.EdgeWeightedDigraph;
import BookCode.IndexMinPQ;
import java.util.Stack;

public class DijkstraSSSP {
  private double[] distTo;          // distTo[v] = distance  of shortest s->v path
  private DirectedEdge[] edgeTo;    // edgeTo[v] = last edge on shortest s->v path
  private IndexMinPQ<Double> pq;    // priority queue of vertices
  private int sink;

  //constructor for te class
  //param: G the edge-weighted digraph
  //param  s the source vertex
  //param  sink the target vertex
  public DijkstraSSSP(EdgeWeightedDigraph G, int s, int sink) {
    for (DirectedEdge e : G.edges()) {
      if (e.weight() < 0) //weight of edge in G can't be negative, otherwise throw exception.
        throw new IllegalArgumentException("edge " + e + " has negative weight");
    }

    //initialize properties
    distTo = new double[G.V()];
    edgeTo = new DirectedEdge[G.V()];
    pq = new IndexMinPQ<>(G.V());
    this.sink = sink;

    //initialize source vertex as 0, all other routes with infinity
    for (int v = 0; v < G.V(); v++)
      distTo[v] = Double.POSITIVE_INFINITY;
    distTo[s] = 0.0;
    pq.insert(s, 0.0); //insert source vertex into pq

    while (!pq.isEmpty()) {
      int v = pq.delMin();
      if (v == this.sink)
        break; // terminate when target vertex comes off pq
      relax(G, v);
    }
  }

  private void relax(EdgeWeightedDigraph G, int v){
    for (DirectedEdge e: G.adj(v)) {
      int pointTo = e.to(); //assign the neighbor vertex of v to pointTo

      //update weight (dist[]) and edge if a new shortest path is found
      if (distTo[pointTo] > distTo[v] + e.weight()){
        distTo[pointTo] = distTo[v] + e.weight();
        edgeTo[pointTo] = e;

        //if weight and edge changed, update priority queue
        if (pq.contains(pointTo)) {
          pq.decreaseKey(pointTo, distTo[pointTo]); // update key, should be same if call pq.changeKey()
        } else {
          pq.insert(pointTo, distTo[pointTo]); //otherwise, add to pq for next iteration
        }
      }
    }
  }

  //helper functions, code from DijkstraSP.java
  //return the length of the shortest path from source vertex to target vertex
  public double distTo() {
    return distTo[this.sink];
  }

   //Returns true if there is a path from the source vertex target vertex
  public boolean hasPathTo() {
    return distTo[this.sink] < Double.POSITIVE_INFINITY;
  }

  //Returns a shortest path from the source vertex target vertex
  public Iterable<DirectedEdge> pathTo() {
    Stack<DirectedEdge> path = new Stack<DirectedEdge>();
    for (DirectedEdge e = edgeTo[this.sink]; e != null; e = edgeTo[e.from()]) {
      path.push(e);
    }
    return path;
  }

  //Throw an IllegalArgumentException unless {@code 0 <= v < V}
  private void validateVertex(int v) {
    int V = distTo.length;
    if (v < 0 || v >= V)
      throw new IllegalArgumentException("vertex " + v + " is not between 0 and " + (V-1));
  }
}
