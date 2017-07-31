/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/* Labb 2 i DD1352 Algoritmer, datastrukturer och komplexitet    */
/* Se labbanvisning under kurswebben https://www.kth.se/social/course/DD1352 */
/* Ursprunglig f�rfattare: Viggo Kann KTH viggo@nada.kth.se      */

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
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

        long t1 = System.currentTimeMillis();
       BufferedReader stdin = new BufferedReader(new FileReader(new File("ordlista.txt")));
 //   BufferedReader stdin = new BufferedReader(new InputStreamReader(System.in, "UTF-8"));

        // S�krast att specificera att UTF-8 ska anv�ndas, f�r vissa system har annan
//    // standardinst�llning f�r teckenkodningen.
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
        long tottime = (System.currentTimeMillis() - t1);
        System.out.println("CPU time: " + tottime + " ms");


    }
}
