/**
 * 
 * @author J.Stec
 * 
 */

import java.util.ArrayList;
import java.util.List;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

public class QuestionHandler extends DefaultHandler {

    private List<Question> questionList = null;
    private List<Question> scienceList = null;
    private List<Question> readingList = null;
    private List<Question> historyList = null;
    
    private Question question = null;
 
    /**
     * @return		return List of all questions.
     */
    public List<Question> getQuestionList() {
        return questionList;
    }
    
    /**
     * @return		return List of science questions.
     */
    public List<Question> getScienceList() {
        return scienceList;
    }
    
    /**
     * @return		return List of reading questions.
     */
    public List<Question> getReadingList() {
        return readingList;
    }
    
    /**
     * @return		return List of history questions.
     */
    public List<Question> getHistoryList() {
        return historyList;
    }
    
    boolean boolTopic = false;
    boolean boolQuestion = false;
    boolean boolA = false;
    boolean boolB = false;
    boolean boolC = false;
    boolean boolD = false;
    boolean boolCorrect = false;
    
    /* (non-Javadoc)
     * @see org.xml.sax.helpers.DefaultHandler#startElement(java.lang.String, java.lang.String, java.lang.String, org.xml.sax.Attributes)
     */
    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
        if (qName.equalsIgnoreCase("record")) {
            question = new Question(); // new Question object
            if (questionList == null) // initiate lists of questions
                questionList = new ArrayList<>();
            if (scienceList == null)
                scienceList = new ArrayList<>();
            if (readingList == null)
                readingList = new ArrayList<>();
            if (historyList == null)
                historyList = new ArrayList<>();
        } 
        else if (qName.equalsIgnoreCase("Topic")) { // look for included features in XML file
            boolTopic = true;
        } 
        else if (qName.equalsIgnoreCase("Question")) {
            boolQuestion = true;
        } 
        else if (qName.equalsIgnoreCase("A")) {
            boolA = true;
        } 
        else if (qName.equalsIgnoreCase("B")) {
            boolB = true;
        }
        else if (qName.equalsIgnoreCase("C")) { 
        	boolC = true;
        }
        else if (qName.equalsIgnoreCase("D")) { 
        	boolD = true;
        }
        else if (qName.equalsIgnoreCase("Correct")) { 
        	boolCorrect = true;
        }
    }
    
    /* (non-Javadoc)
     * @see org.xml.sax.helpers.DefaultHandler#endElement(java.lang.String, java.lang.String, java.lang.String)
     */
    @Override
    public void endElement(String uri, String localName, String qName) throws SAXException {
        if (qName.equalsIgnoreCase("record")) { // add to appropriate list
            questionList.add(question);
            if (question.getTopic().equals("SCIENCE")) { scienceList.add(question); }
            if (question.getTopic().equals("READING")) { readingList.add(question); }
            if (question.getTopic().equals("HISTORY")) { historyList.add(question); }
        }
    } 
 
    /* (non-Javadoc)
     * @see org.xml.sax.helpers.DefaultHandler#characters(char[], int, int)
     */
    @Override
    public void characters(char ch[], int start, int length) throws SAXException { // set attributes per Question object
        if (boolTopic) {
            question.setTopic(new String(ch, start, length));
            boolTopic = false;
        } 
        else if (boolQuestion) {
            question.setQuestion(new String(ch, start, length));
            boolQuestion = false;
        } 
        else if (boolA) {
            question.setA(new String(ch, start, length));
            boolA = false;
        } 
        else if (boolB) {
            question.setB(new String(ch, start, length));
            boolB = false;
        }
        else if (boolC) {
            question.setC(new String(ch, start, length));
            boolC = false;
        } 
        else if (boolD) {
            question.setD(new String(ch, start, length));
            boolD = false;
        }
        else if (boolCorrect) { 
        	question.setCorrect(new String(ch, start, length));
        	boolCorrect = false;
        }
    }
}