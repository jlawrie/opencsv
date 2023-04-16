package au.com.bytecode.opencsv.bean;

import java.beans.IntrospectionException;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.Iterator;

import au.com.bytecode.opencsv.CSVReader;
import au.com.bytecode.opencsv.exception.CSVRuntimeException;

/**
 * Somewhat better on memory when mapping massive CSV files -- which I have to do...
 * 
 * @author bothajo
 *
 * @param <T>
 * 
 */
public class CsvToBeanIterator<T> extends CsvToBean<T> implements Iterator<T> {

	private MappingStrategy<T> mapper;
	private CSVReader reader;
	private String[] nextLine;

	public CsvToBeanIterator(MappingStrategy<T> _mapper, CSVReader csv) throws IOException {
		
		super();

		reader = csv;
		mapper = _mapper;
		mapper.captureHeader(reader);

	}

	@Override
	public boolean hasNext() {

		boolean ret = true;

		try {

			if ((nextLine = reader.readNext()) == null) {

				ret = false;

			}

		} catch (IOException e) {

			//redundant
			ret = false;
			
			throw new CSVRuntimeException(e);

		}

		return ret;

	}

	@Override
	public T next() {

		T obj = null;

		try {

			obj = processLine(mapper, nextLine);

		} catch (IllegalAccessException e) {

			throw new CSVRuntimeException(e);

		} catch (InvocationTargetException e) {

			throw new CSVRuntimeException(e);

		} catch (InstantiationException e) {

			throw new CSVRuntimeException(e);

		} catch (IntrospectionException e) {

			throw new CSVRuntimeException(e);

		}

		return obj;

	}

	@Override
	public void remove() {
		
		throw new UnsupportedOperationException("There is no underlying collection associated with this Iterable class.");

	}

}