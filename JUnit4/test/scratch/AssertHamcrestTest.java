
package scratch;

import org.junit.*;
import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;
import static org.hamcrest.number.IsCloseTo.*;
// ...

//CloseTo()
public class AssertHamcrestTest {
   @Test
//   @Ignore
   @ExpectToFail
   public void assertDoublesEqual() {
      assertThat(2.32 * 3, equalTo(6.96));
   }
   //java.lang.AssertionError: 
   //	Expected: <6.96>
   //		but: was <6.959999999999999>
   // float와 doubl의 두 부동소수점 수를 비교할때는 두수가 벌어질 수 있는 공차, 허용 오차를 지정해줘야함.
	
   
//   @Test
//   public void assertWithTolerance() {
//      assertTrue(Math.abs((2.32 * 3) - 6.96) < 0.0005);
//   }
   
   @SuppressWarnings("deprecation")
   @Test
   public void assertDoublesCloseTo() {
      assertThat(2.32 * 3, closeTo(6.96, 0.0005));
   }
}
