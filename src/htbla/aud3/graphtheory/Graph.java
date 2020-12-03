package htbla.aud3.graphtheory;

import java.io.*;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author fhainzinger, bheissenberger, mschneglberger
 */
public class Graph {

    public static double[][] edgeArray;
    public static List<Edge> edges;
    public static HashMap<Integer, Path> paths;
    private int maxNode = Integer.MAX_VALUE;
    private Path tmpOldPath = new Path();

    public void read(File adjacencyMatrix) {
//        edgeArray = new double[50][50];
        int row = 0;
        if(adjacencyMatrix.exists()){
            try {

                BufferedReader br2 = new BufferedReader(new FileReader(adjacencyMatrix));
                maxNode = Integer.parseInt(String.valueOf(br2.lines().count()));
                edgeArray = new double[maxNode][maxNode];
                br2.close();
                BufferedReader br = new BufferedReader(new FileReader(adjacencyMatrix));

                String line = br.readLine();
                while(line!=null){
                    String[] parts = line.split(";");
                    for (int i = 0; i < maxNode; i++){
                        edgeArray[row][i] = Double.parseDouble(parts[i]);
                    }
                    line = br.readLine();
                    row++;
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
        readInEdges();
    }
    
    public Path determineShortestPath(int sourceNodeId, int targetNodeId) {

        if(edgeArray == null)
            return null;

        paths = new HashMap<>();

        dijkstraShortestPath_NEW(edges.stream().filter(edge -> edge.getFromNodeId() == sourceNodeId).collect(Collectors.toList()), new Path());

        System.out.println(paths.get(targetNodeId));

        return paths.get(targetNodeId);

    }

    private List<Path> dijkstraShortestPath(List<Edge> edgeFromThisPath){
        for (Edge e :
                edgeFromThisPath) {
            if(!paths.containsKey(e.getToNodeId())){ // weg zu Ziel node gibts noch nicht
                Path tmp;
                if(paths.containsKey(e.getFromNodeId())){ //gibt schon einen weg zur FromNode
                    tmp = paths.get(e.getFromNodeId()).clone();
                }
                else{
                    tmp = new Path();
                }
                tmp.edgeList.add(e);
                paths.put(e.getToNodeId(), tmp);
            }
            else{
                Path tmp = paths.get(e.getFromNodeId()).clone();
                tmp.edgeList.add(e);

                if(paths.get(e.getToNodeId()).computeDistance() > tmp.computeDistance()){ //dieser weg ist kürzer
                    paths.remove(e.getToNodeId());
                    paths.put(e.getToNodeId(), tmp);
                }
            }
            edges.remove(e);


            dijkstraShortestPath(edges.stream().filter(edge -> edge.getFromNodeId() == e.getToNodeId() && edge.getToNodeId() != e.getFromNodeId()).collect(Collectors.toList()));
        }
        return null;
    }

    private List<Path> dijkstraShortestPath_NEW(List<Edge> edgeFromThisPath, Path currentPath){

        for (Edge e :
                edgeFromThisPath) {
            Path curP = currentPath.clone();
            curP.edgeList.add(e);

            if(!paths.containsKey(e.getToNodeId())) {//Ziel noch nicht in Paths
                paths.put(e.getToNodeId(), curP.clone());
            }
            else{
                Path oldP = paths.get(e.getToNodeId());
                if(oldP.computeDistance() > curP.computeDistance()){
                    paths.remove(oldP.getEndID());
                    paths.put(e.getToNodeId(), curP.clone());

                    if(curP.getEndID() == 28){
                        System.out.println(paths.get(28).computeDistance());
                    }
                }
            }




            if(curP.edgeList.size() < maxNode)
                dijkstraShortestPath_NEW(edges.stream().filter(edge -> e.getToNodeId() == edge.getFromNodeId() && !curP.edgeList.contains(edge) && edge.getToNodeId() != e.getFromNodeId()).collect(Collectors.toList()), curP.clone());
        }

        return null;
    }

    public void readInEdges(){
        edges = new ArrayList<Edge>();

        for(int i = 0; i < edgeArray.length; i++){
            double[] tmpArr = edgeArray[i];

            for (int i1 = 0; i1 < tmpArr.length; i1++){
                if(tmpArr[i1] != 0.0)
                    edges.add(new Edge(i, i1, edgeArray[i][i1]));
            }
        }
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
