package au.com.bytecode.opencsv;

import org.junit.Test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;
import java.util.List;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;

public class UniCodeTest {
    CSVParser csvParser;
    private static final String COMPOUND_STRING = "??,??";
    private static final String COMPOUND_STRING_WITH_QUOTES = "\"??\",\"??\"";
    private static final String FIRST_STRING = "??";
    private static final String SECOND_STRING = "??";
    private static final String[] UNICODE_ARRAY = {FIRST_STRING, SECOND_STRING};
    private static final String[] MIXED_ARRAY = {"eins, 1", "ichi",FIRST_STRING, SECOND_STRING};
    private static final String[] ASCII_ARRAY = {"foo", "bar"};
    private static final String ASCII_STRING_WITH_QUOTES = "\"foo\",\"bar\"";

    @Test
    public void canParseUnicode() throws IOException {
        csvParser = new CSVParser();
        String simpleString = COMPOUND_STRING;
        String[] items = csvParser.parseLine(simpleString);
        assertEquals(2, items.length);
        assertEquals(FIRST_STRING, items[0]);
        assertEquals(SECOND_STRING, items[1]);
        assertArrayEquals(UNICODE_ARRAY, items);
    }

    @Test
    public void readerTest() throws IOException {
        BufferedReader reader = new BufferedReader(new StringReader(FIRST_STRING));
        String testString = reader.readLine();
        assertEquals(FIRST_STRING, testString);
    }

    @Test
    public void writerTest(){
        StringWriter sw = new StringWriter();
        sw.write(FIRST_STRING);
        assertEquals(FIRST_STRING, sw.toString());
    }

    @Test
    public void runUniCodeThroughCSVReader() throws IOException {
        CSVReader reader = new CSVReader(new StringReader(COMPOUND_STRING));
        String[] items = reader.readNext();
        assertEquals(2, items.length);
        assertEquals(FIRST_STRING, items[0]);
        assertEquals(SECOND_STRING, items[1]);
        assertArrayEquals(UNICODE_ARRAY, items);
    }

    @Test
    public void runUniCodeThroughCSVWriter() {
        StringWriter sw = new StringWriter();
        CSVWriter writer = new CSVWriter(sw);
        writer.writeNext(UNICODE_ARRAY);
        assertEquals(COMPOUND_STRING_WITH_QUOTES.trim(), sw.toString().trim());
    }

    @Test
    public void runASCIIThroughCSVWriter() {
        StringWriter sw = new StringWriter();
        CSVWriter writer = new CSVWriter(sw);
        writer.writeNext(ASCII_ARRAY);
        assertEquals(ASCII_STRING_WITH_QUOTES.trim(), sw.toString().trim());
    }

    @Test
    public void writeThenReadAscii() throws IOException {
        StringWriter sw = new StringWriter();
        CSVWriter writer = new CSVWriter(sw);
        writer.writeNext(ASCII_ARRAY);

        CSVReader reader = new CSVReader(new StringReader(sw.toString()));
        String[] items = reader.readNext();
        assertEquals(2, items.length);
        assertArrayEquals(ASCII_ARRAY, items);
    }

    @Test
    public void writeThenReadTwiceAscii() throws IOException {
        StringWriter sw = new StringWriter();
        CSVWriter writer = new CSVWriter(sw);
        writer.writeNext(ASCII_ARRAY);
        writer.writeNext(ASCII_ARRAY);

        CSVReader reader = new CSVReader(new StringReader(sw.toString()));
        List<String[]> lines = reader.readAll();
        assertEquals(2, lines.size());

        String[] items = lines.get(0);
        assertEquals(2, items.length);
        assertArrayEquals(ASCII_ARRAY, items);


        items = lines.get(1);
        assertEquals(2, items.length);
        assertArrayEquals(ASCII_ARRAY, items);
    }

    @Test
    public void writeThenReadTwiceUnicode() throws IOException {
                StringWriter sw = new StringWriter();
        CSVWriter writer = new CSVWriter(sw);
        writer.writeNext(UNICODE_ARRAY);
        writer.writeNext(UNICODE_ARRAY);

        CSVReader reader = new CSVReader(new StringReader(sw.toString()));
        List<String[]> lines = reader.readAll();
        assertEquals(2, lines.size());

        String[] items = lines.get(0);
        assertEquals(2, items.length);
        assertArrayEquals(UNICODE_ARRAY, items);


        items = lines.get(1);
        assertEquals(2, items.length);
        assertArrayEquals(UNICODE_ARRAY, items);
    }

        @Test
    public void writeThenReadTwiceMixedUnicode() throws IOException {
                StringWriter sw = new StringWriter();
        CSVWriter writer = new CSVWriter(sw);
        writer.writeNext(MIXED_ARRAY);
        writer.writeNext(MIXED_ARRAY);

        CSVReader reader = new CSVReader(new StringReader(sw.toString()));
        List<String[]> lines = reader.readAll();
        assertEquals(2, lines.size());

        String[] items = lines.get(0);
        assertEquals(4, items.length);
        assertArrayEquals(MIXED_ARRAY, items);


        items = lines.get(1);
        assertEquals(4, items.length);
        assertArrayEquals(MIXED_ARRAY, items);
    }
}
