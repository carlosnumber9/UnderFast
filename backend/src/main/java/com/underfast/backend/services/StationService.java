package com.underfast.backend.services;

import java.util.ArrayList;
import org.springframework.stereotype.Service;

@Service
public class StationService {

	static double distAcc = 0;

	// Lista de los nombres de las estaciones que componen el Metro, ordenadas segun
	// el numero que
	// le ha sido asignado a cada una de cara al codigo del programa.

	String[] estaciones = { "Schiedam Centrum", "Marconiplein", "Delfshaven", "Coolhaven", "Dijkzigt",
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
	int[] trasbordos = { 1, 7, 13, 48 };

	// Coordenadas geograficas de las estaciones del Metro

	double[] latitud = { 51.921662, 51.9133069, 51.9100773, 51.9095579, 51.9121763, 51.9159113, 51.9182908, 51.9198553,
			51.9232922, 51.9259773, 51.9251463, 51.9215103, 51.9209163, 51.9326143, 51.9400373, 51.9449323,
			51.9519653, 51.9574683, 51.9615093, 51.9606631, 51.9631505, 51.9647623, 51.9657203, 51.9687893,
			51.9790283, 51.9284952, 51.9315416, 51.9357673, 51.9216253, 51.9152733, 51.9083113, 51.884653,
			51.8626933, 51.863156, 51.8555253, 51.8466693, 51.8382113, 51.8329195, 51.8483803, 51.859134,
			51.8746018, 51.8864693, 51.8970923, 51.9039323, 51.9080103, 51.9137043, 51.9236944, 51.9239583,
			51.9308916, 51.948184, 51.9556013, 51.976994, 51.9890453, 52.0046513, 52.0203457, 52.0455183,
			52.065252, 52.0703203, 52.0772991, 52.082256, 52.078983, 52.080834 };
	double[] longitud = { 4.409289, 4.4305555, 4.4435763, 4.4583404, 4.4645977, 4.4709607, 4.4791095, 4.4869727,
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
	 * @param estacion - id numerico de la estacion de la que se quiere saber la
	 *                 linea en la que se encuentra.
	 * @return Devuelve la linea en la que se encuentra la estacion pasada por
	 *         parametro
	 */
	public int[] buscarLinea(int estacion) {

		boolean terminado = false;
		int[] resultado = null;

		for (int i = 0; i < LA.length && !terminado; i++) {
			if (LA[i] == estacion) {
				terminado = true;
				resultado = LA;
			}
		}
		for (int i = 0; i < LB.length && !terminado; i++) {
			if (LB[i] == estacion) {
				terminado = true;
				resultado = LB;
			}
		}
		for (int i = 0; i < LC.length && !terminado; i++) {
			if (LC[i] == estacion) {
				terminado = true;
				resultado = LC;
			}
		}
		for (int i = 0; i < LD.length && !terminado; i++) {
			if (LD[i] == estacion) {
				terminado = true;
				resultado = LD;
			}
		}
		for (int i = 0; i < LE.length && !terminado; i++) {
			if (LE[i] == estacion) {
				terminado = true;
				resultado = LE;
			}
		}

		return resultado;
	}

	/**
	 * 
	 * @param linea    - Lista de estaciones entre las que se encuentra aquella de
	 *                 la que se quiere saber la posicion.
	 * @param estacion - id numerico de la estacion de la que se quiere saber su
	 *                 posicion.
	 * @return Devuelve la posicion de la linea en la que se encuentra la estacion
	 *         pasada por parametro.
	 */
	public int buscaIndice(int[] linea, int estacion) {
		int resultado = 0;
		for (int i = 0; i < linea.length; i++) {
			if (linea[i] == estacion) {
				resultado = i;
			}
		}
		return resultado;
	}

	/**
	 * 
	 * @param estacion - id numerico de la estacion de origen del trayecto.
	 * @param destino  - id numerico de la estacion de destino del trayecto.
	 * @return Devuelve la distancia (En km) de la linea recta que separa las
	 *         estaciones pasadas como parametro.
	 */
	public double h(int estacion, int destino) {

		double latEst = latitud[estacion - 1];
		double latDest = latitud[destino - 1];
		double lonEst = longitud[estacion - 1];
		double lonDest = longitud[destino - 1];

		final double radioTierra = 6371;

		double Alat = Math.abs(latEst - latDest);
		Alat = Math.toRadians(Alat);

		double Alon = Math.abs(lonEst - lonDest);
		Alon = Math.toRadians(Alon);

		double a = Math.pow(Math.sin(Alat / 2), 2) + Math.cos(Math.toRadians(latEst))
				* Math.cos(Math.toRadians(latDest)) * Math.pow(Math.sin(Alon / 2), 2);

		double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));

		return radioTierra * c;

	}

