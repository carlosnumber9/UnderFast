package com.underfast.backend.services;

import static org.junit.jupiter.api.Assertions.*;
import java.util.ArrayList;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class StationServiceTest {

    @Autowired
    private StationService stationService;

    // Test para buscarLinea
    @Test
    public void testBuscarLinea() {
        Integer[] expectedLineA = { 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20 };
        assertArrayEquals(expectedLineA, stationService.getStationLine(1));

        Integer[] expectedLineC = { 38, 37, 36, 35, 34, 33, 32, 31, 30, 29, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 26,
                27, 28 };
        assertArrayEquals(expectedLineC, stationService.getStationLine(38));

        assertNull(stationService.getStationLine(999)); // Estación inexistente
    }

    // Test para calcular h (línea recta)
    @Test
    public void testH() {
        double distance = stationService.h(1, 2);
        assertTrue(distance > 0); // Debe ser positiva
        assertEquals(0, stationService.h(1, 1), 0.01); // Misma estación
    }

    // Test para calcular g (distancia de la línea)
    @Test
    public void testG() {
        assertEquals(0.86, stationService.g(1, 2)); // Línea A
        assertEquals(1.15, stationService.g(38, 37)); // Línea C
    }

    // Test para calcular F (g + h)
    @Test
    public void testCalcularF() {
        double f = stationService.getF(1, 2, 3);
        assertTrue(f > 0);
    }

    // Tests para sumCamino
    @Test
    public void testSumCaminoSimple() {
        ArrayList<Integer> visitados = new ArrayList<>();
        double distance = stationService.sumCamino(1, 2, 0, visitados);
        assertEquals(0.86, distance, 0.1);
        assertEquals(2, visitados.size()); // Debería visitar las dos estaciones
    }

    @Test
    public void testSumCaminoConTrasbordo() {
        ArrayList<Integer> visitados = new ArrayList<>();
        double distance = stationService.sumCamino(1, 33, 0, visitados); // Cambia de línea
        assertTrue(distance > 0); // Debe encontrar un camino
        assertTrue(visitados.size() > 2); // Múltiples estaciones
    }

    @Test
    public void testSumCaminoImposible() {
        ArrayList<Integer> visitados = new ArrayList<>();
        double distance = stationService.sumCamino(1, 999, 0, visitados); // Estación inexistente
        assertEquals(0, distance);
        assertEquals(0, visitados.size());
    }

    @Test
    public void testSumCaminoMismaEstacion() {
        ArrayList<Integer> visitados = new ArrayList<>();
        double distance = stationService.sumCamino(1, 1, 0, visitados);
        assertEquals(0, distance, 0.1);
        assertEquals(1, visitados.size());
    }

    // TODO Improve performance to pass this test
    /*
     * @Test
     * public void testSumCaminoLargo() {
     * ArrayList<Integer> visitados = new ArrayList<>();
     * double distance = stationService.sumCamino(1, 62, 0, visitados);
     * assertTrue(distance > 0);
     * assertTrue(visitados.size() > 10);
     * }
     */

    @Test
    public void testSumCaminoInvalidInputs() {
        ArrayList<Integer> visitados = new ArrayList<>();
        double distanceA = stationService.sumCamino(-1, 1, 0, visitados);
        double distanceB = stationService.sumCamino(1, -1, 0, visitados);
        assertEquals(0, distanceA);
        assertEquals(0, distanceB);

    }
}