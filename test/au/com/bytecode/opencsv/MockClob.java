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
import java.io.*;
import java.sql.Clob;
import java.sql.SQLException;

/**
 * Created by IntelliJ IDEA.
 * User: scott
 * Date: Dec 15, 2009
 * Time: 10:10:02 PM
 * To change this template use File | Settings | File Templates.
 */
public class MockClob implements Clob {

    private String clobValue;

    public MockClob(String value) {
        clobValue = value;
    }

    public long length() throws SQLException {
        return 0;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public String getSubString(long l, int i) throws SQLException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public Reader getCharacterStream() throws SQLException {
        return new StringReader(clobValue);  //To change body of implemented methods use File | Settings | File Templates.
    }

    public InputStream getAsciiStream() throws SQLException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public long position(String s, long l) throws SQLException {
        return 0;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public long position(Clob clob, long l) throws SQLException {
        return 0;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public int setString(long l, String s) throws SQLException {
        return 0;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public int setString(long l, String s, int i, int i1) throws SQLException {
        return 0;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public OutputStream setAsciiStream(long l) throws SQLException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public Writer setCharacterStream(long l) throws SQLException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public void truncate(long l) throws SQLException {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    public void free() throws SQLException {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    public Reader getCharacterStream(long l, long l1) throws SQLException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }
}
