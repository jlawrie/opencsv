package au.com.bytecode.opencsv;
/**
 * Created by IntelliJ IDEA.
 * User: Scott Conway
 * Date: Oct 7, 2009
 * Time: 9:56:48 PM
 */

import org.junit.Before;
import org.junit.Test;

import java.io.IOException;

import static org.junit.Assert.*;

public class CSVParserTest {

    CSVParser csvParser;

    @Before
    public void setUp() {
        csvParser = new CSVParser();
    }

    @Test
    public void testParseLine() throws Exception {
        String nextItem[] = csvParser.parseLine("This, is, a, test.");
        assertEquals(4, nextItem.length);
        assertEquals("This", nextItem[0]);
        assertEquals(" is", nextItem[1]);
        assertEquals(" a", nextItem[2]);
        assertEquals(" test.", nextItem[3]);
    }


    @Test
    public void parseSimpleString() throws IOException {

        String[] nextLine = csvParser.parseLine("a,b,c");
        assertEquals(3, nextLine.length);
        assertEquals("a", nextLine[0]);
        assertEquals("b", nextLine[1]);
        assertEquals("c", nextLine[2]);
        assertFalse(csvParser.isPending());
    }

    /**
     * Tests quotes in the middle of an element.
     *
     * @throws IOException if bad things happen
     */
    @Test
    public void testParsedLineWithInternalQuota() throws IOException {

        String[] nextLine = csvParser.parseLine("a,123\"4\"567,c");
        assertEquals(3, nextLine.length);

        assertEquals("123\"4\"567", nextLine[1]);

    }

    @Test
    public void parseQuotedStringWithCommas() throws IOException {
        String[] nextLine = csvParser.parseLine("a,\"b,b,b\",c");
        assertEquals("a", nextLine[0]);
        assertEquals("b,b,b", nextLine[1]);
        assertEquals("c", nextLine[2]);
        assertEquals(3, nextLine.length);
    }

    @Test
    public void parseQuotedStringWithDefinedSeperator() throws IOException {
        csvParser = new CSVParser(':');

        String[] nextLine = csvParser.parseLine("a:\"b:b:b\":c");
        assertEquals("a", nextLine[0]);
        assertEquals("b:b:b", nextLine[1]);
        assertEquals("c", nextLine[2]);
        assertEquals(3, nextLine.length);
    }

    @Test
    public void parseQuotedStringWithDefinedSeperatorAndQuote() throws IOException {
        csvParser = new CSVParser(':', '\'');

        String[] nextLine = csvParser.parseLine("a:'b:b:b':c");
        assertEquals("a", nextLine[0]);
        assertEquals("b:b:b", nextLine[1]);
        assertEquals("c", nextLine[2]);
        assertEquals(3, nextLine.length);
    }

    @Test
    public void parseEmptyElements() throws IOException {
        String[] nextLine = csvParser.parseLine(",,");
        assertEquals(3, nextLine.length);
        assertEquals("", nextLine[0]);
        assertEquals("", nextLine[1]);
        assertEquals("", nextLine[2]);
    }

    @Test
    public void parseMultiLinedQuoted() throws IOException {
        String[] nextLine = csvParser.parseLine("a,\"PO Box 123,\nKippax,ACT. 2615.\nAustralia\",d.\n");
        assertEquals(3, nextLine.length);
        assertEquals("a", nextLine[0]);
        assertEquals("PO Box 123,\nKippax,ACT. 2615.\nAustralia", nextLine[1]);
        assertEquals("d.\n", nextLine[2]);
    }

    @Test
    public void testADoubleQuoteAsDataElement() throws IOException {

        String[] nextLine = csvParser.parseLine("a,\"\"\"\",c");// a,"""",c

        assertEquals(3, nextLine.length);

        assertEquals("a", nextLine[0]);
        assertEquals(1, nextLine[1].length());
        assertEquals("\"", nextLine[1]);
        assertEquals("c", nextLine[2]);

    }

