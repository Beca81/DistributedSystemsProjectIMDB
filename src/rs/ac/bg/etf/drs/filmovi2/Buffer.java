package rs.ac.bg.etf.drs.filmovi2;

import java.util.LinkedList;

public class Buffer {

	private LinkedList<String> buffer;
	private int capacity;
	private int n;
	private int cnt;
	private boolean end;

	public Buffer(int cap, int producerNumber) {
		buffer = new LinkedList<String>();
		this.capacity = cap;
		this.n = producerNumber;
		this.cnt = 0;
		this.end = false;
	}

	public synchronized String get() {
		String res = null;
		while (!end && buffer.isEmpty()) {
			try {
				wait();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		if (buffer.size() > 0) {
			res = buffer.remove();
		}
		notifyAll();
		return res;
	}

	public synchronized void put(String line) {
		if (line == null) {
			cnt++;
			if (cnt == n) {
				end = true;
				notifyAll();
			}
		} else {
			while (buffer.size() == capacity) {
				try {
					wait();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			buffer.add(line);
			notifyAll();
		}

	}
}
