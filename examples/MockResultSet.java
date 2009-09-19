
import java.io.InputStream;
import java.io.Reader;
import java.math.BigDecimal;
import java.net.MalformedURLException;
import java.net.URL;
import java.sql.Array;
import java.sql.Blob;
import java.sql.Clob;
import java.sql.Date;
import java.sql.NClob;
import java.sql.Ref;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.RowId;
import java.sql.SQLException;
import java.sql.SQLWarning;
import java.sql.SQLXML;
import java.sql.Statement;
import java.sql.Time;
import java.sql.Timestamp;
import java.sql.Types;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Map;

/**
 * 
 * 
 * mock ResultSet
 *
 * 
 *
 */
public class MockResultSet implements ResultSet
{
	private int currentRow = -1;
	private int rowCount = 0;
	private MockResultSetMetaData metadata; 
	private long currentTime = System.currentTimeMillis();
	
	public MockResultSet(int rows)
	{
		this(rows, MockResultSetMetaData.getDefaultMetaData());
	}
	
	public MockResultSet(int rows, MockResultSetMetaData meta)
	{
		rowCount = rows;
		metadata = meta;
	}

	public MockResultSet()
	{
		metadata = MockResultSetMetaData.getDefaultMetaData();
	}
	
	public int getRowCount()
	{
		return rowCount;
	}
	
	public boolean next() throws SQLException
	{
		if (currentRow + 1 >= rowCount)
		{
			return false;
		}
		else
		{
			currentRow++;
			return true;
		}
		
		
	}

	public void close() throws SQLException
	{
	}

	public boolean wasNull() throws SQLException
	{
		return false;
	}

	protected MockColumn getColumn(int columnIndex)
	{
		return metadata.getColumn(columnIndex);
	}
	
	public String getString(int columnIndex) throws SQLException
	{
		return "string-" + getCurrentRow();
	}

	public boolean getBoolean(int columnIndex) throws SQLException
	{
		return ((getCurrentRow() % 2) == 0);
	}

	public byte getByte(int columnIndex) throws SQLException
	{
		return 0;
	}

	public short getShort(int columnIndex) throws SQLException
	{
		return (short) getCurrentRow();
	}

	public int getInt(int columnIndex) throws SQLException
	{
		return getCurrentRow();
	}

	public long getLong(int columnIndex) throws SQLException
	{
		return getCurrentRow();
	}

	public float getFloat(int columnIndex) throws SQLException
	{
		return getCurrentRow() + 0.25f;
	}

	public double getDouble(int columnIndex) throws SQLException
	{
		return getCurrentRow() + 0.33d;
	}

	public BigDecimal getBigDecimal(int columnIndex, int scale)
			throws SQLException
	{
		return new BigDecimal(getCurrentRow() + 0.44d);
	}

	public byte[] getBytes(int columnIndex) throws SQLException
	{
		return "foo".getBytes();
	}

	public Date getDate(int columnIndex) throws SQLException
	{
		return new Date(currentTime);
	}

	public Time getTime(int columnIndex) throws SQLException
	{
		return new java.sql.Time(currentTime);
	}

	public Timestamp getTimestamp(int columnIndex) throws SQLException
	{
		return new Timestamp(currentTime);
	}

	public InputStream getAsciiStream(int columnIndex) throws SQLException
	{
		return null; // todo
	}

	public InputStream getUnicodeStream(int columnIndex) throws SQLException
	{
		return null;
	}

	public InputStream getBinaryStream(int columnIndex) throws SQLException
	{
		return null;
	}

	public String getString(String columnName) throws SQLException
	{
		return columnName + getCurrentRow();
	}

	public boolean getBoolean(String columnName) throws SQLException
	{
		return ((getCurrentRow() % 2) == 0);
	}

	public byte getByte(String columnName) throws SQLException
	{
		return 0;
	}

	public short getShort(String columnName) throws SQLException
	{
		return (short) getCurrentRow();
	}

	public int getInt(String columnName) throws SQLException
	{
		return (int) getCurrentRow();
	}

	public long getLong(String columnName) throws SQLException
	{
		return (long) getCurrentRow();
	}

	public float getFloat(String columnName) throws SQLException
	{
		return (float) getCurrentRow() + 0.01f;
	}

