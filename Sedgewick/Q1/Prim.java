//reference:
//https://algs4.cs.princeton.edu/43mst/PrimMST.java.html
//https://github.com/hasun/algorithms-sedgewick-wayne/blob/b64f45f54c8d09217805aa76eeffd1e5363535cc/src/chapter4/section3/Exercise22_MinimumSpanningForest.java

package Q1;

import BookCode.Edge;
import BookCode.EdgeWeightedGraph;
import BookCode.IndexMinPQ;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;

public class Prim {
  // modified from PrimMST.java in book p622
  private Edge[] edgeTo;        // edgeTo[v] = shortest edge from tree vertex to non-tree vertex
  private double[] distTo;      // distTo[v] = weight of shortest such edge
  private boolean[] marked;     // marked[v] = true if v on tree, false otherwise
  private IndexMinPQ<Double> pq; // eligible crossing edges
  private CC cc; // connected components of graph

  //constructor initialize properties
  public Prim(EdgeWeightedGraph G) {
    edgeTo = new Edge[G.V()]; // initialize edgeTo
    distTo = new double[G.V()]; // initialize distTo
    Arrays.fill(distTo, Double.POSITIVE_INFINITY); // set initial value to MAX DOUBLE
    marked = new boolean[G.V()]; // initialize marked
    pq = new IndexMinPQ<>(G.V()); // initialize pq
    cc = new CC(G); // compute CC of graph G

    for(int component = 0; component < cc.count(); component++) { // go through each components
      int[] vertexes = cc.getVertexByComponents(component); // get all vertexes by each component
      for(int i = 0; i < vertexes.length; i++) { // loop through the vertexes in same component
        int v = vertexes[i];
        if (!marked[v]) { // only do Prim's if the vertex hasn't been added to MST
          distTo[v] = 0.0; // initialize weight to 0
          pq.insert(v, 0.0); // initialize pq with vertex v
          while(!pq.isEmpty()) {
            visit(G, pq.delMin()); // go through all vertexes to find MST
          }
        }
      }
    }
  }

  private void visit(EdgeWeightedGraph G, int v) {
    marked[v] = true; // add vertex to MST

    for(Edge edge : G.adj(v)) { // go through all connected edges
      int w = edge.other(v); // get vertex from connected edge
      if (marked[w]) {
        continue; // v-w is ineligible
      }

      if (edge.weight() < distTo[w]) { // find smaller weight
        // Edge edge is the new best connection from the minimum spanning forest to otherVertex
        edgeTo[w] = edge; // update new shortest path edge
        distTo[w] = edge.weight(); // update new shortest path weight

        if (pq.contains(w)) {
          pq.changeKey(w, distTo[w]); // if w is already in pq, update its weight
        } else {
          pq.insert(w, distTo[w]); // otherwise, add to pq for next iteration
        }
      }
    }
  }

  public Iterable<Edge> edges() {
    //returns the edges in a minimum spanning forest as an iterable of edges
    Queue<Edge> minimumSpanningForest = new LinkedList<>();

    for(int vertex = 0; vertex < edgeTo.length; vertex++) { // loop through all vertexes in MST
      if (edgeTo[vertex] != null) {
        minimumSpanningForest.add(edgeTo[vertex]); // add edge
      }
    }

    return minimumSpanningForest; // return the edges
  }

  public double weight() {
    // lazily calculate total weight of MST
    double weight = 0;

    for(Edge edge : edges()) {
      // go through each edge in MST and add them together
      weight += edge.weight();
    }

    return weight;
  }
}
