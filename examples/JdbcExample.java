/*
 * 
 * 
 */

import java.io.StringWriter;
import java.sql.*;

import au.com.bytecode.opencsv.CSVWriter;

public class JdbcExample
{

	public static void main(String[] args)
	{
		ResultSet rs = null;

		try
		{
			rs = getResultSet();
			
			StringWriter sw = new StringWriter();
			
			CSVWriter writer = new CSVWriter(sw);
			writer.writeAll(rs, false);
			writer.close();
			
			System.out.println(sw);
		}
		catch (Exception ex)
		{
			ex.printStackTrace();
		}
		finally
		{
			if (rs != null)
			{
				try
				{
					rs.close();
				}
				catch (SQLException ignore)
				{
					// ignore
				}
			}
		}

	}

	private static ResultSet getResultSet()
	{
		return new MockResultSet(10);
	}
}
