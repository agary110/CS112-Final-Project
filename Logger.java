import java.io.PrintWriter;
import java.io.FileNotFoundException;
import java.io.File;
import java.util.Scanner;

class Logger{
	public static void writeHighScore(double points, String outFileName){
		try {
			double currentHighScore = readHighScore(outFileName);
			if (points >= currentHighScore){
				File output = new File(outFileName);
				PrintWriter pointPrinter = new PrintWriter(output);
				pointPrinter.write("" + points);
				pointPrinter.close();
			} else {
				File output = new File(outFileName);
				PrintWriter pointPrinter = new PrintWriter(output);
				pointPrinter.write("" + currentHighScore);
				pointPrinter.close();
			}
		}
		catch(FileNotFoundException e) {
	   		System.err.println("File not found.");
	   		System.err.println(e);
		}
	}

	public static double readHighScore(String inFileName){
		try {
			File input = new File(inFileName);
			Scanner sc = new Scanner(input);
			while(sc.hasNextDouble()){
				return sc.nextDouble();
			}
		}
		catch(FileNotFoundException e) {
	   		System.err.println("File not found.");
	   		System.err.println(e);
		}
		return 0.0;
	}

}
