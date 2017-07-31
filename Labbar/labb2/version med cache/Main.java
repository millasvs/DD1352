/* Labb 2 i DD1352 Algoritmer, datastrukturer och komplexitet    */
 /* Se labbanvisning under kurswebben https://www.kth.se/social/course/DD1352 */
 /* Ursprunglig författare: Viggo Kann KTH viggo@nada.kth.se      */
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

public class Main {

    public static List<String> readWordList(BufferedReader input) throws IOException {
        LinkedList<String> list = new LinkedList<String>();

        while (true) {
            String s = input.readLine();
            if (s.equals("#")) {
                break;
            }
            list.add(s);
        }

        return list;
    }

    public static void main(String args[]) throws IOException {
  
//BufferedReader stdin = new BufferedReader(new InputStreamReader(System.in, "UTF-8"));
       BufferedReader stdin = new BufferedReader(new FileReader(new File("ordlista.txt")));

        // Säkrast att specificera att UTF-8 ska användas, för vissa system har annan
        // standardinställning för teckenkodningen.
        List<String> wordList = readWordList(stdin);
        String word;

        while ((word = stdin.readLine()) != null) {
            ClosestWords closestWords = new ClosestWords(word, wordList);
            System.out.print(word + " (" + closestWords.getMinDistance() + ")");

            
            for (String w : closestWords.getClosestWords()) {
                System.out.print(" " + w);
            }

            System.out.println();
        }
    }
}
