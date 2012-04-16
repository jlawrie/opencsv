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
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by IntelliJ IDEA.
 * User: scott
 * Date: Dec 12, 2009
 * Time: 9:54:50 AM
 * To change this template use File | Settings | File Templates.
 */
public class MockResultSetHelper implements ResultSetHelper {

    private String[] columnNames;
    private String[] columnValues;

    public MockResultSetHelper(String[] names, String[] values) {
        columnNames = names;
        columnValues = values;
    }

    public String[] getColumnNames(ResultSet rs) throws SQLException {
        return columnNames;
    }

    public String[] getColumnValues(ResultSet rs) throws SQLException, IOException {
        return columnValues;
    }
}
