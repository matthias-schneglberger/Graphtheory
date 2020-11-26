package htbla.aud3.graphtheory;

import java.lang.reflect.Array;
import java.util.ArrayList;
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
        List<Integer>l = new ArrayList<>();
        for (Edge e:
             edgeList) {
            l.add(e.getFromNodeId());
        }
        int[] array = l.stream().mapToInt(i->i).toArray();
        return array;
    }
    
    public double computeDistance() {
        double distance =0.0;
        for (Edge e:
                edgeList) {
            distance =+ e.getValue();
        }
        return distance;
    }

    public Path clone(){
        Path p = new Path();
        p.edgeList = new ArrayList<>(edgeList);
        return p;
    }
    
}