	public double getDouble(String columnName) throws SQLException
	{
		return (double) getCurrentRow() + 0.05d;
	}

	public BigDecimal getBigDecimal(String columnName, int scale)
			throws SQLException
	{
		return new java.math.BigDecimal(getCurrentRow() + 0.25);
	}

	public byte[] getBytes(String columnName) throws SQLException
	{
		return columnName.getBytes();
	}

	public Date getDate(String columnName) throws SQLException
	{
		return new Date(currentTime);
	}

	public Time getTime(String columnName) throws SQLException
	{
		return new java.sql.Time(currentTime);
	}

	public Timestamp getTimestamp(String columnName) throws SQLException
	{
		return new Timestamp(currentTime);
	}

	public InputStream getAsciiStream(String columnName) throws SQLException
	{
		return null;
	}

	public InputStream getUnicodeStream(String columnName) throws SQLException
	{
		return null;
	}

	public InputStream getBinaryStream(String columnName) throws SQLException
	{
		return null;
	}

	public SQLWarning getWarnings() throws SQLException
	{
		return null;
	}

	public void clearWarnings() throws SQLException
	{
	}

	public String getCursorName() throws SQLException
	{
		return "CursorName";
	}

	public ResultSetMetaData getMetaData() throws SQLException
	{
		return metadata;
	}

	public Object getObject(int columnIndex) throws SQLException
	{
		return new StringBuilder("Object[row " 
							+ getCurrentRow() 
							+ ", column "
							+ columnIndex
							+ "]");
	}

	public Object getObject(String columnName) throws SQLException
	{
		return new StringBuilder("Object-" + getCurrentRow());
	}

	public int findColumn(String columnName) throws SQLException
	{
		return 0; // todo ?
	}

	public Reader getCharacterStream(int columnIndex) throws SQLException
	{
		return null; // todo ?
	}

	public Reader getCharacterStream(String columnName) throws SQLException
	{
		return null; // todo ?
	}

	public BigDecimal getBigDecimal(int columnIndex) throws SQLException
	{
		return new BigDecimal(getCurrentRow() + 0.25d);
	}

	public BigDecimal getBigDecimal(String columnName) throws SQLException
	{
		return new BigDecimal(getCurrentRow() + 0.25d);
	}

	public boolean isBeforeFirst() throws SQLException
	{
		return (getCurrentRow() < 0);
	}

	public boolean isAfterLast() throws SQLException
	{
		return (getCurrentRow() >= getRowCount());
	}

	public boolean isFirst() throws SQLException
	{
		return (getCurrentRow() == 0);
	}

	public boolean isLast() throws SQLException
	{
		return (getCurrentRow() == (getRowCount() - 1));
	}

	public void beforeFirst() throws SQLException
	{
		currentRow = -1;
	}

	public void afterLast() throws SQLException
	{
		throw new SQLException("not implemented");
	}

	public boolean first() throws SQLException
	{
		currentRow = 0;
		return true;
	}

	public boolean last() throws SQLException
	{
		currentRow = getRowCount() - 1;
		return true;
	}

	public int getRow() throws SQLException
	{
		return currentRow;
	}

	public boolean absolute(int row) throws SQLException
	{
		throw new SQLException("not implemented");
	}

	public boolean relative(int rows) throws SQLException
	{
		throw new SQLException("not implemented");
	}

	public boolean previous() throws SQLException
	{
		throw new SQLException("not implemented");
	}

	public void setFetchDirection(int direction) throws SQLException
	{
		throw new SQLException("not implemented");
	}

	public int getFetchDirection() throws SQLException
	{
		throw new SQLException("not implemented");
	}

	public void setFetchSize(int rows) throws SQLException
	{
		throw new SQLException("not implemented");
	}

	public int getFetchSize() throws SQLException
	{
		throw new SQLException("not implemented");
	}

	public int getType() throws SQLException
	{
		throw new SQLException("not implemented");
	}

	public int getConcurrency() throws SQLException
	{
		throw new SQLException("not implemented");
	}

	public boolean rowUpdated() throws SQLException
	{
		throw new SQLException("not implemented");
	}

