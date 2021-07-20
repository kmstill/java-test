import java.util.Random;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException; 

public class Task1 {

	/**
	 * main method to drive application
	 * @param args - args[0] is the number of random numbers to write to a file
	 *               args[1] is the name of the file to write the numbers to
	 */
	public static void main(String[] args) {
		int n = Integer.parseInt(args[0]);
		String filename = args[1];
		writeRandomNumbersToFile(n, filename);
	}
	
	/**
	 * Creates a file and writes n random numbers to the file.
	 * If a file with the name filename already exists, it will be overwritten.  
	 * Numbers are represented as Java longs. 
	 * @param n - number of random numbers to write to file
	 * @param filename - file to create and write numbers to 
	 */
	private static void writeRandomNumbersToFile(int n, String filename) {
		Random rand = new Random();
		try {
			BufferedWriter br = new BufferedWriter(new FileWriter(new File(filename)));
			for(int i = 0; i < n; i++) {
			    String numToWrite = String.valueOf(rand.nextLong());
				br.write(numToWrite);   
				br.write("\n");
			}	
			br.close();			
		}catch(IOException e) {
			System.out.println("An error occured.");
			e.printStackTrace();
		}
	}
	
}
