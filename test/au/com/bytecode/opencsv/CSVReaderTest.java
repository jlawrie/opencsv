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

import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.io.StringReader;

import static org.junit.Assert.*;

public class CSVReaderTest {

    CSVReader csvr;


    /**
     * Setup the test.
     */
    @Before
    public void setUp() throws Exception {
        StringBuilder sb = new StringBuilder(CSVParser.INITIAL_READ_SIZE);
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
     * @throws IOException if the reader fails.
     */
    @Test
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
        assertEquals("\"\"", nextLine[0]); // check the tricky situation
        assertEquals("test", nextLine[1]); // make sure we didn't ruin the next field..

        nextLine = csvr.readNext();
        assertEquals(4, nextLine.length);

        //test end of stream
        assertNull(csvr.readNext());

    }

    @Test
    public void testParseLineStrictQuote() throws IOException {
        StringBuilder sb = new StringBuilder(CSVParser.INITIAL_READ_SIZE);
        sb.append("a,b,c").append("\n");   // standard case
        sb.append("a,\"b,b,b\",c").append("\n");  // quoted elements
        sb.append(",,").append("\n"); // empty elements
        sb.append("a,\"PO Box 123,\nKippax,ACT. 2615.\nAustralia\",d.\n");
        sb.append("\"Glen \"\"The Man\"\" Smith\",Athlete,Developer\n"); // Test quoted quote chars
        sb.append("\"\"\"\"\"\",\"test\"\n"); // """""","test"  representing:  "", test
        sb.append("\"a\nb\",b,\"\nd\",e\n");
        csvr = new CSVReader(new StringReader(sb.toString()), ',', '\"', true);

        // test normal case
        String[] nextLine = csvr.readNext();
        assertEquals("", nextLine[0]);
        assertEquals("", nextLine[1]);
        assertEquals("", nextLine[2]);

        // test quoted commas
        nextLine = csvr.readNext();
        assertEquals("", nextLine[0]);
        assertEquals("b,b,b", nextLine[1]);
        assertEquals("", nextLine[2]);

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
        assertEquals("a\nb", nextLine[0]);
        assertEquals("", nextLine[1]);
        assertEquals("\nd", nextLine[2]);
        assertEquals("", nextLine[3]);

        //test end of stream
        assertNull(csvr.readNext());
    }


    /**
     * Test parsing to a list.
     *
     * @throws IOException if the reader fails.
     */
    @Test
    public void testParseAll() throws IOException {
        assertEquals(7, csvr.readAll().size());
    }

    /**
     * Tests constructors with optional delimiters and optional quote char.
     *
     * @throws IOException if the reader fails.
     */
    @Test
    public void testOptionalConstructors() throws IOException {

        StringBuilder sb = new StringBuilder(CSVParser.INITIAL_READ_SIZE);
        sb.append("a\tb\tc").append("\n");   // tab separated case
        sb.append("a\t'b\tb\tb'\tc").append("\n");  // single quoted elements
        CSVReader c = new CSVReader(new StringReader(sb.toString()), '\t', '\'');

        String[] nextLine = c.readNext();
        assertEquals(3, nextLine.length);

        nextLine = c.readNext();
        assertEquals(3, nextLine.length);
    }

    @Test
    public void parseQuotedStringWithDefinedSeperator() throws IOException {
        StringBuilder sb = new StringBuilder(CSVParser.INITIAL_READ_SIZE);
        sb.append("a\tb\tc").append("\n");   // tab separated case

        CSVReader c = new CSVReader(new StringReader(sb.toString()), '\t');

        String[] nextLine = c.readNext();
        assertEquals(3, nextLine.length);

    }

