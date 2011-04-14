package integrationTest.issue3189428;


import au.com.bytecode.opencsv.CSVParser;
import au.com.bytecode.opencsv.CSVReader;
import au.com.bytecode.opencsv.CSVWriter;
import au.com.bytecode.opencsv.bean.ColumnPositionMappingStrategy;
import au.com.bytecode.opencsv.bean.CsvToBean;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class CsvSample {
    String filePath = "test/integrationTest/issue3189428/mysample.csv";

    public static void main(String[] args)
            throws Exception {
        CsvSample sample = new CsvSample();
        sample.doSample();
    }

    public CsvSample() {
    }

    public void doSample()
            throws Exception {
        // First write

        String[] fields = new String[5];
        fields[0] = "field1";
        fields[1] = "3.0";
        fields[2] = "3,147.25";
        fields[3] = "$3,147.26";
        // failing string
        fields[4] = "Joe said, \"This is a test of a \nlong broken string,\" and Sally said, \"I bet it won't work.\" ";
        // working string
        // fields[4] = "Joe said, \"This is a test of a \nlong broken string,\" and Sally said, \"I bet it won't work.\"";

        CSVWriter writer = new CSVWriter(new FileWriter(filePath));
        writer.writeNext(fields); // let's make 3 rows so we can see it cleanly in Excel.
        writer.writeNext(fields);
        writer.writeNext(fields);
        writer.close();

        testRawCsvRead(fields[4]);
        testMappingStrategyRead(fields[4]);

        System.out.println("\nComplete.  File written out to " + filePath);

    }

    /**
     * This approach seems to work correctly, even with embedded newlines.
     *
     * @param originalCommentText
     * @throws FileNotFoundException
     * @throws IOException
     */
    protected void testRawCsvRead(String originalCommentText)
            throws FileNotFoundException, IOException {
        CSVReader reader = new CSVReader(new FileReader(filePath));
        String[] nextLine = null;
        int count = 0;
        while ((nextLine = reader.readNext()) != null) {
            if (!nextLine[0].equals("field1")) {
                System.out.println("RawCsvRead Assert Error: Name is wrong.");
            }
            if (!nextLine[1].equals("3.0")) {
                System.out.println("RawCsvRead Assert Error: Value is wrong.");
            }
            if (!nextLine[2].equals("3,147.25")) {
                System.out.println("RawCsvRead Assert Error: Amount1 is wrong.");
            }
            if (!nextLine[3].equals("$3,147.26")) {
                System.out.println("RawCsvRead Assert Error: Currency is wrong.");
            }
            System.out.println("Field 4 read: " + nextLine[4]);
            if (!nextLine[4].equals(originalCommentText)) {
                System.out.println("RawCsvRead Assert Error: Comment is wrong.");
            }
            count++;
        }
        if (count != 3) {
            System.out.println("RawCsvRead Assert Error: Count of lines is wrong.");
        }

    }

    /**
     * This approach seems to fail with embedded newlines; that might be a weakness of
     * the mapping strategy support classes.
     *
     * @param originalCommentText
     * @throws FileNotFoundException
     */
    protected void testMappingStrategyRead(String originalCommentText)
            throws FileNotFoundException {
        ColumnPositionMappingStrategy mappingStrategy = new ColumnPositionMappingStrategy();
        mappingStrategy.setType(MyBean.class);
        String[] columns = new String[]{"name", "value", "amount1", "currency", "comments"}; // the fields to bind to in your JavaBean
        mappingStrategy.setColumnMapping(columns);

        CsvToBean csv = new CsvToBean();
        CSVReader reader = new CSVReader(new FileReader(filePath), CSVParser.DEFAULT_SEPARATOR, CSVParser.DEFAULT_QUOTE_CHARACTER, CSVParser.DEFAULT_ESCAPE_CHARACTER, 0, false, false);
        List<MyBean> list = csv.parse(mappingStrategy, reader);

        if (list.size() != 3) {
            System.out.println("Error - list size is wrong.");
        }
        MyBean myBean = list.get(2);
        if (!myBean.getName().equals("field1")) {
            System.out.println("MappingStrategy Assert Error: Name is wrong.");
        }
        if (!myBean.getValue().equals("3.0")) {
            System.out.println("MappingStrategy Assert Error: Value is wrong.");
        }
        if (!myBean.getAmount1().equals("3,147.25")) {
            System.out.println("MappingStrategy Assert Error: Amount1 is wrong.");
        }
        if (!myBean.getCurrency().equals("$3,147.26")) {
            System.out.println("MappingStrategy Assert Error: Currency is wrong.");
        }
        printfield("MyBeanComments:         ", myBean.getComments());
        printfield("OriginalCommentText:    ", originalCommentText);
        if (!myBean.getComments().equals(originalCommentText)) {
            System.out.println("MappingStrategy Assert Error: Comment is wrong.");
        }
    }

    private void printfield(String header, String field) {
        System.out.println(header + field);
        System.out.println("fieldlen: " + field.length());
    }

    public static class MyBean {
        String name;
        String value;
        String amount1;
        String currency;
        String comments;

        public MyBean() {
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getValue() {
            return value;
        }

        public void setValue(String value) {
            this.value = value;
        }

        public String getAmount1() {
            return amount1;
        }

        public void setAmount1(String amount1) {
            this.amount1 = amount1;
        }

        public String getCurrency() {
            return currency;
        }

        public void setCurrency(String currency) {
            this.currency = currency;
        }

        public String getComments() {
            return comments;
        }

        public void setComments(String comments) {
            this.comments = comments;
        }

    }

}
