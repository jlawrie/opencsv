package au.com.bytecode.opencsv;

/**
 Copyright 2005 Bytecode Pty Ltd.

 Licensed under the Apache License, Version 2.0 (the "License");
 you may not use this file except in compliance with the License.
 You may obtain a copy of the License at

 http://www.apache.org/licenses/LICENSE-2.0

 Unless required by applicable law or agreed to in writing, software
 distributed under the License is distributed on an "AS IS" BASIS,
 WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 See the License for the specific language governing permissions and
 limitations under the License.
 */

import java.io.IOException;
import java.io.StringReader;
import java.util.List;

import junit.framework.TestCase;

public class CSVReaderTest extends TestCase {

	CSVReader csvr;

	
	/**
	 * Setup the test.
	 */
	protected void setUp() throws Exception {
		StringBuffer sb = new StringBuffer();
		sb.append("a,b,c").append("\n");   // standard case
		sb.append("a,\"b,b,b\",c").append("\n");  // quoted elements
		sb.append(",,").append("\n"); // empty elements
		sb.append("a,\"PO Box 123,\nKippax,ACT. 2615.\nAustralia\",d.\n");
		sb.append("\"Glen \"\"The Man\"\" Smith\",Athlete,Developer\n"); // Test quoted quote chars
		sb.append("\"\"\"\"\"\",\"test\"\n"); // """""","test"  representing:  "", test
		sb.append("\"a\nb\",b,\"\nd\",e\n");
		csvr = new CSVReader(new StringReader(sb.toString()));
	}


	/**
	 * Tests iterating over a reader.
	 * 
	 * @throws IOException
	 *             if the reader fails.
	 */
	public void testParseLine() throws IOException {

		// test normal case
		String[] nextLine = csvr.readNext();
		assertEquals("a", nextLine[0]);
		assertEquals("b", nextLine[1]);
		assertEquals("c", nextLine[2]);

		// test quoted commas
		nextLine = csvr.readNext();
		assertEquals("a", nextLine[0]);
		assertEquals("b,b,b", nextLine[1]);
		assertEquals("c", nextLine[2]);

		// test empty elements
		nextLine = csvr.readNext();
		assertEquals(3, nextLine.length);
		
		// test multiline quoted
		nextLine = csvr.readNext();
		assertEquals(3, nextLine.length);
		
		// test quoted quote chars
		nextLine = csvr.readNext();
		assertEquals("Glen \"The Man\" Smith", nextLine[0]);
		
		nextLine = csvr.readNext();
		assertTrue(nextLine[0].equals("\"\"")); // check the tricky situation
		assertTrue(nextLine[1].equals("test")); // make sure we didn't ruin the next field..
		
		nextLine = csvr.readNext();
		assertEquals(4, nextLine.length);
		
		//test end of stream
		assertEquals(null, csvr.readNext());

	}

	/**
	 * Test parsing to a list.
	 * 
	 * @throws IOException
	 *             if the reader fails.
	 */
	public void testParseAll() throws IOException {

		List allElements = csvr.readAll();
		assertEquals(7, allElements.size());

	}
	
	/**
	 * Tests constructors with optional delimiters and optional quote char.
	 * 
	 * @throws IOException if the reader fails.
	 */
	public void testOptionalConstructors() throws IOException {
		
		StringBuffer sb = new StringBuffer();
		sb.append("a\tb\tc").append("\n");   // tab separated case
		sb.append("a\t'b\tb\tb'\tc").append("\n");  // single quoted elements
		CSVReader c = new CSVReader(new StringReader(sb.toString()), '\t', '\'');
		
		String[] nextLine = c.readNext();
		assertEquals(3, nextLine.length);

		nextLine = c.readNext();
		assertEquals(3, nextLine.length);

		
	}
	
	/**
	 * Tests option to skip the first few lines of a file.
	 * 
	 * @throws IOException if bad things happen
	 */
	public void testSkippingLines() throws IOException {
		
		StringBuffer sb = new StringBuffer();
		sb.append("Skip this line\t with tab").append("\n");   // should skip this
		sb.append("And this line too").append("\n");   // and this
		sb.append("a\t'b\tb\tb'\tc").append("\n");  // single quoted elements
		CSVReader c = new CSVReader(new StringReader(sb.toString()), '\t', '\'', 2);
		
		String[] nextLine = c.readNext();
		assertEquals(3, nextLine.length);
		
		assertEquals("a", nextLine[0]);
	}
	
	/**
	 * Tests quotes in the middle of an element.
	 * 
	 * @throws IOException if bad things happen
	 */
	public void testParsedLineWithInternalQuota() throws IOException {

		StringBuffer sb = new StringBuffer();

		sb.append("a,123\"4\"567,c").append("\n");// a,123"4",c

		CSVReader c = new CSVReader(new StringReader(sb.toString()));

		String[] nextLine = c.readNext();
		assertEquals(3, nextLine.length);

		System.out.println(nextLine[1]);
		assertEquals("123\"4\"567", nextLine[1]);

	}

	/**
	 * The Test Runner for commandline use.
	 * 
	 * @param args
	 *            no args required
	 */
	public static void main(String args[]) {
		junit.textui.TestRunner.run(CSVReaderTest.class);
	}

}
