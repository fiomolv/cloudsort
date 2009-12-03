package com.statera.cloudsort.entity;

import junit.framework.TestCase;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;

import com.statera.cloudsort.service.AnswerParser;

public class AnswerParserTest extends TestCase {
      
    private AnswerParser answerParser;
    
    public void setUp() throws Exception
    {
      super.setUp();
      ApplicationContext context = new FileSystemXmlApplicationContext("src/main/webapp/WEB-INF/spring.xml");
      answerParser = (AnswerParser)context.getBean("answerParser");      
    }
     
    public void test() {
	String xml = "<QuestionFormAnswers xmlns=\"http://mechanicalturk.amazonaws.com/AWSMechanicalTurkDataSchemas/2005-10-01/QuestionFormAnswers.xsd\">"
		+ "<Answer>"
		+ "<QuestionIdentifier>category</QuestionIdentifier>"
		+ "<FreeText>Coffee Grinders</FreeText>"
		+ "</Answer>"
		+ "<Answer>"
		+ "<QuestionIdentifier>Submit</QuestionIdentifier>"
		+ "<FreeText>Submit</FreeText>"
		+ "</Answer>"
		+ "</QuestionFormAnswers>";

	String answer = answerParser.getAnswer(xml);

	assertNotNull(answer);
	assertEquals(answer, "10521");


	
	
    }

}