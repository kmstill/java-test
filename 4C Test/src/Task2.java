import java.util.Collections;
import java.util.ArrayList;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.BufferedReader;

public class Task2 {

	/**
	 * main method to drive application
	 * @param args - args[0] is path to file containing numbers
	 *               args[1] is "asc" or "desc" for ascending or descending
	 *               if args[1] is not present, or if it has any value other than "desc", assume ascending
	 */
	public static void main(String[] args) {
		String path = args[0]; 
		boolean isAscending = true;
		if (args.length > 1 && args[1].equals("desc")) {
			isAscending = false;
		}
		ArrayList<Long> numbers = getNumbers(path, isAscending);
		printNumbers(numbers); 
	}
	
	/**
	 * Reads numbers from file and adds them to arrayList numbers.
	 * Sorts numbers in ascending order.
	 * If arrayList should be in descending order, reverses arrayList. 
	 * @param path - path to file to read numbers from
	 * @param isAscending - numbers will be sorted in ascending order if true
	 * 						numbers will be sorted in descending order if false
	 */
	private static ArrayList<Long> getNumbers(String path, boolean isAscending) {
		ArrayList<Long> numbers = new ArrayList<Long>();
		try {
			BufferedReader br = new BufferedReader(new FileReader(new File(path)));
			String line = "";
			while (line != null){
				line = br.readLine();
				try {
					Long lineLong = Long.parseLong(line);
					numbers.add(lineLong);
				}catch(NumberFormatException e) {
					continue;
				}
			}	
		}catch(IOException e) {
			System.out.println("An error occured.");
			e.printStackTrace();
		}
		Collections.sort(numbers);
		if (!isAscending) {
		   Collections.reverse(numbers);
		}
		return numbers;				
	}
	
	/**
	 * Prints all numbers in arrayList of numbers to console. 
	 * @param numbers - arrayList of numbers to print 
	 */
	private static void printNumbers(ArrayList<Long> numbers) {
		for (Long number:numbers) {
			System.out.println(number);
		}
	}

}
