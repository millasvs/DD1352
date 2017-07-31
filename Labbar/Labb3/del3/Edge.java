public class Edge implements Comparable<Edge> {
    int from;
    int to;
    
    Edge(int from, int to) {
        this.from = from;
        this.to = to;
    }
    
    @Override
    public int hashCode() {
        return from + to;
    }
    
    @Override
    public boolean equals(Object other) {
        if (!(other instanceof Edge)) { return false; }
        
        Edge otherEdge = (Edge) other;
        
        return otherEdge.from == from && otherEdge.to == to;
    }

    @Override
    public int compareTo(Edge otherEdge) {
        return from - otherEdge.from;
    }
}
