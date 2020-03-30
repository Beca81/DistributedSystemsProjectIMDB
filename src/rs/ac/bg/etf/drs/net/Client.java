package rs.ac.bg.etf.drs.net;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class Client {

	public static void main(String[] args) {
		String host = "127.0.0.1";
		int port = 8080;

		try (Socket socket = new Socket(host, port)) {
			BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			PrintWriter writer = new PrintWriter(socket.getOutputStream(), true);
			
			String command = "time\n";
			writer.write(command);
			writer.flush();
			
			String line;
			while((line = reader.readLine())!= null) {
				System.out.println(line);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
