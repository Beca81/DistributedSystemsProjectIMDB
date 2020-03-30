package rs.ac.bg.etf.drs.net;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {

	public static void main(String[] args) {
		int port = 8080;

		try (ServerSocket server = new ServerSocket(port);) {
			while (true) {
				new WorkingThread(server.accept()).start();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public static class WorkingThread extends Thread {
		Socket socket;

		public WorkingThread(Socket socket) {
			this.socket = socket;
		}

		public void run() {
			try (Socket socket = this.socket;
					BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
					PrintWriter writer = new PrintWriter(socket.getOutputStream(), true);) {
				System.out.println("Connected:" + socket.getInetAddress());
				
				String command = reader.readLine();
				System.out.println("Command: " + command + ", " + socket.getInetAddress());
				
				String result;
				switch (command.toLowerCase()) {
				case "time":
					long time = System.currentTimeMillis();
					result = time + "\n";
					break;
				case "host":
					result = socket.getLocalAddress().toString() + "\n";
					break;
				default:
					result = "bad command\n";
					break;
				}

				writer.write(result);
				writer.flush();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
}
