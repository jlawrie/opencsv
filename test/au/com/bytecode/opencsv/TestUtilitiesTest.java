package au.com.bytecode.opencsv;

import org.junit.Test;

import static junit.framework.Assert.assertEquals;

/**
 * Created by IntelliJ IDEA.
 * User: scott
 * Date: 12/20/10
 * Time: 2:57 PM
 * To change this template use File | Settings | File Templates.
 */
public class TestUtilitiesTest
{
   @Test
   public void displayStringArray()
   {
      String[] stringArray = new String[3];

      stringArray[0] = "a";
      stringArray[1] = "b";
      stringArray[2] = "c";

      assertEquals("Header\nNumber of elements:\t3\nelement 0:\ta\nelement 1:\tb\nelement 2:\tc\n",
                   TestUtilities.displayStringArray("Header", stringArray));
   }

}
