package rs.ac.bg.etf.drs.filmovi1;

import java.io.BufferedReader;
import java.io.FileReader;

public class Producer extends Thread {

	String reziseri;
	Buffer buffer;

	public Producer(String reziseri, Buffer buffer) {
		super();
		this.reziseri = reziseri;
		this.buffer = buffer;
	}

	@Override
	public void run() {

		try (BufferedReader reader = new BufferedReader(new FileReader(reziseri));) {
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
