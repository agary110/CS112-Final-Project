//=======================================
/** Importing necessary libraries **/
//=======================================

import java.io.PrintWriter;
import java.io.FileNotFoundException;
import java.io.File;
import java.util.Scanner;

//========================================================================================
/** The Logger class reads the user’s previous high score from a file named “highscore.txt” and compares it to the user’s current points. If the user’s current points are higher than the value read from “highscore.txt”, then the Logger class rewrites “highscore.txt” with the new high score. **/
//========================================================================================

class Logger{

//============================================================
/** Method: writeHighScore(double points, String outFileName)
	Functionality: Compares points to currentHighScore and writes the larger of the two in outFileName **/
//=======================================

	public static void writeHighScore(double points, String outFileName){
		try {
			double currentHighScore = readHighScore(outFileName);
			if (points >= currentHighScore){
				File output = new File(outFileName);
				PrintWriter pointPrinter = new PrintWriter(output);
				pointPrinter.write("" + (int)(points));
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

//=======================================
/** Method: readHighScore(String inFileName)
	Functionality: Returns the first double (the high score) found in inFileName **/
//=======================================

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

/** END OF LOGGER CLASS **/
//=======================================
