package com.underfast.backend.controllers;

import com.underfast.backend.services.StationService;
import java.util.ArrayList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class CalculationController {

    @Autowired
    private StationService stationService;

    @GetMapping("/calculate")
    public double calculate(@RequestParam int departure, @RequestParam int arrival) {
        ArrayList<Integer> visitados = new ArrayList<Integer>();
        double distance = 0;
        return stationService.sumCamino(departure, arrival, distance, visitados);
    }
}