    @Test
    public void testEscapedDoubleQuoteAsDataElement() throws IOException {

        String[] nextLine = csvParser.parseLine("\"test\",\"this,test,is,good\",\"\\\"test\\\"\",\"\\\"quote\\\"\""); // "test","this,test,is,good","\"test\",\"quote\""

        assertEquals(4, nextLine.length);

        assertEquals("test", nextLine[0]);
        assertEquals("this,test,is,good", nextLine[1]);
        assertEquals("\"test\"", nextLine[2]);
        assertEquals("\"quote\"", nextLine[3]);

    }

//    @Test
//    public void testEscapingSeparator() throws IOException {
//        String[] nextLine = csvParser.parseLine("test,this\\,test\\,is\\,good"); // "test","this,test,is,good","\"test\",\"quote\""
//
//        assertEquals(2, nextLine.length);
//
//        assertEquals("test", nextLine[0]);
//        assertEquals("this,test,is,good", nextLine[1]);
//    }

    @Test
    public void parseQuotedQuoteCharacters() throws IOException {
        String[] nextLine = csvParser.parseLineMulti("\"Glen \"\"The Man\"\" Smith\",Athlete,Developer\n");
        assertEquals(3, nextLine.length);
        assertEquals("Glen \"The Man\" Smith", nextLine[0]);
        assertEquals("Athlete", nextLine[1]);
        assertEquals("Developer\n", nextLine[2]);
    }

    @Test
    public void parseMultipleQuotes() throws IOException {
        String[] nextLine = csvParser.parseLine("\"\"\"\"\"\",\"test\"\n"); // """""","test"  representing:  "", test
        assertEquals("\"\"", nextLine[0]); // check the tricky situation
        assertEquals("test\"\n", nextLine[1]); // make sure we didn't ruin the next field..
        assertEquals(2, nextLine.length);
    }

    @Test
    public void parseTrickyString() throws IOException {
        String[] nextLine = csvParser.parseLine("\"a\nb\",b,\"\nd\",e\n");
        assertEquals(4, nextLine.length);
        assertEquals("a\nb", nextLine[0]);
        assertEquals("b", nextLine[1]);
        assertEquals("\nd", nextLine[2]);
        assertEquals("e\n", nextLine[3]);
    }

    private String setUpMultiLineInsideQuotes() {
        StringBuffer sb = new StringBuffer(CSVParser.INITIAL_READ_SIZE);

        sb.append("Small test,\"This is a test across \ntwo lines.\"");

        return sb.toString();
    }

    @Test
    public void testAMultiLineInsideQuotes() throws IOException {

        String testString = setUpMultiLineInsideQuotes();

        String[] nextLine = csvParser.parseLine(testString);
        assertEquals(2, nextLine.length);
        assertEquals("Small test", nextLine[0]);
        assertEquals("This is a test across \ntwo lines.", nextLine[1]);
        assertFalse(csvParser.isPending());
    }

    @Test
    public void testStrictQuoteSimple() throws IOException {
        csvParser = new CSVParser(',', '\"', '\\', true);
        String testString = "\"a\",\"b\",\"c\"";

        String[] nextLine = csvParser.parseLine(testString);
        assertEquals(3, nextLine.length);
        assertEquals("a", nextLine[0]);
        assertEquals("b", nextLine[1]);
        assertEquals("c", nextLine[2]);
    }

    @Test
    public void testStrictQuoteWithSpacesAndTabs() throws IOException {
        csvParser = new CSVParser(',', '\"', '\\', true);
        String testString = " \t      \"a\",\"b\"      \t       ,   \"c\"   ";

        String[] nextLine = csvParser.parseLine(testString);
        assertEquals(3, nextLine.length);
        assertEquals("a", nextLine[0]);
        assertEquals("b", nextLine[1]);
        assertEquals("c", nextLine[2]);
    }

    @Test
    public void testStrictQuoteWithGarbage() throws IOException {
        csvParser = new CSVParser(',', '\"', '\\', true);
        String testString = "abc',!@#\",\\\"\"   xyz,";

        String[] nextLine = csvParser.parseLine(testString);
        assertEquals(3, nextLine.length);
        assertEquals("", nextLine[0]);
        assertEquals(",\"", nextLine[1]);
        assertEquals("", nextLine[2]);
    }

    /**
     * Test issue 2263439 where an escaped quote was causing the parse to fail.
     * <p/>
     * Special thanks to Chris Morris for fixing this (id 1979054)
     *
     * @throws IOException
     */
    @Test
    public void testIssue2263439() throws IOException {
        csvParser = new CSVParser(',', '\'');

        String[] nextLine = csvParser.parseLine("865,0,'AmeriKKKa\\'s_Most_Wanted','',294,0,0,0.734338696798625,'20081002052147',242429208,18448");

        assertEquals(11, nextLine.length);

        assertEquals("865", nextLine[0]);
        assertEquals("0", nextLine[1]);
        assertEquals("AmeriKKKa's_Most_Wanted", nextLine[2]);
        assertEquals("", nextLine[3]);
        assertEquals("18448", nextLine[10]);

    }

