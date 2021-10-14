package Q2;

import BookCode.DirectedEdge;
import BookCode.EdgeWeightedDigraph;

public class Main {

  public static void main(String[] args) {
    EdgeWeightedDigraph G = new EdgeWeightedDigraph(6);
    G.addEdge(new DirectedEdge(0, 1, 3.0));
    G.addEdge(new DirectedEdge(1, 3, 2.0));
    G.addEdge(new DirectedEdge(3, 5, 0.3));
    G.addEdge(new DirectedEdge(2, 4, 1.0));
    G.addEdge(new DirectedEdge(4, 5, 0.5));
    G.addEdge(new DirectedEdge(1, 4, 0.1));
    G.addEdge(new DirectedEdge(2, 3, 0.2));
    G.addEdge(new DirectedEdge(0, 2, 5.0));

    DijkstraSSSP sssp = new DijkstraSSSP(G, 0, 5);
    System.out.println("shortest path to vertex 5 is: " + sssp.distTo());
    System.out.println("expected answer: 3.6");
    System.out.println("shortest path is: ");
    for(DirectedEdge e : sssp.pathTo()) {
      System.out.println(e.toString());
    }

    DijkstraSSSP sssp1 = new DijkstraSSSP(G, 0, 3);
    System.out.println("shortest path to vertex 3 is: " + sssp1.distTo());
    System.out.println("expected answer: 5.0");
    System.out.println("shortest path is: ");
    for(DirectedEdge e : sssp1.pathTo()) {
      System.out.println(e.toString());
    }

    DijkstraSSSP sssp2 = new DijkstraSSSP(G, 0, 4);
    System.out.println("shortest path to vertex 4 is: " + sssp2.distTo());
    System.out.println("expected answer: 3.1");
    System.out.println("shortest path is: ");
    for(DirectedEdge e : sssp2.pathTo()) {
      System.out.println(e.toString());
    }
  }

}
