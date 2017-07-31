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

    int closestDistance = Integer.MAX_VALUE;
    int[][] d;
    String lastWordToMatchWith = "";
    List<String> closestWords = new LinkedList<>();

    int Distance(String wordToMatchWith, String wordConstant) {
        
        int wordToMatchWithLength = wordToMatchWith.length();
        int wordConstantLength = wordConstant.length();
        
        int cp = commonPrefix(lastWordToMatchWith, wordToMatchWith);

        // bottom
        for (int rowIndex = cp + 1; rowIndex <= wordToMatchWithLength; rowIndex++) {

            for (int columnIndex = 1; columnIndex <= wordConstantLength; columnIndex++) {
            	
            	fillMatrixCell(rowIndex, columnIndex, wordToMatchWith, wordConstant);
            }
        }
        
        lastWordToMatchWith = wordToMatchWith;

        return d[wordToMatchWithLength][wordConstantLength];
    }
    
    void fillMatrixCell(int rowIndex, int columnIndex, String wordToMatchWith, String wordConstant) {
    	// fill matrix cell
        int res = d[rowIndex - 1][columnIndex - 1] + (wordToMatchWith.charAt(rowIndex - 1) == wordConstant.charAt(columnIndex - 1) ? 0 : 1);

        int addLetter = d[rowIndex][columnIndex - 1] + 1;
        if (addLetter < res) {
            res = addLetter;
        }

        int deleteLetter = d[rowIndex - 1][columnIndex] + 1;
        if (deleteLetter < res) {
            res = deleteLetter;
        }

        d[rowIndex][columnIndex] = res;
        
    }

    public ClosestWords(String w, List<String> wordList) {
    	int constlength = w.length();
    	d = new int[40][constlength + 1];
    	
        for (int x = 1; x <= constlength; x++) {
            d[0][x] = x;
        }
        
        for (int x = 1; x < 40; x++) {
            d[x][0] = x;
        }
        
        for (String s : wordList) {
	    if (Math.abs(s.length() - constlength) > closestDistance) { continue; } // tillagd efter redovisning, från 1.07 till 0.88
	    
            int dist = Distance(s, w);
            
            if (dist < closestDistance) {
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
    
    void printMatrix(int[][] matrix)
    {
        for (int m = 0; m < matrix.length; m++) {
            for (int n = 0; n < matrix[m].length; n++) {
                System.out.print(matrix[m][n] + " ");
            }

            System.out.println();
        }
        
        System.out.println();
    }
}
