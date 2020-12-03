package htbla.aud3.graphtheory;

/*
 * @author bheissenberger, fhainzinger, mschneglberger
 */
public class Edge {
    private int fromNode;
    private int toNode;
    private double valueNode;
    private int flow;

    public Edge(int fromNode, int toNode, double valueNode, int flow) {
        this.fromNode = fromNode;
        this.toNode = toNode;
        this.valueNode = valueNode;
        this.flow = flow;
    }

    public int getFlow() {
        return flow;
    }

    public void setFlow(int flow) {
        this.flow = flow;
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
