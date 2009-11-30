package com.statera.cloudsort.service;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.apache.log4j.Logger;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class AnswerParser {

    static Logger log = Logger.getLogger("AnswerParser");
    XPathExpression expr1;
    XPathExpression expr2;

    public AnswerParser() {

	XPathFactory xpathFactory = XPathFactory.newInstance();

	XPath xpath = xpathFactory.newXPath();

	try {
	    expr1 = xpath.compile("QuestionFormAnswers/Answer[QuestionIdentifier='category']/FreeText/text()");
	    expr2 = xpath.compile("QuestionFormAnswers/Answer[QuestionIdentifier='category2']/FreeText/text()");
	} catch (XPathExpressionException e) {
	    log.error("failed to compile answer xpaths", e);
	}
    }

    public String getAnswer(String xml) {
	String answer = null;

	DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
	factory.setNamespaceAware(false); // never forget this!
	DocumentBuilder builder = null;
	try {
	    builder = factory.newDocumentBuilder();

	    Document doc;
	    
	    byte currentXMLBytes[] = xml.getBytes();
	    ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(currentXMLBytes); 
	    	 
	    doc = builder.parse(byteArrayInputStream);

	    answer = getText(doc,expr1);
	    String category2 = getText(doc,expr2);
	    
	    if(category2!=null&&category2.length()>0&& !category2.equals("0")){
		answer = category2;
	    }	    

	} catch (ParserConfigurationException e) {
	    log.error("unable to parse answer", e);
	} catch (XPathExpressionException e) {
	    log.error("unable to parse answer", e);
	} catch (SAXException e) {
	    log.error("unable to parse answer", e);
	} catch (IOException e) {
	    log.error("unable to parse answer", e);
	}
	return answer;
    }

    private String getText(Document doc,XPathExpression expr) throws XPathExpressionException {
	String text = null;
	NodeList nodes = (NodeList)expr.evaluate(doc, XPathConstants.NODESET);	    
	if (nodes != null && nodes.getLength() > 0) {
	text = nodes.item(0).getNodeValue();
	}
	return text;
    }
}