	/**
	 * 
	 * @param estacion - id numerico de la estacion de origen del trayecto
	 * @param destino  - id numerico de la estacion de destino del trayecto
	 * @return Devuelve la distancia entre las estaciones pasadas como parametro,
	 *         siguiendo el trayecto por las vias de Metro.
	 */
	public double g(int estacion, int destino) {
		double g = 0;
		int[] linea = buscarLinea(destino);

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
	 * @param estacion  - id numerico de la estacion de origen de la iteracion del
	 *                  algoritmo.
	 * @param siguiente - id numerico de la estacion siguiente de la iteracion del
	 *                  algoritmo.
	 * @param destino   - id numerico de la estacion de destino.
	 * @return
	 */
	public double calcularF(int estacion, int siguiente, int destino) {
		double h = 0;
		double g = 0;

		h = h(siguiente, destino);
		g = g(estacion, siguiente);

		return g + h;
	}

	/**
	 * 
	 * @param estacion  - id numerico de la estacion en la que se desea comenzar el
	 *                  trayecto.
	 * @param destino   - id numerico de la estacion en la que se termina el
	 *                  recorrido.
	 * @param distancia - Resultado final (Es un valor modificado recursivamente)
	 * @param visitados - Lista con las estaciones que se van visitando durante el
	 *                  recorrido (Es un valor modificado recursivamente)
	 * @return
	 */
	public double sumCamino(int estacion, int destino, double distancia, ArrayList<Integer> visitados) {

		if(estacion < 0 || destino < 0) {
			return 0;
		}

		int[] departureLine = buscarLinea(estacion);
		int[] arrivalLine = buscarLinea(destino);

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
		if (estacion == destino) {

			if(visitados.size() == 0) {
				visitados.add(destino);
				return 0;
			}

			visitados.add(destino);

			distAcc += g(estacion, destino);

			System.out.println("Trayecto recorrido:");
			for (int i = 0; i < visitados.size(); i++) {
				int parada = visitados.get(i);
				System.out.println((i + 1) + ". " + estaciones[parada - 1] + " [" + parada + "]");
			}

			return distAcc;
		}

		else if (buscarLinea(destino) == LE || buscarLinea(estacion) == LE) {
			return sumCamino(estacion, 7, distancia, visitados) + sumCamino(destino, 7, distancia, visitados);
		}

		else {

			visitados.add(estacion);

			int[] linea = buscarLinea(estacion);

			int indice = buscaIndice(linea, estacion);

			ArrayList<Integer> adyacentes = new ArrayList<Integer>();

			switch (estacion) {
				case 1:
					adyacentes.add(29);
					adyacentes.add(2);
					break;
				case 33:
					adyacentes.add(34);
					adyacentes.add(32);
					adyacentes.add(39);
					break;
				case 41:
					adyacentes.add(40);
					adyacentes.add(42);
					break;
				case 7:
					adyacentes.add(6);
					adyacentes.add(8);
					adyacentes.add(46);
					adyacentes.add(47);
					break;
				case 48:
					adyacentes.add(47);
					adyacentes.add(49);
					break;
				case 18:
					adyacentes.add(19);
					adyacentes.add(21);
					adyacentes.add(17);
					break;
				case 13:
					adyacentes.add(12);
					adyacentes.add(14);
					adyacentes.add(26);
					break;
				case 62:
					adyacentes.add(61);
					break;
				case 28:
					adyacentes.add(27);
					break;
				case 20:
					adyacentes.add(19);
					break;
				case 25:
					adyacentes.add(24);
					break;
				case 38:
					adyacentes.add(37);
					break;
				default:
					adyacentes.add(linea[indice - 1]);
					adyacentes.add(linea[indice + 1]);
					break;
			}

			ArrayList<Integer> opciones = new ArrayList<Integer>();

			for (int i = 0; i < adyacentes.size(); i++) {
				int est = adyacentes.get(i);
				if (!visitados.contains(est)) {
					opciones.add(est);
				}
			}

			ArrayList<Integer> opciones2 = new ArrayList<Integer>();
			for (int i = 0; i < opciones.size(); i++) {
				int est = opciones.get(i);
				int[] lineaEst = buscarLinea(est);
				int[] lineaDest = buscarLinea(destino);

				if (lineaEst == lineaDest) {
					opciones2.add(est);
				}
			}

			if (!opciones2.isEmpty())
				opciones = opciones2;

			if (opciones.size() == 0) {
				System.out.println("");
				System.out.println("XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX");
				System.out.println("No se ha encontrado un trayecto valido para llegar al destino.");
				System.out.println("XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX");
				System.out.println("");
				return 0;
			} else {
				int pasoSig = opciones.get(0);
				if (opciones.size() == 1 && opciones.get(0) == destino) {
					distancia = sumCamino(pasoSig, destino, distancia, visitados);
				} else {

					double f = 100;
					for (int i = 0; i < opciones.size(); i++) {
						int posible = opciones.get(i);
						double ff = calcularF(estacion, posible, destino);
						if (ff < f) {
							pasoSig = posible;
							f = ff;
						}

					}

					distAcc += g(estacion, pasoSig);

					distancia = sumCamino(pasoSig, destino, distancia, visitados);
				}
				return distancia;
			}
		}

	}
}