	public boolean rowInserted() throws SQLException
	{
		throw new SQLException("not implemented");
	}

	public boolean rowDeleted() throws SQLException
	{
		throw new SQLException("not implemented");
	}

	public void updateNull(int columnIndex) throws SQLException
	{
		throw new SQLException("not implemented");
	}

	public void updateBoolean(int columnIndex, boolean x) throws SQLException
	{
		throw new SQLException("not implemented");
	}

	public void updateByte(int columnIndex, byte x) throws SQLException
	{
		throw new SQLException("not implemented");
	}

	public void updateShort(int columnIndex, short x) throws SQLException
	{
		throw new SQLException("not implemented");
	}

	public void updateInt(int columnIndex, int x) throws SQLException
	{
		throw new SQLException("not implemented");
	}

	public void updateLong(int columnIndex, long x) throws SQLException
	{
		throw new SQLException("not implemented");
	}

	public void updateFloat(int columnIndex, float x) throws SQLException
	{
		throw new SQLException("not implemented");
	}

	public void updateDouble(int columnIndex, double x) throws SQLException
	{
		throw new SQLException("not implemented");
	}

	public void updateBigDecimal(int columnIndex, BigDecimal x)
			throws SQLException
	{
		throw new SQLException("not implemented");
	}

	public void updateString(int columnIndex, String x) throws SQLException
	{
		throw new SQLException("not implemented");
	}

	public void updateBytes(int columnIndex, byte[] x) throws SQLException
	{
		throw new SQLException("not implemented");
	}

	public void updateDate(int columnIndex, Date x) throws SQLException
	{
		throw new SQLException("not implemented");
	}

	public void updateTime(int columnIndex, Time x) throws SQLException
	{
		throw new SQLException("not implemented");
	}

	public void updateTimestamp(int columnIndex, Timestamp x)
			throws SQLException
	{
		throw new SQLException("not implemented");
	}

	public void updateAsciiStream(int columnIndex, InputStream x, int length)
			throws SQLException
	{
		throw new SQLException("not implemented");
	}

	public void updateBinaryStream(int columnIndex, InputStream x, int length)
			throws SQLException
	{
		throw new SQLException("not implemented");
	}

	public void updateCharacterStream(int columnIndex, Reader x, int length)
			throws SQLException
	{
		throw new SQLException("not implemented");
	}

	public void updateObject(int columnIndex, Object x, int scale)
			throws SQLException
	{
		throw new SQLException("not implemented");
	}

	public void updateObject(int columnIndex, Object x) throws SQLException
	{
		throw new SQLException("not implemented");
	}

	public void updateNull(String columnName) throws SQLException
	{
		throw new SQLException("not implemented");
	}

	public void updateBoolean(String columnName, boolean x) throws SQLException
	{
		throw new SQLException("not implemented");
	}

	public void updateByte(String columnName, byte x) throws SQLException
	{
		throw new SQLException("not implemented");
	}

	public void updateShort(String columnName, short x) throws SQLException
	{
		throw new SQLException("not implemented");
	}

	public void updateInt(String columnName, int x) throws SQLException
	{
		throw new SQLException("not implemented");
	}

	public void updateLong(String columnName, long x) throws SQLException
	{
		throw new SQLException("not implemented");
	}

	public void updateFloat(String columnName, float x) throws SQLException
	{
		throw new SQLException("not implemented");
	}

	public void updateDouble(String columnName, double x) throws SQLException
	{
		throw new SQLException("not implemented");
	}

	public void updateBigDecimal(String columnName, BigDecimal x)
			throws SQLException
	{
		throw new SQLException("not implemented");
	}

	public void updateString(String columnName, String x) throws SQLException
	{
		throw new SQLException("not implemented");
	}

	public void updateBytes(String columnName, byte[] x) throws SQLException
	{
		throw new SQLException("not implemented");
	}

	public void updateDate(String columnName, Date x) throws SQLException
	{
		throw new SQLException("not implemented");
	}

	public void updateTime(String columnName, Time x) throws SQLException
	{
		throw new SQLException("not implemented");
	}

	public void updateTimestamp(String columnName, Timestamp x)
			throws SQLException
	{
		throw new SQLException("not implemented");
	}

