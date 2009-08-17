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

import junit.framework.TestCase;

public class CSVReaderTest extends TestCase {

	CSVReader csvr;

	
	/**
	 * Setup the test.
	 */
	protected void setUp() throws Exception {
		StringBuilder sb = new StringBuilder(CSVReader.INITIAL_READ_SIZE);
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
		assertEquals(7, csvr.readAll().size());
	}
	
	/**
	 * Tests constructors with optional delimiters and optional quote char.
	 * 
	 * @throws IOException if the reader fails.
	 */
	public void testOptionalConstructors() throws IOException {
		
		StringBuilder sb = new StringBuilder(CSVReader.INITIAL_READ_SIZE);
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
		
		StringBuilder sb = new StringBuilder(CSVReader.INITIAL_READ_SIZE);
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

		StringBuilder sb = new StringBuilder(CSVReader.INITIAL_READ_SIZE);

		sb.append("a,123\"4\"567,c").append("\n");// a,123"4",c

		CSVReader c = new CSVReader(new StringReader(sb.toString()));

		String[] nextLine = c.readNext();
		assertEquals(3, nextLine.length);

		assertEquals("123\"4\"567", nextLine[1]);

	}
	
	/**
	 * Test a normal non quoted line with three elements
	 * @throws IOException
	 */
	public void testNormalParsedLine() throws IOException {

		StringBuilder sb = new StringBuilder(CSVReader.INITIAL_READ_SIZE);

		sb.append("a,1234567,c").append("\n");// a,1234,c

		CSVReader c = new CSVReader(new StringReader(sb.toString()));

		String[] nextLine = c.readNext();
		assertEquals(3, nextLine.length);

		assertEquals("a", nextLine[0]);
		assertEquals("1234567", nextLine[1]);
		assertEquals("c", nextLine[2]);

	}

	/**
	 * Test a line where one of the elements is a single Double quote "
	 * @throws IOException
	 */
	public void testADoubleQuoteAsDataElement() throws IOException {

		StringBuilder sb = new StringBuilder(CSVReader.INITIAL_READ_SIZE);

		sb.append("a,\"\"\"\",c").append("\n");// a,"""",c

		CSVReader c = new CSVReader(new StringReader(sb.toString()));

		String[] nextLine = c.readNext();
		assertEquals(3, nextLine.length);
		
		assertEquals("a", nextLine[0]);
		assertEquals(1, nextLine[1].length());
		assertEquals("\"", nextLine[1]);
		assertEquals("c", nextLine[2]);

	}
	
	/**
	 * Test a line where one of the elements is a single Double quote "
	 * @throws IOException
	 */
	public void testEscapedDoubleQuoteAsDataElement() throws IOException {
		 
		StringBuilder sb = new StringBuilder(CSVReader.INITIAL_READ_SIZE);

		sb.append("\"test\",\"this,test,is,good\",\"\\\"test\\\"\",\"\\\"quote\\\"\"").append("\n"); // "test","this,test,is,good","\"test\",\"quote\""

		CSVReader c = new CSVReader(new StringReader(sb.toString()));

		String[] nextLine = c.readNext();
		assertEquals(4, nextLine.length);
		
		assertEquals("test", nextLine[0]);
		assertEquals("this,test,is,good", nextLine[1]);
		assertEquals("\"test\"", nextLine[2]);
		assertEquals("\"quote\"", nextLine[3]);

	}
	
	/**
	 * Same as testADoubleQuoteAsDataElement but I changed the quotechar to a 
	 * single quote. 
	 * @throws IOException
	 */
	public void testASingleQuoteAsDataElement() throws IOException {

		StringBuilder sb = new StringBuilder(CSVReader.INITIAL_READ_SIZE);

		sb.append("a,'''',c").append("\n");// a,',c

		CSVReader c = new CSVReader(new StringReader(sb.toString()), ',', '\'');

		String[] nextLine = c.readNext();
		assertEquals(3, nextLine.length);
		
		assertEquals("a", nextLine[0]);
		assertEquals(1, nextLine[1].length());
		assertEquals("\'", nextLine[1]);
		assertEquals("c", nextLine[2]);

	}
	
	/**
	 * Same as testADoubleQuoteAsDataElement but I changed the quotechar to a 
	 * single quote.  Also the middle field is empty. 
	 * @throws IOException
	 */
	public void testASingleQuoteAsDataElementWithEmptyField() throws IOException {

		StringBuilder sb = new StringBuilder(CSVReader.INITIAL_READ_SIZE);

		sb.append("a,'',c").append("\n");// a,,c

		CSVReader c = new CSVReader(new StringReader(sb.toString()), ',', '\'');

		String[] nextLine = c.readNext();
		assertEquals(3, nextLine.length);
		
		assertEquals("a", nextLine[0]);
		assertEquals(0, nextLine[1].length());
		assertEquals("", nextLine[1]);
		assertEquals("c", nextLine[2]);

	}
	
