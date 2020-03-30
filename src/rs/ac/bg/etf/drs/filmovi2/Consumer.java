package rs.ac.bg.etf.drs.filmovi2;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

public class Consumer extends Thread {

	Buffer bufferIn, bufferOut;
	DashBoard dashBoard;
	Map<String, Integer> statistic;

	public Consumer(Buffer bufferIn, Buffer bufferOut, DashBoard dashBoard) {
		super();
		this.bufferIn = bufferIn;
		this.bufferOut = bufferOut;
		this.dashBoard = dashBoard;

		this.statistic = new HashMap<>();
	}

	@Override
	public void run() {
		String line = null;
		int cnt = 0;
		while ((line = bufferIn.get()) != null) {
			parseLine(line);

			cnt++;
			if (cnt % 100 == 0) {// podesivo
				dashBoard.writeStatus(cnt);
				cnt = 0;
			}
		}

		if (cnt % 100 != 0) {
			dashBoard.writeStatus(cnt);
			cnt = 0;
		}

		for (Entry<String, Integer> val : statistic.entrySet()) {
			line = val.getKey() + "," + val.getValue();
			bufferOut.put(line);
		}
		bufferOut.put(null); // buffer.end == buffer.put(null)

		dashBoard.done();

	}

	private void parseLine(String line) {
		String[] args = line.split("\t");
		if (!("\\N".equals(args[5]))) {
			int year = Integer.parseInt(args[5]);
			if (year >= 2019) {
				String types[] = args[8].split(",");
				for (String type : types) {
					String yearType = "" + year + "-" + type;
					Integer number = statistic.get(yearType);
					if (number == null) {
						number = 1;
					} else {
						number = number + 1;
					}
					statistic.put(yearType, number);
				}
			}
		}
	}

}
