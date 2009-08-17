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

import java.io.BufferedReader;
import java.io.Closeable;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;

/**
 * A very simple CSV reader released under a commercial-friendly license.
 * 
 * @author Glen Smith
 * 
 */
public class CSVReader implements Closeable {

    private BufferedReader br;

    private boolean hasNext = true;

    private final char separator;

    private final char quotechar;
    
    private final char escape;
    
    private int skipLines;

    private boolean linesSkiped;

    /** The default separator to use if none is supplied to the constructor. */
    public static final char DEFAULT_SEPARATOR = ',';

    public static final int INITIAL_READ_SIZE = 64;

    /**
     * The default quote character to use if none is supplied to the
     * constructor.
     */
    public static final char DEFAULT_QUOTE_CHARACTER = '"';
    

    /**
     * The default escape character to use if none is supplied to the
     * constructor.
     */
    public static final char DEFAULT_ESCAPE_CHARACTER = '\\';
    
    /**
     * The default line to start reading.
     */
    public static final int DEFAULT_SKIP_LINES = 0;

    /**
     * Constructs CSVReader using a comma for the separator.
     * 
     * @param reader
     *            the reader to an underlying CSV source.
     */
    public CSVReader(Reader reader) {
        this(reader, DEFAULT_SEPARATOR);
    }

    /**
     * Constructs CSVReader with supplied separator.
     * 
     * @param reader
     *            the reader to an underlying CSV source.
     * @param separator
     *            the delimiter to use for separating entries.
     */
    public CSVReader(Reader reader, char separator) {
        this(reader, separator, DEFAULT_QUOTE_CHARACTER, DEFAULT_ESCAPE_CHARACTER);
    }
    
    

    /**
     * Constructs CSVReader with supplied separator and quote char.
     * 
     * @param reader
     *            the reader to an underlying CSV source.
     * @param separator
     *            the delimiter to use for separating entries
     * @param quotechar
     *            the character to use for quoted elements
     */
    public CSVReader(Reader reader, char separator, char quotechar) {
        this(reader, separator, quotechar, DEFAULT_ESCAPE_CHARACTER, DEFAULT_SKIP_LINES);
    }

    public CSVReader(Reader reader, char separator,
			char quotechar, char escape) {
        this(reader, separator, quotechar, escape, DEFAULT_SKIP_LINES);
	}
    
    /**
     * Constructs CSVReader with supplied separator and quote char.
     * 
     * @param reader
     *            the reader to an underlying CSV source.
     * @param separator
     *            the delimiter to use for separating entries
     * @param quotechar
     *            the character to use for quoted elements
     * @param line
     *            the line number to skip for start reading 
     */
    public CSVReader(Reader reader, char separator, char quotechar, int line) {
        this(reader, separator, quotechar, DEFAULT_ESCAPE_CHARACTER, line);
    }
    
    /**
     * Constructs CSVReader with supplied separator and quote char.
     * 
     * @param reader
     *            the reader to an underlying CSV source.
     * @param separator
     *            the delimiter to use for separating entries
     * @param quotechar
     *            the character to use for quoted elements
     * @param escape
     *            the character to use for escaping a separator or quote
     * @param line
     *            the line number to skip for start reading 
     */
    public CSVReader(Reader reader, char separator, char quotechar, char escape, int line) {
        this.br = new BufferedReader(reader);
        this.separator = separator;
        this.quotechar = quotechar;
        this.escape = escape;
        this.skipLines = line;
    }


	/**
     * Reads the entire file into a List with each element being a String[] of
     * tokens.
     * 
     * @return a List of String[], with each String[] representing a line of the
     *         file.
     * 
     * @throws IOException
     *             if bad things happen during the read
     */
    public List<String[]> readAll() throws IOException {

        List<String[]> allElements = new ArrayList<String[]>();
        while (hasNext) {
            String[] nextLineAsTokens = readNext();
            if (nextLineAsTokens != null)
                allElements.add(nextLineAsTokens);
        }
        return allElements;

    }