    /**
     * Test issue 2859181 where an escaped character before a character
     * that did not need escaping was causing the parse to fail.
     *
     * @throws IOException
     */
    @Test
    public void testIssue2859181() throws IOException {
        csvParser = new CSVParser(';');
        String[] nextLine = csvParser.parseLine("field1;\\=field2;\"\"\"field3\"\"\""); // field1;\=field2;"""field3"""

        assertEquals(3, nextLine.length);

        assertEquals("field1", nextLine[0]);
        assertEquals("=field2", nextLine[1]);
        assertEquals("\"field3\"", nextLine[2]);

    }

    /**
     * Test issue 2726363
     * <p/>
     * Data given:
     * <p/>
     * "804503689","London",""London""shop","address","116.453182","39.918884"
     * "453074125","NewYork","brief","address"","121.514683","31.228511"
     */
    @Test
    public void testIssue2726363() throws IOException {

        String[] nextLine = csvParser.parseLine("\"804503689\",\"London\",\"\"London\"shop\",\"address\",\"116.453182\",\"39.918884\"");

        assertEquals(6, nextLine.length);


        assertEquals("804503689", nextLine[0]);
        assertEquals("London", nextLine[1]);
        assertEquals("\"London\"shop", nextLine[2]);
        assertEquals("address", nextLine[3]);
        assertEquals("116.453182", nextLine[4]);
        assertEquals("39.918884", nextLine[5]);

    }

    @Test(expected = IOException.class)
    public void anIOExceptionThrownifStringEndsInsideAQuotedString() throws IOException {
        String[] nextLine = csvParser.parseLine("This,is a \"bad line to parse.");


    }

    @Test
    public void parseLineMultiAllowsQuotesAcrossMultipleLines() throws IOException {
        String[] nextLine = csvParser.parseLineMulti("This,\"is a \"good\" line\\\\ to parse");

        assertEquals(1, nextLine.length);
        assertEquals("This", nextLine[0]);
        assertTrue(csvParser.isPending());

        nextLine = csvParser.parseLineMulti("because we are using parseLineMulti.\"");

        assertEquals(1, nextLine.length);
        assertEquals("is a \"good\" line\\ to parse\nbecause we are using parseLineMulti.", nextLine[0]);
        assertFalse(csvParser.isPending());
    }

    @Test
    public void pendingIsClearedAfterCallToParseLine() throws IOException {
        String[] nextLine = csvParser.parseLineMulti("This,\"is a \"good\" line\\\\ to parse");

        assertEquals(1, nextLine.length);
        assertEquals("This", nextLine[0]);
        assertTrue(csvParser.isPending());

        nextLine = csvParser.parseLine("because we are using parseLineMulti.");

        assertEquals(1, nextLine.length);
        assertEquals("because we are using parseLineMulti.", nextLine[0]);
        assertFalse(csvParser.isPending());
    }

    @Test
    public void returnPendingIfNullIsPassedIntoParseLineMulti() throws IOException {
        String[] nextLine = csvParser.parseLineMulti("This,\"is a \"goo\\d\" line\\\\ to parse\\");

        assertEquals(1, nextLine.length);
        assertEquals("This", nextLine[0]);
        assertTrue(csvParser.isPending());

        nextLine = csvParser.parseLineMulti(null);

        assertEquals(1, nextLine.length);
        assertEquals("is a \"good\" line\\ to parse\n", nextLine[0]);
        assertFalse(csvParser.isPending());
    }

    @Test
    public void spacesAtEndOfQuotedStringDoNotCountIfStrictQuotesIsTrue() throws IOException {
        CSVParser parser = new CSVParser(CSVParser.DEFAULT_SEPARATOR, CSVParser.DEFAULT_QUOTE_CHARACTER, CSVParser.DEFAULT_ESCAPE_CHARACTER, true);
        String[] nextLine = parser.parseLine("\"Line with\", \"spaces at end\"  ");

        assertEquals(2, nextLine.length);
        assertEquals("Line with", nextLine[0]);
        assertEquals("spaces at end", nextLine[1]);
    }

