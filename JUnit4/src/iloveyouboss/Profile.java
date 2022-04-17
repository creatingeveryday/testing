package iloveyouboss;

import java.util.*;

//구인자와 구직자는 다수의 객관식 혹은 yes-no question에 대해 답변하고 
//다른 측 기준에 맞는 프로파일로 점수를 매기고
//고용주와 고용자 모두의 관점에서 최상의 매치를 보여주는 서비스  

public class Profile { 
   private Map<String,Answer> answers = new HashMap<>();
   private int score;
   private String name;

   public Profile(String name) {
      this.name = name;
   }
   
   public String getName() {
      return name;
   }

   public void add(Answer answer) { 
      answers.put(answer.getQuestionText(), answer);
   }
   
   public boolean matches(Criteria criteria) { 
      score = 0;
      
      boolean kill = false;
      boolean anyMatches = false; 
      
      for (Criterion criterion: criteria) {    
         
    	  Answer answer = answers.get(criterion.getAnswer().getQuestionText()); //해당 기준이 Profile에 있는 답변과 맞는지 결정 
         
         boolean match = 
               criterion.getWeight() == Weight.DontCare || 
               answer.match(criterion.getAnswer());

         if (!match && criterion.getWeight() == Weight.MustMatch) {  
            kill = true;
         }
         if (match) {         
            score += criterion.getWeight().getValue();
         }
         anyMatches |= match;
      }
      
      if (kill)       
         return false;
      return anyMatches; 
   }

   public int score() {
      return score;
   }
}