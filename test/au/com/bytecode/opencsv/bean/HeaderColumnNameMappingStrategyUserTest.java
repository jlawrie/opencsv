package au.com.bytecode.opencsv.bean;

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
import org.junit.Test;

import java.beans.IntrospectionException;
import java.io.IOException;
import java.io.StringReader;
import java.util.List;

import static org.junit.Assert.*;

public class HeaderColumnNameMappingStrategyUserTest {

    private static final String TEST_QUOTED_STRING = "\"id\",\"email\",\"profile_id\",\"first_name\",\"last_name\"\n" +
            "\"475592\",\"rbst218@yahoo.com\",\"bc1er1163\",\"\"\"CHia Sia Ta\",\"\"\n" +
            "\"12345\",\"kazem.naderi@tt.com\",\"bc234a\",\"kazem\",\"naderi\"";


    private List<MockUserBean> createTestParseResult(String parseString) {
        HeaderColumnNameMappingStrategy<MockUserBean> strat = new HeaderColumnNameMappingStrategy<MockUserBean>();
        strat.setType(MockUserBean.class);
        CsvToBean<MockUserBean> csv = new CsvToBean<MockUserBean>();
        return csv.parse(strat, new StringReader(parseString));
    }

    @Test
    public void testParse() {
        List<MockUserBean> list = createTestParseResult(TEST_QUOTED_STRING);
        assertNotNull(list);
        assertTrue(list.size() == 2);
        MockUserBean bean = list.get(0);
        assertEquals("rbst218@yahoo.com", bean.getEmail());
        assertEquals("\"CHia Sia Ta", bean.getFirst_Name());
        assertEquals("", bean.getLast_Name());
        assertEquals("bc1er1163", bean.getProfile_Id());
    }

    @Test
    public void verifyColumnNames() throws IOException, IntrospectionException {
        HeaderColumnNameMappingStrategy<MockUserBean> strat = new HeaderColumnNameMappingStrategy<MockUserBean>();
        strat.setType(MockUserBean.class);
        assertNull(strat.getColumnName(0));
        assertNull(strat.findDescriptor(0));

        StringReader reader = new StringReader(TEST_QUOTED_STRING);

        CSVReader csvReader = new CSVReader(reader);
        strat.captureHeader(csvReader);

        assertEquals("email", strat.getColumnName(1));
        assertEquals(strat.findDescriptor(1), strat.findDescriptor("email"));
        assertTrue(strat.matches("email", strat.findDescriptor("email")));
    }

}
