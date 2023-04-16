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
import java.sql.ResultSetMetaData;
import java.sql.SQLException;

/**
 * Created by IntelliJ IDEA.
 * User: scott
 * Date: Dec 13, 2009
 * Time: 10:27:13 PM
 * To change this template use File | Settings | File Templates.
 */
public class MockResultSetMetaData implements ResultSetMetaData {
    String[] columnNames;
    int[] columnTypes;

    public void setColumnNames(String[] names) {
        columnNames = names;
    }

    public void setColumnTypes(int[] types) {
        columnTypes = types;
    }

    public int getColumnCount() throws SQLException {
        return columnNames.length;
    }

    public boolean isAutoIncrement(int i) throws SQLException {
        return false;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public boolean isCaseSensitive(int i) throws SQLException {
        return false;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public boolean isSearchable(int i) throws SQLException {
        return false;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public boolean isCurrency(int i) throws SQLException {
        return false;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public int isNullable(int i) throws SQLException {
        return 0;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public boolean isSigned(int i) throws SQLException {
        return false;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public int getColumnDisplaySize(int i) throws SQLException {
        return 0;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public String getColumnLabel(int i) throws SQLException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public String getColumnName(int i) throws SQLException {
        return columnNames[i-1];  //To change body of implemented methods use File | Settings | File Templates.
    }

    public String getSchemaName(int i) throws SQLException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public int getPrecision(int i) throws SQLException {
        return 0;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public int getScale(int i) throws SQLException {
        return 0;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public String getTableName(int i) throws SQLException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public String getCatalogName(int i) throws SQLException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public int getColumnType(int i) throws SQLException {
        return columnTypes[i-1];  
    }

    public String getColumnTypeName(int i) throws SQLException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public boolean isReadOnly(int i) throws SQLException {
        return false;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public boolean isWritable(int i) throws SQLException {
        return false;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public boolean isDefinitelyWritable(int i) throws SQLException {
        return false;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public String getColumnClassName(int i) throws SQLException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public <T> T unwrap(Class<T> tClass) throws SQLException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public boolean isWrapperFor(Class<?> aClass) throws SQLException {
        return false;  //To change body of implemented methods use File | Settings | File Templates.
    }
}
