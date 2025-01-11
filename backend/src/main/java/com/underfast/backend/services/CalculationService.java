package com.underfast.backend.services;

import org.springframework.stereotype.Service;

@Service
public class CalculationService {

    public double performCalculation(double input) {
        return Math.pow(input, 2);
    }
}