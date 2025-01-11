package com.underfast.backend.controllers;

import com.underfast.backend.services.CalculationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class CalculationController {

    @Autowired
    private CalculationService calculationService;

    @GetMapping("/calculate")
    public double calculate(@RequestParam double input) {
        return calculationService.performCalculation(input);
    }
}