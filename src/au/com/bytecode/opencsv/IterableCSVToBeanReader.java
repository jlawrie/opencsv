package au.com.bytecode.opencsv;

import java.io.IOException;
import java.io.Reader;
import java.util.Iterator;

import au.com.bytecode.opencsv.bean.CsvToBeanIterator;
import au.com.bytecode.opencsv.bean.ColumnPositionMappingStrategy;
import au.com.bytecode.opencsv.exception.CSVRuntimeException;

public class IterableCSVToBeanReader<T> extends CSVReader implements Iterable<T> {
	
	private ColumnPositionMappingStrategy<T> mapper;

	public IterableCSVToBeanReader(ColumnPositionMappingStrategy<T> _mapper, Reader reader) {
		super(reader);
		this.mapper = _mapper;
	}

	public IterableCSVToBeanReader(ColumnPositionMappingStrategy<T> _mapper, Reader reader, char separator, char quotechar,
			boolean strictQuotes) {
		super(reader, separator, quotechar, strictQuotes);
		this.mapper = _mapper;
	}

	public IterableCSVToBeanReader(ColumnPositionMappingStrategy<T> _mapper, Reader reader, char separator, char quotechar,
			char escape, int line, boolean strictQuotes,
			boolean ignoreLeadingWhiteSpace) {
		super(reader, separator, quotechar, escape, line, strictQuotes,
				ignoreLeadingWhiteSpace);
		this.mapper = _mapper;
	}

	public IterableCSVToBeanReader(ColumnPositionMappingStrategy<T> _mapper, Reader reader, char separator, char quotechar,
			char escape, int line, boolean strictQuotes) {
		super(reader, separator, quotechar, escape, line, strictQuotes);
		this.mapper = _mapper;
	}

	public IterableCSVToBeanReader(ColumnPositionMappingStrategy<T> _mapper, Reader reader, char separator, char quotechar,
			char escape, int line) {
		super(reader, separator, quotechar, escape, line);
		this.mapper = _mapper;
	}

	public IterableCSVToBeanReader(ColumnPositionMappingStrategy<T> _mapper, Reader reader, char separator, char quotechar,
			char escape) {
		super(reader, separator, quotechar, escape);
		this.mapper = _mapper;
	}

	public IterableCSVToBeanReader(ColumnPositionMappingStrategy<T> _mapper, Reader reader, char separator, char quotechar,
			int line) {
		super(reader, separator, quotechar, line);
		this.mapper = _mapper;
	}

	public IterableCSVToBeanReader(ColumnPositionMappingStrategy<T> _mapper, Reader reader, char separator, char quotechar) {
		super(reader, separator, quotechar);
		this.mapper = _mapper;
	}

	public IterableCSVToBeanReader(ColumnPositionMappingStrategy<T> _mapper, Reader reader, char separator) {
		super(reader, separator);
		this.mapper = _mapper;
	}

	@Override
	public Iterator<T> iterator() {
		
		try {
			
			return new CsvToBeanIterator<T>(mapper, this);
			
		} catch (IOException e) {
			
			throw new CSVRuntimeException(e);
			
		}
		
	}
	
}
