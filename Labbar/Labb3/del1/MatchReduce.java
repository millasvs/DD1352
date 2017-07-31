import java.io.BufferedWriter;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.Scanner;

import java.util.LinkedList;

import java.io.PrintWriter;
import java.io.IOException;

public class MatchReduce {

	static Kattio io;

	static ArrayList<LinkedList<Integer>> adjacencyListA;
	static ArrayList<LinkedList<Integer>> adjacencyListB;
	static int numberOfVertices;
	static int A, B, C, D, E;

	public static void main(String[] args) throws IOException {

		try {
			io = new Kattio(System.in, System.out);

			readBipartiteGraph();
			writeFlowGraph();
			readMaxFlowSolution();
			writeBiMatchSolution();
			
			io.close();
		} catch (Exception exception) {
			exception.printStackTrace();
		}
		
		PrintWriter writer = new PrintWriter("the-file-name.txt", "UTF-8");
		writer.println("A: " + A);
		writer.println("B: " + B);
		writer.println("C: " + C);
		writer.println("D: " + D);
		writer.println("E: " + E);
		writer.println("numberOfVertices: " + numberOfVertices);
		writer.close();
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
		// rad 1
		io.println(numberOfVertices);

		// rad 2
		io.println("1 " + numberOfVertices);

		// rad 3
		int E = A + B + C;
		io.println(E);

		// resterande rader
		for (int i = 1; i <= numberOfVertices; i++) {
			for (int j : adjacencyListA.get(i)) {
				io.println(i + " " + j + " 1");
			}
		}

		io.flush();
	}

	static void readMaxFlowSolution() {
		D = io.getInt(); // antalet hörn

		io.getInt(); // vi behöver
		io.getInt(); // ej dessa

		E = io.getInt(); // antalet kanter i matchningen

		int F = io.getInt(); // antalet kanter med positivt flöde

		adjacencyListB = new ArrayList<LinkedList<Integer>>();
		adjacencyListB.add(null); // vi kör Viggo/Fadil och vill därför ej använda index 0

		for (int i = 0; i < A; i++)
		{
			adjacencyListB.add(new LinkedList<Integer>());
		}

		// hantera kanterna med positivt flöde
		for (int i = 0; i < F; i++) {
			int e1 = io.getInt();
			int e2 = io.getInt();
			int c = io.getInt();
			
			// hantera kanter mellan vänster- och högergrupperna av vikt 1
			if (c==1 && e1 != 1 && e2 != D){
				adjacencyListB.get(e1-1).add(e2-1);
			}
		}
	}

	static void writeBiMatchSolution() throws IOException {
		// rad 1
		io.println(A + " " + B);

		// rad 2
		io.println(E);

		// resterande rader
		for (int i = 1; i <= A; i++) {
			for (int j : adjacencyListB.get(i)) {
				io.println(i + " " + j);
			}
		}

		io.flush();
	}
}