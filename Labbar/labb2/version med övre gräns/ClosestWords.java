package dd1352_labb2;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/* Labb 2 i DD1352 Algoritmer, datastrukturer och komplexitet    */
/* Se labbanvisning under kurswebben https://www.kth.se/social/course/DD1352 */
/* Ursprunglig författare: Viggo Kann KTH viggo@nada.kth.se      */
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class ClosestWords {

    static final int matrixDim = 28;
    static final int matrixSize = matrixDim * matrixDim;

    int closestDistance = -1;
    int[] d = new int[matrixSize];
    String last_w2 = "";
    List<String> closestWords = new LinkedList<>();

    int Distance(String w1, String w2) {

        int w1len = w1.length();
        int w2len = w2.length();

        int cp = commonPrefix(last_w2, w2);

        int index = matrixDim + 1;
        boolean threshold_has_been_reached = false;
        //iterates through the rows
        outerloop:
        for (int x = 1; x <= w1len; x++) {
            //iterates through the columns
            for (int y = 1; y <= w2len; y++) {

                if (x > cp || y > cp) {
                    // fill matrix cell
                    int res = d[index - (matrixDim + 1)] + (w1.charAt(x - 1) == w2.charAt(y - 1) ? 0 : 1);
                    int addLetter = d[index - matrixDim] + 1;
                    if (addLetter < res) {
                        res = addLetter;
                    }

                    int deleteLetter = d[index - 1] + 1;
                    if (deleteLetter < res) {
                        res = deleteLetter;
                    }

                    if (res > w1len & x==y) {
                        threshold_has_been_reached = true;
                        break outerloop;
                    }

                    d[index] = res;
                }

                index++;
            }

            index += (matrixDim - w2len);
        }

        if (threshold_has_been_reached) {
            return Integer.MAX_VALUE;
        }

        last_w2 = w2;

        //System.out.println(w1 + "/" + w2);
        //printMatrix(d);
        return d[index - 1 - (matrixDim - w2len)];
    }

    public ClosestWords(String w, List<String> wordList) {
        for (int x = 0; x < matrixDim; x++) {
            d[x] = x;
        }

        int value = 1;

        for (int index = matrixDim; index < matrixSize; index += matrixDim) {
            d[index] = value;
            value++;
        }

        for (String s : wordList) {
            int dist = Distance(w, s);

            if (dist < closestDistance || closestDistance == -1) {
                closestDistance = dist;
                closestWords.clear();
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

    int getMinDistance() {
        return closestDistance;
    }

    List<String> getClosestWords() {
        return closestWords;
    }

    int getIndex(int x, int y) {
        return (matrixDim * x) + y;
    }

    void printMatrix(int[][] matrix) {
        for (int m = 0; m < matrix.length; m++) {
            for (int n = 0; n < matrix[m].length; n++) {
                System.out.print(matrix[m][n] + " ");
            }

            System.out.println();
        }

        System.out.println();
    }

    void printMatrix(int[] matrix) {
        for (int m = 0; m < matrix.length; m++) {

            if (m % matrixDim == 0) {
                System.out.println();
            }

            System.out.print(matrix[m] + "\t");
        }

        System.out.println();
    }
}
