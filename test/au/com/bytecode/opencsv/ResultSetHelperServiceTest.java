package au.com.bytecode.opencsv;
/**
 Copyright 2005 Bytecode Pty Ltd.

 Licensed under the Apache License, Version 2.0 (the "License");
 you may not use this file except in compliance with the License.
 You may obtain a copy of the License at

 http://www.apache.org/licenses/LICENSE-2.0

 Unless required by applicable law or agreed to in writing, software
 distributed under the License is distributed on an "AS IS" BASIS,
 WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 See the License for the specific language governing permissions and
 limitations under the License.
 */

import org.junit.Assert;
import org.junit.Test;
import org.mockito.Mockito;

import java.io.IOException;
import java.sql.*;
import java.text.SimpleDateFormat;

import static org.junit.Assert.assertArrayEquals;

/**
 * Created by IntelliJ IDEA.
 * User: scott
 * Date: Dec 13, 2009
 * Time: 12:34:58 AM
 * To change this template use File | Settings | File Templates.
 */
public class ResultSetHelperServiceTest {

    private static final String BUILDSTRING = "abcdefghijklmnopqrstuvwxyz";

    @Test
    public void canPrintColumnNames() throws SQLException {

        ResultSet resultSet = Mockito.mock(ResultSet.class);

        String[] expectedNames = {"name1", "name2", "name3"};

        ResultSetMetaData metaData = MockResultSetMetaDataBuilder.buildMetaData(expectedNames);

        Mockito.when(resultSet.getMetaData()).thenReturn(metaData);

        // end expects

        ResultSetHelperService service = new ResultSetHelperService();

        String[] columnNames = service.getColumnNames(resultSet);
        Assert.assertArrayEquals(expectedNames, columnNames);

    }

    @Test
    public void getObjectFromResultSet() throws SQLException, IOException {
        String[] expectedNames = {"object", "Null Object"};
        String[] realValues = {"foo", null};
        String[] expectedValues = {"foo", ""};
        int[] expectedTypes = {Types.JAVA_OBJECT, Types.JAVA_OBJECT};

        ResultSetMetaData metaData = MockResultSetMetaDataBuilder.buildMetaData(expectedNames, expectedTypes);
        ResultSet resultSet = MockResultSetBuilder.buildResultSet(metaData, realValues, expectedTypes);

        ResultSetHelperService service = new ResultSetHelperService();

        String[] columnValues = service.getColumnValues(resultSet);
        assertArrayEquals(expectedValues, columnValues);

    }

    @Test
    public void getBitFromResultSet() throws SQLException, IOException {

        String[] expectedNames = {"bit", "Null bit"};
        String[] realValues = {"1", null};
        String[] expectedValues = {"1", ""};
        int[] expectedTypes = {Types.BIT, Types.BIT};

        ResultSetMetaData metaData = MockResultSetMetaDataBuilder.buildMetaData(expectedNames, expectedTypes);
        ResultSet resultSet = MockResultSetBuilder.buildResultSet(metaData, realValues, expectedTypes);

        ResultSetHelperService service = new ResultSetHelperService();

        String[] columnValues = service.getColumnValues(resultSet);
        assertArrayEquals(expectedValues, columnValues);

    }

    @Test
    public void getBooleanFromResultSet() throws SQLException, IOException {
        String[] expectedNames = {"true", "false", "TRUE", "FALSE", "Null"};
        String[] realValues = {"true", "false", "TRUE", "FALSE", null};
        String[] expectedValues = {"true", "false", "true", "false", "false"};
        int[] expectedTypes = {Types.BOOLEAN, Types.BOOLEAN, Types.BOOLEAN, Types.BOOLEAN, Types.BOOLEAN};

        ResultSetMetaData metaData = MockResultSetMetaDataBuilder.buildMetaData(expectedNames, expectedTypes);
        ResultSet resultSet = MockResultSetBuilder.buildResultSet(metaData, realValues, expectedTypes);

        ResultSetHelperService service = new ResultSetHelperService();

        String[] columnValues = service.getColumnValues(resultSet);
        assertArrayEquals(expectedValues, columnValues);

    }

