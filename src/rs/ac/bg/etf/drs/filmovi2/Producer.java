package rs.ac.bg.etf.drs.filmovi2;

import java.io.BufferedReader;
import java.io.FileReader;

public class Producer extends Thread {

	String fileName;
	Buffer buffer;

	public Producer(String fileName, Buffer buffer) {
		super();
		this.fileName = fileName;
		this.buffer = buffer;
	}

	@Override
	public void run() {

		try (BufferedReader reader = new BufferedReader(new FileReader(fileName));) {
			String line = reader.readLine(); // linija sa zaglavljem
			while ((line = reader.readLine()) != null) {
				buffer.put(line);
			}
			buffer.put(null); // buffer.end == buffer.put(null)

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
