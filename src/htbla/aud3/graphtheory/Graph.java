package htbla.aud3.graphtheory;

import javax.crypto.spec.PSource;
import java.io.*;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author fhainzinger, bheissenberger, mschneglberger
 */
public class Graph {

    public static double[][] edgeArray;
    public static int[][] flowArray;
    public static List<Edge> edges;
    public static HashMap<Integer, Path> paths;
    private int maxNode = Integer.MAX_VALUE;
    private Path tmpOldPath = new Path();
    private List<Integer> pathsTodoInThisRun = new ArrayList<>();
    private List<Integer> pathsTodoInNextRun = new ArrayList<>();
    private List<Integer> pathsToTryAgain = new ArrayList<>();
    private List<Integer> alreadySeen = new ArrayList<>();

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
        readInEdges(true);
    }

    public void readFLow(File adjacencyMatrix, File flowMatrix) {
        //File adjancency
        {
            int row = 0;
            if (adjacencyMatrix.exists()) {
                try {

                    BufferedReader br2 = new BufferedReader(new FileReader(adjacencyMatrix));
                    maxNode = Integer.parseInt(String.valueOf(br2.lines().count()));
                    edgeArray = new double[maxNode][maxNode];
                    br2.close();
                    BufferedReader br = new BufferedReader(new FileReader(adjacencyMatrix));

                    String line = br.readLine();
                    while (line != null) {
                        String[] parts = line.split(";");
                        for (int i = 0; i < maxNode; i++) {
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


        //File FLOW
        {
            int row = 0;
            if (flowMatrix.exists()) {
                try {

                    BufferedReader br2 = new BufferedReader(new FileReader(flowMatrix));
                    flowArray = new int[maxNode][maxNode];
                    br2.close();
                    BufferedReader br = new BufferedReader(new FileReader(flowMatrix));

                    String line = br.readLine();
                    while (line != null) {
                        String[] parts = line.split(";");
                        for (int i = 0; i < maxNode; i++) {
                            flowArray[row][i] = Integer.parseInt(parts[i]);
                        }
                        line = br.readLine();
                        row++;
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        }




        readInEdges();

    }
    
    public Path determineShortestPath(int sourceNodeId, int targetNodeId) {

        if(sourceNodeId == targetNodeId){
            ArrayList<Edge> tmpEdgeList = new ArrayList<>();
            tmpEdgeList.add(new Edge(sourceNodeId, targetNodeId, 0,0));
            return new Path((tmpEdgeList));
        }


        paths = new HashMap<>();
        paths.put(sourceNodeId, new Path());
        alreadySeen = new ArrayList<>();
        pathsTodoInThisRun = new ArrayList<>();
        pathsTodoInNextRun = new ArrayList<>();


        pathsTodoInThisRun.add(sourceNodeId);

        int lastVisited = -1;
        for (int i = 0; i< maxNode; i++){
            for(int p : pathsTodoInThisRun){
                dijkstraShortestPath(p,lastVisited);
                lastVisited = p;
            }


            alreadySeen.addAll(pathsTodoInThisRun);

            List<Integer> newPathsTodoInNextRun = new ArrayList<>();
            newPathsTodoInNextRun.addAll(pathsTodoInNextRun);


            for(int k : pathsTodoInNextRun){
                if(alreadySeen.contains(k)){
                    newPathsTodoInNextRun.remove((Integer) k);
                }
            }
            pathsTodoInNextRun = new ArrayList<>();
            pathsTodoInNextRun.addAll(newPathsTodoInNextRun);

            pathsTodoInThisRun = pathsTodoInNextRun;
            pathsTodoInNextRun = new ArrayList<>();
            pathsToTryAgain = new ArrayList<>();

        }


        if(paths.containsKey(targetNodeId))
            return paths.get(targetNodeId);
        return null;

    }

//    private List<Path> dijkstraShortestPath(List<Edge> edgeFromThisPath){
//        for (Edge e :
//                edgeFromThisPath) {
//            if(!paths.containsKey(e.getToNodeId())){ // weg zu Ziel node gibts noch nicht
//                Path tmp;
//                if(paths.containsKey(e.getFromNodeId())){ //gibt schon einen weg zur FromNode
//                    tmp = paths.get(e.getFromNodeId()).clone();
//                }
//                else{
//                    tmp = new Path();
//                }
//                tmp.edgeList.add(e);
//                paths.put(e.getToNodeId(), tmp);
//            }
//            else{
//                Path tmp = paths.get(e.getFromNodeId()).clone();
//                tmp.edgeList.add(e);
//
//                if(paths.get(e.getToNodeId()).computeDistance() > tmp.computeDistance()){ //dieser weg ist kürzer
//                    paths.remove(e.getToNodeId());
//                    paths.put(e.getToNodeId(), tmp);
//                }
//            }
//            edges.remove(e);
//
//
//            dijkstraShortestPath(edges.stream().filter(edge -> edge.getFromNodeId() == e.getToNodeId() && edge.getToNodeId() != e.getFromNodeId()).collect(Collectors.toList()));
//        }
//        return null;
//    }
//
//    private List<Path> dijkstraShortestPath_NEW(List<Edge> edgeFromThisPath, Path currentPath){
//
//        for (Edge e :
//                edgeFromThisPath) {
//            Path curP = currentPath.clone();
//            curP.edgeList.add(e);
//
//            if(!paths.containsKey(e.getToNodeId())) {//Ziel noch nicht in Paths
//                paths.put(e.getToNodeId(), curP.clone());
//            }
//            else{
//                Path oldP = paths.get(e.getToNodeId());
//                if(oldP.computeDistance() > curP.computeDistance()){
//                    paths.remove(oldP.getEndID());
//                    paths.put(e.getToNodeId(), curP.clone());
//
//                    if(curP.getEndID() == 28){
//                        System.out.println(paths.get(28).computeDistance());
//                    }
//                }
//            }
//
//
//
//
//            if(curP.edgeList.size() < maxNode)
//                dijkstraShortestPath_NEW(edges.stream().filter(edge -> e.getToNodeId() == edge.getFromNodeId() && !curP.edgeList.contains(edge) && edge.getToNodeId() != e.getFromNodeId()).collect(Collectors.toList()), curP.clone());
//        }
//
//
//
//        return null;
//    }


    private void dijkstraShortestPath(int nodeIdFrom, int lastVisited){
        List<Edge> edgesFromThisNodeId = edges.stream().filter(x -> x.getFromNodeId() == nodeIdFrom/* && !alreadySeen.contains(nodeIdFrom)*/).collect(Collectors.toList());

//        for(Edge ed : edgesFromThisNodeId){
//            if(ed.getToNodeId() == lastVisited){
//                edgesFromThisNodeId.remove(ed);
//                break;
//            }
//        }

        List<Edge> tmpEd = new ArrayList<>();
        tmpEd.addAll(edgesFromThisNodeId);

        for(Edge e2 : edgesFromThisNodeId){
            if(alreadySeen.contains(e2.getToNodeId()) && !pathsToTryAgain.contains(e2.getToNodeId()))
                tmpEd.remove(e2);
        }
        edgesFromThisNodeId = tmpEd;

        for (Edge edge :
                edgesFromThisNodeId) {
            if(paths.containsValue(edge.getToNodeId())){ //Node wurde schon abgefahren
                Path newPath = paths.get(edge.getFromNodeId()).clone();
                newPath.edgeList.add(edge);
                if(newPath.computeDistance() < paths.get(edge.getToNodeId()).computeDistance()){
                    paths.remove(paths.get(edge.getToNodeId()));
                    paths.put(newPath.getEndID(), newPath.clone());
                    pathsTodoInNextRun.add(edge.getToNodeId());
                    pathsToTryAgain.add(edge.getToNodeId());
                }
            }
            else{//Neue Node gesichtet
                Path newPath = paths.get(edge.getFromNodeId()).clone();
                newPath.edgeList.add(edge);
                paths.put(edge.getToNodeId(), newPath.clone());

                if(!pathsTodoInNextRun.contains(edge.getToNodeId()))
                    pathsTodoInNextRun.add(edge.getToNodeId());
            }
        }
    }

    public void readInEdges(){
        edges = new ArrayList<Edge>();

        for(int i = 0; i < edgeArray.length; i++){
            double[] tmpArr = edgeArray[i];

            for (int i1 = 0; i1 < tmpArr.length; i1++){
                if(tmpArr[i1] != 0.0)
                    edges.add(new Edge(i+1, i1+1, edgeArray[i][i1], flowArray[i][i1]));
            }
        }
    }

    public void readInEdges(boolean tmp){
        edges = new ArrayList<Edge>();

        for(int i = 0; i < edgeArray.length; i++){
            double[] tmpArr = edgeArray[i];

            for (int i1 = 0; i1 < tmpArr.length; i1++){
                if(tmpArr[i1] != 0.0)
                    edges.add(new Edge(i+1, i1+1, edgeArray[i][i1], 0));
            }
        }
    }
    
    public Path determineShortestPath(int sourceNodeId, int targetNodeId, int... viaNodeIds) {



        List<Integer> nodeIdToDrive = new ArrayList<>();
        nodeIdToDrive.add(sourceNodeId);

        List<Integer> tmpList = new ArrayList<>();
        Arrays.stream(viaNodeIds).forEach(x -> {
            tmpList.add(x);
        });

        nodeIdToDrive.addAll(tmpList);
        nodeIdToDrive.add(targetNodeId);

        Path fullPath = new Path();

        for(int i = 1; i < nodeIdToDrive.size(); i++){
            Path tmpP = determineShortestPath(nodeIdToDrive.get(i-1), nodeIdToDrive.get(i));
            fullPath.edgeList.addAll(tmpP.edgeList);
        }

        return fullPath;
    }
    
    public double determineMaximumFlow(int sourceNodeId, int targetNodeId) {
        Path p = determineShortestPath(sourceNodeId, targetNodeId);
        return p.edgeList.stream().max((a,b) -> a.getFlow() - b.getFlow()).orElseGet(() -> new Edge(sourceNodeId,targetNodeId,0,0)).getFlow();
    }
    
    public List<Edge> determineBottlenecks(int sourceNodeId, int targetNodeId) {
        Path p = determineShortestPath(sourceNodeId, targetNodeId);

        List<Edge> bottlenecks = new ArrayList<>();

        double max = determineMaximumFlow(sourceNodeId, targetNodeId);

        for(Edge e : p.edgeList){
            if(e.getFlow() < max)
                bottlenecks.add(e);
        }

        if(bottlenecks.size() <= 0)
            bottlenecks = p.edgeList;

        return bottlenecks;
    }

}
