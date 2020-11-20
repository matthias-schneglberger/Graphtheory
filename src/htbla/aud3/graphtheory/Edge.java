package htbla.aud3.graphtheory;

/*
 * @author bheissenberger, fhainzinger, mschneglberger
 */
public class Edge {
    private int fromNode;
    private int toNode;
    
    public int getFromNodeId() {
        return fromNode;
    }
    
    public int getToNodeId() {
        return toNode;
    }

    public void setFromNodeId(int id){
        fromNode = id;
    }

    public void setToNodeId(int id){
        toNode = id;
    }
    
}
