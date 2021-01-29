package htbla.aud3.graphtheory;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author bheissenberger, fhainzinger, mschneglberger
 */
public class Path {

    public List<Edge> edgeList;

    public Path() {
        this.edgeList = new ArrayList<>();
    }

    public Path(List<Edge> edgeList) {
        this.edgeList = edgeList;
    }

    public int[] getNodeIds() {
        List<Integer> l = new ArrayList<>();
        for (Edge e:
             edgeList) {
            l.add(e.getFromNodeId());
        }
        if(l.size() != 1)
            l.add(getEndID());
        int[] array = l.stream().mapToInt(i->i).toArray();
        return array;
    }
    
    public double computeDistance() {
        double distance = 0.0;
        for (Edge e:
                edgeList) {
            distance += e.getValue();
        }
        return distance;
    }

    public Path clone(){
        Path p = new Path();
        p.edgeList = new ArrayList<Edge>();
        for (Edge e:
             edgeList) {
            p.edgeList.add(new Edge(e.getFromNodeId(),e.getToNodeId(),e.getValue(), e.getFlow()));
        }

        return p;
    }

    public int getEndID(){
        return edgeList.get(edgeList.size()-1).getToNodeId();
    }
    
}
