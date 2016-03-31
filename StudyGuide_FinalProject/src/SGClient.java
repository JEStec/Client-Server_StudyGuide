/**
 * @author C.Son, G.Toncheva
 */

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;

public class SGClient {
	int port_number;
	InetAddress loopback;
	Socket client_socket;
	PrintWriter output;
	BufferedReader input;
	String response;

	/**
	 * @param port		int port of which port client/server run on.
	 * @throws IOException
	 */
	public SGClient(int port) throws IOException{
		port_number = port;
		loopback = InetAddress.getLoopbackAddress();
		client_socket = new Socket(loopback, port_number);
		output = new PrintWriter(client_socket.getOutputStream(), true);
		input = new BufferedReader(new InputStreamReader(client_socket.getInputStream()));
	}

	/**
	 * @param subject		String subject to request study guide.
	 * @return				return true/false of confirmation.
	 * @throws IOException
	 */
	public boolean find(String subject) throws IOException{
		//request study guide
		output.println(subject);
		
		//receive confirmation
		response = input.readLine();
		if (response.equals("false"))
			return false;
		else
			return true;
	}
	
	/**
	 * @param subject		String subject. 
	 * @throws IOException
	 */
	public void retrieve(String subject) throws IOException{
		int countQuestions = 0;
		while ((response = input.readLine()) != null){
			if (response.equals("Waiting")){
				countQuestions++;
				BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
				System.out.print("Your answer: ");
				String answer = reader.readLine();
				output.println(answer);
			}
			else if (response.contains("Score")){
				String[] split = response.split(" ");
				System.out.println("You answered " + split[1] + "/" + countQuestions + " questions correctly. \n");
				break;
			}
			else
				System.out.println(response);
		}
	}

	/**
	 * @return		return true/false if user wants to run program again.
	 * @throws IOException
	 */
	public boolean runAgain() throws IOException{
		//checks if user wants to do another study guide
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		System.out.println("Would you like to try another subject? (Yes/No)");
		String answer = reader.readLine();
		if (answer.equals("Yes") || answer.equals("yes")){
			output.println("rerun");
			return true;
		}
		else if (answer.equals("No") || answer.equals("no")){
			return false;
		}
		else {
			System.out.println("Unrecognized answer.");
			return false;
		}
	}
	
	// close client
	void close(){
		try {
			client_socket.close();
		}
		catch(Exception e){
			//ignore
		}
	}
}