	public void updateAsciiStream(String columnName, InputStream x, int length)
			throws SQLException
	{
		throw new SQLException("not implemented");
	}

	public void updateBinaryStream(String columnName, InputStream x, int length)
			throws SQLException
	{
		throw new SQLException("not implemented");
	}

	public void updateCharacterStream(String columnName, Reader reader,
			int length) throws SQLException
	{
		throw new SQLException("not implemented");
	}

	public void updateObject(String columnName, Object x, int scale)
			throws SQLException
	{
		throw new SQLException("not implemented");
	}

	public void updateObject(String columnName, Object x) throws SQLException
	{
		throw new SQLException("not implemented");
	}

	public void insertRow() throws SQLException
	{
		throw new SQLException("not implemented");
	}

	public void updateRow() throws SQLException
	{
		throw new SQLException("not implemented");
	}

	public void deleteRow() throws SQLException
	{
		throw new SQLException("not implemented");
	}

	public void refreshRow() throws SQLException
	{
		throw new SQLException("not implemented");
	}

	public void cancelRowUpdates() throws SQLException
	{
		throw new SQLException("not implemented");
	}

	public void moveToInsertRow() throws SQLException
	{
		throw new SQLException("not implemented");
	}

	public void moveToCurrentRow() throws SQLException
	{
		throw new SQLException("not implemented");
	}

	public Statement getStatement() throws SQLException
	{
		throw new SQLException("not implemented");
	}

	public Object getObject(int arg0, Map<String, Class<?>> arg1)
			throws SQLException
	{
		throw new SQLException("not implemented");
	}

	public Ref getRef(int i) throws SQLException
	{
		throw new SQLException("not implemented");
	}

	public Blob getBlob(int i) throws SQLException
	{
		throw new SQLException("not implemented");
	}

	public Clob getClob(int i) throws SQLException
	{
		// TODO Auto-generated method stub
		return null;
	}

	public Array getArray(int i) throws SQLException
	{
		throw new SQLException("not implemented");
	}

	public Object getObject(String arg0, Map<String, Class<?>> arg1)
			throws SQLException
	{
		throw new SQLException("not implemented");
	}

	public Ref getRef(String colName) throws SQLException
	{
		// TODO Auto-generated method stub
		return null;
	}

	public Blob getBlob(String colName) throws SQLException
	{
		throw new SQLException("not implemented");
	}

	public Clob getClob(String colName) throws SQLException
	{
		// TODO Auto-generated method stub
		return null;
	}

	public Array getArray(String colName) throws SQLException
	{
		throw new SQLException("not implemented");
	}

	public Date getDate(int columnIndex, Calendar cal) throws SQLException
	{
		return new Date(currentTime);
	}

	public Date getDate(String columnName, Calendar cal) throws SQLException
	{
		return new Date(currentTime);
	}

	public Time getTime(int columnIndex, Calendar cal) throws SQLException
	{
		return new Time(currentTime);
	}

	public Time getTime(String columnName, Calendar cal) throws SQLException
	{
		return new Time(currentTime);
	}

	public Timestamp getTimestamp(int columnIndex, Calendar cal)
			throws SQLException
	{
		return new Timestamp(currentTime);
	}

	public Timestamp getTimestamp(String columnName, Calendar cal)
			throws SQLException
	{
		return new Timestamp(currentTime);
	}

	public URL getURL(int columnIndex) throws SQLException
	{
		try
		{
			return new URL("http://www.google.com/");
		}
		catch (MalformedURLException ex)
		{
			throw new SQLException();
		}
	}

	public URL getURL(String columnName) throws SQLException
	{
		try
		{
			return new URL("http://www.google.com/");
		}
		catch (MalformedURLException ex)
		{
			throw new SQLException();
		}
	}

	public void updateRef(int columnIndex, Ref x) throws SQLException
	{
		throw new SQLException("not implemented");
	}

	public void updateRef(String columnName, Ref x) throws SQLException
	{
		throw new SQLException("not implemented");
	}

	public void updateBlob(int columnIndex, Blob x) throws SQLException
	{
		throw new SQLException("not implemented");
	}

	public void updateBlob(String columnName, Blob x) throws SQLException
	{
		throw new SQLException("not implemented");
	}