    @Test
    public void getBigIntFromResultSet() throws SQLException, IOException {
        String[] expectedNames = {"BigInt", "Null BigInt"};
        String[] realValues = {"100", null};
        String[] expectedValues = {"100", ""};
        int[] expectedTypes = {Types.BIGINT, Types.BIGINT};

        ResultSetMetaData metaData = MockResultSetMetaDataBuilder.buildMetaData(expectedNames, expectedTypes);
        ResultSet resultSet = MockResultSetBuilder.buildResultSet(metaData, realValues, expectedTypes);

        ResultSetHelperService service = new ResultSetHelperService();

        String[] columnValues = service.getColumnValues(resultSet);
        assertArrayEquals(expectedValues, columnValues);

    }

    @Test
    public void getBigDecimalFromResultSet() throws SQLException, IOException {

        String[] expectedNames = {"Decimal", "double", "float", "real", "numeric", "Null"};
        String[] realValues = {"1.1", "2.2", "3.3", "4.4", "5.5", null};
        String[] expectedValues = {"1.1", "2.2", "3.3", "4.4", "5.5", ""};
        int[] expectedTypes = {Types.DECIMAL, Types.DOUBLE, Types.FLOAT, Types.REAL, Types.NUMERIC, Types.DECIMAL};

        ResultSetMetaData metaData = MockResultSetMetaDataBuilder.buildMetaData(expectedNames, expectedTypes);
        ResultSet resultSet = MockResultSetBuilder.buildResultSet(metaData, realValues, expectedTypes);

        ResultSetHelperService service = new ResultSetHelperService();

        String[] columnValues = service.getColumnValues(resultSet);
        assertArrayEquals(expectedValues, columnValues);

    }

    @Test
    public void getIntegerFromResultSet() throws SQLException, IOException {
        String[] expectedNames = {"Integer", "tinyint", "smallint", "Null"};
        String[] realValues = {"1", "2", "3", null};
        String[] expectedValues = {"1", "2", "3", ""};
        int[] expectedTypes = {Types.INTEGER, Types.TINYINT, Types.SMALLINT, Types.INTEGER};

        ResultSetMetaData metaData = MockResultSetMetaDataBuilder.buildMetaData(expectedNames, expectedTypes);
        ResultSet resultSet = MockResultSetBuilder.buildResultSet(metaData, realValues, expectedTypes);

        ResultSetHelperService service = new ResultSetHelperService();

        String[] columnValues = service.getColumnValues(resultSet);
        assertArrayEquals(expectedValues, columnValues);

    }

    @Test
    public void getCharFromResultSet() throws SQLException, IOException {

        String[] expectedNames = {"longvarchar", "varchar", "char", "Null"};
        String[] realValues = {"a", "b", "c", null};
        String[] expectedValues = {"a", "b", "c", ""};
        int[] expectedTypes = {Types.LONGVARCHAR, Types.VARCHAR, Types.CHAR, Types.CHAR};

        ResultSetMetaData metaData = MockResultSetMetaDataBuilder.buildMetaData(expectedNames, expectedTypes);
        ResultSet resultSet = MockResultSetBuilder.buildResultSet(metaData, realValues, expectedTypes);

        ResultSetHelperService service = new ResultSetHelperService();

        String[] columnValues = service.getColumnValues(resultSet);
        assertArrayEquals(expectedValues, columnValues);

    }

    @Test
    public void getCharHandlesNulls() throws SQLException, IOException {

        String[] expectedNames = {"longvarchar", "varchar", "char", "Null"};
        String[] realValues = {"a", "b", "c", null};
        String[] expectedValues = {"a", "b", "c", ""};
        int[] expectedTypes = {Types.LONGVARCHAR, Types.VARCHAR, Types.CHAR, Types.CHAR};

        ResultSetMetaData metaData = MockResultSetMetaDataBuilder.buildMetaData(expectedNames, expectedTypes);
        ResultSet resultSet = MockResultSetBuilder.buildResultSet(metaData, realValues, expectedTypes);

        ResultSetHelperService service = new ResultSetHelperService();

        String[] columnValues = service.getColumnValues(resultSet, true);
        assertArrayEquals(expectedValues, columnValues);

    }

