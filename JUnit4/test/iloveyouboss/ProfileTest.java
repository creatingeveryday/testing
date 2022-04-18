package iloveyouboss;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class ProfileTest {
	private Profile profile;
	private BooleanQuestion question;
	private Criteria criteria;
		
	//테스트마다 새롭게 인스턴스 생성후 초기화 과정 호출 : 모든 테스트는 다른 테스트 결과에 영향받지 않음.
	//주의! 그런데 테스트 클래스에 static 필드가 있다면 새로운 인스턴스를 생성해도 상태가 공유될 것.  
	@Before 
	public void create() {
		//테스트가 2개일시 2번 호출됨. 테스트시 매번 인스턴스를 생성하므로 
		
		profile = new Profile("Bull Hockey, Inc.");
		question = new BooleanQuestion(1,"상여를 받았나요?");
		criteria = new Criteria();
	}
	
	//경로를 하나씩 하나씩 검증
	//1.  
	@Test
	public void matchAnswersFalseWhenMustMatchCriteriaNotMet() {

		profile.add(new Answer(question, Bool.FALSE));
		criteria.add( new Criterion(new Answer(question, Bool.TRUE), Weight.MustMatch));
		
		boolean matches = profile.matches(criteria);
		
		assertFalse(matches);
		
	}
	
	//2.
	@Test
	public void matchAnswersTrueForAnyDontCareCriteria() {
	
		profile.add(new Answer(question, Bool.FALSE));
		criteria.add(new Criterion(new Answer(question, Bool.TRUE), Weight.DontCare));
		
		boolean matches = profile.matches(criteria);
		
		assertTrue(matches);
		
	}

}
