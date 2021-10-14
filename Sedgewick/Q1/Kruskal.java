package Q1;

import BookCode.Edge;
import BookCode.EdgeWeightedGraph;

import java.util.LinkedList;
import java.util.PriorityQueue;
import java.util.Queue;

public class Kruskal {
    // modified from book's code at p627
    private Queue<Edge> msf; // minimal spanning forest
    private EdgeWeightedGraph internalG; // a private graph used to keep track of MSF so far, and will be used to calculate
                                        // connected components (CC) to determine if edge is eligible for MSF or not

    public Kruskal(EdgeWeightedGraph g) {
        msf = new LinkedList<>(); // initialize msf
        internalG = new EdgeWeightedGraph(g.V());
        PriorityQueue<Edge> pq = new PriorityQueue<>(g.V()); // create a priority queue to be used to generate MSF
        for(Edge e: g.edges()) {
            pq.add(e); // add all edges to min pq
        }

        while (!pq.isEmpty() && msf.size() < g.V() - 1) { // keep finding MSF until pq is empty or find all edges
            Edge e = pq.remove(); // get min weight edge from pq
            int v = e.either(); // one vertex
            int w = e.other(v); // the other vertex
            CC cc = new CC(internalG); // calculate CC based on existing msf
            if (cc.connected(v, w)) { // if v and w is already connected in existing MSF, they are no longer eligible
                continue; // continue looping next edge
            }
            msf.add(e); // add current edge to msf
            internalG.addEdge(e); // add current edge to internal graph (for checking cc next iteration)
        }
    }

    public Iterable<Edge> edges() {
        return msf; // return msf which is contains the minimal spanning forest
    }

    public double weight() {
        // calculate weight of the MSF
        double weight = 0;

        for(Edge edge : edges()) { // loop through all edges in MSF, add their weights
            weight += edge.weight();
        }

        return weight; // return the total weigh
    }
}