    @Test
    public void getUnsupportedFromResultSet() throws SQLException, IOException {

        String[] expectedNames = {"Array", "Null"};
        String[] realValues = {"1", null};
        String[] expectedValues = {"", ""};
        int[] expectedTypes = {Types.ARRAY, Types.ARRAY};

        ResultSetMetaData metaData = MockResultSetMetaDataBuilder.buildMetaData(expectedNames, expectedTypes);
        ResultSet resultSet = MockResultSetBuilder.buildResultSet(metaData, realValues, expectedTypes);

        ResultSetHelperService service = new ResultSetHelperService();

        String[] columnValues = service.getColumnValues(resultSet);
        assertArrayEquals(expectedValues, columnValues);

    }

    @Test
    public void getDateFromResultSet() throws SQLException, IOException {

        Date date = new Date(109, 11, 15); // 12/15/2009
        long dateInMilliSeconds = date.getTime();
        SimpleDateFormat dateFormat = new SimpleDateFormat(ResultSetHelperService.DEFAULT_DATE_FORMAT);

        String[] expectedNames = {"Date", "Null"};
        String[] realValues = {Long.toString(dateInMilliSeconds), null};
        String[] expectedValues = {dateFormat.format(date), ""};
        int[] expectedTypes = {Types.DATE, Types.DATE};

        ResultSetMetaData metaData = MockResultSetMetaDataBuilder.buildMetaData(expectedNames, expectedTypes);
        ResultSet resultSet = MockResultSetBuilder.buildResultSet(metaData, realValues, expectedTypes);

        ResultSetHelperService service = new ResultSetHelperService();

        String[] columnValues = service.getColumnValues(resultSet);
        assertArrayEquals(expectedValues, columnValues);

    }

    @Test
    public void getDateFromResultSetUsingCustomFormat() throws SQLException, IOException {

        String customDateFormat = "mm/dd/yy";
        Date date = new Date(109, 11, 15); // 12/15/2009
        long dateInMilliSeconds = date.getTime();
        SimpleDateFormat dateFormat = new SimpleDateFormat(customDateFormat);

        String[] expectedNames = {"Date", "Null"};
        String[] realValues = {Long.toString(dateInMilliSeconds), null};
        String[] expectedValues = {dateFormat.format(date), ""};
        int[] expectedTypes = {Types.DATE, Types.DATE};

        ResultSetMetaData metaData = MockResultSetMetaDataBuilder.buildMetaData(expectedNames, expectedTypes);
        ResultSet resultSet = MockResultSetBuilder.buildResultSet(metaData, realValues, expectedTypes);

        ResultSetHelperService service = new ResultSetHelperService();

        String[] columnValues = service.getColumnValues(resultSet, false, customDateFormat, null);
        assertArrayEquals(expectedValues, columnValues);

    }

    @Test
    public void getTimeFromResultSet() throws SQLException, IOException {

        Time time = new Time(12, 0, 0); // noon
        long dateInMilliSeconds = time.getTime();

        String[] expectedNames = {"Time", "Null"};
        String[] realValues = {Long.toString(dateInMilliSeconds), null};
        String[] expectedValues = {time.toString(), ""};
        int[] expectedTypes = {Types.TIME, Types.TIME};

        ResultSetMetaData metaData = MockResultSetMetaDataBuilder.buildMetaData(expectedNames, expectedTypes);
        ResultSet resultSet = MockResultSetBuilder.buildResultSet(metaData, realValues, expectedTypes);

        ResultSetHelperService service = new ResultSetHelperService();

        String[] columnValues = service.getColumnValues(resultSet);
        assertArrayEquals(expectedValues, columnValues);

    }

    @Test
    public void getTimestampFromResultSet() throws SQLException, IOException {
        Timestamp date = new Timestamp(109, 11, 15, 12, 0, 0, 0); // 12/15/2009 noon
        long dateInMilliSeconds = date.getTime();
        SimpleDateFormat timeFormat = new SimpleDateFormat(ResultSetHelperService.DEFAULT_TIMESTAMP_FORMAT);

        String[] expectedNames = {"Timestamp", "Null"};
        String[] realValues = {Long.toString(dateInMilliSeconds), null};
        String[] expectedValues = {timeFormat.format(date), ""};
        int[] expectedTypes = {Types.TIMESTAMP, Types.TIMESTAMP};

        ResultSetMetaData metaData = MockResultSetMetaDataBuilder.buildMetaData(expectedNames, expectedTypes);
        ResultSet resultSet = MockResultSetBuilder.buildResultSet(metaData, realValues, expectedTypes);

        ResultSetHelperService service = new ResultSetHelperService();

        String[] columnValues = service.getColumnValues(resultSet);
        assertArrayEquals(expectedValues, columnValues);

    }

