package au.com.bytecode.opencsv.bean;

import au.com.bytecode.opencsv.CSVReader;
import au.com.bytecode.opencsv.CSVWriter;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.io.IOException;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;

public class BeanToCsvTest {

    private static final String TEST_STRING = "\"name\",\"orderNumber\",\"num\"\n"
            + "\"kyle\",\"abc123456\",\"123\"\n"
            + "\"jimmy\",\"def098765\",\"456\"\n";

    private List<MockBean> testData;
    private BeanToCsv<MockBean> bean;

    @Before
    public void setUp() {
        bean = new BeanToCsv<MockBean>();

        testData = new ArrayList<MockBean>();
        MockBean mb = new MockBean();
        mb.setName("kyle");
        mb.setOrderNumber("abc123456");
        mb.setNum(123);
        testData.add(mb);
        mb = new MockBean();
        mb.setName("jimmy");
        mb.setOrderNumber("def098765");
        mb.setNum(456);
        testData.add(mb);
    }

    private MappingStrategy createErrorMappingStrategy() {
        return new MappingStrategy() {

            public PropertyDescriptor findDescriptor(int col)
                    throws IntrospectionException {
                throw new IntrospectionException("This is the test exception");
            }

            public Object createBean() throws InstantiationException,
                    IllegalAccessException {
                return null;
            }

            public void captureHeader(CSVReader reader) throws IOException {
            }
        };
    }

    @Test(expected = RuntimeException.class)
    public void throwRuntimeExceptionWhenExceptionIsThrown() {
        StringWriter sw = new StringWriter();
        CSVWriter writer = new CSVWriter(sw);
        bean.write(createErrorMappingStrategy(), writer, testData);
    }

    @Test
    public void testWriteQuotes() throws IOException {
        ColumnPositionMappingStrategy<MockBean> strat = new ColumnPositionMappingStrategy<MockBean>();
        strat.setType(MockBean.class);
        String[] columns = new String[]{"name", "orderNumber", "num"};
        strat.setColumnMapping(columns);

        StringWriter sw = new StringWriter();

        boolean value = bean.write(strat, sw, testData);

        Assert.assertTrue(value);

        String content = sw.getBuffer().toString();
        Assert.assertNotNull(content);
        Assert.assertEquals(TEST_STRING, content);
    }
}
