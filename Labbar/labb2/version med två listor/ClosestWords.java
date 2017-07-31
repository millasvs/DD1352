/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/* Labb 2 i DD1352 Algoritmer, datastrukturer och komplexitet    */
/* Se labbanvisning under kurswebben https://www.kth.se/social/course/DD1352 */
/* Ursprunglig författare: Viggo Kann KTH viggo@nada.kth.se      */

import java.util.LinkedList;
import java.util.List;

public class ClosestWords {

    LinkedList<String> closestWords = null;
    int closestDistance = -1;
//    static int diff = -1;
//public static int counter = 0;

    static int Distance(String w1, String w2) {
        int w1len = w1.length();
        int w2len = w2.length();

        int[] d1 = new int[w2len + 1];
        int[] d2 = new int[w2len + 1];

        for (int x = 0; x <= w2len; x++) {
            d1[x] = x;
        }

//iterates through the rows
        for (int x = 1; x <= w1len; x++) {
//                        System.out.println("d2");

            d2[0] = x; //each row begins with the number of the row
//iterates through the columns     
            for (int y = 1; y <= w2len; y++) {

                int res = d1[y - 1] + (w1.charAt(x - 1) == w2.charAt(y - 1) ? 0 : 1);
                int addLetter = d2[y - 1] + 1;
                if (addLetter < res) {
                    res = addLetter;
                }
                int deleteLetter = d1[y] + 1;
                if (deleteLetter < res) {
                    res = deleteLetter;
                }
                d2[y] = res;
//                System.out.print(res + " ");
            }

            for (int y = 0; y <= w2len; y++) {

                d1[y] = d2[y];
            }

        }


        return d2[w2len];

    }

    public ClosestWords(String w, List<String> wordList) {
        for (String s : wordList) {
            int dist = Distance(w, s);
            // System.out.println("d(" + w + "," + s + ")=" + dist);
            if (dist < closestDistance || closestDistance == -1) {
                closestDistance = dist;
                closestWords = new LinkedList<String>();
                closestWords.add(s);
            } else if (dist == closestDistance) {
                closestWords.add(s);
            }
        }
    }

    int getMinDistance() {
        return closestDistance;
    }

    List<String> getClosestWords() {
        return closestWords;
    }
}
