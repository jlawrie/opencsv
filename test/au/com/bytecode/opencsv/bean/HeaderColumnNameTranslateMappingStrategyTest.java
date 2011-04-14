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


import org.junit.Test;

import java.io.StringReader;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.*;


public class HeaderColumnNameTranslateMappingStrategyTest {

    @Test
    public void testParse() {
        String s = "n,o,foo\n" +
                "kyle,123456,emp123\n" +
                "jimmy,abcnum,cust09878";
        HeaderColumnNameTranslateMappingStrategy<MockBean> strat = new HeaderColumnNameTranslateMappingStrategy<MockBean>();
        strat.setType(MockBean.class);
        Map<String, String> map = new HashMap<String, String>();
        map.put("n", "name");
        map.put("o", "orderNumber");
        map.put("foo", "id");
        strat.setColumnMapping(map);

        CsvToBean<MockBean> csv = new CsvToBean<MockBean>();
        List<MockBean> list = csv.parse(strat, new StringReader(s));
        assertNotNull(list);
        assertTrue(list.size() == 2);
        MockBean bean = list.get(0);
        assertEquals("kyle", bean.getName());
        assertEquals("123456", bean.getOrderNumber());
        assertEquals("emp123", bean.getId());
    }

    @Test
    public void getColumnNameReturnsNullIfColumnNumberIsTooLarge() {
        String s = "n,o,foo\n" +
                "kyle,123456,emp123\n" +
                "jimmy,abcnum,cust09878";
        HeaderColumnNameTranslateMappingStrategy<MockBean> strat = new HeaderColumnNameTranslateMappingStrategy<MockBean>();
        strat.setType(MockBean.class);
        Map<String, String> map = new HashMap<String, String>();
        map.put("n", "name");
        map.put("o", "orderNumber");
        map.put("foo", "id");
        strat.setColumnMapping(map);

        CsvToBean<MockBean> csv = new CsvToBean<MockBean>();
        List<MockBean> list = csv.parse(strat, new StringReader(s));

        assertEquals("name", strat.getColumnName(0));
        assertEquals("orderNumber", strat.getColumnName(1));
        assertEquals("id", strat.getColumnName(2));
        assertNull(strat.getColumnName(3));
    }

    @Test
    public void columnNameMappingShouldBeCaseInsensitive() {
        String s = "n,o,Foo\n" +
                "kyle,123456,emp123\n" +
                "jimmy,abcnum,cust09878";
        HeaderColumnNameTranslateMappingStrategy<MockBean> strat = new HeaderColumnNameTranslateMappingStrategy<MockBean>();
        strat.setType(MockBean.class);
        Map<String, String> map = new HashMap<String, String>();
        map.put("n", "name");
        map.put("o", "orderNumber");
        map.put("foo", "id");
        strat.setColumnMapping(map);
        assertNotNull(strat.getColumnMapping());

        CsvToBean<MockBean> csv = new CsvToBean<MockBean>();
        List<MockBean> list = csv.parse(strat, new StringReader(s));

        assertEquals("name", strat.getColumnName(0));
        assertEquals("orderNumber", strat.getColumnName(1));
        assertEquals("id", strat.getColumnName(2));
        assertNull(strat.getColumnName(3));
    }

}
