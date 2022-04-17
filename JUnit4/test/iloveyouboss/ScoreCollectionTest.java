package iloveyouboss;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.*;

import org.junit.Test;

public class ScoreCollectionTest {

	@Test
	public void test() {
//		fail("실패");
	}
	
	@SuppressWarnings("deprecation")
	@Test
	public void answerArithmeticMeanOfTwoNumbers() {
		
		ScoreCollection collection = new ScoreCollection();
		collection.add(() -> 5);//간편하게 람다식을 사용하여 인스턴스에서 원하는 값을 반환하도록 설정. 
		collection.add(() -> 7);
		
		int actualResult = collection.arithmeticMean();
		
		assertThat(actualResult, equalTo(6));
	}

}
