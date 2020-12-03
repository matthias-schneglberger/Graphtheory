package htbla.aud3.graphtheory.test;

import htbla.aud3.graphtheory.Graph;
import htbla.aud3.graphtheory.Path;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.io.File;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

class GraphTest {

    @org.junit.jupiter.api.AfterEach
    void tearDown() {
    }

    @org.junit.jupiter.api.Test
    void read() {
        boolean oneIsNotZero = false;
        Graph graph = new Graph();
        graph.read(new File("Suchproblem.csv"));
        double[][] edgeArray = graph.edgeArray;
        assertTrue(edgeArray.length > 0);
        assertTrue(Arrays.stream(edgeArray).count() > 0);
        Arrays.stream(edgeArray).forEach(n -> Arrays.stream(n).forEach(a -> {assertTrue(a >= 0);}));

        Graph graph2 = new Graph();
        graph2.read(new File("FLussproblem.csv"));
        double[][] edgeArray2 = graph2.edgeArray;
        assertTrue(edgeArray2.length > 0);
        assertTrue(Arrays.stream(edgeArray2).count() > 0);
        Arrays.stream(edgeArray2).forEach(n -> Arrays.stream(n).forEach(a -> assertTrue(a >= 0)));
    }


    @org.junit.jupiter.api.Test
    void testDetermineShortestPathNO_CONNECTION() {
        Graph graph = new Graph();
        graph.read(new File("Linz.csv"));
        Path path1 = graph.determineShortestPath(54,1);

        assertEquals(0, path1.computeDistance());
    }

    @org.junit.jupiter.api.Test
    void testDetermineShortestPathCOMPUTE_DISTANCE() {
        Graph graph = new Graph();
        graph.read(new File("Linz.csv"));
        Path path1 = graph.determineShortestPath(0,29);

        assertEquals(650+290, path1.computeDistance());

    }

    @org.junit.jupiter.api.Test
    void testDetermineShortestPathCOMPUTE_DISTANCE_2() {
        Graph graph = new Graph();
        graph.read(new File("Linz.csv"));
        Path path1 = graph.determineShortestPath(1,2);

        assertEquals(150, path1.computeDistance());

    }

    @org.junit.jupiter.api.Test
    void testDetermineShortestPathWITH_VIA() {
        Graph graph = new Graph();
        graph.read(new File("Linz.csv"));
        Path path1 = graph.determineShortestPath(0,2, 1);

        assertEquals(650, path1.computeDistance());

    }

    @org.junit.jupiter.api.Test
    void determineMaximumFlow() {
        throw new NotImplementedException();
    }

    @org.junit.jupiter.api.Test
    void determineBottlenecks() {
        throw new NotImplementedException();
    }
}