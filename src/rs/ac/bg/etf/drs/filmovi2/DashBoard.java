package rs.ac.bg.etf.drs.filmovi2;

public class DashBoard {

	private int status;
	private int n;
	private int cnt;

	public DashBoard(int n) {
		status = 0;
		this.cnt = 0;
		this.n = n;
	}

	public synchronized boolean areDone() {
		return cnt == n;
	}

	public synchronized void done() {
		cnt++;
	}

	public synchronized int readStatus() {
		return status;
	}

	public synchronized void writeStatus(int val) {
		status = status + val;
	}

	// ReadWriteLock rw = new ReentrantReadWriteLock();
	// Lock readLock = rw.readLock();
	// ... writeLock

	/*
	 * read status metoda:
	 *
	 * readLock.lock(); // writeStatus bilo bi samo writeLock.lock try { return
	 * status; } finally { // uvek se izvrsava na kraju readLock.unlock(); //
	 * writeLock.unlock() }
	 */

}
