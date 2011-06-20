package au.com.bytecode.opencsv;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Types;
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
            setExpectToGetColumnValue(resultSet, i+1, columnValues[i], columnTypes[i], wnrl);
        }

        if (!wnrl.isEmpty())
        {
            // Why I have to do it this way I have no idea. but I cannot pass in just an array of Boolean I
            // have to break it up into a first value and the rest of the values.

            Boolean firstValue = wnrl.get(0);
            wnrl.remove(0);

            Boolean [] values = new Boolean[wnrl.size()];
            int i = 0;
            for (Boolean b : wnrl)
            {
                values[i++] = b;
            }

            if (!wnrl.isEmpty())
            {
                when(resultSet.wasNull()).thenReturn(firstValue, values);
            }
            else
            {
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
        }

    }
}
