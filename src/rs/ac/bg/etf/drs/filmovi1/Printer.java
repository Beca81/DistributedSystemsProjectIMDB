package rs.ac.bg.etf.drs.filmovi1;

/**
 * Klasa koja uzima iz bafera filmove i ispisuje ih.
 */
public class Printer extends Thread {

	BufferInterface buffer;

	public Printer(BufferInterface buffer) {
		this.buffer = buffer;
	}

	@Override
	public void run() {
		String line = null;
		while ((line = buffer.get()) != null) {
			System.out.println(line);
		}
	}

}
