package htbla.aud3.graphtheory.test;

import htbla.aud3.graphtheory.Graph;

import java.io.File;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

class GraphTest {

    @org.junit.jupiter.api.AfterEach
    void tearDown() {
    }

    @org.junit.jupiter.api.Test
    void read() {
        Graph graph = new Graph();
        graph.read(new File("Suchproblem.csv"));
        double[][] edgeArray = graph.edgeArray;
        assertTrue(edgeArray.length > 0);
        assertTrue(Arrays.stream(edgeArray).count() > 0);
        Arrays.stream(edgeArray).forEach(n -> Arrays.stream(n).forEach(a -> assertTrue(a >= 0)));

        Graph graph2 = new Graph();
        graph2.read(new File("FLussproblem.csv"));
        double[][] edgeArray2 = graph2.edgeArray;
        assertTrue(edgeArray2.length > 0);
        assertTrue(Arrays.stream(edgeArray2).count() > 0);
        Arrays.stream(edgeArray2).forEach(n -> Arrays.stream(n).forEach(a -> assertTrue(a >= 0)));
    }

    @org.junit.jupiter.api.Test
    void determineShortestPath() {
    }

    @org.junit.jupiter.api.Test
    void testDetermineShortestPath() {
    }

    @org.junit.jupiter.api.Test
    void determineMaximumFlow() {
    }

    @org.junit.jupiter.api.Test
    void determineBottlenecks() {
    }
}