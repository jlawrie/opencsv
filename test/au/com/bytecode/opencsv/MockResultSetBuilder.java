package au.com.bytecode.opencsv;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;

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

    public static ResultSet buildResultSet(ResultSetMetaData metaData, String[] columnValues) throws SQLException {
        ResultSet resultSet = mock(ResultSet.class);

        when(resultSet.getMetaData()).thenReturn(metaData);

        for (int i = 0; i < columnValues.length; i++) {
            when(resultSet.getObject(i + 1)).thenReturn(columnValues[i]);
        }

        return resultSet;
    }
}
