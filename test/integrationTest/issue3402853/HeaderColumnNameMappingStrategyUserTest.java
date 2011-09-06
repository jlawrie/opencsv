package integrationTest.issue3402853;

/**
 Copyright 2007 Kyle Miller.

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


import au.com.bytecode.opencsv.CSVReader;
import au.com.bytecode.opencsv.bean.CsvToBean;
import au.com.bytecode.opencsv.bean.HeaderColumnNameMappingStrategy;
import org.junit.Test;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class HeaderColumnNameMappingStrategyUserTest {

    private static final String USER_FILE = "test/integrationTest/issue3402853/user.csv";

    private List<MockUserBean> createTestParseResult() throws FileNotFoundException {
        CSVReader reader = new CSVReader(new FileReader(USER_FILE));
        HeaderColumnNameMappingStrategy<MockUserBean> strat = new HeaderColumnNameMappingStrategy<MockUserBean>();
        strat.setType(MockUserBean.class);
        CsvToBean<MockUserBean> csv = new CsvToBean<MockUserBean>();
        return csv.parse(strat, reader);
    }

    @Test
    public void testParse() throws FileNotFoundException {
        List<MockUserBean> list = createTestParseResult();
        assertNotNull(list);
        assertEquals(2, list.size());
        MockUserBean bean = list.get(0);
        assertEquals("rbst218@yahoo.com", bean.getEmail());
        assertEquals("\"\"CHia Sia Ta", bean.getFirst_Name());
        assertEquals("", bean.getLast_Name());
        assertEquals("bc1er1163", bean.getProfile_Id());
    }

}
