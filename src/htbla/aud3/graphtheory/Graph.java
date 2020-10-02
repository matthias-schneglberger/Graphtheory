package htbla.aud3.graphtheory;

import java.io.*;
import java.util.List;

/**
 * @author fhainzinger, bheissenberger, mschneglberger
 */
public class Graph {

    public static double[][] edgeArray;

    public void read(File adjacencyMatrix) {
        edgeArray = new double[50][50];
        int row = 0;
        if(adjacencyMatrix.exists()){
            try {
                BufferedReader br = new BufferedReader(new FileReader(adjacencyMatrix));
                String line = br.readLine();
                while(line!=null){
                    String[] parts = line.split(";");
                    for (int i = 0; i < 50; i++){
                        edgeArray[row][i] = Double.parseDouble(parts[i]);
                    }
                    line = br.readLine();
                    row++;
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }
    
    public Path determineShortestPath(int sourceNodeId, int targetNodeId) {
        return null;
    }
    
    public Path determineShortestPath(int sourceNodeId, int targetNodeId, int... viaNodeIds) {
        return null;
    }
    
    public double determineMaximumFlow(int sourceNodeId, int targetNodeId) {
        return -1.0;
    }
    
    public List<Edge> determineBottlenecks(int sourceNodeId, int targetNodeId) {
        return null;
    }

}