	/**
	 * Test issue 2263439 where an escaped quote was causing the parse to fail.  
	 * 
	 * Special thanks to Chris Morris for fixing this (id 1979054)
	 * @throws IOException 
	 * 
	 */
	public void testIssue2263439() throws IOException {
		
		StringBuilder sb = new StringBuilder(CSVReader.INITIAL_READ_SIZE);

		sb.append("865,0,'AmeriKKKa\\'s_Most_Wanted','',294,0,0,0.734338696798625,'20081002052147',242429208,18448").append("\n");

		CSVReader c = new CSVReader(new StringReader(sb.toString()), ',', '\'');

		String[] nextLine = c.readNext();

		assertEquals(11, nextLine.length);
		
		assertEquals("865", nextLine[0]);
		assertEquals("0", nextLine[1]);
		assertEquals("AmeriKKKa's_Most_Wanted", nextLine[2]);
		assertEquals("", nextLine[3]);
		assertEquals("18448", nextLine[10]);
		
	}
	
	public void testEscapedQuote() throws IOException {

		StringBuffer sb = new StringBuffer();

		sb.append("a,\"123\\\"4567\",c").append("\n");// a,123"4",c

		CSVReader c = new CSVReader(new StringReader(sb.toString()));

		String[] nextLine = c.readNext();
		assertEquals(3, nextLine.length);

		assertEquals("123\"4567", nextLine[1]);

	}

	public void testEscapedEscape() throws IOException {

		StringBuffer sb = new StringBuffer();

		sb.append("a,\"123\\\\4567\",c").append("\n");// a,123"4",c

		CSVReader c = new CSVReader(new StringReader(sb.toString()));

		String[] nextLine = c.readNext();
		assertEquals(3, nextLine.length);

		assertEquals("123\\4567", nextLine[1]);

	}

	
	/**
	 * Test a line where one of the elements is two single quotes and the 
	 * quote character is the default double quote.  The expected result is two 
	 * single quotes. 
	 * @throws IOException
	 */
	public void testSingleQuoteWhenDoubleQuoteIsQuoteChar() throws IOException {

		StringBuilder sb = new StringBuilder(CSVReader.INITIAL_READ_SIZE);

		sb.append("a,'',c").append("\n");// a,'',c

		CSVReader c = new CSVReader(new StringReader(sb.toString()));

		String[] nextLine = c.readNext();
		assertEquals(3, nextLine.length);

		assertEquals("a", nextLine[0]);
		assertEquals(2, nextLine[1].length());
		assertEquals("''", nextLine[1]);
		assertEquals("c", nextLine[2]);

	}
	
	/**
	 * Test a normal line with three elements and all elements are quoted
	 * @throws IOException
	 */
	public void testQuotedParsedLine() throws IOException {

		StringBuilder sb = new StringBuilder(CSVReader.INITIAL_READ_SIZE);

		sb.append("\"a\",\"1234567\",\"c\"").append("\n"); // "a","1234567","c"

		CSVReader c = new CSVReader(new StringReader(sb.toString()));

		String[] nextLine = c.readNext();
		assertEquals(3, nextLine.length);

		assertEquals("a", nextLine[0]);
		assertEquals(1, nextLine[0].length());
		
		assertEquals("1234567", nextLine[1]);
		assertEquals("c", nextLine[2]);

	}
	
	/**
	 * Test issue 2726363
	 * 
	 * Data given:
	 * 
	 *	"804503689","London",""London""shop","address","116.453182","39.918884"
	 *	"453074125","NewYork","brief","address"","121.514683","31.228511"
	 */
	
	public void testIssue2726363()throws IOException {

		StringBuilder sb = new StringBuilder(CSVReader.INITIAL_READ_SIZE);

		sb.append("\"804503689\",\"London\",\"\"London\"shop\",\"address\",\"116.453182\",\"39.918884\"").append("\n"); 
	
		CSVReader c = new CSVReader(new StringReader(sb.toString()));

		String[] nextLine = c.readNext();
		assertEquals(6, nextLine.length);


		assertEquals("804503689", nextLine[0]);
		assertEquals("London", nextLine[1]);
		assertEquals("\"London\"shop", nextLine[2]);
		assertEquals("address", nextLine[3]);
		assertEquals("116.453182", nextLine[4]);
		assertEquals("39.918884", nextLine[5]);

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
