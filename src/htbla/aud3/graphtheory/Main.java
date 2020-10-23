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
    }
    
}
