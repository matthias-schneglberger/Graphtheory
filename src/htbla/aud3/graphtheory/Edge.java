package htbla.aud3.graphtheory;

/*
 * @author bheissenberger, fhainzinger, mschneglberger
 */
public class Edge {
    private int fromNode;
    private int toNode;
    private double valueNode;

    public Edge(int fromNode, int toNode, double valueNode) {
        this.fromNode = fromNode;
        this.toNode = toNode;
        this.valueNode = valueNode;
    }

    public int getFromNodeId() {
        return fromNode;
    }
    
    public int getToNodeId() {
        return toNode;
    }

    public double getValue(){
        return valueNode;
    }

    public void setFromNodeId(int id){
        fromNode = id;
    }

    public void setToNodeId(int id){
        toNode = id;
    }

    public void setValue(double val){
        valueNode = val;
    }
    
}
