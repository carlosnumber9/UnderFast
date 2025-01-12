package com.underfast.backend.services;

import java.util.ArrayList;
import org.springframework.stereotype.Service;

@Service
public class StationService {

	static double distAcc = 0;

	// Lista de los nombres de las estaciones que componen el Metro, ordenadas segun
	// el numero que
	// le ha sido asignado a cada una de cara al codigo del programa.

	String[] stations = { "Schiedam Centrum", "Marconiplein", "Delfshaven", "Coolhaven", "Dijkzigt",
			"Eendrachtsplein", "Beurs", "Blaak", "Oostplein", "Gerdesiaweg", "Voorschoterlaan",
			"Kralingse Zoom", "Capelsebrug", "Schenkel", "Prinsenlaan", "Oosterflank", "Alexander",
			"Graskruid", "Romeynshof", "Binnenhof", "Hesseplaats", "Nieuw Verlaat", "Amlachtsland",
			"De Tochten", "Nesselande", "Slotlaan", "Cappelle Centrum", "De Terp", "Parkweg",
			"Troelstralaan", "Vijfsluizen", "Pernis", "Tussenwater", "Hoogvliet", "Zalmplaat",
			"Spijkenisse Centrum", "Heemraadiaan", "De Akkers", "Poortugaal", "Rhoon", "Slinge",
			"Zuidplein", "Maashaven", "Rijnhaven", "Wilhelminaplein", "Leuvehaven", "Stadhuis",
			"Rotterdam Centraal", "Blijdorp", "Melachthonweg", "Meijersplein", "Rodenrijs",
			"Berkel Westpolder", "Pijnacker Zuid", "Pijkacker Centrum", "Nootdorp", "Leidschenveen",
			"Forepark", "Leidschendam-Voorburg", "Voortburg't Loo", "Laan van NOI", "Den Haag Centraal" };

	// Listas de estaciones de cada una de las lineas, incluyendo la lista de
	// trasbordos

	int[] LA = { 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20 };
	int[] LB = { 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 21, 22, 23, 24, 25 };
	int[] LC = { 38, 37, 36, 35, 34, 33, 32, 31, 30, 29, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 26, 27, 28 };
	int[] LD = { 38, 37, 36, 35, 34, 33, 39, 40, 41, 42, 43, 44, 45, 46, 7, 47, 48 };
	int[] LE = { 41, 42, 43, 44, 45, 46, 7, 47, 48, 49, 50, 51, 52, 53, 54, 55, 56, 57, 58, 59, 60, 61, 62 };
	int[] connections = { 1, 7, 13, 48 };

	// Coordenadas geograficas de las estaciones del Metro

	double[] latitudes = { 51.921662, 51.9133069, 51.9100773, 51.9095579, 51.9121763, 51.9159113, 51.9182908, 51.9198553,
			51.9232922, 51.9259773, 51.9251463, 51.9215103, 51.9209163, 51.9326143, 51.9400373, 51.9449323,
			51.9519653, 51.9574683, 51.9615093, 51.9606631, 51.9631505, 51.9647623, 51.9657203, 51.9687893,
			51.9790283, 51.9284952, 51.9315416, 51.9357673, 51.9216253, 51.9152733, 51.9083113, 51.884653,
			51.8626933, 51.863156, 51.8555253, 51.8466693, 51.8382113, 51.8329195, 51.8483803, 51.859134,
			51.8746018, 51.8864693, 51.8970923, 51.9039323, 51.9080103, 51.9137043, 51.9236944, 51.9239583,
			51.9308916, 51.948184, 51.9556013, 51.976994, 51.9890453, 52.0046513, 52.0203457, 52.0455183,
			52.065252, 52.0703203, 52.0772991, 52.082256, 52.078983, 52.080834 };
	double[] longitudes = { 4.409289, 4.4305555, 4.4435763, 4.4583404, 4.4645977, 4.4709607, 4.4791095, 4.4869727,
			4.4942505, 4.5036857, 4.5104337, 4.5317867, 4.5547025, 4.5611957, 4.5545297, 4.5528097,
			4.5495807, 4.5473397, 4.5404407, 4.5331653, 4.5502793, 4.5593537, 4.5677907, 4.5761027,
			4.5841857, 4.5762011, 4.5878075, 4.5976667, 4.3919787, 4.3817117, 4.3697167, 4.382126,
			4.3739257, 4.3572242, 4.3610657, 4.3323277, 4.3293767, 4.3160477, 4.3978757, 4.419200,
			4.475479, 4.4856637, 4.4924247, 4.4947307, 4.4897087, 4.4795807, 4.4760364, 4.4678113,
			4.4561723, 4.464738, 4.4600267, 4.460434, 4.4528147, 4.4439987, 4.4351251, 4.3920017,
			4.399744, 4.3902117, 4.3805694, 4.365416, 4.342635, 4.324196 };