    /**
     * Tests option to skip the first few lines of a file.
     *
     * @throws IOException if bad things happen
     */
    @Test
    public void testSkippingLines() throws IOException {

        StringBuilder sb = new StringBuilder(CSVParser.INITIAL_READ_SIZE);
        sb.append("Skip this line\t with tab").append("\n");   // should skip this
        sb.append("And this line too").append("\n");   // and this
        sb.append("a\t'b\tb\tb'\tc").append("\n");  // single quoted elements
        CSVReader c = new CSVReader(new StringReader(sb.toString()), '\t', '\'', 2);

        String[] nextLine = c.readNext();
        assertEquals(3, nextLine.length);

        assertEquals("a", nextLine[0]);
    }


    /**
     * Tests option to skip the first few lines of a file.
     *
     * @throws IOException if bad things happen
     */
    @Test
    public void testSkippingLinesWithDifferentEscape() throws IOException {

        StringBuilder sb = new StringBuilder(CSVParser.INITIAL_READ_SIZE);
        sb.append("Skip this line?t with tab").append("\n");   // should skip this
        sb.append("And this line too").append("\n");   // and this
        sb.append("a\t'b\tb\tb'\t'c'").append("\n");  // single quoted elements
        CSVReader c = new CSVReader(new StringReader(sb.toString()), '\t', '\'', '?', 2);

        String[] nextLine = c.readNext();

        assertEquals(3, nextLine.length);

        assertEquals("a", nextLine[0]);
        assertEquals("c", nextLine[2]);
    }

    /**
     * Test a normal non quoted line with three elements
     *
     * @throws IOException
     */
    @Test
    public void testNormalParsedLine() throws IOException {

        StringBuilder sb = new StringBuilder(CSVParser.INITIAL_READ_SIZE);

        sb.append("a,1234567,c").append("\n");// a,1234,c

        CSVReader c = new CSVReader(new StringReader(sb.toString()));

        String[] nextLine = c.readNext();
        assertEquals(3, nextLine.length);

        assertEquals("a", nextLine[0]);
        assertEquals("1234567", nextLine[1]);
        assertEquals("c", nextLine[2]);

    }


