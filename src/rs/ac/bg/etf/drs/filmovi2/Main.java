package rs.ac.bg.etf.drs.filmovi2;

public class Main {

	public static void main(String[] args) {
		int cap = 10;
		int n = 5;
		String fileName = "data.tsv";
		Buffer buffer1 = new Buffer(cap, 1);
		Buffer buffer2 = new Buffer(cap, n);
		Buffer buffer3 = new Buffer(cap, 1);

		DashBoard dashBoard = new DashBoard(n + 1);

		long start = System.currentTimeMillis();
		System.out.println("Pocetak: " + start);

		Producer producer = new Producer(fileName, buffer1);
		producer.setName("Producer");
		producer.start();

		for (int i = 0; i < n; i++) {
			Consumer consumer = new Consumer(buffer1, buffer2, dashBoard);
			consumer.setName("Consumer" + i);
			consumer.start();
		}

		Combiner combiner = new Combiner(buffer2, buffer3, dashBoard);
		combiner.setName("Combiner");
		combiner.start();

		Printer printer = new Printer(buffer3, dashBoard);
		printer.setName("Printer");
		printer.start();

		try {
			printer.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		long end = System.currentTimeMillis();
		System.out.println("Kraj: " + end);
		long duration = end - start;
		System.out.println("Trajanje: " + (duration / 1000.) + " s");
	}
}
