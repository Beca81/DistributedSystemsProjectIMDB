package rs.ac.bg.etf.drs.filmovi2;

public class Printer extends Thread {

	DashBoard dashBoard;
	Buffer buffer;

	public Printer(Buffer buffer, DashBoard dashBoard) {
		this.dashBoard = dashBoard;
		this.buffer = buffer;
	}

	@Override
	public void run() {
		while (!dashBoard.areDone()) {
			try {
				sleep(1000);// podesivo
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			System.out.println(dashBoard.readStatus());
		}
		String element = null;
		while ((element = buffer.get()) != null) {
			System.out.println(element);
		}
	}

}
