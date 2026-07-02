package com.mayank.spamdetector.controller;

import com.mayank.spamdetector.dto.PredictionRequest;
import com.mayank.spamdetector.service.PythonPredictionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/predict")
public class PredictionController {

    @Autowired
    private PythonPredictionService pythonPredictionService;

    @PostMapping
    public String predict(@RequestBody PredictionRequest request) {

        return pythonPredictionService.predict(request.getMessage());

    }
}