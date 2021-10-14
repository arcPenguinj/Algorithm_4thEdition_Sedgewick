package Q1;

import BookCode.Edge;
import BookCode.EdgeWeightedGraph;

import java.util.Arrays;

public class Main {
    public static void main(String[] args) {
        System.out.println("===Test Connected Component===");
        EdgeWeightedGraph g1 = new EdgeWeightedGraph(5);
        // Component 1
        g1.addEdge(new Edge(0, 1, 1.1));
        g1.addEdge(new Edge(1, 2, 2.2));
        g1.addEdge(new Edge(2, 0, 0.2));
        // Component 2
        g1.addEdge(new Edge(3, 4, 0.1));
        g1.addEdge(new Edge(4, 4, 4.1));
        CC cc = new CC(g1);
        System.out.println("G1 has components: " + cc.count());
        for (int i = 0; i < 5; i++) {
            System.out.println("G1 vertex " + i + " belongs to component: " + cc.id(i));
        }
        System.out.println("G1 component 1: " + Arrays.toString(cc.getVertexByComponents(0)));
        System.out.println("G1 component 1: " + Arrays.toString(cc.getVertexByComponents(1)));

        System.out.println("===Test Prim MST===");
        EdgeWeightedGraph g2 = new EdgeWeightedGraph(15);
        // Component 1
        g2.addEdge(new Edge(0, 1, 0.2));
        g2.addEdge(new Edge(1, 2, 0.3));
        g2.addEdge(new Edge(0, 3, 0.15));
        // Component 2
        g2.addEdge(new Edge(5, 7, 0.2));
        g2.addEdge(new Edge(6, 7, 0.5));
        g2.addEdge(new Edge(4, 6, 0.4));
        g2.addEdge(new Edge(8, 7, 0.9));
        g2.addEdge(new Edge(8, 4, 0.77));
        g2.addEdge(new Edge(7, 10, 0.2));
        g2.addEdge(new Edge(5, 10, 0.4));
        g2.addEdge(new Edge(9, 10, 1.0));
        // Component 3
        g2.addEdge(new Edge(11, 12, 1.2));
        g2.addEdge(new Edge(12, 13, 2.2));
        g2.addEdge(new Edge(12, 14, 3.2));
        g2.addEdge(new Edge(11, 14, 0.2));

        System.out.println("Prim MSF: ");
        Prim p = new Prim(g2);
        for (Edge e: p.edges()) {
            System.out.println(e);
        }
        System.out.println("Weight of Prim MSF: " + p.weight());

        System.out.println("Kruskal MSF: ");
        Kruskal k = new Kruskal(g2);
        for (Edge e: k.edges()) {
            System.out.println(e);
        }
        System.out.println("Weight of Kruskal MSF: " + k.weight());
    }
}
