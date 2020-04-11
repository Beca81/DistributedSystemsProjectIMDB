package rs.ac.bg.etf.drs.filmovi1;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.HashMap;
import java.util.Map;

public class Consumer extends Thread {

	Buffer bufferInListaFilmovaSaReziserima, bufferOut; // sta klasa sadrzi
	Combiner combiner;
	String filmovi;

	public Consumer(Buffer bufferIn, Combiner combiner, Buffer bufferOut, String filmovi) {
		super();
		this.bufferInListaFilmovaSaReziserima = bufferIn;// koja treba da bude vrednost prilikom stvaranja klase
		this.combiner = combiner;
		this.bufferOut = bufferOut;
		this.filmovi = filmovi;
	}

	@Override
	public void run() {
		System.out.println("Consumer pocetak.");

		Map<String, String> trajanjeFilmova = new HashMap<String, String>();
		// ucitaj drugi fajl i ubaci u memoriju tabelu <IDFILMA, TRAJANJE FILMA>
		// Ostavili smo minuti string posto ne radimo manipulaciju sa vremenom vec samo
		// prebacujemo dalje.
		// 6M * 10BAJTOVA+4BAJTA = 6M * 14 = 80MB tabela u memoriji
		{
			// 1. reader za ucitavanje
			// 2. primer iz producera.
			// 3. umesto u buffer, parsiraj
			// 3.1. // u komentar kako izgelda linija
			// 3.2. // split /....
			// 4. ubaci u mapu

			try (BufferedReader reader = new BufferedReader(new FileReader(filmovi));) {
				String lineFilmSaMinutima = reader.readLine(); // linija sa zaglavljem
				int i=0;
				while ((lineFilmSaMinutima = reader.readLine())!= null && i<10) {
					// ucitaj film po film.
					i++;
					String[] elementiNiza = lineFilmSaMinutima.split("\t");
					if (!("\\N".equals(elementiNiza[7]))) {
						String idFilmaIzTabeleMinuti = elementiNiza[0];// id mi treba //
						// tconst titleType primaryTitle originalTitle isAdult startYear endYear
						// runtimeMinutes genres
						// tt0000001 short Carmencita Carmencita 0 1894 \N 1 Documentary,Short
						// tt0000002 short Le clown et ses chiens Le clown et ses chiens 0 1892 \N 5
						// Animation,Short
						//
						trajanjeFilmova.put(idFilmaIzTabeleMinuti, elementiNiza[7]);

					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		System.out.println("Kesiranje u memoriji je uradjeno.");

		// 1. consumer radi u petlji dokle god ima zadataka
		// 2. Uzima jedan po jedan zadatak
		// 3. Obradjuje zadatak
		// 4. Objavljuje lokalni rezultat
		String line = null;
		// Ako je limit -1 (onda nema limita) inace predstavlja broj max filmova
		int limit = -1;
		int brojObradjenih = 0;
		while ((line = bufferInListaFilmovaSaReziserima.get()) != null) {
			brojObradjenih++;

			if ((limit > 0) && (brojObradjenih == limit)) {
				break;
			}

			// Line = jedan film
			// Izgled linije (primer): 12312124<TAB>rez1,rez2,rez3<TAB>pisac1,pisac2
			// tconst directors writers
			// tt0000001 nm0005690 \N
			String[] elementiOdvojeniTabom = line.split("\t");
			if (!("\\N".equals(elementiOdvojeniTabom[1]))) {
				String idFilma = elementiOdvojeniTabom[0];// id mi treba i reziseri
				String[] reziseri = elementiOdvojeniTabom[1].split(",");
				String minuti = trajanjeFilmova.get(idFilma);

				if (minuti == null) {
					// System.out.println("Greska. Film nema vreme trajanja");
					minuti = "0";
				} else {
					// System.out.println("Film IMA vreme trajanja");
				}

				// Sad znamo
				// reziseri, minut i idFilma

				// 1. da se minuti pretvore u String
				// 2. Da se polj

				int numberOfItems = reziseri.length;

				for (int i = 0; i < numberOfItems; i++) {
					String reziseriMinuti = reziseri[i] + "," + minuti;
					bufferOut.put(reziseriMinuti);
				}

			}

		}

		bufferOut.put("KRAJ");
		System.out.println("Consumer run is finished.");

	}

}
