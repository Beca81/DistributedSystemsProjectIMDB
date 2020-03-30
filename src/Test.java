
public class Test {

	public static void main(String[] args) {
		int x;
		int y;
		x = 10;
		y = 15;
		int z = sub(x, y);

		String result = x + ", " + y + ", " + z;

		System.out.println(result);

		int t = beskorisnaMetoda(2, 10);
		System.out.println(t);

		Calculator c1 = new Calculator();
		c1.upisiUA(9);
		c1.upisiUB(t);

		int r = c1.add();
		System.out.println(r);

		c1.upisiUA(15);

		int e = c1.add();
		System.out.println(e);

		Calculator c2 = c1;
		c2.upisiUA(5);
		c2.upisiUB(c1.a);

		int w = c2.add();
		System.out.println(w);
	}

	static int sub(int a, int b) {
		int c = a - b;

		c = c + 1;

		if (c < 0) {
			c = 0;
		}

		return c;
	}

	static int beskorisnaMetoda(int start, int end) {
		int suma = 0;

		for (int i = start; i <= end; i++) {
			if (i % 3 == 0) {
				suma = suma + i;
			}
		}

		return suma;
	}

}
