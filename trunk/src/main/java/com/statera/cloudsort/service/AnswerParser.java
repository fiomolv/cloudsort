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
    XPathExpression expr2;
    XPathExpression expr3;

    public AnswerParser() {

	XPathFactory xpathFactory = XPathFactory.newInstance();

	XPath xpath = xpathFactory.newXPath();

	try {
	    expr1 = xpath
		    .compile("QuestionFormAnswers/Answer[QuestionIdentifier='category']/FreeText/text()");
	    expr2 = xpath
		    .compile("QuestionFormAnswers/Answer[QuestionIdentifier='category2']/FreeText/text()");
	    expr3 = xpath
		    .compile("QuestionFormAnswers/Answer[QuestionIdentifier='category0']/FreeText/text()");
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

	    answer = getText(doc, expr1);
	    String category2 = getText(doc, expr2);
	    String category0 = getText(doc, expr3);

	    // category 0 is tier 1 answer selected by tier2 worker
	    if (category0 != null && category0.length() > 0
		    && !category0.equals("0")) {
		answer = category0;

		// cateogry is autocomplete answer.. need to convert from name
		// to code
	    } else if (category2 != null && category2.length() > 0
		    && !category2.equals("0")) {
		Category cat = turkDAO.getCategoryByName(category2);
		if (cat != null) {
		    answer = cat.getCategoryCode();
		}
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
