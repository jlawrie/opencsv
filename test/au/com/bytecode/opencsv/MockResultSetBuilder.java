package au.com.bytecode.opencsv;

import javax.sql.rowset.serial.SerialClob;
import java.math.BigDecimal;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import static java.sql.Types.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Created by IntelliJ IDEA.
 * User: sconway
 * Date: 6/14/11
 * Time: 10:40 PM
 * To change this template use File | Settings | File Templates.
 */
public class MockResultSetBuilder {

    public static ResultSet buildResultSet(ResultSetMetaData metaData, String[] columnValues, int[] columnTypes) throws SQLException {
        ResultSet resultSet = mock(ResultSet.class);
        List<Boolean> wnrl = new ArrayList<Boolean>();
        when(resultSet.getMetaData()).thenReturn(metaData);

        for (int i = 0; i < columnValues.length; i++) {
            setExpectToGetColumnValue(resultSet, i + 1, columnValues[i], columnTypes[i], wnrl);
        }

        if (!wnrl.isEmpty()) {
            // Why I have to do it this way I have no idea. but I cannot pass in just an array of Boolean I
            // have to break it up into a first value and the rest of the values.

            Boolean firstValue = wnrl.get(0);
            wnrl.remove(0);

            Boolean[] values = new Boolean[wnrl.size()];
            int i = 0;
            for (Boolean b : wnrl) {
                values[i++] = b;
            }

            if (!wnrl.isEmpty()) {
                when(resultSet.wasNull()).thenReturn(firstValue, values);
            } else {
                when(resultSet.wasNull()).thenReturn(firstValue);
            }
        }

        return resultSet;
    }

    private static void setExpectToGetColumnValue(ResultSet rs, int index, String value, int type, List<Boolean> wnrl) throws SQLException {

        switch (type) {
            case BIT:
            case JAVA_OBJECT:
                when(rs.getObject(index)).thenReturn(value);
                break;
            case BOOLEAN:
                when(rs.getBoolean(index)).thenReturn(Boolean.valueOf(value));
                break;
            case BIGINT:
                when(rs.getLong(index)).thenReturn(value != null ? Long.valueOf(value).longValue() : 0);
                wnrl.add(value == null);
                break;
            case Types.DECIMAL:
            case Types.DOUBLE:
            case Types.FLOAT:
            case Types.REAL:
            case Types.NUMERIC:
                when(rs.getBigDecimal(index)).thenReturn(value != null ? new BigDecimal(value) : null);
                break;
            case Types.INTEGER:
            case Types.TINYINT:
            case Types.SMALLINT:
                when(rs.getInt(index)).thenReturn(value != null ? new Integer(value).intValue() : 0);
                wnrl.add(value == null);
                break;
            case ResultSetHelperService.NVARCHAR: // todo : use rs.getNString
            case ResultSetHelperService.NCHAR: // todo : use rs.getNString
            case ResultSetHelperService.LONGNVARCHAR: // todo : use rs.getNString
            case Types.LONGVARCHAR:
            case Types.VARCHAR:
            case Types.CHAR:
                when(rs.getString(index)).thenReturn(value);
                break;
            case Types.DATE:
                Date date = createDateFromMilliSeconds(value);
                when(rs.getDate(index)).thenReturn(date);
                break;
            case Types.TIME:
                Time time = createTimeFromMilliSeconds(value);
                when(rs.getTime(index)).thenReturn(time);
                break;
            case Types.TIMESTAMP:
                Timestamp ts = createTimeStampFromMilliSeconds(value);
                when(rs.getTimestamp(index)).thenReturn(ts);
                break;
            case ResultSetHelperService.NCLOB: // todo : use rs.getNClob
            case Types.CLOB:
                Clob c = createClobFromString(value);
                when(rs.getClob(index)).thenReturn(c);
                break;

        }

    }

    private static Clob createClobFromString(String value) throws SQLException {
        return value != null ? new SerialClob(value.toCharArray()) : null;
    }

    private static Date createDateFromMilliSeconds(String value) {
        Date date;

        if (value == null) {
            date = null;
        } else {
            Long milliseconds = Long.valueOf(value);
            date = new Date(milliseconds);
        }
        return date;
    }

    private static Time createTimeFromMilliSeconds(String value) {
        Time time;

        if (value == null) {
            time = null;
        } else {
            Long milliseconds = Long.valueOf(value);
            time = new Time(milliseconds);
        }
        return time;
    }

    private static Timestamp createTimeStampFromMilliSeconds(String value) {
        Timestamp timestamp;

        if (value == null) {
            timestamp = null;
        } else {
            Long milliseconds = Long.valueOf(value);
            timestamp = new Timestamp(milliseconds);
        }
        return timestamp;
    }

    public static ResultSet buildResultSet(String[] header, String[] values, int numRows) throws SQLException {
        ResultSet rs = mock(ResultSet.class);
        ResultSetMetaData rsmd = MockResultSetMetaDataBuilder.buildMetaData(header);

        when(rs.getMetaData()).thenReturn(rsmd);

        for (int i = 0; i < values.length; i++) {
            buildStringExpects(rs, i + 1, values[i], numRows);
        }
        buildNextExpect(rs, numRows);
        return rs;  //To change body of created methods use File | Settings | File Templates.
    }

    private static void buildStringExpects(ResultSet rs, int index, String value, int numRows) throws SQLException {

        if (numRows > 1) {
            String[] columnValues = new String[numRows];
            for (int i = 0; i < numRows - 1; i++) {
                columnValues[i] = value;
            }
            when(rs.getString(index)).thenReturn(value, columnValues);
        } else {
            when(rs.getString(index)).thenReturn(value);
        }

    }

    private static void buildNextExpect(ResultSet rs, int numRows) throws SQLException {
        if (numRows == 1) {
            when(rs.next()).thenReturn(true, false);
        } else {
            Boolean[] nextArray = new Boolean[numRows];
            for (int i = 0; i < numRows; i++) {
                nextArray[i] = i < (numRows - 1);
            }
            when(rs.next()).thenReturn(true, nextArray);
        }

    }
}
