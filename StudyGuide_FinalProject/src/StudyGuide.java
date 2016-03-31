/**
 * @author Y.Gomez, J.Stec
 */

/*  CSC 376: Final Group Project
 *  Description: Implementing a Study Guide using a client and server
 *  Group members:
 *  	Claire Son
 *  	Geri Toncheva
 *  	Jessika Stec
 *  	Yovana Gomez
*/

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class StudyGuide {
	public static int serverport;
	public static int clientport;
	public static Subject subjectList;
	public static SGClient client;
	public static SGServer server;

	public static void main(String[] args) throws IOException {
		int len = args.length;
		subjectList = addSubjects();

		if (len <= 0 || len > 2){ // error
			System.err.println("Proper format: java StudyGuide [client/server] <portnumber>");
			//Client format: java StudyGuide client <portnumber>
			//Server format: java StudyGuide server <portnumber>
			return;
		}
		
		if (args[0].equals("client")){ // run client 
			clientport = Integer.parseInt(args[1]);
			System.out.println("Welcome! Here you'll be able to test yourself on different \n" +
					   		   "subjects using our practice tests/study guides.");
			client = new SGClient(clientport);
			runClient();
			client.close();
		}
		else if (args[0].equals("server")){ // run server
			serverport = Integer.parseInt(args[1]);
			server = new SGServer(serverport, subjectList);
			runServer();
			server.close();
		}
	}
	
	/**
	 * @return		return Subject list of subjects to select between.
	 */
	public static Subject addSubjects(){ // J.Stec
		Subject list = new Subject();
		list.add("Science");
		list.add("Reading");
		list.add("History");
		list.add("All");
		return list;
	}
	
	/**
	 * @throws IOException		
	 */
	public static void runClient() throws IOException{
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		System.out.println("\nPlease pick a subject: ");
		System.out.println(subjectList.getSubjects());
		String subject = reader.readLine();
		System.out.println("\nRetrieving study guide... \n");
		if (client.find(subject) == true){
			System.out.println("Study guide retrieved. \n");
			System.out.println("*** Let's begin! ***");
			System.out.println("After reading each question, choose one of the letters as your answer.\n");
			client.retrieve(subject);
			System.out.println("You have completed the study guide!\n");
			if (client.runAgain() == true){
				runClient();
			}
			else
				System.out.println("\nThank you and good luck studying!");
		}
		else {
			System.out.println("Could not find requested study guide.");
		}
	}
	
	/**
	 * @throws IOException
	 */
	public static void runServer() throws IOException{
		server.listen();
	}
	
}
