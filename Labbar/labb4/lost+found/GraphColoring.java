import java.util.*;

/**
 * Created by martin and mathilda on 11/11/16.
 */
public class GraphColoring
{
    private static Object ArrayList;

    public static void main(String[] args) {
        Kattio io = new Kattio(System.in, System.out);

        List<Edge> edges = new LinkedList<Edge>();
        Set<Integer> lonelyNodes = new HashSet<Integer>();

        // läs in graf
        int countVertices = io.getInt();
        int countEdges = io.getInt();
        int maxColors = io.getInt();

        for (int i = 1; i <= countVertices; i++) {
            lonelyNodes.add(i);
        }

        for (int i = 0; i < countEdges; i++) {
            int x = io.getInt();
            int y = io.getInt();

            lonelyNodes.remove(x);
            lonelyNodes.remove(y);

            edges.add(new Edge(x, y));
        }

        // justera kanternas hörn
        for (int lonelyNode : lonelyNodes) {

          //io.println("hittade ensam: " + lonelyNode);

          for (Edge edge : edges) {

            //io.print("\t" + edge.x + "," + edge.y);

            if (edge.x >= lonelyNode) {
              edge.reduceX++;
              //io.print("\t--x");
            }

            if (edge.y >= lonelyNode) {
              edge.reduceY++;
              //io.print("\t--y");
            }

            //io.println();
          }
        }

        for (Edge edge : edges) {
          edge.x = edge.x - edge.reduceX;
          edge.y = edge.y - edge.reduceY;
        }

        /*
        io.println(countVertices - lonelyNodes.size());
        io.println(edges.size());
        io.println(maxColors);

        for (Edge edge : edges) {
          io.println(edge.x + " " + edge.y);
        }
        */


        // konvertera till rollbesättningsproblemet
        int n = countVertices - lonelyNodes.size() + 3;
        int s = edges.size() + 2;
        int k = maxColors + 3;

        // skriv ut
        io.println(n);
        io.println(s);
        io.println(k);

        // villkor av typ 1 (vilka skådespelare som kan spela vilka roller)
        io.println("1 1"); // roll 1 kan bara spelas av skådis 1
        io.println("1 2"); // roll 2 kan bara spelas av skådis 2
        io.println("1 3"); // roll 3 kan bara spelas av skådis 3

        for (int i = 4; i <= n; i++) {
            io.print(k - 3);

            for (int j = 4; j <= k; j++) {
                io.print(" " + j);
            }

            io.println();
        }

        // hårdkodade villkor för att satisfiera divorna
        io.println("2 1 3");
        io.println("2 2 3");

        // villkor av typ 2 (vilka roller som ingår i vilka scener)
        for (Edge edge : edges) {
            io.println("2 " + (edge.x + 3) + " " + (edge.y + 3));
        }
        
        io.close();
    }
}
