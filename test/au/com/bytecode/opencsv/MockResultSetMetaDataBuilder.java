package au.com.bytecode.opencsv;

import org.mockito.Mockito;

import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Types;

public class MockResultSetMetaDataBuilder {

    public static ResultSetMetaData buildMetaData(String[] columnNames) throws SQLException {

        ResultSetMetaData metaData = Mockito.mock(ResultSetMetaData.class);

        Mockito.when(metaData.getColumnCount()).thenReturn(columnNames.length);
        for (int i = 0; i < columnNames.length; i++) {
            Mockito.when(metaData.getColumnName(i + 1)).thenReturn(columnNames[i]);
            Mockito.when(metaData.getColumnType(i + 1)).thenReturn(Types.VARCHAR);
        }

        return metaData;
    }

    public static ResultSetMetaData buildMetaData(String[] columnNames, int[] columnTypes) throws SQLException {

        ResultSetMetaData metaData = Mockito.mock(ResultSetMetaData.class);

        Mockito.when(metaData.getColumnCount()).thenReturn(columnNames.length);
        for (int i = 0; i < columnNames.length; i++) {
            Mockito.when(metaData.getColumnName(i + 1)).thenReturn(columnNames[i]);
            Mockito.when(metaData.getColumnType(i + 1)).thenReturn(columnTypes[i]);
        }

        return metaData;
    }
}