    /**
     * Same as testADoubleQuoteAsDataElement but I changed the quotechar to a
     * single quote.
     *
     * @throws IOException
     */
    @Test
    public void testASingleQuoteAsDataElement() throws IOException {

        StringBuilder sb = new StringBuilder(CSVParser.INITIAL_READ_SIZE);

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
     *
     * @throws IOException
     */
    @Test
    public void testASingleQuoteAsDataElementWithEmptyField() throws IOException {

        StringBuilder sb = new StringBuilder(CSVParser.INITIAL_READ_SIZE);

        sb.append("a,'',c").append("\n");// a,,c

        CSVReader c = new CSVReader(new StringReader(sb.toString()), ',', '\'');

        String[] nextLine = c.readNext();
        assertEquals(3, nextLine.length);

        assertEquals("a", nextLine[0]);
        assertEquals(0, nextLine[1].length());
        assertEquals("", nextLine[1]);
        assertEquals("c", nextLine[2]);

    }

    @Test
    public void testSpacesAtEndOfString() throws IOException {
        StringBuilder sb = new StringBuilder(CSVParser.INITIAL_READ_SIZE);

        sb.append("\"a\",\"b\",\"c\"   ");

        CSVReader c = new CSVReader(new StringReader(sb.toString()), CSVParser.DEFAULT_SEPARATOR, CSVParser.DEFAULT_QUOTE_CHARACTER, true);

        String[] nextLine = c.readNext();
        assertEquals(3, nextLine.length);

        assertEquals("a", nextLine[0]);
        assertEquals("b", nextLine[1]);
        assertEquals("c", nextLine[2]);
    }


    @Test
    public void testEscapedQuote() throws IOException {

        StringBuffer sb = new StringBuffer();

        sb.append("a,\"123\\\"4567\",c").append("\n");// a,123"4",c

        CSVReader c = new CSVReader(new StringReader(sb.toString()));

        String[] nextLine = c.readNext();
        assertEquals(3, nextLine.length);

        assertEquals("123\"4567", nextLine[1]);

    }

    @Test
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
     *
     * @throws IOException
     */
    @Test
    public void testSingleQuoteWhenDoubleQuoteIsQuoteChar() throws IOException {

        StringBuilder sb = new StringBuilder(CSVParser.INITIAL_READ_SIZE);

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
     *
     * @throws IOException
     */
    @Test
    public void testQuotedParsedLine() throws IOException {

        StringBuilder sb = new StringBuilder(CSVParser.INITIAL_READ_SIZE);

        sb.append("\"a\",\"1234567\",\"c\"").append("\n"); // "a","1234567","c"

        CSVReader c = new CSVReader(new StringReader(sb.toString()), CSVParser.DEFAULT_SEPARATOR, CSVParser.DEFAULT_QUOTE_CHARACTER, true);

        String[] nextLine = c.readNext();
        assertEquals(3, nextLine.length);

        assertEquals("a", nextLine[0]);
        assertEquals(1, nextLine[0].length());

        assertEquals("1234567", nextLine[1]);
        assertEquals("c", nextLine[2]);

    }

    @Test
    public void testIssue2992134OutOfPlaceQuotes() throws IOException {
        StringBuilder sb = new StringBuilder(CSVParser.INITIAL_READ_SIZE);

        sb.append("a,b,c,ddd\\\"eee\nf,g,h,\"iii,jjj\"");

        CSVReader c = new CSVReader(new StringReader(sb.toString()));

        String[] nextLine = c.readNext();

        assertEquals("a", nextLine[0]);
        assertEquals("b", nextLine[1]);
        assertEquals("c", nextLine[2]);
        assertEquals("ddd\"eee", nextLine[3]);
    }

    @Test(expected = UnsupportedOperationException.class)
    public void quoteAndEscapeMustBeDifferent() {
        StringBuilder sb = new StringBuilder(CSVParser.INITIAL_READ_SIZE);

        sb.append("a,b,c,ddd\\\"eee\nf,g,h,\"iii,jjj\"");

        CSVReader c = new CSVReader(new StringReader(sb.toString()), CSVParser.DEFAULT_SEPARATOR, CSVParser.DEFAULT_QUOTE_CHARACTER, CSVParser.DEFAULT_QUOTE_CHARACTER, CSVReader.DEFAULT_SKIP_LINES, CSVParser.DEFAULT_STRICT_QUOTES, CSVParser.DEFAULT_IGNORE_LEADING_WHITESPACE);
    }

    @Test(expected = UnsupportedOperationException.class)
    public void separatorAndEscapeMustBeDifferent() {
        StringBuilder sb = new StringBuilder(CSVParser.INITIAL_READ_SIZE);

        sb.append("a,b,c,ddd\\\"eee\nf,g,h,\"iii,jjj\"");

        CSVReader c = new CSVReader(new StringReader(sb.toString()), CSVParser.DEFAULT_SEPARATOR, CSVParser.DEFAULT_QUOTE_CHARACTER, CSVParser.DEFAULT_SEPARATOR, CSVReader.DEFAULT_SKIP_LINES, CSVParser.DEFAULT_STRICT_QUOTES, CSVParser.DEFAULT_IGNORE_LEADING_WHITESPACE);
    }

    @Test(expected = UnsupportedOperationException.class)
    public void separatorAndQuoteMustBeDifferent() {
        StringBuilder sb = new StringBuilder(CSVParser.INITIAL_READ_SIZE);

        sb.append("a,b,c,ddd\\\"eee\nf,g,h,\"iii,jjj\"");

        CSVReader c = new CSVReader(new StringReader(sb.toString()), CSVParser.DEFAULT_SEPARATOR, CSVParser.DEFAULT_SEPARATOR, CSVParser.DEFAULT_ESCAPE_CHARACTER, CSVReader.DEFAULT_SKIP_LINES, CSVParser.DEFAULT_STRICT_QUOTES, CSVParser.DEFAULT_IGNORE_LEADING_WHITESPACE);
    }

}
