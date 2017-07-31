import java.io.BufferedWriter;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.Iterator;

import java.util.LinkedList;

import java.io.IOException;

public class MatchReduce {

	static Kattio io;

	static ArrayList<LinkedList<Integer>> adjacencyListA;
	//static ArrayList<LinkedList<Integer>> adjacencyListB;
	static int numberOfVertices;
	static int A, B, C, D, E;
	static MaxFlowResult maxFlowResult;

	public static void main(String[] args) throws IOException {

		try {
			io = new Kattio(System.in, System.out);

			readBipartiteGraph();
			writeFlowGraph();
			writeBiMatchSolution();
			
			io.close();
		} catch (Exception exception) {
			exception.printStackTrace();
		}
	}

	static void readBipartiteGraph() {
		A = io.getInt();
		B = io.getInt();
		C = io.getInt();

		adjacencyListA = new ArrayList<LinkedList<Integer>>();
		adjacencyListA.add(null); // vi kör Viggo/Fadil-index och vill därför ej använda index 0

		numberOfVertices = A+B+2;

		// lägg in source + vänsterhörn + högerhörn + sink
		for (int i = 1; i <= numberOfVertices; i++) {
			adjacencyListA.add(new LinkedList<Integer>());
		}

		// lägg in de kanter vi får som indata
		for (int i = 0; i < C; i++) {
			adjacencyListA.get(io.getInt()+1).add(io.getInt()+1);
		}

		// kanter från source till vänstersidan
		for(int x = 1; x <= A; x++) {
			adjacencyListA.get(1).add(x+1);
		}

		// kanter från högersidan till sink
		for(int x = A+1; x < numberOfVertices-1; x++) {
			adjacencyListA.get(x+1).add(numberOfVertices);
		}
	}

	static void writeFlowGraph() throws IOException {
		int E = A + B + C;
		
		MaxFlow maxFlow = new MaxFlow(numberOfVertices, 1, numberOfVertices, E, adjacencyListA);
		maxFlowResult = maxFlow.run();
	}
	
	static void writeBiMatchSolution() throws IOException {
		// rad 1
		io.println(A + " " + B);

		// resterande rader
		Iterator<Edge> iterator = maxFlowResult.edges.iterator();
		
		while (iterator.hasNext()) {
			Edge focus = iterator.next();
			
			if (focus.from == 1 || focus.to == numberOfVertices) {
				iterator.remove();
			}
		}
		
		// rad 2
		io.println(maxFlowResult.edges.size());
		
		for (Edge edge : maxFlowResult.edges) {
			
			io.println((edge.from-1) + " " + (edge.to-1));
		}

		io.flush();
	}
}