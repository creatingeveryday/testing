
package scratch;

import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;
import java.io.*;
import java.util.*;
import org.junit.*;
import static scratch.PointMatcher.isNear;
// ...
import org.junit.rules.*;
// ...

public class AssertTest {

   class InsufficientFundsException extends RuntimeException {
	   private static final long serialVersionUID = 1L;
      
	   public InsufficientFundsException(String message) {
         super(message);
      }

   }

   class Account {
      int balance;
      String name;

      Account(String name) {
         this.name = name;
      }

      void deposit(int dollars) {
         balance += dollars;
      }

      void withdraw(int dollars) {
         if (balance < dollars) {
            throw new InsufficientFundsException("balance only " + balance);
         }
         balance -= dollars;
      }

      public String getName() {
         return name;
      }

      public int getBalance() {
         return balance;
      }

      public boolean hasPositiveBalance() {
         return balance > 0;
      }
   }

   class Customer {
      List<Account> accounts = new ArrayList<>();

      void add(Account account) {
         accounts.add(account);
      }

      Iterator<Account> getAccounts() {
         return accounts.iterator();
      }
   }

   private Account account;

   @Before
   public void createAccount() {
      account = new Account("an account name");
   }


   @Test
   public void hasPositiveBalance() {
      account.deposit(50);
      assertTrue(account.hasPositiveBalance());
   }


   
   @Test
   public void depositIncreasesBalance() {
      int initialBalance = account.getBalance();
      account.deposit(100);
      assertTrue(account.getBalance() > initialBalance);
      assertThat(account.getBalance(), equalTo(100));
   }

   
   @Test
   public void depositIncreasesBalance_hamcrestAssertTrue() {
      account.deposit(50);
      assertThat(account.getBalance() > 0, is(true));
   }
   
   @SuppressWarnings("deprecation")
   @Test
   public void comparesArraysPassing() {
	   assertThat(new String[] {"a", "b"}, equalTo(new String[] {"a", "b"}));
   }
   
//   @Ignore
   @SuppressWarnings("deprecation")
   @ExpectToFail
   @Test  //equalTo : 배열 및 컬렉션 객체 비교시에도 사용 가능
   public void comparesArraysFailing() {
      assertThat(new String[] {"a", "b", "c"}, equalTo(new String[] {"a", "b"}));
   }
   //java.lang.AssertionError: 
   //	Expected: ["a", "b"]
   //	     but: was ["a", "b", "c"]
			


//   @Ignore
   @ExpectToFail
   @Test
   public void comparesCollectionsFailing() {
      assertThat(Arrays.asList(new String[] {"a"}), 
            equalTo(Arrays.asList(new String[] {"a", "ab"})));
   }
   //java.lang.AssertionError: 
   //	Expected: <[a, ab]>
   //		but: was <[a]>



   @Test
   public void comparesCollectionsPassing() {
      assertThat(Arrays.asList(new String[] {"a"}), 
            equalTo(Arrays.asList(new String[] {"a"})));
   }
   
//   @Ignore
   @Test
   public void testWithWorthlessAssertionComment() {
      account.deposit(50);
      assertThat("account balance is 100", account.getBalance(), equalTo(10));
   }
   //단언 메시지 설명문은 유용한 정보를 좀 더 빠르게 전달할 수 있지만 
   //프로그래머가 작성하는거라 실제로 테스트하는 내용과 일치하는지 100% 확신할 수 없다. 실수하기 쉬운 부분. 
   // 더 좋은 방법은 테스트 코드 자체만으로 이해할 수 있게 작성하는 것이다. 
   // 테스트 메소드 이름 수정, 가독성이 우수한 햄크레스트 단언을 사용하는 것을 추천.

   @ExpectToFail
//   @Ignore
   @Test
   public void assertFailure() {
      assertTrue(account.getName().startsWith("xyz"));
   }

   @ExpectToFail
//   @Ignore
   @Test
   public void matchesFailure() {
      assertThat(account.getName(), startsWith("xyz"));
   }
   
   /*
    * Junit 햄크레이트 매처를 요긴하게 사용가능
    * 객체 타입 검사, 객체가 같은 참조값을 가지는지 검사, 컬렉션 요소 포함 및 조건 검사, 컬렉션 갯수...
    * 도메인에 맞는 사용자 정의 매처를 만들 수도 있다?    
    * */

