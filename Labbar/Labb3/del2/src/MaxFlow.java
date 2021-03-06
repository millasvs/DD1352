import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.HashSet;

public class MaxFlow {
	static ArrayList<LinkedList<Integer>> adjacencyList = new ArrayList<LinkedList<Integer>>();
	static int[][] c, f, cf;
	static int numberOfVertices;
	static int source, sink;
	
	public static void main(String[] args) {
		Kattio io = new Kattio(System.in, System.out);
		
		numberOfVertices = io.getInt();

		source = io.getInt(); 
		sink = io.getInt(); 
		
		adjacencyList.add(null);
		
		
		for (int i = 1; i <= numberOfVertices; i++) {
			adjacencyList.add(new LinkedList<>());
		}
		
		int numberOfEdges = io.getInt();
		
		// f[u,v]:=0; f[v,u]:=0 
		f = new int[numberOfVertices + 1][numberOfVertices + 1];
		
		cf = new int[numberOfVertices + 1][numberOfVertices + 1];
		c = new int[numberOfVertices + 1][numberOfVertices + 1];
		
		for (int i = 0; i < numberOfEdges; i++) {

			int from = io.getInt();
			int to = io.getInt();
			int capacity = io.getInt();
			
			adjacencyList.get(from).add(to);
			c[from][to] += capacity;
			
			// cf[u,v]:=c[u,v]; cf[v,u]:=c[v,u]
			cf[from][to] += capacity;
		}
		
		
		LinkedList<Flow> flows = new LinkedList<>();
		HashMap<Edge, Integer> combinedEdges = new HashMap<>();
		
		
		// while det finns en stig p fran s till t i restflodesgrafen do
		while (true) {
			
			// hitta en stig
			LinkedList<Edge> p = BFS();
			
			if (p.isEmpty()) {
				break;
			}
			
			// hitta det storsta tal som kan floda genom flodet
			// "bottleneck"
			int r = Integer.MAX_VALUE;
			
			for (Edge edge : p) {
				int u = edge.from;
				int v = edge.to;
				
				int cfThis = cf[u][v];
				 
				if (cfThis < r) { r = cfThis; }
			}
			
			// for varje kant (u,v) i p do
			for (Edge edge : p) {
				int u = edge.from;
				int v = edge.to;
				
				// f[u,v]:=f[u,v]+r 
				f[u][v] = f[u][v] + r;
				
				// f[v,u]:= -f[u,v]
				f[v][u] = f[u][v] * -1;
				
				// cf[u,v]:=c[u,v] - f[u,v]
				cf[u][v] = c[u][v] - f[u][v];
				
				// cf[v,u]:=c[v,u] - f[v,u]
				cf[v][u] = c[v][u] - f[v][u];
				
				if (!adjacencyList.get(v).contains(u)) {
					adjacencyList.get(v).add(u);
				}
			}
			
			flows.add(new Flow(r, p));
			
			
			
			// kod for att inte duplicera kanter vid utskrift
			for (Edge edge : p) {
				Integer previousFlow = combinedEdges.get(edge);
				
				if (previousFlow == null) {
					combinedEdges.put(edge, r);
				} else {
					combinedEdges.put(edge, previousFlow + r);
				}
			}
		}
		
		Iterator<Edge> iterator = combinedEdges.keySet().iterator();
		Set<Edge> toRemove = new HashSet<>();
		
		while (iterator.hasNext()) {
			Edge focus = iterator.next();
			Edge mightExist = new Edge(focus.to, focus.from);
			
			if (combinedEdges.containsKey(mightExist)) {
			//om finalCapacity < 0 är mightExist den rättvända kanten som ska behållas
			//om finalCapacity > 0 är focus den rättvända kanten som ska behållas
				//int finalCapacity = cf[focus.from][focus.to] - cf[mightExist.from][mightExist.to];
				int finalCapacity = combinedEdges.get(focus) - combinedEdges.get(mightExist);
				
				if(finalCapacity == 0) {
					iterator.remove();
					toRemove.add(mightExist);


				} else if(finalCapacity < 0){
					// cf[mightExist.from][mightExist.to] = -finalCapacity;
					combinedEdges.put(mightExist, -finalCapacity);
					
					iterator.remove();
				}
			}
			
		}
		
		for (Edge removeMe : toRemove) {
			combinedEdges.remove(removeMe);
		}
		
		int flowSum = 0;
		
		for (Flow flow : flows) {
			flowSum += flow.flow;
		}
		
		io.println(numberOfVertices);
		io.println(source + " " + sink + " " + flowSum);
		io.println(combinedEdges.size());
		
		List<Edge> combinedEdgesSorted = new ArrayList<>(combinedEdges.keySet());
		Collections.sort(combinedEdgesSorted);
		
		for (Edge edge : combinedEdgesSorted) {
			io.println(edge.from + " " + edge.to + " " + combinedEdges.get(edge));
		}
		
		io.flush();
		io.close();
	}
	
	static LinkedList<Edge> BFS() {
		
		int[] parent = new int[numberOfVertices + 1];
		
		parent[source] = -1;
		
		LinkedList<Integer> Q = new LinkedList<>();
		Q.add(source);
		
		outer:
		while (!Q.isEmpty()) {
			
			int current = Q.pop();
			
			for (int n : adjacencyList.get(current)) {
				
				if (cf[current][n] < 1) { continue; }
				
				if (parent[n] == 0) {
					parent[n] = current;
					
					Q.add(n);
					
					// optimera
					if (n == sink) {
						break outer;
					}
				}
			}
		}
		
		LinkedList<Edge> path = new LinkedList<>();
		
		int from = parent[sink];
		int to = sink;
		
		while (true) {
			path.addFirst(new Edge(from, to));
			
			if (from == 0) {
				return new LinkedList<>();
			} else if (from == source) {
				return path;
			} else {
				to = from;
				from = parent[from];
			}
		}
	}
	
	
	static void printPath(LinkedList<Edge> path) {
		
		if (path.isEmpty()) {
			System.out.println("(empty path)");
			return;
		}
		
		Iterator<Edge> iterator = path.iterator();

		Edge edge = iterator.next();
		System.out.print(edge.from + " -> " + edge.to);
		
		while (iterator.hasNext()) {
			System.out.print(" -> " + iterator.next().to);
		}
		
		System.out.println();
	}
}