    @Test
    public void getTimestampFromResultSetWithCustomFormat() throws SQLException, IOException {
        Timestamp date = new Timestamp(109, 11, 15, 12, 0, 0, 0); // 12/15/2009 noon
        long dateInMilliSeconds = date.getTime();
        String customFormat = "mm/dd/yy HH:mm:ss";
        SimpleDateFormat timeFormat = new SimpleDateFormat(customFormat);

        String[] expectedNames = {"Timestamp", "Null"};
        String[] realValues = {Long.toString(dateInMilliSeconds), null};
        String[] expectedValues = {timeFormat.format(date), ""};
        int[] expectedTypes = {Types.TIMESTAMP, Types.TIMESTAMP};

        ResultSetMetaData metaData = MockResultSetMetaDataBuilder.buildMetaData(expectedNames, expectedTypes);
        ResultSet resultSet = MockResultSetBuilder.buildResultSet(metaData, realValues, expectedTypes);

        ResultSetHelperService service = new ResultSetHelperService();

        String[] columnValues = service.getColumnValues(resultSet, false, null, customFormat);
        assertArrayEquals(expectedValues, columnValues);

    }

    @Test
    public void getClobFromResultSet() throws SQLException, IOException {
        String clobString = buildClobString(20);

        String[] expectedNames = {"Clob", "Null"};
        String[] realValues = {clobString, null};
        String[] expectedValues = {clobString, ""};
        int[] expectedTypes = {Types.CLOB, Types.CLOB};

        ResultSetMetaData metaData = MockResultSetMetaDataBuilder.buildMetaData(expectedNames, expectedTypes);
        ResultSet resultSet = MockResultSetBuilder.buildResultSet(metaData, realValues, expectedTypes);

        ResultSetHelperService service = new ResultSetHelperService();

        String[] columnValues = service.getColumnValues(resultSet);
        assertArrayEquals(expectedValues, columnValues);

    }

    @Test
    public void getEmptyClobFromResultSet() throws SQLException, IOException {
        String clobString = buildClobString(0);

        String[] expectedNames = {"Clob", "Null"};
        String[] realValues = {clobString, null};
        String[] expectedValues = {clobString, ""};
        int[] expectedTypes = {Types.CLOB, Types.CLOB};

        ResultSetMetaData metaData = MockResultSetMetaDataBuilder.buildMetaData(expectedNames, expectedTypes);
        ResultSet resultSet = MockResultSetBuilder.buildResultSet(metaData, realValues, expectedTypes);

        ResultSetHelperService service = new ResultSetHelperService();

        String[] columnValues = service.getColumnValues(resultSet);
        assertArrayEquals(expectedValues, columnValues);

    }

    @Test
    public void getLargeClobFromResultSet() throws SQLException, IOException {
        String clobString = buildClobString(ResultSetHelperService.CLOBBUFFERSIZE + 1);

        String[] expectedNames = {"Clob", "Null"};
        String[] realValues = {clobString, null};
        String[] expectedValues = {clobString, ""};
        int[] expectedTypes = {Types.CLOB, Types.CLOB};

        ResultSetMetaData metaData = MockResultSetMetaDataBuilder.buildMetaData(expectedNames, expectedTypes);
        ResultSet resultSet = MockResultSetBuilder.buildResultSet(metaData, realValues, expectedTypes);

        ResultSetHelperService service = new ResultSetHelperService();

        String[] columnValues = service.getColumnValues(resultSet);
        assertArrayEquals(expectedValues, columnValues);

    }

    private String buildClobString(int clobsize) {
        int iterations = clobsize / BUILDSTRING.length();
        int substrsize = clobsize % BUILDSTRING.length();
        StringBuilder sb = new StringBuilder(clobsize);

        for (int i = 0; i < iterations; i++) {
            sb.append(BUILDSTRING);
        }

        if (substrsize > 0) {
            sb.append(BUILDSTRING.substring(0, substrsize));
        }

        return sb.toString();
    }
}
