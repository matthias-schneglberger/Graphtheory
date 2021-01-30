package htbla.aud3.graphtheory;

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

//        if(adjacencyMatrix.getName().equals("Linz.csv") || adjacencyMatrix.getName().equals("Linz_Suchproblem.csv")){
//            readFLow();
//        }

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
            tmpEdgeList.add(new Edge(sourceNodeId, targetNodeId, -1,0));
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



    private void dijkstraShortestPath(int nodeIdFrom, int lastVisited){
        List<Edge> edgesFromThisNodeId = edges.stream().filter(x -> x.getFromNodeId() == nodeIdFrom/* && !alreadySeen.contains(nodeIdFrom)*/).collect(Collectors.toList());

        List<Edge> tmpEd = new ArrayList<>();
        tmpEd.addAll(edgesFromThisNodeId);
//
//        for(Edge e2 : edgesFromThisNodeId){
//            if(e2.getValue() == Integer.MAX_VALUE)
//                tmpEd.remove(e2);
//        }
        edgesFromThisNodeId = tmpEd;

        for (Edge edge :
                edgesFromThisNodeId) {
//            if(edge.getValue() == Integer.MAX_VALUE)
//                continue;

            if(paths.containsKey(edge.getToNodeId())){ //Node wurde schon abgefahren
                Path newPath = paths.get(edge.getFromNodeId()).clone();
                newPath.edgeList.add(edge);
                if(newPath.computeDistance() < paths.get(edge.getToNodeId()).computeDistance()){
                    paths.remove(edge.getToNodeId());
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

    public void readInEdges(boolean useFlow){
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

    public List<Path> getAllPossiblePaths(int sourceNodeId, int targetNodeId){
        edges.clear();
        readInEdges(true);
        List<Path> allPossiblePaths = new ArrayList<>();
        boolean alreadyFoundOne = false;

        while(true){
            Path tmp = determineShortestPath(sourceNodeId, targetNodeId);

            //Kein Pfad zur TargetId
            if(tmp==null && !alreadyFoundOne) return null;
            if(tmp==null) return allPossiblePaths;

            alreadyFoundOne = true;

            if(getMaximum(tmp) == Integer.MAX_VALUE)
                break;

            allPossiblePaths.add(tmp.clone());

            double min = getMinimun(tmp);
            for (Edge eTmp : tmp.edgeList) {

                edges.stream()
                        .filter(x -> x.getFromNodeId() == eTmp.getFromNodeId() && x.getToNodeId() == eTmp.getToNodeId())
                        .findFirst()
                        .get()
                        .decreaseValue(min);

                Edge e = edges.stream()
                        .filter(x -> x.getFromNodeId() == eTmp.getFromNodeId() && x.getToNodeId() == eTmp.getToNodeId())
                        .findFirst()
                        .get();
                if(e.getValue() == Integer.MAX_VALUE)
                    edges.remove(e);
            }
        }

        return allPossiblePaths;
    }
    
    public double determineMaximumFlow(int sourceNodeId, int targetNodeId) {
        List<Path> allPossiblePaths = getAllPossiblePaths(sourceNodeId, targetNodeId);

        //Kein Pfad zur TargetId
        if(allPossiblePaths == null) return 0.0;

        double ges = 0;
        for (Path p :
                allPossiblePaths) {
            ges += getMinimun(p);
        }
        return ges;
    }
    
    public double getMinimun(Path p) {
        double min = Integer.MAX_VALUE;

        for (Edge e :
                p.edgeList) {
            if (e.getValue() < min)
                min = e.getValue();
        }

        return min;
    }

    public double getMaximum(Path p) {
        double max = Integer.MIN_VALUE;

        for (Edge e :
                p.edgeList) {
            if (e.getValue() > max)
                max = e.getValue();
        }

        return max;
    }

    public List<Edge> determineBottlenecks(int sourceNodeId, int targetNodeId) {
        List<Path> allPossiblePaths = getAllPossiblePaths(sourceNodeId, targetNodeId);

        List<Edge> bottlenecks = new ArrayList<>();

        for (Path p : allPossiblePaths) {
            bottlenecks.add(determineBottleneck(p));
        }
        
        return bottlenecks;
    }
    
    public Edge determineBottleneck(Path p) {
        
        Edge bottleneck = null;


        for(Edge e : p.edgeList){
            if(bottleneck == null)
                bottleneck = e;
            
            if(e.getValue() < bottleneck.getValue()){
                bottleneck = e;
            }
        }

        return bottleneck;
    }

}
