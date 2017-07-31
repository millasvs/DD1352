class Edge {
  int x, y;
  int reduceX = 0;
  int reduceY = 0;

  Edge(int x, int y) {
    this.x = x;
    this.y = y;
  }

  @Override
  public int hashCode() {
    return x+y;
  }

  @Override
  public boolean equals(Object other) {
    if (!(other instanceof Edge)) {
      return false;
    }

    Edge otherEdge = (Edge) other;

    return otherEdge.x == x && otherEdge.y == y || otherEdge.x == y && otherEdge.y == x;
  }
}