   //ex) 가독성을 높이기 위해 'is' decorator 사용하는 경우도 있을수도... 개인취향차이임. 
   @SuppressWarnings("deprecation")
   @Test
   public void variousMatcherTests() {
      Account account = new Account("my big fat acct");
      assertThat(account.getName(), is(equalTo("my big fat acct")));
      
      //AND
      assertThat(account.getName(), allOf(startsWith("my"), endsWith("acct")));
      
      //OR 
      assertThat(account.getName(), anyOf(startsWith("my"), endsWith("loot")));
      
      assertThat(account.getName(), not(equalTo("cat")));
      
      assertThat(account.getName(), is(not(nullValue())));
      assertThat(account.getName(), is(notNullValue()));

      assertThat(account.getName(), isA(String.class));

      assertThat(account.getName(), is(notNullValue())); // 유용하지 않다.
      assertThat(account.getName(), equalTo("my big fat acct"));
      //첫번째 파라미터가 null을반환한다면 2번째 파라미터는 
   }

   @Test
   public void sameInstance() {
      Account a = new Account("a");
      Account aPrime = new Account("a");
      // TODO why needs to be fully qualified??
      assertThat(a, not(org.hamcrest.CoreMatchers.sameInstance(aPrime)));
   }

   @Test
   public void moreMatcherTests() {
      Account account = new Account(null);
      assertThat(account.getName(), is(nullValue()));
   }

   @Test
   @SuppressWarnings("unchecked")
   public void items() {
      List<String> names = new ArrayList<>();
      names.add("Moe");
      names.add("Larry");
      names.add("Curly");

      assertThat(names, hasItem("Curly"));

      assertThat(names, hasItems("Curly", "Moe"));

      assertThat(names, hasItem(endsWith("y")));

      assertThat(names, hasItems(endsWith("y"), startsWith("C"))); //warning!

      assertThat(names, not(everyItem(endsWith("y"))));
   }

   @Test
   @ExpectToFail 
//   @Ignore
   public void location() {
      Point point = new Point(4, 5);
      
      // WTF why do JUnit matches not include closeTo
//      assertThat(point.x, closeTo(3.6, 0.2));
//      assertThat(point.y, closeTo(5.1, 0.2));
      
      assertThat(point, isNear(3.6, 5.1));
   }
   
   @Test
   @ExpectToFail 
//   @Ignore
   public void classicAssertions() {
      Account account = new Account("acct namex");
      assertEquals("acct name", account.getName());
   }
   
   //@@@@@@@@@@@@@@@@@ 예외 처리  @@@@@@@@@@@@@@@@@@@@@@@@
   
   // 기대한 예외를 지정: 지정된 예외가 발생하면 테스트는 성공처리.
   @Test(expected=InsufficientFundsException.class)
   public void throwsWhenWithdrawingTooMuch() {
      account.withdraw(100);
   }
   
   @Test
   public void throwsWhenWithdrawingTooMuchTry() {
      try {
         account.withdraw(100);
         fail();
      }
      catch (InsufficientFundsException expected) {
    	 //기대된 예외 처리 성공시 테스트 통과
         assertThat(expected.getMessage(), equalTo("balance only 0"));
      }
   }
   
   //AOP
   
   @Rule
   public ExpectedException thrown = ExpectedException.none();  
   
   @Test
   public void exceptionRule() {
	   thrown.expect(InsufficientFundsException.class); 
	   thrown.expectMessage("balance only 0");
	   
//	   throw new InsufficientFundsException("balance only 0");
	   account.withdraw(100);  
   }
   
   //checked Exception은 그냥 에러를 던지면 된다.
   @Test
   public void readsFromTestFile() throws IOException {
      String filename = "test.txt";
      BufferedWriter writer = new BufferedWriter(new FileWriter(filename));
      writer.write("test data");
      
      writer.close();
//      throw new InsufficientFundsException("balance only 0");
      // ...
   }
   
   @After
   public void deleteForReadsFromTestFile() {
      new File("test.txt").delete();
   }
   
   @Test
   @Ignore("don't forget me!")
   public void somethingWeCannotHandleRightNow() {
      // ...
   }
   
   
   @Test
   public void doubles() {
      assertEquals(9.7, 10.0 - 0.3, 0.005);
   }
}
