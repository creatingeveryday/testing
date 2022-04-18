
package scratch;

import org.junit.*;

public class CoverageTest {
   @Test
   public void noIncrementOfCount() {
      new Coverage().soleMethod();
   }
   
   @Test
   public void incrementOfCount() {
      Coverage c = new Coverage();
      c.count = 1;
      c.soleMethod();
   }
}