	public void updateClob(int columnIndex, Clob x) throws SQLException
	{
		throw new SQLException("not implemented");
	}

	public void updateClob(String columnName, Clob x) throws SQLException
	{
		throw new SQLException("not implemented");
	}

	public void updateArray(int columnIndex, Array x) throws SQLException
	{
		throw new SQLException("not implemented");
	}

	public void updateArray(String columnName, Array x) throws SQLException
	{
		throw new SQLException("not implemented");
	}

	protected int getCurrentRow()
	{
		return currentRow;
	}

	@Override
    public int getHoldability() throws SQLException
    {
	    // TODO Auto-generated method stub
	    return 0;
    }

	@Override
    public Reader getNCharacterStream(int arg0) throws SQLException
    {
	    // TODO Auto-generated method stub
	    return null;
    }

	@Override
    public Reader getNCharacterStream(String arg0) throws SQLException
    {
	    // TODO Auto-generated method stub
	    return null;
    }

	@Override
    public NClob getNClob(int arg0) throws SQLException
    {
	    // TODO Auto-generated method stub
	    return null;
    }

	@Override
    public NClob getNClob(String arg0) throws SQLException
    {
	    // TODO Auto-generated method stub
	    return null;
    }

	@Override
    public String getNString(int arg0) throws SQLException
    {
	    // TODO Auto-generated method stub
	    return null;
    }

	@Override
    public String getNString(String arg0) throws SQLException
    {
	    // TODO Auto-generated method stub
	    return null;
    }

	@Override
    public RowId getRowId(int arg0) throws SQLException
    {
	    // TODO Auto-generated method stub
	    return null;
    }

	@Override
    public RowId getRowId(String arg0) throws SQLException
    {
	    // TODO Auto-generated method stub
	    return null;
    }

	@Override
    public SQLXML getSQLXML(int arg0) throws SQLException
    {
	    // TODO Auto-generated method stub
	    return null;
    }

	@Override
    public SQLXML getSQLXML(String arg0) throws SQLException
    {
	    // TODO Auto-generated method stub
	    return null;
    }

	@Override
    public boolean isClosed() throws SQLException
    {
	    // TODO Auto-generated method stub
	    return false;
    }

	@Override
    public void updateAsciiStream(int arg0, InputStream arg1)
            throws SQLException
    {
	    // TODO Auto-generated method stub
	    
    }

	@Override
    public void updateAsciiStream(String arg0, InputStream arg1)
            throws SQLException
    {
	    // TODO Auto-generated method stub
	    
    }

	@Override
    public void updateAsciiStream(int arg0, InputStream arg1, long arg2)
            throws SQLException
    {
	    // TODO Auto-generated method stub
	    
    }

	@Override
    public void updateAsciiStream(String arg0, InputStream arg1, long arg2)
            throws SQLException
    {
	    // TODO Auto-generated method stub
	    
    }

	@Override
    public void updateBinaryStream(int arg0, InputStream arg1)
            throws SQLException
    {
	    // TODO Auto-generated method stub
	    
    }

	@Override
    public void updateBinaryStream(String arg0, InputStream arg1)
            throws SQLException
    {
	    // TODO Auto-generated method stub
	    
    }

	@Override
    public void updateBinaryStream(int arg0, InputStream arg1, long arg2)
            throws SQLException
    {
	    // TODO Auto-generated method stub
	    
    }

	@Override
    public void updateBinaryStream(String arg0, InputStream arg1, long arg2)
            throws SQLException
    {
	    // TODO Auto-generated method stub
	    
    }

	@Override
    public void updateBlob(int arg0, InputStream arg1) throws SQLException
    {
	    // TODO Auto-generated method stub
	    
    }

	@Override
    public void updateBlob(String arg0, InputStream arg1) throws SQLException
    {
	    // TODO Auto-generated method stub
	    
    }

	@Override
    public void updateBlob(int arg0, InputStream arg1, long arg2)
            throws SQLException
    {
	    // TODO Auto-generated method stub
	    
    }

	@Override
    public void updateBlob(String arg0, InputStream arg1, long arg2)
            throws SQLException
    {
	    // TODO Auto-generated method stub
	    
    }

