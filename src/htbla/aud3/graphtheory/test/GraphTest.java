package htbla.aud3.graphtheory.test;

import htbla.aud3.graphtheory.Edge;
import htbla.aud3.graphtheory.Graph;
import htbla.aud3.graphtheory.Path;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.io.File;
import java.util.Arrays;
import java.util.List;

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
        Path path1 = graph.determineShortestPath(55,1);

        assertEquals(0, path1.computeDistance());
    }

    @org.junit.jupiter.api.Test
    void testDetermineShortestPathCOMPUTE_DISTANCE() {
        Graph graph = new Graph();
        graph.read(new File("Linz.csv"));
        Path path1 = graph.determineShortestPath(1,29);

        assertEquals(650, path1.computeDistance());

    }

    @org.junit.jupiter.api.Test
    void testDetermineShortestPathCOMPUTE_DISTANCE_2() {
        Graph graph = new Graph();
        graph.read(new File("Linz.csv"));
        Path path1 = graph.determineShortestPath(1,3);

        assertEquals(500+150, path1.computeDistance());

    }

    @org.junit.jupiter.api.Test
    void testDetermineShortestPathCOMPUTE_DISTANCE_3() {
        Graph graph = new Graph();
        graph.read(new File("Linz.csv"));
        Path path1 = graph.determineShortestPath(52,39);

        assertEquals(210+120, path1.computeDistance());

    }

    @org.junit.jupiter.api.Test
    void testDetermineShortestPathWITH_VIA() {
        Graph graph = new Graph();
        graph.read(new File("Linz.csv"));
        Path path1 = graph.determineShortestPath(1,2, 30);

        assertEquals(1130, path1.computeDistance());

    }

    @org.junit.jupiter.api.Test
    void testDetermineShortestPathNODE_NOT_EXISTING() {
        Graph graph = new Graph();
        graph.read(new File("Linz.csv"));
        Path path1 = graph.determineShortestPath(100,4753,187);

        assertEquals(0, path1.computeDistance());
    }

    @org.junit.jupiter.api.Test
    void determineMaximumFlow() {
        Graph graph = new Graph();
        graph.readFLow(new File("Linz.csv"),new File("Linz_Flussproblem.csv"));
        double maxFlow = graph.determineMaximumFlow(1,2);

        assertEquals(2000,maxFlow);
    }
    @org.junit.jupiter.api.Test
    void determineMaximumFlowNODE_NOT_EXISTING() {
        Graph graph = new Graph();
        graph.readFLow(new File("Linz.csv"),new File("Linz_Flussproblem.csv"));
        double maxFlow = graph.determineMaximumFlow(180,1000);

        assertEquals(0,maxFlow);
    }

    @org.junit.jupiter.api.Test
    void determineBottlenecks() {
        Graph graph = new Graph();
        graph.readFLow(new File("Linz.csv"),new File("Linz_Flussproblem.csv"));

        List<Edge> edges = graph.determineBottlenecks(1,3);

        assertEquals(1, edges.size());


    }

    @org.junit.jupiter.api.Test
    void a1_determineShortestPathBasic(){
        Graph graph = new Graph();
        //graph.readFLow(new File("Linz.csv"),new File("Linz_Flussproblem.csv"));
        graph.read(new File("Linz.csv"));

        Path p = graph.determineShortestPath(1,31);
        assertEquals(780,p.computeDistance(), 1.0);
        assertArrayEquals( new int[]{1, 2, 3, 4}, p.getNodeIds());
    }

    @org.junit.jupiter.api.Test
    void b1_determineMaximumFlowBasic(){
        Graph graph = new Graph();
        graph.readFLow(new File("Linz.csv"),new File("Linz_Flussproblem.csv"));

        double p = graph.determineMaximumFlow(1,2);

        assertEquals(2000, p);
    }

    @org.junit.jupiter.api.Test
    void c1_determineBottlenecksBase(){
        Graph graph = new Graph();
        graph.readFLow(new File("Linz.csv"),new File("Linz_Flussproblem.csv"));

        List<Edge> p = graph.determineBottlenecks(1,2);

        assertEquals(1, p.size());
    }
}