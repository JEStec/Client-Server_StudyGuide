/**
 *  @author C.Son, J.Stec, G.Toncheva
 */

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.SAXException;

public class SGServer {
	int port_number;
	ServerSocket server_socket;
	Socket client_socket;
	BufferedReader input;
	PrintWriter output;
	Subject subjList;
	BufferedReader file;
	List<Question> qList, questionList, scienceList, readingList, historyList = null;
	
	/**
	 * @param port				port number.
	 * @param subjs				subject selected.
	 * @throws IOException
	 */
	public SGServer(int port, Subject subjs) throws IOException{
		port_number = port;
		server_socket = new ServerSocket(port_number);
		subjList = subjs;
		System.out.println("Listening on port " + Integer.toString(port_number) + "...");
		client_socket = server_socket.accept();
		input = new BufferedReader(new InputStreamReader(client_socket.getInputStream()));
		output = new PrintWriter(client_socket.getOutputStream(), true);
		setupGuides();
	}
	
	/**
	 * parse questions and set up question lists 
	 */
	public void setupGuides(){ // J.Stec
		SAXParserFactory saxParserFactory = SAXParserFactory.newInstance();
	    try {
	        SAXParser saxParser = saxParserFactory.newSAXParser();
	        QuestionHandler handler = new QuestionHandler();
	        saxParser.parse(new File("src/guides/question_and_answers.xml"), handler);
	        questionList = handler.getQuestionList();
	        scienceList = handler.getScienceList();
	        readingList = handler.getReadingList();
	        historyList = handler.getHistoryList();
	    } 
	    catch (ParserConfigurationException | SAXException | IOException e) {
	        e.printStackTrace();
	    }
	}

	/**
	 * @throws IOException
	 */
	public void listen() throws IOException{
		//retrieve subject
		String subject = input.readLine();
		System.out.println("Requested subject: " + subject);

		//send confirmation
		if(subjList.contains(subject)){
			output.println("true");
			if (subject.toLowerCase().startsWith("s")) { openGuide(scienceList); }
			else if (subject.toLowerCase().startsWith("h")) { openGuide(historyList); }
			else if (subject.toLowerCase().startsWith("r")) { openGuide(readingList); }
			else if (subject.toLowerCase().startsWith("a")) { openGuide(questionList); }
			startGuide();
		}
		else
			output.println("false");
	}
	
	/**
	 * @param list		open question list.
	 */
	public void openGuide(List<Question> list) {
		qList = list;
	}
	
	/**
	 * @throws IOException
	 */
	public void startGuide() throws IOException{
		int countCorrect = 0;
		Question tempQ = null;
		List<Integer> askedQuestions = new ArrayList<Integer>();
		int sizeOfList = qList.size();
				
		while (askedQuestions.size() != sizeOfList) { 
			StringBuilder s = new StringBuilder();
			int r = (int) (Math.random() * sizeOfList);
			while (askedQuestions.contains(r)) { 
				r = (int) (Math.random() * sizeOfList);
			}
			askedQuestions.add(r);
			tempQ = qList.get(r);
			s.append(tempQ.getQuestion() +  
						   "\nA: " + tempQ.getA() + 
						   "\nB: " + tempQ.getB() + 
						   "\nC: " + tempQ.getC() + 
						   "\nD: " + tempQ.getD() );
			output.println(s);
			output.flush();
			output.println("Waiting");
			output.flush();
			String answer = input.readLine();
			System.out.println("User answered with " + answer + ".");
			if (answer.toUpperCase().equals(tempQ.getCorrect())) { 
				output.println("Correct!\n");
				output.flush();
				countCorrect++;
			}
			else {
		   		output.println("Incorrect. The answer is " + tempQ.getCorrect() + ".\n");
		   		output.flush();
		   	}
		}
		
		output.println("Score " + countCorrect);
		System.out.println("User has completed the guide.");
		runAgain();
	}
	
	/**
	 * @throws IOException		exception if string not "rerun".
	 */
	public void runAgain() throws IOException{
		String run = input.readLine();
		if (run != null && run.equals("rerun")){
			listen();
		}
	}
	
	// close server
	void close(){
		try {
			client_socket.close();
			System.out.println("Server shutting down...");
			server_socket.close();
		}
		catch(Exception e){
			// ignore
		}
	}
}
