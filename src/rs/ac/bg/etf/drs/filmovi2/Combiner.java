package rs.ac.bg.etf.drs.filmovi2;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

public class Combiner extends Thread {

	Buffer bufferIn, bufferOut;
	DashBoard dashBoard;
	Map<String, Integer> statistic;

	public Combiner(Buffer bufferIn, Buffer bufferOut, DashBoard dashBoard) {
		super();
		this.bufferIn = bufferIn;
		this.bufferOut = bufferOut;
		this.dashBoard = dashBoard;

		this.statistic = new HashMap<>();
	}

	@Override
	public void run() {
		String line = null;
		while ((line = bufferIn.get()) != null) {
			parseLine(line);
		}

		dashBoard.done();

		for (Entry<String, Integer> val : statistic.entrySet()) {
			line = val.getKey() + "," + val.getValue();
			bufferOut.put(line);
		}
		bufferOut.put(null); // buffer.end == buffer.put(null)

	}

	private void parseLine(String line) {
		String[] args = line.split(",");
		Integer number = statistic.get(args[0]); // agrs[0] = "2015-Drama"
		int newValue = Integer.parseInt(args[1]);
		if (number == null) {
			number = newValue;
		} else {
			number = number + newValue;
		}
		statistic.put(args[0], number);
	}
}
