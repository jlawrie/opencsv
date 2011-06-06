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

import au.com.bytecode.opencsv.CSVWriter;

import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.io.Writer;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

/**
 * Allows to export Java beans content to a new CSV spreadsheet file.
 *
 * @author Kali &lt;kali.tystrit@gmail.com&gt;
 */
public class BeanToCsv<T> {

    public BeanToCsv() {
    }

    public boolean write(MappingStrategy<T> mapper, Writer writer,
                         List<?> objects) {
        return write(mapper, new CSVWriter(writer), objects);
    }

    public boolean write(MappingStrategy<T> mapper, CSVWriter csv,
                         List<?> objects) {
        if (objects == null || objects.isEmpty())
            return false;

        try {
            csv.writeNext(processHeader(mapper));
            List<Method> getters = findGetters(mapper);
            for (Object obj : objects) {
                String[] line = processObject(getters, obj);
                csv.writeNext(line);
            }
            return true;
        } catch (Exception e) {
            throw new RuntimeException("Error writing CSV !", e);
        }
    }

    protected String[] processHeader(MappingStrategy<T> mapper)
            throws IntrospectionException {
        List<String> values = new ArrayList<String>();
        int i = 0;
        PropertyDescriptor prop = mapper.findDescriptor(i);
        while (prop != null) {
            values.add(prop.getName());
            i++;
            prop = mapper.findDescriptor(i);
        }
        return values.toArray(new String[0]);
    }

    protected String[] processObject(List<Method> getters, Object bean)
            throws IntrospectionException, IllegalArgumentException,
            IllegalAccessException, InvocationTargetException {
        List<String> values = new ArrayList<String>();
        // retrieve bean values
        for (Method getter : getters) {
            Object value = getter.invoke(bean, (Object[]) null);
            if (value == null) {
                values.add("null");
            } else {
                values.add(value.toString());
            }
        }
        return values.toArray(new String[0]);
    }

    /**
     * Build getters list from provided mapper.
     */
    private List<Method> findGetters(MappingStrategy<T> mapper)
            throws IntrospectionException {
        int i = 0;
        PropertyDescriptor prop = mapper.findDescriptor(i);
        // build getters methods list
        List<Method> readers = new ArrayList<Method>();
        while (prop != null) {
            readers.add(prop.getReadMethod());
            i++;
            prop = mapper.findDescriptor(i);
        }
        return readers;
    }
}