	// Distancias entre paradas de una misma linea (km)

	static double dA = 0.86;
	static double dB = 0.87;
	static double dC = 1.15;
	static double dD = 1.2;
	static double dE = 1.17;

	/**
	 * 
	 * @param station - id numerico de la estacion de la que se quiere saber la
	 *                 linea en la que se encuentra.
	 * @return Devuelve la linea en la que se encuentra la estacion pasada por
	 *         parametro
	 */
	public int[] getStationLine(int station) {

		boolean found = false;
		int[] result = null;

		for (int i = 0; i < LA.length && !found; i++) {
			if (LA[i] == station) {
				found = true;
				result = LA;
			}
		}
		for (int i = 0; i < LB.length && !found; i++) {
			if (LB[i] == station) {
				found = true;
				result = LB;
			}
		}
		for (int i = 0; i < LC.length && !found; i++) {
			if (LC[i] == station) {
				found = true;
				result = LC;
			}
		}
		for (int i = 0; i < LD.length && !found; i++) {
			if (LD[i] == station) {
				found = true;
				result = LD;
			}
		}
		for (int i = 0; i < LE.length && !found; i++) {
			if (LE[i] == station) {
				found = true;
				result = LE;
			}
		}

		return result;
	}

	/**
	 * 
	 * @param line    - Lista de estaciones entre las que se encuentra aquella de
	 *                 la que se quiere saber la posicion.
	 * @param station - id numerico de la estacion de la que se quiere saber su
	 *                 posicion.
	 * @return Devuelve la posicion de la linea en la que se encuentra la estacion
	 *         pasada por parametro.
	 */
	public int getStationIndexInLine(int[] line, int station) {
		int resultado = 0;
		for (int i = 0; i < line.length; i++) {
			if (line[i] == station) {
				resultado = i;
			}
		}
		return resultado;
	}

