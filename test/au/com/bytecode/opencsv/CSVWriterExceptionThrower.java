package au.com.bytecode.opencsv;

import java.io.IOException;
import java.io.Writer;

/**
 * Created by IntelliJ IDEA.
 * User: sconway
 * Date: 6/5/11
 * Time: 7:08 PM
 * To change this template use File | Settings | File Templates.
 */
public class CSVWriterExceptionThrower extends CSVWriter {
    public CSVWriterExceptionThrower(Writer writer) {
        super(writer);
    }

    @Override
    public void flush() throws IOException {
        throw new IOException("Exception thrown from Mock test flush method");
    }
}
