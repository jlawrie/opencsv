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


import java.io.StringReader;
import java.util.List;

import au.com.bytecode.opencsv.bean.ColumnPositionMappingStrategy;
import au.com.bytecode.opencsv.bean.CsvToBean;

import junit.framework.TestCase;


public class ColumnPositionMappingStrategyTest extends TestCase {

    public void testParse() {
        String s = "" +
                "kyle,123456,emp123\n" +
                "jimmy,abcnum,cust09878";
        ColumnPositionMappingStrategy<TestBean> strat = new ColumnPositionMappingStrategy<TestBean>();
        strat.setType(TestBean.class);
        String[] columns = new String[] {"name", "orderNumber", "id"};
        strat.setColumnMapping(columns);

        CsvToBean<TestBean> csv = new CsvToBean<TestBean>();
        List<TestBean> list = csv.parse(strat, new StringReader(s));
        assertNotNull(list);
        assertTrue(list.size() == 2);
        TestBean bean = list.get(0);
        assertEquals("kyle", bean.getName());
        assertEquals("123456", bean.getOrderNumber());
        assertEquals("emp123", bean.getId());
    }

}
