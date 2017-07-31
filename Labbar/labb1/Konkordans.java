import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.RandomAccessFile;
import java.util.Scanner;

public class Konkordans {

	public static void main(String args[]) throws Exception{
		
		if (args.length != 1) {
			System.out.println("enter precisely one word");
			return;
		}
		
		//String searchWord = "komplexiteten";
		//String searchWord = new String(args[0].getBytes("ISO-8859-1"));
		String searchWord = args[0];


		BufferedReader in
		= new BufferedReader(new FileReader("/var/tmp/MATHILDA/f1.txt"));

		String hash = String.valueOf(hash(searchWord));

		String line;

		long startPos = -1;
		long length = -1;

		while((line = in.readLine()) != null){
			String[] sarray = line.split(":", 3);

			if(sarray[0].equals(hash)){
				startPos =  Long.parseLong(sarray[1]);
				length = Long.parseLong(sarray[2]);

				break;
			}
		}

		in.close();

		if(length==-1) {
			System.out.println("no match (1)");
			return;
		}

		File f2 = new File("/var/tmp/MATHILDA/f2.txt");
		RandomAccessFile raf = new RandomAccessFile(f2, "r");

		raf.seek(startPos);

		byte[] barray = new byte[(int) length];
		raf.read(barray);
		raf.close();

		String[] sarray = new String(barray).split(":");
		//String[] output = binarySearch(sarray, 0, sarray.length - 1, searchWord);
		
		String[] output = null;
		
		for (String possibleWordRaw : sarray) {
			String[] possibleWordSplit = possibleWordRaw.split(";");

			if (possibleWordSplit[0].equals(searchWord)) {
				output = possibleWordSplit;
				break;
			}
		}

		
		if (output == null) {
			System.out.println("no match (2)");
			return;
		}


		File f3 = new File("/var/tmp/MATHILDA/f3.txt");
		raf = new RandomAccessFile(f3, "r");

		raf.seek(Long.parseLong(output[1]));

		barray = new byte[Integer.parseInt(output[2])];
		raf.read(barray);
		raf.close();

		sarray = new String(barray).split(":");

		File korpus = new File("/info/adk16/labb1/korpus");
		raf = new RandomAccessFile(korpus, "r");

		System.out.println("Det finns " + sarray.length + " " +  (sarray.length == 1 ? "förekomst" : "förekomster") + " av ordet.");

		if(sarray.length > 25){
			Scanner scanner = new Scanner(System.in);
			
			while (true) {	
				System.out.print("Vill du se alla förekomster av ordet (y/n)? ");
				String answer = scanner.next();
				
				if(answer.equalsIgnoreCase("n")){
					scanner.close();
					return;
				}
				
				else if (answer.equalsIgnoreCase("y") || answer.length() == 0) {
					break;
				}
			}
			
			scanner.close();
		}

		for(int i = 0; i < sarray.length;i++){


			long startP = Long.parseLong(sarray[i])  - 30;
			long len = searchWord.length() + 60;
			if(startP < 0){
				startP = 0;	
			}
			if((startP + len) > raf.length()){
				len = raf.length() - startP;	
			}



			raf.seek(startP);
			barray = new byte[(int)len];

			raf.read(barray);

			System.out.println(new String(barray, "ISO-8859-1").replace('\n', ' '));
			//System.out.println(new String(barray, "UTF-8").replace('\n', ' '));


		}
		
		raf.close();


	}

/*
	private static String[] binarySearch(String[] array, int a, int b, String searchWord) {

		if (b-a < 0) { return null; }

		int middle = a + ((b - a) / 2);

		String[] split = array[middle].split(";");

		int cmp = split[0].compareTo(searchWord);
		
		if (cmp < 0) {
			return binarySearch(array, middle + 1, b, searchWord);
		} else if (cmp > 0) {
			return binarySearch(array, a, middle, searchWord);
		} else {
			return split;
		}
	}
*/

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