	/**
	 * 
	 * @param stationFrom - id numerico de la estacion de origen del trayecto.
	 * @param stationTo  - id numerico de la estacion de destino del trayecto.
	 * @return Devuelve la distancia (En km) de la linea recta que separa las
	 *         estaciones pasadas como parametro.
	 */
	public double h(int stationFrom, int stationTo) {

		double latitudeFrom = latitudes[stationFrom - 1];
		double latitudeTo = latitudes[stationTo - 1];
		double longitudeFrom = longitudes[stationFrom - 1];
		double longitudeTo = longitudes[stationTo - 1];

		final double earthRadius = 6371;

		double Alat = Math.abs(latitudeFrom - latitudeTo);
		Alat = Math.toRadians(Alat);

		double Alon = Math.abs(longitudeFrom - longitudeTo);
		Alon = Math.toRadians(Alon);

		double a = Math.pow(Math.sin(Alat / 2), 2) + Math.cos(Math.toRadians(latitudeFrom))
				* Math.cos(Math.toRadians(latitudeTo)) * Math.pow(Math.sin(Alon / 2), 2);

		double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));

		return earthRadius * c;

	}

	/**
	 * 
	 * @param stationFrom - id numerico de la estacion de origen del trayecto
	 * @param stationTo  - id numerico de la estacion de destino del trayecto
	 * @return Devuelve la distancia entre las estaciones pasadas como parametro,
	 *         siguiendo el trayecto por las vias de Metro.
	 */
	public double g(int stationFrom, int stationTo) {
		double g = 0;
		int[] linea = getStationLine(stationTo);

		if (linea.equals(LA)) {
			g = dA;
		}
		if (linea.equals(LB)) {
			g = dB;
		}
		if (linea.equals(LC)) {
			g = dC;
		}
		if (linea.equals(LD)) {
			g = dD;
		}
		if (linea.equals(LE)) {
			g = dE;
		}

		return g;

	}

	/**
	 * 
	 * @param stationFrom  - id numerico de la estacion de origen de la iteracion del
	 *                  algoritmo.
	 * @param nextStation - id numerico de la estacion siguiente de la iteracion del
	 *                  algoritmo.
	 * @param stationTo   - id numerico de la estacion de destino.
	 * @return
	 */
	public double getF(int stationFrom, int nextStation, int stationTo) {
		double h = 0;
		double g = 0;

		h = h(nextStation, stationTo);
		g = g(stationFrom, nextStation);

		return g + h;
	}

	/**
	 * 
	 * @param stationFrom  - id numerico de la estacion en la que se desea comenzar el
	 *                  trayecto.
	 * @param stationTo   - id numerico de la estacion en la que se termina el
	 *                  recorrido.
	 * @param distance - Resultado final (Es un valor modificado recursivamente)
	 * @param visitedStations - Lista con las estaciones que se van visitando durante el
	 *                  recorrido (Es un valor modificado recursivamente)
	 * @return
	 */
	public double sumCamino(int stationFrom, int stationTo, double distance, ArrayList<Integer> visitedStations) {

		if(stationFrom < 0 || stationTo < 0) {
			return 0;
		}

		int[] departureLine = getStationLine(stationFrom);
		int[] arrivalLine = getStationLine(stationTo);

		if (departureLine == null || arrivalLine == null) {
			return 0;
		}

		/*
		 * Si la estacion de origen coincide con la de destino, sumamos al acumulador
		 * una vez la distancia
		 * media entre paradas de la linea en la que se encuentre y devolvemos como
		 * resultado el propio acumulador
		 * (Se ha terminado el algoritmo)
		 */
		if (stationFrom == stationTo) {

			if(visitedStations.size() == 0) {
				visitedStations.add(stationTo);
				return 0;
			}

			visitedStations.add(stationTo);

			distAcc += g(stationFrom, stationTo);

			System.out.println("Trayecto recorrido:");
			for (int i = 0; i < visitedStations.size(); i++) {
				int parada = visitedStations.get(i);
				System.out.println((i + 1) + ". " + stations[parada - 1] + " [" + parada + "]");
			}

			return distAcc;
		}

		else if (getStationLine(stationTo) == LE || getStationLine(stationFrom) == LE) {
			return sumCamino(stationFrom, 7, distance, visitedStations) + sumCamino(stationTo, 7, distance, visitedStations);
		}

		else {

			visitedStations.add(stationFrom);

			int[] linea = getStationLine(stationFrom);

			int indice = getStationIndexInLine(linea, stationFrom);

			ArrayList<Integer> adjacentStations = new ArrayList<Integer>();

			switch (stationFrom) {
				case 1:
					adjacentStations.add(29);
					adjacentStations.add(2);
					break;
				case 33:
					adjacentStations.add(34);
					adjacentStations.add(32);
					adjacentStations.add(39);
					break;
				case 41:
					adjacentStations.add(40);
					adjacentStations.add(42);
					break;
				case 7:
					adjacentStations.add(6);
					adjacentStations.add(8);
					adjacentStations.add(46);
					adjacentStations.add(47);
					break;
				case 48:
					adjacentStations.add(47);
					adjacentStations.add(49);
					break;
				case 18:
					adjacentStations.add(19);
					adjacentStations.add(21);
					adjacentStations.add(17);
					break;
				case 13:
					adjacentStations.add(12);
					adjacentStations.add(14);
					adjacentStations.add(26);
					break;
				case 62:
					adjacentStations.add(61);
					break;
				case 28:
					adjacentStations.add(27);
					break;
				case 20:
					adjacentStations.add(19);
					break;
				case 25:
					adjacentStations.add(24);
					break;
				case 38:
					adjacentStations.add(37);
					break;
				default:
					adjacentStations.add(linea[indice - 1]);
					adjacentStations.add(linea[indice + 1]);
					break;
			}

			ArrayList<Integer> options = new ArrayList<Integer>();

			for (int i = 0; i < adjacentStations.size(); i++) {
				int est = adjacentStations.get(i);
				if (!visitedStations.contains(est)) {
					options.add(est);
				}
			}

			ArrayList<Integer> options2 = new ArrayList<Integer>();
			for (int i = 0; i < options.size(); i++) {
				int est = options.get(i);
				int[] lineaEst = getStationLine(est);
				int[] lineaDest = getStationLine(stationTo);

				if (lineaEst == lineaDest) {
					options2.add(est);
				}
			}

			if (!options2.isEmpty())
				options = options2;

			if (options.size() == 0) {
				System.out.println("");
				System.out.println("XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX");
				System.out.println("No se ha encontrado un trayecto valido para llegar al destino.");
				System.out.println("XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX");
				System.out.println("");
				return 0;
			} else {
				int nextStep = options.get(0);
				if (options.size() == 1 && options.get(0) == stationTo) {
					distance = sumCamino(nextStep, stationTo, distance, visitedStations);
				} else {

					double f = 100;
					for (int i = 0; i < options.size(); i++) {
						int possibility = options.get(i);
						double ff = getF(stationFrom, possibility, stationTo);
						if (ff < f) {
							nextStep = possibility;
							f = ff;
						}

					}

					distAcc += g(stationFrom, nextStep);

					distance = sumCamino(nextStep, stationTo, distance, visitedStations);
				}
				return distance;
			}
		}

	}
}