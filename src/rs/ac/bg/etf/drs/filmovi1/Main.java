package rs.ac.bg.etf.drs.filmovi1;



public class Main {

	public static void main(String[] args) {
		int cap = 10;
		int n = 5; // broj potrosaca
		String filmovi = "C:\\Users\\brank_000\\Desktop\\IT\\MASTER IT\\data1.tsv";
		String reziseriPutanja ="C:\\Users\\brank_000\\Desktop\\IT\\MASTER IT\\data.tsv";
		
		Buffer buffer1 = new Buffer(cap);
		Buffer buffer2 = new Buffer(cap);
		Buffer buffer3 = new Buffer(cap);
		
		Barrier barrier = new Barrier(n);

		long start = System.currentTimeMillis();
		System.out.println("Pocetak: " + start);

		Producer producer = new Producer(reziseriPutanja, buffer1);
		producer.setName("Producer");
		producer.start();
		
		Combiner combiner = new Combiner(buffer2, buffer3);
		combiner.setName("Combiner");
		combiner.start();

		for (int i = 0; i < n; i++) {
			Consumer consumer = new Consumer(buffer1, barrier, buffer2,  filmovi);
			consumer.setName("Consumer" + i);
			consumer.start();
		}

		Printer printer = new Printer(buffer3);
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