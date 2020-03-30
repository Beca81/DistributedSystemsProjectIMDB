package rs.ac.bg.etf.drs.filmovi1;
import java.util.HashMap;
import java.util.Map;
/**
 * Klasa koja za sada samo cita iz jednog bafera i prebacuje u drugi.<br>
 * To je zato sto je vecinu posla uradila barijera. U suprotnom, combiner bi
 * izgledao slicno kao consumer - citao bi filmove i trazio onaj sa najvise
 * rezisera.
 */
public class Combiner extends Thread {

	Buffer bufferIn, bufferOut;

	public Combiner(Buffer bufferIn, Buffer bufferOut) {
		super();
		this.bufferIn = bufferIn;
		this.bufferOut = bufferOut;
	}

	@Override
	public void run() {
		
		Integer ukupniMinuti = 0;
		String gotovo = null;
		String reziser = null;
		String porukaOdConsumera = null;
		Map<String, Integer> map = new HashMap<String, Integer>();
		int primljenihPoruka = 0;
		
		// TODO - resiti tako da se inicijalizuje broj consumera u konstruktoru 
		int brojConsumera = 2;
		int stigloKrajPoruka = 0;
		while ((porukaOdConsumera = bufferIn.get()) != null) {
			
			if (porukaOdConsumera.equals("KRAJ"))
			{
				stigloKrajPoruka++;
				
				if (stigloKrajPoruka == brojConsumera)
				{
					break;
				}
				
			}
			else
			{
				primljenihPoruka++;
					
				// line primer
				//string --> reziseri, minuti
				// 1. parse (  
				String[] elementiOdvojeniZarezom = porukaOdConsumera.split(",");			
	
				// 2. uzmi minute -> integer
				Integer minuti = Integer.parseInt(elementiOdvojeniZarezom[1]);
	
				// 3. dodaj minute na reziera u hashmapi (potrazi ideju na engleskom_
				//myMap.put(key, myMap.get(key) + 1)
				
				// 3.1. vidi da li postoji kljuc i ako postoji uzmi vrednost
				// 3.2. Ako kljuc ne postoji, lokalniZbirniMinuti su null!!!!!!!!!!!!!!!!!!!!!
				reziser = elementiOdvojeniZarezom[0];
				Integer lokalniZbirniMinuti = map.get(reziser);
				if (map.get(reziser)==null){
					lokalniZbirniMinuti=0;
				}
				// 3.3. dodaj na lokalne zbirne minute, trenutne minute od consumera
				ukupniMinuti =  minuti + lokalniZbirniMinuti;
				
				// 3.4. vrati na mesto elementa u hashmap-i 			
				map.put(reziser, ukupniMinuti);	

			}
		}
			bufferOut.put(map.toString());//bufferOut.put(gotovo); // buffer.end
	}

}
