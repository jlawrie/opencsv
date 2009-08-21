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

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import junit.framework.TestCase;

public class CSVWriterTest extends TestCase {


    /**
     * Test routine for converting output to a string.
     *
     * @param args
     *            the elements of a line of the cvs file
     * @return a String version
     * @throws IOException
     *             if there are problems writing
     */
    private String invokeWriter(String[] args) throws IOException {
        StringWriter sw = new StringWriter();
        CSVWriter csvw = new CSVWriter(sw,',','\'');
        csvw.writeNext(args);
        return sw.toString();
    }
    

    /**
     * Tests parsing individual lines.
     *
     * @throws IOException
     *             if the reader fails.
     */
    @Test
    public void testParseLine() throws IOException {

        // test normal case
        String[] normal = { "a", "b", "c" };
        String output = invokeWriter(normal);
        assertEquals("'a','b','c'\n", output);

        // test quoted commas
        String[] quoted = { "a", "b,b,b", "c" };
        output = invokeWriter(quoted);
        assertEquals("'a','b,b,b','c'\n", output);

        // test empty elements
        String[] empty = { , };
        output = invokeWriter(empty);
        assertEquals("\n", output);

        // test multiline quoted
        String[] multiline = { "This is a \n multiline entry", "so is \n this" };
        output = invokeWriter(multiline);
        assertEquals("'This is a \n multiline entry','so is \n this'\n", output);

    }

    /**
     * Test parsing from to a list.
     *
     * @throws IOException
     *             if the reader fails.
     */
    @Test
    public void testParseAll() throws IOException {

        List<String[]> allElements = new ArrayList<String[]>();
        String[] line1 = "Name#Phone#Email".split("#");
        String[] line2 = "Glen#1234#glen@abcd.com".split("#");
        String[] line3 = "John#5678#john@efgh.com".split("#");
        allElements.add(line1);
        allElements.add(line2);
        allElements.add(line3);

        StringWriter sw = new StringWriter();
        CSVWriter csvw = new CSVWriter(sw);
        csvw.writeAll(allElements);

        String result = sw.toString();
        String[] lines = result.split("\n");

        assertEquals(3, lines.length);

    }

    /**
     * Tests the option of having omitting quotes in the output stream.
     * 
     * @throws IOException if bad things happen
     */
    @Test
    public void testNoQuoteChars() throws IOException {
    	
        String[] line = {"Foo","Bar","Baz"};
        StringWriter sw = new StringWriter();
        CSVWriter csvw = new CSVWriter(sw, CSVWriter.DEFAULT_SEPARATOR, CSVWriter.NO_QUOTE_CHARACTER);
        csvw.writeNext(line);
        String result = sw.toString();

        assertEquals("Foo,Bar,Baz\n",result);
    }
    
    /**
     * Test null values.
     *
     * @throws IOException if bad things happen
     */
    @Test
    public void testNullValues() throws IOException {

        String[] line = {"Foo",null,"Bar","baz"};
        StringWriter sw = new StringWriter();
        CSVWriter csvw = new CSVWriter(sw);
        csvw.writeNext(line);
        String result = sw.toString();

        assertEquals("\"Foo\",,\"Bar\",\"baz\"\n",result);

    }
    
    @Test
    public void testStreamFlushing() throws IOException {

        String WRITE_FILE = "myfile.csv";

        String[] nextLine = new String[]{"aaaa", "bbbb","cccc","dddd"};

        FileWriter fileWriter = new FileWriter(WRITE_FILE);
        CSVWriter writer = new CSVWriter(fileWriter);

        writer.writeNext(nextLine);

        // If this line is not executed, it is not written in the file.
        writer.close();

    }
    
    @Test
    public void testAlternateEscapeChar() {
        String[] line = {"Foo","bar's"};
        StringWriter sw = new StringWriter();
        CSVWriter csvw = new CSVWriter(sw,CSVWriter.DEFAULT_SEPARATOR,CSVWriter.DEFAULT_QUOTE_CHARACTER,'\'');
        csvw.writeNext(line);
        assertEquals("\"Foo\",\"bar''s\"\n",sw.toString());
    }
    
    @Test
    public void testNoQuotingNoEscaping() {
        String[] line = {"\"Foo\",\"Bar\""};
        StringWriter sw = new StringWriter();
        CSVWriter csvw = new CSVWriter(sw,CSVWriter.DEFAULT_SEPARATOR,CSVWriter.NO_QUOTE_CHARACTER,CSVWriter.NO_ESCAPE_CHARACTER);
        csvw.writeNext(line);
        assertEquals("\"Foo\",\"Bar\"\n",sw.toString());
    }
    
    @Test
    public void testNestedQuotes(){
        String[] data = new String[]{"\"\"", "test"};
        String oracle = new String("\"\"\"\"\"\",\"test\"\n");

        CSVWriter writer=null;
        File tempFile=null;
        FileWriter fwriter=null;

        try{
            tempFile = File.createTempFile("csvWriterTest", ".csv");
            tempFile.deleteOnExit();
            fwriter = new FileWriter(tempFile);
            writer = new CSVWriter(fwriter);
        }catch(IOException e){
            fail();
        }

        // write the test data:
        writer.writeNext(data);

        try{
            writer.close();
        }catch(IOException e){
            fail();
        }

        try{
            // assert that the writer was also closed.
            fwriter.flush();
            fail();
        }catch(IOException e){
            // we should go through here..
        }

        // read the data and compare.
        FileReader in=null;
        try{
            in = new FileReader(tempFile);
        }catch(FileNotFoundException e){
            fail();
        }

        StringBuilder fileContents = new StringBuilder(CSVWriter.INITIAL_STRING_SIZE);
        try{
            int ch;
            while((ch = in.read()) != -1){
                fileContents.append((char)ch);
            }
            in.close();
        }catch(IOException e){
            fail();
        }

        assertTrue(oracle.equals(fileContents.toString()));
    }

    @Test
    public void testAlternateLineFeeds() {
        String[] line = {"Foo","Bar","baz"};
        StringWriter sw = new StringWriter();
        CSVWriter csvw = new CSVWriter(sw, CSVWriter.DEFAULT_SEPARATOR, CSVWriter.DEFAULT_QUOTE_CHARACTER, "\r");
        csvw.writeNext(line);
        String result = sw.toString();
        
        assertTrue(result.endsWith("\r"));
    	
    }

    /**
     * The Test Runner for commandline use.
     *
     * @param args
     *            no args required
     */
    public static void main(String args[]) {
        junit.textui.TestRunner.run(CSVWriterTest.class);
    }

}