	@Override
    public void updateCharacterStream(int arg0, Reader arg1)
            throws SQLException
    {
	    // TODO Auto-generated method stub
	    
    }

	@Override
    public void updateCharacterStream(String arg0, Reader arg1)
            throws SQLException
    {
	    // TODO Auto-generated method stub
	    
    }

	@Override
    public void updateCharacterStream(int arg0, Reader arg1, long arg2)
            throws SQLException
    {
	    // TODO Auto-generated method stub
	    
    }

	@Override
    public void updateCharacterStream(String arg0, Reader arg1, long arg2)
            throws SQLException
    {
	    // TODO Auto-generated method stub
	    
    }

	@Override
    public void updateClob(int arg0, Reader arg1) throws SQLException
    {
	    // TODO Auto-generated method stub
	    
    }

	@Override
    public void updateClob(String arg0, Reader arg1) throws SQLException
    {
	    // TODO Auto-generated method stub
	    
    }

	@Override
    public void updateClob(int arg0, Reader arg1, long arg2)
            throws SQLException
    {
	    // TODO Auto-generated method stub
	    
    }

	@Override
    public void updateClob(String arg0, Reader arg1, long arg2)
            throws SQLException
    {
	    // TODO Auto-generated method stub
	    
    }

	@Override
    public void updateNCharacterStream(int arg0, Reader arg1)
            throws SQLException
    {
	    // TODO Auto-generated method stub
	    
    }

	@Override
    public void updateNCharacterStream(String arg0, Reader arg1)
            throws SQLException
    {
	    // TODO Auto-generated method stub
	    
    }

	@Override
    public void updateNCharacterStream(int arg0, Reader arg1, long arg2)
            throws SQLException
    {
	    // TODO Auto-generated method stub
	    
    }

	@Override
    public void updateNCharacterStream(String arg0, Reader arg1, long arg2)
            throws SQLException
    {
	    // TODO Auto-generated method stub
	    
    }

	@Override
    public void updateNClob(int arg0, NClob arg1) throws SQLException
    {
	    // TODO Auto-generated method stub
	    
    }

	@Override
    public void updateNClob(String arg0, NClob arg1) throws SQLException
    {
	    // TODO Auto-generated method stub
	    
    }

	@Override
    public void updateNClob(int arg0, Reader arg1) throws SQLException
    {
	    // TODO Auto-generated method stub
	    
    }

	@Override
    public void updateNClob(String arg0, Reader arg1) throws SQLException
    {
	    // TODO Auto-generated method stub
	    
    }

	@Override
    public void updateNClob(int arg0, Reader arg1, long arg2)
            throws SQLException
    {
	    // TODO Auto-generated method stub
	    
    }

	@Override
    public void updateNClob(String arg0, Reader arg1, long arg2)
            throws SQLException
    {
	    // TODO Auto-generated method stub
	    
    }

	@Override
    public void updateNString(int arg0, String arg1) throws SQLException
    {
	    // TODO Auto-generated method stub
	    
    }

	@Override
    public void updateNString(String arg0, String arg1) throws SQLException
    {
	    // TODO Auto-generated method stub
	    
    }

	@Override
    public void updateRowId(int arg0, RowId arg1) throws SQLException
    {
	    // TODO Auto-generated method stub
	    
    }

	@Override
    public void updateRowId(String arg0, RowId arg1) throws SQLException
    {
	    // TODO Auto-generated method stub
	    
    }

	@Override
    public void updateSQLXML(int arg0, SQLXML arg1) throws SQLException
    {
	    // TODO Auto-generated method stub
	    
    }

	@Override
    public void updateSQLXML(String arg0, SQLXML arg1) throws SQLException
    {
	    // TODO Auto-generated method stub
	    
    }

	@Override
    public boolean isWrapperFor(Class<?> arg0) throws SQLException
    {
	    // TODO Auto-generated method stub
	    return false;
    }

	@Override
    public <T> T unwrap(Class<T> arg0) throws SQLException
    {
	    // TODO Auto-generated method stub
	    return null;
    }
}


class MockColumn
{
	private String columnName;
	private int type;
	
	
	public MockColumn(int type, String name)
	{
		this.columnName = name;
		this.type = type;
	}
	
