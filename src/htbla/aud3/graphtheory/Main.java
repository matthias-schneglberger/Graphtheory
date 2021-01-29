package htbla.aud3.graphtheory;

import java.io.File;

/**
 * @author Torsten Welsch
 */
public class Main {
    
    public static void main(String[] args) {
        System.out.println("Graphtheory _ htbla.aud3.graphtheory.test");
        Graph graph = new Graph();
        graph.read(new File("Linz.csv"));
        Path p = graph.determineShortestPath(1, 31);
        System.out.println(p.computeDistance());
        Path p1 = graph.determineShortestPath(1, 31);
        System.out.println(p1.computeDistance());
    }
}
