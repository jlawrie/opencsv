package au.com.bytecode.opencsv;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class CSVParserBuilderTest {

    private CSVParserBuilder builder;

    @Before
    public void setUp() throws Exception {
        builder = new CSVParserBuilder();
    }

    @Test
    public void testDefaultBuilder() {
        assertEquals(
                CSVParser.DEFAULT_SEPARATOR,
                builder.separator);
        assertEquals(
                CSVParser.DEFAULT_QUOTE_CHARACTER,
                builder.quoteChar);
        assertEquals(
                CSVParser.DEFAULT_ESCAPE_CHARACTER,
                builder.escapeChar);
        assertEquals(
                CSVParser.DEFAULT_STRICT_QUOTES,
                builder.strictQuotes);
        assertEquals(
                CSVParser.DEFAULT_IGNORE_LEADING_WHITESPACE,
                builder.ignoreLeadingWhiteSpace);
        assertEquals(
                CSVParser.DEFAULT_IGNORE_QUOTATIONS,
                builder.ignoreQuotations);

        CSVParser parser = builder.build();
        assertEquals(
                CSVParser.DEFAULT_SEPARATOR,
                parser.separator);
        assertEquals(
                CSVParser.DEFAULT_QUOTE_CHARACTER,
                parser.quotechar);
        assertEquals(
                CSVParser.DEFAULT_ESCAPE_CHARACTER,
                parser.escape);
        assertEquals(
                CSVParser.DEFAULT_STRICT_QUOTES,
                parser.strictQuotes);
        assertEquals(
                CSVParser.DEFAULT_IGNORE_LEADING_WHITESPACE,
                parser.ignoreLeadingWhiteSpace);
        assertEquals(
                CSVParser.DEFAULT_IGNORE_QUOTATIONS,
                parser.ignoreQuotations);
    }

    @Test
    public void testWithSeparator() {
        final char expected = '1';
        builder.withSeparator(expected);
        assertEquals(expected, builder.separator);
        assertEquals(expected, builder.build().separator);
    }

    @Test
    public void testWithQuoteChar() {
        final char expected = '2';
        builder.withQuoteChar(expected);
        assertEquals(expected, builder.quoteChar);
        assertEquals(expected, builder.build().quotechar);
    }

    @Test
    public void testWithEscapeChar() {
        final char expected = '3';
        builder.withEscapeChar(expected);
        assertEquals(expected, builder.escapeChar);
        assertEquals(expected, builder.build().escape);
    }

    @Test
    public void testWithStrictQuotes() {
        final boolean expected = true;
        builder.withStrictQuotes(expected);
        assertEquals(expected, builder.strictQuotes);
        assertEquals(expected, builder.build().strictQuotes);
    }

    @Test
    public void testWithIgnoreLeadingWhiteSpace() {
        final boolean expected = true;
        builder.withIgnoreLeadingWhiteSpace(expected);
        assertEquals(expected, builder.ignoreLeadingWhiteSpace);
        assertEquals(expected, builder.build().ignoreLeadingWhiteSpace);
    }

    @Test
    public void testWithIgnoreQuotations() {
        final boolean expected = true;
        builder.withIgnoreQuotations(expected);
        assertEquals(expected, builder.ignoreQuotations);
        assertEquals(expected, builder.build().ignoreQuotations);
    }

}
