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
    int[][] last_d = null;
    String last_w2 = "";

    int distance(String w1, String w2) {

        int w1len = w1.length();
        int w2len = w2.length();

        int cp = commonPrefix(last_w2, w2);

        if (w2len > w1len && cp + 1 == w2len) {

            return last_d[w1len][last_w2.length()] + 1;
        }

        int[][] d = new int[w1len + 1][w2len + 1];

        // första raden och första kolumnen
        for (int x = 0; x <= w2len; x++) {
            d[0][x] = x;
        }
        for (int x = 0; x <= w1len; x++) {
            d[x][0] = x;
        }

        // kolla om vi har något i vår cache
        if (cp >= w2len/2 && last_d != null) {

            // kopiera in vår cache till d
            for (int x = 1; x <= w1len; x++) {
                for (int y = 1; y <= w2len; y++) {
                    if (y > cp) {
                        // lägg in det som skiljer 
                        fillMatrixCell(w1, w2, x, y, d);
                    } else {
                        d[x][y] = last_d[x][y];
                    }
                }
            }

        } // vi har inget i vår cache
        else {
            //iterates through the rows
            for (int x = 1; x <= w1len; x++) {
                //iterates through the columns
                for (int y = 1; y <= w2len; y++) {
                    fillMatrixCell(w1, w2, x, y, d);
                }
            }
        }
	
        last_d = d;
        last_w2 = w2;

        return d[w1len][w2len];
    }

    public ClosestWords(String w, List<String> wordList) {
        for (String s : wordList) {
            int dist = Distance(w, s);
            if (dist < closestDistance || closestDistance == -1) {
                closestDistance = dist;
                closestWords = new LinkedList<>();
                closestWords.add(s);
            } else if (dist == closestDistance) {
                closestWords.add(s);
            }
        }
    }

    int commonPrefix(String w1, String w2) {
        int min = Math.min(w1.length(), w2.length());
        for (int i = 0; i < min; i++) {
            if (w1.charAt(i) != w2.charAt(i)) {
                return i;
            }
        }
        return min;
    }

    void fillMatrixCell(String w1, String w2, int x, int y, int[][] d) {
        int res = d[x - 1][y - 1] + (w1.charAt(x - 1) == w2.charAt(y - 1) ? 0 : 1);
        int addLetter = d[x][y - 1] + 1;
        if (addLetter < res) {
            res = addLetter;
        }

        int deleteLetter = d[x - 1][y] + 1;
        if (deleteLetter < res) {
            res = deleteLetter;
        }

        d[x][y] = res;
    }

    int getMinDistance() {
        return closestDistance;
    }

    List<String> getClosestWords() {
        return closestWords;
    }
}
