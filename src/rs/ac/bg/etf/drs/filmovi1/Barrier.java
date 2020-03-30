package rs.ac.bg.etf.drs.filmovi1;

import java.util.concurrent.Semaphore;

public class Barrier implements BarrierInterface {
	int n;
	int counter = 0; // za pracenje broja niti koje su pristigle na barijeru
	int realMax = 0;
	Semaphore sem1, sem2;

	/**
	 * Stvaranje nove barijere.
	 * 
	 * @param n broj niti koje treba da pristignu na barijeru, pre nego sto mogu sve
	 *          da nastave dalje
	 */
	public Barrier(int n) {
		this.n = n;
		sem1 = new Semaphore(1);
		sem2 = new Semaphore(0);
	}

	/**
	 * Barijera osim sto sinhronizuje niti, tj. blokira prvih (n-1) dok ne dodje i
	 * n-ta, takodje nalazi koja od tih n niti ima film(ove) sa najvecim brojem
	 * rezisera.<br>
	 * To radi tako sto svaka nit javlja koji je njen maksimum, a barijera javlja na
	 * kraju koji je najveci od tih maksimuma (niti imaju svoje "lokalne maksimume",
	 * a barijera nalazi "globalni maksimum").
	 * 
	 * @param currentMax maksimalni broj (rezisera) koji je nit consumer-a nasla
	 * @return stvarno maksimalni broj (rezisera)
	 */
	@Override
	public int sync(int currentMax) {
		sem1.acquireUninterruptibly(); // pustamo jednu nit da prodje
		if (realMax < currentMax) {
			// ako smo dobili veci broj, zapamtimo
			// (nije nam bitno od strane kog consumer-a)
			realMax = currentMax;
		}
		counter++; // uvecavamo broj pristiglih
		if (counter == n) {
			// ako je stiglo njih n, pustamo ih da odlaze, jedan po jedan
			sem2.release();
			// posto ne pustamo sem1, nijedna nova nit ne moze da pocne metodu sync
		} else {
			// u suprotnom, pustamo novu nit da pocne metodu
			sem1.release();
		}

		sem2.acquireUninterruptibly(); // ovde cekamo da svi dodju
		counter--; // smanjujemo broj niti koje su na barijeri
		if (counter > 0) {
			sem2.release(); // ako nisu jos svi otisli, pustamo jos jednog
		} else {
			sem1.release(); // ako su svi otisli, pustamo ponovo da ulaze u metodu sync
		}

		return realMax;
	}

}
