package au.com.bytecode.opencsv.exception;

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
