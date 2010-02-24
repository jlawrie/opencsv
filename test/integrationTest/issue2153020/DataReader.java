/**
 * 
 */
package integrationTest.issue2153020;

import au.com.bytecode.opencsv.CSVReader;

import java.io.FileReader;
import java.io.IOException;

/**
 * @author scott
 *
 */
public class DataReader
{
	private static final String ADDRESS_FILE="test/integrationTest/issue2153020/Sample.csv";

	/**
	 * @param args
	 * @throws IOException 
	 */
	public static void main(String[] args) throws IOException
	{
					
			CSVReader reader = new CSVReader(new FileReader(ADDRESS_FILE));
			String [] nextLine;
			while ((nextLine = reader.readNext()) != null) {
				int numLines = nextLine.length;
				System.out.println("Number of Data Items: " + numLines);
				for (int i = 0; i < numLines; i++)
				{
					System.out.println("     nextLine[" + i + "]:  " + nextLine[i]);
				}
			}

	}

}
