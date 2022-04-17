package iloveyouboss;

import static org.junit.Assert.*;

import org.junit.Test;

public class ProfileTest {
	
	//경로를 하나씩 하나씩 검증하는 테스트를 작성
	
	//1.  
	@Test
	public void matchAnswersFalseWhenMustMatchCriteriaNotMet() {
	
		Profile profile = new Profile("Bull Hockey, Inc.");
		
		Question question = new BooleanQuestion(1,"상여를 받았나요?");
		Answer profileAnswer = new Answer(question, Bool.FALSE);
		profile.add(profileAnswer);
		
		Criteria criteria = new Criteria();
		Answer criteriaAnswer = new Answer(question, Bool.TRUE);
		Criterion criterion = new Criterion(criteriaAnswer, Weight.MustMatch);
		criteria.add(criterion);
		
		boolean matches = profile.matches(criteria);
		
		assertFalse(matches);
		
	}

}