    /**
     * Reads the next line from the buffer and converts to a string array.
     * 
     * @return a string array with each comma-separated element as a separate
     *         entry.
     * 
     * @throws IOException
     *             if bad things happen during the read
     */
    public String[] readNext() throws IOException {

        String nextLine = getNextLine();
        return hasNext ? parseLine(nextLine) : null;
    }

    /**
     * Reads the next line from the file.
     * 
     * @return the next line from the file without trailing newline
     * @throws IOException
     *             if bad things happen during the read
     */
    private String getNextLine() throws IOException {
    	if (!this.linesSkiped) {
            for (int i = 0; i < skipLines; i++) {
                br.readLine();
            }
            this.linesSkiped = true;
        }
        String nextLine = br.readLine();
        if (nextLine == null) {
            hasNext = false;
        }
        return hasNext ? nextLine : null;
    }

    /**
     * Parses an incoming String and returns an array of elements.
     * 
     * @param nextLine
     *            the string to parse
     * @return the comma-tokenized list of elements, or null if nextLine is null
     * @throws IOException if bad things happen during the read
     */
    private String[] parseLine(String nextLine) throws IOException {

        if (nextLine == null) {
            return null;
        }

        List<String>tokensOnThisLine = new ArrayList<String>();
        StringBuilder sb = new StringBuilder(INITIAL_READ_SIZE);
        boolean inQuotes = false;
        do {
        	if (inQuotes) {
                // continuing a quoted section, reappend newline
                sb.append("\n");
                nextLine = getNextLine();
                if (nextLine == null)
                    break;
            }
            for (int i = 0; i < nextLine.length(); i++) {

                char c = nextLine.charAt(i);
                if (c == this.escape) {
                	if( isEscapable(nextLine, inQuotes, i) ){ 
                		sb.append(nextLine.charAt(i+1));
                		i++;
                	} else {
                		i++; // ignore the escape
                	}
                } else if (c == quotechar) {
                	if( isEscapedQuote(nextLine, inQuotes, i) ){ 
                		sb.append(nextLine.charAt(i+1));
                		i++;
                	}else{
                		inQuotes = !inQuotes;
                		// the tricky case of an embedded quote in the middle: a,bc"d"ef,g
                		if(i>2 //not on the beginning of the line
                				&& nextLine.charAt(i-1) != this.separator //not at the beginning of an escape sequence 
                				&& nextLine.length()>(i+1) &&
                				nextLine.charAt(i+1) != this.separator //not at the	end of an escape sequence
                		){
                			sb.append(c);
                		}
                	}
                } else if (c == separator && !inQuotes) {
                    tokensOnThisLine.add(sb.toString());
                    sb = new StringBuilder(INITIAL_READ_SIZE); // start work on next token
                } else {
                    sb.append(c);
                }
            }
        } while (inQuotes);
        tokensOnThisLine.add(sb.toString());
        return tokensOnThisLine.toArray(new String[0]);

    }

	/**  
	 * precondition: the current character is a quote or an escape
	 * @param nextLine the current line
	 * @param inQuotes true if the current context is quoted
	 * @param i current index in line
	 * @return true if the following character is a quote
	 */
	private boolean isEscapedQuote(String nextLine, boolean inQuotes, int i) {
		return inQuotes  // we are in quotes, therefore there can be escaped quotes in here.
		    && nextLine.length() > (i+1)  // there is indeed another character to check.
		    && nextLine.charAt(i+1) == quotechar;
	}

	/**  
	 * precondition: the current character is an escape
	 * @param nextLine the current line
	 * @param inQuotes true if the current context is quoted
	 * @param i current index in line
	 * @return true if the following character is a quote
	 */
	private boolean isEscapable(String nextLine, boolean inQuotes, int i) {
		return inQuotes  // we are in quotes, therefore there can be escaped quotes in here.
		    && nextLine.length() > (i+1)  // there is indeed another character to check.
		    && ( nextLine.charAt(i+1) == quotechar || nextLine.charAt(i+1) == this.escape);
	}

    /**
     * Closes the underlying reader.
     * 
     * @throws IOException if the close fails
     */
    public void close() throws IOException{
    	br.close();
    }
    
}
