package au.com.bytecode.opencsv.exception;

/**
 * I apologize for this class -- haven't figured out
 * a cleaner way of making the CsvToBeanIterator work...
 * @author bothajo
 *
 */
public class CSVRuntimeException extends RuntimeException {

	private static final long serialVersionUID = 7303281195236049701L;

	public CSVRuntimeException() {
		super();
	}

	public CSVRuntimeException(String message, Throwable cause) {
		super(message, cause);
	}

	public CSVRuntimeException(String message) {
		super(message);
	}

	public CSVRuntimeException(Throwable cause) {
		super(cause);
	}
	
}
