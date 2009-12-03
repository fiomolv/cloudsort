package com.statera.cloudsort.service;

import java.io.ByteArrayInputStream;
import java.io.IOException;

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

import com.statera.cloudsort.dao.TurkDAO;
import com.statera.cloudsort.entity.Category;

public class AnswerParser {

    public static final String EMPTY_ANSWER = "EMPTY";

    private TurkDAO turkDAO;

    public void setTurkDAO(TurkDAO turkDAO) {
	this.turkDAO = turkDAO;
    }

    static Logger log = Logger.getLogger("AnswerParser");
    XPathExpression expr1;

    public AnswerParser() {

	XPathFactory xpathFactory = XPathFactory.newInstance();

	XPath xpath = xpathFactory.newXPath();

	try {
	    expr1 = xpath
		    .compile("QuestionFormAnswers/Answer[QuestionIdentifier='category']/FreeText/text()");
	   
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
	    ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(
		    currentXMLBytes);

	    doc = builder.parse(byteArrayInputStream);

	    String category = getText(doc, expr1);
	    
	    if(category!=null&&category.length()>2 && category.startsWith("|")){
		category = category.substring(1);
	    }
	    
	    if(category!=null&&category.length()>2 && category.endsWith("|")){
		category = category.substring(0,category.lastIndexOf("|"));
	    }	    
	    try{
		
		Category cat = turkDAO.getCategoryByName(category);
		if (cat != null) {
		    answer = cat.getCategoryCode();
		}else{
		    log.info("unable to look up categoryCode for " + category);
		}
	    }catch(Exception e){
		    log.info("unable to look up categoryCode for " + category);

	    }

	    if (xml != null && xml.length() > 0 && answer == null) {
		answer = AnswerParser.EMPTY_ANSWER;
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

    private String getText(Document doc, XPathExpression expr)
	    throws XPathExpressionException {
	String text = null;
	NodeList nodes = (NodeList) expr.evaluate(doc, XPathConstants.NODESET);
	if (nodes != null && nodes.getLength() > 0) {
	    text = nodes.item(0).getNodeValue();
	}
	return text;
    }

}