	public int getDisplaySize()
	{
		return 10;
	}
	
	public String getName()
	{
		return this.columnName;
	}
	
	public int getType()
	{
		return type;
	}
	
	public String toString()
	{
		return this.getName();
	}
}

class MockResultSetMetaData implements ResultSetMetaData
{

	public static MockResultSetMetaData getDefaultMetaData()
	{
		MockResultSetMetaData meta = new MockResultSetMetaData();
		
		meta.addColumn(new MockColumn(Types.VARCHAR, "VarcharColumn"));
		meta.addColumn(new MockColumn(Types.BOOLEAN, "BooleanColumn"));
		meta.addColumn(new MockColumn(Types.INTEGER, "IntegerColumn"));
		meta.addColumn(new MockColumn(Types.DOUBLE, "DoubleColumn"));
		meta.addColumn(new MockColumn(Types.TIME, "TimeColumn"));
		meta.addColumn(new MockColumn(Types.DATE, "DateColumn"));
		meta.addColumn(new MockColumn(Types.CHAR, "CharColumn"));
		meta.addColumn(new MockColumn(Types.JAVA_OBJECT, "JavaObjectColumn"));
		meta.addColumn(new MockColumn(Types.TIMESTAMP, "TimeStampColumn"));
		meta.addColumn(new MockColumn(Types.NUMERIC, "NumericColumn"));
		meta.addColumn(new MockColumn(Types.DECIMAL, "DecimalColumn"));
		meta.addColumn(new MockColumn(Types.BIGINT, "BigIntegerColumn"));
		
		return meta;
	}
	
	private ArrayList<MockColumn> columns = new ArrayList<MockColumn>();
	
	public MockResultSetMetaData()
	{
		
	}

	public void clear()
	{
		columns.clear();
	}
	
	public void addColumn(MockColumn c)
	{
		columns.add(c);
	}
	
	
	public int getColumnCount() throws SQLException
	{
		return columns.size();
	}

	public boolean isAutoIncrement(int column) throws SQLException
	{
		return false;
	}

	public boolean isCaseSensitive(int column) throws SQLException
	{
		return false;
	}

	public boolean isSearchable(int column) throws SQLException
	{
		return false;
	}

	public boolean isCurrency(int column) throws SQLException
	{
		return false;
	}

	public int isNullable(int column) throws SQLException
	{
		return ResultSetMetaData.columnNoNulls;
	}

	public boolean isSigned(int column) throws SQLException
	{
		return false;
	}

	public MockColumn getColumn(int column)
	{
		return columns.get(column - 1);
		
	}
	public int getColumnDisplaySize(int column) throws SQLException
	{
		return getColumn(column).getDisplaySize();
	}

	public String getColumnLabel(int column) throws SQLException
	{
		return this.getColumnName(column);
	}

	public String getColumnName(int column) throws SQLException
	{
		return getColumn(column).getName();
	}

	public String getSchemaName(int column) throws SQLException
	{
		return "SchemaName";
	}

	public int getPrecision(int column) throws SQLException
	{
		return 0;
	}

	public int getScale(int column) throws SQLException
	{
		return 0;
	}

	public String getTableName(int column) throws SQLException
	{
		return "Table" + column;
	}

	public String getCatalogName(int column) throws SQLException
	{
		return "Catalog" + column;
	}

	public int getColumnType(int column) throws SQLException
	{
		return getColumn(column).getType();
	}

	public String getColumnTypeName(int column) throws SQLException
	{
		return "ColumnTypeName" + column;
	}

	public boolean isReadOnly(int column) throws SQLException
	{
		return false;
	}

	public boolean isWritable(int column) throws SQLException
	{
		return false;
	}

	public boolean isDefinitelyWritable(int column) throws SQLException
	{
		return false;
	}

	public String getColumnClassName(int column) throws SQLException
	{
		return "ColumnClassName" + column;
	}

	@Override
    public boolean isWrapperFor(Class<?> arg0) throws SQLException
    {
	    // TODO Auto-generated method stub
	    return false;
    }

	@Override
    public <T> T unwrap(Class<T> arg0) throws SQLException
    {
	    // TODO Auto-generated method stub
	    return null;
    }

}

