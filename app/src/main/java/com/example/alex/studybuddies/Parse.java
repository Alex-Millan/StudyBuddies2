// ------------------------------------------------------------------------------------------------
// Mario Cabrera
//
// File name:
// 	- Parse.java
//
// Sources:
// 	- UCSC_Course_List
//
// Description:
//	- This file takes in the UCSC_Course_List and parses the file such that
// each Class is saved as it's own string.
//
// ------------------------------------------------------------------------------------------------




import java.util.Scanner;
import java.io.*;

class Parse{

	public static void main(String[] args) throws IOException{


// Usage Code -------------------------------------------------------------------------------------

		// Checks for 2 line arguments
		// Quits with usage message otherwise
		if(args.length != 2){
			System.err.println("Requires UCSC Course List\nTry: java Parse 'Your UCSC Course List file' 'Your Outputfile'");
			System.exit(1);
		}

// Input/Output Code ------------------------------------------------------------------------------

		Scanner inFirst = null;	// Scanner, reads input file
        Scanner inSec = null;
        Scanner inThird = null;
		PrintWriter out = null; // PrintWriter, writes to output file
        String line = null;
        String[] token = null;
		int i, j, k, count = 0, indice, lineNumber = 0;

		// inFirst Scanner: open input file to count number of lines
		// inSec Scanner: open input file again to fill in stringArray
		// out PrintWriter: writes tokens into output file
		inFirst = new Scanner(new File(args[0]));
		inSec = new Scanner(new File(args[0]));
		inThird = new Scanner(new File(args[0]));
		out = new PrintWriter(new FileWriter(args[1]));

		// Counts number of lines in file
		while(inFirst.hasNextLine()){
			lineNumber++;
			inFirst.nextLine();
		}

		System.out.println("lineNumber: " + lineNumber + "\n");

// String Arrays ----------------------------------------------------------------------------------
		
		// Creates two String Arrays,
		// Reads in lines of the file as Strings,
		// and places them into the arrays.
		String[] stringArray = new String[lineNumber];
		String[] tempArray = new String[2];
		String tempString;

		// Reads in class list, removes class sections,
		// and counts the number of unique classes
		line = inSec.nextLine();
		tempArray = line.split(" - ");
		line = tempArray[0];
		stringArray[0] = line;
		tempString = stringArray[0];
		//out.println(tempString + "<--");
		i = 1;
		j = 0;
		while(j < stringArray.length - 1){
			line = inSec.nextLine();
			tempArray = line.split(" - ");
			line = tempArray[0];
			stringArray[i] = line;
			// Duplicates
			if(!stringArray[i].equals(tempString)){
				//out.println(stringArray[i] + "<--");
				tempString = stringArray[i];
				count++;
				//out.println(count);
			}
			i++;
			j++;
			//out.println(j);
		}
		count++;

// Final String Array -----------------------------------------------------------------------------

		// Fills in all the unique classes in the finalArray below
		String[] finalArray = new String[count];
		k = 0;

		line = inThird.nextLine();
		tempArray = line.split(" - ");
		line = tempArray[0];
		stringArray[0] = line;
		tempString = stringArray[0];
		finalArray[k++] = stringArray[0];
		//out.println(finalArray[0] + "<--");
		i = 1;
		j = 0;
		while(j < stringArray.length - 1){
			line = inThird.nextLine();
			tempArray = line.split(" - ");
			line = tempArray[0];
			stringArray[i] = line;
			// Duplicates
			if(!stringArray[i].equals(tempString)){
				//out.println(stringArray[i] + "<--");
				tempString = stringArray[i];
				finalArray[k++] = stringArray[i];
			}
			i++;
			j++;
		}

		// Prints the final String array to the output file
		for(i = 0; i < finalArray.length; i++){
			out.println(finalArray[i]);
		}

		inFirst.close();
		inSec.close();
		inThird.close();
		out.close();
	}
}