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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import au.com.bytecode.opencsv.bean.CsvToBean;
import au.com.bytecode.opencsv.bean.HeaderColumnNameTranslateMappingStrategy;

import junit.framework.TestCase;


public class HeaderColumnNameTranslateMappingStrategyTest extends TestCase {

    public void testParse() {
        String s = "n,o,foo\n" +
                "kyle,123456,emp123\n" +
                "jimmy,abcnum,cust09878";
        HeaderColumnNameTranslateMappingStrategy strat = new HeaderColumnNameTranslateMappingStrategy();
        strat.setType(TestBean.class);
        Map map = new HashMap();
        map.put("n", "name");
        map.put("o", "orderNumber");
        map.put("foo", "id");
        strat.setColumnMapping(map);

        CsvToBean csv = new CsvToBean();
        List list = csv.parse(strat, new StringReader(s));
        assertNotNull(list);
        assertTrue(list.size() == 2);
        TestBean bean = (TestBean)list.get(0);
        assertEquals("kyle", bean.getName());
        assertEquals("123456", bean.getOrderNumber());
        assertEquals("emp123", bean.getId());
    }

}
