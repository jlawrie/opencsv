import java.io.FileReader;
import java.io.IOException;
import java.io.StringWriter;
import java.util.List;

import au.com.bytecode.opencsv.CSVReader;
import au.com.bytecode.opencsv.CSVWriter;

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
public class AddressExample {

	private static final String ADDRESS_FILE="examples/addresses.csv";
	
	public static void main(String[] args) throws IOException {
		
		CSVReader reader = new CSVReader(new FileReader(ADDRESS_FILE));
		String [] nextLine;
		while ((nextLine = reader.readNext()) != null) {
			System.out.println("Name: [" + nextLine[0] + "]\nAddress: [" + nextLine[1] + "]\nEmail: [" + nextLine[2] + "]");
		}
		
		// Try writing it back out as CSV to the console
		CSVReader reader2 = new CSVReader(new FileReader(ADDRESS_FILE));
		List<String[]> allElements = reader2.readAll();
		StringWriter sw = new StringWriter();
		CSVWriter writer = new CSVWriter(sw);
		writer.writeAll(allElements);
		
		System.out.println("\n\nGenerated CSV File:\n\n");
		System.out.println(sw.toString());
		
		
	}
}
