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
    void testDetermineShortestPathNO_VIA() {
        Graph graph = new Graph();
        graph.read(new File("Suchproblem.csv"));
        int randomNum1 = (int)Math.random()*50;
        int randomNum2 = (int)Math.random()*50;
        Path path1 = graph.determineShortestPath(randomNum1,randomNum2);
        Path path2 = graph.determineShortestPath(randomNum1,randomNum2);

        if(path1 != null && path2 != null){
            assertTrue(path1.computeDistance() == path2.computeDistance());
        }
        //assertTrue(path1.equals(path2));
        throw new NotImplementedException();
    }

    @org.junit.jupiter.api.Test
    void testDetermineShortestPathNO_CONNECTION() {
        Graph graph = new Graph();
        graph.read(new File("Linz.csv"));
        Path path1 = graph.determineShortestPath(55,1);

        if(path1 != null){
            assertTrue(path1.computeDistance() == 0);
        }
        //assertTrue(path1.equals(path2));
        assertTrue(false);
    }

    @org.junit.jupiter.api.Test
    void testDetermineShortestPathCOMPUTE_DISTANCE() {
        Graph graph = new Graph();
        graph.read(new File("Linz.csv"));
        Path path1 = graph.determineShortestPath(0,51);

        if(path1 != null){
            assertTrue(path1.computeDistance() == 650);
        }
        //assertTrue(path1.equals(path2));
        assertTrue(false);
    }

    @org.junit.jupiter.api.Test
    void testDetermineShortestPathWITH_VIA() {
    }

    @org.junit.jupiter.api.Test
    void determineMaximumFlow() {
    }

    @org.junit.jupiter.api.Test
    void determineBottlenecks() {
    }
}