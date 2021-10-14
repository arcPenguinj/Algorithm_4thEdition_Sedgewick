//reference: book page 543 & 544

package Q1;

import BookCode.Edge;
import BookCode.EdgeWeightedGraph;

import java.util.ArrayList;

public class CC {
  private int count; // count of components
  private int[] id; // map from vertex -> component id
  boolean[] marked; // boolean map for vertex -> whehter visited before

  //initialize properties
  //go through all vertex by DFS to generate a map including id[] and marked[] to identify the
  //number of connected components
  public CC(EdgeWeightedGraph G) {
    this.count = 0;
    this.id = new int[G.V()];
    this.marked = new boolean[G.V()];

    //go through all the vertices in G, if vertex is not marked, then dfs() on such vertex
    for (int v = 0; v < G.V(); v++){
      if(!marked[v]){
        dfs(G,v);
        count++;
      }
    }
  }

  //input graph and a vertex, dfs on such vertex until all vertices connected to it are marked.
  private void dfs(EdgeWeightedGraph G, int v){
    marked[v] = true;
    //both count and id count from 0, since v is the first vertex visited, both id[v] and count are 0 firstly
    id[v] = count;

    for (Edge e: G.adj(v)){ //find first vertex on v's adjacency list,
      //if its not been visited yet, recursively call dfs() and mark such vertex as visited,
      //if it has been visited, continue, find next vertex on adjacency list
      //until all vertices connected to v are marked.
      int otherPoint = e.other(v); // get the other vertex from edge
      if(!marked[otherPoint]){
        dfs(G,otherPoint);
      }
    }
  }

  public boolean connected(int v, int w){
    return id[v] == id[w]; //if v and w has same id number, then these two vertices are connected
  }

  public int id(int v){
    return id[v];
  }

  public int count(){
    return count;
  }

  public int[] getVertexByComponents(int componentId) { // return vertex list by  componentId
    if (componentId >= count || componentId < 0) { // throw error if componentId is less than 0 or greater than count
      throw new IllegalArgumentException("Illegal components componentId passed: " + componentId);
    }
    ArrayList<Integer> vertexes = new ArrayList<>(); // a list to contain matching vertexes
    for (int i = 0; i < this.id.length; i++) {
      if (this.id[i] == componentId) vertexes.add(i); // go through id list and add matching vertexes to list
    }
    return vertexes.stream().mapToInt(i->i).toArray(); // convert to int array
  }
}