    @Test
    public void returnNullWhenNullPassedIn() throws IOException {
        String[] nextLine = csvParser.parseLine(null);
        assertNull(nextLine);
    }

    private static final String ESCAPE_TEST_STRING = "\\\\1\\2\\\"3\\"; // \\1\2\"\

    @Test
    public void validateEscapeStringBeforeRealTest() {
        assertNotNull(ESCAPE_TEST_STRING);
        assertEquals(9, ESCAPE_TEST_STRING.length());
    }

    @Test
    public void whichCharactersAreEscapable() {
        assertTrue(csvParser.isNextCharacterEscapable(ESCAPE_TEST_STRING, true, 0));
        assertFalse(csvParser.isNextCharacterEscapable(ESCAPE_TEST_STRING, false, 0));
        // Second character is not escapable because there is a non quote or non slash after it. 
        assertFalse(csvParser.isNextCharacterEscapable(ESCAPE_TEST_STRING, true, 1));
        assertFalse(csvParser.isNextCharacterEscapable(ESCAPE_TEST_STRING, false, 1));
        // Fourth character is not escapable because there is a non quote or non slash after it.
        assertFalse(csvParser.isNextCharacterEscapable(ESCAPE_TEST_STRING, true, 3));
        assertFalse(csvParser.isNextCharacterEscapable(ESCAPE_TEST_STRING, false, 3));

        assertTrue(csvParser.isNextCharacterEscapable(ESCAPE_TEST_STRING, true, 5));
        assertFalse(csvParser.isNextCharacterEscapable(ESCAPE_TEST_STRING, false, 5));

        int lastChar = ESCAPE_TEST_STRING.length() - 1;
        assertFalse(csvParser.isNextCharacterEscapable(ESCAPE_TEST_STRING, true, lastChar));
        assertFalse(csvParser.isNextCharacterEscapable(ESCAPE_TEST_STRING, false, lastChar));

    }


    @Test
    public void whitespaceBeforeEscape() throws IOException {
        String[] nextItem = csvParser.parseLine("\"this\", \"is\",\"a test\""); //"this", "is","a test"
        assertEquals("this", nextItem[0]);
        assertEquals("is", nextItem[1]);
        assertEquals("a test", nextItem[2]);
    }

    @Test
    public void testIssue2958242WithoutQuotes() throws IOException {
        CSVParser testParser = new CSVParser('\t');
        String[] nextItem = testParser.parseLine("zo\"\"har\"\"at\t10-04-1980\t29\tC:\\\\foo.txt");
        assertEquals(4, nextItem.length);
        assertEquals("zo\"har\"at", nextItem[0]);
        assertEquals("10-04-1980", nextItem[1]);
        assertEquals("29", nextItem[2]);
        assertEquals("C:\\foo.txt", nextItem[3]);
    }

    @Test(expected = UnsupportedOperationException.class)
    public void quoteAndEscapeCannotBeTheSame() {
        CSVParser p = new CSVParser(CSVParser.DEFAULT_SEPARATOR, CSVParser.DEFAULT_QUOTE_CHARACTER, CSVParser.DEFAULT_QUOTE_CHARACTER);
    }

    @Test
    public void quoteAndEscapeCanBeTheSameIfNull() {
        CSVParser p = new CSVParser(CSVParser.DEFAULT_SEPARATOR, CSVParser.NULL_CHARACTER, CSVParser.NULL_CHARACTER);
    }

    @Test(expected = UnsupportedOperationException.class)
    public void separatorCharacterCannotBeNull() {
        CSVParser p = new CSVParser(CSVParser.NULL_CHARACTER);
    }

    @Test(expected = UnsupportedOperationException.class)
    public void separatorAndEscapeCannotBeTheSame() {
        CSVParser p = new CSVParser(CSVParser.DEFAULT_SEPARATOR, CSVParser.DEFAULT_QUOTE_CHARACTER, CSVParser.DEFAULT_SEPARATOR);
    }

    @Test(expected = UnsupportedOperationException.class)
    public void separatorAndQuoteCannotBeTheSame() {
        CSVParser p = new CSVParser(CSVParser.DEFAULT_SEPARATOR, CSVParser.DEFAULT_SEPARATOR, CSVParser.DEFAULT_ESCAPE_CHARACTER);
    }

}