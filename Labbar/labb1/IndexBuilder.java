import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class IndexBuilder {
	
	public static void main(String args[]) throws Exception{
		
		Map<Integer, Map<String, List<Integer>>> arrays = new HashMap<Integer, Map<String, List<Integer>>>();
		File tk = new File("/var/tmp/MATHILDA/tokenizer_bra_format.txt");
		FileInputStream stream = new FileInputStream(tk);
		
		
		Kattio io = new Kattio(stream, System.out);
		
		  while (io.hasMoreTokens()) {
			
			
		     String word = io.getWord();
		     int position = io.getInt();
		     
		     int hash = hash(word);
		     
		     Map<String, List<Integer>> array = arrays.get(hash);
		     
		     if (array == null)
		     {
		    	 array = new HashMap<String, List<Integer>>();
		    	 array.put(word, null);
		    	 
		    	 arrays.put(hash, array);
		     }
		     
		     List<Integer> positions = array.get(word);
		     
		     if (positions == null)
		     {
		    	 positions = new ArrayList<Integer>();
		    	 array.put(word, positions);
		     }
		     
		     positions.add(position);
		  }
			  
			  int[] hashes = new int[arrays.size()];

			  int index = 0;

			  for( Integer i : arrays.keySet() ) {
			    hashes[index++] = i;
			  }
			  
			  Arrays.sort(hashes);
			  
			  File f1 = new File("/var/tmp/MATHILDA/f1.txt");
			  File f2 = new File("/var/tmp/MATHILDA/f2.txt");
			  File f3 = new File("/var/tmp/MATHILDA/f3.txt");
			  
			  FileOutputStream f1Stream = new FileOutputStream(f1);
			  FileOutputStream f2Stream = new FileOutputStream(f2);
			  FileOutputStream f3Stream = new FileOutputStream(f3);
			  
			  int c2 = 0;
			  int c3 = 0;
			  
			  for (int hash : hashes)
			{
				  int c2StartThisLine = c2;
				  
				  f1Stream.write((hash + ":" + c2 + ":").getBytes());
				
				Map<String, List<Integer>> wordsNotSorted = arrays.get(hash);
				String[] words = new String[wordsNotSorted.size()];
				
				int index2 = 0;
				
				for( String i2 : wordsNotSorted.keySet() ) {
					words[index2++] = i2;
				  }
				  
				  Arrays.sort(words);
				  
				  for (String word : words)
				  {
					  int c3StartThisLine = c3;

					  byte[] toWrite2 = (word + ";" + c3 + ";").getBytes();
					  f2Stream.write(toWrite2);
					  c2 += toWrite2.length;
					  
					  for(int pos : wordsNotSorted.get(word)){
						  
						  byte[] toWrite3 = (pos + ":").getBytes();
						  f3Stream.write(toWrite3);
						  c3 += toWrite3.length;
				
					  }
					  
					  toWrite2 = ((c3 - c3StartThisLine) + ":").getBytes();
					  f2Stream.write(toWrite2);
					  c2 += toWrite2.length;

					  f3Stream.write('\n');
					  c3++;
					  //System.out.println(" - " + word);
				  }
				  
				  f1Stream.write(((c2 - c2StartThisLine) + "\n").getBytes());
				  
				  f2Stream.write('\n');
				  c2++;
			}
			  
			  f1Stream.close();
			  f2Stream.close();
			  f3Stream.close();
			  
			  io.close();
			  
		
			  
		
	}
	
	
	public static int hash(String str){
		int res=0;
		
		for(int i = 0; i < Math.min(3, str.length()); i++){
			char a = str.charAt(i);
			
			int ia = a-97;
			if(ia < 0 || ia > 30)ia =0;
			
			ia *= (int)Math.pow(30, 2-i);
			
			res+=ia;

		}
		
			
			return res + 1;
			
		
		
		
	}
	
}
