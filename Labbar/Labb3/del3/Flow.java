import java.util.LinkedList;


public class Flow {
    int flow;
    LinkedList<Edge> edges;
    
    Flow(int flow, LinkedList<Edge> edges) {
        this.flow = flow;
        this.edges = edges;
    }
}
